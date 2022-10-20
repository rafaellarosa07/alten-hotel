package com.alten.hotel.service;

import com.alten.hotel.dto.HotelViewDTO;
import com.alten.hotel.dto.NewHotelDTO;

import java.util.List;

sealed public interface HotelService permits HotelServiceImpl {

  HotelViewDTO create(NewHotelDTO hotelDTO);

  HotelViewDTO update(NewHotelDTO hotelDTO);

  HotelViewDTO delete(Long id);

  List<HotelViewDTO> getAll();

  HotelViewDTO findById(long id);
}
