package com.github.frankiie.springboot.domain.collection_note.repository.custom;

public  class NoteQueries {
 
    public final static String FIND_BY_ID = """
        SELECT 
            n.id, 
            n.note_title, 
            n.note_desc, 
            n.note_link
        FROM
            note n
        WHERE 
            n.id = :note_id AND 
            n.created_by = :owner_id

    """;

    public final static String FIND_BY_ID_AND_FETCH_IMAGES = """    
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
            n.id = :note_id AND 
            n.created_by = :owner_id
    """;

    public final static String FIND_IMAGES_BY_ID = """
        SELECT 
            i.id, 
            i.image_url
        FROM 
            image i
        WHERE 
            i.note_id = :note_id AND 
            i.created_by = :owner_id 
        ORDER BY 
            i.created_at ASC
    """;

    public final static String COUNT_IMAGES_BY_ID = """
        SELECT 
            count(i.id) 
        FROM 
            image i 
        WHERE 
            i.note_id = :note_id AND
            i.created_by = :owner_id 
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
          ) AND 
          n.created_by = :owner_id 
    """;

    public final static String COUNT_BY_COLLECTION_ID = """
      SELECT 
          count(n.id)
      FROM 
          note n 
      WHERE
          n.id IN (
              SELECT c_n.note_id
              FROM collection_note c_n 
              WHERE c_n.collection_id = :collection_id
          ) AND 
          n.created_by = :owner_id 
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
            n.created_by = :owner_id AND
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
            n.created_by = :owner_id AND
            LOWER(n.note_title) LIKE CONCAT('%', LOWER(:keyword), '%') OR 
            LOWER(n.note_desc) LIKE CONCAT('%', LOWER(:keyword), '%') OR 
            LOWER(n.note_link) LIKE CONCAT('%', LOWER(:keyword), '%') 
    """;

    public final static String FIND_MANY = """
        SELECT 
            n.id, 
            n.note_title, 
            n.note_desc, 
            n.created_by,
            n.note_link
        FROM 
            note n
        WHERE 
            n.created_by = :owner_id
        ORDER BY 
            n.created_at ASC
    """;

    public static final String COUNT_MANY = """
        SELECT
            COUNT(n.id)
        FROM
            note n
        WHERE 
            n.created_by = :owner_id AND
            n.active is true                                                                            
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
         WHERE 
            n.created_by = :owner_id 
        ORDER BY
            CASE WHEN :filter = 'title' THEN n.note_title END,
            CASE WHEN :filter = 'created_at' THEN n.created_at END,
            CASE WHEN :filter = 'used_at' THEN n.used_at END
    """;

    public final static String COUNT_MANY_WITH_FILTER = """
        WITH filtered_notes AS (
          SELECT 
            n.id, 
            n.note_title, 
            n.note_desc, 
            n.note_link
        FROM 
            note n
        WHERE 
            n.created_by = :owner_id 
        ORDER BY
            CASE WHEN :filter = 'title' THEN n.note_title END,
            CASE WHEN :filter = 'created_at' THEN n.created_at END,
            CASE WHEN :filter = 'used_at' THEN n.used_at END
        )
        SELECT COUNT(*) FROM filtered_notes;
      """;
        
    public static final String COUNT_ACTIVE_COLLECTION_COMMENTS = """
        SELECT
            count(c.id)
        FROM
            comment c
        WHERE 
            n.created_by = :owner_id AND
            c.active is true AND 
            c.collection_id = :collection_id                                                                           
    """;

    public static String UPDATE_BY_TITLE_OR_DESCRIPTION_AND_RETURN = """

        
    """;
}
