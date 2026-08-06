package com.shubham.employeemanagementsystem.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shubham.employeemanagementsystem.dto.EmployeeSearchCriteria;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public EmployeeSearchCriteria parseQueryToCriteria(String naturalLanguageQuery) {
        String prompt = buildPrompt(naturalLanguageQuery);

        Map<String, Object> requestBody = Map.of(
                "contents", new Object[]{
                        Map.of("parts", new Object[]{Map.of("text", prompt)})
                },
                "generationConfig", Map.of(
                        "temperature", 0,
                        "responseMimeType", "application/json"
                )
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
        String url = apiUrl + "?key=" + apiKey;

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
            String rawJsonText = extractTextFromGeminiResponse(response.getBody());
            return objectMapper.readValue(rawJsonText, EmployeeSearchCriteria.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse AI response into search criteria: " + e.getMessage(), e);
        }
    }

    private String extractTextFromGeminiResponse(String responseBody) throws Exception {
        JsonNode root = objectMapper.readTree(responseBody);
        return root.path("candidates").get(0)
                .path("content").path("parts").get(0)
                .path("text").asText();
    }

    private String buildPrompt(String userQuery) {
        return """
                You are a query parser for an employee database. Convert the user's natural
                language request into a JSON object matching exactly this schema:

                {
                  "department": string or null,
                  "firstName": string or null,
                  "lastName": string or null,
                  "minSalary": number or null,
                  "maxSalary": number or null
                }

                Rules:
                - Only include fields the user actually mentioned; leave others null.
                - Return ONLY the JSON object, no explanation, no markdown formatting.

                User query: "%s"
                """.formatted(userQuery);
    }
}