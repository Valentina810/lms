package com.valentinakole.lms.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "subjects", schema = "public")
@Getter
@Setter
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_subject", nullable = false, unique = true)
    private Long idSubject;
    @Column(name = "name", nullable = false, unique = true)
    private String name;
}