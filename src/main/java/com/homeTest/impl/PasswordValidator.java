package com.homeTest.impl;

import com.homeTest.exception.InvalidPasswordException;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator {

    private static PasswordValidator instance = null;

    private PasswordValidator() {
    }

    public static PasswordValidator getInstance() {
        if (instance == null) {
            instance = new PasswordValidator();
        }
        return instance;
    }

    /***
     *
     * @param oldPassword
     * @param newPassword
     * @return Returns true if the new password meets all the criteria; else false.
     * @throws InvalidPasswordException
     */
    public boolean changePassword(String oldPassword, String newPassword) throws InvalidPasswordException {
        // If password length is less than 18 characters, throw exception
        if (newPassword.length() < 18) {
            throw new InvalidPasswordException("Password length cannot be less than 18");
        }
        // Verify if password has one upper case, one lower case, one numeric and one special character
        if (!verifyForAllCharacters(newPassword)) {
            throw new InvalidPasswordException("Password must contain one upper case, one lower case, one numeric and one special character");
        }
        // Verify 50% of password should not be a number
        // Replace all non-digits characters with null to extract digits
        String extractNumbers = newPassword.replaceAll("\\D", "");
        if (extractNumbers.length() >= (newPassword.length() / 2)) {
            throw new InvalidPasswordException("50% of password should not be a number");
        }
        // No more than 4 special characters
        // Replace all the non-special characters in the string with null to extract special characters
        String specialChars = newPassword.replaceAll("\\w", "");
        if (specialChars.length() > 4) {
            throw new InvalidPasswordException("No more than 4 special characters");
        }
        //verify if password a 4 or more repeating characters
        Map<Character, Integer> newPasswordCount = getCharacterCount(newPassword);
        AtomicBoolean isInvalidPassword = new AtomicBoolean(false);
        // Iterate over values in map and set isInvalidPassword to true, if value greater than 4;
        newPasswordCount.values().forEach(value -> {
            if (value > 4) {
                isInvalidPassword.set(true);
            }
        });
        if (isInvalidPassword.get()) {
            throw new InvalidPasswordException("Cannot have more than 4 repeating characters");
        }
        Map<Character, Integer> oldPasswordCount = getCharacterCount(oldPassword);
        //Compare old password and new password for 80% match
        if (isPasswordEquals(newPasswordCount, oldPasswordCount, newPassword.length())) {
            throw new InvalidPasswordException("New password cannot match more than 80%");
        }
        return true;
    }

    public boolean validateNewPassword(String oldPassword) throws InvalidPasswordException {
        // If password length is less than 18 characters, throw exception
        if (oldPassword.length() < 18) {
            throw new InvalidPasswordException("Password length cannot be less than 18");
        }
        // Verify if password has one upper case, one lower case, one numeric and one special character
        if (!verifyForAllCharacters(oldPassword)) {
            throw new InvalidPasswordException("Password must contain one upper case, one lower case, one numeric and one special character");
        }
        // Verify 50% of password should not be a number
        // Replace all non-digits characters with null to extract digits
        String extractNumbers = oldPassword.replaceAll("\\D", "");
        if (extractNumbers.length() >= (oldPassword.length() / 2)) {
            throw new InvalidPasswordException("50% of password should not be a number");
        }
        // No more than 4 special characters
        // Replace all the non-special characters in the string with null to extract special characters
        String specialChars = oldPassword.replaceAll("\\w", "");
        if (specialChars.length() > 4) {
            throw new InvalidPasswordException("No more than 4 special characters");
        }
        //verify if password a 4 or more repeating characters
        Map<Character, Integer> newPasswordCount = getCharacterCount(oldPassword);
        AtomicBoolean isInvalidPassword = new AtomicBoolean(false);
        // Iterate over values in map and set isInvalidPassword to true, if value greater than 4;
        newPasswordCount.values().forEach(value -> {
            if (value > 4) {
                isInvalidPassword.set(true);
            }
        });
        if (isInvalidPassword.get()) {
            throw new InvalidPasswordException("Cannot have more than 4 repeating characters");
        }
        return true;
    }

    /***
     *
     * @param arg1
     * @return Returns a map where key represents the character in the string and
     * value represents the occurences the
     */
    private Map<Character, Integer> getCharacterCount(String arg1) {
        Map<Character, Integer> characterCountMap = new HashMap<>();
        char[] charArray = arg1.toCharArray();
        for (char ch : charArray) {
            if (characterCountMap.containsKey(ch)) {
                int value = characterCountMap.get(ch);
                value = value + 1;
                characterCountMap.put(ch, value);
            } else {
                characterCountMap.put(ch, 1);
            }
        }
        return characterCountMap;
    }

    /***
     *
     * @param newPassword
     * @param oldPassword
     * @param newPasswordLength
     * @return True if the newPassword and oldPassword has more than 80% match, else false.
     */
    private boolean isPasswordEquals(Map<Character, Integer> newPassword, Map<Character, Integer> oldPassword, int newPasswordLength) {
        var ref = new Object() {
            int maxCharMatch = (newPasswordLength * 80) / 100;
        };
        newPassword.forEach((newPasswordKey, newPasswordValue) -> {
            if (oldPassword.containsKey(newPasswordKey)) {
                int oldPasswordValue = oldPassword.get(newPasswordKey);
                ref.maxCharMatch = ref.maxCharMatch - oldPasswordValue;
            }
        });
        // If ref.maxMatchChar is negative number, then old password and new password has 80% match
        // If ref.maxMatchChar is positive number, then old password and new password does not have 80% match
        System.out.println(ref.maxCharMatch);
        return ref.maxCharMatch <= 0;
    }

    private boolean verifyForAllCharacters(String newPassword) {
        String[] pattern = {".*\\d.*", ".*[a-z].*", ".*[A-Z].*", ".*[!@#$&*].*"};
        for (String s : pattern) {
            Matcher matcher = Pattern.compile(s).matcher(newPassword);
            if (!matcher.matches()) {
                return false;
            }
        }
        return true;
    }
}
