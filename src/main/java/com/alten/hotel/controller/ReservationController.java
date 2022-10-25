package com.alten.hotel.controller;

import com.alten.hotel.dto.ReservationDTO;
import com.alten.hotel.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

  private ReservationService reservationService;

  @Autowired
  public ReservationController(ReservationService reservationService) {
    this.reservationService = reservationService;
  }


  @PostMapping
  public ResponseEntity<?> reserveARoom(@RequestBody @Valid ReservationDTO reservationDTO) {
    var reservation = reservationService.reserveARoom(reservationDTO);
    return new ResponseEntity<>(reservation, HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<?> getAll() {
    return new ResponseEntity<>(reservationService.getAll(), HttpStatus.OK);
  }

  @GetMapping("{id}")
  public ResponseEntity<?> getById(@PathVariable("id") long Id) {
    var reservationViewDTO = reservationService.findById(Id);
    return new ResponseEntity<>(reservationViewDTO, HttpStatus.OK);
  }

  @PutMapping
  public ResponseEntity<?> modifyAReservation(@RequestBody @Valid ReservationDTO reservationDTO) {
    var reservation = reservationService.modifyAReservation(reservationDTO);
    return new ResponseEntity<>(reservation, HttpStatus.ACCEPTED);
  }

  @GetMapping("/cancel/{number}")
  public ResponseEntity<?> cancelAReservation(@PathVariable("number") long number) {
    reservationService.cancelAReservation(number);
    return new ResponseEntity<>(HttpStatus.OK);
  }

}
