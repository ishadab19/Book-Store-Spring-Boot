package com.bridgelabz.bookstoreapp.model;

import com.bridgelabz.bookstoreapp.dto.UserDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDate;

@Component
@Entity
@Data
@NoArgsConstructor
@Table(name = "user_details")
public class UserData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Integer userId;
    private String firstName;
    private String lastName;
    private String address;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String email;
    private String password;

    private String jwtToken;
    private Boolean isVerified = false;

    public UserData(Integer userId, String firstName, String lastName, String address, LocalDate dateOfBirth,
                    String phoneNumber, String email, String password, Boolean isVerified) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
       this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.isVerified = isVerified;
    }

    public UserData(UserDTO userDTO) {
        this.updateUserData(userDTO);

    }

    public void updateUserData(UserDTO userDTO) {
        this.firstName = userDTO.firstName;
        this.lastName = userDTO.lastName;
        this.address = userDTO.address;
        this.dateOfBirth = userDTO.dateOfBirth;
        this.phoneNumber = userDTO.phoneNumber;
        this.email = userDTO.email;
        this.password = userDTO.password;
    }
}
