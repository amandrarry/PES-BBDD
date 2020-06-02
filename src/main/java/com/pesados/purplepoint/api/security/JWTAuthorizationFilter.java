package com.pesados.purplepoint.api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.pesados.purplepoint.api.exception.FirebaseTokenInvalidException;
import com.pesados.purplepoint.api.security.firebase.FirebaseAuthenticationToken;
import com.pesados.purplepoint.api.security.firebase.FirebaseParser;
import com.pesados.purplepoint.api.security.firebase.FirebaseTokenHolder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JWTAuthorizationFilter extends OncePerRequestFilter {

	private final String HEADER1 = "Authorization";
	private final String HEADER2 = "X-Authorization-Firebase";
	private final String PREFIX = "Bearer ";
	private final String SECRET = "adivinaestacrack";
	
	@Autowired
	private FirebaseParser firebaseService;
	
	public JWTAuthorizationFilter() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
		try {
			if (existeFBToken(request)) {
				String xAuth = request.getHeader(HEADER2);
				FirebaseTokenHolder holder = firebaseService.parseToken(xAuth);
				if (holder != null) {
					String userName = holder.getName();
					List<SimpleGrantedAuthority> res = new ArrayList<SimpleGrantedAuthority>();
					res.add(new SimpleGrantedAuthority("ROLE_DEVICE"));
					
					if (existeJWToken(request, response)) {
						Claims claims = validateJWToken(request);
						if (claims.get("authorities") != null) {
							res.add(new SimpleGrantedAuthority("ROLE_USER"));
						} 
					}
					
					Authentication auth = new FirebaseAuthenticationToken(userName, holder, res);
					SecurityContextHolder.getContext().setAuthentication(auth);
				} else {
					SecurityContextHolder.clearContext();
					throw new FirebaseTokenInvalidException(xAuth);
				}
			}
			chain.doFilter(request, response);
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
			return;
		}
	}

	private Claims validateJWToken(HttpServletRequest request) {
		String jwtToken = request.getHeader(HEADER1).replace(PREFIX, "");
		return Jwts.parser().setSigningKey(SECRET.getBytes()).parseClaimsJws(jwtToken).getBody();
	}
	/*
	private void setUpSpringUserAuthentication(Claims claims) {
		
		
		@SuppressWarnings("unchecked")
		ArrayList<String> list = (ArrayList<String>) claims.get("authorities");
		
		List<SimpleGrantedAuthority> res = new ArrayList<SimpleGrantedAuthority>();

		for (Iterator<String> it = list.iterator(); it.hasNext();) {
			res.add(new SimpleGrantedAuthority(it.next()));
		}

		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null, res);
		SecurityContextHolder.getContext().setAuthentication(auth);

	}
*/
	private boolean existeJWToken(HttpServletRequest request, HttpServletResponse res) {
		String authenticationHeader = request.getHeader(HEADER1);
		if (authenticationHeader == null || !authenticationHeader.startsWith(PREFIX))
			return false;
		return true;
	}
	
	private boolean existeFBToken(HttpServletRequest request) {
		String xAuth = request.getHeader(HEADER2);
		if (xAuth == null)
			return false;
		return true;
	}
}
