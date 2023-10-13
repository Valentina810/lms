package com.valentinakole.lms.repository.impl;

import com.querydsl.core.types.Path;
import com.valentinakole.lms.model.User;
import jakarta.persistence.EntityManager;

import java.util.List;

public class UserRepositoryImpl extends BaseRepositoryImpl<User, Long> {
    public UserRepositoryImpl(EntityManager em) {
        super(User.class, em);
    }

    @Override
    public Long update(Long id, List<? extends Path<?>> keys, List<?> values) {
        return jpaQueryFactory
                .update(user)
                .set(keys, values)
                .where(user.userId.eq(id))
                .execute();
    }
}
