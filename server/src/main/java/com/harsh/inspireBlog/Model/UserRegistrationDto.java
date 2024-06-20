package com.harsh.inspireBlog.Model;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRegistrationDto {

	private String name;
	private String userName;
	private String email;
	private String password;
	private Role roles;

}
