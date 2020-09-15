package com.rchauhan.amdb.repositories;

import com.rchauhan.amdb.model.AwardOrganisation;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AwardOrganisationRepository extends Neo4jRepository<AwardOrganisation, UUID> {

    @Query("MATCH (ao:AwardOrganisation {name: $name, yearEstablishedIn: $yearEstablishedIn}) RETURN (ao)")
    Optional<AwardOrganisation> getAwardOrganisationByNameAndYearEstablishedIn(String name, Integer yearEstablishedIn);

    Optional<AwardOrganisation> findByName(String name);
}
