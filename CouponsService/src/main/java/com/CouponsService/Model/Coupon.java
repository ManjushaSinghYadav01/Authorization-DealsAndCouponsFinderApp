package com.CouponsService.Model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "coupons")
public class Coupon {
	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    @Column(unique = true, nullable = false)
	    private String code;
	    private boolean active;
	    private double discountPercent;// e.g., 10% discount
	    private String category;  // Category-based discount
	    private double flatDiscount; // e.g., ₹500 discount
	    private double minPurchaseAmount; // Min purchase required
	    private LocalDate expiryDate;
 
	    
		
	    
}
