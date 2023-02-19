package com.bridgelabz.bookstoreapp.controller;

import com.bridgelabz.bookstoreapp.dto.ResponseDTO;
import com.bridgelabz.bookstoreapp.dto.UserDTO;
import com.bridgelabz.bookstoreapp.dto.UserLoginDTO;
import com.bridgelabz.bookstoreapp.model.UserData;
import com.bridgelabz.bookstoreapp.service.EmailSenderService;
import com.bridgelabz.bookstoreapp.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@CrossOrigin( allowedHeaders = "*", origins = "*")
@RequestMapping("/bookstore")
@RestController
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private EmailSenderService senderService;

    /**
     * @return welcome message
     * @Purpose : To check simple api call
     */
    @RequestMapping(value = {"/welcome", "/", ""})
    public String welcomeMessage() {
        return "Welcome to Book Store Application !!!";
    }

    /**
     * @return user data and httpStatus
     * @Purpose : To add / register user in book store application
     * @Param : UserDTO
     */
    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> userRegistration(@Valid @RequestBody UserDTO userDTO) {
        UserData userData = userService.registerUser(userDTO);
        ResponseDTO respDTO = new ResponseDTO("User created successfully ,check mail & verify",userData);
        return new ResponseEntity(respDTO, HttpStatus.OK);
    }

    /**
     * @return user data and httpStatus
     * @Purpose : To verify otp at the time of registration
     * @Param : otp
     */
    @GetMapping("/verify/email/{otp}")
    public ResponseEntity<ResponseDTO> otpVerification(@PathVariable Long otp) {
        ResponseDTO respDTO = userService.verifyOtp(otp);
        return new ResponseEntity<>(respDTO, HttpStatus.OK);
    }

    /**
     * @return user data and httpStatus
     * @Purpose : To login user
     * @Param : UserLoginDTO
     */
    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> userLogin(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        ResponseDTO respDTO = userService.loginUser(userLoginDTO);
        //String token = (String) respDTO.getData();
        //httpServletResponse.setHeader("Authorization", token);
        return new ResponseEntity(respDTO, HttpStatus.OK);
    }

    /**
     * @return response and status
     * @Purpose : To sent forgot password request
     * @Param : email
     */
    @PostMapping("/forgotpassword")
    public ResponseEntity<ResponseDTO> forgotPasswordRequest(@RequestParam String email) {
        String otp = userService.forgotPasswordRequest(email);
        ResponseDTO respDTO = new ResponseDTO("Otp sent Successfully", otp);
        return new ResponseEntity(respDTO, HttpStatus.OK);
    }

    /**
     * @return response and status
     * @Purpose : To reset password
     * @Param : password, otp
     */
    @PostMapping("/resetpassword/{otp}")
    public ResponseEntity<ResponseDTO> resetPassword(@RequestParam String password, @PathVariable Long otp) {
        String newPassword = userService.resetPassword(password, otp);
        ResponseDTO respDTO = new ResponseDTO("Reset Password", newPassword);
        return new ResponseEntity<>(respDTO, HttpStatus.OK);

    }

    /**
     * @return user data list and httpStatus
     * @Purpose : To get list of all users in book store application
     */
    @RequestMapping(value = {"", "/", "/getall"})
    public ResponseEntity<ResponseDTO> getEmployeePayrollData() {
        List<UserData> userDataList = null;
        userDataList = userService.getUserData();
        ResponseDTO respDTO = new ResponseDTO("Get Call Successful", userDataList);
        return new ResponseEntity<ResponseDTO>(respDTO, HttpStatus.OK);
    }

    /**
     * @return updated user data 
     * @Purpose : To update user data in book store application
     * @Param : id, UserDTO
     */
    @PutMapping("/update/{userId}")
    public ResponseEntity<ResponseDTO> updateUserData(@PathVariable("userId") Integer userId, @Valid @RequestBody UserDTO userDTO) {
        UserData userData = userService.updateUserData(userId, userDTO);
        ResponseDTO respDTO = new ResponseDTO("User Updated Successfully", userData);
        return new ResponseEntity(respDTO, HttpStatus.OK);
    }

    /**
     * @return response and status
     * @Purpose : To delete user data by in book store application
     * @Param : id
     */
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<ResponseDTO> deleteUser(@PathVariable("userId") Integer userId) {
        userService.deleteUserData(userId);
        ResponseDTO respDTO = new ResponseDTO("Deleted successfully", "Deleted id: " + userId);
        return new ResponseEntity<>(respDTO, HttpStatus.OK);
    }
    @GetMapping("/getAllUserNumber")
    public ResponseEntity<ResponseDTO> getAllUsers(){
        int count = userService.getAllUserNumber();
        ResponseDTO responseDTO =new ResponseDTO("Total Number Of User: ",count);
        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }

}