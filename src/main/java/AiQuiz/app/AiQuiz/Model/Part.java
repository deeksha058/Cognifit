package AiQuiz.app.AiQuiz.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Part {
    public String getText() {
        return text;
    }

    public Part(String text) {
        this.text = text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @JsonProperty("text")
    String text;
}
