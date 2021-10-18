INSERT INTO veterinary_clinic.owners (id, first_name, last_name, email) VALUES (1, 'Katarzyna', 'Bacek', 'random1@gmail.com');
INSERT INTO veterinary_clinic.owners (id, first_name, last_name, email) VALUES (2, 'Wilhelm', 'Natkaniec', 'random2@gmail.com');
INSERT INTO veterinary_clinic.owners (id, first_name, last_name, email) VALUES (3, 'Zenon', 'Rybicki', 'random3@gmail.com');
INSERT INTO veterinary_clinic.owners (id, first_name, last_name, email) VALUES (4, 'Konstantyn', 'Norek', 'random4@gmail.com');
INSERT INTO veterinary_clinic.owners (id, first_name, last_name, email) VALUES (5, 'Leopolda', 'Szymala', 'random5@gmail.com');
INSERT INTO veterinary_clinic.owners (id, first_name, last_name, email) VALUES (6, 'Olimpia', 'Zgoda', 'random6@gmail.com');
INSERT INTO veterinary_clinic.owners (id, first_name, last_name, email) VALUES (7, 'Rajmund', 'Kuczewski', 'random7@gmail.com');

INSERT INTO veterinary_clinic.doctors (id, first_name, last_name, nip, salary_for_hour, status) VALUES (1, 'Adam', 'Nowinski', '1234567891', 40, true);
INSERT INTO veterinary_clinic.doctors (id, first_name, last_name, nip, salary_for_hour, status) VALUES (2, 'Kasia', 'Nowiejska', '1234567892', 40, true);
INSERT INTO veterinary_clinic.doctors (id, first_name, last_name, nip, salary_for_hour, status) VALUES (3, 'Arek', 'Waski', '1234567893', 50, true);
INSERT INTO veterinary_clinic.doctors (id, first_name, last_name, nip, salary_for_hour, status) VALUES (4, 'Malgorzata', 'Kanclerz', '1234567894', 60, true);
INSERT INTO veterinary_clinic.doctors (id, first_name, last_name, nip, salary_for_hour, status) VALUES (5, 'Marietta', 'Kubera', '1234567895', 20, true);
INSERT INTO veterinary_clinic.doctors (id, first_name, last_name, nip, salary_for_hour, status) VALUES (6, 'Patrycja', 'Wawrzyniak', '1234567896', 10, true);
INSERT INTO veterinary_clinic.doctors (id, first_name, last_name, nip, salary_for_hour, status) VALUES (7, 'Tomek', 'Burek', '1234567897', 20, false);
INSERT INTO veterinary_clinic.doctors (id, first_name, last_name, nip, salary_for_hour, status) VALUES (8, 'Filip', 'Bonk', '1234567898', 30, true);
INSERT INTO veterinary_clinic.doctors (id, first_name, last_name, nip, salary_for_hour, status) VALUES (9, 'Andrzej', 'Kubis', '1234567899', 45, true);
INSERT INTO veterinary_clinic.doctors (id, first_name, last_name, nip, salary_for_hour, status) VALUES (10, 'Pola', 'Radosz', '1234567890', 18, true);

INSERT INTO veterinary_clinic.medical_specializations (id, name, doctor_id) VALUES (1, 'Kardiolog', 1);
INSERT INTO veterinary_clinic.medical_specializations (id, name, doctor_id) VALUES (2, 'Ginekolog', 1);
INSERT INTO veterinary_clinic.medical_specializations (id, name, doctor_id) VALUES (3, 'Chirurg', 3);
INSERT INTO veterinary_clinic.medical_specializations (id, name, doctor_id) VALUES (4, 'Naczyniowiec', 4);
INSERT INTO veterinary_clinic.medical_specializations (id, name, doctor_id) VALUES (5, 'Urolog', 5);
INSERT INTO veterinary_clinic.medical_specializations (id, name, doctor_id) VALUES (6, 'Neurolog', 5);
INSERT INTO veterinary_clinic.medical_specializations (id, name, doctor_id) VALUES (7, 'Alergolog', 6);
INSERT INTO veterinary_clinic.medical_specializations (id, name, doctor_id) VALUES (8, 'Epidemiolog', 7);
INSERT INTO veterinary_clinic.medical_specializations (id, name, doctor_id) VALUES (9, 'Diabetologia', 8);
INSERT INTO veterinary_clinic.medical_specializations (id, name, doctor_id) VALUES (10, 'Okulistyka', 9);
INSERT INTO veterinary_clinic.medical_specializations (id, name, doctor_id) VALUES (11, 'Psychiatra', 10);

INSERT INTO veterinary_clinic.pet_specializations (id, name, doctor_id) VALUES (1, 'Kon', 2);
INSERT INTO veterinary_clinic.pet_specializations (id, name, doctor_id) VALUES (2, 'Ryba', 1);
INSERT INTO veterinary_clinic.pet_specializations (id, name, doctor_id) VALUES (3, 'Pies', 3);
INSERT INTO veterinary_clinic.pet_specializations (id, name, doctor_id) VALUES (4, 'Pies', 5);
INSERT INTO veterinary_clinic.pet_specializations (id, name, doctor_id) VALUES (5, 'Kot', 4);
INSERT INTO veterinary_clinic.pet_specializations (id, name, doctor_id) VALUES (6, 'Mysz', 6);
INSERT INTO veterinary_clinic.pet_specializations (id, name, doctor_id) VALUES (7, 'Glonojad', 8);
INSERT INTO veterinary_clinic.pet_specializations (id, name, doctor_id) VALUES (8, 'Kot', 9);

INSERT INTO veterinary_clinic.pets (id, name, birth_date, breed, specs, owner_id) VALUES (1, 'Spencer', '2018-07-12', 'Bernardyn', 'Pies', 2);
INSERT INTO veterinary_clinic.pets (id, name, birth_date, breed, specs, owner_id) VALUES (2, 'Szerin', '2018-07-04', 'Owczarek', 'Pies', 1);
INSERT INTO veterinary_clinic.pets (id, name, birth_date, breed, specs, owner_id) VALUES (3, 'Riley', '2018-09-20', 'Polna', 'Mysz', 2);
INSERT INTO veterinary_clinic.pets (id, name, birth_date, breed, specs, owner_id) VALUES (4, 'Milik', '2017-05-04', 'Fryzjerski', 'Kon', 7);
INSERT INTO veterinary_clinic.pets (id, name, birth_date, breed, specs, owner_id) VALUES (5, 'Roger', '2020-07-11', 'Sum', 'Ryba', 4);
INSERT INTO veterinary_clinic.pets (id, name, birth_date, breed, specs, owner_id) VALUES (6, 'Dalida', '2019-11-07', 'Pers', 'Kot', 6);

INSERT INTO veterinary_clinic.visits (id, date, description, doctor_id, pet_id) VALUES (1, '2021-10-24 09:00:00', 'Boli noga', 1, 1);
INSERT INTO veterinary_clinic.visits (id, date, description, doctor_id, pet_id) VALUES (2, '2021-10-24 10:00:00', 'Problem z brzuchem', 2, 2);
INSERT INTO veterinary_clinic.visits (id, date, description, doctor_id, pet_id) VALUES (3, '2021-10-24 11:00:00', null, 3, 3);
INSERT INTO veterinary_clinic.visits (id, date, description, doctor_id, pet_id) VALUES (4, '2021-10-27 16:00:00', 'Porblem z plecami', 4, 4);
INSERT INTO veterinary_clinic.visits (id, date, description, doctor_id, pet_id) VALUES (5, '2021-10-28 08:00:00', 'Konsultacja', 5, 5);
INSERT INTO veterinary_clinic.visits (id, date, description, doctor_id, pet_id) VALUES (6, '2021-10-29 16:00:00', null, 6, 6);
INSERT INTO veterinary_clinic.visits (id, date, description, doctor_id, pet_id) VALUES (7, '2021-11-14 09:00:00', null, 5, null);
INSERT INTO veterinary_clinic.visits (id, date, description, doctor_id, pet_id) VALUES (8, '2021-12-15 11:00:00', null, 4, null);