package org.acme.domain.models.files;

import java.time.Instant;
import java.util.Optional;

public abstract class FileSystemNode {
    private final String id;
    private final String name;
    private final String ownerId;
    private final String parentId; 
    private final Instant createdAt;

    protected FileSystemNode(String id, String name, String ownerId, String parentId, Instant createdAt) {
        this.id = id;
        this.name = name;
        this.ownerId = ownerId;
        this.parentId = parentId;
        this.createdAt = createdAt;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getOwnerId() { return ownerId; }
    public Optional<String> getParentId() { return Optional.ofNullable(parentId); }
    public Instant getCreatedAt() { return createdAt; }

    public boolean isRoot() {
        return parentId == null;
    }
}