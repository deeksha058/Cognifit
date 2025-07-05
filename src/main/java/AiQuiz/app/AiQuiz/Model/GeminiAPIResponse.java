package AiQuiz.app.AiQuiz.Model;

import java.util.List;

public class GeminiAPIResponse {
    List<Candidate> candidates;
    public List<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<Candidate> candidates) {
        this.candidates = candidates;
    }
}
