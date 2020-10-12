package com.rchauhan.amdb.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.rchauhan.amdb.model.*;
import com.rchauhan.amdb.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class QueryResolver implements GraphQLQueryResolver {

    @Autowired
    AwardService awardService;

    @Autowired
    GenreService genreService;

    @Autowired
    PersonService personService;

    @Autowired
    TitleService titleService;

    @Autowired
    SearchableService searchableService;

    public Optional<Award> getAward(UUID id) {
        return awardService.getAward(id);
    }

    public Optional<Award> getAwardByNameAndOrganisation(String name, String organisation) {
        return awardService.getAwardByNameAndOrganisation(name, organisation);
    }

    public Optional<Genre> getGenre(UUID id) {
        return genreService.getGenre(id);
    }

    public Optional<Genre> getGenreByName(String name) {
        return genreService.getGenreByName(name);
    }

    public Optional<Person> getPerson(UUID id) {
        return personService.getPerson(id);
    }

    public Optional<Person> getPersonByUrlID(String urlID) { return personService.getPersonByUrlID(urlID); }

    public Optional<Person> getPersonByNameAndDateOfBirth(String name, String dateOfBirth) {
        return personService.getPersonByNameAndDateOfBirth(name, dateOfBirth);
    }

    public Optional<Title> getTitle(UUID id) {
        return titleService.getTitle(id);
    }

    public Optional<Title> getTitleByUrlID(String urlID) {
        return titleService.getTitleByUrlID(urlID);
    }

    public Optional<Title> getTitleByNameAndReleased(String name, Integer released) {
        return titleService.getTitleByNameAndReleased(name, released);
    }

    public List<Searchable> getSearchableByName(String name) {
        return searchableService.getSearchableByName(name);
    }

}
