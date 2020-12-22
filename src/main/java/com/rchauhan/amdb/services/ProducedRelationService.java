package com.rchauhan.amdb.services;

import com.rchauhan.amdb.exceptions.PersonDoesNotExistException;
import com.rchauhan.amdb.exceptions.ProducedRelationExistsException;
import com.rchauhan.amdb.exceptions.TitleDoesNotExistException;
import com.rchauhan.amdb.model.Person;
import com.rchauhan.amdb.model.ProducedRelation;
import com.rchauhan.amdb.model.Title;
import com.rchauhan.amdb.repositories.ProducedRelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProducedRelationService {

    @Autowired
    ProducedRelationRepository producedRelationRepository;

    @Autowired
    PersonService personService;

    @Autowired
    TitleService titleService;

    public ProducedRelation createProducedRelation(String personName, Date personDOB, String titleName, Integer titleReleased, List<String> items) {

        Optional<Person> person = personService.getPersonByNameAndDateOfBirth(personName, personDOB);
        if (person.isEmpty()) {
            throw new PersonDoesNotExistException(String.format("Cannot create PRODUCED relation between Person %s and Title %s. Person does not exist.", personName, titleName));
        }

        Optional<Title> title = titleService.getTitleByNameAndReleased(titleName, titleReleased);
        if (title.isEmpty()) {
            throw new TitleDoesNotExistException(String.format("Cannot create PRODUCED relation between Person %s and Title %s. Title does not exist.", personName, titleName));
        }

        if (producedRelationExists(person.get().getId(), title.get().getId())) {
            throw new ProducedRelationExistsException(String.format("PRODUCED relation between person: %s and title: %s already exists.", personName, titleName));
        }

        return producedRelationRepository.createProducedRelation(person.get().getId(), title.get().getId(), items);
    }

    private boolean producedRelationExists(UUID personID, UUID titleID) {
        return producedRelationRepository.getProducedRelation(personID, titleID).isPresent();
    }

}
