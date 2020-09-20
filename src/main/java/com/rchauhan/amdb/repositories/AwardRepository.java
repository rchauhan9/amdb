package com.rchauhan.amdb.repositories;

import com.rchauhan.amdb.model.Award;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AwardRepository extends Neo4jRepository<Award, UUID> {

    Optional<Award> findByNameAndOrganisation(String name, String organisation);
}
