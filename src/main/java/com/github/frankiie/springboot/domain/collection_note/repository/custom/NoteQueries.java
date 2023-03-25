package com.github.frankiie.springboot.domain.collection_note.repository.custom;

public  class NoteQueries {
 
    public final static String FIND_BY_ID = """
        SELECT 
            n.id, 
            n.note_title, 
            n.note_desc, 
            n.note_link,
            n.created_at,
            n.updated_at,
            n.used_at
        FROM
            note n
        WHERE 
            n.id = :note_id
    """;

    public final static String FIND_BY_ID_AND_FETCH_IMAGES = """
        SELECT 
            n.id, 
            n.note_title, 
            n.note_desc,
            i.id AS i_id, 
            id.image_url
        FROM 
            note n
        JOIN 
            image i ON c.id = i.note_id
        WHERE 
            n.id = :note_id
    """;

    public final static String FIND_IMAGES_BY_ID = """
        SELECT 
            i.id, 
            i.image_url
        FROM 
            image i
        WHERE 
            i.note_id = :note_id
        ORDER BY 
            i.created_at ASC
    """;

    public final static String COUNT_IMAGES_BY_ID = """
      SELECT 
          count(i.id) 
      FROM 
          image i
      WHERE 
          i.note_id = :note_id
    """;

    public final static String FIND_BY_COLLECTION_ID = """
      select 
          n.id, 
          n.note_title, 
          n.note_desc, 
          n.note_link
      from 
          note n 
      where
           n.id IN (
          SELECT 
              c_n.note_id
          FROM 
              collection_note c_n 
          WHERE 
              c_n.collection_id = :collection_id
          )
    """;

    public final static String COUNT_BY_COLLECTION_ID = """
      select 
          count(n.id)
      from 
          note n 
      where
          n.id IN (
          select c_n.note_id
          from collection_note c_n 
          where c_n.collection_id = :collection_id
      )
    """;

    public static String UPDATE_BY_ID = """
        UPDATE 
            collection c 
        SET 
            c.collection_title = :title, c.description = :description 
        WHERE 
            c.id = :collection_id
    """;

    /**
     * find records containing given keyword related to `title` or `description` (can handling all case-insensitive)
     */
    public final static String FIND_BY_KEYWORD = """
        SELECT 
            n.id,
            n.note_title,
            n.note_desc,
            n.note_link
        FROM 
            note n
        WHERE 
              LOWER(n.note_title) LIKE CONCAT('%', LOWER(:keyword), '%') OR 
              LOWER(n.note_desc) LIKE CONCAT('%', LOWER(:keyword), '%') OR 
              LOWER(n.note_link) LIKE CONCAT('%', LOWER(:keyword), '%') 
    """;

    public final static String COUNT_BY_KEYWORD = """
        SELECT 
            count(n.id)
        FROM 
            note n
        WHERE 
              LOWER(n.note_title) LIKE CONCAT('%', LOWER(:keyword), '%') OR 
              LOWER(n.note_desc) LIKE CONCAT('%', LOWER(:keyword), '%') OR 
              LOWER(n.note_link) LIKE CONCAT('%', LOWER(:keyword), '%') 
    """;

    public final static String FIND_MANY = """
        SELECT 
            n.id, 
            n.note_title, 
            n.note_desc, 
            n.note_link
        FROM 
            note n
        ORDER BY 
            n.created_at ASC
    """;

    public static final String COUNT_MANY = """
        select
            count(n.id)
        from
            note n
        where n.active is true                                                                           
    """;
    
    /**
     * available filters (title, created_at, used_at)
     */
    public final static String FIND_MANY_WITH_FILTER = """
        SELECT 
            n.id, 
            n.note_title, 
            n.note_desc, 
            n.note_link
        FROM 
            note n
        order by
            case when :filter = 'title' then n.note_title end,
            case when :filter = 'created_at' then n.created_at end,
            case when :filter = 'used_at' then n.used_at end
    """;

    public final static String COUNT_MANY_WITH_FILTER = """
        SELECT 
            count(n.id)
        FROM 
            note n
        order by
            CASE WHEN :filter = 'title' THEN n.note_title end,
            CASE WHEN :filter = 'created_at' THEN n.created_at end,
            CASE WHEN :filter = 'used_at' THEN n.used_at end
    """;

    public static final String COUNT_ACTIVE_COLLECTION_COMMENTS = """
        select
            count(c.id)
        from
            comment c
        where 
            c.active is true AND c.collection_id = :collection_id                                                                           
    """;

    public static String UPDATE_BY_TITLE_OR_DESCRIPTION_AND_RETURN = """

        
    """;
}
