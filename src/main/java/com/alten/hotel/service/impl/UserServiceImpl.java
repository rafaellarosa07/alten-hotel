package com.alten.hotel.service.impl;

import com.alten.hotel.dto.NewUserDTO;
import com.alten.hotel.dto.UserViewDTO;
import com.alten.hotel.exception.ApiException;
import com.alten.hotel.exception.messages.Messages;
import com.alten.hotel.model.User;
import com.alten.hotel.repository.UserRepository;
import com.alten.hotel.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

  private UserRepository repository;
  private ModelMapper mapper;


  @Autowired
  public UserServiceImpl(UserRepository repository, ModelMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  /**
   * Create and persist a new User
   *
   * @param newUserDTO - DTO representing a new User
   * @return UserViewDTO
   */
  @Override
  public UserViewDTO create(NewUserDTO newUserDTO) {
    var user = new User(newUserDTO.getId(), newUserDTO.getName());
    repository.save(user);
    return mapper.map(user, UserViewDTO.class);
  }

  /**
   * Update an existing user
   *
   * @param userDTO
   * @return UserViewDTO
   */
  @Override
  public UserViewDTO update(NewUserDTO userDTO) {
    var user = repository.findById(userDTO.getId())
            .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND,
                    Messages.ERROR_USER_NOT_FOUND));
    user.setName(userDTO.getName());
    repository.save(user);
    return mapper.map(user, UserViewDTO.class);
  }


  /**
   * Delete an existing user
   *
   * @param id
   */
  @Override
  public void delete(Long id) {
    var user = repository.findById(id).orElseThrow(() ->
            new ApiException(HttpStatus.NOT_FOUND,
            Messages.ERROR_USER_NOT_FOUND));
    repository.delete(user);
  }

  /**
   * find all users
   *
   * @return List<UserViewDTO>
   */
  @Override
  public List<UserViewDTO> getAll() {
    var users = repository.findAll();
    return users.stream().map(user -> mapper.map(user, UserViewDTO.class))
            .collect(Collectors.toList());
  }


  /**
   * find user by id
   *
   * @param id
   * @return UserViewDTO
   */
  @Override
  public UserViewDTO findById(long id) {
    var user = repository.findById(id).orElseThrow(
            () -> new ApiException(HttpStatus.NOT_FOUND,
                    Messages.ERROR_USER_NOT_FOUND));
    return mapper.map(user, UserViewDTO.class);
  }
}
