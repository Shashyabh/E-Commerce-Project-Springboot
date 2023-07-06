package com.store;

import com.store.entities.Role;
import com.store.repositories.RoleRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.UUID;

@SpringBootApplication
@EnableWebMvc //For swagger
public class ElectronicStoreApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepo roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(ElectronicStoreApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@Value("${normal.role.id}")
	private String role_normal_id;

	@Value("${admin.role.id}")
	private String role_admin_id;

	@Override
	public void run(String... args) throws Exception {
		//System.out.println(passwordEncoder.encode("pkj"));
		try {
			Role role_admin = Role.builder()
					.roleId(role_admin_id)
					.roleName("ROLE_ADMIN")
					.build();

			Role role_normal = Role.builder()
					.roleId(role_normal_id)
					.roleName("ROLE_NORMAL")
					.build();

			roleRepo.save(role_admin);
			roleRepo.save(role_normal);
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
}
