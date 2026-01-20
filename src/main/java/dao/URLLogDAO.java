package dao;

import util.DBConnection;
import java.sql.*;

public class URLLogDAO {

    /* 
       1. SAVE DETECTION RESULT
       */
    public void saveResult(String url, int score, String result) throws Exception {
        Connection con = DBConnection.getConnection();

        PreparedStatement ps = con.prepareStatement(
            "INSERT INTO url_logs (url, risk_score, result) VALUES (?, ?, ?)"
        );
        ps.setString(1, url);
        ps.setInt(2, score);
        ps.setString(3, result);

        ps.executeUpdate();
        con.close();
    }

    /* 
       2. COUNT HOW MANY TIMES A URL IS CHECKED
       */
    public int getURLCheckCount(String url) throws Exception {
        Connection con = DBConnection.getConnection();

        PreparedStatement ps = con.prepareStatement(
            "SELECT COUNT(*) FROM url_logs WHERE url = ?"
        );
        ps.setString(1, url);

        ResultSet rs = ps.executeQuery();
        int count = 0;
        if (rs.next()) {
            count = rs.getInt(1);
        }

        con.close();
        return count;
    }

    /*
       3. TOTAL URL COUNT (ADMIN DASHBOARD)
       */
    public int getTotalCount() throws Exception {
        Connection con = DBConnection.getConnection();

        PreparedStatement ps = con.prepareStatement(
            "SELECT COUNT(*) FROM url_logs"
        );
        ResultSet rs = ps.executeQuery();

        rs.next();
        int count = rs.getInt(1);

        con.close();
        return count;
    }

    /* 
       4. TOTAL PHISHING COUNT
        */
    public int getPhishingCount() throws Exception {
        Connection con = DBConnection.getConnection();

        PreparedStatement ps = con.prepareStatement(
            "SELECT COUNT(*) FROM url_logs WHERE result = 'PHISHING'"
        );
        ResultSet rs = ps.executeQuery();

        rs.next();
        int count = rs.getInt(1);

        con.close();
        return count;
    }

    /* 
       5. DAILY STATISTICS (FOR GRAPHS)
       */
    public ResultSet getDailyStats() throws Exception {
        Connection con = DBConnection.getConnection();

        PreparedStatement ps = con.prepareStatement(
            "SELECT DATE(checked_at) AS day, COUNT(*) AS count " +
            "FROM url_logs GROUP BY DATE(checked_at)"
        );

        return ps.executeQuery();
    }
}
