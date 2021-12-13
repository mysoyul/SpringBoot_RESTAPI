package metanet.springboot.restapi.events;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class EventTest {
    @Test
    public void builder() {
        Event event = Event.builder()
                .name("Spring RESTAPI")
                .description("SpringBoot로 개발하는 REST API")
                .build();
        assertThat(event).isNotNull();
    }

    @Test
    public void javaBean() {
        String name = "Event";
        String description = "Spring Boot";

        Event event = new Event();
        event.setName(name);
        event .setDescription(description);

        assertThat(event.getName()).isEqualTo("Event");
        assertThat(event.getDescription()).isEqualTo("Spring Boot");
    }

}