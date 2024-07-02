CREATE TABLE IF NOT EXISTS flight (
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

CREATE TABLE IF NOT EXISTS flight_x_user (
    flight_id VARCHAR(100) REFERENCES flight(id) ON DELETE CASCADE,
    user_id VARCHAR(100) REFERENCES tg_user(chat_id) ON DELETE CASCADE,
    UNIQUE(flight_id,user_id)
);