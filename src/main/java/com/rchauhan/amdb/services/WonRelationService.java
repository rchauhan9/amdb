package com.rchauhan.amdb.services;

import com.rchauhan.amdb.exceptions.AwardDoesNotExistException;
import com.rchauhan.amdb.exceptions.PersonDoesNotExistException;
import com.rchauhan.amdb.exceptions.WonRelationExistsException;
import com.rchauhan.amdb.model.Award;
import com.rchauhan.amdb.model.Person;
import com.rchauhan.amdb.model.WonRelation;
import com.rchauhan.amdb.repositories.WonRelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class WonRelationService {

    @Autowired
    WonRelationRepository wonRelationRepository;

    @Autowired
    PersonService personService;

    @Autowired
    AwardService awardService;


    public WonRelation createWonRelation(String personName, Date personDOB, String awardName, String awardOrganisation, Integer wonYear, String titleName, Integer titleReleased) {

        Optional<Person> person = personService.getPersonByNameAndDateOfBirth(personName, personDOB);
        if (person.isEmpty()) {
            throw new PersonDoesNotExistException(String.format("Cannot create WON relation between Person %s and Award %s. Person does not exist.", personName, awardName));
        }

        Optional<Award> award = awardService.getAwardByNameAndOrganisation(awardName, awardOrganisation);
        if (award.isEmpty()) {
            throw new AwardDoesNotExistException(
                    String.format("Cannot create WON relation between Person %s and Award %s. Award does not exist.",
                            personName, awardName));
        }

        if (wonRelationExists(person.get().getId(), award.get().getId(), wonYear, titleName, titleReleased)) {
            throw new WonRelationExistsException(
                    String.format("WON relation between person: %s and award: %s for title %s (%d) and year %d already exists.",
                            personName, awardName, titleName, titleReleased, wonYear));
        }

        return wonRelationRepository.createWonRelation(person.get().getId(), award.get().getId(), wonYear, titleName, titleReleased);
    }

    private boolean wonRelationExists(UUID personID, UUID awardID, Integer wonYear, String titleName, Integer titleReleased) {
        return wonRelationRepository.getWonRelation(personID, awardID, wonYear, titleName, titleReleased).isPresent();
    }
}
