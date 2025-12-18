package com.example.expenseManagerv2.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.example.expenseManagerv2.Bean.LoginBean;
import com.google.api.client.util.Value;

@Repository
public class AuthDAOWrapper implements AuthDAO {
	@Value("${firebase.api.key}")
    private static String firebaseApiKey;
	private static final String FIREBASE_LOGIN_URL =
            "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key="+ firebaseApiKey;

    private final RestTemplate restTemplate = new RestTemplate();

	@Override
	public LoginBean login(String email, String password) {
		Map<String, Object> request = new HashMap();
        request.put("email", email);
        request.put("password", password);
        request.put("returnSecureToken", true);

        ResponseEntity<Map> response = restTemplate.postForEntity(
                FIREBASE_LOGIN_URL,
                request,
                Map.class
        );

        Map body = response.getBody();

        return new LoginBean(
                body.get("idToken").toString(),
                body.get("refreshToken").toString(),
                body.get("localId").toString()
        );
	}

}
