package com.app.service;



import java.sql.SQLException;

import com.app.dto.UserDAO;
import com.app.model.User;
import com.app.util.PasswordUtil;

public class AuthService {
    private final UserDAO dao = new UserDAO();

    public User login(String username, String password) throws SQLException {
        if (username == null || password == null || username.trim().isEmpty() || password.isEmpty()) return null;
        return dao.findByCredentials(username.trim(), PasswordUtil.sha256(password));
    }
}