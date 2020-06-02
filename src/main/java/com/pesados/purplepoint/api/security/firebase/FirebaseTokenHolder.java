package com.pesados.purplepoint.api.security.firebase;

import java.util.ArrayList;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.google.api.client.util.ArrayMap;
import com.google.firebase.auth.FirebaseToken;

public class FirebaseTokenHolder {
    private @Id @GeneratedValue Long pkId;
	
	private FirebaseToken token;

	public FirebaseTokenHolder(FirebaseToken token) {
		this.token = token;
	}

	public String getEmail() {
		return token.getEmail();
	}

	public String getIssuer() {
		return token.getIssuer();
	}

	public String getName() {
		return token.getName();
	}

	public String getUid() {
		return token.getUid();
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<String> getAuthorities() {
		return (ArrayList<String>) token.getClaims().get("authorities");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String getGoogleId() {
		String userId = ((ArrayList<String>) ((ArrayMap) ((ArrayMap) token.getClaims()
				.get("firebase"))
				.get("identities"))
				.get("google.com"))
				.get(0);
		return userId;
	}

}