package com.alten.hotel.controller;

import com.alten.hotel.dto.UserViewDTO;
import com.alten.hotel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

  private UserService userService;

  @Autowired
  public UserController(UserService userService){
    this.userService = userService;
  }

  @GetMapping
  public List<UserViewDTO> getAll() {
    return userService.getAll();
  }

  @GetMapping("{id}")
  public ResponseEntity<UserViewDTO> getById(@PathVariable("id") long Id) {
    var userViewDTO = userService.findById(Id);
    return new ResponseEntity<>(userViewDTO, HttpStatus.OK);
  }

}
