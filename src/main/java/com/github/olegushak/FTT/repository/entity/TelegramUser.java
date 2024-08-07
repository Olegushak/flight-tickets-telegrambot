package com.github.olegushak.FTT.repository.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Data
@Entity
@Table(name = "tg_user")
@NoArgsConstructor
@EqualsAndHashCode
public class TelegramUser {

    @Id
    @Column(name = "chat_id")
    private String chatId;

    @Column(name = "active")
    private boolean active;

    @Column(name = "market")
    private String market;

    @Column(name = "locale")
    private String locale;

    @Column(name = "currency")
    private String currency;

    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    private List<Flight> flights;


}
