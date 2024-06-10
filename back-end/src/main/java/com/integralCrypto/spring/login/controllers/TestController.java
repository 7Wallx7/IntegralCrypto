package com.integralCrypto.spring.login.controllers;

import com.integralCrypto.spring.portfolio.dto.PortfolioDTO;
import com.integralCrypto.spring.portfolio.services.PortfolioService;
import com.integralCrypto.spring.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//for Angular Client (withCredentials)
//@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600, allowCredentials="true")
@CrossOrigin (origins = "*")
@RestController
@RequestMapping ("/api/test")
public class TestController {

	@Autowired
	private PortfolioService portfolioService;

	@GetMapping ("/all")
	public String allAccess () {
		return "Public Content.";
	}

	@GetMapping ("/user")
	@PreAuthorize ("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public String userAccess () {
		return "User Content.";
	}

	@GetMapping ("/mod")
	@PreAuthorize ("hasRole('MODERATOR')")
	public String moderatorAccess () {
		return "Moderator Board.";
	}

	@GetMapping ("/admin")
	@PreAuthorize ("hasRole('ADMIN')")
	public String adminAccess () {
		return "Admin Board.";
	}

	@GetMapping ("/portfolio")
	@PreAuthorize ("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<List<PortfolioDTO>> getPortfoliosByUser () {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		Long userId = userDetails.getId();

		List<PortfolioDTO> portfolioDTOs = portfolioService.getUserPortfolios(userId);
		return ResponseEntity.ok(portfolioDTOs);
	}
}
