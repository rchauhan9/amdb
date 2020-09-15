package com.rchauhan.amdb.repositories;

import com.rchauhan.amdb.model.DirectedRelation;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DirectedRelationRepository extends Neo4jRepository<DirectedRelation, Long> {

    @Query("MATCH (p:Person {id: $personID}), (t:Title {id: $titleID}) CREATE (p)-[d:DIRECTED]->(t) RETURN p, d, t")
    DirectedRelation createDirectedRelation(UUID personID, UUID titleID);

    @Query("MATCH (p:Person {id: $personID})-[d:DIRECTED]->(t:Title {id: $titleID}) RETURN p, d, t")
    Optional<DirectedRelation> getDirectedRelation(UUID personID, UUID titleID);
}
