package org.acme.infrastructure.adapters.out.database.entities.user;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "users")
public class UserEntity extends PanacheEntityBase {
    @Id
    public String id;

    @Column(unique = true, nullable = false)
    public String email;

    @Column(name = "created_at")
    public Instant createdAt;
} 
