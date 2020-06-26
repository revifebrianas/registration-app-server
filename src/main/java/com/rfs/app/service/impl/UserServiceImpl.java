package com.rfs.app.service.impl;

import com.rfs.app.entity.UserEntity;
import com.rfs.app.exception.GlobalException;
import com.rfs.app.repository.UserRepository;
import com.rfs.app.request.RegistrationRequest;
import com.rfs.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String INDONESIAN_CODE_MOBILE_NUMBER = "+62";
    private final UserRepository userRepository;

    @Override
    public void registerUser(RegistrationRequest registrationRequest) {
        validateMobileNumber(registrationRequest.getMobileNumber());
        validateEmail(registrationRequest.getEmail());

        UserEntity userEntity = UserEntity.builder()
                .firstName(registrationRequest.getFirstName())
                .lastName(registrationRequest.getLastName())
                .email(registrationRequest.getEmail())
                .dateOfBirth(new Date(registrationRequest.getDateOfBirth()))
                .gender(registrationRequest.getGender())
                .mobileNumber(registrationRequest.getMobileNumber())
                .build();

        userRepository.save(userEntity);
    }

    private void validateMobileNumber(String mobileNumber) {
        if (!userRepository.existsByMobileNumber(mobileNumber)) {
            if (!mobileNumber.substring(0, 3).equals(INDONESIAN_CODE_MOBILE_NUMBER)) {
                throw new GlobalException("Must be Indonesian phone number");
            }
        } else {
            throw new GlobalException("Duplicate Mobile Number");
        }
    }

    private void validateEmail(String email) {
        if (userRepository.existsByEmail(email))
            throw new GlobalException("Must be Indonesian phone number");
    }

}
