package jdbc.complaintManagmentSystem;

import java.sql.*;
import java.util.*;

public class ComplaintManagmentSystem {

    static final String url =
        "jdbc:mysql://localhost:3306/complaint_db";
    static final String user = "root";
    static final String pass = "ella@123";

    static Connection con;

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int ch;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(url, user, pass);

            do {
                System.out.println("\n1. Add Complaint");
                System.out.println("2. View Complaints");
                System.out.println("3. Update Status");
                System.out.println("4. Delete Complaint");
                System.out.println("5. Exit");
                System.out.print("Enter choice: ");

                ch = sc.nextInt();

                switch (ch) {
                    case 1:
                        addComplaint(sc);
                        break;

                    case 2:
                        viewComplaints();
                        break;

                    case 3:
                        updateStatus(sc);
                        break;

                    case 4:
                        deleteComplaint(sc);
                        break;

                    case 5:
                        System.out.println("Program Exit");
                        break;

                    default:
                        System.out.println("Invalid Choice");
                }

            } while (ch != 5);

            con.close();
            sc.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void addComplaint(Scanner sc)
            throws SQLException {

        sc.nextLine();

        System.out.print("Enter your name: ");
        String name = sc.nextLine();

        System.out.print("Enter complaint: ");
        String complaint = sc.nextLine();

        String sql =
            "INSERT INTO complaints(name,complaint,status) " +
            "VALUES (?,?,?)";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, name);
        ps.setString(2, complaint);
        ps.setString(3, "Pending");

        ps.executeUpdate();

        System.out.println("Complaint Added Successfully");
    }

    public static void viewComplaints()
            throws SQLException {

        String sql = "SELECT * FROM complaints";

        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);

        System.out.println("Complaint Records:");

        while (rs.next()) {
            System.out.println(
                rs.getInt("id") + " " +
                rs.getString("name") + " " +
                rs.getString("complaint") + " " +
                rs.getString("status")
            );
        }
    }

    public static void updateStatus(Scanner sc)
            throws SQLException {

        System.out.print("Enter complaint ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter new status: ");
        String status = sc.nextLine();

        String sql =
            "UPDATE complaints SET status=? WHERE id=?";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, status);
        ps.setInt(2, id);

        int row = ps.executeUpdate();

        if (row > 0)
            System.out.println("Status Updated");
        else
            System.out.println("Complaint Not Found");
    }

    public static void deleteComplaint(Scanner sc)
            throws SQLException {

        System.out.print("Enter complaint ID: ");
        int id = sc.nextInt();

        String sql = "DELETE FROM complaints WHERE id=?";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, id);

        int row = ps.executeUpdate();

        if (row > 0)
            System.out.println("Complaint Deleted");
        else
            System.out.println("Complaint Not Found");
    }
}