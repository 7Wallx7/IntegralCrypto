package com.integralCrypto.spring.portfolio.controllers;

import com.integralCrypto.spring.login.payload.response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.integralCrypto.spring.portfolio.dto.PortfolioDTO;
import com.integralCrypto.spring.portfolio.models.Portfolio;
import com.integralCrypto.spring.portfolio.services.PortfolioService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RestController
@RequestMapping ("/portfolios")
@Validated
public class PortfolioController {

	@Autowired
	private PortfolioService portfolioService;

	@PostMapping ("/createPortfolio")
	public ResponseEntity<?> createPortfolio (@Valid @RequestBody PortfolioDTO portfolioDTO) {
		Portfolio portfolio = portfolioService.createPortfolio(portfolioDTO.getUserId(), portfolioDTO.getName());

		String successMessage = "{\"message\": \"Portfolio created successfully\"}";

		return ResponseEntity.ok(new MessageResponse("Portfolio registered successfully!"));
	}


	@DeleteMapping ("/{portfolioId}")
	public ResponseEntity<Void> deletePortfolio (@PathVariable Long portfolioId) {
		portfolioService.deletePortfolio(portfolioId);
		return ResponseEntity.noContent().build();
	}

	@GetMapping ("/user/{userId}")
	public ResponseEntity<List<PortfolioDTO>> getPortfoliosByUser (@PathVariable Long userId) {
		List<PortfolioDTO> portfolioDTOs = portfolioService.getUserPortfolios(userId);
		return ResponseEntity.ok(portfolioDTOs);
	}

	@GetMapping ("/{userId}/{portfolioId}")
	public ResponseEntity<PortfolioDTO> º (@PathVariable Long userId, @PathVariable Long portfolioId) {
		PortfolioDTO portfolioDTO = portfolioService.getPortfolioByUserAndId(userId, portfolioId);
		return ResponseEntity.ok(portfolioDTO);
	}
}
