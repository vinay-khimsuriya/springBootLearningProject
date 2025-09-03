package com.vinay.FirstProjectInSpring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class FirstProjectInSpringApplication {

	public static void main(String[] args) {
// String SECRET = "this_is_a_very_secret_key_123456789012345";
//         Key key = Keys.hmacShaKeyFor(SECRET.getBytes());
		
// 		String base64Key = Base64.getEncoder().encodeToString(key.getEncoded());
//         System.out.println("Key in Base64: " + base64Key);
		SpringApplication.run(FirstProjectInSpringApplication.class, args);
	}

}
