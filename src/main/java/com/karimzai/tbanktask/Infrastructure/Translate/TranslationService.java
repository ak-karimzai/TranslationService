package com.karimzai.tbanktask.Infrastructure.Translate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.karimzai.tbanktask.Application.Contracts.Infrastructure.ITranslationService;

import com.karimzai.tbanktask.Application.Exceptions.ServiceUnavailableException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Component
public class TranslationService implements ITranslationService {
    private RestTemplate restTemplate;

    @Value("${rapidapi.key}")
    private String rapidApiKey;
    @Value("${rapidapi.baseUrl}")
    private String tranlationEndpoint;
    @Value("${rapidapi.languageUrl}")
    private String languagesEndpoint;

    private TreeMap<String, String> availableLanguages;

    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    public TranslationService(RestTemplate restTemplate) throws ServiceUnavailableException {
        this.restTemplate = restTemplate;
        availableLanguages = new TreeMap<>();
    }

    @Override
    public String translate(String text, String srcLang, String destLang) throws ServiceUnavailableException {
        String[] words = text.split("\\s+");
        List<Future<String>> futures = new ArrayList<>();

        for (String word : words) {
            try {
                futures.add(executorService.submit(() -> translateWord(word, srcLang, destLang)));
            } catch (ResourceAccessException e) {
                throw new ServiceUnavailableException("third party service unavalible.");
            }
        }

        StringBuilder translatedString = new StringBuilder();
        for (Future<String> future : futures) {
            try {
                translatedString.append(future.get()).append(" ");
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }

        return translatedString.toString().trim();
    }

    private String translateWord(String text, String srcLang, String destLang) throws ServiceUnavailableException {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(tranlationEndpoint)
                .queryParam("q", text)
                .queryParam("source", srcLang)
                .queryParam("target", destLang);

        URI uri = builder.build().toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-rapidapi-host", "google-translator9.p.rapidapi.com");
        headers.set("x-rapidapi-key", rapidApiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);
        String responseBody = response.getBody();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = null;
        try {
            root = mapper.readTree(responseBody);
        } catch (JsonProcessingException e) {
            throw new ServiceUnavailableException("Failed to parse response");
        }
        JsonNode translations = root.path("data").get("translations");

        if (translations.isArray() && !translations.isEmpty()) {
            String translatedText = translations.get(0).path("translatedText").asText();
            return URLDecoder.decode(translatedText, StandardCharsets.UTF_8);
        }
        return "";
    }

    @Override
    public boolean existLanguage(String lang) throws ServiceUnavailableException {
        if (availableLanguages.isEmpty()) {
            fetchAvailableLanguages();
        }
        return availableLanguages.containsKey(lang);
    }

    private void fetchAvailableLanguages() throws ServiceUnavailableException {
        try {
            this.availableLanguages = getSupportedLanguages();
        } catch (Exception e) {
            throw new ServiceUnavailableException("cannot fetch available language list");
        }
    }
    private TreeMap<String, String> getSupportedLanguages() throws ServiceUnavailableException {
        String target = "en";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(languagesEndpoint)
                .queryParam("target", target);

        URI uri = builder.build().toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-rapidapi-host", "google-translator9.p.rapidapi.com");
        headers.set("x-rapidapi-key", rapidApiKey);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
        String responseBody = response.getBody();
        if (response.getStatusCode() == HttpStatus.OK) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode rootNode = mapper.readTree(response.getBody());
                JsonNode languagesNode = rootNode.path("data").path("languages");

                TreeMap<String, String> languagesMap = new TreeMap<>();
                for (JsonNode languageNode : languagesNode) {
                    languagesMap.put(languageNode.path("language").asText(),
                            languageNode.path("name").asText());
                }

                return languagesMap;
            } catch (IOException e) {
                throw new ServiceUnavailableException("cannot send request to server.");
            }
        } else {
            throw new ServiceUnavailableException("cannot load supported languages.");
        }
    }
}
