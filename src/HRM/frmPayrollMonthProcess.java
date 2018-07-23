/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HRM;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

/**
 *
 * @author USER
 */
public class frmPayrollMonthProcess extends javax.swing.JFrame {
String ShowEmpID,ShowDepartment,ShowDesignation;
double WholeSalarySum,BasicSalary;
DbConnection DbConn = new DbConnection();
    public frmPayrollMonthProcess() {
        initComponents();
        DbConn.DoConnect();
        FillEmployee();
        FillComboMonthsAndYears();
        setLocationRelativeTo(null);
        setLocationRelativeTo(null);
        cmbEmployee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {GetEmpId();
            GetEmployeeDetails();}
        });
    }
    private void GetEmployeeDetails(){
         try{
            DbConn.pstmt = DbConn.conn.prepareStatement("select * from tblemployeeprofile where em_name=? and em_seqno=?");
            DbConn.pstmt.setString(1, cmbEmployee.getSelectedItem().toString());
            DbConn.pstmt.setString(2, ShowEmpID);
            DbConn.rs = DbConn.pstmt.executeQuery();
            if (DbConn.rs.next()){
                ShowDepartment = DbConn.rs.getString("em_department");
                ShowDesignation = DbConn.rs.getString("em_designation");
            }
            DbConn.pstmt.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    private void GetEmpId(){
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("Select al_empid from tblallowance where al_empname=?");
            DbConn.pstmt.setString(1, cmbEmployee.getSelectedItem().toString());
            DbConn.rs = DbConn.pstmt.executeQuery();
            if (DbConn.rs.next()){
                ShowEmpID = DbConn.rs.getString(1);
                System.out.println("The id is: " + ShowEmpID);
            }
            DbConn.pstmt.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    private void FillEmployee(){
        cmbEmployee.removeAllItems();
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("select em_name from tblemployeeprofile order by em_name");
            DbConn.rs = DbConn.pstmt.executeQuery();
            while (DbConn.rs.next()){
                cmbEmployee.addItem(DbConn.rs.getString(1));
            }
            DbConn.pstmt.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    private void FillComboMonthsAndYears(){
        cmbMonth.removeAllItems();
        cmbMonth.addItem("January");
        cmbMonth.addItem("February");
        cmbMonth.addItem("March");
        cmbMonth.addItem("April");
        cmbMonth.addItem("May");
        cmbMonth.addItem("June");
        cmbMonth.addItem("July");
        cmbMonth.addItem("August");
        cmbMonth.addItem("September");
        cmbMonth.addItem("October");
        cmbMonth.addItem("November");
        cmbMonth.addItem("December");
        //years
        cmbYear.removeAllItems();
        int currentYear = 2018;
        while (currentYear <= 2030){
            cmbYear.addItem(String.valueOf(currentYear));
            currentYear++;
        }
        
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        cmbEmployee = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableAttendance = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        cmbMonth = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        cmbYear = new javax.swing.JComboBox<>();
        txtMonthdays = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(214, 214, 194));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(214, 214, 194));
        jPanel2.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(153, 153, 153)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel16.setForeground(new java.awt.Color(0, 51, 51));
        jLabel16.setText("Month:");
        jPanel2.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 50, 50, 20));

        cmbEmployee.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel2.add(cmbEmployee, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 20, 330, -1));

        tableAttendance.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tableAttendance);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 470, 360));

        jButton4.setBackground(new java.awt.Color(51, 153, 0));
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Upload Attendance");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 90, 160, -1));

        jButton5.setBackground(new java.awt.Color(0, 0, 153));
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Submit");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 540, 80, -1));

        jLabel18.setForeground(new java.awt.Color(0, 51, 51));
        jLabel18.setText("Employee:");
        jPanel2.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 100, 20));

        cmbMonth.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel2.add(cmbMonth, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 50, 110, -1));

        jLabel19.setForeground(new java.awt.Color(0, 51, 51));
        jLabel19.setText("Year:");
        jPanel2.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 50, 50, 20));

        cmbYear.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel2.add(cmbYear, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 50, 110, -1));

        txtMonthdays.setText("jTextField1");
        jPanel2.add(txtMonthdays, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 90, -1, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 490, 580));

        jLabel17.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 51, 51));
        jLabel17.setText("Attendance Import");
        jPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 230, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 824, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 676, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
Vector headers = new Vector();
        Vector data = new Vector();
        //DefaultTableModel model = (DefaultTableModel) tblMessage.getModel();
        try{
            JFileChooser jf = new JFileChooser();
            jf.setDialogTitle("Choose .xls file");
            FileNameExtensionFilter fileFilter = new FileNameExtensionFilter(".xls files", "xls");
            jf.setFileFilter(fileFilter);
            jf.addChoosableFileFilter(fileFilter);
            
            int result = jf.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION){
            String getPath = jf.getSelectedFile().getAbsolutePath();
            File pathFile = new File(getPath);
            Workbook workbook = Workbook.getWorkbook(pathFile);
            Sheet sheet = workbook.getSheet(0);
            headers.clear();
            for (int i = 0; i < sheet.getColumns(); i++) {
            Cell cell1 = sheet.getCell(i, 0);
            headers.add(cell1.getContents());
            }
            data.clear();
            for (int j = 1; j < sheet.getRows(); j++) {
            Vector d = new Vector();
            for (int i = 0; i < sheet.getColumns(); i++) {
            Cell cell = sheet.getCell(i, j);
            d.add(cell.getContents());
            }
            d.add("\n");
            data.add(d);
            }
            }
        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
            DefaultTableModel model = new DefaultTableModel(data,headers);
            tableAttendance.setModel(model);
            tableAttendance.setAutoCreateRowSorter(true);
            model = new DefaultTableModel(data,headers);
            tableAttendance.setModel(model);
        
       
    }//GEN-LAST:event_jButton4ActionPerformed
    private void FetchWholeSalary(){
         try{
            DbConn.SQLQuery = "select sum(al_amount) from tblallowance where al_empname=? and al_empid=?";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.pstmt.setString(1, cmbEmployee.getSelectedItem().toString());
            DbConn.pstmt.setString(2, ShowEmpID);
            DbConn.rs = DbConn.pstmt.executeQuery();
            if(DbConn.rs.next()){
                WholeSalarySum = DbConn.rs.getDouble(1);
                System.out.println("salary ay: " + WholeSalarySum);
            }
            DbConn.pstmt.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    private void FetchBasicSalary(){
    try{
            DbConn.SQLQuery = "select sum(al_amount) from tblallowance where al_empname=? and al_empid=? and al_type=?";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.pstmt.setString(1, cmbEmployee.getSelectedItem().toString());
            DbConn.pstmt.setString(2, ShowEmpID);
            DbConn.pstmt.setString(3, "Basic Salary");
            DbConn.rs = DbConn.pstmt.executeQuery();
            if(DbConn.rs.next()){
                BasicSalary = DbConn.rs.getDouble(1);
                System.out.println("basic "+ BasicSalary);
            }
            DbConn.pstmt.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        
        //get the whole salary
        FetchWholeSalary();
        //get the basic salary
        FetchBasicSalary();
        //save to payout
        try{
            int i = 0;
            while (i<tableAttendance.getRowCount()){
                DbConn.SQLQuery = "insert into tblpayout (po_empname,po_empid,po_empdepartment,po_empdesignation,po_basicsalary,"
                        + "po_datemonth,po_dateyear,po_absent,po_processedby,po_finalbasic) values(?,?,?,?,?,?,?,?,?,?)";
                DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
                DbConn.pstmt.setString(1, tableAttendance.getModel().getValueAt(i, 0).toString());
                DbConn.pstmt.setString(2, tableAttendance.getModel().getValueAt(i, 1).toString());
                DbConn.pstmt.setString(3, tableAttendance.getModel().getValueAt(i, 2).toString());
                DbConn.pstmt.setString(4, tableAttendance.getModel().getValueAt(i, 3).toString());
                DbConn.pstmt.setDouble(5, Double.parseDouble(tableAttendance.getModel().getValueAt(i, 4).toString()));
                DbConn.pstmt.setString(6, cmbMonth.getSelectedItem().toString());
                DbConn.pstmt.setString(7, cmbYear.getSelectedItem().toString());
                DbConn.pstmt.setInt(8, Integer.parseInt(tableAttendance.getModel().getValueAt(i, 5).toString()));
                DbConn.pstmt.setString(9, "Admin");
                //get Calculation basic: basic / monthdays * absentdays
                double GetPerDay = Double.parseDouble(tableAttendance.getModel().getValueAt(i, 4).toString()) / Integer.parseInt(txtMonthdays.getText());
                double GetAbsentCalc = GetPerDay * Integer.parseInt(tableAttendance.getModel().getValueAt(i, 5).toString());
                DbConn.pstmt.setDouble(10, Double.parseDouble(DbConn.df.format(Double.parseDouble(tableAttendance.getModel().getValueAt(i, 4).toString()) - GetAbsentCalc)));
                DbConn.pstmt.execute();
                DbConn.pstmt.close();
                i++;
            }
            JOptionPane.showMessageDialog(this, "Payroll Submitted");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmPayrollMonthProcess.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmPayrollMonthProcess.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmPayrollMonthProcess.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmPayrollMonthProcess.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmPayrollMonthProcess().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cmbEmployee;
    private javax.swing.JComboBox<String> cmbMonth;
    private javax.swing.JComboBox<String> cmbYear;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableAttendance;
    private javax.swing.JTextField txtMonthdays;
    // End of variables declaration//GEN-END:variables
}
