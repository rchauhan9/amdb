package com.rchauhan.amdb.model;

import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "WON")
public class WonRelation {

    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private Person person;

    @EndNode
    private Award award;

    private Integer year;
    private String titleName;
    private Integer titleReleased;

    public WonRelation() {
    }

    public WonRelation(Person person, Award award, Integer year, String titleName, Integer titleReleased) {
        this.person = person;
        this.award = award;
        this.year = year;
        this.titleName = titleName;
        this.titleReleased = titleReleased;
    }

    public Long getId() {
        return id;
    }

    public Person getPerson() {
        return person;
    }

    public Award getAward() {
        return award;
    }

    public Integer getYear() {
        return year;
    }

    public String getTitleName() {
        return titleName;
    }

    public Integer getTitleReleased() {
        return titleReleased;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setAward(Award award) {
        this.award = award;
    }
}
