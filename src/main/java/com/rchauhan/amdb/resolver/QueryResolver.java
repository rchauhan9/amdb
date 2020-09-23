package com.rchauhan.amdb.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.rchauhan.amdb.model.Award;
import com.rchauhan.amdb.model.Genre;
import com.rchauhan.amdb.model.Person;
import com.rchauhan.amdb.model.Title;
import com.rchauhan.amdb.services.AwardService;
import com.rchauhan.amdb.services.GenreService;
import com.rchauhan.amdb.services.PersonService;
import com.rchauhan.amdb.services.TitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
