package net.guides.springboot2.springboot2jpacrudexample;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ComponentScan(basePackages = "com.example")
@SpringBootTest
public class ApplicationTests {

	@Test
	public void contextLoads() {
	}
	
	
	@Bean
	public UserDetailsService userDetailsService(){
		GrantedAuthority authority = new SimpleGrantedAuthority("ADMIN");
		UserDetails userDetails = (UserDetails)new User("admin", "admin", Arrays.asList(authority));
		return new InMemoryUserDetailsManager(Arrays.asList(userDetails));
	} 

}
