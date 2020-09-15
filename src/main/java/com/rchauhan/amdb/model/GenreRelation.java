package com.rchauhan.amdb.model;

import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "GENRE")
public class GenreRelation {

    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    Title title;

    @EndNode
    Genre genre;

    public GenreRelation() {
    }

    public GenreRelation(Title title, Genre genre) {
        this.title = title;
        this.genre = genre;
    }

    public Long getId() {
        return id;
    }

    public Title getTitle() {
        return title;
    }

    public Genre getGenre() {
        return genre;
    }
}
