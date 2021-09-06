package com.homeTest.model;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;


public class User {
    String userName;
    String password;

    private User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public User createUser(String userName, String password) {
        return new User(userName, password);
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

}
