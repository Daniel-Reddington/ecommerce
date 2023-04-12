package com.backend.ecommerce.securities.services;

import com.backend.ecommerce.utils.UserLoginForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService{

    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;
    private final UserDetailsService userDetailsService;

    @Override
    public String generateAccessToken(String subject, String scope, boolean withRefreshToken){

        Instant instant = Instant.now();

        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .subject(subject)
                .issuedAt(instant)
                .expiresAt(instant.plus(withRefreshToken?1:24, ChronoUnit.HOURS))
                .issuer("security-service")
                .claim("scope", scope)
                .build();

        String jwtAccessToken = jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();

        return jwtAccessToken;

    }

    @Override
    public String generateRefreshToken(String subject, String scope){

        Instant instant = Instant.now();

        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .subject(subject)
                .issuedAt(instant)
                .expiresAt(instant.plus(24, ChronoUnit.HOURS))
                .issuer("security-service")
                .claim("scope", scope)
                .build();

        String jwtRefreshToken = jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();

        return jwtRefreshToken;

    }

    @Override
    public Map<String, String> generateToken(String grantType, UserLoginForm userLoginForm, boolean withRefreshToken, String refreshToken) {

        List<String> subjectAndScope = new ArrayList<>();

        if(grantType.equals("password")){
            subjectAndScope = getSubjectAndScopeFromAuthentication(userLoginForm);
        } else if (grantType.equals("refreshToken")) {
            subjectAndScope = getSubjectAndScopeFromRefreshToken(refreshToken);
        }

        Map<String, String> idToken = new HashMap<>();
        idToken.put("access-token",
                generateAccessToken(subjectAndScope.get(0),subjectAndScope.get(1), withRefreshToken));

        if(withRefreshToken){
            idToken.put("refresh-token", generateRefreshToken(subjectAndScope.get(0),subjectAndScope.get(1)));
        }

        return idToken;

    }

    public List<String> getSubjectAndScopeFromAuthentication(UserLoginForm userLoginForm) throws BadCredentialsException{

        Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userLoginForm.username(),
                            userLoginForm.password()
                    )
            );
        String subject = authentication.getName();
        String scope = authentication.getAuthorities().stream()
                .map(auth-> auth.getAuthority()).collect(Collectors.joining(" "));
        return Arrays.asList(subject, scope);

    }

    public List<String> getSubjectAndScopeFromRefreshToken(String refreshToken){

        if(refreshToken == null){
            throw new RuntimeException("Refresh token is required");
        }

        Jwt decodedJwt = null;

        try {
            decodedJwt = jwtDecoder.decode(refreshToken);
        }catch (JwtException exception){
            throw new RuntimeException(exception.getMessage());
        }

        String subject = decodedJwt.getSubject();
        UserDetails userDetails = userDetailsService.loadUserByUsername(subject);
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        String scope = authorities.stream().map(auth->auth.getAuthority()).collect(Collectors.joining(" "));

        return Arrays.asList(subject, scope);

    }

}
