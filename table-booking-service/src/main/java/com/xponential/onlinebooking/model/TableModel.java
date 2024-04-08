package com.xponential.onlinebooking.model;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "table_model")
public class TableModel {
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "reserved", nullable = false)
    private boolean reserved;

    @Column(name = "reservation_id", nullable = true)
    private String reservationId;

//    @ManyToOne()
//    @JoinColumn(name ="reservation_id", nullable = true)
//    private Reservation reservation;

}