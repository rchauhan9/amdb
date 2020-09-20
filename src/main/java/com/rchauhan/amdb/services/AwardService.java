package com.rchauhan.amdb.services;

import com.rchauhan.amdb.exceptions.AwardExistsException;
import com.rchauhan.amdb.model.Award;
import com.rchauhan.amdb.repositories.AwardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@Service
public class AwardService {

    @Autowired
    AwardRepository awardRepository;

    public Optional<Award> getAward(UUID id) {
        return awardRepository.findById(id);
    }

    public Optional<Award> getAwardByNameAndOrganisation(String name, String organisation) {
        return awardRepository.findByNameAndOrganisation(name, organisation);
    }

    public Award createAward(String name, String organisation) {
        if (awardExists(name, organisation)) {
            throw new AwardExistsException(String.format("Award with name %s from organisation %s already exists.", name, organisation));
        }
        Award award = new Award(name, organisation);
        return awardRepository.save(award);
    }

    private boolean awardExists(String name, String organisation) {
        return awardRepository.findByNameAndOrganisation(name, organisation).isPresent();
    }
}
