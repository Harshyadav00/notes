package com.harsh.inspireBlog.Controller;

import com.harsh.inspireBlog.Jwt.JwtService;
import com.harsh.inspireBlog.Model.LoginRequest;
import com.harsh.inspireBlog.Model.LoginResponse;
import com.harsh.inspireBlog.Model.User;
import com.harsh.inspireBlog.Model.UserRegistrationDto;
import com.harsh.inspireBlog.Security.CustomUserDetailsService;
import com.harsh.inspireBlog.Service.UserService;
import com.harsh.inspireBlog.Service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	private UserService userService;

	@PostMapping("/register")
	public ResponseEntity<User> registerUser(@RequestBody UserRegistrationDto userRegistrationDto){
		User registeredUser = userService.registerUser(userRegistrationDto);
		return new ResponseEntity<>(registeredUser,HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestBody  LoginRequest loginRequest) {
		doAuthenticate(loginRequest.getUserName(), loginRequest.getPassword());
		UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginRequest.getUserName());
		String jwtToken = jwtService.generateToken(userDetails);
		User user = userService.getUserByEmail(userDetails.getUsername());
		LoginResponse response = LoginResponse.builder()
				.id(user.getUserId())
				.jwtToken(jwtToken)
				.name(user.getName())
				.email(user.getEmail())
				.build();
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}

	private void doAuthenticate(String email, String password) {
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
		authenticationManager.authenticate(authentication);
	}

}
