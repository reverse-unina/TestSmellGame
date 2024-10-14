package com.dariotintore.tesi.userservice.utils;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class UserCheck {

    private UserCheck(){
        // Empty
    }
    public static boolean isPasswordLongEnough(String password) {
        return password.length() >= 6;
    }

    public static String passwordEncrypter(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean checkPassword(String passwordInPlain, String passwordEncrypted) {
        return BCrypt.checkpw(passwordInPlain, passwordEncrypted);
    }

}
