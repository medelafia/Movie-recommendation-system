package com.backend.repository;


import com.backend.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    private User user1 ;
    private User user2 ;

    @BeforeEach
    void setUp() {
        user1 = User.builder()
                .username("user1")
                .build();
        user2 = User.builder()
                .username("user2")
                .build();
    }
    @Test
    void findByUsernameTest() {
        User savedUser1 = userRepository.save(user1);
        User savedUser2 = userRepository.save(user2);

        userRepository.save(savedUser1);
        userRepository.save(savedUser2);

        Optional<User> retrievedUser = userRepository.findByUsername(user1.getUsername());
        assertThat(retrievedUser).isPresent();
        assertThat(retrievedUser.get().getUsername()).isEqualTo(user1.getUsername());
    }
}