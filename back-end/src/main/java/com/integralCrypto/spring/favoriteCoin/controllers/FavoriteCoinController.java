package com.integralCrypto.spring.favoriteCoin.controllers;

import com.integralCrypto.spring.favoriteCoin.dto.FavoriteCoinDTO;
import com.integralCrypto.spring.favoriteCoin.services.FavoriteCoinService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favoriteCoins")
@Validated
public class FavoriteCoinController {

	@Autowired
	private FavoriteCoinService favoriteCoinService;

	@GetMapping("/{userId}")
	public ResponseEntity<List<FavoriteCoinDTO>> getFavoriteCoinsByUser(@PathVariable Long userId) {
		List<FavoriteCoinDTO> favoriteCoins = favoriteCoinService.getFavoriteCoinsByUser(userId);
		return ResponseEntity.ok(favoriteCoins);
	}

	@PostMapping("/register")
	public ResponseEntity<FavoriteCoinDTO> registerFavorite(@Valid @RequestBody FavoriteCoinDTO favoriteCoinDTO) {
		FavoriteCoinDTO result = favoriteCoinService.registerFavorite(favoriteCoinDTO);
		return ResponseEntity.ok(result);
	}

	@DeleteMapping("/{idCoin}/{userId}")
	public ResponseEntity<Void> deleteFavoriteCoin(@PathVariable Long idCoin, @PathVariable Long userId) {
		favoriteCoinService.deleteFavoriteCoin(idCoin, userId);
		return ResponseEntity.noContent().build();
	}
}