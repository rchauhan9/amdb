package com.rchauhan.amdb.repositories;

import com.rchauhan.amdb.model.Title;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TitleRepository extends Neo4jRepository<Title, UUID> {

    Optional<Title> findByNameAndReleased(String name, Integer released);

    @Query("MATCH (m:Title)<-[r:ACTED_IN|DIRECTED]-(a:Person) RETURN m,r,a LIMIT $limit")
    Collection<Title> getAllTitles(@Param("limit") int limit);

    @Query("MATCH (p: Person {name: $name})-[r:ACTED_IN]->(m:Title) RETURN m")
    List<Title> getTitlesStarring(String name);
}


//    CREATE (Inception:Title {title: 'Inception', description: 'An exciting thriller set in the dream world.'})
//    CREATE (DarkKnight:Title {title: 'The Dark Knight', description: 'Batman takes on the Joker in this thrilling sequel to Batman Begins.'})
//    CREATE (ChrisNolan:Person {name: 'Christopher Nolan', dateOfBirth: '30 July 1970'})
//    CREATE (LeoDiCaprio:Person {name: 'Leonardo DiCaprio', dateOfBirth: '11 November 1974'})
//    CREATE (ChristianBale:Person {name: 'Christian Bale', dateOfBirth: '30 January 1974'})
//
//    CREATE (ChrisNolan)-[:DIRECTED]->(Inception)
//    CREATE (ChrisNolan)-[:DIRECTED]->(DarkKnight)
//    CREATE (LeoDiCaprio)-[:ACTED_IN {characters: ['Cobb']}]->(Inception)
//    CREATE (ChristianBale)-[:ACTED_IN {characters: ['Bruce Wayne', 'Batman']}]->(DarkKnight)