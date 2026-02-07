package org.acme.domain.models.files;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

public class Folder extends FileSystemNode {
    private final String icon;
    private final List<FileSystemNode> children;
    private final String metadata; // Pode ser mapeado para um Record/Map futuramente

    public Folder(String id, String name, String ownerId, String parentId, 
                  Instant createdAt, String icon, List<FileSystemNode> children, String metadata) {
        super(id, name, ownerId, parentId, createdAt);
        this.icon = icon;
        this.children = children != null ? children : Collections.emptyList();
        this.metadata = metadata;
    }

    public String getIcon() { return icon; }
    public List<FileSystemNode> getChildren() { return Collections.unmodifiableList(children); }
    public String getMetadata() { return metadata; }

    // Regra de negócio: Total de itens internos
    public int getItemCount() {
        return children.size();
    }
}