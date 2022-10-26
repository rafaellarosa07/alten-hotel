package com.alten.hotel.controller;

import com.alten.hotel.dto.NewUserDTO;
import com.alten.hotel.dto.UserViewDTO;
import com.alten.hotel.exception.ApiException;
import com.alten.hotel.exception.messages.Messages;
import com.alten.hotel.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

  private static final String PATH = "/user";
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private UserService userService;

  private UserViewDTO userDTO;
  private List<UserViewDTO> userDTOS;

  @BeforeEach
  void setUp() {
    setUser();
    userDTOS = new ArrayList<>();
    userDTOS.add(userDTO);
  }
  @Test
  void getAll() throws Exception {
    when(userService.getAll()).thenReturn(userDTOS);
    mockMvc.perform(get(PATH)).andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id", is(1)));
  }

  @Test
  void getById() throws Exception {
    long id = 1L;
    when(userService.findById(1)).thenReturn(userDTO);
    this.mockMvc.perform(get(PATH + "/{id}", id)).andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(1)));
  }
  @Test
  void deleteSuccessfully() throws Exception {
    Long id = 1L;
    doNothing().when(userService).delete(id);
    mockMvc.perform(get(PATH + "/{id}", id))
            .andExpect(status().isOk());
  }

  @Test
  void deleteShouldThrowException() throws Exception {
    Long id = 1L;
    Mockito.doThrow(new ApiException(HttpStatus.NOT_FOUND, Messages.ERROR_USER_NOT_FOUND))
            .when(userService).delete(id);
    mockMvc.perform(delete(PATH + "/{id}", id)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
  }


  @Test
  public void createUserSuccessfully() throws Exception {
    NewUserDTO newUserDTO = new NewUserDTO();
    newUserDTO.setName("User test 1");
    when(userService.create(newUserDTO)).thenReturn(userDTO);
    mockMvc.perform(post(PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(newUserDTO)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name", is(userDTO.getName())));
  }

  @Test
  public void updateUserSuccessfully() throws Exception {
    NewUserDTO newUserDTO = new NewUserDTO();
    newUserDTO.setName("User test 1");
    when(userService.update(newUserDTO)).thenReturn(userDTO);
    mockMvc.perform(put(PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(newUserDTO)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name", is(userDTO.getName())));
  }


  private void setUser(){
    userDTO = new UserViewDTO();
    userDTO.setId(1L);
    userDTO.setName("User test 1");
  }
}