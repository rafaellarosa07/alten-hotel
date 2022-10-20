package com.alten.hotel.controller;

import com.alten.hotel.dto.HotelViewDTO;
import com.alten.hotel.dto.NewHotelDTO;
import com.alten.hotel.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/hotel")
public class HotelController {

  private HotelService hotelService;

  @Autowired
  public HotelController(HotelService hotelService){
    this.hotelService = hotelService;
  }


  @PostMapping
  public ResponseEntity<HotelViewDTO> create(@RequestBody NewHotelDTO resource) {
    HotelViewDTO hotel = hotelService.create(resource);
    return new ResponseEntity<>(hotel, HttpStatus.CREATED);
  }

  @GetMapping
  public List<HotelViewDTO> getAll() {
    return hotelService.getAll();
  }

  @GetMapping("{id}")
  public ResponseEntity<HotelViewDTO> getById(@PathVariable("id") long bagId) {
    HotelViewDTO hotelViewDTO = hotelService.findById(bagId);
    return new ResponseEntity<>(hotelViewDTO, HttpStatus.OK);
  }

}
