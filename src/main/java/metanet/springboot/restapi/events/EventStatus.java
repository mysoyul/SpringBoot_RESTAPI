package metanet.springboot.restapi.events;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EventStatus {
    //@JsonProperty("DRAFT")
    DRAFT("DRAFT"),
    //@JsonProperty("PUBLISHED")
    PUBLISHED("PUBLISHED"),
    //@JsonProperty("BEGAN_ENROLLMENT")
    BEGAN_ENROLLMENT("BEGAN_ENROLLMENT");

    private String displayName;

    EventStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}