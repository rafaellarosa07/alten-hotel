package com.alten.hotel.service;

import com.alten.hotel.dto.NewUserDTO;
import com.alten.hotel.dto.UserViewDTO;

import java.util.List;

public interface UserService {

  UserViewDTO create(NewUserDTO newUserDTO);

  UserViewDTO update(NewUserDTO newUserDTO);

  void delete(Long id);

  List<UserViewDTO> getAll();

  UserViewDTO findById(long id);
}
