package com.rchauhan.amdb.repositories;

import com.rchauhan.amdb.model.WonRelation;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WonRelationRepository extends Neo4jRepository<WonRelation, Long> {

    @Query("MATCH (p:Person{id: $personID})-[w:WON{titleID: $titleID, year: $wonYear}]->(a:Award{id: $awardID}) RETURN p, w, a")
    Optional<WonRelation> getWonRelation(UUID personID, UUID awardID, String titleID, Integer wonYear);

    @Query("MATCH (p:Person{id: $personID}), (a:Award{id: $awardID}) CREATE (p)-[w:WON {titleID: $titleID, year: $wonYear}]->(a) RETURN p, w, a")
    WonRelation createWonRelation(UUID personID, UUID awardID, String titleID, Integer wonYear);
}
