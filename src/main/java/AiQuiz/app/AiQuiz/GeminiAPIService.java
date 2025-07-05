package AiQuiz.app.AiQuiz;

import AiQuiz.app.AiQuiz.Model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class GeminiAPIService {

    @Autowired
    Environment env;

    public GeminiAPIResponse processGeminiAPI(QuizDetailsRequest quizDetailsRequest) {
        try {

            String text = "Generate a multiple-choice quiz with 5 " + quizDetailsRequest.getTopicName() + " questions suitable for " + quizDetailsRequest.getLevelOfQuiz() + " developers. Each question should include 4 options and highlight the correct answer.";

            Part part = new Part(text);
            Content content = new Content(List.of(part));
            GeminiAPIRequest geminiAPIRequest = new GeminiAPIRequest(List.of(content));

            RestTemplate restTemplate = new RestTemplate();

            // Create headers and set API key
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-goog-api-key",env.getProperty("GEMINI_API_KEY"));
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Convert request body to JSON
            ObjectMapper objectMapper = new ObjectMapper();
            String requestBody = objectMapper.writeValueAsString(geminiAPIRequest);

            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

            String url = getTopHeadlineURL();
            ResponseEntity<GeminiAPIResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    GeminiAPIResponse.class
            );

            return response.getBody();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getTopHeadlineURL() {
        return env.getProperty("NEWS_BASE_URL") + "/v1beta/models/gemini-2.0-flash:generateContent";
    }
}
