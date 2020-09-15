package com.rchauhan.amdb.services;

import com.rchauhan.amdb.exceptions.DirectedRelationExistsException;
import com.rchauhan.amdb.exceptions.PersonDoesNotExistException;
import com.rchauhan.amdb.exceptions.TitleDoesNotExistException;
import com.rchauhan.amdb.model.DirectedRelation;
import com.rchauhan.amdb.model.Person;
import com.rchauhan.amdb.model.Title;
import com.rchauhan.amdb.repositories.DirectedRelationRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DirectedRelationServiceTest {

    @Mock
    DirectedRelationRepository directedRelationRepository;

    @Mock
    PersonService personService;

    @Mock
    TitleService titleService;

    @InjectMocks
    private DirectedRelationService directedRelationService = new DirectedRelationService();

    private UUID personID = UUID.randomUUID();
    private UUID titleID = UUID.randomUUID();
    private String personName = "Christopher Nolan";
    private String personDOB = "30-Jan-1970";
    private String titleName = "The Dark Knight";
    private Integer titleReleased = 2008;

    @Test
    public void createDirectedRelationWhenPersonDoesNotExistTest() {
        when(personService.getPersonByNameAndDateOfBirth(personName, personDOB))
                .thenReturn(Optional.empty());
        Exception exception = assertThrows(PersonDoesNotExistException.class, () -> {
            directedRelationService.createDirectedRelation(personName, personDOB, titleName, titleReleased);
        });

        String expectedMessage = "Cannot create DIRECTED relation between Person Christopher Nolan and Title The Dark Knight. Person does not exist.";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void createDirectedRelationWhenTitleDoesNotExistTest() {
        when(personService.getPersonByNameAndDateOfBirth(personName, personDOB))
                .thenReturn(Optional.of(new Person(personID)));
        when(titleService.getTitleByNameAndReleased(titleName, titleReleased))
                .thenReturn(Optional.empty());
        Exception exception = assertThrows(TitleDoesNotExistException.class, () -> {
            directedRelationService.createDirectedRelation(personName, personDOB, titleName, titleReleased);
        });

        String expectedMessage = "Cannot create DIRECTED relation between Person Christopher Nolan and Title The Dark Knight. Title does not exist.";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void createDirectedRelationWhenItExistsTest() {
        when(personService.getPersonByNameAndDateOfBirth(personName, personDOB))
                .thenReturn(Optional.of(new Person(personID)));
        when(titleService.getTitleByNameAndReleased(titleName, titleReleased))
                .thenReturn(Optional.of(new Title(titleID)));
        when(directedRelationRepository.getDirectedRelation(personID, titleID))
                .thenReturn(Optional.of(new DirectedRelation()));
        Exception exception = assertThrows(DirectedRelationExistsException.class, () -> {
            directedRelationService.createDirectedRelation(personName, personDOB, titleName, titleReleased);
        });

        String expectedMessage = "DIRECTED relation between person: Christopher Nolan and title: The Dark Knight already exists.";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void createDirectedRelationTest() {
        Person person = new Person(personID);
        when(personService.getPersonByNameAndDateOfBirth(personName, personDOB))
                .thenReturn(Optional.of(person));
        Title title = new Title(titleID);
        when(titleService.getTitleByNameAndReleased(titleName, titleReleased))
                .thenReturn(Optional.of(title));
        when(directedRelationRepository.getDirectedRelation(personID, titleID))
                .thenReturn(Optional.empty());
        DirectedRelation directedRelation = new DirectedRelation(person, title);
        when(directedRelationRepository.createDirectedRelation(personID, titleID))
                .thenReturn(directedRelation);
        DirectedRelation dr = directedRelationService.createDirectedRelation(personName, personDOB, titleName, titleReleased);
        assertEquals(personID, dr.getPerson().getId());
        assertEquals(titleID, dr.getTitle().getId());
    }

}
