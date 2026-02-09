package lc.springmicroservices;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WikimediaStreamConsumer {
    public static void main(String[] args) {
//        https://stream.wikimedia.org/v2/stream/recentchange
        SpringApplication.run(WikimediaStreamConsumer.class, args);
    }
}