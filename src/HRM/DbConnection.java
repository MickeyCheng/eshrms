package HRM;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DbConnection {
    ResultSet rs;
    PreparedStatement pstmt;
    Connection conn;
    String SQLQuery,GetLoggedInUserName;
    int GetEmpIDToSave;
    Date DateToday = new Date();
    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat sdfDateDisplay = new SimpleDateFormat("d MMM yyyy");
    DecimalFormat df = new DecimalFormat("0.000");
    
   String aaa = "";
    public void DoConnect()
    {
        try{
            Class.forName("com.mysql.jdbc.Driver");
//            conn = DriverManager.getConnection("jdbc:mysql://166.62.10.53:3306/DbEmrPh","jopjop","strongadmin123");
//            conn = DriverManager.getConnection("jdbc:mysql://166.62.10.53:3306/dbemrbeta","betapilfer","123456789");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbeshr?autoReconnect=true","root","root");
    System.out.println("web connected");
        }catch(SQLException | ClassNotFoundException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    public void GetEmpIdForProfile(String EmployeeName){
        try{
            pstmt = conn.prepareStatement("select * from tblemployeeprofile where em_name=?");
            pstmt.setString(1, EmployeeName);
            rs = pstmt.executeQuery();
            if (rs.next()){
                GetEmpIDToSave = rs.getInt("em_seqno");
            }
            pstmt.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
  
}
