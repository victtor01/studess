package org.acme.infrastructure.adapters.out.database.entities.files;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("DOCUMENT")
public class DocumentEntity extends FileSystemNodeEntity {
    @Column(name = "s3_url")
    public String s3Url;

    @Column(name = "is_vectorized")
    public boolean vectorized = false;

    @Column(name = "total_pages")
    public int totalPages;
}