package com.app.dto;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.app.model.User;
import com.app.util.DBUtil;

public class UserDAO {
    public User findByCredentials(String username, String passwordHash) throws SQLException {
        String sql = "SELECT id, username, role FROM users WHERE username=? AND password_hash=?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, passwordHash);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? new User(rs.getInt("id"), rs.getString("username"), rs.getString("role")) : null;
            }
        }
    }
}