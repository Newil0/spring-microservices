package lc.springmicroservices.messaging;

import lc.springmicroservices.repository.WikimediaRepository;
import lc.springmicroservices.repository.entity.WikimediaData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;

import java.time.Duration;
import java.util.List;

import static org.awaitility.Awaitility.await;

/**
 * For viewing H2 db during the test, put Thread.sleep() with however many seconds you want at the end of the test method
 * (or wherever you want to pause the test),
 * then connect to H2 console at http://localhost:8080/h2-console with config from the application.yml
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@EmbeddedKafka(
        partitions = 1,
        topics = {"wikimedia.recentchange"}
)
class WikimediaConsumerServiceTest {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private WikimediaRepository repository;

    @Test
    void testConsumeSavesToRepository() throws InterruptedException {
        String mockPayload = "{\"event\": \"edit\", \"user\": \"test-user\"}";

        kafkaTemplate.send("wikimedia.recentchange", mockPayload);

        await()
            .atMost(Duration.ofSeconds(5))
            .pollInterval(Duration.ofMillis(500))
            .until(() -> repository.count() > 0);

        List<WikimediaData> data = repository.findAll();
        assert data.size() == 1;
        assert data.get(0).getEventData().equals(mockPayload);
    }
}