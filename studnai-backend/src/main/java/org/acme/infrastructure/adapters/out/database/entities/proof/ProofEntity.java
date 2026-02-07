package org.acme.infrastructure.adapters.out.database.entities.proof;

import java.time.Instant;
import java.util.List;

import org.acme.infrastructure.adapters.out.database.entities.files.DocumentEntity;
import org.acme.infrastructure.adapters.out.database.entities.files.FolderEntity;
import org.acme.infrastructure.adapters.out.database.entities.question.QuestionEntity;
import org.hibernate.annotations.CreationTimestamp;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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

    @ManyToMany
    @JoinTable(
        name = "proof_source_documents",
        joinColumns = @JoinColumn(name = "proof_id"),
        inverseJoinColumns = @JoinColumn(name = "document_id")
    )
    public List<DocumentEntity> sourceDocuments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "folder_id")
    public FolderEntity folder;

    @CreationTimestamp
    public Instant createdAt;

    @OneToMany(mappedBy = "proof", cascade = CascadeType.ALL)
    public List<QuestionEntity> questions;

    @Column
    public Integer studentScore;
    
    @Column
    public Integer maxPossibleScore;
}