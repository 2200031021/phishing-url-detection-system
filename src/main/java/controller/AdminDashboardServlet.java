package controller;

import dao.URLLogDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.*;

@WebServlet("/admin")
public class AdminDashboardServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        URLLogDAO dao = new URLLogDAO();

        try {
            int total = dao.getTotalCount();
            int phishing = dao.getPhishingCount();

            ResultSet rs = dao.getDailyStats();
            List<String> days = new ArrayList<>();
            List<Integer> counts = new ArrayList<>();

            while (rs.next()) {
                days.add(rs.getString("day"));
                counts.add(rs.getInt("count"));
            }

            req.setAttribute("total", total);
            req.setAttribute("phishing", phishing);
            req.setAttribute("days", days);
            req.setAttribute("counts", counts);

        } catch (Exception e) {
            e.printStackTrace();
        }

        req.getRequestDispatcher("admin.jsp").forward(req, res);
    }
}
