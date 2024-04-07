package com.xponential.onlinebooking.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Entity
@Getter
@Setter
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue
    private UUID id;

//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "reservation")
//    private List<TableModel> tables;
}