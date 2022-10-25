package com.alten.hotel.model;

import com.alten.hotel.enumaration.RoomStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "Room")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Room {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "NUMBER", nullable = false)
  private Long number;

  @Enumerated(EnumType.STRING)
  @Column(name = "STATUS", nullable = false)
  private RoomStatus status;

}
