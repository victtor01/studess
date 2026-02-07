package org.acme.domain.models.files;

import java.time.Instant;

public class Document extends FileSystemNode {
    private final String s3Url;
    private final boolean vectorized;
    private final int totalPages;

    public Document(String id, String name, String ownerId, String parentId, Instant createdAt, String s3Url, boolean vectorized, int totalPages) {
        super(id, name, ownerId, parentId, createdAt);
        this.s3Url = s3Url;
        this.vectorized = vectorized;
        this.totalPages = totalPages;
    }

    public String getS3Url() { return s3Url; }
    public boolean isVectorized() { return vectorized; }
    public int getTotalPages() { return totalPages; }
}