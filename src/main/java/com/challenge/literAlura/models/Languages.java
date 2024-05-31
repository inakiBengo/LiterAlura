package com.challenge.literAlura.models;

public enum Languages {
    EN("en"),
    TL("tl"),
    ES("es"),
    FR("fr"),
    AR("ar"),
    DE("de"),
    IT("it"),
    IS("is"),
    LA("la"),
    KO("ko"),
    JA("ja"),
    FA("fa"),
    PT("pt"),
    RU("ru"),
    ZH("zh"),
    HU("hu");
    private String languages;

    Languages (String languages) {
        this.languages = languages;
    }

    public static Languages fromString(String text) {
        for (Languages language : Languages.values()) {
            if (language.languages.equalsIgnoreCase(text)) {
                return language;
            }
        }
        throw new IllegalArgumentException("Ninguna categoría encontrada: " + text);
    }
}
