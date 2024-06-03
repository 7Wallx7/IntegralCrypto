package com.integralCrypto.spring.favoriteCoin.services;

import com.integralCrypto.spring.coin.models.Coin;
import com.integralCrypto.spring.coin.repository.CoinRepository;
import com.integralCrypto.spring.exception.ResourceNotFoundException;
import com.integralCrypto.spring.favoriteCoin.dto.FavoriteCoinDTO;
import com.integralCrypto.spring.favoriteCoin.models.FavoriteCoin;
import com.integralCrypto.spring.favoriteCoin.repository.FavoriteCoinRepository;
import com.integralCrypto.spring.login.models.User;
import com.integralCrypto.spring.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteCoinService {

	@Autowired
	private CoinRepository coinRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private FavoriteCoinRepository favoriteCoinRepository;

	public List<FavoriteCoinDTO> getFavoriteCoinsByUser(Long userId) {
		List<FavoriteCoin> favoriteCoins = favoriteCoinRepository.findByIdUser(userId);
		return favoriteCoins.stream()
				.map(this::convertToDTO)
				.collect(Collectors.toList());
	}

	public FavoriteCoinDTO registerFavorite(FavoriteCoinDTO favoriteCoinDTO) {
		Coin coin = coinRepository.findById(favoriteCoinDTO.getCoinId())
				.orElseThrow(() -> new ResourceNotFoundException("Invalid coin ID"));

		User user = userRepository.findById(favoriteCoinDTO.getUserId())
				.orElseThrow(() -> new ResourceNotFoundException("Invalid user ID"));

		FavoriteCoin favoriteCoin = new FavoriteCoin();
		favoriteCoin.setCoin(coin);
		favoriteCoin.setUser(user);

		favoriteCoin = favoriteCoinRepository.save(favoriteCoin);

		return convertToDTO(favoriteCoin);
	}

	public void deleteFavoriteCoin(Long idCoin, Long userId) {
		FavoriteCoin favoriteCoin = favoriteCoinRepository.findByCoinIdAndUserId(idCoin, userId);
		if (favoriteCoin == null) {
			throw new ResourceNotFoundException("FavoriteCoin not found with id " + idCoin);
		}
		favoriteCoinRepository.delete(favoriteCoin);
	}

	private FavoriteCoinDTO convertToDTO(FavoriteCoin favoriteCoin) {
		FavoriteCoinDTO favoriteCoinDTO = new FavoriteCoinDTO();
		favoriteCoinDTO.setId(favoriteCoin.getId());
		favoriteCoinDTO.setCoinId(favoriteCoin.getCoin().getId());
		favoriteCoinDTO.setUserId(favoriteCoin.getUser().getId());
		return favoriteCoinDTO;
	}
}