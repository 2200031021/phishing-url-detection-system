<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, java.util.Map" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Detection Result</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<div class="container result-container">

    <h1 class="result-title">🔍 Detection Result</h1>

    <div class="result-card">

        <!-- Detection summary -->
        <div class="features">
            <h3>Detection Summary</h3>
            <ul>
                <li>✔ URL extracted from copied message</li>
                <li>✔ URL structure and length analyzed</li>
                <li>✔ HTTPS and IP-based patterns checked</li>
                <li>✔ Suspicious symbols evaluated</li>
                <li>✔ Social-engineering keywords detected</li>
            </ul>
        </div>

        <!-- Interpretation -->
        <div class="features">
            <h3>Risk Interpretation</h3>
            <ul>
                <li><strong>SAFE:</strong> Low risk, appears legitimate</li>
                <li><strong>SUSPICIOUS:</strong> Caution advised before opening</li>
                <li><strong>PHISHING:</strong> High risk, do not open</li>
            </ul>
        </div>

        <!-- ✅ TABLE FOR MULTIPLE URL RESULTS -->
        <table>
            <tr>
                <th>URL</th>
                <th>Risk Score</th>
                <th>Status</th>
                <th>Checked</th>
                <th>Reputation</th>
                <th>Message Clues</th> <!-- ✅ ADDED -->
            </tr>

            <%
                List<Map<String, Object>> results =
                    (List<Map<String, Object>>) request.getAttribute("results");

                if (results != null) {
                    for (Map<String, Object> row : results) {
            %>
            <tr>
                <td class="url-cell"><%= row.get("url") %></td>
                <td><%= row.get("score") %></td>
                <td class="<%= row.get("result") %>">
                    <%= row.get("result") %>
                </td>
                <td><%= row.get("repeatCount") %> time(s)</td>
                <td><%= row.get("reputationScore") %></td>
                <td><%= row.get("messageScore") %></td> <!-- ✅ ADDED -->
            </tr>

            <!-- ⚠️ SOCIAL ENGINEERING WARNING -->
            <%
                Integer msgScore = (Integer) row.get("messageScore");
                if (msgScore != null && msgScore > 20) {
            %>
            <tr>
                <td colspan="6" class="warning"> <!-- updated colspan -->
                    ⚠️ Social-engineering language detected (urgent / verify / blocked).
                </td>
            </tr>
            <%
                }

                Boolean rep = (Boolean) row.get("repeated");
                if (rep != null && rep) {
            %>
            <tr>
                <td colspan="6" class="warning">
                    ⚠️ Repeated detection — possible phishing campaign.
                </td>
            </tr>
            <%
                }
                    }
                }
            %>
        </table>

        <!-- Explanation -->
        <p class="info">
            Domain reputation is calculated using access frequency,
            protocol security, and suspicious naming patterns.
        </p>

    </div>

    <a href="index.html" class="back-btn">Check Another Message</a>
</div>

</body>
</html>
