package com.rchauhan.amdb.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.rchauhan.amdb.model.Award;
import com.rchauhan.amdb.services.AwardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

//@Component
//public class AwardQuery implements GraphQLQueryResolver {
//
//    @Autowired
//    AwardService awardService;
//
//    public Optional<Award> getAward(UUID id) {
//        return awardService.getAward(id);
//    }
//
//    public Optional<Award> getAwardByName(String name) {
//        return awardService.getAwardByName(name);
//    }
//}
