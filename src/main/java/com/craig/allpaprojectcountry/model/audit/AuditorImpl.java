package com.craig.allpaprojectcountry.model.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;

import java.util.Optional;

public class AuditorImpl implements AuditorAware<String> {
    @Override
    @NonNull
    public Optional<String> getCurrentAuditor() {
        return Optional.of("Craig");
    }
}
