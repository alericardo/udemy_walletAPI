package com.wallet.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.wallet.entities.User;
import com.wallet.repository.UserRepository;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@MockBean
	private UserRepository repository;
	
	@Autowired
	private UserService service;
	
	@BeforeEach
	public void setup() {
		BDDMockito.given(repository.findByEmailEquals(anyString())).willReturn(Optional.of(new User()));
	}
	
	@Test
	public void testFindByEmail() {
		Optional<User> user = service.findByEmail("email@teste.com");
		assertNotNull(user.isPresent());
	}
}
