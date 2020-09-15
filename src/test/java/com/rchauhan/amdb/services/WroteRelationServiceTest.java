package com.rchauhan.amdb.services;

import com.rchauhan.amdb.exceptions.*;
import com.rchauhan.amdb.model.DirectedRelation;
import com.rchauhan.amdb.model.Person;
import com.rchauhan.amdb.model.Title;
import com.rchauhan.amdb.model.WroteRelation;
import com.rchauhan.amdb.repositories.WroteRelationRepository;
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
public class WroteRelationServiceTest {

    @Mock
    WroteRelationRepository wroteRelationRepository;

    @Mock
    PersonService personService;

    @Mock
    TitleService titleService;

    @InjectMocks
    private WroteRelationService wroteRelationService = new WroteRelationService();

    private UUID personID = UUID.randomUUID();
    private UUID titleID = UUID.randomUUID();
    private String personName = "Christopher Nolan";
    private String personDOB = "30-Jan-1970";
    private String titleName = "The Dark Knight";
    private Integer titleReleased = 2008;
    private List<String> items = Arrays.asList("Screenplay", "Story");

    @Test
    public void createWroteRelationWhenPersonDoesNotExistTest() {
        when(personService.getPersonByNameAndDateOfBirth(personName, personDOB))
                .thenReturn(Optional.empty());
        Exception exception = assertThrows(PersonDoesNotExistException.class, () -> {
            wroteRelationService.createWroteRelation(personName, personDOB, titleName, titleReleased, items);
        });

        String expectedMessage = "Cannot create WROTE relation between Person Christopher Nolan and Title The Dark Knight. Person does not exist.";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void createWroteRelationWhenTitleDoesNotExistTest() {
        when(personService.getPersonByNameAndDateOfBirth(personName, personDOB))
                .thenReturn(Optional.of(new Person(personID)));
        when(titleService.getTitleByNameAndReleased(titleName, titleReleased))
                .thenReturn(Optional.empty());
        Exception exception = assertThrows(TitleDoesNotExistException.class, () -> {
            wroteRelationService.createWroteRelation(personName, personDOB, titleName, titleReleased, items);
        });

        String expectedMessage = "Cannot create WROTE relation between Person Christopher Nolan and Title The Dark Knight. Title does not exist.";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void createWroteRelationWhenItExistsTest() {
        when(personService.getPersonByNameAndDateOfBirth(personName, personDOB))
                .thenReturn(Optional.of(new Person(personID)));
        when(titleService.getTitleByNameAndReleased(titleName, titleReleased))
                .thenReturn(Optional.of(new Title(titleID)));
        when(wroteRelationRepository.getWroteRelation(personID, titleID))
                .thenReturn(Optional.of(new WroteRelation()));
        Exception exception = assertThrows(WroteRelationExistsException.class, () -> {
            wroteRelationService.createWroteRelation(personName, personDOB, titleName, titleReleased, items);
        });

        String expectedMessage = "WROTE relation between person: Christopher Nolan and title: The Dark Knight already exists.";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void createWroteRelationTest() {
        Person person = new Person(personID);
        when(personService.getPersonByNameAndDateOfBirth(personName, personDOB))
                .thenReturn(Optional.of(person));
        Title title = new Title(titleID);
        when(titleService.getTitleByNameAndReleased(titleName, titleReleased))
                .thenReturn(Optional.of(title));
        when(wroteRelationRepository.getWroteRelation(personID, titleID))
                .thenReturn(Optional.empty());
        WroteRelation wroteRelation = new WroteRelation(items, person, title);
        when(wroteRelationRepository.createWroteRelation(personID, titleID, items))
                .thenReturn(wroteRelation);
        WroteRelation wr = wroteRelationService.createWroteRelation(personName, personDOB, titleName, titleReleased, items);
        assertEquals(personID, wr.getPerson().getId());
        assertEquals(titleID, wr.getTitle().getId());
        assertEquals(items, wr.getItems());
    }

}
