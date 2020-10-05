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
public class Genre {

    @Id
    @GeneratedValue(strategy = UuidStrategy.class)
    @Convert(UuidStringConverter.class)
    private UUID id;

    private String name;

    private String urlID;

    @Relationship(type = "GENRE", direction = Relationship.INCOMING)
    List<GenreRelation> movies = new ArrayList<>();

    public Genre() {
    }

    public Genre(UUID id) {
        this.id = id;
    }

    public Genre(String name, String urlID) {
        this.name = name;
        this.urlID = urlID;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrlID() {
        return urlID;
    }

    public List<GenreRelation> getMovies() {
        return movies;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", urlID='" + urlID + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genre genre = (Genre) o;
        return Objects.equals(id, genre.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
