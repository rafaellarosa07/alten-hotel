package com.alten.hotel.controller;

import com.alten.hotel.dto.RoomDTO;
import com.alten.hotel.enumaration.RoomStatus;
import com.alten.hotel.service.RoomService;
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
  public List<RoomDTO> getAll() {
    return roomService.getAll();
  }

  @GetMapping(value = "/by-status/{status}")
  public List<RoomDTO> getAllByStatus(@PathVariable("status") RoomStatus status) {
    return roomService.getAllAvailableRooms(status);
  }

  @GetMapping("{id}")
  public ResponseEntity<RoomDTO> getById(@PathVariable("id") long roomId) {
    var roomViewDTO = roomService.findById(roomId);
    return new ResponseEntity<>(roomViewDTO, HttpStatus.OK);
  }

}
