package com.harsh.inspireBlog.Model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequest {
	
	private String userName;
	private String password;

}
