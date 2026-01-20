package dao;

import util.DBConnection;
import java.sql.*;

public class AdminDAO {

    public boolean validateAdmin(String username, String password) {

        boolean valid = false;

        try {
            // DEBUG LINE (ADD HERE)
            System.out.println("AdminDAO.validateAdmin() called");

            Connection con = DBConnection.getConnection();

            //  CONFIRM DB CONNECTION
            System.out.println("DB Connected for admin login");

            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM admin_users WHERE username=? AND password=?"
            );

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                valid = true;
                System.out.println("Admin credentials VALID");
            } else {
                System.out.println("Admin credentials INVALID");
            }

            con.close();

        } catch (Exception e) {
            System.out.println("ERROR inside AdminDAO");
            e.printStackTrace();
        }

        return valid;
    }
}
