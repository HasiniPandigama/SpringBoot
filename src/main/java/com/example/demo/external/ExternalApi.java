package com.example.demo.external;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Component
@RestController
public class ExternalApi {
    @Autowired
    private RestTemplate template;

    @Value("${countries.url}")
    private String countriesUrl;

    public String getCountriesUrl() {
        return countriesUrl;
    }

    public void setCountriesUrl(String countriesUrl) {
        this.countriesUrl = countriesUrl;
    }

    @GetMapping("/restCountry")
    public ResponseEntity<?> getCountrys() {
        try {
            String url=countriesUrl;
            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.getForObject(url, String.class);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Error!, Please try again", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
