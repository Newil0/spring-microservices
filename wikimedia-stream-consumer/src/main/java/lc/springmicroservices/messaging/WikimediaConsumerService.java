package lc.springmicroservices.messaging;

import lc.springmicroservices.repository.WikimediaRepository;
import lc.springmicroservices.repository.entity.WikimediaData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class WikimediaConsumerService {

    private final WikimediaRepository repository;

    @KafkaListener(topics = "${app.kafka.topic.wikimedia}", groupId = "my-group")
    public void consume(String message) {
        try {
            log.info("Received message: {}", message);
            WikimediaData data = new WikimediaData(message);
            repository.save(data);
        } catch (Exception e) {
            log.error("Error processing message: {}", e.getMessage());
        }
    }
}
