package com.rchauhan.amdb.services;

import com.rchauhan.amdb.exceptions.ActedInRelationExistsException;
import com.rchauhan.amdb.exceptions.PersonDoesNotExistException;
import com.rchauhan.amdb.exceptions.TitleDoesNotExistException;
import com.rchauhan.amdb.model.ActedInRelation;
import com.rchauhan.amdb.model.Person;
import com.rchauhan.amdb.model.Title;
import com.rchauhan.amdb.repositories.ActedInRelationRepository;
import com.rchauhan.amdb.utils.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ActedInRelationServiceTest {

    @Mock
    ActedInRelationRepository actedInRelationRepository;

    @Mock
    PersonService personService;

    @Mock
    TitleService titleService;

    @InjectMocks
    private ActedInRelationService actedInRelationService = new ActedInRelationService();

    private UUID personID = UUID.randomUUID();
    private UUID titleID = UUID.randomUUID();
    private String personName = "Christian Bale";
    private Date personDOB = DateUtil.createDate("dd-MMM-yyyy", "01-Jan-1974");
    private String titleName = "The Dark Knight";
    private Integer titleReleased = 2008;
    private List<String> characters = Arrays.asList("Bruce Wayne", "Batman");
    private Integer billing = 0;

    @Test
    public void createActedInRelationWhenPersonDoesNotExistTest() {
        when(personService.getPersonByNameAndDateOfBirth(personName, personDOB)).thenReturn(Optional.empty());
        Exception exception = assertThrows(PersonDoesNotExistException.class, () -> {
            actedInRelationService.createActedInRelation(personName, personDOB, titleName, titleReleased, characters, billing);
        });
        String expectedMessage = "Cannot create ACTED_IN relation between Person Christian Bale and Title The Dark Knight. Person does not exist.";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void createActedInRelationWhenTitleDoesNotExistTest() {
        when(personService.getPersonByNameAndDateOfBirth(personName, personDOB)).thenReturn(Optional.of(new Person()));
        when(titleService.getTitleByNameAndReleased(titleName, titleReleased)).thenReturn(Optional.empty());
        Exception exception = assertThrows(TitleDoesNotExistException.class, () -> {
            actedInRelationService.createActedInRelation(personName, personDOB, titleName, titleReleased, characters, billing);
        });
        String expectedMessage = "Cannot create ACTED_IN relation between Person Christian Bale and Title The Dark Knight. Title does not exist.";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void createActedInRelationWhenItExistsTest() {
        Person person = new Person(personID);
        when(personService.getPersonByNameAndDateOfBirth(personName, personDOB)).thenReturn(Optional.of(person));
        Title title = new Title(titleID);
        when(titleService.getTitleByNameAndReleased(titleName, titleReleased)).thenReturn(Optional.of(title));
        when(actedInRelationRepository.getActedInRelation(personID, titleID)).thenReturn(Optional.of(new ActedInRelation()));
        Exception exception = assertThrows(ActedInRelationExistsException.class, () -> {
            actedInRelationService.createActedInRelation(personName, personDOB, titleName, titleReleased, characters, billing);
        });
        String expectedMessage = "ACTED_IN relation between person: Christian Bale and title: The Dark Knight already exists.";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void createActedInRelationTest() {
        Person person = new Person(personID);
        when(personService.getPersonByNameAndDateOfBirth(personName, personDOB)).thenReturn(Optional.of(person));
        Title title = new Title(titleID);
        when(titleService.getTitleByNameAndReleased(titleName, titleReleased)).thenReturn(Optional.of(title));
        when(actedInRelationRepository.getActedInRelation(personID, titleID)).thenReturn(Optional.empty());

        ActedInRelation actedInRelation = new ActedInRelation(characters, billing, person, title);
        when(actedInRelationRepository.createActedInRelation(personID, titleID, characters, billing))
                .thenReturn(actedInRelation);

        ActedInRelation relation = actedInRelationService.createActedInRelation(personName, personDOB, titleName, titleReleased, characters, billing);
        assertEquals(characters, relation.getCharacters());
        assertEquals(billing, relation.getBilling());
        assertEquals(personID, relation.getPerson().getId());
        assertEquals(titleID, relation.getTitle().getId());
    }

}
