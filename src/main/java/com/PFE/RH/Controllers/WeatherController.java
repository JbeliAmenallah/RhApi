package com.PFE.RH.Controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class WeatherController {

    @Value("${openweathermap.api.key}")
    private String apiKey;

    private final String apiUrl = "https://api.openweathermap.org/data/2.5/weather";

    @GetMapping("/weather")
    public ResponseEntity<?> getWeather(@RequestParam("lat") String lat,
                                        @RequestParam("lon") String lon) {
        String url = apiUrl + "?lat=" + lat + "&lon=" + lon + "&appid=" + apiKey;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return ResponseEntity.ok().body(response.getBody());
    }
}
