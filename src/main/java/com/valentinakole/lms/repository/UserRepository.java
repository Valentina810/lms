package com.valentinakole.lms.repository;

import com.valentinakole.lms.model.User;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User, Long>, QuerydslPredicateExecutor<User> {

    Optional<User> findByEmail(String email);
}
