package com.alten.hotel.controller;

import com.alten.hotel.dto.NewUserDTO;
import com.alten.hotel.dto.UserViewDTO;
import com.alten.hotel.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
  @Operation(
          summary = "Create User",
          description = "Create User",
          responses = {
                  @ApiResponse(responseCode = "200", description = "Request for Reservation successful"),
                  @ApiResponse(responseCode = "400", description = "Bad Request"),
                  @ApiResponse(responseCode = "500", description = "Internal Server Error")
          }
  )
  public ResponseEntity<?> create(@RequestBody @Valid NewUserDTO newUserDTO) {
    var reservation = userService.create(newUserDTO);
    return new ResponseEntity<>(reservation, HttpStatus.CREATED);
  }

  @PutMapping
  @Operation(
          summary = "Update User",
          description = "Update User",
          responses = {
                  @ApiResponse(responseCode = "200", description = "Request for Reservation successful"),
                  @ApiResponse(responseCode = "400", description = "Bad Request"),
                  @ApiResponse(responseCode = "500", description = "Internal Server Error")
          }
  )
  public ResponseEntity<?> update(@RequestBody @Valid NewUserDTO newUserDTO) {
    var reservation = userService.update(newUserDTO);
    return new ResponseEntity<>(reservation, HttpStatus.CREATED);
  }

  @DeleteMapping("{id}")
  @Operation(
          summary = "Delete User",
          description = "Delete User",
          responses = {
                  @ApiResponse(responseCode = "200", description = "Request for Reservation successful"),
                  @ApiResponse(responseCode = "400", description = "Bad Request"),
                  @ApiResponse(responseCode = "500", description = "Internal Server Error")
          }
  )
  public ResponseEntity<UserViewDTO> delete(@PathVariable("id") long Id) {
    userService.delete(Id);
    return new ResponseEntity<>(HttpStatus.OK);
  }
  @GetMapping
  @Operation(
          summary = "Get All Users",
          description = "Get All Users",
          responses = {
                  @ApiResponse(responseCode = "200", description = "Request for Reservation successful"),
                  @ApiResponse(responseCode = "400", description = "Bad Request"),
                  @ApiResponse(responseCode = "500", description = "Internal Server Error")
          }
  )
  public List<UserViewDTO> getAll() {
    return userService.getAll();
  }

  @GetMapping("{id}")
  @Operation(
          summary = "Get User By ID",
          description = "Get User By ID",
          responses = {
                  @ApiResponse(responseCode = "200", description = "Request for Reservation successful"),
                  @ApiResponse(responseCode = "400", description = "Bad Request"),
                  @ApiResponse(responseCode = "500", description = "Internal Server Error")
          }
  )
  public ResponseEntity<UserViewDTO> getById(@PathVariable("id") long Id) {
    var userViewDTO = userService.findById(Id);
    return new ResponseEntity<>(userViewDTO, HttpStatus.OK);
  }

}
