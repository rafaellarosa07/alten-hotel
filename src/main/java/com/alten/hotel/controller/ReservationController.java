package com.alten.hotel.controller;

import com.alten.hotel.dto.ReservationDTO;
import com.alten.hotel.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

  private ReservationService reservationService;

  @Autowired
  public ReservationController(ReservationService reservationService){
    this.reservationService = reservationService;
  }


  @PostMapping
  public ResponseEntity<ReservationDTO> reserveARoom(@RequestBody @Valid ReservationDTO reservationDTO) {
    var reservation = reservationService.reserveARoom(reservationDTO);
    return new ResponseEntity<>(reservation, HttpStatus.CREATED);
  }

  @GetMapping
  public List<ReservationDTO> getAll() {
    return reservationService.getAll();
  }

  @GetMapping("{id}")
  public ResponseEntity<ReservationDTO> getById(@PathVariable("id") long Id) {
    var reservationViewDTO = reservationService.findById(Id);
    return new ResponseEntity<>(reservationViewDTO, HttpStatus.OK);
  }

  @PutMapping
  public ResponseEntity<ReservationDTO> modifyAReservation(@RequestBody @Valid ReservationDTO reservationDTO) {
    var reservation = reservationService.modifyAReservation(reservationDTO);
    return new ResponseEntity<>(reservation, HttpStatus.ACCEPTED);
  }

}
