package com.wallet.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wallet.entities.UserWallet;
import com.wallet.repository.UserWalletRepository;
import com.wallet.service.UserWalletService;

@Service
public class UserWalletServiceImpl implements UserWalletService {

	@Autowired
	private UserWalletRepository repository;
	
	@Override
	public UserWallet save(UserWallet wallet) {
		return repository.save(wallet);
	}

	@Override
	public Optional<UserWallet> findByUsersIdAndWalletId(Long user, Long wallet) {
		return repository.findByUsersIdAndWalletId(user, wallet);
	}

}
