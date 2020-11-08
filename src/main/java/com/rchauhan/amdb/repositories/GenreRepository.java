package com.rchauhan.amdb.repositories;

import com.rchauhan.amdb.model.Genre;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GenreRepository extends Neo4jRepository<Genre, UUID> {

    Optional<Genre> findByName(String name);

    Optional<Genre> findByUrlID(String urlID);
}
