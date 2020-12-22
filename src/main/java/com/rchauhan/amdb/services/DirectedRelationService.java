package com.rchauhan.amdb.services;

import com.rchauhan.amdb.exceptions.DirectedRelationExistsException;
import com.rchauhan.amdb.exceptions.PersonDoesNotExistException;
import com.rchauhan.amdb.exceptions.TitleDoesNotExistException;
import com.rchauhan.amdb.model.DirectedRelation;
import com.rchauhan.amdb.model.Person;
import com.rchauhan.amdb.model.Title;
import com.rchauhan.amdb.repositories.DirectedRelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class DirectedRelationService {

    @Autowired
    DirectedRelationRepository directedRelationRepository;

    @Autowired
    PersonService personService;

    @Autowired
    TitleService titleService;

    public DirectedRelation createDirectedRelation(String personName, Date personDOB, String titleName, Integer titleReleased) {

        Optional<Person> person = personService.getPersonByNameAndDateOfBirth(personName, personDOB);
        if (person.isEmpty()) {
            throw new PersonDoesNotExistException(String.format("Cannot create DIRECTED relation between Person %s and Title %s. Person does not exist.", personName, titleName));
        }

        Optional<Title> title = titleService.getTitleByNameAndReleased(titleName, titleReleased);
        if (title.isEmpty()) {
            throw new TitleDoesNotExistException(String.format("Cannot create DIRECTED relation between Person %s and Title %s. Title does not exist.", personName, titleName));
        }

        if (directedRelationExists(person.get().getId(), title.get().getId())) {
            throw new DirectedRelationExistsException(String.format("DIRECTED relation between person: %s and title: %s already exists.", personName, titleName));
        }

        return directedRelationRepository.createDirectedRelation(person.get().getId(), title.get().getId());
    }

    private boolean directedRelationExists(UUID personID, UUID titleID) {
        return directedRelationRepository.getDirectedRelation(personID, titleID).isPresent();
    }
}
