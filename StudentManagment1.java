package jdbc.studentManagment;
import java.sql.*;
import java.util.*;

public class StudentManagment1 {

    static final String url =
        "jdbc:mysql://localhost:3306/student_db";
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
                System.out.println("\n1. Add Student");
                System.out.println("2. View Students");
                System.out.println("3. Update Student");
                System.out.println("4. Delete Student");
                System.out.println("5. Exit");
                System.out.print("Enter choice: ");

                ch = sc.nextInt();

                switch (ch) {
                    case 1:
                        addStudent(sc);
                        break;

                    case 2:
                        viewStudents();
                        break;

                    case 3:
                        updateStudent(sc);
                        break;

                    case 4:
                        deleteStudent(sc);
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
    
    

    public static void addStudent(Scanner sc)
            throws SQLException {

        sc.nextLine();

        System.out.print("Enter name: ");
        String name = sc.nextLine();

        System.out.print("Enter email: ");
        String email = sc.nextLine();

        String sql =
            "INSERT INTO students(name,email) VALUES (?,?)";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, name);
        ps.setString(2, email);

        ps.executeUpdate();

        
        
        System.out.println("Student Added Successfully");
    }

    public static void viewStudents()
            throws SQLException {

    	
        String sql = "SELECT * FROM students";

        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);

        
        System.out.println("Student Records:");

        while (rs.next()) {
            System.out.println(
                rs.getInt("id") + " " +
                rs.getString("name") + " " +
                rs.getString("email")
            );
    
 
        }
        
    }
    
    
  
    
    
    public static void updateStudent(Scanner sc)
            throws SQLException {

          System.out.print("Enter student ID: ");
        int id = sc.nextInt();
      sc.nextLine();

        System.out.print("Enter new email: ");
      String email = sc.nextLine();

        
        
        String sql ="UPDATE students SET email=? WHERE id=?";

        PreparedStatement ps = con.prepareStatement(sql);

           ps.setString(1, email);
          ps.setInt(2, id);

        int row = ps.executeUpdate();

       
        
        if (row > 0)
            System.out.println("Student Updated");
        else
        	
            System.out.println("Student Not Found");
    }

    public static void deleteStudent(Scanner sc)
            throws SQLException {

        System.out.print("Enter student ID: ");
       int id = sc.nextInt();

      String sql = "DELETE FROM students WHERE id=?";

        PreparedStatement ps = con.prepareStatement(sql);

        
        
      ps.setInt(1, id);

      int row = ps.executeUpdate();

    if (row > 0)
            System.out.println("Student Deleted");
    
       else
    	   
            System.out.println("Student Not Found");
    }
}
