package org.acme.infrastructure.adapters.out.database.entities.files;

import java.time.Instant;
import java.util.UUID;

import org.acme.infrastructure.adapters.out.database.entities.user.UserEntity;
import org.hibernate.annotations.CreationTimestamp;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "filesystem_nodes")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "node_type")
public abstract class FileSystemNodeEntity extends PanacheEntityBase {
    @Id
    public String id;

    @Column
    public String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", insertable = false, updatable = false) // <--- O PULO DO GATO
    public UserEntity owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", insertable = false, updatable = false) // <--- O PULO DO GATO
    public FolderEntity parent;

    @CreationTimestamp
    @Column(name = "created_at")
    public Instant createdAt;

    @Column(name = "parent_id")
    public String parentId; 

    @Column(name = "owner_id")
    public UUID ownerId;
}