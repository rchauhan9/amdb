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

    public Optional<Award> getAwardByName(String name) {
        return awardRepository.findByName(name);
    }

    public Award createAward(String name) {
        if (awardExists(name)) {
            throw new AwardExistsException(String.format("Award with name %s already exists.", name));
        }
        Award award = new Award(name);
        return awardRepository.save(award);
    }

    private boolean awardExists(String name) {
        return awardRepository.findByName(name).isPresent();
    }
}
