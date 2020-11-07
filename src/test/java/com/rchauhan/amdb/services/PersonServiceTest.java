package com.rchauhan.amdb.services;

import com.rchauhan.amdb.exceptions.PersonExistsException;
import com.rchauhan.amdb.model.Person;
import com.rchauhan.amdb.repositories.PersonRepository;
import com.rchauhan.amdb.utils.URLGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    private String dateOfBirth = "1974-11-11";
    private String bio = "From teenage heartthrob to leading actor, Leonardo DiCaprio has...";
    private String urlID = "4bCd3F6h1Jk";

    @Test
    public void getPersonByIDTest() {
        personService.getPerson(id);
        verify(personRepository).findById(id);
    }

    @Test
    public void getPersonByNameAndDateOfBirthTest() {
        personService.getPersonByNameAndDateOfBirth(name, dateOfBirth);
        verify(personRepository).getPersonByNameAndDateOfBirth(name, dateOfBirth);
    }

    @Test
    public void createPersonWhenPersonExistsTest() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dOB = sdf.parse(dateOfBirth);

        when(personRepository.getPersonByNameAndDateOfBirth(name, "11-Nov-1974"))
                .thenReturn(Optional.of(new Person(name, dOB, bio, urlID)));

        Exception exception = assertThrows(PersonExistsException.class, () -> {
            personService.createPerson(name, dOB, bio);
        });

        String expectedMessage = "A person with name Leonardo DiCaprio and date of birth 11-Nov-1974 already exists.";
        assertEquals(expectedMessage, exception.getMessage());

    }

    @Test
    public void createPersonWhenDoesNotExist() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dOB = sdf.parse(dateOfBirth);
        Person leo = new Person(name, dOB, bio, urlID);

        when(personRepository.getPersonByNameAndDateOfBirth(name, "11-Nov-1974")).thenReturn(Optional.empty());
        when(urlGenerator.createURLString()).thenReturn(urlID);
        when(personRepository.save(leo)).thenReturn(leo);

        Person person = personService.createPerson(name, dOB, bio);
        assertEquals(name, person.getName());
        assertEquals(dOB, person.getDateOfBirth());
        assertEquals(urlID, person.getUrlID());
    }

    @Test
    public void personExistsIDNotFoundTest() {
        when(personRepository.findById(id))
                .thenReturn(Optional.empty());

        assertFalse(personService.personExists(id));
    }

    @Test
    public void personExistsIDFoundTest() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dOB = sdf.parse(dateOfBirth);

        when(personRepository.findById(id))
                .thenReturn(Optional.of(new Person(name, dOB, bio, urlID)));

        assertTrue(personService.personExists(id));
    }

    @Test
    public void personExistsNameAndDateOfBirthNotFoundTest() {
        when(personRepository.getPersonByNameAndDateOfBirth(name, dateOfBirth))
                .thenReturn(Optional.empty());

        assertFalse(personService.personExists(name, dateOfBirth));
    }

    @Test
    public void personExistsNameAndDateOfBirthFoundTest() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dOB = sdf.parse(dateOfBirth);
        when(personRepository.getPersonByNameAndDateOfBirth(name, dateOfBirth))
                .thenReturn(Optional.of(new Person(name, dOB, bio, urlID)));
        assertTrue(personService.personExists(name, dateOfBirth));
    }

}
