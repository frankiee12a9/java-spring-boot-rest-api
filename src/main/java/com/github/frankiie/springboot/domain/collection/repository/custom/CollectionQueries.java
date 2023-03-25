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
            c.id = :collection_id
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
            c.id = :collection_id
    """;

    public static String UPDATE_BY_ID = """
        UPDATE 
            collection c 
        SET 
            c.collection_title = :title, 
            c.description = :description 
        WHERE 
            c.id = :collection_id
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
            image i ON c.image_id = i.id
        WHERE 
            LOWER(c.collection_title) LIKE CONCAT('%', LOWER(:keyword), '%') OR 
            LOWER(c.collection_desc) LIKE CONCAT('%', LOWER(:keyword), '%')
    """;

    public final static String FIND_MANY = """
        SELECT 
            c.id, c.collection_title, c.collection_desc, 
            i.id AS i_id, i.image_url
        FROM 
            collection c 
        LEFT JOIN 
            image i ON c.id = i.collection_id
    """;

    public static final String COUNT_ACTIVE_COLLECTIONS = """
        SELECT
            count(c.id)
        FROM
            collection c
        WHERE
            c.active is true                                                                           
    """;

    public static final String COUNT_ACTIVE_COLLECTION_COMMENTS = """
        SELECT
            count(c.id)
        FROM
            comment c
        WHERE 
          c.active is true AND c.collection_id = :collection_id                                                                           
    """;

    public static String UPDATE_BY_TITLE_OR_DESCRIPTION_AND_RETURN = """

        
    """;
}
