
-- SELECT region -------- 
select * from collection c where c.image_id is  null;
select * from collection;

-- user region
select * from user_role;
select u.id, u.created_by, u.updated_at, u.used_at  from "user" u;
select * from "user";
alter table "user" drop COLUMN to_at;


-- collection region
delete from collection c where c.id = 1;
select * from  collection where note_id is null;
select * from note where id = 43;
select * from collection_note;
select * from collection;

delete from collection where id = 3;

-- note region
select * from note order by note_title limit 10;
select n.id, n.note_title, n.note_link, n.note_owner from note n;
select n.id, n.used_at, n.created_by from note n where n.id = 42;
select n.id, n.used_at, n.created_by from note n where n.created_by is null;
select n.id, n.used_at, n.created_by from note n where n.created_by is not null;
select n.id, n.note_title, n.used_at, n.created_by, n.updated_at from note n where n.created_by is not null;
select n.id, n.used_at, n.created_by, n.created_at, n.updated_at from note n where n.created_at is not null;
UPDATE note SET used_at = CURRENT_TIMESTAMP WHERE id = 23;

-- comment region
select * from comment c where c.collection_id is not null;
SELECT
    c.id, c.message
FROM
    comment c
WHERE 
    c.created_by = 1 AND
    c.active is true AND 
    c.collection_id =  1   

delete from comment c where c.id = 10;
delete from comment c where c.collection_id = ?;


SELECT 
    n.id, 
    n.note_title, 
    n.note_desc, 
    n.note_link
  FROM
      note n
  WHERE 
    n.created_by = 2


 WITH images_cte AS (
            select 
                i.note_id, 
                string_agg(i.image_url, ',') as image_urls
            FROM 
                image i
            GROUP BY 
                i.note_id
        )

        SELECT 
            n.id, 
            n.note_title, 
            n.note_desc, 
            n.note_link, 
            n.created_by,
            i.image_urls
        FROM 
            note n 
        LEFT JOIN 
            images_cte i ON n.id = i.note_id
        WHERE 
            n.id = 42


-- SELECT 
--     count(n.id)
-- FROM 
--     note n
-- ORDER BY
--   CASE WHEN n.note_title = 'title' THEN n.note_title END;

WITH filtered_notes AS (
      SELECT 
        n.id, 
        n.note_title, 
        n.note_desc, 
        n.note_link
    FROM 
        note n
    ORDER BY
        CASE WHEN n.note_title = 'note_title' THEN n.note_title END)
SELECT COUNT(*) FROM filtered_notes;

drop table foo;
drop table bar;
drop table foo_bar;

drop table course;

SELECT 
    c.id,
    c.collection_title,
    c.collection_desc,
    i.id AS i_id,
    i.image_url
FROM 
    collection c
LEFT JOIN 
    image i ON c.id = i.collection_id
WHERE 
    (i.id = (SELECT MAX(i.id) FROM image i 
        WHERE i.collection_id = c.id AND i.created_at = (SELECT MAX(i.created_at) 
        FROM image i WHERE i.collection_id = c.id)) OR i.id IS NULL)
ANd c.id = 4;

SELECT 
      c.id,
      c.collection_title,
      c.collection_desc,
      i.id AS i_id,
      i.image_url
  FROM 
      collection c
  LEFT JOIN 
      image i ON c.id = i.collection_id
  WHERE 
      (i.id = (SELECT MAX(i.id) FROM image i 
            WHERE i.collection_id = c.id AND i.created_at = (SELECT MAX(i.created_at) 
                FROM image i WHERE i.collection_id = c.id)) OR i.id IS NULL) AND
      LOWER(c.collection_title) LIKE CONCAT('%', LOWER('dev'), '%') OR 
      LOWER(c.collection_desc) LIKE CONCAT('%', LOWER('Dev'), '%')

SELECT 
      c.id,
      c.collection_title,
      c.collection_desc,
      i.id AS i_id,
      i.image_url
  FROM 
    collection c
  LEFT JOIN 
      (SELECT 
        i.collection_id,
        MAX(i.id) AS i_id,
        MAX(i.created_at) AS max_created_at,
        FIRST_VALUE(i.image_url) OVER (PARTITION BY i.collection_id ORDER BY i.created_at DESC) AS image_url
      FROM 
          image i
      GROUP BY 
      i.collection_id) i ON c.id = i.collection_id
  WHERE 
      LOWER(c.collection_title) LIKE CONCAT('%', LOWER('dev'), '%') OR 
      LOWER(c.collection_desc) LIKE CONCAT('%', LOWER('Dev'), '%')

SELECT 
    c.id,
    c.collection_title,
    c.collection_desc,
    i.id AS i_id,
    i.image_url
FROM 
    collection c
LEFT JOIN 
    image i ON c.id = i.collection_id
WHERE 
    (i.id = (SELECT i.id FROM image i WHERE i.collection_id = c.id 
    ORDER BY i.created_at DESC LIMIT 1) OR i.id IS NULL)
    AND (LOWER(c.collection_title) LIKE CONCAT('%', LOWER('linux'), '%') OR 
    LOWER(c.collection_desc) LIKE CONCAT('%', LOWER('LinUx'), '%'))




-- image region
select count(id) from image where collection_id is not null;
select i.id, i.collection_id, i.created_at from image i where i.collection_id = 4;
select * from image c where c.collection_id is not null;
select id, note_id, image_url from image where note_id is not null;
select count(i.id) from image i where i.note_id is not null;
select count(i.id) from image i where i.note_id = 23;
select i.id, i.image_url, i.note_id  from image i where i.note_id = 23;
select i.id, i.image_url from image i order by i.created_at ASC;
select i.id, i.created_by from image i where i.created_by is not null;
SELECT 
        i.id, 
        i.image_url
    FROM 
        image i
    WHERE 
        i.note_id = 23 AND 
        i.created_by = 2
    ORDER BY 
        i.created_at ASC


SELECT 
    count(i.id) 
FROM 
    image i AND
WHERE 
    i.note_id = 43;
    -- AND i.created_by = 1


delete from image where collection_id = 4;

-- common table expression (query note_by_id with list of image_urls)
with images_cte as (
  select i.note_id, string_agg(i.image_url, ', ') as image_urls
  from image i
  group by i.note_id
)
select 
    n.id, n.note_title, n.note_desc, n.note_link, 
    i.image_urls
from note n 
join images_cte i on n.id = i.note_id
where n.id = 23;

with images_cte as (
            select i.note_id, string_agg(i.image_url, ',') as image_urls
            from image i
            group by i.note_id
        )
        select 
            n.id, 
            n.note_title, 
            n.note_desc, 
            n.note_link, 
            i.image_urls
        from 
            note n 
        join 
            images_cte i on n.id = i.note_id
        where 
            n.id = 23;


select count(c.id) from image c where c.note_id is not null;
select count(c.id) from image c where c.note_id = 23;
select count(i.id) from image i;

SELECT 
  i.id, 
  i.image_url
FROM 
  image i
WHERE 
    i.note_id = 23
ORDER BY 
    i.created_at ASC


select i.id, i.note_id from image i where i.note_id is not null;

update image set note_id = 23 where id = 8;


-- list all current tables
SELECT tablename
FROM pg_tables
WHERE schemaname = 'public';

select * from foo_bar;
select * from bar;
select * from foo;
select * from collection_comments;

SELECT 
  n.id, n.note_title, n.note_link,
  c_n.*
FROM 
  note n
JOIN 
  collection_note c_n ON c_n.collection_id = 2

-- nested query 
select n.id, n.note_title, n.note_link
from note n 
where n.id in (
  select c_n.note_id
  from collection_note c_n 
  where c_n.collection_id = 2
)

-- 
SELECT 
    i.id, i.image_url, n.id AS n_id
FROM 
    image i
JOIN 
    note n ON i.note_id = 43

-- 
SELECT 
    i.id, i.image_url, i.created_at
FROM 
    image i
RIGHT JOIN 
    note n ON i.note_id = 43
ORDER BY 
    i.created_at ASC
LIMIT 20;


select * from note n join collection_note cn on n.id = cn.note_id;
delete from note;

-- DROP region  --------
drop table collection_note;
drop table user_collections;
drop table collection_notes;
drop table user_my_collections;

delete from image i where i.image_url is null and i.note_id = 43;

ALTER TABLE table_name
DROP COLUMN column_name;

delete from image i where i.collection_id = 3;
select * from image i where i.collection_id = 4;
select * from image where note_id is not null;
alter table image set note_id = 43 where note_id is null;
update image set note_id = 43 where note_id is null;

DROP table image;

delete from collection c where c.id = 1;
alter table image add CONSTRAINT 

-- UPDTATE region ---

update note n set note_title = 'aaaa' where id = 32;

select *  from 
  collection c
LEFT join image i on c.id = i.collection_id 
where c.id = 1;
-- TRUNCATE TABLE image; cannot perform if table referenced in a foreign key constraint

-- write a store procedure for below query


-- select user records from user table where active field is not null
select * from "user" where active is not null;



select * from note;

SELECT 
      c.id, c.collection_title, c.collection_desc, 
      i.id AS i_id, i.image_url
  FROM collection c 
  LEFT JOIN image i ON c.id = i.collection_id
  WHERE 
      c.id = 1;


update  image set collection_id = 3 where id = 1;

 SELECT 
    c.*
    FROM collection c
    WHERE c.collection_title LIKE '%Networking%' OR c.collection_desc LIKE '%store%'

select * from "user";

update "user" set username = '_user' where name = 'user';
 
 update collection set collection_desc = 'Collections of Networking topic' where id = 3;

 select * from comment;
select * from image;

-- UPDATE collection SET collection_title = 'updated title' , collection_desc = 'updated description' WHERE id = 1;
-- Hibernate:
--     UPDATE
--         collection
--     SET
--         collection_title = ?,
--         collection_desc = ?
--     WHERE
--         id = ?

-- check current unique constraints from entity
SELECT conname, conrelid::regclass
FROM pg_constraint
WHERE conrelid = 'image'::regclass;