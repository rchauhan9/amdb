package com.rchauhan.amdb.model;

import org.neo4j.ogm.annotation.*;

import java.util.List;

@RelationshipEntity(type = "WROTE")
public class WroteRelation {

    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private Person person;

    @EndNode
    private Title title;

    private List<String> items;

    public WroteRelation() {
    }

    public WroteRelation(Person person, Title title, List<String> items) {
        this.person = person;
        this.title = title;
        this.items = items;
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
