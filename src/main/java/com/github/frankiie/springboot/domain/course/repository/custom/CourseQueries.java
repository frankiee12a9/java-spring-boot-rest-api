package com.github.frankiie.springboot.domain.course.repository.custom;

public  class CourseQueries {
 
    public final static String FIND_BY_ID_DETAILS = """
        SELECT 
            c.id, c.course_title, c.course_description, 
            ct.task_title, cn.notice_title
        FROM course c 
        LEFT JOIN course_task ct ON c.id = ct.course_id
        LEFT JOIN course_notice cn ON c.id = cn.course_id
        WHERE c.id = :course_id 
    """;

    public final static String FIND_BY_ID = """
          SELECT 
            c.id,
            c.course_title,
            c.course_description
          FROM course c
          WHERE c.id = :course_id """;
  
    public final static String FIND_BY_KEYWORD = """
          SELECT 
            c.id,
            c.title,
            c.desc
          FROM course c
          WHERE c.title LIKE '%keyword%' OR c.desc LIKE '%keyword%'
      """;

   public final static String FIND_ALL = """
         SELECT 
            c.id, c.course_title, c.course_description, 
            ct.task_title, cn.notice_title
        FROM course c 
        LEFT JOIN course_task ct ON c.id = ct.course_id
        LEFT JOIN course_notice cn ON c.id = cn.course_id
      """;

    public final static String COUNT_ACTIVE_COURSE = """
        SELECT 
            c.id, c.course_title, c.course_description, 
            ct.task_title, cn.notice_title
        FROM course c 
        LEFT JOIN course_task ct ON c.id = ct.course_id
        LEFT JOIN course_notice cn ON c.id = cn.course_id
        WHERE c.active = true
      """;


    public static final String COUNT_ACTIVE_COURSES = """
        select
            count(c.id)
        from
            course c
        where c.active is true                                                                            
    """;
}
