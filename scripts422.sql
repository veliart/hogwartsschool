/*
Описание структуры: у каждого человека есть машина.
Причем несколько человек могут пользоваться одной машиной.
У каждого человека есть имя, возраст и признак того, что у него есть права (или их нет).
У каждой машины есть марка, модель и стоимость.
Также не забудьте добавить таблицам первичные ключи и связать их.
*/

CREATE TABLE people (
    id SERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(50),
    age INT,
    driverLicense BOOL,
    car_id INT REFERENCES car(id));

CREATE TABLE car (
    id SERIAL PRIMARY KEY NOT NULL,
    model VARCHAR(50),
    mark VARCHAR(50),
    cost DECIMAL(10, 2)
);