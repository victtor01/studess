package org.acme.domain.models.question;

import java.util.List;

public class OpenQuestion extends Question {
    List<String> referenceConcepts; // conceitos esperados
    String modelAnswer; // resposta modelo (não única)
    EvaluationRubric rubric;

    public List<String> getReferenceConcepts() {
        return this.referenceConcepts;
    }

    public String getModelAnswer() {
        return this.modelAnswer;
    }

    public EvaluationRubric getRubric() {
        return this.rubric;
    }

    public void setReferenceConcepts(List<String> referenceConcepts) {
        this.referenceConcepts = referenceConcepts;
    }

    public void setModelAnswer(String modelAnswer) {
        this.modelAnswer = modelAnswer;
    }

    public void setRubric(EvaluationRubric rubric) {
        this.rubric = rubric;
    }
}