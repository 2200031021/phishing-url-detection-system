<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>System Architecture</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<div class="container">
    <h1>🏗 System Architecture</h1>

    <p>The system follows a layered MVC-based architecture:</p>

    <ul>
        <li><strong>Frontend:</strong> HTML, CSS, JavaScript</li>
        <li><strong>Controller:</strong> Java Servlets</li>
        <li><strong>Business Logic:</strong> PhishingDetectionService</li>
        <li><strong>Database:</strong> MySQL</li>
    </ul>

    <h3>Flow</h3>
    <p>
        User → Web Interface → Servlet → Algorithm → Database → Result Page
    </p>

    <a href="index.html" class="back-btn">Back</a>
</div>

</body>
</html>
