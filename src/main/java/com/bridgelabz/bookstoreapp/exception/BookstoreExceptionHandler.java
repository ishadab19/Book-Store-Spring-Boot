package com.bridgelabz.bookstoreapp.exception;

import com.bridgelabz.bookstoreapp.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class BookstoreExceptionHandler {

    /**
     * @Purpose : User Friendly Error Response in case validation fails
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        List<ObjectError> errorList = exception.getBindingResult().getAllErrors();
        List<String> errMesg = errorList.stream()
                .map(objectError -> objectError.getDefaultMessage())
                .collect(Collectors.toList());
        ResponseDTO responseDTO = new ResponseDTO("Exception While processing REST Request", errMesg);
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }

    /**
     * @Purpose : User Friendly Errors in case person Id is not found in bookstore app
     */
    @ExceptionHandler(BookStoreException.class)
    public ResponseEntity<ResponseDTO> handleMethodBookstoreException(BookStoreException exception) {
        ResponseDTO responseDTO = new ResponseDTO("Exception While processing REST Request", exception.getMessage());
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }
}
