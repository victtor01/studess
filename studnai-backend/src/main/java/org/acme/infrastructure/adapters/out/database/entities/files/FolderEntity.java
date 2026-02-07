package org.acme.infrastructure.adapters.out.database.entities.files;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
@DiscriminatorValue("FOLDER")
public class FolderEntity extends FileSystemNodeEntity {
    public String icon;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    public java.util.List<FileSystemNodeEntity> children;

    @Column(columnDefinition = "jsonb")
    public String metadata; 
}