package org.acme.domain.models.question;

import java.util.Map;

public class EvaluationRubric {
    private Map<String, Integer> conceptWeights; // conceito -> pontos

    public Map<String, Integer> getConceptWeights() {
        return this.conceptWeights;
    }
}
