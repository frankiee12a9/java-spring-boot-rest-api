package com.github.frankiie.springboot.domain.collection.repository.custom;

public  class CollectionQueries {
 
    public final static String FIND_BY_ID_DETAILS = """
        SELECT 
            c.id, c.collection_title, c.collection_desc, 
            i.id AS i_id, i.image_url
        FROM 
            collection c 
        LEFT JOIN 
            image i ON c.id = i.collection_id
        WHERE 
            c.id = :collection_id AND 
            c.created_by = :owner_id
    """;

    public final static String FIND_BY_ID_AND_FETCH_COMMENTS = """
        SELECT 
            c.id, c.collection_title, c.collection_desc,
            cm.id AS i_id, cm.message
        FROM 
            collection c
        LEFT JOIN 
            comment cm ON c.id = cm.collection_id
        WHERE 
            c.id = :collection_id AND 
            c.created_by = :owner_id
    """;

    public static String UPDATE_BY_ID = """
        UPDATE 
            collection c 
        SET 
            c.collection_title = :title, 
            c.description = :description 
        WHERE 
            c.id = :collection_id AND 
            c.created_by = :owner_id
    """;

    /**
     * find records containing given keyword related to `title` or `description` (can handling all case-insensitive)
     */
    public final static String FIND_BY_KEYWORD = """
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
            c.created_by = :owner_id AND 
            LOWER(c.collection_title) LIKE CONCAT('%', LOWER(:keyword), '%') OR 
            LOWER(c.collection_desc) LIKE CONCAT('%', LOWER(:keyword), '%')
    """;

    public final static String COUNT_BY_KEYWORD = """
        SELECT 
            count(c.id)
        FROM 
            collection c
        LEFT JOIN 
            image i ON c.id = i.collection_id
        WHERE 
            c.created_by = :owner_id AND 
            LOWER(c.collection_title) LIKE CONCAT('%', LOWER(:keyword), '%') OR 
            LOWER(c.collection_desc) LIKE CONCAT('%', LOWER(:keyword), '%')
    """;

    /**
     * find records containing given keyword related to `title` or `description` (can handling all case-insensitive).
     * also, this query will return only the associated entity with latest-created_at-record from the associated entity (Image)
     */
    public final static String FIND_BY_KEYWORD_AND_GET_A_ASSOCIATION = """
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
              ORDER BY i.created_at DESC LIMIT 1) OR 
                  i.id IS NULL) AND 
              ( LOWER(c.collection_title) LIKE CONCAT('%', LOWER(:keyword), '%') OR 
              LOWER(c.collection_desc) LIKE CONCAT('%', LOWER(:keyword), '%' )
            ) AND 
            c.created_by = :owner_id
    """;

    public final static String COUNT_BY_KEYWORD_AND_GET_A_ASSOCIATION = """
        SELECT 
            count(c.id)
        FROM 
            collection c
        LEFT JOIN 
            image i ON c.id = i.collection_id
        WHERE 
            (i.id = (SELECT i.id FROM image i WHERE i.collection_id = c.id 
              ORDER BY i.created_at DESC LIMIT 1) OR 
                  i.id IS NULL) AND 
              ( LOWER(c.collection_title) LIKE CONCAT('%', LOWER(:keyword), '%') OR 
              LOWER(c.collection_desc) LIKE CONCAT('%', LOWER(:keyword), '%' )
            ) AND 
            c.created_by = :owner_id
    """;

    public final static String FIND_MANY = """
        SELECT 
            c.id, c.collection_title, c.collection_desc, 
            i.id AS i_id, i.image_url
        FROM 
            collection c  
        LEFT JOIN 
            image i ON c.id = i.collection_id
        WHERE
            c.created_by = :owner_id
    """;

    public static final String COUNT_ACTIVE_COLLECTIONS = """
        SELECT
            count(c.id)
        FROM
            collection c
        WHERE
            c.active is true AND
            c.created_by = :owner_id                                                                           
    """;

    public static final String FIND_COMMENTS_BY_ID = """
        SELECT
            c.id, c.message
        FROM
            comment c
        WHERE 
            c.created_by = :owner_id AND
            c.active is true AND 
            c.collection_id = :collection_id                                                                            
    """;

    public static final String COUNT_ACTIVE_COLLECTION_COMMENTS = """
        SELECT
            count(c.id)
        FROM
            comment c
        WHERE 
            c.created_by = :owner_id AND
            c.active is true AND 
            c.collection_id = :collection_id                                                                            
    """;

    public static String DELETE_A_COMMENT = """
        delete from 
            comment c 
        where 
            c.id = :comment_id
    """;

    public static String DELETE_ALL_COMMENTS_BY_ID = """
       delete from 
            comment c 
        where 
            c.collection_id = :collection_id
    """;
}
