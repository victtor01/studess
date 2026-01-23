package org.acme.infrastructure.adapters.out.database.entities.question;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class QuestionSourceEmbeddable {
    public String documentId;
    public int startPage;
    public int endPage;
    
    @Column(columnDefinition = "TEXT")
    public String excerpt;
}