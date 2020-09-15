package com.rchauhan.amdb.repositories;

import com.rchauhan.amdb.model.NominatedRelation;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface NominatedRelationRepository extends Neo4jRepository<NominatedRelation, Long> {

    @Query("MATCH (p:Person{id: $personID})-[n:NOMINATED{titleID: $titleID, year: $nominationYear}]->(a:Award{id: $awardID}) RETURN p, n, a")
    Optional<NominatedRelation> getNominatedRelation(UUID personID, UUID awardID, String titleID, Integer nominationYear);

    @Query("MATCH (p:Person{id: $personID}), (a:Award{id: $awardID}) CREATE (p)-[n:NOMINATED {titleID: $titleID, year: $nominationYear}]->(a) RETURN p, n, a")
    NominatedRelation createNominatedRelation(UUID personID, UUID awardID, String titleID, Integer nominationYear);
}
