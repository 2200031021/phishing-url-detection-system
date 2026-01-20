package controller;

import service.PhishingDetectionService;
import dao.URLLogDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.*;
import java.util.regex.*;

@WebServlet("/check")
public class PhishingCheckServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String inputText = req.getParameter("inputText");

        //  Extract ALL URLs from message
        List<String> urls = new ArrayList<>();
        Matcher matcher = Pattern.compile("https?://\\S+").matcher(inputText);

        while (matcher.find()) {
            urls.add(matcher.group());
        }

        PhishingDetectionService service = new PhishingDetectionService();
        URLLogDAO dao = new URLLogDAO();

        //  Store results for table display
        List<Map<String, Object>> results = new ArrayList<>();

        for (String url : urls) {

            // Base score
            int score = service.calculateRiskScore(url, inputText);

            //  ADD MESSAGE SOCIAL-ENGINEERING SCORE
            int messageScore = service.detectSocialEngineering(inputText);
            score += messageScore;

            // DB lookups
            int previousCount = 0;
            try {
                previousCount = dao.getURLCheckCount(url);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //  ADD DOMAIN REPUTATION SCORE
            int reputationScore = service.applyDomainReputation(url, previousCount);
            score += reputationScore;

            // Get result after boosting score
            String result = service.getResult(score);

            // Save result
            try {
                dao.saveResult(url, score, result);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Prepare table row
            Map<String, Object> row = new HashMap<>();
            row.put("url", url);
            row.put("score", score);
            row.put("result", result);
            row.put("repeatCount", previousCount + 1);
            row.put("repeated", previousCount >= 3);
            row.put("reputationScore", reputationScore);

            //  STORE messageScore for UI
            row.put("messageScore", messageScore);

            results.add(row);
        }

        // Forward results
        req.setAttribute("results", results);
        req.getRequestDispatcher("result.jsp").forward(req, res);
    }
}
