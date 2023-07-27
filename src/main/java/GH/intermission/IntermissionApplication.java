package GH.intermission;

import GH.intermission.domain.Member;
import GH.intermission.domain.Role;
import GH.intermission.service.MemberService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class IntermissionApplication {

	public static void main(String[] args) {
		SpringApplication.run(IntermissionApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	/*
	@Bean
	CommandLineRunner run (MemberService memberService){
		return args -> {
			memberService.saveRole(new Role(null, "ROLE_USER"));
			memberService.saveRole(new Role(null, "ROLE_MANAGER"));
			memberService.saveRole(new Role(null, "ROLE_ADMIN"));
			memberService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));

			memberService.saveMember(new Member(null, "John Travolta", "John","1234", new ArrayList<>()));
			memberService.saveMember(new Member(null, "Will Smith", "Will","1234", new ArrayList<>()));
			memberService.saveMember(new Member(null, "Jim Carry", "Jim","1234", new ArrayList<>()));
			memberService.saveMember(new Member(null, "Arnold Schwarzenegger", "Arnold","1234", new ArrayList<>()));

			memberService.addRoleToMember("John","ROLE_USER");
			memberService.addRoleToMember("Will","ROLE_MANAGER");
			memberService.addRoleToMember("Arnold","ROLE_SUPER_ADMIN");
			memberService.addRoleToMember("Jim","ROLE_USER");
		};
	}*/
}

