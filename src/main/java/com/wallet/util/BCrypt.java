package com.wallet.util;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCrypt {

	public static String getHash(String password) {
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		return Optional.ofNullable(password)
				.map(encoder::encode)
				.orElse(null);
	}
}
