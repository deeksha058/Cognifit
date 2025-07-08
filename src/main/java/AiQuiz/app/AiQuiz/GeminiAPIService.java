package AiQuiz.app.AiQuiz;

import AiQuiz.app.AiQuiz.Model.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class GeminiAPIService {

    @Autowired
    Environment env;

    public List<QuestionDetails> processGeminiAPI(QuizDetailsRequest quizDetailsRequest) {
        try {

//            String text = "Generate a multiple-choice quiz with exactly 5 questions on the topic of "
//                    + quizDetailsRequest.getTopicName()
//                    + ", targeted at " + quizDetailsRequest.getLevelOfQuiz()
//                    + " level developers. Format each question exactly like this:\n\n"
//                    + "<question> <question text> </question>\n"
//                    + "<option>a) Option A</option>\n"
//                    + "<option>b) Option B</option>\n"
//                    + "<option>c) Option C</option>\n"
//                    + "<option>d) Option D</option>\n"
//                    + "<answer> <correct option letter> </answer>\n\n"
//                    + "Only use the tags <question>, <option>, and <answer> as shown above. Do not include extra text or formatting.";

            String text = "Generate a multiple-choice quiz with exactly 5 questions on the topic of "
                    + quizDetailsRequest.getTopicName()
                    + ", targeted at " + quizDetailsRequest.getLevelOfQuiz()
                    + " level developers. Return the result strictly as a valid JSON array of objects, where each object has the following fields:\n\n"
                    + "{\n"
                    + "  \"question\": \"<question text>\",\n"
                    + "  \"options\": [\"Option A\", \"Option B\", \"Option C\", \"Option D\"],\n"
                    + "  \"answer\": \"a\"  // the correct option's letter: a, b, c, or d\n"
                    + "}\n\n"
                    + "Do not include any extra text or explanations. Only return the JSON array.";

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

            GeminiAPIResponse responseBody = response.getBody();
            assert responseBody != null;
            String rawResponse = responseBody.getCandidates().get(0).getContent().getParts().get(0).getText().trim();

            // Remove trailing ``` if present
            if (rawResponse.endsWith("```")) {
                rawResponse = rawResponse.substring(0, rawResponse.length() - 3).trim();
            }
            if (rawResponse.startsWith("```json")) {
                rawResponse = rawResponse.substring(7).trim();  // removes "```json"
            } else if (rawResponse.startsWith("```")) {
                rawResponse = rawResponse.substring(3).trim();  // removes "```"
            }

            ObjectMapper objectMapper1 = new ObjectMapper();
            List<QuestionDetails> questions = objectMapper1.readValue(rawResponse, new TypeReference<List<QuestionDetails>>() {});

            return questions;
//            return extractQuestionsFromResponse(apiResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<QuestionDetails> extractQuestionsFromResponse(String apiResponse) {
        List<QuestionDetails> questionDetailsList = new ArrayList<>();

        // Match each full question block (1 question, 4 options, 1 answer)
        Pattern fullBlockPattern = Pattern.compile(
                "<question>(.*?)</question>\\s*" +
                        "<option>(.*?)</option>\\s*" +
                        "<option>(.*?)</option>\\s*" +
                        "<option>(.*?)</option>\\s*" +
                        "<option>(.*?)</option>\\s*" +
                        "<answer>\\s*([a-dA-D])\\s*</answer>",
                Pattern.DOTALL
        );

        Matcher matcher = fullBlockPattern.matcher(apiResponse);

        while (matcher.find()) {
            QuestionDetails qd = new QuestionDetails();

            // Extract question text
            String questionText = matcher.group(1).trim();

            // Extract all options into a list
            List<String> options = new ArrayList<>();
            options.add(matcher.group(2).trim());
            options.add(matcher.group(3).trim());
            options.add(matcher.group(4).trim());
            options.add(matcher.group(5).trim());

            String answer = matcher.group(6).trim();

            // Set to model
            qd.setQuestion(questionText);
            qd.setOptions(options); // assuming "answer" holds all options
            qd.setCorrectAnswer(answer);
            questionDetailsList.add(qd);
        }

        return questionDetailsList;
    }

    private String getTopHeadlineURL() {
        return env.getProperty("NEWS_BASE_URL") + "/v1beta/models/gemini-2.0-flash:generateContent";
    }
}
