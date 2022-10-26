package com.alten.hotel.controller;

import com.alten.hotel.dto.RoomDTO;
import com.alten.hotel.enumaration.RoomStatus;
import com.alten.hotel.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {

  private RoomService roomService;

  @Autowired
  public RoomController(RoomService roomService){
    this.roomService = roomService;
  }

  @GetMapping
  @Operation(
          summary = "Get all rooms",
          description = "Get all rooms",
          responses = {
                  @ApiResponse(responseCode = "200", description = "Request for Reservation successful"),
                  @ApiResponse(responseCode = "400", description = "Bad Request"),
                  @ApiResponse(responseCode = "500", description = "Internal Server Error")
          }
  )
  public List<RoomDTO> getAll() {
    return roomService.getAll();
  }

  @GetMapping(value = "/by-status/{status}")
  @Operation(
          summary = "Get all available rooms",
          description = "Get all available rooms",
          responses = {
                  @ApiResponse(responseCode = "200", description = "Request for Reservation successful"),
                  @ApiResponse(responseCode = "400", description = "Bad Request"),
                  @ApiResponse(responseCode = "500", description = "Internal Server Error")
          }
  )
  public List<RoomDTO> getAllByStatus(@PathVariable("status") RoomStatus status) {
    return roomService.getAllAvailableRooms(status);
  }

  @GetMapping("{id}")
  @Operation(
          summary = "Get room by id",
          description = "Get room by id",
          responses = {
                  @ApiResponse(responseCode = "200", description = "Request for Reservation successful"),
                  @ApiResponse(responseCode = "400", description = "Bad Request"),
                  @ApiResponse(responseCode = "500", description = "Internal Server Error")
          }
  )
  public ResponseEntity<RoomDTO> getById(@PathVariable("id") long roomId) {
    var roomViewDTO = roomService.findById(roomId);
    return new ResponseEntity<>(roomViewDTO, HttpStatus.OK);
  }

}
