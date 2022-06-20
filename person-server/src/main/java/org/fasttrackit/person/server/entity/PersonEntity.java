package org.fasttrackit.person.server.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@With
@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class PersonEntity {
    @Id
    @GeneratedValue
    private int id;

    private String newName;
    private int age;

    public PersonEntity(String newName, int age) {
        this(0, newName, age);
    }
}
