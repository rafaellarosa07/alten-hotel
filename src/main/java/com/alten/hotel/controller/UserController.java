package com.alten.hotel.controller;

import com.alten.hotel.dto.NewUserDTO;
import com.alten.hotel.dto.UserViewDTO;
import com.alten.hotel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

  private UserService userService;

  @Autowired
  public UserController(UserService userService){
    this.userService = userService;
  }


  @PostMapping
  public ResponseEntity<?> create(@RequestBody @Valid NewUserDTO newUserDTO) {
    var reservation = userService.create(newUserDTO);
    return new ResponseEntity<>(reservation, HttpStatus.CREATED);
  }

  @PutMapping
  public ResponseEntity<?> update(@RequestBody @Valid NewUserDTO newUserDTO) {
    var reservation = userService.update(newUserDTO);
    return new ResponseEntity<>(reservation, HttpStatus.CREATED);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<UserViewDTO> delete(@PathVariable("id") long Id) {
    userService.delete(Id);
    return new ResponseEntity<>(HttpStatus.OK);
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
