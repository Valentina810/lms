-- добавление тестовых данных
-- DELETE
-- FROM lessons
-- WHERE lessons.id_lesson > 0;
--
-- DELETE
-- FROM users
-- WHERE users.id_user > 0;
-- INSERT INTO users (name, surname, login, password, token, email, date_birth, date_registration, avatar_url)
-- VALUES ('Иван', 'Иванов', 'Vasilii', 'orange', 'treerinxlx864xn', 'vasia23@mail.ru', '2000-01-01', '2023-08-29',
--         'https://pgcookbook.ru/programming/vasia.jpg'),
--        ('Ольга', 'Петрова', 'Olga', 'green', 'hjj76gvjdckdnck', 'olgapetrova@ya.ru', '1991-02-12', '2023-08-30',
--         'https://pgcookbook.ru/programming/olga.jpg');

DELETE
FROM subjects
WHERE subjects.id_subject > 0;
INSERT INTO subjects (name)
VALUES ('Математика'),
       ('Русский язык'),
       ('Литературное чтение'),
       ('Технология'),
       ('Английский язык'),
       ('Информатика'),
       ('Физкультура');
--
--
--
-- INSERT INTO lessons (user_id, subject_id, date, time_start, time_end, theory_url, practice_url, homework_url,
--                      progress,check_successfully)
-- VALUES ((SELECT users.id_user FROM users WHERE users.login='Vasilii'),
--         (SELECT subjects.id_subject FROM subjects WHERE subjects.name='Математика'),
--         '2023-09-06', '10:00:00', '10:40:00', 'https://habr.com/ru/articles/560336/theory_url.htm',
--         'https://habr.com/ru/practice_url.htm', 'https://habr.com/homework_url.htm', 0,
--         false),
--        ((SELECT users.id_user FROM users WHERE users.login='Vasilii'),
--         (SELECT subjects.id_subject FROM subjects WHERE subjects.name='Математика'),
--         '2023-09-07', '11:20:00', '12:00:00', 'https://habr.com/ru/articles/560336/theory_url.htm',
--         'https://habr.com/ru/practice_url.htm', 'https://habr.com/homework_url.htm', 0,
--         false),
--        ((SELECT users.id_user FROM users WHERE users.login='Vasilii'),
--         (SELECT subjects.id_subject FROM subjects WHERE subjects.name='Математика'),
--         '2023-09-08', '12:00:00', '12:40:00', 'https://habr.com/ru/articles/560336/theory_url.htm',
--         'https://habr.com/ru/practice_url.htm', 'https://habr.com/homework_url.htm', 0,
--         false),
--        ((SELECT users.id_user FROM users WHERE users.login='Vasilii'),
--         (SELECT subjects.id_subject FROM subjects WHERE subjects.name='Математика'),
--         '2023-09-09', '09:00:00', '09:40:00', 'https://habr.com/ru/articles/560336/theory_url.htm',
--         'https://habr.com/ru/practice_url.htm', 'https://habr.com/homework_url.htm', 0,
--         false),
--        ((SELECT users.id_user FROM users WHERE users.login='Vasilii'),
--         (SELECT subjects.id_subject FROM subjects WHERE subjects.name='Математика'),
--         '2023-09-10', '11:20:00', '09:40:00', 'https://habr.com/ru/articles/560336/theory_url.htm',
--         'https://habr.com/ru/practice_url.htm', 'https://habr.com/homework_url.htm', 0,
--         false),
--        ((SELECT users.id_user FROM users WHERE users.login='Vasilii'),
--         (SELECT subjects.id_subject FROM subjects WHERE subjects.name='Информатика'),
--         '2023-09-01', '12:20:00', '13:00:00', 'https://habr.com/ru/articles/560336/theory_url.htm',
--         'https://habr.com/ru/practice_url.htm', 'https://habr.com/homework_url.htm', 0,
--         false),
--        ((SELECT users.id_user FROM users WHERE users.login='Vasilii'),
--         (SELECT subjects.id_subject FROM subjects WHERE subjects.name='Информатика'),
--         '2023-09-06', '11:20:00', '12:00:00', 'https://habr.com/ru/articles/560336/theory_url.htm',
--         'https://habr.com/ru/practice_url.htm', 'https://habr.com/homework_url.htm', 0,
--         false),
--        ((SELECT users.id_user FROM users WHERE users.login='Vasilii'),
--         (SELECT subjects.id_subject FROM subjects WHERE subjects.name='Информатика'),
--         '2023-09-07', '10:20:00', '10:40:00', 'https://habr.com/ru/articles/560336/theory_url.htm',
--         'https://habr.com/ru/practice_url.htm', 'https://habr.com/homework_url.htm', 0,
--         false),
--        ((SELECT users.id_user FROM users WHERE users.login='Olga'),
--         (SELECT subjects.id_subject FROM subjects WHERE subjects.name='Информатика'),
--         '2023-09-08', '10:20:00', '11:00:00', 'https://habr.com/ru/articles/560336/theory_url.htm',
--         'https://habr.com/ru/practice_url.htm', 'https://habr.com/homework_url.htm', 0,
--         false),
--        ((SELECT users.id_user FROM users WHERE users.login='Olga'),
--         (SELECT subjects.id_subject FROM subjects WHERE subjects.name='Информатика'),
--         '2023-09-09', '10:20:00', '11:00:00', 'https://habr.com/ru/articles/560336/theory_url.htm',
--         'https://habr.com/ru/practice_url.htm', 'https://habr.com/homework_url.htm', 0,
--         false),
--        ((SELECT users.id_user FROM users WHERE users.login='Olga'),
--         (SELECT subjects.id_subject FROM subjects WHERE subjects.name='Информатика'),
--         '2023-09-10', '10:00:00', '10:40:00', 'https://habr.com/ru/articles/560336/theory_url.htm',
--         'https://habr.com/ru/practice_url.htm', 'https://habr.com/homework_url.htm', 0,
--         false),
--        ((SELECT users.id_user FROM users WHERE users.login='Olga'),
--         (SELECT subjects.id_subject FROM subjects WHERE subjects.name='Русский язык'),
--         '2023-09-11', '16:00:00', '16:40:00', 'https://habr.com/ru/articles/560336/theory_url.htm',
--         'https://habr.com/ru/practice_url.htm', 'https://habr.com/homework_url.htm', 0,
--         false),
--        ((SELECT users.id_user FROM users WHERE users.login='Olga'),
--         (SELECT subjects.id_subject FROM subjects WHERE subjects.name='Английский язык'),
--         '2023-09-06', '09:00:00', '09:40:00', 'https://habr.com/ru/articles/560336/theory_url.htm',
--         'https://habr.com/ru/practice_url.htm', 'https://habr.com/homework_url.htm', 0,
--         false),
--        ((SELECT users.id_user FROM users WHERE users.login='Olga'),
--         (SELECT subjects.id_subject FROM subjects WHERE subjects.name='Литературное чтение'),
--         '2023-09-07', '10:00:00', '10:40:00', 'https://habr.com/ru/articles/560336/theory_url.htm',
--         'https://habr.com/ru/practice_url.htm', 'https://habr.com/homework_url.htm', 0,
--         false),
--        ((SELECT users.id_user FROM users WHERE users.login='Olga'),
--         (SELECT subjects.id_subject FROM subjects WHERE subjects.name='Математика'),
--         '2023-09-08', '11:00:00', '11:40:00', 'https://habr.com/ru/articles/560336/theory_url.htm',
--         'https://habr.com/ru/practice_url.htm', 'https://habr.com/homework_url.htm', 0,
--         false);