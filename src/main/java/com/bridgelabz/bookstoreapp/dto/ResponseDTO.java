package com.bridgelabz.bookstoreapp.dto;

import com.bridgelabz.bookstoreapp.model.UserData;
import lombok.Data;

public @Data class ResponseDTO {
    private String message;
    private Object data;

    public ResponseDTO(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public ResponseDTO() {

    }


}
