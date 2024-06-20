package com.harsh.inspireBlog.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import com.harsh.inspireBlog.Model.Role;
import com.harsh.inspireBlog.Model.User;
import com.harsh.inspireBlog.Model.UserRegistrationDto;
import com.harsh.inspireBlog.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;


	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public User registerUser(UserRegistrationDto userRegistrationDto) {

		// Save User with inactive status
		User newUser = User.builder()
				.name(userRegistrationDto.getName())
				.userName(userRegistrationDto.getUserName())
				.email(userRegistrationDto.getEmail())
				.password(passwordEncoder.encode(userRegistrationDto.getPassword()))
				.roles(userRegistrationDto.getRoles() == null ? Role.USER : userRegistrationDto.getRoles())
				.enabled(true).build();
		User savedUser = userRepository.save(newUser);

		return savedUser;
	}

	@Override
	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("User not found with email: " + email));
	}

}
