package com.rchauhan.amdb.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.rchauhan.amdb.model.Genre;
import com.rchauhan.amdb.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

//@Component
//public class GenreQuery implements GraphQLQueryResolver {
//
//    @Autowired
//    GenreService genreService;
//
//    public Optional<Genre> getGenre(UUID id) {
//        return genreService.getGenre(id);
//    }
//
//    public Optional<Genre> getGenreByName(String name) {
//        return genreService.getGenreByName(name);
//    }
//}
