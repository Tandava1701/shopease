package com.app.dto;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.app.model.Employee;
import com.app.util.DBUtil;

public class EmployeeDAO {

    public List<Employee> search(String keyword) throws SQLException {
        boolean all = keyword == null || keyword.trim().isEmpty();
        String sql = all
            ? "SELECT * FROM employees ORDER BY id"
            : "SELECT * FROM employees WHERE name ILIKE ? OR email ILIKE ? OR department ILIKE ? ORDER BY id";
        List<Employee> list = new ArrayList<>();
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            if (!all) {
                String like = "%" + keyword.trim() + "%";
                ps.setString(1, like); ps.setString(2, like); ps.setString(3, like);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
        }
        return list;
    }

    public Employee findById(int id) throws SQLException {
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement("SELECT * FROM employees WHERE id=?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? map(rs) : null; }
        }
    }

    public void insert(Employee e) throws SQLException {
        String sql = "INSERT INTO employees(name,email,department,salary) VALUES(?,?,?,?)";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, e.getName()); ps.setString(2, e.getEmail());
            ps.setString(3, e.getDepartment()); ps.setDouble(4, e.getSalary());
            ps.executeUpdate();
        }
    }

    public void update(Employee e) throws SQLException {
        String sql = "UPDATE employees SET name=?,email=?,department=?,salary=? WHERE id=?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, e.getName()); ps.setString(2, e.getEmail());
            ps.setString(3, e.getDepartment()); ps.setDouble(4, e.getSalary());
            ps.setInt(5, e.getId());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement("DELETE FROM employees WHERE id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private Employee map(ResultSet rs) throws SQLException {
        return new Employee(rs.getInt("id"), rs.getString("name"), rs.getString("email"),
                rs.getString("department"), rs.getDouble("salary"));
    }
}