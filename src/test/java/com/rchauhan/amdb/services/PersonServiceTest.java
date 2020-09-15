package com.rchauhan.amdb.services;

import com.rchauhan.amdb.exceptions.PersonExistsException;
import com.rchauhan.amdb.model.Person;
import com.rchauhan.amdb.repositories.PersonRepository;
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
    PersonRepository personRepository;

    @InjectMocks
    private PersonService personService = new PersonService();

    @Test
    public void getPersonByIDTest() {
        UUID id = UUID.randomUUID();
        personService.getPerson(id);
        verify(personRepository).findById(id);
    }

    @Test
    public void getPersonByNameAndDateOfBirthTest() {
        String name = "Leonardo DiCaprio";
        String dateOfBirth = "1974-11-11";
        personService.getPersonByNameAndDateOfBirth(name, dateOfBirth);
        verify(personRepository).getPersonByNameAndDateOfBirth(name, dateOfBirth);
    }

    @Test
    public void createPersonWhenPersonExistsTest() throws ParseException {
        String name = "Leonardo DiCaprio";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateOfBirth = sdf.parse("1974-11-11");

        when(personRepository.getPersonByNameAndDateOfBirth(name, "11-Nov-1974"))
                .thenReturn(Optional.of(new Person("Leonardo DiCaprio", dateOfBirth)));

        Exception exception = assertThrows(PersonExistsException.class, () -> {
            personService.createPerson(name, dateOfBirth);
        });

        String expectedMessage = "A person with name Leonardo DiCaprio and date of birth 11-Nov-1974 already exists.";
        assertEquals(expectedMessage, exception.getMessage());

    }

    @Test
    public void createPersonWhenDoesNotExist() throws ParseException {
        String name = "Leonardo DiCaprio";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateOfBirth = sdf.parse("1974-11-11");
        when(personRepository.getPersonByNameAndDateOfBirth(name, "11-Nov-1974"))
                .thenReturn(Optional.empty());
        Person leo = new Person(name, dateOfBirth);

        when(personRepository.save(leo)).thenReturn(leo);

        Person person = personService.createPerson(name, dateOfBirth);
        assertEquals(name, person.getName());
        assertEquals(dateOfBirth, person.getDateOfBirth());
    }

    @Test
    public void personExistsIDNotFoundTest() {
        UUID id = UUID.randomUUID();
        when(personRepository.findById(id))
                .thenReturn(Optional.empty());

        assertFalse(personService.personExists(id));
    }

    @Test
    public void personExistsIDFoundTest() throws ParseException {
        UUID id = UUID.randomUUID();
        String name = "Joe Bloggs";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateOfBirth = sdf.parse("2000-01-01");
        when(personRepository.findById(id))
                .thenReturn(Optional.of(new Person(name, dateOfBirth)));

        assertTrue(personService.personExists(id));
    }

    @Test
    public void personExistsNameAndDateOfBirthNotFoundTest() {
        String name = "Joe Bloggs";
        String dOB = "01-Jan-2000";
        when(personRepository.getPersonByNameAndDateOfBirth(name, dOB))
                .thenReturn(Optional.empty());

        assertFalse(personService.personExists(name, dOB));
    }

    @Test
    public void personExistsNameAndDateOfBirthFoundTest() throws ParseException {
        String name = "Joe Bloggs";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateOfBirth = sdf.parse("2000-01-01");
        String dOB = "01-Jan-2000";
        when(personRepository.getPersonByNameAndDateOfBirth(name, dOB))
                .thenReturn(Optional.of(new Person(name, dateOfBirth)));

        assertTrue(personService.personExists(name, dOB));
    }

}
