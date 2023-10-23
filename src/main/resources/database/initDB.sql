CREATE TABLE IF NOT EXISTS users (
    id    BIGSERIAL PRIMARY KEY,
    nickname VARCHAR(127) NOT NULL,
    name VARCHAR(254) NOT NULL,
    surname VARCHAR(254) NOT NULL,
    password VARCHAR(254) NOT NULL,
    mail VARCHAR(254) NOT NULL unique,
    enabled BOOLEAN,
    phone_number varchar(254),
    activity BOOLEAN
    );

CREATE TABLE IF NOT EXISTS roles (
  id SERIAL PRIMARY KEY,
  name varchar(254) NOT NULL
);

CREATE TABLE IF NOT EXISTS users_roles (
  user_id BIGINT NOT NULL,
  role_id INT NOT NULL,
  PRIMARY KEY (user_id, role_id),
  FOREIGN KEY (user_id) references users (id),
  FOREIGN KEY (role_id) references roles (id)
);

CREATE TABLE IF NOT EXISTS chats (
    id BIGSERIAL PRIMARY KEY,
    first_username BIGSERIAL,
    second_username BIGSERIAL
);

CREATE TABLE IF NOT EXISTS messages (
    chat_id BIGSERIAL PRIMARY KEY,
    sender_username VARCHAR(254),
    message VARCHAR(254),
    FOREIGN KEY (chat_id) references chats (id)
);
