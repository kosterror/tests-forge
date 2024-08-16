package ru.kosterror.testsforge.userservice.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kosterror.testsforge.commonmodel.user.UserDto;
import ru.kosterror.testsforge.commonmodel.user.UserRole;
import ru.kosterror.testsforge.userservice.dto.CredentialsDto;
import ru.kosterror.testsforge.userservice.dto.TokensDto;
import ru.kosterror.testsforge.userservice.dto.UpdateUserDto;
import ru.kosterror.testsforge.userservice.entity.RefreshTokenEntity;
import ru.kosterror.testsforge.userservice.entity.UserEntity;
import ru.kosterror.testsforge.userservice.exception.ConflictException;
import ru.kosterror.testsforge.userservice.exception.UnauthorizedException;
import ru.kosterror.testsforge.userservice.mapper.UserMapper;
import ru.kosterror.testsforge.userservice.repository.RefreshTokenRepository;
import ru.kosterror.testsforge.userservice.repository.UserRepository;
import ru.kosterror.testsforge.userservice.service.AuthService;
import ru.kosterror.testsforge.userservice.service.JwtService;
import ru.kosterror.testsforge.userservice.service.MailService;
import ru.kosterror.testsforge.userservice.service.PasswordGeneratorService;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtService jwtService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final PasswordGeneratorService passwordGeneratorService;
    private final MailService mailService;

    private static void validateRefreshTokenOwner(UUID userId,
                                                  String refreshToken,
                                                  UserEntity user) {
        if (!user.getId().equals(userId)) {
            log.info("User with userId {} does not have refresh token {}", userId, refreshToken);
            throw new UnauthorizedException();
        }
    }

    @Override
    @Transactional
    public TokensDto login(CredentialsDto credentialsDto) {
        var user = userRepository.findByEmail(credentialsDto.email())
                .orElseThrow(() -> new UnauthorizedException("Incorrect email or password"));

        if (!passwordEncoder.matches(credentialsDto.password(), user.getPassword())) {
            log.info("Invalid password for user with email: {}", credentialsDto.email());
            throw new UnauthorizedException();
        }

        return jwtService.generateTokens(user);
    }

    @Override
    @Transactional
    public TokensDto refresh(String refreshToken) {
        var foundRefreshToken = refreshTokenRepository
                .findByToken(refreshToken)
                .orElseThrow(UnauthorizedException::new);
        var user = foundRefreshToken.getOwner();

        deleteRefreshToken(foundRefreshToken, user);

        return jwtService.generateTokens(user);
    }

    @Override
    @Transactional
    public UserDto registerTeacher(UpdateUserDto updateUserDto) {
        checkExistingUserWithEmail(updateUserDto.email());

        var entity = userMapper.toEntity(updateUserDto);
        entity.setPassword(passwordEncoder.encode(updateUserDto.password()));
        entity.setRole(UserRole.ROLE_TEACHER);

        entity = userRepository.save(entity);
        return userMapper.toDto(entity);
    }

    @Override
    @Transactional
    public TokensDto registerStudent(UpdateUserDto updateUserDto) {
        checkExistingUserWithEmail(updateUserDto.email());

        var entity = userMapper.toEntity(updateUserDto);
        entity.setPassword(passwordEncoder.encode(updateUserDto.password()));
        entity.setRole(UserRole.ROLE_STUDENT);

        entity = userRepository.save(entity);
        return jwtService.generateTokens(entity);
    }

    @Override
    @Transactional
    public void logout(UUID userId, String refreshToken) {
        var refreshTokenEntity = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(UnauthorizedException::new);
        var user = refreshTokenEntity.getOwner();

        validateRefreshTokenOwner(userId, refreshToken, user);
        deleteRefreshToken(refreshTokenEntity, user);
    }

    @Override
    @Transactional
    public void resetPassword(String email) {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UnauthorizedException("User with email '%s' not found".formatted(email)));

        var password = passwordGeneratorService.generatePassword();

        mailService.sendResettingPasswordMail(email, password);

        user.setPassword(passwordEncoder.encode(password));

        userRepository.save(user);
        refreshTokenRepository.deleteAllByOwnerId(user.getId());
    }

    private void deleteRefreshToken(RefreshTokenEntity foundRefreshToken,
                                    UserEntity user) {
        log.info("Deleting refresh token {} for user {}",
                foundRefreshToken.getId(),
                user.getId()
        );
        refreshTokenRepository.delete(foundRefreshToken);
    }

    private void checkExistingUserWithEmail(String email) {
        userRepository.findByEmail(email)
                .ifPresent(user -> {
                    throw new ConflictException("User with email '%s' already exists".formatted(email));
                });
    }

}
