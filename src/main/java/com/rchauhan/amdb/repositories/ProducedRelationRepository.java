package com.rchauhan.amdb.repositories;

import com.rchauhan.amdb.model.ProducedRelation;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProducedRelationRepository extends Neo4jRepository<ProducedRelation, Long> {

    @Query("MATCH (p:Person {id: $personID})-[y:PRODUCED]->(t:Title {id: $titleID}) RETURN p, y, t")
    Optional<ProducedRelation> getProducedRelation(UUID personID, UUID titleID);

    @Query("MATCH (p:Person {id: $personID}), (t:Title {id: $titleID}) CREATE (p)-[y:PRODUCED {items: $items}]->(t) RETURN p, y, t")
    ProducedRelation createProducedRelation(UUID personID, UUID titleID, List<String> items);
}
