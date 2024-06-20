package com.harsh.inspireBlog.Model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private String jwtToken ;
    private String name ;
    private String email ;
    private int id ;
}
