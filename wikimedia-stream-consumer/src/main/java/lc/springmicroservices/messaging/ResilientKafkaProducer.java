package lc.springmicroservices.messaging;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.decorators.Decorators;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ResilientKafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final CircuitBreaker circuitBreaker;

    public ResilientKafkaProducer(KafkaTemplate<String, String> kafkaTemplate, CircuitBreakerRegistry registry) {
        this.kafkaTemplate = kafkaTemplate;
        this.circuitBreaker = registry.circuitBreaker("kafka-producer");
    }

    public void send(String topic, String message) {
        Decorators.ofRunnable(() -> {
            kafkaTemplate.send(topic, message);
        })
        .withCircuitBreaker(circuitBreaker)
        .run();
        KafkaAutoConfiguration

    }
}
