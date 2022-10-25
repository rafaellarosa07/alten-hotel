package com.alten.hotel.service;

import com.alten.hotel.dto.RoomDTO;
import com.alten.hotel.enumaration.RoomStatus;
import com.alten.hotel.exception.ApiException;
import com.alten.hotel.exception.messages.Messages;
import com.alten.hotel.repository.RoomRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {


  private RoomRepository repository;
  private ModelMapper mapper;

  @Autowired
  public RoomServiceImpl(RoomRepository repository, ModelMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  /**
   * find all rooms
   *
   * @return List<RoomDTO>
   */
  @Override
  public List<RoomDTO> getAll() {
    var rooms = repository.findAll();
    return rooms.stream().map(room -> mapper.map(room, RoomDTO.class))
            .collect(Collectors.toList());
  }

  /**
   * find all available rooms
   *
   * @return List<RoomDTO>
   */
  @Override
  public List<RoomDTO> getAllAvailableRooms(RoomStatus status) {
    var rooms = repository.findByStatus(status);
    return rooms.stream().map(room -> mapper.map(room, RoomDTO.class))
            .collect(Collectors.toList());
  }

  /**
   * find room by id
   *
   * @param id
   * @return RoomDTO
   */
  @Override
  public RoomDTO findById(long id) {
    var room = repository.findById(id).orElseThrow(
            () -> new ApiException(HttpStatus.NOT_FOUND, Messages.ERROR_ROOM_NOT_FOUND));
    return mapper.map(room, RoomDTO.class);
  }


  /**
   * Check if the room is available
   *
   * @param id
   * @return RoomStatus
   */
  @Override
  public RoomStatus checkAvailability(long id) {
    return findById(id).getStatus();
  }

}
