package org.fasttrackit.person.server.repository;

import org.fasttrackit.person.server.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<PersonEntity, Integer> {
}
