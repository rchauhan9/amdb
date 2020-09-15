package com.rchauhan.amdb.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.rchauhan.amdb.model.Person;
import com.rchauhan.amdb.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

//@Component
//public class PersonQuery implements GraphQLQueryResolver {
//
//    @Autowired
//    PersonService personService;
//
//    public Optional<Person> getPerson(UUID id) { return personService.getPerson(id); }
//
//    public Optional<Person> getPersonByNameAndDateOfBirth(String name, String dateOfBirth) {
//        return personService.getPersonByNameAndDateOfBirth(name, dateOfBirth);
//    }
//
//}
