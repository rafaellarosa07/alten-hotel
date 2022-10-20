package com.alten.hotel.service;

import com.alten.hotel.dto.ReservationDTO;
import com.alten.hotel.repository.ReservationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public final class ReservationServiceImpl implements ReservationService {


  private ReservationRepository repository;
  private ModelMapper mapper;

  @Autowired
  public ReservationServiceImpl(ReservationRepository repository, ModelMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  /**
   * find all hotels
   * @return List<HotelViewDTO>
   */
  @Override
  public List<ReservationDTO> getAll() {
    var reservations = repository.findAll();
    return reservations.stream().map(reservation -> mapper.map(reservation, ReservationDTO.class))
            .collect(Collectors.toList());
  }

  /**
   * find hotel by id
   * @param id
   * @return HotelViewDTO
   */
  @Override
  public ReservationDTO findById(long id) {
    var reservation = repository.findById(id).orElseThrow(
            () -> new RuntimeException("Hotel not found"));
    return mapper.map(reservation, ReservationDTO.class);
  }
}
