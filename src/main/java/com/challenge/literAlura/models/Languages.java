package com.challenge.literAlura.models;

public enum Languages {
    EN("English", "Ingles"),
    TL("Tagalog", "Tagalo"),
    ES("Spanish", "Español"),
    FR("French", "Frances"),
    AR("Arabic", "Arabico"),
    DE("German", "Aleman"),
    IT("Italian", "Italiano"),
    IS("Icelandic", "Islandés"),
    LA("Latin", "Latin"),
    KO("Corea", "Corea"),
    JA("Japanese", "Japones"),
    FA("Persian", "Persa"),
    PT("Portuguese", "Portuges"),
    RU("Russian", "Ruso"),
    ZH("Chinese", "Chino");
    private String languageEn;
    private String languageEs;

    Languages (String languageEn, String languageEs) {
        this.languageEn = languageEn;
        this.languageEs = languageEs;
    }

    public static Languages fromString(String text) {
        for (Languages language : Languages.values()) {
            if (language.languageEn.equalsIgnoreCase(text)) {
                return language;
            }
        }
        throw new IllegalArgumentException("Ninguna categoría encontrada: " + text);
    }

    public static Languages fromSpanish(String text) {
        for (Languages language : Languages.values()) {
            if (language.languageEs.equalsIgnoreCase(text)) {
                return language;
            }
        }
        throw new IllegalArgumentException("Ninguna categoría encontrada: " + text);
    }
}
