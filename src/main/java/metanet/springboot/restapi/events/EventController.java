package metanet.springboot.restapi.events;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;

@Controller
@RequestMapping(value = "/api/events", produces = MediaTypes.HAL_JSON_VALUE)
@RequiredArgsConstructor
public class EventController {

    private final EventRepository eventRepository;

    @PostMapping
    public ResponseEntity<?> createEvent(@RequestBody Event event) {
        Event addEvent = eventRepository.save(event);

        //http://localhost:8087/api/events/10
        WebMvcLinkBuilder selfLinkBuilder = WebMvcLinkBuilder.linkTo(EventController.class).slash(event.getId());
        URI createdUri = selfLinkBuilder.toUri();
        return ResponseEntity.created(createdUri).body(addEvent);
    }
}
