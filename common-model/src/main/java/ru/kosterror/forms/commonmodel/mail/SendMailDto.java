package ru.kosterror.forms.commonmodel.mail;

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
    @Override
    public String toString() {
        return "SendMailDto{" +
                "receivers=" + receivers +
                ", copies=" + copies +
                ", hiddenCopies=" + hiddenCopies +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
