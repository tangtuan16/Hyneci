package com.example.Aglaea;

import ch.qos.logback.core.testUtil.RandomUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Random;

@SpringBootApplication
public class AglaeaApplication {

	public static void main(String[] args) {
		System.out.println("Aglaea Application Started TangT" + RandomUtil.getPositiveInt());
		SpringApplication.run(AglaeaApplication.class, args);
	}

}
