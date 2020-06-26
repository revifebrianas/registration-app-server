package com.rfs.app;

import com.rfs.app.entity.UserEntity;
import com.rfs.app.exception.GlobalException;
import com.rfs.app.repository.UserRepository;
import com.rfs.app.request.RegistrationRequest;
import com.rfs.app.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

@SpringBootTest(classes = RegistrationAppApplicationTest.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testRegisterUser_withValidData() {
        RegistrationRequest registrationRequest = RegistrationRequest.builder()
                .firstName("First B")
                .lastName("Last B")
                .email("email_b@gmail.com")
                .gender("Female")
                .mobileNumber("+6282222222222")
                .dateOfBirth(new Date().getTime())
                .build();

        userService.registerUser(registrationRequest);
        UserEntity userEntity = userRepository.findByEmail("email_b@gmail.com");

        Assert.assertEquals("+6282222222222", userEntity.getMobileNumber());

        userRepository.deleteById(userEntity.getId());
    }

    @Test(expected = GlobalException.class)
    public void testRegisterUser_withInvalidMobileNumber_thenThrowException() {
        RegistrationRequest registrationRequest = RegistrationRequest.builder()
                .firstName("First C")
                .lastName("Last C")
                .email("email_cc@gmail.com")
                .gender("Female")
                .mobileNumber("+6181111111111")
                .dateOfBirth(new Date().getTime())
                .build();

        userService.registerUser(registrationRequest);
    }

}
