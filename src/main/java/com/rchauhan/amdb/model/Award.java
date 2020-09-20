package com.rchauhan.amdb.model;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.typeconversion.Convert;
import org.neo4j.ogm.id.UuidStrategy;
import org.neo4j.ogm.typeconversion.UuidStringConverter;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@NodeEntity
public class Award {

    @Id
    @GeneratedValue(strategy = UuidStrategy.class)
    @Convert(UuidStringConverter.class)
    private UUID id;

    private String name;

    private String organisation;

    @Relationship(type = "NOMINATED", direction = Relationship.INCOMING)
    private List<NominatedRelation> nominations;

    @Relationship(type = "WON", direction = Relationship.INCOMING)
    private List<NominatedRelation> wins;


    public Award() {
    }

    public Award(UUID id) {
        this.id = id;
    }

    public Award(String name, String organisation) {
        this.name = name;
        this.organisation = organisation;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOrganisation() {
        return organisation;
    }

    public List<NominatedRelation> getNominations() {
        return nominations;
    }

    public List<NominatedRelation> getWins() {
        return wins;
    }

    @Override
    public String toString() {
        return "Award{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Award award = (Award) o;
        return Objects.equals(id, award.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
