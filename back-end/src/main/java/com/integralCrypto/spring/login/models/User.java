package com.integralCrypto.spring.login.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.integralCrypto.spring.favoriteCoin.models.FavoriteCoin;
import com.integralCrypto.spring.portfolio.models.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table (name = "users",
		uniqueConstraints = {
				@UniqueConstraint (columnNames = "username"),
				@UniqueConstraint (columnNames = "email")
		})
public class User {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size (max = 20)
	private String username;

	@NotBlank
	@Size (max = 50)
	@Email
	private String email;

	@NotBlank
	@Size (max = 120)
	private String password;

	@OneToMany (mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Portfolio> portfolios;

	@ManyToMany (fetch = FetchType.LAZY)
	@JoinTable (name = "user_roles",
			joinColumns = @JoinColumn (name = "user_id"),
			inverseJoinColumns = @JoinColumn (name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	@OneToMany (mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<FavoriteCoin> favoriteCoins;

	public User () {
	}

	public User (String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public Long getId () {
		return id;
	}

	public void setId (Long id) {
		this.id = id;
	}

	public String getUsername () {
		return username;
	}

	public void setUsername (String username) {
		this.username = username;
	}

	public String getEmail () {
		return email;
	}

	public void setEmail (String email) {
		this.email = email;
	}

	public String getPassword () {
		return password;
	}

	public void setPassword (String password) {
		this.password = password;
	}

	public Set<Role> getRoles () {
		return roles;
	}

	public void setRoles (Set<Role> roles) {
		this.roles = roles;
	}

	public List<Portfolio> getPortfolios () {
		return portfolios;
	}

	public void setPortfolios (List<Portfolio> portfolios) {
		this.portfolios = portfolios;
	}

	public List<FavoriteCoin> getFavoriteCoins () {
		return favoriteCoins;
	}

	public void setFavoriteCoins (List<FavoriteCoin> favoriteCoins) {
		this.favoriteCoins = favoriteCoins;
	}
}
