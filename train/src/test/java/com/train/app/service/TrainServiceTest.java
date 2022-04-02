package com.train.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.train.app.dto.TrainRequestDTO;
import com.train.app.entity.Train;
import com.train.app.exception.TrainNotFoundException;
import com.train.app.repository.TrainRepository;
import com.train.app.service.TrainService;

@ExtendWith(MockitoExtension.class)
public class TrainServiceTest {
	
	@InjectMocks
	TrainService trainService;
	
	@Mock
	TrainRepository trainRepository; //Mock object
	
	TrainRequestDTO trainRequestDTO;
	Train train;
	
	@BeforeEach
	public void setUp() {
		trainRequestDTO = new TrainRequestDTO();
		train = new Train();
		
		trainRequestDTO.setSource("Guadalajara");
		trainRequestDTO.setDestination("CDMX");
		trainRequestDTO.setDate("2022/04/4");
		
		train.setTrainId(1);
		train.setTimeLeaves(LocalTime.of(20,30,0));
		train.setTimeArrive(LocalTime.of(5,0,0));
		train.setPrice(234.00);
		train.setRail("Right side");
	}
	
	@Test
	@DisplayName("Save trains details: positive")
	public void saveTrainTest() {
		when(trainRepository.save(any(Train.class))).thenAnswer(i -> {
			Train train = i.getArgument(0);
			train.setTrainId(1);
			return train;
		});
		
		Train trainResult = trainService.saveTrains(train);
		assertNotNull(trainResult);
		assertEquals(1, trainResult.getTrainId());
		assertEquals(" ", trainResult.getDestination());
	}
	
	@Test
	@DisplayName("Save trains details: negative")
	public void saveTrainsNegative() {
		when(trainService.getTrainByTrainId(1)).thenReturn(Optional.empty());
		
		assertThrows(TrainNotFoundException.class, () -> trainService.saveTrains(train));
	}
}