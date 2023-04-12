package com.backend.ecommerce.securities.services;

import com.backend.ecommerce.securities.constants.TokenParam;
import com.backend.ecommerce.utils.UserLoginForm;
import com.backend.ecommerce.utils.constants.GrandType;
import lombok.RequiredArgsConstructor;
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
                .expiresAt(
                        instant.plus(withRefreshToken?TokenParam.EXPIRED_ACCESS_TOKEN:TokenParam.EXPIRED_REFRESH_TOKEN,
                                ChronoUnit.HOURS))
                .issuer("security-service")
                .claim("scope", scope)
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();

    }

    @Override
    public String generateRefreshToken(String subject, String scope){

        Instant instant = Instant.now();

        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .subject(subject)
                .issuedAt(instant)
                .expiresAt(instant.plus(TokenParam.EXPIRED_REFRESH_TOKEN, ChronoUnit.HOURS))
                .issuer("security-service")
                .claim("scope", scope)
                .build();

        String jwtRefreshToken = jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();

        return jwtRefreshToken;

    }

    @Override
    public Map<String, String> generateToken(GrandType grantType, UserLoginForm userLoginForm, boolean withRefreshToken, String refreshToken) {

        List<String> subjectAndScope = new ArrayList<>();

        if(grantType.equals(GrandType.PASSWORD)){
            subjectAndScope = getSubjectAndScopeFromAuthentication(userLoginForm);
        } else if (grantType.equals(GrandType.REFRESHTOKEN)) {
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
                .map(GrantedAuthority::getAuthority).collect(Collectors.joining(" "));
        return Arrays.asList(subject, scope);

    }

    public List<String> getSubjectAndScopeFromRefreshToken(String refreshToken){

        if(refreshToken == null){
            throw new RuntimeException("Refresh token is required");
        }

        Jwt decodedJwt;

        try {
            decodedJwt = jwtDecoder.decode(refreshToken);
        }catch (JwtException exception){
            throw new RuntimeException(exception.getMessage());
        }

        String subject = decodedJwt.getSubject();
        UserDetails userDetails = userDetailsService.loadUserByUsername(subject);
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        String scope = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(" "));

        return Arrays.asList(subject, scope);

    }

}
