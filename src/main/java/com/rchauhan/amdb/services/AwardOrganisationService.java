package com.rchauhan.amdb.services;

import com.rchauhan.amdb.exceptions.AwardOrganisationExistsException;
import com.rchauhan.amdb.model.AwardOrganisation;
import com.rchauhan.amdb.repositories.AwardOrganisationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AwardOrganisationService {

    @Autowired
    AwardOrganisationRepository awardOrganisationRepository;

    public Optional<AwardOrganisation> getAwardOrganisation(UUID id) {
        return awardOrganisationRepository.findById(id);
    }

    public Optional<AwardOrganisation> getAwardOrganisationByName(String name) {
        return awardOrganisationRepository.findByName(name);
    }

    public AwardOrganisation createAwardOrganisation(String name, Integer yearEstablishedIn) {
        if (awardOrganisationExists(name)) {
            throw new AwardOrganisationExistsException(String.format("Award Organisation with name %s established in %d already exists.", name, yearEstablishedIn));
        }
        AwardOrganisation awardOrganisation = new AwardOrganisation(name, yearEstablishedIn);
        return awardOrganisationRepository.save(awardOrganisation);
    }

    private boolean awardOrganisationExists(String name) {
        return awardOrganisationRepository.findByName(name).isPresent();
    }
}
