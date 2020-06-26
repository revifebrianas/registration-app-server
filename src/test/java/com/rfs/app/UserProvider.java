package com.rfs.app;

import com.rfs.app.repository.UserRepository;
import com.rfs.app.service.UserService;
import com.rfs.app.service.impl.UserServiceImpl;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserProvider {

    @Bean
    public UserRepository userRepository() {
        return Mockito.mock(UserRepository.class);
    }

    @Bean
    public UserService userService() {
        return new UserServiceImpl(userRepository());
    }

}
