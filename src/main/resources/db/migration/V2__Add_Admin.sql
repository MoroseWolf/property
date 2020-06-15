insert into users (id, username, password, active)
values (1, 'admin', '$2y$12$LXVa2WoJKk2uepM1x4vZ4uK78ZNRICUaqCTGGJ3L7AcqO6NkRV3nC', true);
--password: 12345

insert into roles (user_id, roles)
values (1, 'USER'),
       (1, 'ADMIN');
