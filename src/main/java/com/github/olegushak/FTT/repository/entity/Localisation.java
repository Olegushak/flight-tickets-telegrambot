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
@Table(name = "locale_config")
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor

@Builder
public class Localisation {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "country")
    private String country;

    @Column(name = "market")
    private String market;

    @Column(name = "locale")
    private String locale;

    @Column(name = "currency")
    private String currency;

    @Column(name = "site")
    private String site;


}
