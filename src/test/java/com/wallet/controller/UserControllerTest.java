package com.wallet.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wallet.controller.dto.UserDTO;
import com.wallet.entities.User;
import com.wallet.service.UserService;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class UserControllerTest {

	private static final String EMAIL = "email@teste.com";
	private static final String NAME = "User Test";
	private static final String PASSWORD = "123456";
	private static final String URL = "/user";

	@MockBean
	private UserService userService;
	
	@Autowired
	private MockMvc mvc;

	@Test
	public void testSave() throws Exception {
		BDDMockito.given(userService.save(any(User.class))).willReturn(getMockUser());
		
		mvc.perform(MockMvcRequestBuilders.post(URL).content(getJsonPayload())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated());
	}
	
	private User getMockUser() {
		User u = new User();
		u.setEmail(EMAIL);
		u.setName(NAME);
		u.setPassword(PASSWORD);
		return u;
	}
	
	public String getJsonPayload() throws JsonProcessingException {
		UserDTO dto = new UserDTO();
		dto.setEmail(EMAIL);
		dto.setName(NAME);
		dto.setPassword(PASSWORD);
		
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(dto);
	}
	
}
