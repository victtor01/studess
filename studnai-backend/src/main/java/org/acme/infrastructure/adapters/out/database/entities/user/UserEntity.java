package org.acme.infrastructure.adapters.out.database.entities.user;

import java.time.Instant;
import java.util.UUID;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class UserEntity extends PanacheEntityBase {
    @Id
    public UUID id;

    @Column(unique = true, nullable = false)
    public String email;

    @Column
    public String firstName;

    @Column
    public String lastName;

    @Column(name = "created_at")
    public Instant createdAt;
} 
