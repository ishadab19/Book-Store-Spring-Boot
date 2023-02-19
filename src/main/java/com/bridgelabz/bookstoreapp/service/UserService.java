package com.bridgelabz.bookstoreapp.service;

import com.bridgelabz.bookstoreapp.dto.ResponseDTO;
import com.bridgelabz.bookstoreapp.dto.UserDTO;
import com.bridgelabz.bookstoreapp.dto.UserLoginDTO;
import com.bridgelabz.bookstoreapp.exception.BookStoreException;
import com.bridgelabz.bookstoreapp.model.UserData;
import com.bridgelabz.bookstoreapp.repository.UserRegistrationRepository;
import com.bridgelabz.bookstoreapp.util.OtpGenerator;
import com.bridgelabz.bookstoreapp.util.TokenGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;


@Service
@Slf4j
public class UserService implements UserServiceImpl {

    @Autowired
    private OtpGenerator otpGenerator;

    @Autowired
    UserData userData;

    @Autowired
    private UserRegistrationRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private TokenGenerator tokenGenerator;

    Long otp;

    /**
     * @Purpose This method is used to check user is present or not by Id
     */
    @Override
    public UserData getUserDataById(Integer id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new BookStoreException(("User with Id " + id + " does not exists...")));
    }

    /**
     * @Purpose This method is used to register user
     */
    @Override
    public UserData  registerUser(UserDTO userDTO) {
        UserData user = userRepository.findUserDataByEmail(userDTO.getEmail());
        if (user == null) {
            userData = new UserData(userDTO);
            String epassword = bCryptPasswordEncoder.encode(userDTO.getPassword());
            userData.setPassword(epassword);
            System.out.println("password is " + epassword);
            userData = userRepository.save(userData);
            System.out.println(userData);
            otp = generateOtpAndSendEmail(userData);
            return userData;
        } else {
            throw new BookStoreException("Email already exists",
                    BookStoreException.ExceptionType.USER_ALREADY_PRESENT);
        }
    }


    /**
     * @Purpose This method is used to verify otp while user registration
     */
    @Override
    public ResponseDTO verifyOtp(Long otp) {
        if (otp.equals(otp) && userData.getIsVerified().equals(false)) {
            userData.setIsVerified(true);
            userRepository.save(userData);
            return new ResponseDTO("otp verified", userData);
        }
        return new ResponseDTO("Invalid otp", "please enter correct otp");
    }

    /**
     * @Purpose This method is used to login user with
     * correct email and password
     */
    @Override
    public ResponseDTO loginUser(UserLoginDTO userLoginDTO) {
        System.out.println(userLoginDTO.getEmail());
        UserData userDataByEmail = userRepository.findUserDataByEmail(userLoginDTO.getEmail());
        if (userDataByEmail == null) {
            throw new BookStoreException("Enter registered Email", BookStoreException.ExceptionType.EMAIL_NOT_FOUND);
        }
        if (userDataByEmail.getIsVerified()) {
            boolean isPassword = bCryptPasswordEncoder.matches(userLoginDTO.getPassword(),
                    userDataByEmail.getPassword());
            if (!isPassword) {
                throw new BookStoreException("Invalid Password!!!Please Enter Correct Password",
                        BookStoreException.ExceptionType.PASSWORD_INVALID);
            }
            String jwtToken = tokenGenerator.generateLoginToken(userDataByEmail);
            userDataByEmail.setJwtToken(jwtToken);
            userRepository.save(userDataByEmail);
            return new ResponseDTO("Logged in successfully", userDataByEmail);
        }
        otp = generateOtpAndSendEmail(userDataByEmail);
        throw new BookStoreException("Please verify your email before proceeding",
                BookStoreException.ExceptionType.EMAIL_NOT_FOUND);
    }

    /**
     * @Purpose This method is used to generate otp and send email
     */
    private Long generateOtpAndSendEmail(UserData userData) {
        long generatedOtp = otpGenerator.generateOTP();
        String requestUrl = "http://localhost:9094/bookstore/verify/email/" + generatedOtp;
        System.out.println("the generated otp is " + generatedOtp);
        try {
            emailSenderService.sendEmail(
                    userData.getEmail(),
                    "Your Registration is successful",
                    requestUrl + "\n your generated otp is "
                            + generatedOtp +
                            " click on the above link to verify the user");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return generatedOtp;
    }



    /**
     * @Purpose This method is used to generate otp for forgot password request
     */
    @Override
    public String forgotPasswordRequest(String email) {
        userData = userRepository.findUserDataByEmail(email);
        if (userData == null) {
            throw new BookStoreException("User Not Found");
        }
        otp = otpGenerator.generateOTP();
        String generatedOtp = "Otp is generated \n" + otp;
        emailSenderService.sendEmail(userData.getEmail(), "Your otp is ", generatedOtp);
        return "Reset Password otp Has Been Sent To Your Email Address " + userData.getEmail();
    }

    /**
     * @Purpose This method is used to reset password
     */
    @Override
    public String resetPassword(String password, Long mailOtp) {
        String ePassword = bCryptPasswordEncoder.encode(password);
        if (!mailOtp.equals(otp)) {
            throw new BookStoreException("Enter Correct OTP");
        }
        userData.setPassword(ePassword);
        userRepository.save(userData);
        return "password Reset successfully";
    }

    @Override
    public int getAllUserNumber() {
        List<UserData> alldata = getUserData();
        int count = 0;
        for(int i = 0; alldata.size()> i ; i++){
            count++;
        }
        return count;
    }

    /**
     * @Purpose This method is used to update the user data
     */
    @Override
    public UserData updateUserData(Integer id, UserDTO userDTO) {
        UserData updateUser = this.getUserDataById(id);
        updateUser.updateUserData(userDTO);
        updateUser.setPassword(bCryptPasswordEncoder.encode(updateUser.getPassword()));
        return userRepository.save(updateUser);
    }

    /**
     * @Purpose This method is used to delete the user data
     */
    @Override
    public void deleteUserData(Integer userId) {
        UserData deleteUser = this.getUserDataById(userId);
        userRepository.delete(deleteUser);
    }

    /**
     * @Purpose This method is used to get list of all user data
     */
    @Override
    public List<UserData> getUserData() {
        return userRepository.findAll();
    }

}


