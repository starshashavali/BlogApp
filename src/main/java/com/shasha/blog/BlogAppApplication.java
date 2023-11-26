package com.shasha.blog;

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class BlogAppApplication implements CommandLineRunner {


	
	
	
	public static void main(String[] args) {
		SpringApplication.run(BlogAppApplication.class, args);
	}

	@Bean
	public ModelMapper mapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		
	/*	RoleEntity role1=new RoleEntity(5001, "ADMIN");

		RoleEntity role2=new RoleEntity(5002, "USER");
		List<RoleEntity> listRoles = List.of(role2,role1);
		System.out.println("Roles of entity ::"+listRoles);
		roleRepo.saveAll(listRoles);*/
		

	}

}
