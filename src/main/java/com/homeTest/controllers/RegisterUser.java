package com.homeTest.controllers;

import com.homeTest.dataStore.DataStorage;
import com.homeTest.exception.InvalidPasswordException;
import com.homeTest.impl.PasswordValidator;
import com.homeTest.model.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class RegisterUser {

    @RequestMapping(method = RequestMethod.POST, value = "/registerUser")
    public String registerNewUser(@RequestBody User user) {
        boolean isPasswordValid;
        try {
            isPasswordValid = PasswordValidator.getInstance().validateNewPassword(user.getPassword());
        } catch (InvalidPasswordException invalidPasswordException) {
            return invalidPasswordException.getMessage();
        }
        if (isPasswordValid) {
            DataStorage.getInstance().addUser(user);
        }
        return user.getUserName();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/updatePassword")
    public String updatePassword(@RequestBody User user) {
        boolean isPasswordValid = true;

        String oldPassword = DataStorage.getInstance().getUserPassword(user.getUserName());
        try {
            isPasswordValid = PasswordValidator.getInstance().changePassword(oldPassword, user.getPassword());
        } catch (InvalidPasswordException invalidPasswordException) {
            return invalidPasswordException.getMessage();
        }
        if (isPasswordValid) {
            DataStorage.getInstance().updatePassword(user);
        }
        return user.getUserName();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getUsers")
    public Set<String> getUsers() {
        return DataStorage.getInstance().getAllUsers();
    }
}
