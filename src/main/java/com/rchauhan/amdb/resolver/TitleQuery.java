package com.rchauhan.amdb.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.rchauhan.amdb.model.Title;
import com.rchauhan.amdb.services.TitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;
//
//@Component
//public class TitleQuery implements GraphQLQueryResolver {
//
//    @Autowired
//    TitleService titleService;
//
//    public Optional<Title> getTitle(UUID id) {
//        return titleService.getTitle(id);
//    }
//
//    public Optional<Title> getTitleByNameAndReleased(String name, Integer released) { return titleService.getTitleByNameAndReleased(name, released); }
//
//}
