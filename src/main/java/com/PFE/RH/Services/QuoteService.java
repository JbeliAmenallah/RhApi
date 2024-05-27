// QuoteService.java
package com.PFE.RH.Services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class QuoteService {

    private final RestTemplate restTemplate;

    public QuoteService() {
        this.restTemplate = new RestTemplate();
    }

    public String getQuotes() {
        String apiUrl = "https://zenquotes.io/api/quotes";
        return restTemplate.getForObject(apiUrl, String.class);
    }
}
