package com.example.expenseManagerv2.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

@Configuration
public class FirebaseConfig {
	@Bean
    public Firestore firestore() throws IOException {

		try {
	        String firebasePath = System.getenv("FIREBASE_CONFIG_PATH");

	        if (firebasePath == null || firebasePath.isEmpty()) {
	            throw new RuntimeException("FIREBASE_CONFIG_PATH environment variable not set");
	        }

	        InputStream serviceAccount = new FileInputStream(firebasePath);

		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.build();
		if(FirebaseApp.getApps().isEmpty()) {
			FirebaseApp.initializeApp(options);
		}
		}catch(Exception e) {
			throw new RuntimeException("Failed to initialize firebase",e);
		}

        return FirestoreClient.getFirestore();
	}

}
