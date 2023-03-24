
--1. query all `course` and `user` details along with all users those have roles `TA` or `INSTRUCTOR` by `:course_id` 
SELECT c.*, u.* 
FROM course c
JOIN course_attendant ca ON c.id = ca.course_id
JOIN "user" u ON ca.attendant_id = u.id
JOIN user_role ur ON u.id = ur.user_id
JOIN role r ON ur.role_id = r.id
WHERE c.id = :course_id AND r.name IN ('TA', 'INSTRUCTOR');

-- 1.1. query to perform the above requirement but using `Subquery`
SELECT c.*, u.*
FROM course c, user u
WHERE c.id = :course_id AND u.id IN (
    SELECT ca.attendant_id
    FROM course_attendant ca
    WHERE ca.course_id = :course_id
) AND u.id IN (
    SELECT ur.user_id
    FROM user_role ur
    WHERE ur.role_id IN (
        SELECT r.id
        FROM Role r
        WHERE r.name IN ('TA', 'INSTRUCTOR')
    )
);

-- 1.2. perform above requirement, except details from `Role` has been added

SELECT c.id, c.course_title, c.course_description, u.username, r.name
FROM course c, "user" u, role r
WHERE c.id = 7 AND u.id IN (
    SELECT ca.attendant_id
    FROM course_attendant ca
    WHERE ca.course_id = 7
) AND u.id IN (
    SELECT ur.user_id
    FROM user_role ur
    WHERE ur.role_id IN (
        SELECT r.id
        WHERE r.name IN ('TA', 'INSTRUCTOR')
    )
);

-- 1.3.  perform the above requirement, but `Correlated Subquery`

SELECT c.id, c.course_title
FROM course c
WHERE EXISTS (
    SELECT 1
    FROM course_attendant ca
    JOIN User u ON ca.attendant_id = u.id
    JOIN user_role ur ON u.id = ur.user_id
    JOIN Role r ON ur.role_id = r.id
    WHERE ca.course_id = c.id AND r.name IN ('TA', 'TA1')
);

-- query all course, and its related entities (course_task, course_notice) where `course_id = :course_id`
SELECT 
    c.id, c.course_title, c.course_description, 
    ct.task_title, cn.notice_title
FROM course c 
LEFT JOIN course_task ct ON c.id = ct.course_id
LEFT JOIN course_notice cn ON c.id = cn.course_id
WHERE c.id = :course_id
