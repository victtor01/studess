package org.acme.infrastructure.adapters.out.database.entities.question;

import jakarta.persistence.*;
import org.acme.domain.enums.QuestionType;
import org.acme.infrastructure.adapters.out.database.entities.proof.ProofEntity;

@Entity
@Table(name = "questions")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
public abstract class QuestionEntity {
    @Id
    public String id;

    @Enumerated(EnumType.STRING)
    @Column(insertable = false, updatable = false)
    public QuestionType type;

    public String statement;

    @Column(name = "max_score")
    public int maxScore;

    @ManyToOne
    ProofEntity proof;

    @Embedded
    public QuestionSourceEmbeddable source;
}