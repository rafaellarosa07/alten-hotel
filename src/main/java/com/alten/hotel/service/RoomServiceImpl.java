package com.alten.hotel.service;

import com.alten.hotel.dto.RoomDTO;
import com.alten.hotel.repository.RoomRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public final class RoomServiceImpl implements RoomService {


  private RoomRepository repository;
  private ModelMapper mapper;

  @Autowired
  public RoomServiceImpl(RoomRepository repository, ModelMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  /**
   * find all hotels
   * @return List<HotelViewDTO>
   */
  @Override
  public List<RoomDTO> getAll() {
    var rooms = repository.findAll();
    return rooms.stream().map(room -> mapper.map(room, RoomDTO.class))
            .collect(Collectors.toList());
  }

  /**
   * find hotel by id
   * @param id
   * @return HotelViewDTO
   */
  @Override
  public RoomDTO findById(long id) {
    var room = repository.findById(id).orElseThrow(
            () -> new RuntimeException("Hotel not found"));
    return mapper.map(room, RoomDTO.class);
  }
}
