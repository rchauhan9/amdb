package com.rchauhan.amdb.model;

import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "PRODUCED")
public class ProducedRelation {

    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    Person person;

    @EndNode
    Title title;

    public ProducedRelation() {
    }

    public ProducedRelation(Person person, Title title) {
        this.person = person;
        this.title = title;
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
}
