insert into roles (name)
values
    ('ROLE_USER'), ('ROLE_ADMIN');

insert into users (nickname, name, surname, password, mail, enabled, phone_number)
values
    ('u', 'user', 'us', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'user@gmail.com', TRUE,'89300000'),
    ('a', 'admin', 'ad', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'admin@gmail.com', TRUE,'89300000');

insert into users_roles (user_id, role_id)
values
    (1, 1),
    (2, 2);