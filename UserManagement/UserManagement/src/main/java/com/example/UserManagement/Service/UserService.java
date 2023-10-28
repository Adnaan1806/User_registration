package com.example.UserManagement.Service;

import com.example.UserManagement.Data.User;
import com.example.UserManagement.Data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private boolean isValidEmail(String email) {
        return email != null && email.contains("@"); //@ should contain in the emailID
    }

    private boolean isValidPassword(String password) {
        return password != null && password.length() >= 8; //Maximum password length is 8
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User loginUser(String emailId, String password) {
        // Triming whitespaces incase
        emailId = emailId.trim();
        password = password.trim();

        // Validating email and password
        if (isValidEmail(emailId) && isValidPassword(password)) {
            return userRepository.findByEmailIdAndPassword(emailId, password);
        } else {
            return null;
        }
    }

    public boolean updatePassword(String emailId, String newPassword) {
        User user = userRepository.findByEmailId(emailId);

        if (user != null) {
            // Update the password and save the user
            user.setPassword(newPassword);
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }
}
