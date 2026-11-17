<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Employees</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="topbar">
  <h2>Employee Management</h2>
  <form action="${pageContext.request.contextPath}/logout" method="post">
    <span>Hi, <c:out value="${sessionScope.user.username}"/></span>
    <button type="submit" class="link">Logout</button>
  </form>
</div>

<c:if test="${not empty flash}"><p class="success"><c:out value="${flash}"/></p></c:if>

<div class="toolbar">
  <form action="${pageContext.request.contextPath}/employees" method="get">
    <input type="text" name="q" placeholder="Search name, email, department" value="<c:out value='${q}'/>">
    <button type="submit">Search</button>
  </form>
  <a class="btn" href="${pageContext.request.contextPath}/employees?action=new">+ Add Employee</a>
</div>

<table>
  <tr><th>ID</th><th>Name</th><th>Email</th><th>Department</th><th>Salary</th><th>Actions</th></tr>
  <c:forEach var="e" items="${employees}">
    <tr>
      <td>${e.id}</td>
      <td><c:out value="${e.name}"/></td>
      <td><c:out value="${e.email}"/></td>
      <td><c:out value="${e.department}"/></td>
      <td><fmt:formatNumber value="${e.salary}" type="number" minFractionDigits="2"/></td>
      <td class="actions">
        <a class="btn" href="${pageContext.request.contextPath}/employees?action=edit&id=${e.id}">Edit</a>
        <form action="${pageContext.request.contextPath}/employees" method="post"
              onsubmit="return confirm('Delete this employee?')">
          <input type="hidden" name="action" value="delete">
          <input type="hidden" name="id" value="${e.id}">
          <button type="submit" class="btn danger">Delete</button>
        </form>
      </td>
    </tr>
  </c:forEach>
  <c:if test="${empty employees}"><tr><td colspan="6">No employees found.</td></tr></c:if>
</table>
</body>
</html>