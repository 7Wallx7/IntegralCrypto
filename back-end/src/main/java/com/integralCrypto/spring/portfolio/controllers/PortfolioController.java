package com.integralCrypto.spring.portfolio.controllers;

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
@RequestMapping("/portfolios")
@Validated
public class PortfolioController {

    @Autowired
    private PortfolioService portfolioService;

    @PostMapping("/createPortfolio")
    public ResponseEntity<Portfolio> createPortfolio(@Valid @RequestBody PortfolioDTO portfolioDTO) {
        Portfolio portfolio = portfolioService.createPortfolio(portfolioDTO.getUserId(), portfolioDTO.getName());
        return ResponseEntity.ok(portfolio);
    }

    @DeleteMapping("/{portfolioId}")
    public ResponseEntity<Void> deletePortfolio(@PathVariable Long portfolioId) {
        portfolioService.deletePortfolio(portfolioId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Portfolio>> getPortfoliosByUser(@PathVariable Long userId) {
        List<Portfolio> portfolios = portfolioService.getUserPortfolios(userId);
        return ResponseEntity.ok(portfolios);
    }

    @GetMapping("/{userId}/{portfolioId}")
    public ResponseEntity<Portfolio> getPortfolioByUserAndId(@PathVariable Long userId, @PathVariable Long portfolioId) {
        Portfolio portfolio = portfolioService.getPortfolioByUserAndId(userId, portfolioId);
        return ResponseEntity.ok(portfolio);
    }
}
