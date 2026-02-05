package org.acme.infrastructure.adapters.out.database.entities.question;

import jakarta.persistence.*;

@Entity
@Table(name = "question_options")
public class OptionQuestionEntity {

    @Id
    public String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    public QuestionEntity question;

    @Column
    public String text;
}