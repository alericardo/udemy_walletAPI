package com.wallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wallet.entities.Wallet;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

}
