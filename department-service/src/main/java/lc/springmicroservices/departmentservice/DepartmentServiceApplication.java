package lc.springmicroservices.departmentservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@SpringBootApplication
@EnableDiscoveryClient
public class DepartmentServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(DepartmentServiceApplication.class, args);
        WebClient webClient = WebClient.create("website.com");
        Mono<String> response = webClient.get()
                .uri("/endpoint")
                .retrieve()
                .bodyToMono(String.class);
        response.block();
    }
}