package lc.springmicroservices.service;

import jakarta.annotation.PostConstruct;
import lc.springmicroservices.messaging.ResilientKafkaProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.Disposable;
import reactor.util.retry.Retry;

import java.time.Duration;

@Service
@Slf4j
public class WikimediaConsumerService {

    private final WebClient webClient;
    private final ResilientKafkaProducer producer;

    public WikimediaConsumerService(
            @Value("${app.wikimedia.stream-url}") String streamUrl,
            ResilientKafkaProducer producer) {
        webClient = WebClient.create(streamUrl);
        this.producer = producer;
    }


    @EventListener(ApplicationReadyEvent.class)
     public void consumeStream() {
         webClient.get()
                 .retrieve()
                 .bodyToFlux(String.class)
                 .retryWhen(Retry.backoff(10, Duration.ofSeconds(2)))
                 .subscribe(
                         data -> {
                             System.out.println(data);
                             producer.send("wikimedia.recentchange", data);
                         },
                         error -> System.err.println("Error consuming stream: " + error.getMessage())
                 );

         System.out.println("Stream consumption started...");
     }

}
