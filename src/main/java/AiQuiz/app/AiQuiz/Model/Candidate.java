package AiQuiz.app.AiQuiz.Model;

import lombok.Data;

@Data
public class Candidate {

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    Content content;

}
