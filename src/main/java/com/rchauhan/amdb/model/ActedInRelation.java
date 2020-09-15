package com.rchauhan.amdb.model;

import org.neo4j.ogm.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RelationshipEntity(type = "ACTED_IN")
public class ActedInRelation {

    @Id
    @GeneratedValue
    private Long id;

    private List<String> characters;

    private Integer billing;

    @StartNode
    private Person person;

    @EndNode
    private Title title;

    public ActedInRelation() {
    }

    public ActedInRelation(List<String> characters, Integer billing, Person person, Title title) {
        this.characters = characters;
        this.billing = billing;
        this.person = person;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public List<String> getCharacters() {
        return characters;
    }

    public Integer getBilling() {
        return billing;
    }

    public Person getPerson() {
        return person;
    }

    public Title getTitle() {
        return title;
    }

    public void addRoleName(String name) {
        if (this.characters == null) {
            this.characters = new ArrayList<>();
        }
        this.characters.add(name);
    }
}
