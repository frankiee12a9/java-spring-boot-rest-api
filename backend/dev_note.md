## Development Note

### @Modifying annotation.

The error message

> _“Not supported for DML operations”_

indicates that you are trying to execute a DML (Data Manipulation Language) operation such as an INSERT, UPDATE, or DELETE statement using a query method that does not support DML operations.

In Spring Data JPA, you need to annotate query methods that perform DML operations with the @Modifying annotation. This annotation tells Spring Data JPA that the query method performs a DML operation and should be executed as such.

### @Transactional annotation

The error message

> _“javax.persistence.TransactionRequiredException: Executing an update/delete query”_

indicates that you are trying to execute an update or delete query outside of a transaction.

In JPA, all DML operations must be executed within a transaction. This means that you need to start a transaction before executing the update or delete query and commit or rollback the transaction after the query has been executed.

In Spring Data JPA, you can use the @Transactional annotation to declaratively manage transactions. This annotation can be added to a service method or a repository method to indicate that the method should be executed within a transaction.

### Transaction that needs _@Transactional_ and _@Modifying_

In Spring Data JPA, the @Query annotation is used to specify a custom query that should be executed by a repository method. This annotation can be used to define both read and write operations.

However, when defining a write operation such as an INSERT, UPDATE, or DELETE statement, you also need to use the @Modifying and @Transactional annotations.

The @Modifying annotation tells Spring Data JPA that the query method performs a write operation and should be executed as such. Without this annotation, Spring Data JPA would treat the query method as a read operation and would not execute the write operation.

The @Transactional annotation tells Spring Data JPA that the query method should be executed within a transaction. In JPA, all write operations must be executed within a transaction. Without this annotation, Spring Data JPA would not start a transaction before executing the write operation and an exception would be thrown at runtime.

In summary, when defining a write operation using the @Query annotation in Spring Data JPA, you also need to use the @Modifying and @Transactional annotations to indicate that the query method performs a write operation and should be executed within a transaction.

### Implicit `#{#entityName}` and explicit `EntityName`

If you want to perform the query on a specific entity, such as Comment, you can either use the #{#entityName} expression or specify the name of the entity directly in the query.

If you use the #{#entityName} expression, the name of the entity managed by the repository will be dynamically resolved at runtime. This allows you to write repository interfaces that can be reused for different entity types.

If you specify the name of the entity directly in the query, such as Comment, the query will always be executed on that specific entity type. This approach is more straightforward but less flexible than using the #{#entityName} expression.

In summary, you can either use the #{#entityName} expression to dynamically resolve the name of the entity being managed by the repository or specify the name of the entity directly in the query.
