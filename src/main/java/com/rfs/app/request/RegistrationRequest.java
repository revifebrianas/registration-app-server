package com.rfs.app.request;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrationRequest {

    @NotEmpty
    private String mobileNumber;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    private long dateOfBirth;

    private String gender;

    @NotEmpty
    private String email;

}
