package com.rchauhan.amdb.services;

import com.rchauhan.amdb.exceptions.AwardDoesNotExistException;
import com.rchauhan.amdb.exceptions.PersonDoesNotExistException;
import com.rchauhan.amdb.exceptions.WonRelationExistsException;
import com.rchauhan.amdb.model.Award;
import com.rchauhan.amdb.model.Person;
import com.rchauhan.amdb.model.WonRelation;
import com.rchauhan.amdb.repositories.WonRelationRepository;
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
public class WonRelationServiceTest {

    @Mock
    WonRelationRepository wonRelationRepository;

    @Mock
    PersonService personService;

    @Mock
    AwardService awardService;

    @InjectMocks
    private WonRelationService wonRelationService = new WonRelationService();

    private UUID personID = UUID.randomUUID();
    private UUID awardID = UUID.randomUUID();
    private String personName = "Leonardo DiCaprio";
    private Date personDOB = DateUtil.createDate("dd-MMM-yyyy", "11-Nov-1974");
    private String awardName = "Best Performance by an Actor in a Leading Role";
    private String awardOrganisation = "Academy Awards";
    private Integer wonYear = 2016;
    private String titleName = "The Revenant";
    private Integer titleReleased = 2015;

    @Test
    public void createWonRelationWhenPersonDoesNotExistTest() {
        when(personService.getPersonByNameAndDateOfBirth(personName, personDOB)).thenReturn(Optional.empty());
        Exception exception = assertThrows(PersonDoesNotExistException.class, () -> {
            wonRelationService.createWonRelation(personName, personDOB, awardName, awardOrganisation, wonYear, titleName, titleReleased);
        });
        String expectedMessage = "Cannot create WON relation between Person Leonardo DiCaprio and " +
                "Award Best Performance by an Actor in a Leading Role. Person does not exist.";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void createWonRelationWhenAwardDoesNotExistTest() {
        when(personService.getPersonByNameAndDateOfBirth(personName, personDOB)).thenReturn(Optional.of(new Person(personID)));
        when(awardService.getAwardByNameAndOrganisation(awardName, awardOrganisation)).thenReturn(Optional.empty());
        Exception exception = assertThrows(AwardDoesNotExistException.class, () -> {
            wonRelationService.createWonRelation(personName, personDOB, awardName, awardOrganisation, wonYear, titleName, titleReleased);
        });
        String expectedMessage = "Cannot create WON relation between Person Leonardo DiCaprio and " +
                "Award Best Performance by an Actor in a Leading Role. Award does not exist.";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void createWonRelationWhenItExistsTest() {
        when(personService.getPersonByNameAndDateOfBirth(personName, personDOB)).thenReturn(Optional.of(new Person(personID)));
        when(awardService.getAwardByNameAndOrganisation(awardName, awardOrganisation)).thenReturn(Optional.of(new Award(awardID)));
        when(wonRelationRepository.getWonRelation(personID, awardID, wonYear, titleName, titleReleased)).thenReturn(Optional.of(new WonRelation()));
        Exception exception = assertThrows(WonRelationExistsException.class, () -> {
            wonRelationService.createWonRelation(personName, personDOB, awardName, awardOrganisation, wonYear, titleName, titleReleased);
        });
        String expectedMessage = "WON relation between person: Leonardo DiCaprio and award: Best " +
                "Performance by an Actor in a Leading Role for title The Revenant (2015) and year 2016 already exists.";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void createWonRelationTest() {
        Person person = new Person(personID);
        when(personService.getPersonByNameAndDateOfBirth(personName, personDOB)).thenReturn(Optional.of(person));
        Award award = new Award(awardID);
        when(awardService.getAwardByNameAndOrganisation(awardName, awardOrganisation)).thenReturn(Optional.of(award));
        when(wonRelationRepository.getWonRelation(personID, awardID, wonYear, titleName, titleReleased)).thenReturn(Optional.empty());
        WonRelation wonRelation = new WonRelation(person, award, wonYear, titleName, titleReleased);
        when(wonRelationRepository.createWonRelation(personID, awardID, wonYear, titleName, titleReleased)).thenReturn(wonRelation);

        WonRelation wr = wonRelationService.createWonRelation(personName, personDOB, awardName, awardOrganisation, wonYear, titleName, titleReleased);
        assertEquals(personID, wr.getPerson().getId());
        assertEquals(awardID, wr.getAward().getId());
        assertEquals(wonYear, wr.getYear());
        assertEquals(titleName, wr.getTitleName());
        assertEquals(titleReleased, wr.getTitleReleased());
    }
}
