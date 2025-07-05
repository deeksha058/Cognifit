package AiQuiz.app.AiQuiz.Model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class QuizDetailsRequest {

    String levelOfQuiz; // Advance, beginner, moderate

    String topicName; // forExample Java Question

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public QuizDetailsRequest(String levelOfQuiz, String topicName) {
        this.levelOfQuiz = levelOfQuiz;
        this.topicName = topicName;
    }

    public String getLevelOfQuiz() {
        return levelOfQuiz;
    }

    public void setLevelOfQuiz(String levelOfQuiz) {
        this.levelOfQuiz = levelOfQuiz;
    }
}
