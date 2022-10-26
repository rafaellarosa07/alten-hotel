package com.alten.hotel.controller;

import com.alten.hotel.config.ApplicationConfig;
import com.alten.hotel.dto.ReservationDTO;
import com.alten.hotel.dto.RoomDTO;
import com.alten.hotel.dto.UserViewDTO;
import com.alten.hotel.enumaration.ReservationStatus;
import com.alten.hotel.enumaration.RoomStatus;
import com.alten.hotel.exception.ApiException;
import com.alten.hotel.exception.messages.Messages;
import com.alten.hotel.service.ReservationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Import(ApplicationConfig.class)
class ReservationControllerTest {

  private static final String PATH = "/reservation";
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private ReservationService reservationService;

  private ReservationDTO reservationDTO;
  private List<ReservationDTO> reservationDTOS;


  @BeforeEach
  void setUp() {
    setReservation();
    reservationDTOS = new ArrayList<>();
    reservationDTOS.add(reservationDTO);
  }

  @Test
  void reserveARoomSuccessfully() throws Exception {
    when(reservationService.reserveARoom(reservationDTO)).thenReturn(reservationDTO);
    mockMvc.perform(post(PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(reservationDTO)))
            .andExpect(status().isCreated());
  }

  @Test
  void getAll() throws Exception {
    when(reservationService.getAll()).thenReturn(reservationDTOS);
    mockMvc.perform(get(PATH)).andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id", Is.is(1)));
  }

  @Test
  void getById() throws Exception {
    long id = 1L;
    when(reservationService.findById(1)).thenReturn(reservationDTO);
    this.mockMvc.perform(get(PATH + "/{id}", id)).andExpect(status().isOk())
            .andExpect(jsonPath("$.id", Is.is(1)));
  }


  @Test
  void modifyAReservation() throws Exception {
    when(reservationService.modifyAReservation(reservationDTO)).thenReturn(reservationDTO);
    mockMvc.perform(put(PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(reservationDTO)))
            .andExpect(status().isAccepted());
  }

  @Test
  void cancelAReservation() throws Exception {
    Long numberReservation = 123123L;
    doNothing().when(reservationService).cancelAReservation(numberReservation);
    mockMvc.perform(get(PATH + "/cancel/{number}", numberReservation))
            .andExpect(status().isOk());
  }

  @Test
  void cancelAReservationErrorNotFound() throws Exception {
    Long numberReservation = 123124L;
    doThrow(new ApiException(HttpStatus.NOT_FOUND, Messages.ERROR_USER_NOT_FOUND)).when(reservationService).cancelAReservation(numberReservation);

    mockMvc.perform(get(PATH + "/cancel/{number}", numberReservation)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
  }


  private void setReservation() {
    reservationDTO = new ReservationDTO(1L, 123123L,
            LocalDateTime.of(LocalDate.of(2022, 10, 30),
                    LocalTime.of(12, 12)),
            LocalDateTime.of(LocalDate.of(2022, 10, 31),
                    LocalTime.of(12, 12)), new UserViewDTO(1L, "TESTE"),
            new RoomDTO(3L, 30L, RoomStatus.READY_TO_RESERVE), ReservationStatus.RESERVED);
  }
}