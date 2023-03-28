package com.github.frankiie.springboot.domain.user.repository.custom;

public class UserQueries {
  
    public static final String FIND_BY_ID = """
        with user_roles AS (
          SELECT
              ur.user_id, string_agg(r.initials, ',') roles
          FROM 
              "role" r
          LEFT JOIN
               user_role ur on r.id = ur.role_id
          GROUP BY 
              ur.user_id
        )

        SELECT
            u.id,
            u."name",
            u.email,
            u.password,
            urs.roles
        FROM
            "user" u
        LEFT JOIN 
            user_roles AS urs on urs.user_id = u.id
        WHERE 
            u.deleted_at is null and %s
        ORDER BY
             u.created_at DESC
    """;

    public static final String FIND_BY_FIELD_AND_FETCH_ROLES = """
        with user_roles AS (
            SELECT
                    ur.user_id, string_agg(r.initials, ',') roles
            FROM "role" r
                    LEFT JOIN user_role ur on r.id = ur.role_id
            GROUP BY ur.user_id
        )

        SELECT
            u.id,
            u."name",
            u.email,
            u.password,
            urs.roles
        FROM
            "user" u
        LEFT JOIN 
            user_roles AS urs on urs.user_id = u.id
        WHERE 
            u.deleted_at is null and %s
        ORDER BY 
            u.created_at DESC
    """;

    public static final String FIND_BY_USERNAME_AND_FETCH_ROLES = """
        with user_roles AS (
          SELECT
              ur.user_id, string_agg(r.initials, ',') roles
          FROM "role" r
              LEFT JOIN user_role ur on r.id = ur.role_id
          GROUP BY ur.user_id
        )

        SELECT
            u.id,
            u."name",
            u.email,
            u.password,
            urs.roles
        FROM
            "user" u
        LEFT JOIN 
            user_roles AS urs on urs.username = u.username
        WHERE 
            u.deleted_at is null and %s
        ORDER BY 
            u.created_at DESC
    """;

    public static final String FIND_BY_EMAIL_AND_FETCH_ROLES = """
        with user_roles AS (
          SELECT
              ur.user_id, string_agg(r.initials, ',') roles
          FROM "role" r
              LEFT JOIN user_role ur on r.id = ur.role_id
          GROUP BY ur.user_id
        )

        SELECT
          u.id,
          u."name",
          u.email,
          u.password,
          urs.roles
        FROM
            "user" u
        LEFT JOIN
            user_roles AS urs on urs.email = u.email
        WHERE 
            u.deleted_at is null and %s
        ORDER BY 
            u.created_at DESC
    """;

    public static final String DELETE_BY_ID = """
        UPDATE
            #{#entityName}
        SET
            deleted_email = (
                SELECT
                    email
                FROM
                    #{#entityName}
                WHERE id = ?1),
            email = NULL,
            deleted_at = CURRENT_TIMESTAMP,
            active = false,
            deleted_by = ?#{principal?.id}
        WHERE 
            id = ?1
    """;

    public static final String COUNT_ENABLED_USERS = """
        SELECT
            count(id)
        FROM
            "user"
        WHERE 
            deleted_at is null                                                                               
    """;
    
    public static final String FIND_ALL_AND_FETCH_ROLES = """
        with user_roles AS (
            SELECT
                ur.user_id, string_agg(r.initials, ',') roles
            FROM 
                "role" r
            LEFT JOIN 
                user_role ur on r.id = ur.role_id
            GROUP BY 
                ur.user_id 
        )

        SELECT
            u.id,
            u."name",
            u.email,
            u.password,
            urs.roles
        FROM 
            "user" u
        LEFT JOIN 
            user_roles AS urs on urs.user_id = u.id
        WHERE 
            u.deleted_at is null
        ORDER BY
            u.created_at DESC
    """;

}
