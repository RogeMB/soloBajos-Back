insert into categoria_entity (id, name) values ('746388fe-83eb-43f1-8851-9cab3008c0f2', 'Bajos de 4 Cuerdas');
insert into categoria_entity (id, name) values ('c42e7eb4-c433-4b02-a6e3-79363229e35d', 'Bajos de 5 Cuerdas');
insert into categoria_entity (id, name) values ('4015768b-5fe2-4473-b052-e0e7ca1bcc2f', 'Bajos de 6 Cuerdas');
insert into categoria_entity (id, name) values ('6c1adce1-6a22-4092-bcc3-4b85af7c730f', 'Bajos fretless');
insert into categoria_entity (id, name) values ('c9b2489d-aacc-4299-b7bf-add4eeec7551', 'Bajos zurdos');
insert into categoria_entity (id, name) values ('293402df-3a51-4d57-9886-51d2feae355f', 'Bajos acústicos');


insert into bass_entity (id, brand, model, frets, image, origin, built_year, price, discount, is_available, state, created_at, last_modified_date_at, categoria_id) values ('7ce17b0b-3266-4434-acc5-d3061447be32', 'Sadowsky', 'METROLINE 21-4 LIMITED EDITION WBT', 21, null, 'Alemania', 2021, 3190.0, 0.0, true, 'NEW', '2023-02-17', '2023-02-17', '746388fe-83eb-43f1-8851-9cab3008c0f2');
insert into bass_entity (id, brand, model, frets, image, origin, built_year, price, discount, is_available, state, created_at, last_modified_date_at, categoria_id) values ('f5ce8d89-cea0-4869-88c6-4e9b4a666dd8', 'Sadowsky', 'METROEXPRESS 21 VINTAGE J/J BASS MN BLK', 21, null, 'China', 2021, 777.0, 0.0, true, 'NEW', '2023-02-17', '2023-02-17', '746388fe-83eb-43f1-8851-9cab3008c0f2');
insert into bass_entity (id, brand, model, frets, image, origin, built_year, price, discount, is_available, state, created_at, last_modified_date_at, categoria_id) values ('033dd174-1f1e-4bbd-85f5-7f7812f89ea1', 'Sadowsky', 'METROEXPRESS 21 VINTAGE J/J BASS MN CAR', 21, null, 'China', 2021, 777.0, 0.0, true, 'NEW', '2023-02-17', '2023-02-17', '746388fe-83eb-43f1-8851-9cab3008c0f2');


insert into user_entity (id, full_name, username, email, password, avatar, birth_date, account_non_expired, account_non_locked, credentials_non_expired, enabled, roles, created_at, last_modified_date_at, last_password_change_at) values ('a520d2e5-16c1-44ce-a120-ed19c862d2bd', 'Roge Mohigefer', 'rogemb', 'rogelio@gmail.com', '$2a$12$cXidvZxqCFFvJDn2MsOjSetTMqHF9dOvqT6GTbzQF3gO0ZAASCpsy', null, '1989-06-09', true, true, true, true, 'ADMIN', '2023-02-17', '2023-02-17', '2023-02-17');
insert into user_entity (id, full_name, username, email, password, avatar, birth_date, account_non_expired, account_non_locked, credentials_non_expired, enabled, roles, created_at, last_modified_date_at, last_password_change_at) values ('8c5eedfc-df65-4552-8b37-84507e432a5a', 'Javier Mohigefer', 'javiermb', 'javier@gmail.com', '$2a$12$cXidvZxqCFFvJDn2MsOjSetTMqHF9dOvqT6GTbzQF3gO0ZAASCpsy', null, '1993-03-18', true, true, true, true, 'USER', '2023-02-17', '2023-02-17', '2023-02-17');