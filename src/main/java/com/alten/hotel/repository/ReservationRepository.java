package com.alten.hotel.repository;

import com.alten.hotel.enumaration.ReservationStatus;
import com.alten.hotel.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

  Optional<Reservation> findByNumber(Long numberReservation);

  @Query("SELECT r FROM Reservation r WHERE :date between r.dateCheckIn and r.dateCheckOut")
  Optional<Reservation> findReservationByDateBetweenPeriod(LocalDate date);

  @Modifying
  @Query("UPDATE Reservation SET status = :status  WHERE number = :numberReservation ")
  Reservation updateReservationByStatus(Long numberReservation, ReservationStatus status);

}
