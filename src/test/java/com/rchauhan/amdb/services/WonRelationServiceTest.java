package com.rchauhan.amdb.services;

import com.rchauhan.amdb.exceptions.AwardDoesNotExistException;
import com.rchauhan.amdb.exceptions.PersonDoesNotExistException;
import com.rchauhan.amdb.exceptions.WonRelationExistsException;
import com.rchauhan.amdb.model.Award;
import com.rchauhan.amdb.model.Person;
import com.rchauhan.amdb.model.WonRelation;
import com.rchauhan.amdb.repositories.WonRelationRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

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
    private String personDOB = "11-Nov-1974";
    private String awardName = "Best Performance by an Actor in a Leading Role";
    private Integer wonYear = 2016;
    private String titleID = UUID.randomUUID().toString();

    @Test
    public void createWonRelationWhenPersonDoesNotExistTest() {
        when(personService.getPersonByNameAndDateOfBirth(personName, personDOB)).thenReturn(Optional.empty());
        Exception exception = assertThrows(PersonDoesNotExistException.class, () -> {
            wonRelationService.createWonRelation(personName, personDOB, awardName, titleID, wonYear);
        });
        String expectedMessage = "Cannot create WON relation between Person Leonardo DiCaprio and " +
                "Award Best Performance by an Actor in a Leading Role. Person does not exist.";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void createWonRelationWhenAwardDoesNotExistTest() {
        when(personService.getPersonByNameAndDateOfBirth(personName, personDOB)).thenReturn(Optional.of(new Person(personID)));
        when(awardService.getAwardByName(awardName)).thenReturn(Optional.empty());
        Exception exception = assertThrows(AwardDoesNotExistException.class, () -> {
            wonRelationService.createWonRelation(personName, personDOB, awardName, titleID, wonYear);
        });
        String expectedMessage = "Cannot create WON relation between Person Leonardo DiCaprio and " +
                "Award Best Performance by an Actor in a Leading Role. Award does not exist.";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void createWonRelationWhenItExistsTest() {
        when(personService.getPersonByNameAndDateOfBirth(personName, personDOB)).thenReturn(Optional.of(new Person(personID)));
        when(awardService.getAwardByName(awardName)).thenReturn(Optional.of(new Award(awardID)));
        when(wonRelationRepository.getWonRelation(personID, awardID, titleID, wonYear)).thenReturn(Optional.of(new WonRelation()));
        Exception exception = assertThrows(WonRelationExistsException.class, () -> {
            wonRelationService.createWonRelation(personName, personDOB, awardName, titleID, wonYear);
        });
        String expectedMessage = "WON relation between person: Leonardo DiCaprio and award: Best " +
                "Performance by an Actor in a Leading Role for title " + titleID + " and year 2016 already exists.";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void createWonRelationTest() {
        Person person = new Person(personID);
        when(personService.getPersonByNameAndDateOfBirth(personName, personDOB)).thenReturn(Optional.of(person));
        Award award = new Award(awardID);
        when(awardService.getAwardByName(awardName)).thenReturn(Optional.of(award));
        when(wonRelationRepository.getWonRelation(personID, awardID, titleID, wonYear)).thenReturn(Optional.empty());
        WonRelation wonRelation = new WonRelation(wonYear, titleID, person, award);
        when(wonRelationRepository.createWonRelation(personID, awardID, titleID, wonYear)).thenReturn(wonRelation);

        WonRelation wr = wonRelationService.createWonRelation(personName, personDOB, awardName, titleID, wonYear);
        assertEquals(personID, wr.getPerson().getId());
        assertEquals(awardID, wr.getAward().getId());
        assertEquals(titleID, wr.getTitleID());
        assertEquals(wonYear, wr.getYear());
    }
}
