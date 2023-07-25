package tech.dinhphu28.springsec.Utils.Security.Jwt;

import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
// import javax.crypto.SecretKey;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtTokenProvider {
    
    private static final String AUTHORITIES_KEY = "roles";

    private final JwtProperties jwtProperties;

    // private SecretKey secretKey;
    private Algorithm algorithm;

    @PostConstruct
    public void init() {
        // var secret = Base64.getEncoder().encodeToString(this.jwtProperties.getSecretKey().getBytes());

        String secret = this.jwtProperties.getSecretKey();

        this.algorithm = Algorithm.HMAC256(secret);
    }

    public String createToken(Authentication authentication) {
        
        String username = authentication.getName();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        Builder jwt = JWT.create();

        jwt = jwt.withSubject(username);

        if(!authorities.isEmpty()) {
            jwt = jwt.withClaim(AUTHORITIES_KEY, authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")));
        }

        Date now = new Date();
        Date validity = new Date(now.getTime() + this.jwtProperties.getValidityInMs());

        String token = jwt.withIssuer("auth0")
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .sign(algorithm);
        return token;
    }

    public Authentication getAuthentication(String token) {
        
        Verification verification = JWT.require(algorithm);

        JWTVerifier verifier = verification.build();

        DecodedJWT decodedJWT = verifier.verify(token);

        Object authoritiesClaim = decodedJWT.getClaim(AUTHORITIES_KEY);

        Collection<? extends GrantedAuthority> authorities = authoritiesClaim == null ? AuthorityUtils.NO_AUTHORITIES
                : (AuthorityUtils.commaSeparatedStringToAuthorityList(authoritiesClaim.toString()));

        User principal = new User(decodedJWT.getSubject(), "", authorities);

        log.info("User has been getAuthentication: {}", principal.toString());
        log.info("Authorities is: {}", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validateToken(String token) {
        try {
            Verification verification = JWT.require(algorithm);

            JWTVerifier verifier = verification.withIssuer("auth0")
                    .build();

            DecodedJWT decodedJWT = verifier.verify(token);

            log.info("expiration date: {}", decodedJWT.getExpiresAt());

            return true;

        } catch (JWTVerificationException | IllegalArgumentException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        }

        return false;
    }
}
