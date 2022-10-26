package com.alten.hotel.service;

import com.alten.hotel.dto.RoomDTO;
import com.alten.hotel.enumaration.RoomStatus;

import java.util.List;

public interface RoomService {

  List<RoomDTO> getAll();

  RoomDTO findById(long id);

  RoomStatus checkAvailability(long id);

  List<RoomDTO> getAllAvailableRooms(RoomStatus status);
}
