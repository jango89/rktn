package com.rakuten.test;

import com.rakuten.model.auth.Role;
import com.rakuten.model.auth.User;
import com.rakuten.repository.auth.RoleRepository;
import com.rakuten.repository.auth.UserRepository;
import com.rakuten.service.auth.UserServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UserServiceTests {

    private UserServiceImpl userService;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Before
    public void setUp() {
        userService = new UserServiceImpl(userRepository, roleRepository, bCryptPasswordEncoder);
        User user = new User();
        user.setEmail("rohit@test1.com");
        Mockito.when(userRepository.findByEmail("rohit@test1.com")).thenReturn(user);
    }

    @Test
    public void findByUserEmailTest() {
        String email = "rohit@test.com";
        final User userByEmail = userService.findUserByEmail(email);
        Assert.assertNull(userByEmail);
    }

    @Test
    public void findByUserEmailSuccessTest() {
        String email = "rohit@test1.com";
        final User userByEmail = userService.findUserByEmail(email);
        Assert.assertNotNull(userByEmail);
        Assert.assertEquals(userByEmail.getEmail(), email);
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveUserWithoutRole() {
        User user = new User();
        user.setEmail("rohit@test1.com");
        userService.saveUser(user);
    }

    @Test
    public void saveUserWithRole() {
        Role role = new Role();
        role.setRole("ADMIN");
        User user = new User();
        user.setEmail("rohit@test1.com");
        Mockito.when(roleRepository.findByRole("ADMIN")).thenReturn(role);
        userService.saveUser(user);
    }
}
