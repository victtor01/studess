package org.acme.infrastructure.adapters.out.database.entities.question;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("OPEN")
public class OpenQuestionEntity extends QuestionEntity {

    @Column(name = "reference_concepts", columnDefinition = "jsonb")
    public String referenceConcepts;

    @Column(name = "model_answer", columnDefinition = "TEXT")
    public String modelAnswer;

    @Column(columnDefinition = "jsonb")
    public String rubric;
}
