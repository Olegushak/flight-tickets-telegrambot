package com.github.olegushak.FTT.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "airport")
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor

@Builder
public class Airport {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "iata")
    private String iata;

    @Column(name = "icao")
    private String icao;

    @Column(name = "airport_name")
    private String name;

    @Column(name = "location")
    private String location;

    @Column(name = "utc_time")
    private String time;

    @Column(name = "skyId")
    private String skyId;

}
