package org.acme.domain.values;

public record Name(String value) {
    private static final int MIN = 2;
    private static final int MAX = 50;

    public Name {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }

        var normalized = value.trim();

        if (normalized.length() < MIN) {
            throw new IllegalArgumentException(
                "Nome deve ter no mínimo " + MIN + " caracteres"
            );
        }

        if (normalized.length() > MAX) {
            throw new IllegalArgumentException(
                "Nome deve ter no máximo " + MAX + " caracteres"
            );
        }

        value = normalized;
    }
}