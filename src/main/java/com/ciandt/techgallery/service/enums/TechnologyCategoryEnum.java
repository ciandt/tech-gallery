package com.ciandt.techgallery.service.enums;

public enum TechnologyCategoryEnum {

    TECHNIQUE("technique", "Technique"),
    TOOL("tool", "Tools"),
    LANGUAGE("language", "Language"),
    FRAMEWORK("framework", "Framework"),
    PLATFORM("platform", "Platform");

    private String id;
    private String title;

    TechnologyCategoryEnum(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
