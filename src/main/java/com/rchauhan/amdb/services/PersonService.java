package com.rchauhan.amdb.services;

import com.rchauhan.amdb.exceptions.PersonExistsException;
import com.rchauhan.amdb.model.Person;
import com.rchauhan.amdb.repositories.PersonRepository;
import com.rchauhan.amdb.utils.Constants;
import com.rchauhan.amdb.utils.URLGenerator;
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

    @Autowired
    private URLGenerator urlGenerator;

    public Optional<Person> getPerson(UUID id) {
        return personRepository.findById(id);
    }

    public Optional<Person> getPersonByUrlID(String urlID) {
        return personRepository.findByUrlID(urlID);
    }

    public Optional<Person> getPersonByNameAndDateOfBirth(String name, String dateOfBirth) {
        return personRepository.getPersonByNameAndDateOfBirth(name, dateOfBirth);
    }

    public Person createPerson(String name, Date dateOfBirth) throws PersonExistsException {
        SimpleDateFormat formatter = new SimpleDateFormat(Constants.PERSON_DOB_FORMAT);
        if (personExists(name, formatter.format(dateOfBirth))) {
            throw new PersonExistsException(String.format("A person with name %s and date of birth %s already exists.", name, formatter.format(dateOfBirth)));
        }

        return personRepository.save(new Person(name, dateOfBirth, urlGenerator.createURLString()));
    }

    public boolean personExists(String name, String dateOfBirth) {
        return getPersonByNameAndDateOfBirth(name, dateOfBirth).isPresent();
    }

    public boolean personExists(UUID id) {
        return getPerson(id).isPresent();
    }
}
