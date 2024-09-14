ALTER TABLE flight DROP CONSTRAINT flight_pkey CASCADE;

ALTER TABLE flight_x_user
ALTER COLUMN flight_id TYPE integer USING  (flight_id::integer);

ALTER TABLE flight
ALTER COLUMN id TYPE integer USING  (id::integer);

