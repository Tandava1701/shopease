package com.app.controller;



import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.app.model.Employee;
import com.app.service.EmployeeService;

@WebServlet("/employees")
public class EmployeeController extends HttpServlet {
    private final EmployeeService service = new EmployeeService();
    private static final String LIST = "/WEB-INF/views/employee-list.jsp";
    private static final String FORM = "/WEB-INF/views/employee-form.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        try {
            if ("new".equals(action)) {
                req.getRequestDispatcher(FORM).forward(req, resp);
            } else if ("edit".equals(action)) {
                Employee e = service.get(parseId(req.getParameter("id")));
                if (e == null) { resp.sendError(HttpServletResponse.SC_NOT_FOUND); return; }
                req.setAttribute("employee", e);
                req.getRequestDispatcher(FORM).forward(req, resp);
            } else {
                String q = req.getParameter("q");
                req.setAttribute("employees", service.search(q));
                req.setAttribute("q", q);
                HttpSession s = req.getSession();
                req.setAttribute("flash", s.getAttribute("flash"));   // show once
                s.removeAttribute("flash");
                req.getRequestDispatcher(LIST).forward(req, resp);
            }
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        try {
            if ("delete".equals(req.getParameter("action"))) {
                service.delete(parseId(req.getParameter("id")));
                req.getSession().setAttribute("flash", "Employee deleted.");
            } else {
                Employee e = readForm(req);
                Map<String, String> errors = service.save(e);
                if (!errors.isEmpty()) {
                    req.setAttribute("employee", e);
                    req.setAttribute("errors", errors);
                    req.getRequestDispatcher(FORM).forward(req, resp);
                    return;
                }
                req.getSession().setAttribute("flash", "Employee saved.");
            }
            resp.sendRedirect(req.getContextPath() + "/employees");   // Post-Redirect-Get
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private int parseId(String s) {
        return (s == null || s.trim().isEmpty()) ? 0 : Integer.parseInt(s.trim());
    }

    private Employee readForm(HttpServletRequest req) {
        Employee e = new Employee();
        e.setId(parseId(req.getParameter("id")));
        e.setName(trim(req.getParameter("name")));
        e.setEmail(trim(req.getParameter("email")));
        e.setDepartment(trim(req.getParameter("department")));
        try {
            e.setSalary(Double.parseDouble(trim(req.getParameter("salary"))));
        } catch (NumberFormatException | NullPointerException ex) {
            e.setSalary(-1);      // triggers the validation message
        }
        return e;
    }

    private String trim(String s) { return s == null ? null : s.trim(); }
}