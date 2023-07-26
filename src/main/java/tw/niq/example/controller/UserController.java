package tw.niq.example.controller;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tw.niq.example.model.UserResponseModel;

@RestController
@RequestMapping("/users")
public class UserController {

	@GetMapping("/status/check")
	public String status() {
		return "Working...";
	}
	
//	@PreAuthorize("hasRole('developer')")
	@PreAuthorize("hasAuthority('ROLE_developer') and #id == #jwt.subject")
//	@Secured("ROLE_developer")
	@DeleteMapping(path = "/{id}")
	public String deleteUser(@PathVariable String id, @AuthenticationPrincipal Jwt jwt) {
		return "Deleted user with id: " + id + " and JWT subject " + jwt.getSubject();
	}
	
	@PostAuthorize("hasAuthority('ROLE_developer') and returnObject.userId == #jwt.subject")
	@GetMapping(path = "/{id}")
	public UserResponseModel getUser(@PathVariable String id, @AuthenticationPrincipal Jwt jwt) {
				
		return new UserResponseModel(id, "John", "Doe");
	}
	
}
