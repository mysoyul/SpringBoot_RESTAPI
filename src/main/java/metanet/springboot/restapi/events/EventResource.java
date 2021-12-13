package metanet.springboot.restapi.events;

import org.springframework.hateoas.RepresentationModel;

public class EventResource extends RepresentationModel<EventResource> {
    private Event event;

    public EventResource(Event event) {
        this.event = event;
    }

    public Event getEvent() {
        return event;
    }
}
