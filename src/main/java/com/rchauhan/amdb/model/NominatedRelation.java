package com.rchauhan.amdb.model;

import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "NOMINATED")
public class NominatedRelation {

    @Id
    @GeneratedValue
    private Long id;

    private Integer year;

    private String titleID;

    @StartNode
    private Person person;

    @EndNode
    private Award award;

    public NominatedRelation() {
    }

    public NominatedRelation(Integer year, String titleID, Person person, Award award) {
        this.year = year;
        this.titleID = titleID;
        this.person = person;
        this.award = award;
    }

    public Long getId() {
        return id;
    }

    public Integer getYear() {
        return year;
    }

    public String getTitleID() {
        return titleID;
    }

    public Person getPerson() {
        return person;
    }

    public Award getAward() {
        return award;
    }
}
