<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Employee Form</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body class="center">
  <form class="card" action="${pageContext.request.contextPath}/employees" method="post">
    <h2>${employee.id > 0 ? 'Edit' : 'Add'} Employee</h2>
    <input type="hidden" name="id" value="${employee.id}">

    <label>Name</label>
    <input type="text" name="name" value="<c:out value='${employee.name}'/>">
    <span class="error">${errors.name}</span>

    <label>Email</label>
    <input type="text" name="email" value="<c:out value='${employee.email}'/>">
    <span class="error">${errors.email}</span>

    <label>Department</label>
    <input type="text" name="department" value="<c:out value='${employee.department}'/>">
    <span class="error">${errors.department}</span>

    <label>Salary</label>
    <input type="text" name="salary" value="${employee.salary}">
    <span class="error">${errors.salary}</span>

    <button type="submit">Save</button>
    <a href="${pageContext.request.contextPath}/employees">Cancel</a>
  </form>
</body>
</html>