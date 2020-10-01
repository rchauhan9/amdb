package com.rchauhan.amdb.repositories;

import com.rchauhan.amdb.model.NominatedRelation;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface NominatedRelationRepository extends Neo4jRepository<NominatedRelation, Long> {

    @Query("MATCH (p:Person{id: $personID})-[n:NOMINATED{year: $nominationYear, titleName: $titleName, titleReleased: $titleReleased}]->(a:Award{id: $awardID}) RETURN p, n, a")
    Optional<NominatedRelation> getNominatedRelation(UUID personID, UUID awardID, Integer nominationYear, String titleName, Integer titleReleased);

    @Query("MATCH (p:Person{id: $personID}), (a:Award{id: $awardID}) CREATE (p)-[n:NOMINATED {year: $nominationYear, titleName: $titleName, titleReleased: $titleReleased}]->(a) RETURN p, n, a")
    NominatedRelation createNominatedRelation(UUID personID, UUID awardID, Integer nominationYear, String titleName, Integer titleReleased);
}
