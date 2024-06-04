package com.integralCrypto.spring.portfolio.services;

import com.integralCrypto.spring.portfolio.dto.PortfolioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.integralCrypto.spring.exception.ResourceNotFoundException;
import com.integralCrypto.spring.login.models.User;
import com.integralCrypto.spring.login.repository.UserRepository;
import com.integralCrypto.spring.portfolio.models.Portfolio;
import com.integralCrypto.spring.portfolio.repository.PortfolioRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class PortfolioService {

	@Autowired
	private PortfolioRepository portfolioRepository;

	@Autowired
	private UserRepository userRepository;

	public Portfolio createPortfolio (Long userId, String name) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Invalid user ID"));

		Portfolio portfolio = new Portfolio();
		portfolio.setUser(user);
		portfolio.setName(name);

		return portfolioRepository.save(portfolio);
	}

	public void deletePortfolio (Long portfolioId) {
		Portfolio portfolio = portfolioRepository.findById(portfolioId)
				.orElseThrow(() -> new ResourceNotFoundException("Portfolio not found with id " + portfolioId));
		portfolioRepository.delete(portfolio);
	}

	public List<PortfolioDTO> getUserPortfolios(Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Invalid user ID"));

		List<Portfolio> portfolios = portfolioRepository.findByUser(user);
		List<PortfolioDTO> portfolioDTOs = new ArrayList<>();

		for (Portfolio portfolio : portfolios) {
			PortfolioDTO portfolioDTO = new PortfolioDTO();
			portfolioDTO.setUserId(portfolio.getUser().getId());
			portfolioDTO.setName(portfolio.getName());

			portfolioDTOs.add(portfolioDTO);
		}

		return portfolioDTOs;
	}

	public PortfolioDTO getPortfolioByUserAndId(Long userId, Long portfolioId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Invalid user ID"));

		Portfolio portfolio = portfolioRepository.findByIdAndUser(portfolioId, user)
				.orElseThrow(() -> new ResourceNotFoundException("Portfolio not found with id " + portfolioId + " for " +
						"user " + userId));

		PortfolioDTO portfolioDTO = new PortfolioDTO();
		portfolioDTO.setUserId(portfolio.getUser().getId());
		portfolioDTO.setName(portfolio.getName());

		return portfolioDTO;
	}
}
