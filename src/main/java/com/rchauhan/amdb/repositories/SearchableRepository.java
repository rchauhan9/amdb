package com.rchauhan.amdb.repositories;

import com.rchauhan.amdb.model.Searchable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SearchableRepository extends Neo4jRepository<Searchable, UUID> {

    @Query("CALL db.index.fulltext.queryNodes(\"names\", $name) YIELD node RETURN node")
    List<Searchable> findByName(String name);
}
