package com.alten.hotel.service;

import com.alten.hotel.dto.NewUserDTO;
import com.alten.hotel.dto.UserViewDTO;
import com.alten.hotel.exception.ApiException;
import com.alten.hotel.model.User;
import com.alten.hotel.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

  @InjectMocks
  private UserServiceImpl userService;
  @Mock
  private UserRepository repository;
  @Mock
  private ModelMapper mapper;

  private UserViewDTO userDTO;

  private User user;
  private List<UserViewDTO> userDTOS;
  private List<User> users;


  @BeforeEach
  void setUp() {
    setUser();
    setUserDTO();
    userDTOS = new ArrayList<>();
    userDTOS.add(userDTO);

    users = new ArrayList<>();
    users.add(user);
  }
  
  @Test
  void create() {
    NewUserDTO newUserDTO = new NewUserDTO(1L, "User test");

    when(mapper.map(user, UserViewDTO.class)).thenReturn(userDTO);

    UserViewDTO userViewDTO = userService.create(newUserDTO);

    verify(repository).save(user);
    verify(mapper).map(user, UserViewDTO.class);

    assertEquals(userViewDTO.getName(), newUserDTO.getName());

  }

  @Test
  void updateSuccess() {
    NewUserDTO newUserDTO = new NewUserDTO(1L, "User test");

    when(repository.save(user)).thenReturn(user);
    when(repository.findById(newUserDTO.getId())).thenReturn(Optional.of(user));
    when(mapper.map(user, UserViewDTO.class)).thenReturn(userDTO);

    var response = userService.update(newUserDTO);

    verify(repository).save(user);
    verify(mapper).map(user, UserViewDTO.class);

    assertEquals(response.getName(), newUserDTO.getName());
  }

  @Test
  void updateExceptionUserNotFound() {
    NewUserDTO newUserDTO = new NewUserDTO(1L, "User Test");
    when(repository.findById(newUserDTO.getId())).thenReturn(Optional.empty());

    Assertions.assertThrows(ApiException.class, () -> userService.update(newUserDTO));
  }

  @Test
  void deleteSuccess() {
    Long id = 1L;
    when(repository.findById(id)).thenReturn(Optional.of(user));

    userService.delete(id);

    verify(repository).delete(user);
  }

  @Test
  void deleteExceptionUserNotFound() {
    long id = 2L;

    when(repository.findById(id)).thenReturn(Optional.empty());

    Assertions.assertThrows(ApiException.class, () -> userService.delete(id));
  }

  @Test
  void getAll() {
    when(repository.findAll()).thenReturn(users);

    List<UserViewDTO> userViewDTOS = userService.getAll();
    Mockito.verify(repository).findAll();
    Mockito.verify(mapper).map(users.get(0), UserViewDTO.class);
    assertNotNull(userViewDTOS);
  }

  @Test
  void findByIdSuccess() {
    long id = 1L;
    when(repository.findById(id)).thenReturn(Optional.of(user));

    userService.findById(id);

    verify(repository).findById(id);
    verify(mapper).map(user, UserViewDTO.class);

  }

  @Test
  void findByIdNotfound() {
    long id = 2L;
    when(repository.findById(id)).thenReturn(Optional.empty());
    Assertions.assertThrows(ApiException.class, () -> userService.findById(id));
  }

  private void setUser(){
    user = new User();
    user.setId(1L);
    user.setName("User test");
  }

  private void setUserDTO(){
    userDTO = new UserViewDTO();
    userDTO.setId(1L);
    userDTO.setName("User test");
  }
}