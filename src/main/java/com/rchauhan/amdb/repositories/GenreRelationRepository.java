package com.rchauhan.amdb.repositories;

import com.rchauhan.amdb.model.GenreRelation;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GenreRelationRepository extends Neo4jRepository<GenreRelation, Long> {

    @Query("MATCH (g:Genre {name: $name})<-[y:GENRE]-(t:Title) RETURN g, y, t")
    List<GenreRelation> getTitlesByGenre(String name);

    @Query("MATCH (t:Title{id: $titleID})-[y:GENRE]->(g:Genre{id: $genreID}) RETURN t, y, g")
    Optional<GenreRelation> getGenreRelation(UUID titleID, UUID genreID);

    @Query("MATCH (t:Title{id: $titleID}), (g:Genre{id: $genreID}) CREATE (t)-[y:GENRE]->(g) RETURN t, y, g")
    GenreRelation createGenreRelation(UUID titleID, UUID genreID);
}
