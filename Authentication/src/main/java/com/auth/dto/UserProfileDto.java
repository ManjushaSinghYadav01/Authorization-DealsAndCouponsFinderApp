package com.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDto {
	  private int id;
	    private String name;
	    private String email;
	    private double walletPoints;
	    
		
	    

}
