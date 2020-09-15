package com.rchauhan.amdb.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.rchauhan.amdb.model.AwardOrganisation;
import com.rchauhan.amdb.services.AwardOrganisationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

//@Component
//public class AwardOrganisationQuery implements GraphQLQueryResolver {
//
//    @Autowired
//    AwardOrganisationService awardOrganisationService;
//
//    public Optional<AwardOrganisation> getAwardOrganisation(UUID id) {
//        return awardOrganisationService.getAwardOrganisation(id);
//    }
//
//    public Optional<AwardOrganisation> getAwardOrganisationByName(String name) {
//        return awardOrganisationService.getAwardOrganisationByName(name);
//    }
//}
