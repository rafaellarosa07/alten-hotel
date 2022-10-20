package com.alten.hotel.service;

import com.alten.hotel.dto.NewUserDTO;
import com.alten.hotel.dto.UserViewDTO;

import java.util.List;

public sealed interface UserService permits UserServiceImpl{

  UserViewDTO create(NewUserDTO newUserDTO);

  UserViewDTO update(NewUserDTO newUserDTO);

  UserViewDTO delete(Long id);

  List<UserViewDTO> getAll();

  UserViewDTO findById(long id);
}
