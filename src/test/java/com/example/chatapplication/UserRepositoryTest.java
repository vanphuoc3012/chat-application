package com.example.chatapplication;

import com.example.chatapplication.authentication.model.Role;
import com.example.chatapplication.authentication.repository.RoleRepository;
import com.example.chatapplication.authentication.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void getRoleTest() {
        int id = 3;
        Optional<Role> optionalRole = roleRepository.findById(id);

        Assertions.assertThat(optionalRole.isPresent()).isTrue();
    }

    @Test
    public void createRoleTest() {
        Role role = new Role();
        role.setName("USER");

        Role savedRole = roleRepository.save(role);
        Assertions.assertThat(savedRole.getId()).isNotNull();

        Optional<Role> optionalRole = roleRepository.findById(savedRole.getId());
        Assertions.assertThat(optionalRole.isPresent()).isTrue();
    }
}
