package com.integralCrypto.spring.portfolio.repository;

import com.integralCrypto.spring.login.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import com.integralCrypto.spring.portfolio.models.Portfolio;

import java.util.List;
import java.util.Optional;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
	List<Portfolio> findByUser(User user);
	Optional<Portfolio> findByIdAndUser(Long portfolioId, User user);
}