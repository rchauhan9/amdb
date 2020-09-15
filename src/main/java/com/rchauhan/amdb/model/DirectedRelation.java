package com.rchauhan.amdb.model;

import org.neo4j.ogm.annotation.*;


@RelationshipEntity(type = "DIRECTED")
public class DirectedRelation {

    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private Person person;

    @EndNode
    private Title title;

    public DirectedRelation() {
    }

    public DirectedRelation(Person person, Title title) {
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
