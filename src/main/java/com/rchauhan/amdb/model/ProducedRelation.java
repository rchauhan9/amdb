package com.rchauhan.amdb.model;

import org.neo4j.ogm.annotation.*;

import java.util.List;

@RelationshipEntity(type = "PRODUCED")
public class ProducedRelation {

    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    Person person;

    @EndNode
    Title title;

    private List<String> items;

    public ProducedRelation() {
    }

    public ProducedRelation(Person person, Title title, List<String> items) {
        this.person = person;
        this.title = title;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public Person getPerson() {
        return person;
    }

    public Title getTitle() {
        return title;
    }

    public List<String> getItems() {
        return items;
    }
}
