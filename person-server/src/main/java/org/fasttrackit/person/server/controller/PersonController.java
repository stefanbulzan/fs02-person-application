package org.fasttrackit.person.server.controller;

import com.github.fge.jsonpatch.JsonPatch;
import lombok.RequiredArgsConstructor;
import org.fasttrackit.person.model.Person;
import org.fasttrackit.person.server.entity.PersonEntity;
import org.fasttrackit.person.server.exception.ResourceNotFoundException;
import org.fasttrackit.person.server.model.PersonFilter;
import org.fasttrackit.person.server.model.mapper.PersonMapper;
import org.fasttrackit.person.server.service.PersonService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("persons")
@RequiredArgsConstructor
public class PersonController {
    private final PersonService service;
    private final PersonMapper mapper;

    @GetMapping
    List<Person> getAll(PersonFilter filter) {
        return service.getAll(filter).stream()
                .map(mapper::toApi)
                .toList();
    }

    @PostMapping("filter")
    List<Person> getAllPost(@RequestBody PersonFilter filter) {
        return mapper.toApi(service.getAll(filter));
    }

    @GetMapping("{personId}")
    Person getOne(@PathVariable int personId) {
        return service.get(personId)
                .map(mapper::toApi)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find person with id " + personId));
    }

    @PostMapping
    PersonEntity createEntity(@RequestBody Person newEntity) {
        return service.createEntity(mapper.toEntity(newEntity));
    }

    @PutMapping("{id}")
    PersonEntity replaceEntity(@PathVariable int id, @RequestBody Person newEntity) {
        return service.replaceEntity(id, mapper.toEntity(newEntity));
    }

    @PatchMapping("{id}")
    PersonEntity updateEntity(@PathVariable int id, @RequestBody JsonPatch updatedEntity) {
        return service.updateEntity(id, updatedEntity);
    }
}

