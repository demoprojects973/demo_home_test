package com.homeTest.exception;

public class InvalidPasswordException extends Exception {
    public InvalidPasswordException(String message){
        super(message);
    }
}
