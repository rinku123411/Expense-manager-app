package com.example.expenseManagerv2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@SpringBootApplication
public class ExpenseManagerv2Application {

		public static void main(String[] args) throws IOException {
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
			SpringApplication.run(ExpenseManagerv2Application.class, args);
	}

}
