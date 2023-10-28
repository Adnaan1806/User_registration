package com.example.UserManagement.Controller;

import com.example.UserManagement.Data.User;
import com.example.UserManagement.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/createUsers")
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @PostMapping("/loginUsers")
    public ResponseEntity<?> loginUser(@RequestBody User loginUser) {
        try {
            String emailId = loginUser.getEmailId();
            String password = loginUser.getPassword();

            // Performing validations
            if (emailId != null && password != null) {
                User loggedInUser = userService.loginUser(emailId, password);

                if (loggedInUser != null) {
                    // Returning the json response if the validation is correct
                    return ResponseEntity.ok().body("You have successfully logged in");
                } else {
                    // Indicates Login failed
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
                }
            } else {
                // Indicates Invalid email or password
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email and password are required");
            }
        } catch (Exception e) {
            // Catching Exceptional errors
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }

    @PostMapping("/updatePasswords")
    public ResponseEntity<?> updatePassword(@RequestBody User updateUser) {
        try {
            String emailId = updateUser.getEmailId();
            String newPassword = updateUser.getPassword();

            //Performing validations
            if (emailId != null && newPassword != null) {
                boolean isUpdated = userService.updatePassword(emailId, newPassword);

                if (isUpdated) {
                    return ResponseEntity.ok().body("Password updated successfully");
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or unable to update password");
                }
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email and new password are required");
            }
        } catch (Exception e) {
            //To catch exceptional errors
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }
}
