package tw.niq.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import tw.niq.example.security.KeyclockRoleConverter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
	SecurityFilterChain configure(HttpSecurity http) throws Exception {
		
		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeyclockRoleConverter());
		
		http
			.authorizeHttpRequests(authorizeHttpRequests -> 
				authorizeHttpRequests
					.requestMatchers(HttpMethod.GET, "/users/status/check")
//						.hasAuthority("SCOPE_profile")
//						.hasRole("developer")
						.hasAnyAuthority("ROLE_developer")
					.anyRequest().authenticated())
			.oauth2ResourceServer(oauth2ResourceServer -> 
				oauth2ResourceServer
					.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter)));
		
		return http.build();
	}
	
}
