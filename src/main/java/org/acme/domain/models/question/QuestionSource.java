package org.acme.domain.models.question;

public class QuestionSource {
    String documentId;
    int startPage;
    int endPage;
    String excerpt; // trecho relevante do PDF

    public String getDocumentId() {
        return this.documentId;
    }

    public int getStartPage() {
        return this.startPage;
    }

    public int getEndPage() {
        return this.endPage;
    }

    public String getExcerpt() {
        return this.excerpt;
    }
}