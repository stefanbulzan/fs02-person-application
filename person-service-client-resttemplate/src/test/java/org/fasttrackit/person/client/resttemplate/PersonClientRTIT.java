package org.fasttrackit.person.client.resttemplate;

import org.fasttrackit.person.client.resttemplate.config.PersonClientRTConfig;
import org.fasttrackit.person.model.Person;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

@Disabled
class PersonClientRTIT {
    private final PersonClientRT client = new PersonClientRT(new PersonClientRTConfig("http://localhost:8080"));

    @Test
    void getOnePerson() {
        System.out.println(client.persons()
                .get(2));

        List<Person> result = client.persons().get();
        System.out.println(result);
    }
}