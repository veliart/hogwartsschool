-- liquibase formatted sql

-- changeset avahitov:1

CREATE INDEX student_name_index ON student(name);

-- changeset avahitov:2

CREATE INDEX faculty_name_color_index on faculty(name, color);