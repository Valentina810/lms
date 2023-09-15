package com.valentinakole.lms.repository;

import com.valentinakole.lms.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryIT {

    @Autowired
    private UserRepository userRepository;

    User userIvan;
    User userPetr;

    @BeforeEach
    public void setUp() {
        userIvan = new User(0, "Ivan", "Ivanov", "login", "password", "123456789",
                "ivan@mail.com", LocalDate.of(2000, 01, 01), LocalDate.now(), "url");
        userRepository.save(userIvan);
        userPetr = new User(0, "Petr", "Petrov", "loginOfPetr", "passwordOfPetr", "987654321",
                "petr@mail.com", LocalDate.of(2010, 10, 10), LocalDate.now(), "urlOfPetr");
    }

//    @Test
//    void updateWithoutPassword() {
//    }

    @Test
    void updateWithPassword() {
        System.out.println(userIvan);
        System.out.println(userPetr);
//        userRepository.updateWithPassword(userPetr.getName(), userPetr.getSurname(), userPetr.getLogin(), userPetr.getPassword(),
//                userPetr.getEmail(), userPetr.getDateBirth(), userPetr.getAvatarUrl(), userIvan.getId_user());

        userRepository.updateWithPassword("xsdgvsd", "dvbgsd", "dvbsd", "vsdvsd",
                "vsdv@mail.com", LocalDate.of(2000, 01, 01), "fdgnsfg", userIvan.getId_user());
//        userRepository.save(userPetr);
        System.out.println(userRepository.findById(userIvan.getId_user()));
//        System.out.println(userRepository.findById(userPetr.getId_user()));
//        Optional<User> optional = userRepository.findById(userIvan.getId_user());
//        Assertions.assertTrue(optional.isPresent());
//        System.out.println(optional.get());
//        Assertions.assertEquals(optional.get(), userPetr);
    }

    @Test
    void findByEmail() {
        Optional<User> optional = userRepository.findByEmail("ivan@mail.com");
        Assertions.assertTrue(optional.isPresent());
        Assertions.assertEquals(optional.get(), userIvan);
    }

    @AfterEach
    private void deleteAll() {
        userRepository.deleteAll();
    }
}