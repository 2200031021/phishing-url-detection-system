<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin Login</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<div class="container">
    <h1>🔐 Admin Login</h1>

    <!-- ✅ FIXED FORM ACTION -->
    <form action="<%= request.getContextPath() %>/admin-login" method="post">

        <input
            type="text"
            name="username"
            placeholder="Admin Username"
            required
        >

        <input
            type="password"
            name="password"
            placeholder="Admin Password"
            required
        >

        <button type="submit">Login</button>
    </form>

    <!-- ERROR MESSAGE -->
    <p class="warning">
        <%
            String error = (String) request.getAttribute("error");
            if (error != null) {
                out.print(error);
            }
        %>
    </p>
</div>

</body>
</html>
