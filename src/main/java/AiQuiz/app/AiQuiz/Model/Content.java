package AiQuiz.app.AiQuiz.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class Content {

    private List<Part> parts;

    public Content(List<Part> parts) {
        this.parts = parts;
    }

    public List<Part> getParts() {
        return parts;
    }

    public void setParts(List<Part> parts) {
        this.parts = parts;
    }
}
