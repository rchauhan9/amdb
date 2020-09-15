package com.rchauhan.amdb.services;

import com.rchauhan.amdb.constants.Neo4jConstants;
import com.rchauhan.amdb.exceptions.PersonExistsException;
import com.rchauhan.amdb.model.Person;
import com.rchauhan.amdb.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;


    public Optional<Person> getPerson(UUID id) {
        return personRepository.findById(id);
    }

    public Optional<Person> getPersonByNameAndDateOfBirth(String name, String dateOfBirth) {
        return personRepository.getPersonByNameAndDateOfBirth(name, dateOfBirth);
    }

    public Person createPerson(String name, Date dateOfBirth) throws PersonExistsException {
        SimpleDateFormat formatter = new SimpleDateFormat(Neo4jConstants.PERSON_DOB_FORMAT);
        if (personExists(name, formatter.format(dateOfBirth))) {
            throw new PersonExistsException(String.format("A person with name %s and date of birth %s already exists.", name, formatter.format(dateOfBirth)));
        }

        return personRepository.save(new Person(name, dateOfBirth));
    }

    public boolean personExists(String name, String dateOfBirth) {
        return getPersonByNameAndDateOfBirth(name, dateOfBirth).isPresent();
    }

    public boolean personExists(UUID id) {
        return getPerson(id).isPresent();
    }
}
