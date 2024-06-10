package com.integralCrypto.spring.portfolio.controllers;

import com.integralCrypto.spring.login.payload.response.MessageResponse;
import com.integralCrypto.spring.portfolio.dto.PortfolioDTO;
import com.integralCrypto.spring.portfolio.models.Portfolio;
import com.integralCrypto.spring.portfolio.services.PortfolioService;
import com.integralCrypto.spring.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping ("/api/portfolios")
@Validated
public class PortfolioController {

	@Autowired
	private PortfolioService portfolioService;

	@PostMapping ("/createPortfolio")
	public ResponseEntity<?> createPortfolio (@Valid @RequestBody PortfolioDTO portfolioDTO) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		Long userId = userDetails.getId();

		Portfolio portfolio = portfolioService.createPortfolio(userId, portfolioDTO.getName());
		return ResponseEntity.ok(new MessageResponse("Portfolio registered successfully!"));
	}

	@DeleteMapping ("/{portfolioId}")
	public ResponseEntity<Void> deletePortfolio (@PathVariable Long portfolioId) {
		portfolioService.deletePortfolio(portfolioId);
		return ResponseEntity.noContent().build();
	}

	@GetMapping ("/user")
	@PreAuthorize ("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<List<PortfolioDTO>> getPortfoliosByUser () {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		Long userId = userDetails.getId();

		List<PortfolioDTO> portfolioDTOs = portfolioService.getUserPortfolios(userId);
		return ResponseEntity.ok(portfolioDTOs);
	}

	@GetMapping ("/{portfolioId}")
	public ResponseEntity<PortfolioDTO> getPortfolioByUserAndId (@PathVariable Long portfolioId) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		Long userId = userDetails.getId();

		PortfolioDTO portfolioDTO = portfolioService.getPortfolioByUserAndId(userId, portfolioId);
		return ResponseEntity.ok(portfolioDTO);
	}

}

