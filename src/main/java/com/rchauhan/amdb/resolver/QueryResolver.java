package com.rchauhan.amdb.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.rchauhan.amdb.model.*;
import com.rchauhan.amdb.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class QueryResolver implements GraphQLQueryResolver {

    @Autowired
    AwardOrganisationService awardOrganisationService;

    @Autowired
    AwardService awardService;

    @Autowired
    GenreService genreService;

    @Autowired
    PersonService personService;

    @Autowired
    TitleService titleService;

    public Optional<AwardOrganisation> getAwardOrganisation(UUID id) {
        return awardOrganisationService.getAwardOrganisation(id);
    }

    public Optional<AwardOrganisation> getAwardOrganisationByName(String name) {
        return awardOrganisationService.getAwardOrganisationByName(name);
    }

    public Optional<Award> getAward(UUID id) {
        return awardService.getAward(id);
    }

    public Optional<Award> getAwardByName(String name) {
        return awardService.getAwardByName(name);
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

    public Optional<Person> getPersonByNameAndDateOfBirth(String name, String dateOfBirth) {
        return personService.getPersonByNameAndDateOfBirth(name, dateOfBirth);
    }

    public Optional<Title> getTitle(UUID id) {
        return titleService.getTitle(id);
    }

    public Optional<Title> getTitleByNameAndReleased(String name, Integer released) {
        return titleService.getTitleByNameAndReleased(name, released);
    }

}
