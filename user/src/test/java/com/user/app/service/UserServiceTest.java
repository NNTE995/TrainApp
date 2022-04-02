package com.user.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.user.app.client.TrainClient;
import com.user.app.dto.UserRequestDTO;
import com.user.app.repository.UserRepository;
import com.user.app.client.BookingClient;
import com.user.app.dto.LoginRequestDTO;
import com.user.app.entity.User;
import com.user.app.exception.UserNotFoundException;
import com.user.app.model.Train;
import com.user.app.model.Booking;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
	
	@InjectMocks
	UserService userService;
	
	@Mock
	UserRepository userRepository; //Mock object
	
	UserRequestDTO userRequestDTO;
	User user;
	
	@BeforeEach
	public void setUp() {
		userRequestDTO = new UserRequestDTO();
		user = new User();
		
		userRequestDTO.setUsername("Diana Rodriguez");
		userRequestDTO.setPassword("1234");
		userRequestDTO.setEmail("diana@gmail.com");
		userRequestDTO.setPhoneNo("47959944");
		
		user.setUserId(1);
	}
	
	@Test
	@DisplayName("Save users details: positive")
	public void saveUserTest() {
		when(userRepository.save(any(User.class))).thenAnswer(i -> {
			User user = i.getArgument(0);
			user.setUserId(1);
			return user;
		});
		
		User userResult = userService.saveUsers(user);
		assertNotNull(userResult);
		assertEquals(1, userResult.getUserId());
		assertEquals("Diana Rodriguez", userResult.getUsername());
	}
	
	@Test
	@DisplayName("Save users details: negative")
	public void saveUsersNegative() {
		when(userService.getUserByUserId(1)).thenReturn(Optional.empty());
		
		assertThrows(UserNotFoundException.class, () -> userService.saveUsers(user));
	}
	
	@Test
	@DisplayName("login details: positive")
	public void login() {
		when(userRepository.save(any(User.class))).thenAnswer(i -> {
			User user = i.getArgument(0);
			user.setUserId(2);
			return user;
		});
		
		Optional<User> loginResult = userRepository.findByUsernameAndPassword("Diana Rodriguez", "1234");
		assertNotNull(loginResult);
		assertEquals(1, loginResult.get(user.getUserId()));
	}
}