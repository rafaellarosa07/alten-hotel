package com.alten.hotel.controller;

import com.alten.hotel.dto.RoomDTO;
import com.alten.hotel.enumaration.RoomStatus;
import com.alten.hotel.service.RoomService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RoomController.class)
class RoomControllerTest {

  private static final String PATH = "/room";
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private RoomService roomService;

  private RoomDTO roomDTO;
  private List<RoomDTO> roomDTOS;
  
  @BeforeEach
  void setUp() {
    setRoom();
    roomDTOS = new ArrayList<>();
    roomDTOS.add(roomDTO);
  }
  @Test
  void getAll() throws Exception {
    when(roomService.getAll()).thenReturn(roomDTOS);
    mockMvc.perform(get(PATH)).andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id", Is.is(1)));
  }

  @Test
  void getAllByStatus() throws Exception {
    when(roomService.getAllAvailableRooms(RoomStatus.READY_TO_RESERVE)).thenReturn(roomDTOS);
    mockMvc.perform(get(PATH+ "/by-status/{status}", RoomStatus.READY_TO_RESERVE)).andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id", Is.is(1)));
  }

  @Test
  void getById() throws Exception {
    long id = 1L;
    when(roomService.findById(1)).thenReturn(roomDTO);
    this.mockMvc.perform(get(PATH + "/{id}", id)).andExpect(status().isOk())
            .andExpect(jsonPath("$.id", Is.is(1)));
  }


  private void setRoom(){
    roomDTO = new RoomDTO();
    roomDTO.setId(1L);
    roomDTO.setNumber(10L);
    roomDTO.setStatus(RoomStatus.READY_TO_RESERVE);
  }
}