package tn.esprit.twin.microservicelivraison;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MicroServiceLivraisonApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroServiceLivraisonApplication.class, args);
    }

}
