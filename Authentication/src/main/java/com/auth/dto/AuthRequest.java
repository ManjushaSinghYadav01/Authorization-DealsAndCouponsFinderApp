package com.auth.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthRequest {

    private String email;
    private String password;
    
//	public String getEmail() {
//		return email;
//	}
//	public void setEmail(String email) {
//		this.email = email;
//	}
//	public String getPassword() {
//		return password;
//	}
//	public void setPassword(String password) {
//		this.password = password;
//	}
//	public AuthRequest(String email, String password) {
//		super();
//		this.email = email;
//		this.password = password;
//	}
//	public AuthRequest() {
//		super();
//		// TODO Auto-generated constructor stub
//	}
//    

}
