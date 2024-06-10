package ru.kosterror.forms.coreservice.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.kosterror.forms.securitystarter.model.JwtUser;

import java.util.Optional;
import java.util.UUID;

@Configuration
@EnableJpaAuditing
public class SecurityAuditorAware implements AuditorAware<UUID> {

    @Override
    @NonNull
    public Optional<UUID> getCurrentAuditor() {
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        if (principal instanceof JwtUser user) {
            return Optional.of(user.userId());
        }

        return Optional.empty();
    }

}
