package com.rchauhan.amdb.model;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.typeconversion.Convert;
import org.neo4j.ogm.id.UuidStrategy;
import org.neo4j.ogm.typeconversion.UuidStringConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@NodeEntity
public class Title {

    @Id
    @GeneratedValue(strategy = UuidStrategy.class)
    @Convert(UuidStringConverter.class)
    private UUID id;

    private String name;

    private String summary;

    private Integer released;

    private String certificateRating;

    private Integer titleLengthInMins;

    private String storyline;

    private String tagline;

    private String urlID;

    @Relationship(type = "GENRE")
    private List<GenreRelation> genres;

    @Relationship(type = "ACTED_IN", direction = Relationship.INCOMING)
    private List<ActedInRelation> cast;

    @Relationship(type = "DIRECTED", direction = Relationship.INCOMING)
    private List<DirectedRelation> directors;

    @Relationship(type = "WROTE", direction = Relationship.INCOMING)
    private List<WroteRelation> writers;

    @Relationship(type = "PRODUCED", direction = Relationship.INCOMING)
    private List<ProducedRelation> producers;

    public Title() {
    }

    public Title(UUID id) {
        this.id = id;
    }

    public Title(String name, String summary, Integer released, String certificateRating, Integer titleLengthInMins, String storyline, String tagline, String urlID) {
        this.name = name;
        this.summary = summary;
        this.released = released;
        this.certificateRating = certificateRating;
        this.titleLengthInMins = titleLengthInMins;
        this.storyline = storyline;
        this.tagline = tagline;
        this.urlID = urlID;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSummary() {
        return summary;
    }

    public Integer getReleased() {
        return released;
    }

    public String getCertificateRating() {
        return certificateRating;
    }

    public Integer getTitleLengthInMins() {
        return titleLengthInMins;
    }

    public String getStoryline() {
        return storyline;
    }

    public String getTagline() {
        return tagline;
    }

    public String getUrlID() {
        return urlID;
    }

    public List<ActedInRelation> getCast() {
        return cast;
    }

    public List<DirectedRelation> getDirectors() {
        return directors;
    }

    public List<WroteRelation> getWriters() {
        return writers;
    }

    public List<ProducedRelation> getProducers() {
        return producers;
    }

    public List<GenreRelation> getGenres() {
        return genres;
    }

    public void addRole(ActedInRelation role) {
        if (this.cast == null) {
            this.cast = new ArrayList<>();
        }
        this.cast.add(role);
    }

    @Override
    public String toString() {
        return "Title{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", summary='" + summary + '\'' +
                ", released=" + released +
                ", certificateRating='" + certificateRating + '\'' +
                ", titleLengthInMins=" + titleLengthInMins +
                ", storyline='" + storyline + '\'' +
                ", tagline='" + tagline + '\'' +
                ", urlID='" + urlID + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Title title = (Title) o;
        return Objects.equals(id, title.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
