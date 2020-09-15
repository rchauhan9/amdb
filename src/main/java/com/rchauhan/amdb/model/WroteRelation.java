package com.rchauhan.amdb.model;

import org.neo4j.ogm.annotation.*;

import java.util.List;

@RelationshipEntity(type = "WROTE")
public class WroteRelation {

    @Id
    @GeneratedValue
    private Long id;

    private List<String> items;

    @StartNode
    private Person person;

    @EndNode
    private Title title;

    public WroteRelation() {
    }

    public WroteRelation(List<String> items, Person person, Title title) {
        this.items = items;
        this.person = person;
        this.title = title;
    }

    public WroteRelation(List<String> items) {
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public List<String> getItems() { return items; }

    public Person getPerson() {
        return person;
    }

    public Title getTitle() {
        return title;
    }
}
