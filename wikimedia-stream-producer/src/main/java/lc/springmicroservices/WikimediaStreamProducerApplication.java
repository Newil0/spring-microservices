package lc.springmicroservices;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WikimediaStreamProducerApplication {
    public static void main(String[] args) {
//        https://stream.wikimedia.org/v2/stream/recentchange
        SpringApplication.run(WikimediaStreamProducerApplication.class, args);
    }
}