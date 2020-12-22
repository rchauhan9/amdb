package com.rchauhan.amdb.services;

import com.rchauhan.amdb.exceptions.PersonExistsException;
import com.rchauhan.amdb.model.Person;
import com.rchauhan.amdb.repositories.PersonRepository;
import com.rchauhan.amdb.utils.URLGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class PersonService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonService.class);

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

    public Optional<Person> getPersonByNameAndDateOfBirth(String name, Date dateOfBirth) {
        LOGGER.info("Getting person by name: {} and dOB: {}", name, dateOfBirth);
        return personRepository.findPersonByNameAndDateOfBirth(name, dateOfBirth);
    }

    public Person createPerson(String name, Date dateOfBirth, String bio) throws PersonExistsException {
        if (personExists(name, dateOfBirth)) {
            throw new PersonExistsException(String.format("A person with name %s and date of birth %s already exists.", name, dateOfBirth));
        }

        return personRepository.save(new Person(name, dateOfBirth, bio, urlGenerator.createURLString()));
    }

    public boolean personExists(String name, Date dateOfBirth) {
        return getPersonByNameAndDateOfBirth(name, dateOfBirth).isPresent();
    }

    public boolean personExists(UUID id) {
        return getPerson(id).isPresent();
    }
}
