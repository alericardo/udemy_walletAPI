package com.wallet.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wallet.controller.dto.UserWalletDTO;
import com.wallet.controller.response.Response;
import com.wallet.entities.User;
import com.wallet.entities.UserWallet;
import com.wallet.entities.Wallet;
import com.wallet.service.UserWalletService;

@RestController
@RequestMapping("user-wallet")
public class UserWalletController {

	@Autowired
	private UserWalletService service;
	
	@PostMapping
	public ResponseEntity<Response<UserWalletDTO>> create(@Valid @RequestBody UserWalletDTO dto, BindingResult result) {
		
		Response<UserWalletDTO> response = new Response<UserWalletDTO>();
		
		if (result.hasErrors()) {
			result.getAllErrors().forEach(e -> response.getErrors().add(e.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
				
		UserWallet userWallet = service.save(this.convertDtoToEntity(dto));
		
		response.setData(this.convertEntityToDto(userWallet));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	private UserWallet convertDtoToEntity(UserWalletDTO dto) {
		UserWallet uw = new UserWallet();
		uw.setId(dto.getId());
		
		User u = new User();
		u.setId(dto.getUsers());
		uw.setUsers(u);
		
		Wallet w =new Wallet();
		w.setId(dto.getWallet());
		uw.setWallet(w);
		
		return uw;
	}
	
	private UserWalletDTO convertEntityToDto(UserWallet uw) {
		UserWalletDTO dto = new UserWalletDTO();
		dto.setId(uw.getId());
		dto.setUsers(uw.getUsers().getId());
		dto.setWallet(uw.getWallet().getId());
		return dto;
	}
}
