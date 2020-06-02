package com.pesados.purplepoint.api.controller;

import com.pesados.purplepoint.api.model.user.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import com.pesados.purplepoint.api.exception.UserNotFoundException;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class LoginSystem {

    private final UserService userService;

    public LoginSystem (UserService usrService) {
        this.userService = usrService;
    }

    public boolean checkLoggedIn(String unformatedJWT) {
        try {
            this.userService.getUserByToken(unformatedJWT).orElseThrow(() -> new UserNotFoundException(unformatedJWT));
            return true;
        } catch ( UserNotFoundException e ) {
            return false;
        }	
    }

    public String getJWTToken(String email) {
		String secretKey = "adivinaestacrack";
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList("ROLE_USER, ROLE_DEVICE");

		String token = Jwts
				.builder()
				.setId("ppJWT")
				.setSubject(email)
				.claim("authorities",
						grantedAuthorities.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 5)) // 5 minutos

				.signWith(SignatureAlgorithm.HS512,
						secretKey.getBytes()).compact();

		return "Bearer " + token;
	}
}