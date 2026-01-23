package org.acme.domain.models.question;

import java.util.List;
import java.util.Map;

public class ClosedQuestion extends Question {
    private List<OptionQuestion> options;
    private String correctOptionId;
    private String correctExplanation;
    private Map<String, String> wrongExplanations;

    public List<OptionQuestion> getOptions() {
        return this.options;
    }

    public String getCorrectOptionId() {
        return this.correctOptionId;
    }

    public String getCorrectExplanation() {
        return this.correctExplanation;
    }

    public Map<String, String> getWrongExplanations() {
        return this.wrongExplanations;
    }

    public void setOptions(List<OptionQuestion> options) {
        this.options = options;
    }

    public void setCorrectOptionId(String correctOptionId) {
        this.correctOptionId = correctOptionId;
    }

    public void setCorrectExplanation(String correctExplanation) {
        this.correctExplanation = correctExplanation;
    }

    public void setWrongExplanations(Map<String, String> wrongExplanations) {
        this.wrongExplanations = wrongExplanations;
    }

    
}