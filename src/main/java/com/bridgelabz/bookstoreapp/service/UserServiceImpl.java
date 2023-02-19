package com.bridgelabz.bookstoreapp.service;

import com.bridgelabz.bookstoreapp.dto.ResponseDTO;
import com.bridgelabz.bookstoreapp.dto.UserDTO;
import com.bridgelabz.bookstoreapp.dto.UserLoginDTO;
import com.bridgelabz.bookstoreapp.model.UserData;

import java.util.List;

public interface UserServiceImpl {

    UserData updateUserData(Integer id, UserDTO userDTO);

    UserData getUserDataById(Integer id);

    void deleteUserData(Integer userId);

    List<UserData> getUserData();

    ResponseDTO loginUser(UserLoginDTO userLoginDTO);

    ResponseDTO verifyOtp(Long otp);

    UserData  registerUser(UserDTO userDTO);

    String forgotPasswordRequest(String email);

    String resetPassword(String password, Long otp);

    int getAllUserNumber();
}
