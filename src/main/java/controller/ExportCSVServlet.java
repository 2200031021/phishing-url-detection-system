package controller;

import util.DBConnection;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.*;
import java.sql.*;

@WebServlet("/export")
public class ExportCSVServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        res.setContentType("text/csv");
        res.setHeader("Content-Disposition", "attachment; filename=phishing_logs.csv");

        PrintWriter out = res.getWriter();
        out.println("URL,Risk Score,Result,Checked At");

        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM url_logs");

            while (rs.next()) {
                out.println(
                    rs.getString("url") + "," +
                    rs.getInt("risk_score") + "," +
                    rs.getString("result") + "," +
                    rs.getTimestamp("checked_at")
                );
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
