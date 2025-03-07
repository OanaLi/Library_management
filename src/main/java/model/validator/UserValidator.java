package model.validator;


import model.User;
import repository.user.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class UserValidator {
    private static final String EMAIL_VALIDATION_REGEX = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    public static final int MIN_PASSWORD_LENGTH = 8;
    private final List<String> errors;
    private final User user;

    public UserValidator(User user) {
        this.user = user;
        errors = new ArrayList<>();
    }

    public boolean validate() {
        validateUsername(user.getUsername());
        validatePassword(user.getPassword());

        return errors.isEmpty();
    }

    private void validateUsername(String username) {
        if(!Pattern.compile(EMAIL_VALIDATION_REGEX).matcher(username).matches()) {
            errors.add("Email is not valid!");
        }
    }

    private void validatePassword(String password) {
        if(password.length() < MIN_PASSWORD_LENGTH) {
            errors.add(String.format("Password must be at least %d characters long!", MIN_PASSWORD_LENGTH));
        }
        if (!containsSpecialCharacter(password)){
            errors.add("Password must contain at least one special character.");
        }
        if(!containsDigit(password)){
            errors.add("Password must contain at least one digit!");
        }
    }


    private boolean containsSpecialCharacter(String password){
        if (password == null || password.trim().isEmpty()){
            return false;
        }

        Pattern specialCharacterPattern = Pattern.compile("[^A-Za-z0-9]"); //black list
        Matcher specialCharacterMatcher = specialCharacterPattern.matcher(password);

        return specialCharacterMatcher.find();
    }

    private boolean containsDigit(String password) {
        return Pattern.compile(".*[0-9].*").matcher(password).find();
    }


    public List<String> getErrors() {
        return errors;
    }

    public String getFormattedErrors() {
        return String.join("\n", errors);
    }
}