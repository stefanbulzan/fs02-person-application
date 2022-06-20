package org.fasttrackit.person.client.resttemplate;

import lombok.RequiredArgsConstructor;
import org.fasttrackit.person.client.resttemplate.config.PersonClientRTConfig;
import org.fasttrackit.person.model.Person;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PersonClientRT {
    private final PersonClientRTConfig config;


    public PersonClient persons() {
        return new PersonClient(config.location());
    }

    public class PersonClient {
        private final String url;

        public PersonClient(String location) {
            this.url = UriComponentsBuilder.fromHttpUrl(location)
                    .pathSegment("persons")
                    .toUriString();
        }

        public Optional<Person> get(int personId) {
            var url = UriComponentsBuilder.fromHttpUrl(this.url)
                    .pathSegment(String.valueOf(personId))
                    .toUriString();
            return Optional.ofNullable(new RestTemplate().getForObject(url, Person.class));
        }

        public List<Person> get() {
            return new RestTemplate()
                    .exchange(this.url, HttpMethod.GET, new HttpEntity<>(null), new ParameterizedTypeReference<List<Person>>() {
                    })
                    .getBody();
        }
    }
}
