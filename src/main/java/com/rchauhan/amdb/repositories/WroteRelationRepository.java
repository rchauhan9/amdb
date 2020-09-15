package com.rchauhan.amdb.repositories;

import com.rchauhan.amdb.model.WroteRelation;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WroteRelationRepository extends Neo4jRepository<WroteRelation, Long> {

    @Query("MATCH (p:Person {id: $personID}), (t:Title {id: $titleID}) CREATE (p)-[w:WROTE {items: $items}]->(t) RETURN p, w, t")
    WroteRelation createWroteRelation(UUID personID, UUID titleID, List<String> items);

    @Query("MATCH (p:Person {id: $personID})-[w:WROTE]->(t:Title {id: $titleID}) RETURN p, w, t")
    Optional<WroteRelation> getWroteRelation(UUID personID, UUID titleID);
}
