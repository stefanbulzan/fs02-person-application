package org.fasttrackit.person.model;

import lombok.Builder;

@Builder
public record Person(int id, String name, int age) {
}
