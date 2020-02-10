package com.boot.test1.javaCode;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

public class PassWordEncoderTest {

	private static PasswordEncoder passwordEncoder;

	private static void encode() {

		// 이렇게쓰면 기본으로 bcrypt형식으로 암호화 되는구나..
		passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

		String password = "password";
		String encPassword = passwordEncoder.encode(password);

		System.out.println(" password : " + password );
		System.out.println(" encPassword : " + encPassword ); // encode의 결과로 앞에 저절로 {bcrypt}가 붙어왔네 ??

		System.out.println("1. passwordEncoder.matches(password, encPassword); : " + passwordEncoder.matches(password, encPassword));
		System.out.println("2. (encPassword).contains(\"{bcrypt}\") : " + (encPassword).contains("{bcrypt}"));

	}

	// {id}가 없이 들어간경우에는 아래처럼 확인하는 작업이 가능하다.
	private static void noEncIdCheck() {

		String password = "password";
		String encPassword = "$2a$10$Ot44NE6k1kO5bfNHTP0m8ejdpGr8ooHGT90lOD2/LpGIzfiS3p6oq"; // bcrypt 

		DelegatingPasswordEncoder delegatingPasswordEncoder = (DelegatingPasswordEncoder) PasswordEncoderFactories.createDelegatingPasswordEncoder();
		delegatingPasswordEncoder.setDefaultPasswordEncoderForMatches(new BCryptPasswordEncoder());

		System.out.println(" delegatingPasswordEncoder.matches(password, encPassword) : " + (delegatingPasswordEncoder.matches(password, encPassword)));

	}
	
	// 다른 암호화.. 적용해보기..
	// 다른 암호화 적용하려면 저렇게 PasswordEncoder객체를 다른 class를 지정해주어야 하는구나 ..
	private static void anotherEnc() {

		String password = "password";
		String encPassword = "";
		
		PasswordEncoder passwordEncoder = NoOpPasswordEncoder.getInstance();
		encPassword = passwordEncoder.encode(password);
		System.out.println("noop : " + encPassword);
		
		passwordEncoder = new Pbkdf2PasswordEncoder();
		encPassword = passwordEncoder.encode(password);
		System.out.println("pbkdf2 : " + encPassword);

		passwordEncoder = new org.springframework.security.crypto.password.MessageDigestPasswordEncoder("SHA-256");
		encPassword = passwordEncoder.encode(password);
		System.out.println("SHA-256 : " + encPassword);

	}

	public static void main(String[] args) {
		System.out.println("### encode ###");
		encode();
		System.out.println("### noEncId ###");
		noEncIdCheck();
		System.out.println("### anotherEnc ###");
		anotherEnc();
	}

}
