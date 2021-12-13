package metanet.springboot.restapi.events;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of="id")
@Entity
public class Event {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime beginEnrollmentDateTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime closeEnrollmentDateTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime beginEventDateTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime endEventDateTime;

    private String location;
    private int basePrice;
    private int maxPrice;
    private int limitOfEnrollment;
    private boolean offline;
    private boolean free;
    @Enumerated(EnumType.STRING)
    private EventStatus eventStatus = EventStatus.DRAFT;
}