package com.wallet.service;

import java.util.Optional;

import com.wallet.entities.UserWallet;

public interface UserWalletService {

	UserWallet save(UserWallet uw);
	
	Optional<UserWallet> findByUsersIdAndWalletId(Long user, Long wallet);
}
