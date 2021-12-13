package metanet.springboot.restapi.events;

import lombok.RequiredArgsConstructor;
import metanet.springboot.restapi.common.ErrorsResource;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Controller
@RequestMapping(value = "/api/events", produces = MediaTypes.HAL_JSON_VALUE)
@RequiredArgsConstructor
public class EventController {

    private final EventRepository eventRepository;
    private final ModelMapper modelMapper;
    private final EventValidator eventValidator;

    @GetMapping
    public ResponseEntity queryEvents(Pageable pageable) {
        return ResponseEntity.ok(this.eventRepository.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<?> createEvent(@RequestBody @Valid EventDto eventDto, Errors errors) {
        //입력항목 검증 오류가 발생했다면
        if(errors.hasErrors()) {
            return badRequest(errors);
        }

        //입력항목에 로직의 검증 오류가 발생했다면
        eventValidator.validate(eventDto, errors);
        if(errors.hasErrors()) {
            return badRequest(errors);
        }

        //EventDto -> Event 로 매핑
        Event event = modelMapper.map(eventDto, Event.class);

        event.update();
        Event addEvent = eventRepository.save(event);

        //http://localhost:8087/api/events/10
        WebMvcLinkBuilder selfLinkBuilder = linkTo(EventController.class).slash(event.getId());
        URI createdUri = selfLinkBuilder.toUri();

        EventResource eventResource = new EventResource(addEvent);
        eventResource.add(linkTo(EventController.class).withRel("query-events"));
        //eventResource.add(selfLinkBuilder.withSelfRel());
        eventResource.add(selfLinkBuilder.withRel("update-event"));
        return ResponseEntity.created(createdUri).body(eventResource);
    }

    private ResponseEntity<?> badRequest(Errors errors) {
        return ResponseEntity.badRequest().body(new ErrorsResource(errors));//build();
    }
}
