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
public class AwardOrganisation {

    @Id
    @GeneratedValue(strategy = UuidStrategy.class)
    @Convert(UuidStringConverter.class)
    private UUID id;

    private String name;

    private Integer yearEstablishedIn;

    @Relationship(type = "AWARDS")
    private List<AwardsRelation> awards;

    public AwardOrganisation() {
    }

    public AwardOrganisation(UUID id) {
        this.id = id;
    }

    public AwardOrganisation(String name, Integer yearEstablishedIn) {
        this.name = name;
        this.yearEstablishedIn = yearEstablishedIn;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getYearEstablishedIn() {
        return yearEstablishedIn;
    }

    public List<AwardsRelation> getAwards() {
        return awards;
    }

    @Override
    public String toString() {
        return "AwardOrganisation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", yearEstablishedIn=" + yearEstablishedIn +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AwardOrganisation that = (AwardOrganisation) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
