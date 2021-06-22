package com.wallet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wallet.entities.UserWallet;

@Repository
public interface UserWalletRepository extends JpaRepository<UserWallet, Long> {

	Optional<UserWallet> findByUsersIdAndWalletId(Long user, Long wallet);

}
