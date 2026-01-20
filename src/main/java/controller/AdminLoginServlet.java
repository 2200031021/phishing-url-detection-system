package controller;

import dao.AdminDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/admin-login")
public class AdminLoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        // Get login form data
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // DEBUG (optional but helpful)
        System.out.println("Admin login attempt: " + username);

        // DAO for admin validation
        AdminDAO adminDAO = new AdminDAO();

        // Validate admin credentials
        if (adminDAO.validateAdmin(username, password)) {

            // Create session
            HttpSession session = req.getSession(true);
            session.setAttribute("admin", username);

            System.out.println("Admin login SUCCESS");

            // ✅ FIXED REDIRECT (context-path safe)
            res.sendRedirect(req.getContextPath() + "/admin");

        } else {
            System.out.println("Admin login FAILED");

            // Invalid login
            req.setAttribute("error", "Invalid username or password");
            req.getRequestDispatcher("admin-login.jsp").forward(req, res);
        }
    }
}
