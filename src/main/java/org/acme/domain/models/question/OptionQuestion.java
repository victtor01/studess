package org.acme.domain.models.question;

public class OptionQuestion {
    private String id;
    private String text;

    public OptionQuestion(String id, String text) {
        this.id = id;
        this.text = text;
    }

    public String getId() {
        return this.id;
    }

    public String getText() {
        return this.text;
    }


}
