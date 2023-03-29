package com.github.frankiie.springboot.domains.user.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.github.frankiie.springboot.domains.pagination.model.Page;
import com.github.frankiie.springboot.domains.user.entity.User;
import com.github.frankiie.springboot.domains.user.repository.custom.NativeQueryUserRepository;
import com.github.frankiie.springboot.domains.user.repository.springdata.SpringDataUserRepository;

import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepository {

    @Autowired private final SpringDataUserRepository springData;
    @Autowired private final NativeQueryUserRepository nativeQuery;

    @Override
    public Page<User> findAll(Pageable pageable) {
        return nativeQuery.findAll(pageable);
    }

    @Override
    public Optional<User> findById(Long id) {
        return nativeQuery.findById(id);
    }

   @Override
    public Optional<User> findByUsername(String username) {
      return nativeQuery.findByUsername(username);
    }

    @Override
    public Optional<User> findByIdFetchRoles(Long id) {
        return springData.findByIdFetchRoles(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return nativeQuery.findByEmail(email);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return springData.existsByEmail(email);
    }

    @Override
    public void deleteById(Long id) {
        springData.deleteById(id);
    }

    @Override
    public void delete(User user) {
        springData.delete(user);
    }

    @Override
    public User save(User user) {
        return springData.save(user);
    }

    @Override
    public Collection<User> saveAll(Collection<User> users) {
        return springData.saveAll(users);
    }

    @Override
    public void deleteAll(Iterable<? extends User> users) {
        springData.deleteAll(users);
    }

}
