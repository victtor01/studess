package org.acme.infrastructure.adapters.out.database.entities.question;

import org.acme.domain.enums.QuestionType;
import org.acme.infrastructure.adapters.out.database.entities.proof.ProofEntity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

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