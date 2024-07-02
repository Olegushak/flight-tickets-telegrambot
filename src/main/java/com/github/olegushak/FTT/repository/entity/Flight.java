package com.github.olegushak.FTT.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import static java.util.Objects.isNull;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "flight")
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor

@Builder
public class Flight {

    @Id
    private String id;

    @Column(name = "departure_airport")
    private String departure;

    @Column(name = "from_country")
    private String fromCountry;

    @Column(name = "from_city")
    private String fromCity;

    @Column(name = "destination_airport")
    private String destination;

    @Column(name = "to_country")
    private String toCountry;

    @Column(name = "to_city")
    private String toCity;

    @Column(name = "dep_time")
    private String depTime;

    @Column(name = "arr_time")
    private String arrTime;

    @Column(name = "duration")
    private int duration;

    @Column(name = "price")
    private String price;

    @Column(name = "token")
    private String token;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "flight_x_user",
            joinColumns = @JoinColumn(name = "flight_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )

    private List<TelegramUser> users;


    public void addUser(TelegramUser telegramUser) {
        if(isNull(users)) {
            users = new ArrayList<>();
        }

        users.add(telegramUser);
    }


}
