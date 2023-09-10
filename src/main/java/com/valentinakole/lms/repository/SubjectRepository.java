package com.valentinakole.lms.repository;

import com.valentinakole.lms.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}