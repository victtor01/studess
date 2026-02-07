package org.acme.api.dtos.request;

import jakarta.validation.constraints.NotBlank;

public record CreateFolderRequest(
    @NotBlank
    String name,
    String icon,
    String parentId
){}
