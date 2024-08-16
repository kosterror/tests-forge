package ru.kosterror.testsforge.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kosterror.testsforge.userservice.service.PasswordGeneratorService;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class PasswordGeneratorServiceImpl implements PasswordGeneratorService {

    private final static String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!&$@?<>";
    private final static int PASSWORD_LENGTH = 12;

    @Override
    public String generatePassword() {
        var random = new Random();

        var stringBuilder = new StringBuilder(PASSWORD_LENGTH);

        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            var characterIndex = random.nextInt(CHARACTERS.length());
            var character = CHARACTERS.charAt(characterIndex);
            stringBuilder.append(character);
        }

        return stringBuilder.toString();
    }

}
