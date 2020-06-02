package com.pesados.purplepoint.api.security.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.pesados.purplepoint.api.exception.FirebaseTokenBlankException;
import com.pesados.purplepoint.api.exception.FirebaseTokenInvalidException;

public class FirebaseParser {
	public FirebaseTokenHolder parseToken(String idToken) {
		if (idToken == null || idToken == "") {
			throw new FirebaseTokenBlankException();			
		}
		try {
			// idToken comes from the client app (shown above)
			FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);	
			return new FirebaseTokenHolder(decodedToken);
			
		} catch (Exception e) {
			throw new FirebaseTokenInvalidException(e.getMessage());
		}
	}
}