package com.wallet.security.controller.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class JwtAuthenticationDTO {
	
	@NotNull(message = "Informe um email")
	private String email;
	@NotNull(message = "Informe uma senha")
	private String password;

}
