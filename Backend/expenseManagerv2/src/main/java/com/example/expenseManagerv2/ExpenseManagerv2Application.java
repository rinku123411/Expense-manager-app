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
			SpringApplication.run(ExpenseManagerv2Application.class, args);
	}

}
