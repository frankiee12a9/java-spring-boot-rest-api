package com.github.frankiie.springboot.domain.collection_note.repository.custom;

public  class NoteQueries {
 
    public final static String FIND_BY_ID = """
        SELECT 
            n.id, n.title, n.desc, n.link
        FROM note n
        WHERE n.id = :note_id
    """;

    public final static String FIND_BY_ID_AND_FETCH_IMAGES = """
        SELECT 
            n.id, n.note_title, n.note_desc,
            i.id AS i_id, id.image_url
        FROM note n
        LEFT JOIN image i ON c.id = i.note_id
        where n.id = :note_id
    """;

    public final static String FIND_BY_COLLECTION_ID = """
        SELECT 
            n.id, n.title, n.desc, n.link
        FROM 
            note n
        LEFT JOIN 
            collection c ON n.collection_id = :collection_id
    """;

    public static String UPDATE_BY_ID = """
        UPDATE collection c 
        SET 
            c.collection_title = :title, c.description = :description 
        WHERE c.id = :collection_id
    """;

    /**
     * find records containing given keyword related to `title` or `description` (can handling all case-insensitive)
     */
    public final static String FIND_BY_KEYWORD = """
        SELECT 
            n.id,
            n.note_title,
            n.note_desc
        FROM note n
        WHERE LOWER(n.note_title) LIKE CONCAT('%', LOWER(:keyword), '%') OR 
              LOWER(n.note_desc) LIKE CONCAT('%', LOWER(:keyword), '%')
    """;

    public final static String FIND_MANY = """
        SELECT 
            n.id, n.note_title, n.note_desc
        FROM note n
    """;

    public static final String COUNT_ACTIVE_NOTES = """
        select
            count(n.id)
        from
            note n
        where n.active is true                                                                           
    """;

    public static final String COUNT_ACTIVE_COLLECTION_COMMENTS = """
        select
            count(c.id)
        from
            comment c
        where c.active is true AND c.collection_id = :collection_id                                                                           
    """;

    public static String UPDATE_BY_TITLE_OR_DESCRIPTION_AND_RETURN = """

        
    """;
}
