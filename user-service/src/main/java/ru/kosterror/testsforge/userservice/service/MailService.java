package ru.kosterror.testsforge.userservice.service;

public interface MailService {

    void sendResettingPasswordMail(String email, String password);

}
