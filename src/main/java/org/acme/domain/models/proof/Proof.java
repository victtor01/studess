package org.acme.domain.models.proof;

import java.util.List;

import org.acme.domain.models.question.Question;

public class Proof {
    private String id;
    private String title;
    private String description;

    private  String sourceDocumentId; // PDF ou conjunto de PDFs
    private List<Question> questions;

    private ProofMetadata metadata;

    public String getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public String getSourceDocumentId() {
        return this.sourceDocumentId;
    }

    public List<Question> getQuestions() {
        return this.questions;
    }

    public ProofMetadata getMetadata() {
        return this.metadata;
    }
}
