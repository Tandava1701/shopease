<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Error</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body class="center">
  <div class="card">
    <h2>Something went wrong</h2>
    <p>The page you asked for could not be shown. Please go back and try again.</p>
    <a class="btn" href="${pageContext.request.contextPath}/employees">Back to employees</a>
  </div>
</body>
</html>