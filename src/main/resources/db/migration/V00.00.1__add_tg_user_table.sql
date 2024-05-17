DROP TABLE IF EXISTS tg_user;

CREATE TABLE IF NOT EXISTS tg_user (
   chat_id VARCHAR(100),
   active BOOLEAN
);