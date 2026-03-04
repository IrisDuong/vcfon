package com.vcfon.auth.config;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Duration;
import java.util.Base64;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
public class AuthServerConfig {

	@Value("${url.auth-server}")
	String authServerURL;

	@Value("${url.gateway}")
	String gatewayURL;

	@Value("${sec.oauth2.gateway.client-id}")
	String gatewayClientId;

	@Value("${sec.oauth2.gateway.client-secret}")
	String gatewayClientSecret;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean
	public RegisteredClientRepository registeredClientRepository() {
		RegisteredClient gatewayClient = RegisteredClient.withId(gatewayClientId)
				.clientId(gatewayClientId)
				.clientSecret(passwordEncoder().encode(gatewayClientSecret))
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
				.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
				.redirectUri(gatewayURL.concat("/login/oauth2/code/").concat(gatewayClientId))
				.scope(OidcScopes.OPENID)
				.scope(OidcScopes.EMAIL)
				.scope(OidcScopes.PROFILE)
				.clientSettings(ClientSettings.builder().requireAuthorizationConsent(false).build())
				.tokenSettings(TokenSettings.builder()
						.accessTokenTimeToLive(Duration.ofMillis(3600000))
						.refreshTokenTimeToLive(Duration.ofMillis(7200000))
						.build()
				).build();
		return new InMemoryRegisteredClientRepository(gatewayClient);
	}
	
	@Bean
	public JWKSource<SecurityContext> genKeys() {
		KeyPair keyPair;
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(2048);
			keyPair = keyPairGenerator.generateKeyPair();
			
			RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
			RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
			
			RSAKey rsaKey = new RSAKey.Builder(publicKey).privateKey(privateKey).keyID(UUID.randomUUID().toString()).build();
			JWKSet jwkSet = new JWKSet(rsaKey);
			return (selector,context)->selector.select(jwkSet);
		} catch (Exception e) {
			return null;
		}
	}
	
	@Bean
	public AuthorizationServerSettings authorizationServerSettings() {
		return AuthorizationServerSettings.builder()
				.issuer(authServerURL).build();
	}

	@Bean
	public OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer(){
		return context->{
			if(OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())
				|| OidcParameterNames.ID_TOKEN.equals(context.getTokenType().getValue())
			) {
				Authentication principal = context.getPrincipal();
				if(principal instanceof OAuth2AuthenticationToken token) {
					OAuth2User oAuth2User = token.getPrincipal();
					String email = oAuth2User.getAttribute("email");
					String name = oAuth2User.getAttribute("name");
					String picture = oAuth2User.getAttribute("picture");
					
					context.getClaims().claim("email", email);
					context.getClaims().claim("name", name);
					context.getClaims().claim("picture", picture);
					context.getClaims().claim("provider", "google");
				}
			}
		};
	}
}
