package com.example.UserManagement.Data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmailIdAndPassword(String emailId, String password);
    User findByEmailId(String emailId);
}
