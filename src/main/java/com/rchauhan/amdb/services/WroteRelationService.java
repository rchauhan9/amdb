package com.rchauhan.amdb.services;

import com.rchauhan.amdb.exceptions.PersonDoesNotExistException;
import com.rchauhan.amdb.exceptions.TitleDoesNotExistException;
import com.rchauhan.amdb.exceptions.WroteRelationExistsException;
import com.rchauhan.amdb.model.Person;
import com.rchauhan.amdb.model.Title;
import com.rchauhan.amdb.model.WroteRelation;
import com.rchauhan.amdb.repositories.WroteRelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class WroteRelationService {

    @Autowired
    WroteRelationRepository wroteRelationRepository;

    @Autowired
    PersonService personService;

    @Autowired
    TitleService titleService;


    public WroteRelation createWroteRelation(String personName, String personDOB, String titleName, Integer titleReleased, List<String> items) {

        Optional<Person> person = personService.getPersonByNameAndDateOfBirth(personName, personDOB);
        if (person.isEmpty()) {
            throw new PersonDoesNotExistException(String.format("Cannot create WROTE relation between Person %s and Title %s. Person does not exist.", personName, titleName));
        }

        Optional<Title> title = titleService.getTitleByNameAndReleased(titleName, titleReleased);
        if (title.isEmpty()) {
            throw new TitleDoesNotExistException(String.format("Cannot create WROTE relation between Person %s and Title %s. Title does not exist.", personName, titleName));
        }

        if (wroteRelationExists(person.get().getId(), title.get().getId())) {
            throw new WroteRelationExistsException(String.format("WROTE relation between person: %s and title: %s already exists.", personName, titleName));
        }

        return wroteRelationRepository.createWroteRelation(person.get().getId(), title.get().getId(), items);
    }

    private boolean wroteRelationExists(UUID personID, UUID titleID) {
        return wroteRelationRepository.getWroteRelation(personID, titleID).isPresent();
    }
}
