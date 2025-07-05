package AiQuiz.app.AiQuiz.Model;

import java.util.List;

public class GeminiAPIRequest {
    private List<Content> contents;
    public List<Content> getContents() {
        return contents;
    }

    @Override
    public String toString() {
        return "GeminiAPIRequest{" +
                "contents=" + contents +
                '}';
    }

    public GeminiAPIRequest(List<Content> contents) {
        this.contents = contents;
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
    }

}
