package tn.esprit.twin.microservicelivraison.service;


import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import tn.esprit.twin.microservicelivraison.Config.RabbitMQConfig;
import tn.esprit.twin.microservicelivraison.dto.CommandeDTO;

@Service
@RequiredArgsConstructor
public class CommandeConsumer {

    private final LivraisonService livraisonService;
    private static final Logger log = LoggerFactory.getLogger(CommandeConsumer.class);

    @RabbitListener(queues = RabbitMQConfig.COMMANDE_QUEUE)
    public void consumeCommande(CommandeDTO commandeDTO) {

        log.info("Commande reçue depuis RabbitMQ : {}", commandeDTO);

        livraisonService.createLivraisonFromCommande(commandeDTO);

        log.info("Livraison créée automatiquement pour commande : {}", commandeDTO.getId());
    }
}