package lc.springmicroservices.messaging;

import lc.springmicroservices.repository.WikimediaRepository;
import lc.springmicroservices.repository.entity.WikimediaData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import static org.awaitility.Awaitility.await;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ActiveProfiles("test")
@EmbeddedKafka(
        partitions = 1,
        brokerProperties = { "listeners=PLAINTEXT://localhost:5432", "port=5432" },
        topics = {"wikimedia.recentchange"}
)
class WikimediaConsumerServiceTest {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private WikimediaRepository repository;

    @Test
    void testConsumeSavesToRepository() throws InterruptedException {
        // 1. Arrange: Prepare the test data
        String mockPayload = "{\"event\": \"edit\", \"user\": \"test-user\"}";

        // 2. Act: Send a message to the topic the listener is watching
        kafkaTemplate.send("wikimedia.recentchange", mockPayload);

        // 3. Assert: Use Awaitility to wait for the async process to complete
        // Since Kafka is async, standard verify() might fail if it checks too early.
        await()
            .atMost(Duration.ofSeconds(5))
            .pollInterval(Duration.ofMillis(500))
            .until(() -> repository.count() > 0);

        List<WikimediaData> data = repository.findAll();
        System.out.println("Data in repository: " + data.get(0));
        assert data.size() == 1;
        assert data.get(0).getEventData().equals(mockPayload);

    }
}