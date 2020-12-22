package com.rchauhan.amdb.services;

import com.rchauhan.amdb.exceptions.PersonExistsException;
import com.rchauhan.amdb.model.Person;
import com.rchauhan.amdb.repositories.PersonRepository;
import com.rchauhan.amdb.utils.DateUtil;
import com.rchauhan.amdb.utils.URLGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.ParseException;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PersonServiceTest {

    @Mock
    URLGenerator urlGenerator;

    @Mock
    PersonRepository personRepository;

    @InjectMocks
    private PersonService personService = new PersonService();

    private UUID id = UUID.randomUUID();
    private String name = "Leonardo DiCaprio";
    private Date dateOfBirth = DateUtil.createDate("dd-MMM-yyyy", "11-Nov-1974");
    private String bio = "From teenage heartthrob to leading actor, Leonardo DiCaprio has...";
    private String urlID = "4bCd3F6h1Jk";

    @Test
    public void getPersonByIDTest() {
        personService.getPerson(id);
        verify(personRepository).findById(id);
    }

    @Test
    public void getPersonByUrlIDTest() {
        personService.getPersonByUrlID(urlID);
        verify(personRepository).findByUrlID(urlID);
    }

    @Test
    public void getPersonByNameAndDateOfBirthTest() {
        personService.getPersonByNameAndDateOfBirth(name, dateOfBirth);
        verify(personRepository).findPersonByNameAndDateOfBirth(name, dateOfBirth);
    }

    @Test
    public void createPersonWhenPersonExistsTest() throws ParseException {
        when(personRepository.findPersonByNameAndDateOfBirth(name, dateOfBirth))
                .thenReturn(Optional.of(new Person(name, dateOfBirth, bio, urlID)));

        Exception exception = assertThrows(PersonExistsException.class, () -> {
            personService.createPerson(name, dateOfBirth, bio);
        });

        String expectedMessage = "A person with name Leonardo DiCaprio and date of birth Mon Nov 11 00:00:00 GMT 1974 already exists.";
        assertEquals(expectedMessage, exception.getMessage());

    }

    @Test
    public void createPersonWhenDoesNotExist() {
        Person leo = new Person(name, dateOfBirth, bio, urlID);

        when(personRepository.findPersonByNameAndDateOfBirth(name, dateOfBirth)).thenReturn(Optional.empty());
        when(urlGenerator.createURLString()).thenReturn(urlID);
        when(personRepository.save(leo)).thenReturn(leo);

        Person person = personService.createPerson(name, dateOfBirth, bio);
        assertEquals(name, person.getName());
        assertEquals(dateOfBirth, person.getDateOfBirth());
        assertEquals(urlID, person.getUrlID());
    }

    @Test
    public void personExistsIDNotFoundTest() {
        when(personRepository.findById(id))
                .thenReturn(Optional.empty());

        assertFalse(personService.personExists(id));
    }

    @Test
    public void personExistsIDFoundTest() {
        when(personRepository.findById(id))
                .thenReturn(Optional.of(new Person(name, dateOfBirth, bio, urlID)));

        assertTrue(personService.personExists(id));
    }

    @Test
    public void personExistsNameAndDateOfBirthNotFoundTest() {
        when(personRepository.findPersonByNameAndDateOfBirth(name, dateOfBirth))
                .thenReturn(Optional.empty());

        assertFalse(personService.personExists(name, dateOfBirth));
    }

    @Test
    public void personExistsNameAndDateOfBirthFoundTest() {
        when(personRepository.findPersonByNameAndDateOfBirth(name, dateOfBirth))
                .thenReturn(Optional.of(new Person(name, dateOfBirth, bio, urlID)));
        assertTrue(personService.personExists(name, dateOfBirth));
    }

}
