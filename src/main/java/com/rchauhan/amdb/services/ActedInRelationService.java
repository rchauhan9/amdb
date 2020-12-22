package com.rchauhan.amdb.services;

import com.rchauhan.amdb.exceptions.ActedInRelationExistsException;
import com.rchauhan.amdb.exceptions.PersonDoesNotExistException;
import com.rchauhan.amdb.exceptions.TitleDoesNotExistException;
import com.rchauhan.amdb.model.ActedInRelation;
import com.rchauhan.amdb.model.Person;
import com.rchauhan.amdb.model.Title;
import com.rchauhan.amdb.repositories.ActedInRelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ActedInRelationService {

    @Autowired
    ActedInRelationRepository actedInRelationRepository;

    @Autowired
    PersonService personService;

    @Autowired
    TitleService titleService;

    public List<ActedInRelation> getTitlesStarring(String name) {
        return actedInRelationRepository.getMoviesStarring(name);
    }

    public ActedInRelation createActedInRelation(String personName, Date personDOB, String titleName, Integer titleReleased, List<String> characters, Integer billing) {
        Optional<Person> person = personService.getPersonByNameAndDateOfBirth(personName, personDOB);
        if (person.isEmpty()) {
            throw new PersonDoesNotExistException(String.format("Cannot create ACTED_IN relation between Person %s and Title %s. Person does not exist.", personName, titleName));
        }

        Optional<Title> title = titleService.getTitleByNameAndReleased(titleName, titleReleased);
        if (title.isEmpty()) {
            throw new TitleDoesNotExistException(String.format("Cannot create ACTED_IN relation between Person %s and Title %s. Title does not exist.", personName, titleName));
        }

        if (actedInRelationExists(person.get().getId(), title.get().getId())) {
            throw new ActedInRelationExistsException(String.format("ACTED_IN relation between person: %s and title: %s already exists.", personName, titleName));
        }

        return actedInRelationRepository.createActedInRelation(person.get().getId(), title.get().getId(), characters, billing);
    }

    private boolean actedInRelationExists(UUID personID, UUID titleID) {
        return actedInRelationRepository.getActedInRelation(personID, titleID).isPresent();
    }

}
