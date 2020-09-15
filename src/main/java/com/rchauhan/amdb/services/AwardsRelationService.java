package com.rchauhan.amdb.services;

import com.rchauhan.amdb.exceptions.AwardDoesNotExistException;
import com.rchauhan.amdb.exceptions.AwardOrganisationDoesNotExistException;
import com.rchauhan.amdb.exceptions.AwardsRelationExistsException;
import com.rchauhan.amdb.model.Award;
import com.rchauhan.amdb.model.AwardOrganisation;
import com.rchauhan.amdb.model.AwardsRelation;
import com.rchauhan.amdb.repositories.AwardsRelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AwardsRelationService {

    @Autowired
    AwardsRelationRepository awardsRelationRepository;

    @Autowired
    AwardOrganisationService awardOrganisationService;

    @Autowired
    AwardService awardService;


    public AwardsRelation createAwardsRelation(String awardOrganisationName, String awardName) {
        Optional<AwardOrganisation> awardOrganisation = awardOrganisationService.getAwardOrganisationByName(awardOrganisationName);
        if (awardOrganisation.isEmpty()) {
            throw new AwardOrganisationDoesNotExistException(String.format("Cannot create AWARDS relation between Award Organisation %s and Award %s. Award Organisation does not exist.", awardOrganisationName, awardName));
        }

        Optional<Award> award = awardService.getAwardByName(awardName);
        if (award.isEmpty()) {
            throw new AwardDoesNotExistException(String.format("Cannot create AWARDS relation between Award Organisation %s and Award %s. Award does not exist.", awardOrganisationName, awardName));
        }

        if (awardsRelationExists(awardOrganisation.get().getId(), award.get().getId())) {
            throw new AwardsRelationExistsException(String.format("AWARDS relation between Award Organisation %s and Award %s exists already.", awardOrganisationName, awardName));
        }

        return awardsRelationRepository.createAwardsRelation(awardOrganisation.get().getId(), award.get().getId());
    }

    private boolean awardsRelationExists(UUID awardOrganisationID, UUID awardID) {
        return awardsRelationRepository.getAwardsRelation(awardOrganisationID, awardID).isPresent();
    }
}
