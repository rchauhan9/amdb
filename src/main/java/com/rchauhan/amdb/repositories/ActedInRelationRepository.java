package com.rchauhan.amdb.repositories;

import com.rchauhan.amdb.model.ActedInRelation;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ActedInRelationRepository extends Neo4jRepository<ActedInRelation, Long> {

    @Query("MATCH (p:Person {id: $personID}), (t:Title {id: $titleID}) CREATE (p)-[a:ACTED_IN {characters: $characters, billing: $billing}]->(t) RETURN p, a, t")
    ActedInRelation createActedInRelation(UUID personID, UUID titleID, List<String> characters, Integer billing);

    @Query("MATCH (p: Person {name: $name})-[r:ACTED_IN]->(t:Title) RETURN p, r, t")
    List<ActedInRelation> getMoviesStarring(String name);

    @Query("MATCH (p:Person{id: $personID})-[a:ACTED_IN]->(t:Title{id: $titleID}) RETURN p, a, t")
    Optional<ActedInRelation> getActedInRelation(UUID personID, UUID titleID);

}
