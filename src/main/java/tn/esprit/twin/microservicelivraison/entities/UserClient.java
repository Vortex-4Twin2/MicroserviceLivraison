package tn.esprit.twin.microservicelivraison.entities;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tn.esprit.twin.microservicelivraison.Config.FeignConfig;
import tn.esprit.twin.microservicelivraison.dto.UserDTO;

@FeignClient(
        name = "UserMS",
        configuration = FeignConfig.class
)
public interface UserClient {

    @GetMapping("/users/{id}")
    UserDTO getUserById(@PathVariable("id") Long id);
}
