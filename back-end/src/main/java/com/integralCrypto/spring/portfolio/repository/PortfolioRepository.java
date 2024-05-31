package com.integralCrypto.spring.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.integralCrypto.spring.portfolio.models.Portfolio;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
}