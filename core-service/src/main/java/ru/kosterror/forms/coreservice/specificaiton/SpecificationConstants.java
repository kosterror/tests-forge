package ru.kosterror.forms.coreservice.specificaiton;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class SpecificationConstants {

    static final String PERCENT = "%";

    static String like(String text) {
        return PERCENT + text + PERCENT;
    }

}
