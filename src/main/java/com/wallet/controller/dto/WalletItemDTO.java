package com.wallet.controller.dto;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Data;

@Data
public class WalletItemDTO {

	private Long id;
	@NotNull(message = "Insira o id da carteira")
	private Long wallet;
	@NotNull(message = "Informe uma data")
	@JsonFormat(shape = Shape.STRING, pattern = "dd-MM-yyyy", locale = "pt-BR", timezone = "Brazil/East")
	private Date date;
	@NotNull(message = "Informe um tipo")
	@Pattern(regexp="^(ENTRADA|SAIDA)$", message = "Para o tipo somente são aceitos os valores ENTRADA ou SAIDA")
	private String type;
	@NotNull(message = "Informe uma descrição")
	private String description;
	@NotNull(message = "Informe um valor")
	private BigDecimal value;
}
