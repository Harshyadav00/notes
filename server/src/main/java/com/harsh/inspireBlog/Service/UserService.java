package com.harsh.inspireBlog.Service;

import com.harsh.inspireBlog.Model.User;
import com.harsh.inspireBlog.Model.UserRegistrationDto;


public interface UserService {
	
	User registerUser(UserRegistrationDto userRegistrationDto);

	User getUserByEmail(String email);
}
