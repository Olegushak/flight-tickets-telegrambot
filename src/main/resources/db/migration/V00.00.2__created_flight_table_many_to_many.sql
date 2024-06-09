DROP TABLE IF EXISTS flight;
DROP TABLE IF EXISTS flight_x_user;


CREATE TABLE flight (
    id varchar(100) PRIMARY KEY,
    departure_airport varchar(100) NOT NULL ,
    from_country varchar(100) NOT NULL ,
    from_city varchar(100) NOT NULL,
    destination_airport varchar(100) NOT NULL,
    to_country varchar(100) NOT NULL,
    to_city varchar(100) NOT NULL,
    dep_time varchar(100) NOT NULL,
    arr_time varchar(100) NOT NULL,
    duration INT NOT NULL,
    price varchar(100) NOT NULL,
    token varchar(100) NOT NULL
);

CREATE TABLE flight_x_user (
    flight_id VARCHAR(100) NOT NULL,
    user_id VARCHAR(100) NOT NULL,
    FOREIGN KEY(user_id) REFERENCES tg_user(chat_id),
    FOREIGN KEY (flight_id) REFERENCES flight(id),
    UNIQUE(user_id,flight_id)
);