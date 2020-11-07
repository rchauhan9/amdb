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

    Optional<Title> findByUrlID(String urlID);

    @Query("MATCH (m:Title)<-[r:ACTED_IN|DIRECTED]-(a:Person) RETURN m,r,a LIMIT $limit")
    Collection<Title> getAllTitles(@Param("limit") int limit);

}