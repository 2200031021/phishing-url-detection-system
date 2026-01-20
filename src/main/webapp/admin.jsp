<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="css/style.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>

<div class="container">
    <!-- HEADER WITH LOGOUT -->
    <div style="display:flex; justify-content:space-between; align-items:center;">
        <h1>📊 Admin Dashboard</h1>
        <a href="logout" class="back-btn">Logout</a>
    </div>

    <!-- STATS -->
    <div class="stats">
        <p><strong>Total URLs Checked:</strong> ${total}</p>
        <p><strong>Phishing URLs:</strong> ${phishing}</p>
    </div>

    <!-- CHARTS -->
    <canvas id="pieChart"></canvas>
    <canvas id="barChart" style="margin-top:30px;"></canvas>

    <!-- EXPORT BUTTON -->
    <a href="export" class="back-btn" style="margin-top:20px; display:inline-block;">
        ⬇ Export Logs (CSV)
    </a>
</div>

<script>
    const safe = ${total} - ${phishing};
    const phishing = ${phishing};

    // Pie Chart
    new Chart(document.getElementById("pieChart"), {
        type: "pie",
        data: {
            labels: ["SAFE", "PHISHING"],
            datasets: [{
                data: [safe, phishing],
                backgroundColor: ["#2ecc71", "#ff2e2e"]
            }]
        }
    });

    // Bar Chart
    new Chart(document.getElementById("barChart"), {
        type: "bar",
        data: {
            labels: ${days},
            datasets: [{
                label: "Daily URL Checks",
                data: ${counts},
                backgroundColor: "#2b4eff"
            }]
        }
    });
</script>

</body>
</html>
