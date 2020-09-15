package com.rchauhan.amdb.services;

import com.rchauhan.amdb.exceptions.PersonDoesNotExistException;
import com.rchauhan.amdb.exceptions.ProducedRelationExistsException;
import com.rchauhan.amdb.exceptions.TitleDoesNotExistException;
import com.rchauhan.amdb.model.Person;
import com.rchauhan.amdb.model.ProducedRelation;
import com.rchauhan.amdb.model.Title;
import com.rchauhan.amdb.repositories.ProducedRelationRepository;
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
public class ProducedRelationServiceTest {

    @Mock
    ProducedRelationRepository producedRelationRepository;

    @Mock
    PersonService personService;

    @Mock
    TitleService titleService;

    @InjectMocks
    private ProducedRelationService producedRelationService = new ProducedRelationService();

    private UUID personID = UUID.randomUUID();
    private UUID titleID = UUID.randomUUID();
    private String personName = "Christopher Nolan";
    private String personDOB = "30-Jan-1970";
    private String titleName = "The Dark Knight";
    private Integer titleReleased = 2008;

    @Test
    public void createProducedRelationWhenPersonDoesNotExistTest() {
        when(personService.getPersonByNameAndDateOfBirth(personName, personDOB))
                .thenReturn(Optional.empty());
        Exception exception = assertThrows(PersonDoesNotExistException.class, () -> {
            producedRelationService.createProducedRelation(personName, personDOB, titleName, titleReleased);
        });

        String expectedMessage = "Cannot create PRODUCED relation between Person Christopher Nolan and Title The Dark Knight. Person does not exist.";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void createProducedRelationWhenTitleDoesNotExistTest() {
        when(personService.getPersonByNameAndDateOfBirth(personName, personDOB))
                .thenReturn(Optional.of(new Person(personID)));
        when(titleService.getTitleByNameAndReleased(titleName, titleReleased))
                .thenReturn(Optional.empty());
        Exception exception = assertThrows(TitleDoesNotExistException.class, () -> {
            producedRelationService.createProducedRelation(personName, personDOB, titleName, titleReleased);
        });

        String expectedMessage = "Cannot create PRODUCED relation between Person Christopher Nolan and Title The Dark Knight. Title does not exist.";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void createProducedRelationWhenItExistsTest() {
        when(personService.getPersonByNameAndDateOfBirth(personName, personDOB))
                .thenReturn(Optional.of(new Person(personID)));
        when(titleService.getTitleByNameAndReleased(titleName, titleReleased))
                .thenReturn(Optional.of(new Title(titleID)));
        when(producedRelationRepository.getProducedRelation(personID, titleID))
                .thenReturn(Optional.of(new ProducedRelation()));
        Exception exception = assertThrows(ProducedRelationExistsException.class, () -> {
            producedRelationService.createProducedRelation(personName, personDOB, titleName, titleReleased);
        });

        String expectedMessage = "PRODUCED relation between person: Christopher Nolan and title: The Dark Knight already exists.";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void createProducedRelationTest() {
        Person person = new Person(personID);
        when(personService.getPersonByNameAndDateOfBirth(personName, personDOB))
                .thenReturn(Optional.of(person));
        Title title = new Title(titleID);
        when(titleService.getTitleByNameAndReleased(titleName, titleReleased))
                .thenReturn(Optional.of(title));
        when(producedRelationRepository.getProducedRelation(personID, titleID))
                .thenReturn(Optional.empty());
        ProducedRelation producedRelation = new ProducedRelation(person, title);
        when(producedRelationRepository.createProducedRelation(personID, titleID))
                .thenReturn(producedRelation);
        ProducedRelation dr = producedRelationService.createProducedRelation(personName, personDOB, titleName, titleReleased);
        assertEquals(personID, dr.getPerson().getId());
        assertEquals(titleID, dr.getTitle().getId());
    }
}
