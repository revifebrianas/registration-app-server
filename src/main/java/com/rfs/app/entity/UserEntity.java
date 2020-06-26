package com.rfs.app.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USER")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @Column(name = "MOBILE_NUMBER", nullable = false, unique = true)
    private String mobileNumber;

    @Column(name = "DATE_OF_BIRTH")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @Column(name = "GENDER")
    private String gender;

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    public UserEntity(String firstName, Date dateOfBirth) {
        this.firstName = firstName;
        this.dateOfBirth = dateOfBirth;
    }
}
