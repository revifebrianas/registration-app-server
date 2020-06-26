package com.rfs.app;

import com.rfs.app.exception.GlobalException;
import com.rfs.app.repository.UserRepository;
import com.rfs.app.request.RegistrationRequest;
import com.rfs.app.service.UserService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {UserProvider.class})
public class UserServiceImplBehaviourTest {

    private Validator validator;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @After
    public void tearDown() {
        Mockito.reset(userRepository);
    }

    @Test
    public void validate() {
        RegistrationRequest registrationRequest = RegistrationRequest.builder()
                .firstName(null)
                .lastName(null)
                .email(null)
                .gender(null)
                .mobileNumber(null)
                .dateOfBirth(0)
                .build();

        Set<ConstraintViolation<RegistrationRequest>> violationsNullRegistrationRequest = validator.validate(registrationRequest);
        Assert.assertEquals(4, violationsNullRegistrationRequest.size());

        RegistrationRequest registrationRequest2 = RegistrationRequest.builder()
                .firstName("")
                .lastName("")
                .email("")
                .gender("")
                .mobileNumber("")
                .dateOfBirth(0)
                .build();

        Set<ConstraintViolation<RegistrationRequest>> violationsEmptyRegistrationRequest = validator.validate(registrationRequest2);
        Assert.assertEquals(4, violationsEmptyRegistrationRequest.size());
    }

    @Test
    public void testRegisterUser_withValidData() {
        RegistrationRequest registrationRequest = RegistrationRequest.builder()
                .firstName("A")
                .lastName("A")
                .email("a@gmail.com")
                .gender("Female")
                .mobileNumber("+628666666666")
                .dateOfBirth(0)
                .build();

        userService.registerUser(registrationRequest);

        verify(userRepository, times(1)).existsByEmail(any());
        verify(userRepository, times(1)).existsByMobileNumber(any());
        verify(userRepository, times(1)).save(any());
    }

    @Test(expected = GlobalException.class)
    public void testRegisterUser_withDuplicateMobileNumber_thenThrowGlobalException() {
        RegistrationRequest registrationRequest = RegistrationRequest.builder()
                .firstName("B")
                .lastName("B")
                .email("b@gmail.com")
                .gender("Female")
                .mobileNumber("+628777777777")
                .dateOfBirth(0)
                .build();

        when(userRepository.existsByMobileNumber(registrationRequest.getMobileNumber())).thenReturn(true);

        userService.registerUser(registrationRequest);

        verify(userRepository, times(1)).existsByMobileNumber(any());
        verify(userRepository, never()).existsByEmail(any());
        verify(userRepository, never()).save(any());
    }

    @Test(expected = GlobalException.class)
    public void testRegisterUser_withDuplicateEmail_thenThrowGlobalException() {
        RegistrationRequest registrationRequest = RegistrationRequest.builder()
                .firstName("B")
                .lastName("B")
                .email("b@gmail.com")
                .gender("Female")
                .mobileNumber("+628777777777")
                .dateOfBirth(0)
                .build();

        when(userRepository.existsByEmail(registrationRequest.getEmail())).thenReturn(true);

        userService.registerUser(registrationRequest);

        verify(userRepository, times(1)).existsByEmail(any());
        verify(userRepository, never()).existsByMobileNumber(any());
        verify(userRepository, never()).save(any());
    }

    @Test(expected = GlobalException.class)
    public void testRegisterUser_withInvalidMobileNumber_thenThrowGlobalException() {
        RegistrationRequest registrationRequest = RegistrationRequest.builder()
                .firstName("B")
                .lastName("B")
                .email("b@gmail.com")
                .gender("Female")
                .mobileNumber("+698777777777")
                .dateOfBirth(0)
                .build();

        userService.registerUser(registrationRequest);

        verify(userRepository, times(1)).existsByMobileNumber(any());
        verify(userRepository, never()).existsByEmail(any());
        verify(userRepository, never()).save(any());
    }


}
