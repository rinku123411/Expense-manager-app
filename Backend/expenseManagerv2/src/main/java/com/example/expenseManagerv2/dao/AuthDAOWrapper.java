package com.example.expenseManagerv2.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.example.expenseManagerv2.Bean.LoginBean;

@Repository
public class AuthDAOWrapper implements AuthDAO {
	@Value("${firebase.api.key}")
    private String firebaseApiKey;
	private static final String Base_FIREBASE_LOGIN_URL =
            "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword";

    private final RestTemplate restTemplate = new RestTemplate();
    public String getLoginURL() {
    	return Base_FIREBASE_LOGIN_URL+ "?key="+ firebaseApiKey;
    }

	@Override
	public LoginBean login(String email, String password) {
		String url= getLoginURL();
		Map<String, Object> request = new HashMap();
        request.put("email", email);
        request.put("password", password);
        request.put("returnSecureToken", true);

        ResponseEntity<Map<String, Object>> response = restTemplate.postForEntity(
                url,
                request,
                (Class<Map<String, Object>>)(Class<?>)Map.class
        );

        Map<String,Object> body = response.getBody();
        if (body == null || !body.containsKey("idToken")) {
            throw new RuntimeException("Invalid login credentials");
        }

        return new LoginBean(
                body.get("idToken").toString(),
                body.get("refreshToken").toString(),
                body.get("localId").toString()
        );
	}

}
