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

import java.util.Date;
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

    public NominatedRelation createNominatedRelation(String personName, Date personDOB, String awardName, String awardOrganisation, Integer nominationYear, String titleName, Integer titleReleased) {
        Optional<Person> person = personService.getPersonByNameAndDateOfBirth(personName, personDOB);
        if (person.isEmpty()) {
            throw new PersonDoesNotExistException(String.format("Cannot create NOMINATED relation between Person %s and Award %s. Person does not exist.", personName, awardName));
        }

        Optional<Award> award = awardService.getAwardByNameAndOrganisation(awardName, awardOrganisation);
        if (award.isEmpty()) {
            throw new AwardDoesNotExistException(
                    String.format("Cannot create NOMINATED relation between Person %s and Award %s. Award does not exist.",
                            personName, awardName));
        }

        if (nominatedRelationExists(person.get().getId(), award.get().getId(), nominationYear, titleName, titleReleased)) {
            throw new NominatedRelationExistsException(
                    String.format("NOMINATED relation between person: %s and award: %s for title %s (%d) and year %d already exists.",
                            personName, awardName,titleName, titleReleased, nominationYear));
        }

        return nominatedRelationRepository.createNominatedRelation(person.get().getId(), award.get().getId(), nominationYear, titleName, titleReleased);
    }

    private boolean nominatedRelationExists(UUID personID, UUID awardID, Integer nominationYear, String titleName, Integer titleReleased) {
        return nominatedRelationRepository.getNominatedRelation(personID, awardID, nominationYear, titleName, titleReleased).isPresent();
    }
}
