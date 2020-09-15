package com.rchauhan.amdb.services;

import com.rchauhan.amdb.exceptions.AwardDoesNotExistException;
import com.rchauhan.amdb.exceptions.NominatedRelationExistsException;
import com.rchauhan.amdb.exceptions.PersonDoesNotExistException;
import com.rchauhan.amdb.model.Award;
import com.rchauhan.amdb.model.NominatedRelation;
import com.rchauhan.amdb.model.Person;
import com.rchauhan.amdb.repositories.NominatedRelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class NominatedRelationService {

    @Autowired
    NominatedRelationRepository nominatedRelationRepository;

    @Autowired
    PersonService personService;

    @Autowired
    AwardService awardService;

    public NominatedRelation createNominatedRelation(String personName, String personDOB, String awardName, String titleID, Integer nominationYear) {
        Optional<Person> person = personService.getPersonByNameAndDateOfBirth(personName, personDOB);
        if (person.isEmpty()) {
            throw new PersonDoesNotExistException(String.format("Cannot create NOMINATED relation between Person %s and Award %s. Person does not exist.", personName, awardName));
        }

        Optional<Award> award = awardService.getAwardByName(awardName);
        if (award.isEmpty()) {
            throw new AwardDoesNotExistException(
                    String.format("Cannot create NOMINATED relation between Person %s and Award %s. Award does not exist.",
                            personName, awardName));
        }

        if (nominatedRelationExists(person.get().getId(), award.get().getId(), titleID, nominationYear)) {
            throw new NominatedRelationExistsException(
                    String.format("NOMINATED relation between person: %s and award: %s for title %s and year %d already exists.",
                            personName, awardName, titleID, nominationYear));
        }

        return nominatedRelationRepository.createNominatedRelation(person.get().getId(), award.get().getId(), titleID, nominationYear);
    }

    private boolean nominatedRelationExists(UUID personID, UUID awardID, String titleID, Integer nominationYear) {
        return nominatedRelationRepository.getNominatedRelation(personID, awardID, titleID, nominationYear).isPresent();
    }
}
