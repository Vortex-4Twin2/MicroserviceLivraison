package tn.esprit.twin.microservicelivraison.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import tn.esprit.twin.microservicelivraison.dto.LivraisonEventDTO;

@Service
@RequiredArgsConstructor
public class LivraisonProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String TOPIC = "livraison_livree";

    public void sendLivraison(LivraisonEventDTO dto) {
        try {
            String json = objectMapper.writeValueAsString(dto);
            kafkaTemplate.send(TOPIC, objectMapper.writeValueAsString(dto));

            System.out.println("Message envoyé à Kafka : " + json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}