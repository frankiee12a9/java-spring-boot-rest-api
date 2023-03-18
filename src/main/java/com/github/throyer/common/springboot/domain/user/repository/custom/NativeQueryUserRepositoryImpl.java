package com.github.throyer.common.springboot.domain.user.repository.custom;

import static com.github.throyer.common.springboot.domain.user.repository.Queries.COUNT_ENABLED_USERS;
import static com.github.throyer.common.springboot.domain.user.repository.Queries.FIND_ALL_USER_FETCH_ROLES;
import static com.github.throyer.common.springboot.domain.user.repository.Queries.FIND_BY_FIELD_FETCH_ROLES;
import static java.lang.String.format;
import static java.util.Optional.empty;
import static java.util.Optional.of;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Tuple;

import com.github.throyer.common.springboot.domain.pagination.model.Page;
import com.github.throyer.common.springboot.domain.user.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class NativeQueryUserRepositoryImpl implements NativeQueryUserRepository {

    @Autowired
    EntityManager manager;

    @Override
    public Optional<User> findById(Long id) {
        var query = manager
                .createNativeQuery(format(FIND_BY_FIELD_FETCH_ROLES, "u.id = :user_id"), Tuple.class)
                    .setParameter("user_id", id);
        try {
            var tuple = (Tuple) query.getSingleResult();
            return of(User.from(tuple));
        } catch (NoResultException exception) {
            return empty();
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        var query = manager
                .createNativeQuery(format(FIND_BY_FIELD_FETCH_ROLES, "u.email = :user_email"), Tuple.class)
                    .setParameter("user_email", email);
        try {
            var tuple = (Tuple) query.getSingleResult();
            return of(User.from(tuple));
        } catch (NoResultException exception) {
            return empty();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Page<User> findAll(Pageable pageable) {
        var query = manager
                .createNativeQuery(FIND_ALL_USER_FETCH_ROLES, Tuple.class);

        var count = ((BigInteger) manager
                .createNativeQuery(COUNT_ENABLED_USERS)
                .getSingleResult())
                    .longValue();

        var pageNumber = pageable.getPageNumber();
        var pageSize = pageable.getPageSize();

        query.setFirstResult(pageNumber * pageSize);
        query.setMaxResults(pageSize);

        List<Tuple> content = query.getResultList();

        var users = content.stream().map(User::from).toList();

        return Page.of(users, pageNumber, pageSize, count);
    }
}
