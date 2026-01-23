package org.acme.domain.models.question;

import org.acme.domain.enums.QuestionType;

public abstract class Question {
    protected String id;
    protected QuestionType type;
    protected String statement;
    protected int maxScore;
    protected QuestionSource source; 

    public String getId() {
        return this.id;
    }

    public QuestionType getType() {
        return this.type;
    }

    public String getStatement() {
        return this.statement;
    }

    public int getMaxScore() {
        return this.maxScore;
    }

    public QuestionSource getSource() {
        return this.source;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    public void setSource(QuestionSource source) {
        this.source = source;
    }
}