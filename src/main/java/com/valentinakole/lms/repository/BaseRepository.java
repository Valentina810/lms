package com.valentinakole.lms.repository;

import com.querydsl.core.types.Path;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepository<T, ID> {

    Long update(Long id, List<? extends Path<?>> key, List<?> value);
}