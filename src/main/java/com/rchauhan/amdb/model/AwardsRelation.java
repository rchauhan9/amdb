package com.rchauhan.amdb.model;

import org.neo4j.ogm.annotation.*;
import org.neo4j.ogm.annotation.typeconversion.Convert;
import org.neo4j.ogm.id.UuidStrategy;
import org.neo4j.ogm.typeconversion.UuidStringConverter;

import java.util.UUID;

@RelationshipEntity(type = "AWARDS")
public class AwardsRelation {

    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private AwardOrganisation awardOrganisation;

    @EndNode
    private Award award;

    public AwardsRelation() {
    }

    public AwardsRelation(AwardOrganisation awardOrganisation, Award award) {
        this.awardOrganisation = awardOrganisation;
        this.award = award;
    }

    public Long getId() {
        return id;
    }

    public AwardOrganisation getAwardOrganisation() {
        return awardOrganisation;
    }

    public Award getAward() {
        return award;
    }
}
