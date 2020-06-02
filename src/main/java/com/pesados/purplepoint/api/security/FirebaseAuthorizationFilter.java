package com.pesados.purplepoint.api.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.pesados.purplepoint.api.PurplePointApplication;
import com.pesados.purplepoint.api.security.firebase.FirebaseAuthenticationToken;
import com.pesados.purplepoint.api.security.firebase.FirebaseParser;
import com.pesados.purplepoint.api.security.firebase.FirebaseTokenHolder;

public class FirebaseAuthorizationFilter extends OncePerRequestFilter {
	
	private static final Logger logger = LoggerFactory.getLogger(PurplePointApplication.class);
	private static String HEADER_NAME = "X-Authorization-Firebase";

	@Autowired
	private FirebaseParser firebaseService;

	public FirebaseAuthorizationFilter(FirebaseParser firebaseService) {
		this.firebaseService = firebaseService;
	}

	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		logger.debug("Extracting header from request");
		String xAuth = request.getHeader(HEADER_NAME);
		if (xAuth != null) {
			filterChain.doFilter(request, response);
			return;
		} else {
			try {
				logger.debug("Checking firebase token against google service");
				FirebaseTokenHolder holder = firebaseService.parseToken(xAuth);
				if (holder != null) {
					logger.debug("Authenticating device");
					String userName = holder.getUid();
					
					ArrayList<String> list = holder.getAuthorities();
					List<GrantedAuthority> res = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_DEVICE");
					Authentication auth = new FirebaseAuthenticationToken(userName, holder, res);
					SecurityContextHolder.getContext().setAuthentication(auth);
				}
				
				filterChain.doFilter(request, response);
			} catch (SecurityException e) {
				throw new SecurityException(e);
			}
		}
	}

}
