package com.rchauhan.amdb.model;

import com.rchauhan.amdb.utils.Constants;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.typeconversion.Convert;
import org.neo4j.ogm.annotation.typeconversion.DateString;
import org.neo4j.ogm.id.UuidStrategy;
import org.neo4j.ogm.typeconversion.UuidStringConverter;

import java.util.*;

@NodeEntity
public class Person implements Searchable {

    @Id
    @GeneratedValue(strategy = UuidStrategy.class)
    @Convert(UuidStringConverter.class)
    private UUID id;

    private String name;

    @DateString(Constants.PERSON_DOB_FORMAT)
    private Date dateOfBirth;

    private String bio;

    private String urlID;

    @Relationship(type = "ACTED_IN")
    private List<ActedInRelation> actedIn = new ArrayList<>();

    @Relationship(type = "DIRECTED")
    private List<DirectedRelation> directed = new ArrayList<>();

    @Relationship(type = "PRODUCED")
    private List<ProducedRelation> produced = new ArrayList<>();

    @Relationship(type = "WROTE")
    private List<WroteRelation> wrote = new ArrayList<>();

    @Relationship(type = "NOMINATED")
    private List<NominatedRelation> awardNominations = new ArrayList<>();

    @Relationship(type = "WON")
    private List<WonRelation> awardWins = new ArrayList<>();

    public Person() {
    }

    public Person(UUID id) {
        this.id = id;
    }

    public Person(String name, Date dateOfBirth, String bio, String urlID) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.bio = bio;
        this.urlID = urlID;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getBio() {
        return bio;
    }

    public String getUrlID() {
        return urlID;
    }

    public List<ActedInRelation> getActedIn() {
        return actedIn;
    }

    public List<DirectedRelation> getDirected() {
        return directed;
    }

    public List<WroteRelation> getWrote() {
        return wrote;
    }

    public List<ProducedRelation> getProduced() {
        return produced;
    }

    public List<NominatedRelation> getAwardNominations() {
        return awardNominations;
    }

    public List<WonRelation> getAwardWins() {
        return awardWins;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", bio='" + bio + '\'' +
                ", urlID='" + urlID + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
