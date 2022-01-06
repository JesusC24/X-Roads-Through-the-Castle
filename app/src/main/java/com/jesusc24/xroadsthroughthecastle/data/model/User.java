package com.jesusc24.xroadsthroughthecastle.data.model;

/**
 * POJO User para guardar y poder editar la información de los diferentes usuarios del sistema
 */
public class User {
    public static final String TAG = "user";
    String user, email, password, confirmPassword;

    public User(String usuario, String password) {
        this.email = usuario;
        this.password = password;
    }

    public User(String usuario, String email, String password, String confirmPassword) {
        user = usuario;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUser() {
        return user;
    }

    public String getConfirmPassword() { return confirmPassword; }

    @Override
    public String toString() {
        return "User{" +
                "user='" + user + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                '}';
    }
}
