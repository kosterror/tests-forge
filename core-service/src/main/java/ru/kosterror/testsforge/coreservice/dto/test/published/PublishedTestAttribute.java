package ru.kosterror.testsforge.coreservice.dto.test.published;

import lombok.Getter;

@Getter
public enum PublishedTestAttribute {
    TIMER("Время таймера"),
    DEADLINE("Дедлайн");

    private final String description;

    PublishedTestAttribute(String description) {
        this.description = description;
    }
}
