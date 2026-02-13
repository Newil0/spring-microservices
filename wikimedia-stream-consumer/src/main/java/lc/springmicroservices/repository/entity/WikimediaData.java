package lc.springmicroservices.repository.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

@Entity
@Table(name = "wikimedia_data")
@Getter
@Setter
public class WikimediaData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String eventData;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime createTimeStamp = OffsetDateTime.now();

    public WikimediaData() {
    }

    public WikimediaData(String message) {
        setEventData(message);
    }

    private void setEventData(String eventData) {
        this.eventData = eventData != null && eventData.length() > 255 ? eventData.substring(0, 255) : eventData;
    }
}
