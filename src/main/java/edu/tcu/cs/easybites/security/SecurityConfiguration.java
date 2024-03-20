package edu.tcu.cs.easybites.security;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.KeySourceException;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSelector;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.jwk.source.RemoteJWKSet;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import com.nimbusds.jwt.proc.JWTProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;


@Configuration
public class SecurityConfiguration {

    private final RSAPublicKey publicKey;
    private final RSAPrivateKey privateKey;

    @Value("${api.endpoint.base-url}")
    private String baseUrl;

    private final CustomBasicAuthenticationEntryPoint customBasicAuthenticationEntryPoint;

    private final CustomBearerTokenAuthenticationEntryPoint customBearerTokenAuthenticationEntryPoint;

    private final CustomBearerTokenAccessDeniedHandler customBearerTokenAccessDeniedHandler;

    public SecurityConfiguration(CustomBasicAuthenticationEntryPoint customBasicAuthenticationEntryPoint,
                                 CustomBearerTokenAuthenticationEntryPoint customBearerTokenAuthenticationEntryPoint,
                                 CustomBearerTokenAccessDeniedHandler customBearerTokenAccessDeniedHandler) throws NoSuchAlgorithmException {
        this.customBasicAuthenticationEntryPoint = customBasicAuthenticationEntryPoint;
        this.customBearerTokenAuthenticationEntryPoint = customBearerTokenAuthenticationEntryPoint;
        this.customBearerTokenAccessDeniedHandler = customBearerTokenAccessDeniedHandler;
        // Generate public / private key pair in java
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA"); // use factory pattern to generate a keypairgenerator with RSA algorithm
        keyPairGenerator.initialize(2048); // generated key will have a size of 2048 bits
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        this.publicKey = (RSAPublicKey) keyPair.getPublic();
        this.privateKey = (RSAPrivateKey) keyPair.getPrivate();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                                // All recipe API endpoints should require authentication
                                // Here we only specify PUT - update recipe status for admins only
                                .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.PUT, this.baseUrl + "/recipes/{newStatus}/{recipeId}")).hasAuthority("ROLE_admin")

                                // nutrition-user api endpoints
                                .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.PUT, this.baseUrl + "/nutrition-user/**")).hasAuthority("ROLE_admin")
                                .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST, this.baseUrl + "/nutrition-user")).hasAuthority("ROLE_admin") // login does not need admin
                                .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.DELETE, this.baseUrl + "/nutrition-user/**")).hasAuthority("ROLE_admin")
                                .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, this.baseUrl + "/nutrition-user/**")).hasAuthority("ROLE_admin")
                                .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll() // h2 console

                                .anyRequest().authenticated() // always good idea to put this as last to authenticate everything else.
//                                .anyRequest().permitAll()

                )
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .httpBasic(httpBasic -> httpBasic.authenticationEntryPoint(this.customBasicAuthenticationEntryPoint))
                .oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer.jwt(
                                jwt -> {
                                    try {
                                        jwt.decoder(jwtDecoder())
                                                .jwtAuthenticationConverter(jwtAuthenticationConverter());
                                    } catch (MalformedURLException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                        ).authenticationEntryPoint(this.customBearerTokenAuthenticationEntryPoint)
                        .accessDeniedHandler(this.customBearerTokenAccessDeniedHandler))
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public JwtEncoder jwtEncoder() {
        // json web key, pass both keys and call the builder. encoder uses private key to encode JWT
        JWK jwk = new RSAKey.Builder(this.publicKey).privateKey(this.privateKey).build();
        JWKSource<SecurityContext> jwkSet = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwkSet);
    }

//    @Bean
//    public JwtDecoder jwtDecoder() {
//        // use public key to decode and verify. this public key can be distributed, but private key must be kept secret
//        return NimbusJwtDecoder.withPublicKey(this.publicKey).build();
//    }


    @Bean
    public JwtDecoder jwtDecoder() throws MalformedURLException {
        // Load Clerk's public keys
        JWKSource<SecurityContext> clerkJwkSource = new RemoteJWKSet<>(new URL("https://precious-skink-82.clerk.accounts.dev/.well-known/jwks.json"));

        // Load self-generated public keys
        JWK selfGeneratedJwk = new RSAKey.Builder(publicKey).privateKey(privateKey).build();
        JWKSource<SecurityContext> selfGeneratedJwkSource = new ImmutableJWKSet<>(new JWKSet(selfGeneratedJwk));

        // Combine both sources
        JWKSource<SecurityContext> combinedJwkSource = (jwkSelector, securityContext) -> {
            List<JWK> combinedJwks = new ArrayList<>();
            List<JWK> clerkJwk = clerkJwkSource.get(jwkSelector, securityContext);
            if (clerkJwk != null) {
                combinedJwks.addAll(clerkJwk);
            }
            combinedJwks.addAll(selfGeneratedJwkSource.get(jwkSelector, securityContext));
            return combinedJwks;
        };

        // Create a JWT processor
        JWTProcessor<SecurityContext> jwtProcessor = new DefaultJWTProcessor<>();
        JWSVerificationKeySelector<SecurityContext> keySelector = new JWSVerificationKeySelector<>(JWSAlgorithm.RS256, combinedJwkSource);
        ((DefaultJWTProcessor<SecurityContext>) jwtProcessor).setJWSKeySelector(keySelector);

        // Create a JWT decoder
        NimbusJwtDecoder jwtDecoder = new NimbusJwtDecoder(jwtProcessor);

        return jwtDecoder;
    }


    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("authorities");
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix(""); // default authority prefix is "SCOPE", so we set it to nothing so we can keep our "ROLE_" prefix

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

        return jwtAuthenticationConverter;
    }

}

