package com.integralCrypto.spring.favoriteCoin.repository;

import com.integralCrypto.spring.coin.models.Coin;
import com.integralCrypto.spring.favoriteCoin.models.FavoriteCoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FavoriteCoinRepository extends JpaRepository<FavoriteCoin, Long> {

	@Query("SELECT fc FROM FavoriteCoin fc WHERE fc.user.id = :userId")
	List<FavoriteCoin> findByIdUser (Long userId);

	@Query ("SELECT fc FROM FavoriteCoin fc WHERE fc.coin.id = :coinId AND fc.user.id = :userId")
	FavoriteCoin findByCoinIdAndUserId(Long coinId, Long userId);



}
