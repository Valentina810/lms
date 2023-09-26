package com.valentinakole.lms.repository;

import com.valentinakole.lms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update User u set u.name = ?1, u.surname = ?2 ,u.login=?3,u.email=?4,u.dateBirth=?5,u.avatarUrl=?6 where u.userId = ?7")
    void updateWithoutPassword(String name, String surname, String login, String email, LocalDate birthday, String url, long id);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update User u set u.name = ?1, u.surname = ?2 ,u.login=?3, u.password = ?4 ,u.email=?5,u.dateBirth=?6,u.avatarUrl=?7 where u.userId = ?8")
    void updateWithPassword(String name, String surname, String login, String password, String email, LocalDate birthday, String url, long id);

    Optional<User> findByEmail(String email);

    Optional<User> findByLogin(String login);
}
