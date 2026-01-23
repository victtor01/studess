package org.acme.infrastructure.adapters.out.database.entities.proof;

import java.time.Instant;
import java.util.List;

import org.acme.infrastructure.adapters.out.database.entities.question.QuestionEntity;
import org.hibernate.annotations.CreationTimestamp;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name = "proofs")
public class ProofEntity extends PanacheEntityBase {
    @Id
    public String id;

    @Column
    public String title;
    
    @Column
    public String description;

    @Column(name = "source_document_id")
    public String sourceDocumentId;

    @CreationTimestamp
    public Instant createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proof_id")
    public ProofEntity proof;

    @OneToMany(mappedBy = "proof", cascade = CascadeType.ALL)
    public List<QuestionEntity> questions;
}