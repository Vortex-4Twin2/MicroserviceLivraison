package tn.esprit.twin.microservicelivraison.service;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class WeatherService {

    private final String apiKey;

    public WeatherService() {
        Dotenv dotenv = Dotenv.load();
        this.apiKey = dotenv.get("WEATHER_API_KEY");
    }

    public String getWeather(String ville) {

        try {
            String url = "https://api.openweathermap.org/data/2.5/weather?q="
                    + ville + "&appid=" + apiKey;

            RestTemplate restTemplate = new RestTemplate();
            Map response = restTemplate.getForObject(url, Map.class);

            List weatherList = (List) response.get("weather");
            Map weather = (Map) weatherList.get(0);

            return weather.get("main").toString();

        } catch (Exception e) {
            System.out.println("❌ Weather error: " + e.getMessage());
            return "UNKNOWN";
        }
    }
}