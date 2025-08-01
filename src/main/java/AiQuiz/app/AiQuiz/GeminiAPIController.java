package AiQuiz.app.AiQuiz;

import AiQuiz.app.AiQuiz.Model.GeminiAPIRequest;
import AiQuiz.app.AiQuiz.Model.GeminiAPIResponse;
import AiQuiz.app.AiQuiz.Model.QuestionDetails;
import AiQuiz.app.AiQuiz.Model.QuizDetailsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GeminiAPIController {

    @Autowired
    GeminiAPIService geminiAPIService;
    @PostMapping("/getApiResponse")
    private List<QuestionDetails> getGeminiResponse(@RequestBody QuizDetailsRequest quizDetailsRequest){

        return geminiAPIService.processGeminiAPI(quizDetailsRequest);
    }
}
