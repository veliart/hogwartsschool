/*
Составить первый JOIN-запрос,
чтобы получить информацию обо всех студентах
(достаточно получить только имя и возраст студента) школы Хогвартс
вместе с названиями факультетов.

Составить второй JOIN-запрос,
чтобы получить только тех студентов, у которых есть аватарки.
*/


SELECT
	student.name,
	age,
	faculty.name
FROM student
LEFT JOIN faculty ON student.faculty_id = faculty.id;


SELECT
	student.name
FROM student
LEFT JOIN avatar ON student.id = avatar.student_id
WHERE avatar.id IS NOT NULL;