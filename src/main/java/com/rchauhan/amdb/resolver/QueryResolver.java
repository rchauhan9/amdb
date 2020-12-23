package com.rchauhan.amdb.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.rchauhan.amdb.model.*;
import com.rchauhan.amdb.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class QueryResolver implements GraphQLQueryResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueryResolver.class);

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
        LOGGER.info("Getting award by id: {}", id);
        return awardService.getAward(id);
    }

    public Optional<Award> getAwardByNameAndOrganisation(String name, String organisation) {
        LOGGER.info("Getting award by name: {} and organisation: {}", name, organisation);
        return awardService.getAwardByNameAndOrganisation(name, organisation);
    }

    public Optional<Genre> getGenre(UUID id) {
        LOGGER.info("Getting genre by id: {}", id);
        return genreService.getGenre(id);
    }

    public Optional<Genre> getGenreByUrlID(String urlID) {
        LOGGER.info("Getting genre by urlID: {}", urlID);
        return genreService.getGenreByUrlID(urlID);
    }

    public Optional<Genre> getGenreByName(String name) {
        LOGGER.info("Getting genre by name: {}", name);
        return genreService.getGenreByName(name);
    }

    public Optional<Person> getPerson(UUID id) {
        LOGGER.info("Getting person by id: {}", id);
        return personService.getPerson(id);
    }

    public Optional<Person> getPersonByUrlID(String urlID) {
        LOGGER.info("Getting person by urlID: {}", urlID);
        return personService.getPersonByUrlID(urlID);
    }

    public Optional<Person> getPersonByNameAndDateOfBirth(String name, Date dateOfBirth) {
        LOGGER.info("Getting person by name: {} and dOB: {}", name, dateOfBirth);
        return personService.getPersonByNameAndDateOfBirth(name, dateOfBirth);
    }

    public Optional<Title> getTitle(UUID id) {
        LOGGER.info("Getting title by id: {}", id);
        return titleService.getTitle(id);
    }

    public Optional<Title> getTitleByUrlID(String urlID) {
        LOGGER.info("Getting title by urlID: {}", urlID);
        return titleService.getTitleByUrlID(urlID);
    }

    public Optional<Title> getTitleByNameAndReleased(String name, Integer released) {
        LOGGER.info("Getting title by name: {} and release year: {}", name, released);
        return titleService.getTitleByNameAndReleased(name, released);
    }

    public List<Searchable> getSearchableByName(String name) {
        LOGGER.info("Getting searchables by query: {}", name);
        return searchableService.getSearchableByName(name);
    }

    public String healthcheck(String info) {
        LOGGER.info("Healthcheck: {}", info);
        return "Healthcheck: " + info;
    }

}
