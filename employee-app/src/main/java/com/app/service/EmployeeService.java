package com.app.service;




import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.app.dto.EmployeeDAO;
import com.app.model.Employee;

public class EmployeeService {
    private final EmployeeDAO dao = new EmployeeDAO();

    public List<Employee> search(String q) throws SQLException { return dao.search(q); }
    public Employee get(int id) throws SQLException { return dao.findById(id); }
    public void delete(int id) throws SQLException { dao.delete(id); }

    /** Returns a map of field -> error message. Empty map means saved successfully. */
    public Map<String, String> save(Employee e) throws SQLException {
        Map<String, String> errors = validate(e);
        if (!errors.isEmpty()) return errors;
        try {
            if (e.getId() == 0) dao.insert(e); else dao.update(e);
        } catch (SQLException ex) {
            if ("23505".equals(ex.getSQLState())) {           // unique violation
                errors.put("email", "This email already exists.");
                return errors;
            }
            throw ex;
        }
        return errors;
    }

    private Map<String, String> validate(Employee e) {
        Map<String, String> errors = new LinkedHashMap<>();
        if (e.getName() == null || e.getName().isEmpty()) errors.put("name", "Name is required.");
        else if (e.getName().length() > 100) errors.put("name", "Name must be 100 characters or fewer.");

        if (e.getEmail() == null || !e.getEmail().matches("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$"))
            errors.put("email", "Enter a valid email address.");

        if (e.getDepartment() != null && e.getDepartment().length() > 50)
            errors.put("department", "Department must be 50 characters or fewer.");

        if (e.getSalary() < 0) errors.put("salary", "Salary must be a number, zero or more.");
        return errors;
    }
}