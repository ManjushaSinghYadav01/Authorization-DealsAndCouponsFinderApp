package com.TransactionService.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {

	 private Long transactionId;
	    private double amount;
	    private double discountAmount;
	    private double cashback;
	    private double finalPrice;
	    private LocalDateTime timestamp;
		

}


