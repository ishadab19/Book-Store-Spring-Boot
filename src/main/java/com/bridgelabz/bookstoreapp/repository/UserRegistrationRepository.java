package com.bridgelabz.bookstoreapp.repository;

import com.bridgelabz.bookstoreapp.model.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRegistrationRepository extends JpaRepository<UserData, Integer> {
    UserData findUserDataByEmail(String email);
}
