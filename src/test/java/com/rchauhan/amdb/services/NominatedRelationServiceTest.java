package com.rchauhan.amdb.services;

import com.rchauhan.amdb.exceptions.AwardDoesNotExistException;
import com.rchauhan.amdb.exceptions.NominatedRelationExistsException;
import com.rchauhan.amdb.exceptions.PersonDoesNotExistException;
import com.rchauhan.amdb.model.Award;
import com.rchauhan.amdb.model.NominatedRelation;
import com.rchauhan.amdb.model.Person;
import com.rchauhan.amdb.repositories.NominatedRelationRepository;
import com.rchauhan.amdb.utils.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NominatedRelationServiceTest {

    @Mock
    NominatedRelationRepository nominatedRelationRepository;

    @Mock
    PersonService personService;

    @Mock
    AwardService awardService;

    @InjectMocks
    private NominatedRelationService nominatedRelationService = new NominatedRelationService();

    private UUID personID = UUID.randomUUID();
    private UUID awardID = UUID.randomUUID();
    private String personName = "Leonardo DiCaprio";
    private Date personDOB = DateUtil.createDate("dd-MMM-yyyy", "11-Nov-1974");
    private String awardName = "Best Performance by an Actor in a Leading Role";
    private String awardOrganisation = "Academy Awards";
    private Integer nominationYear = 2014;
    private String titleName = "The Wolf of Wall Street";
    private Integer titleReleased = 2013;

    @Test
    public void createNominatedRelationWhenPersonDoesNotExistTest() {
        when(personService.getPersonByNameAndDateOfBirth(personName, personDOB)).thenReturn(Optional.empty());
        Exception exception = assertThrows(PersonDoesNotExistException.class, () -> {
            nominatedRelationService.createNominatedRelation(personName, personDOB, awardName, awardOrganisation, nominationYear, titleName, titleReleased);
        });
        String expectedMessage = "Cannot create NOMINATED relation between Person Leonardo DiCaprio and " +
                "Award Best Performance by an Actor in a Leading Role. Person does not exist.";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void createNominationRelationWhenAwardDoesNotExistTest() {
        when(personService.getPersonByNameAndDateOfBirth(personName, personDOB)).thenReturn(Optional.of(new Person(personID)));
        when(awardService.getAwardByNameAndOrganisation(awardName, awardOrganisation)).thenReturn(Optional.empty());
        Exception exception = assertThrows(AwardDoesNotExistException.class, () -> {
            nominatedRelationService.createNominatedRelation(personName, personDOB, awardName, awardOrganisation, nominationYear ,titleName, titleReleased);
        });
        String expectedMessage = "Cannot create NOMINATED relation between Person Leonardo DiCaprio and " +
                "Award Best Performance by an Actor in a Leading Role. Award does not exist.";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void createNominationRelationWhenItExistsTest() {
        when(personService.getPersonByNameAndDateOfBirth(personName, personDOB)).thenReturn(Optional.of(new Person(personID)));
        when(awardService.getAwardByNameAndOrganisation(awardName, awardOrganisation)).thenReturn(Optional.of(new Award(awardID)));
        when(nominatedRelationRepository.getNominatedRelation(personID, awardID, nominationYear ,titleName, titleReleased)).thenReturn(Optional.of(new NominatedRelation()));
        Exception exception = assertThrows(NominatedRelationExistsException.class, () -> {
            nominatedRelationService.createNominatedRelation(personName, personDOB, awardName, awardOrganisation, nominationYear ,titleName, titleReleased);
        });
        String expectedMessage = "NOMINATED relation between person: Leonardo DiCaprio and award: Best " +
                "Performance by an Actor in a Leading Role for title The Wolf of Wall Street (2013) and year 2014 already exists.";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void createNominationRelationTest() {
        Person person = new Person(personID);
        when(personService.getPersonByNameAndDateOfBirth(personName, personDOB)).thenReturn(Optional.of(person));
        Award award = new Award(awardID);
        when(awardService.getAwardByNameAndOrganisation(awardName, awardOrganisation)).thenReturn(Optional.of(award));
        when(nominatedRelationRepository.getNominatedRelation(personID, awardID, nominationYear ,titleName, titleReleased)).thenReturn(Optional.empty());
        NominatedRelation nominatedRelation = new NominatedRelation(person, award, nominationYear ,titleName, titleReleased);
        when(nominatedRelationRepository.createNominatedRelation(personID, awardID, nominationYear ,titleName, titleReleased)).thenReturn(nominatedRelation);

        NominatedRelation nr = nominatedRelationService.createNominatedRelation(personName, personDOB, awardName, awardOrganisation, nominationYear ,titleName, titleReleased);
        assertEquals(personID, nr.getPerson().getId());
        assertEquals(awardID, nr.getAward().getId());
        assertEquals(nominationYear, nr.getYear());
        assertEquals(titleName, nr.getTitleName());
        assertEquals(titleReleased, nr.getTitleReleased());
    }
}
