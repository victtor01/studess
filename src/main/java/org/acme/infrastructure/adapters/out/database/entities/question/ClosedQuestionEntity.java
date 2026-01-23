package org.acme.infrastructure.adapters.out.database.entities.question;

import java.util.List;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
@DiscriminatorValue("CLOSED")
public class ClosedQuestionEntity extends QuestionEntity {

    @Column(name = "correct_option_id")
    public String correctOptionId;

    @Column(columnDefinition = "TEXT")
    public String correctExplanation;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "wrong_explanations", columnDefinition = "jsonb")
    public String wrongExplanations;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<OptionQuestionEntity> options;
}
