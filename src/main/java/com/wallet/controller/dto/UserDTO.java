package com.wallet.controller.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class UserDTO {

	private Long id;
	@NotNull
	@Length(min = 6, message = "A senha deve conter pelo menos 6 caracteres")
	private String password;
	@Length(min = 3, max=50, message = "O nome deve conter entre 3 e 50 caracteres")
	private String name;
	@Email(message = "Email inválido")
	private String email;
}
