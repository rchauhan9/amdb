package com.rchauhan.amdb.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.rchauhan.amdb.model.*;
import com.rchauhan.amdb.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class MutationResolver implements GraphQLMutationResolver {

    @Autowired
    ActedInRelationService actedInRelationService;

    @Autowired
    AwardService awardService;

    @Autowired
    AwardOrganisationService awardOrganisationService;

    @Autowired
    AwardsRelationService awardsRelationService;

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

    public ActedInRelation createActedInRelation(String personName, String personDOB, String titleName, Integer titleReleased, List<String> characters, Integer billing) {
        return actedInRelationService.createActedInRelation(personName, personDOB, titleName, titleReleased, characters, billing);
    }

    public Award createAward(String name) {
        return awardService.createAward(name);
    }

    public AwardOrganisation createAwardOrganisation(String name, Integer yearEstablishedIn) {
        return awardOrganisationService.createAwardOrganisation(name, yearEstablishedIn);
    }

    public AwardsRelation createAwardsRelation(String awardOrganisationName, String awardName) {
        return awardsRelationService.createAwardsRelation(awardOrganisationName, awardName);
    }


    public DirectedRelation createDirectedRelation(String personName, String personDOB, String titleName, Integer titleReleased) {
        return directedRelationService.createDirectedRelation(personName, personDOB, titleName, titleReleased);
    }

    public Genre createGenre(String name) {
        return genreService.createGenre(name);
    }

    public GenreRelation createGenreRelation(String titleName, Integer titleReleased, String genreName) {
        return genreRelationService.createGenreRelation(titleName, titleReleased, genreName);
    }

    public NominatedRelation createNominatedRelation(String personName, String personDOB, String awardName, String titleID, Integer nominationYear) {
        return nominatedRelationService.createNominatedRelation(personName, personDOB, awardName, titleID, nominationYear);
    }

    public Person createPerson(String name, Date dateOfBirth) {
        return personService.createPerson(name, dateOfBirth);
    }

    public ProducedRelation createProducedRelation(String personName, String personDOB, String titleName, Integer titleReleased) {
        return producedRelationService.createProducedRelation(personName, personDOB, titleName, titleReleased);
    }

    public Title createTitle(String title, String summary, Integer released, String certificateRating, Integer titleLengthInMins, String storyline, String tagline) {
        System.out.println(title);
        System.out.println(summary);
        System.out.println(released);
        System.out.println(certificateRating);
        System.out.println(titleLengthInMins);
        System.out.println(storyline);
        System.out.println(tagline);
        return titleService.createTitle(title, summary, released, certificateRating, titleLengthInMins, storyline, tagline);
    }

    public WonRelation createWonRelation(String personName, String personDOB, String awardName, String titleID, Integer wonYear) {
        System.out.println(personName);
        System.out.println(personDOB);
        System.out.println(awardName);
        System.out.println(titleID);
        System.out.println(wonYear);
        return wonRelationService.createWonRelation(personName, personDOB, awardName, titleID, wonYear);
    }

    public WroteRelation createWroteRelation(String personName, String personDOB, String titleName, Integer titleReleased, List<String> items) {
        return wroteRelationService.createWroteRelation(personName, personDOB, titleName, titleReleased, items);
    }


}
