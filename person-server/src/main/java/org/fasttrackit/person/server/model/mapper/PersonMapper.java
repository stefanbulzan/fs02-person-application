package org.fasttrackit.person.server.model.mapper;

import org.fasttrackit.person.model.Person;
import org.fasttrackit.person.server.entity.PersonEntity;
import org.springframework.stereotype.Component;

@Component
public class PersonMapper implements ModelMapper<Person, PersonEntity> {
    public Person toApi(PersonEntity source) {
        return Person.builder()
                .id(source.getId())
                .name(source.getNewName())
                .age(source.getAge())
                .build();
    }

    public PersonEntity toEntity(Person source) {
        return PersonEntity.builder()
                .id(source.id())
                .newName(source.name())
                .age(source.age())
                .build();
    }
}
