package com.backend.ecommerce.securities.config;

import com.backend.ecommerce.securities.services.UserDetailsServiceImpl;
import com.backend.ecommerce.services.interfaces.AppUserService;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final RsaKeyConfig rsaKeyConfig;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsServiceImpl userDetailsService;

    private static final String[] PERMIT_ENDPOINT = {
            "api/roles/find-all-role/**",
            "api/roles/find-role-by-id/**",
            "api/users/create-account/**",
            "api/categories/find-by-id/**",
            "api/categories/find-all-category/**",
            "api/categories/find-all-product-in-category/**",
            "api/products/find-all-product/**",
            "api/products/find-by-product-name-contains/**",
            "api/products/find-by-id/**"
    };

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        authenticationProvider.setUserDetailsService(userDetailsService());
        return new ProviderManager(authenticationProvider);
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return username ->  userDetailsService.loadUserByUsername(username);
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        final String[] AUTH_WHITE_LIST = {
                "/v3/api-docs/**",
                "/swagger-ui/**",
                "/v2/api-docs/**",
                "/swagger-resources/**"
        };
        return httpSecurity
                .csrf(csrf-> csrf.disable())
                .authorizeHttpRequests(auth-> {
                    auth.requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll();
                    auth.requestMatchers("/login/**").permitAll();
                    auth.requestMatchers(PERMIT_ENDPOINT).permitAll();
                    auth.requestMatchers(AUTH_WHITE_LIST).permitAll();
                    auth.anyRequest().authenticated();
                })
                .headers(header-> header.frameOptions().disable())
                .sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .httpBasic(Customizer.withDefaults())
                .build();

    }

    @Bean
    public JwtEncoder jwtEncoder(){
        JWK jwk = new RSAKey.Builder(rsaKeyConfig.publicKey()).privateKey(rsaKeyConfig.privateKey()).build();
        JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwkSource);
    }

    @Bean
    public JwtDecoder jwtDecoder(){
        return NimbusJwtDecoder.withPublicKey(rsaKeyConfig.publicKey()).build();
    }

}
