package com.integralCrypto.spring.portfolio.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PortfolioDTO {

    @NotNull
    private Long userId;

    @NotNull
    @Size(min = 2, max = 255)
    private String name;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    
}