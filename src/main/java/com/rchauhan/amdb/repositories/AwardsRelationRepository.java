package com.rchauhan.amdb.repositories;

import com.rchauhan.amdb.model.AwardsRelation;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AwardsRelationRepository extends Neo4jRepository<AwardsRelation, Long> {


    @Query("MATCH (o:AwardOrganisation{id: $awardOrganisationID)-[y:AWARDS]->(a:Award{id: $awardID}) RETURN o, y, a")
    Optional<AwardsRelation> getAwardsRelation(UUID awardOrganisationID, UUID awardID);

    @Query("MATCH (o:AwardOrganisation{id: $awardOrganisationID), (a:Award{id: $awardID}) CREATE (o)-[y:AWARDS]->(a) RETURN o, y, a")
    AwardsRelation createAwardsRelation(UUID awardOrganisationID, UUID awardID);
}
