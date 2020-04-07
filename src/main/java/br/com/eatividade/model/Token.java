package br.com.eatividade.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

public class Token {

	@JsonProperty(value = "access_token")
	private @Getter @Setter String accessToken;
	
	@JsonProperty(value = "token_type")
	private @Getter @Setter String tokenType;
	
	@JsonProperty(value = "expires_in")
	private @Getter @Setter String expiresIn;
	
}
