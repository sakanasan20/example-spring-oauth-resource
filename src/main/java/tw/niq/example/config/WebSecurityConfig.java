package tw.niq.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
	SecurityFilterChain configure(HttpSecurity http) throws Exception {
		
		http
			.authorizeHttpRequests(authorizeHttpRequests -> 
				authorizeHttpRequests
					.requestMatchers(HttpMethod.GET, "/users/status/check").hasAuthority("SCOPE_profile")
					.anyRequest().authenticated())
			.oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer.jwt(jwt -> {}));
		
		return http.build();
	}
	
}
