package com.wallet.util.enums;

import java.util.Arrays;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TypeEnum {

	EN("ENTRADA"),
	SD("SAIDA");
	
	private final String value;
	
	public static TypeEnum getEnum(String value) {
		return Arrays.stream(TypeEnum.values())
				.filter(t -> t.value.equals(value))
				.findFirst().orElse(null);
	}
}
