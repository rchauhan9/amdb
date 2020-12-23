package com.rchauhan.amdb.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.rchauhan.amdb.model.*;
import com.rchauhan.amdb.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class MutationResolver implements GraphQLMutationResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(MutationResolver.class);

    @Autowired
    ActedInRelationService actedInRelationService;

    @Autowired
    AwardService awardService;

    @Autowired
    DirectedRelationService directedRelationService;

    @Autowired
    GenreService genreService;

    @Autowired
    GenreRelationService genreRelationService;

    @Autowired
    NominatedRelationService nominatedRelationService;

    @Autowired
    PersonService personService;

    @Autowired
    ProducedRelationService producedRelationService;

    @Autowired
    TitleService titleService;

    @Autowired
    WonRelationService wonRelationService;

    @Autowired
    WroteRelationService wroteRelationService;

    public ActedInRelation createActedInRelation(String personName, Date personDOB, String titleName, Integer titleReleased, List<String> characters, Integer billing) {
        LOGGER.info("Creating ACTED_IN relation between Person({}, {}) and Title({}, {}). Relation info: characters: {}, billing: {}.",
                personName, personDOB, titleName, titleReleased, characters, billing);
        return actedInRelationService.createActedInRelation(personName, personDOB, titleName, titleReleased, characters, billing);
    }

    public Award createAward(String name, String organisation) {
        LOGGER.info("Creating Award with name: {} and organisation: {}", name, organisation);
        return awardService.createAward(name, organisation);
    }

    public DirectedRelation createDirectedRelation(String personName, Date personDOB, String titleName, Integer titleReleased) {
        LOGGER.info("Creating DIRECTED relation between Person({}, {}) and Title({}, {}).",
                personName, personDOB, titleName, titleReleased);
        return directedRelationService.createDirectedRelation(personName, personDOB, titleName, titleReleased);
    }

    public Genre createGenre(String name) {
        LOGGER.info("Creating Genre with name: {}", name);
        return genreService.createGenre(name);
    }

    public GenreRelation createGenreRelation(String titleName, Integer titleReleased, String genreName) {
        LOGGER.info("Creating GENRE relation between Title({}, {}) and Genre({}).", titleName, titleReleased, genreName);
        return genreRelationService.createGenreRelation(titleName, titleReleased, genreName);
    }

    public NominatedRelation createNominatedRelation(String personName, Date personDOB, String awardName, String awardOrganisation, Integer nominationYear, String titleName, Integer titleReleased) {
        LOGGER.info("Creating NOMINATED relation between Person({}, {}) and Award({}, {}). Relation info: nominationYear: {}, titleName: {}, titleRelease: {}.",
                personName, personDOB, awardName, awardOrganisation, nominationYear, titleName, titleReleased);
        return nominatedRelationService.createNominatedRelation(personName, personDOB, awardName, awardOrganisation, nominationYear, titleName, titleReleased);
    }

    public Person createPerson(String name, Date dateOfBirth, String bio) {
        LOGGER.info("Creating Person with name: {} and dOB: {}", name, dateOfBirth);
        return personService.createPerson(name, dateOfBirth, bio);
    }

    public ProducedRelation createProducedRelation(String personName, Date personDOB, String titleName, Integer titleReleased, List<String> items) {
        LOGGER.info("Creating PRODUCED relation between Person({}, {}) and Title({}, {}). Relation info: items: {}.",
                personName, personDOB, titleName, titleReleased, items);
        return producedRelationService.createProducedRelation(personName, personDOB, titleName, titleReleased, items);
    }

    public Title createTitle(String name, String summary, Integer released, String certificateRating, Integer titleLengthInMins, String storyline, String tagline) {
        LOGGER.info("Creating Title with name: {}: and releaseYear {}", name, released);
        return titleService.createTitle(name, summary, released, certificateRating, titleLengthInMins, storyline, tagline);
    }

    public WonRelation createWonRelation(String personName, Date personDOB, String awardName, String awardOrganisation, Integer wonYear, String titleName, Integer titleReleased) {
        LOGGER.info("Creating WON relation between Person({}, {}) and Award({}, {}). Relation info: wonYear: {}, titleName: {}, titleRelease: {}.",
                personName, personDOB, awardName, awardOrganisation, wonYear, titleName, titleReleased);
        return wonRelationService.createWonRelation(personName, personDOB, awardName, awardOrganisation, wonYear, titleName, titleReleased);
    }

    public WroteRelation createWroteRelation(String personName, Date personDOB, String titleName, Integer titleReleased, List<String> items) {
        LOGGER.info("Creating WROTE relation between Person({}, {}) and Title({}, {}). Relation info: items: {}.",
                personName, personDOB, titleName, titleReleased, items);
        return wroteRelationService.createWroteRelation(personName, personDOB, titleName, titleReleased, items);
    }


}
