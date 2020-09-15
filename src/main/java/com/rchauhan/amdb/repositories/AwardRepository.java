package com.rchauhan.amdb.repositories;

import com.rchauhan.amdb.model.Award;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AwardRepository extends Neo4jRepository<Award, UUID> {

//    @Query("MATCH (a:Award {name: $name}) RETURN (a)")
//    Optional<Object> getAwardByName(String name);

    Optional<Award> findByName(String name);
}
