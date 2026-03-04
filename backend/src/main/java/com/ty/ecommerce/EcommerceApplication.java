package com.ty.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import com.ty.ecommerce.entity.User;
import com.ty.ecommerce.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class EcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}

	@Bean
	public CommandLineRunner seedData(UserRepository userRepository) {
		return args -> {
			User existingAdmin = userRepository.findByUsername("admin");
			if (existingAdmin == null) {
				User admin = new User();
				admin.setUsername("admin");
				admin.setPassword(new BCryptPasswordEncoder().encode("12345"));
				admin.setRole("ADMIN");
				admin.setEmail("admin@monocart.com");
				userRepository.save(admin);
				System.out.println("✅ Default Admin created: admin / 12345");
			} else {
				System.out.println(
						"✅ Admin verified: ID=" + existingAdmin.getId() + ", Email=" + existingAdmin.getEmail());
			}

			System.out.println("--- Current Users in 'users' Table ---");
			userRepository.findAll().forEach(u -> {
				System.out
						.println("ID: " + u.getId() + " | Username: " + u.getUsername() + " | Email: " + u.getEmail());
			});
			System.out.println("---------------------------------------");
		};
	}
}
