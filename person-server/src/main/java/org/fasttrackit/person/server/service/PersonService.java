package org.fasttrackit.person.server.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.RequiredArgsConstructor;
import org.fasttrackit.person.model.Person;
import org.fasttrackit.person.server.entity.PersonEntity;
import org.fasttrackit.person.server.exception.ResourceNotFoundException;
import org.fasttrackit.person.server.model.PersonFilter;
import org.fasttrackit.person.server.model.mapper.PersonMapper;
import org.fasttrackit.person.server.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository repository;
    private final PersonMapper mapper;

    public List<PersonEntity> getAll(PersonFilter filter) {
        return repository.findAll().stream()
                .filter(person -> applyNameFilter(filter, person))
                .filter(person -> filter.minAge() == null || person.getAge() >= filter.minAge())
                .toList();
    }

    private boolean applyNameFilter(PersonFilter filter, PersonEntity person) {
        return filter.name() == null
                || filter.name().stream()
                .anyMatch(name -> person.getNewName().contains(name));
    }

    public PersonEntity replaceEntity(int id, PersonEntity newEntity) {
        PersonEntity dbEntity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find person with id %s".formatted(id)));
//        return repository.save(dbEntity.toBuilder()
//                .age(newEntity.getAge())
//                .name(newEntity.getName())
//                .build());

        return repository.save(dbEntity
                .withNewName(newEntity.getNewName())
                .withAge(newEntity.getAge()));
    }

    public PersonEntity createEntity(PersonEntity newEntity) {
        validatePerson(newEntity);
        return repository.save(newEntity.withId(0));
    }

    private void validatePerson(PersonEntity newEntity) {
    }

    public PersonEntity updateEntity(int id, JsonPatch jsonPatch) {
        return repository.findById(id)
                .map(dbEntity -> applyPatch(dbEntity, jsonPatch))
                .map(dbEntity -> replaceEntity(id, dbEntity))
                .orElseThrow(() -> new ResourceNotFoundException("Could not find person with id " + id));

    }

    private PersonEntity applyPatch(PersonEntity dbEntity, JsonPatch jsonPatch) {
        try {
            ObjectMapper jsonMapper = new ObjectMapper();
            JsonNode jsonNode = jsonMapper.convertValue(mapper.toApi(dbEntity), JsonNode.class);
            JsonNode patchedJson = jsonPatch.apply(jsonNode);
            return mapper.toEntity(jsonMapper.treeToValue(patchedJson, Person.class));
        } catch (JsonProcessingException | JsonPatchException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<PersonEntity> get(int personId) {
        return repository.findById(personId);
    }
}
