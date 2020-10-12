package com.rchauhan.amdb.repositories;

import com.rchauhan.amdb.model.Person;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PersonRepository extends Neo4jRepository<Person, UUID> {

    @Query("MATCH (p:Person {name: $name, dateOfBirth: $dateOfBirth}) RETURN p")
    Optional<Person> getPersonByNameAndDateOfBirth(String name, String dateOfBirth);

    Optional<Person> findByUrlID(String urlID);

    Optional<Person> findByNameAndDateOfBirth(String name, String dateOfBirth);
}
