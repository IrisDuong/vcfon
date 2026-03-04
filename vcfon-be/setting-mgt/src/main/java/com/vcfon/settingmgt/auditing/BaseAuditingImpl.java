package com.vcfon.settingmgt.auditing;

import java.security.Principal;
import java.util.Optional;

//import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component(value = "auditorAware")
public class BaseAuditingImpl implements AuditorAware<String>{

	@Override
	public Optional<String> getCurrentAuditor() {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if(authentication.getPrincipal() instanceof Jwt jwt)
				return Optional.of(jwt.getClaimAsString("email"));
			return Optional.empty();
		} catch (NullPointerException e) {
			return Optional.empty();
		}
	}

}
