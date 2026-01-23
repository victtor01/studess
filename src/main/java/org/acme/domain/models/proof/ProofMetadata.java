package org.acme.domain.models.proof;

import java.time.Instant;

public class ProofMetadata {
    private String modelVersion;
    private String generationPromptId;
    private Instant generatedAt;

    public String getModelVersion() {
        return this.modelVersion;
    }

    public String getGenerationPromptId() {
        return this.generationPromptId;
    }

    public Instant getGeneratedAt() {
        return this.generatedAt;
    }
}