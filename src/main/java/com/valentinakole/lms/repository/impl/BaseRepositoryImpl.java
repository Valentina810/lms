package com.valentinakole.lms.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.valentinakole.lms.model.QLesson;
import com.valentinakole.lms.model.QSubject;
import com.valentinakole.lms.model.QUser;
import com.valentinakole.lms.repository.BaseRepository;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

public abstract class BaseRepositoryImpl<T, ID>
        extends SimpleJpaRepository<T, ID>
        implements BaseRepository<T, ID> {

    EntityManager em;
    JPAQueryFactory jpaQueryFactory;

    protected final QUser user = QUser.user;
    protected final QLesson lesson = QLesson.lesson;
    protected final QSubject subject = QSubject.subject;

    public BaseRepositoryImpl(
            Class<T> domainClass,
            EntityManager em) {
        super(domainClass, em);
        this.em = em;
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }
}