package com.alten.hotel.controller;

import com.alten.hotel.dto.ReservationDTO;
import com.alten.hotel.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
  @Operation(
          summary = "Reserve a room",
          description = "Reserve a room",
          responses = {
                  @ApiResponse(responseCode = "200", description = "Request for Reservation successful"),
                  @ApiResponse(responseCode = "400", description = "Bad Request"),
                  @ApiResponse(responseCode = "500", description = "Internal Server Error")
          }
  )
  public ResponseEntity<?> reserveARoom(@RequestBody @Valid ReservationDTO reservationDTO) {
    var reservation = reservationService.reserveARoom(reservationDTO);
    return new ResponseEntity<>(reservation, HttpStatus.CREATED);
  }

  @GetMapping
  @Operation(
          summary = "Return all Reservations",
          description = "Return all Reservations",
          responses = {
                  @ApiResponse(responseCode = "200", description = "Request for Reservation successful"),
                  @ApiResponse(responseCode = "400", description = "Bad Request"),
                  @ApiResponse(responseCode = "500", description = "Internal Server Error")
          }
  )
  public ResponseEntity<?> getAll() {
    return new ResponseEntity<>(reservationService.getAll(), HttpStatus.OK);
  }

  @GetMapping("{id}")
  @Operation(
          summary = "Get Reservation By ID",
          description = "Return a Reservation By ID",
          responses = {
                  @ApiResponse(responseCode = "200", description = "Request for Reservation successful"),
                  @ApiResponse(responseCode = "400", description = "Bad Request"),
                  @ApiResponse(responseCode = "500", description = "Internal Server Error")
          }
  )
  public ResponseEntity<?> getById(@PathVariable("id") long Id) {
    var reservationViewDTO = reservationService.findById(Id);
    return new ResponseEntity<>(reservationViewDTO, HttpStatus.OK);
  }

  @PutMapping
  @Operation(
          summary = "Modify the Reservation",
          description = "Update a Reservation",
          responses = {
                  @ApiResponse(responseCode = "200", description = "Request for Reservation successful"),
                  @ApiResponse(responseCode = "400", description = "Bad Request"),
                  @ApiResponse(responseCode = "500", description = "Internal Server Error")
          }
  )
  public ResponseEntity<?> modifyAReservation(@RequestBody @Valid ReservationDTO reservationDTO) {
    var reservation = reservationService.modifyAReservation(reservationDTO);
    return new ResponseEntity<>(reservation, HttpStatus.ACCEPTED);
  }

  @GetMapping("/cancel/{number}")
  @Operation(
          summary = "Cancel the Reservation by number",
          description = "Cancel the Reservation by number",
          responses = {
                  @ApiResponse(responseCode = "200", description = "Request for Reservation successful"),
                  @ApiResponse(responseCode = "400", description = "Bad Request"),
                  @ApiResponse(responseCode = "500", description = "Internal Server Error")
          }
  )
  public ResponseEntity<?> cancelAReservation(@PathVariable("number") long number) {
    reservationService.cancelAReservation(number);
    return new ResponseEntity<>(HttpStatus.OK);
  }

}
