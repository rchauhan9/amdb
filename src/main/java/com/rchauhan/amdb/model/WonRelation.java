package com.rchauhan.amdb.model;

import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "WON")
public class WonRelation {

    @Id
    @GeneratedValue
    private Long id;

    private Integer year;

    private String titleID;

    @StartNode
    private Person person;

    @EndNode
    private Award award;

    public WonRelation() {
    }

    public WonRelation(Integer year, String titleID, Person person, Award award) {
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

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setTitleID(String titleID) {
        this.titleID = titleID;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setAward(Award award) {
        this.award = award;
    }
}
