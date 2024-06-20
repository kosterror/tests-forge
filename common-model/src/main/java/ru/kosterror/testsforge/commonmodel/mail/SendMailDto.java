package ru.kosterror.testsforge.commonmodel.mail;

import lombok.Builder;

import java.util.List;

@Builder
public record SendMailDto(
        List<String> receivers,
        List<String> copies,
        List<String> hiddenCopies,
        String subject,
        String body
) {
}
