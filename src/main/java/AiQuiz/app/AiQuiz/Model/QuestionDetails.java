package AiQuiz.app.AiQuiz.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class QuestionDetails {

    private String question;

    public List<String> getOptions() {
        return options;
    }

    public QuestionDetails(String question, List<String> options, String correctAnswer) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    // This maps directly to "options" in the JSON
    private List<String> options;

    // This maps the "answer" field from JSON to this Java field
    @JsonProperty("answer")
    private String correctAnswer;

    public QuestionDetails() {
    }
}
