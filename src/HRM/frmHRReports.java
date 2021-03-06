package HRM;

import java.io.File;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;


public class frmHRReports extends javax.swing.JFrame {
DbConnection DbConn = new DbConnection();
    public frmHRReports() {
        initComponents();
        DbConn.DoConnect();
        FillNationality();
        FillDepartment();
        FillDesignation();
        FillEmployee();
        FillMonthsAndYears();
        SetHeaderTables();
        setLocationRelativeTo(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(frmHRReports.DISPOSE_ON_CLOSE);
    }
    private void SetHeaderTables(){
        tableNationality.getColumnModel().getColumn(0).setHeaderValue("NATIONALITY");
        tableNationality.getColumnModel().getColumn(1).setHeaderValue("NAME");
        tableNationality.getColumnModel().getColumn(2).setHeaderValue("ID");
        
         tableDepartment.getColumnModel().getColumn(0).setHeaderValue("ID");   
         tableDepartment.getColumnModel().getColumn(1).setHeaderValue("NAME");   
         tableDepartment.getColumnModel().getColumn(2).setHeaderValue("DESIGNATION");   
         tableDepartment.getColumnModel().getColumn(3).setHeaderValue("TYPE");   
      
         tableDesignation.getColumnModel().getColumn(0).setHeaderValue("ID");   
         tableDesignation.getColumnModel().getColumn(1).setHeaderValue("NAME");   
         tableDesignation.getColumnModel().getColumn(2).setHeaderValue("DEPARTMENT");   
         tableDesignation.getColumnModel().getColumn(3).setHeaderValue("TYPE");   
    }
    private void FillMonthsAndYears(){
        cmbMonthPayroll.removeAllItems();
        cmbYearPayroll.removeAllItems();
        
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("select distinct(po_datemonth),(po_dateyear),(po_status) from tblpayout");
            DbConn.rs = DbConn.pstmt.executeQuery();
            while (DbConn.rs.next()){
                cmbMonthPayroll.addItem(DbConn.rs.getString(1));
                cmbYearPayroll.addItem(DbConn.rs.getString(2));
                }
            DbConn.pstmt.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    private void FillEmployee(){
        cmbEmployeePayroll.removeAllItems();
        try{
            DbConn.pstmt  = DbConn.conn.prepareStatement("Select em_name from tblemployeeprofile order by em_name");
            DbConn.rs = DbConn.pstmt.executeQuery();
            while (DbConn.rs.next()){
                cmbEmployeePayroll.addItem(DbConn.rs.getString(1));
            }
            DbConn.pstmt.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    private void FillDesignation(){
        cmbDesignation.removeAllItems();
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("Select distinct(em_designation) from tblemployeeprofile order by em_designation");
            DbConn.rs = DbConn.pstmt.executeQuery();
            while (DbConn.rs.next()){
                cmbDesignation.addItem(DbConn.rs.getString(1));
            }
            DbConn.pstmt.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    private void FillDepartment(){
        cmbDepartment.removeAllItems();
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("Select distinct(em_department) from tblemployeeprofile order by em_department");
            DbConn.rs = DbConn.pstmt.executeQuery();
            while (DbConn.rs.next()){
                cmbDepartment.addItem(DbConn.rs.getString(1));
            }
            DbConn.pstmt.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    private void FillNationality(){
        cmbNationality.removeAllItems();
        try{
            DbConn.SQLQuery = "select nm_description from tblnationality order by nm_description";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.rs = DbConn.pstmt.executeQuery();
            while(DbConn.rs.next()){
                cmbNationality.addItem(DbConn.rs.getString(1));
            }
            DbConn.pstmt.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
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
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        cmbNationality = new javax.swing.JComboBox<>();
        lblNationalityCount = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableNationality = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        cmbDepartment = new javax.swing.JComboBox<>();
        lblDepartmentCount = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableDepartment = new javax.swing.JTable();
        jButton5 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        cmbDesignation = new javax.swing.JComboBox<>();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableDesignation = new javax.swing.JTable();
        lblDesignationCount = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        cmbMonthPayroll = new javax.swing.JComboBox<>();
        jLabel21 = new javax.swing.JLabel();
        cmbYearPayroll = new javax.swing.JComboBox<>();
        btnViewPayroll = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        cmbEmployeePayroll = new javax.swing.JComboBox<>();
        btnPrintPayroll = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(214, 214, 194));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 860, 10));

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 51));
        jLabel2.setText("HR Reports");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 130, -1));

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel5.setBackground(new java.awt.Color(214, 214, 194));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cmbNationality.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel5.add(cmbNationality, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 20, 170, -1));

        lblNationalityCount.setForeground(new java.awt.Color(0, 51, 51));
        lblNationalityCount.setText("0");
        jPanel5.add(lblNationalityCount, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 460, 50, -1));

        tableNationality.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tableNationality);

        jPanel5.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, 360));

        jButton4.setBackground(new java.awt.Color(92, 184, 92));
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Print");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 20, 80, -1));

        jButton1.setBackground(new java.awt.Color(51, 122, 183));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("View");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 20, 70, -1));

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 51));
        jLabel4.setText("Select:");
        jPanel5.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 50, -1));

        jLabel5.setForeground(new java.awt.Color(0, 51, 51));
        jLabel5.setText("Count:");
        jPanel5.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 460, 50, -1));

        jButton6.setBackground(new java.awt.Color(240, 173, 78));
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("View All");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 60, -1, -1));

        jPanel2.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 480, 490));

        jTabbedPane1.addTab("Nationality", jPanel2);

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel6.setBackground(new java.awt.Color(214, 214, 194));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cmbDepartment.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel6.add(cmbDepartment, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 20, 170, -1));

        lblDepartmentCount.setForeground(new java.awt.Color(0, 51, 51));
        lblDepartmentCount.setText("0");
        jPanel6.add(lblDepartmentCount, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 460, 50, -1));

        tableDepartment.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tableDepartment);

        jPanel6.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, 360));

        jButton5.setBackground(new java.awt.Color(92, 184, 92));
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Print");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel6.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 20, 80, -1));

        jButton2.setBackground(new java.awt.Color(51, 122, 183));
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("View");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel6.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 20, 70, -1));

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 51, 51));
        jLabel6.setText("Select:");
        jPanel6.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 50, -1));

        jLabel7.setForeground(new java.awt.Color(0, 51, 51));
        jLabel7.setText("Count:");
        jPanel6.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 460, 50, -1));

        jButton3.setBackground(new java.awt.Color(240, 173, 78));
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("View All");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel6.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 60, -1, -1));

        jPanel3.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 480, 490));

        jTabbedPane1.addTab("Department", jPanel3);

        jPanel4.setBackground(new java.awt.Color(214, 214, 194));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 51, 51));
        jLabel8.setText("Select:");
        jPanel4.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 50, -1));

        cmbDesignation.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel4.add(cmbDesignation, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 20, 170, -1));

        jButton7.setBackground(new java.awt.Color(51, 122, 183));
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("View");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 20, 70, -1));

        jButton8.setBackground(new java.awt.Color(92, 184, 92));
        jButton8.setForeground(new java.awt.Color(255, 255, 255));
        jButton8.setText("Print");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 20, 80, -1));

        jButton9.setBackground(new java.awt.Color(240, 173, 78));
        jButton9.setForeground(new java.awt.Color(255, 255, 255));
        jButton9.setText("View All");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 60, -1, -1));

        tableDesignation.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(tableDesignation);

        jPanel4.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, 360));

        lblDesignationCount.setForeground(new java.awt.Color(0, 51, 51));
        lblDesignationCount.setText("0");
        jPanel4.add(lblDesignationCount, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 460, 50, -1));

        jLabel9.setForeground(new java.awt.Color(0, 51, 51));
        jLabel9.setText("Count:");
        jPanel4.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 460, 50, -1));

        jTabbedPane1.addTab("Designation", jPanel4);

        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel8.setBackground(new java.awt.Color(214, 214, 194));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel20.setForeground(new java.awt.Color(0, 51, 51));
        jLabel20.setText("Month:");
        jPanel8.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 60, 20));

        cmbMonthPayroll.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel8.add(cmbMonthPayroll, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, 100, -1));

        jLabel21.setForeground(new java.awt.Color(0, 51, 51));
        jLabel21.setText("Year:");
        jPanel8.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 20, 60, 20));

        cmbYearPayroll.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel8.add(cmbYearPayroll, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 20, 100, -1));

        btnViewPayroll.setBackground(new java.awt.Color(51, 122, 183));
        btnViewPayroll.setForeground(new java.awt.Color(255, 255, 255));
        btnViewPayroll.setText("View");
        btnViewPayroll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewPayrollActionPerformed(evt);
            }
        });
        jPanel8.add(btnViewPayroll, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 20, 80, -1));

        jLabel18.setForeground(new java.awt.Color(0, 51, 51));
        jLabel18.setText("Employee:");
        jPanel8.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 60, 20));

        cmbEmployeePayroll.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel8.add(cmbEmployeePayroll, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 60, 150, -1));

        btnPrintPayroll.setBackground(new java.awt.Color(92, 184, 92));
        btnPrintPayroll.setForeground(new java.awt.Color(255, 255, 255));
        btnPrintPayroll.setText("Print");
        btnPrintPayroll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintPayrollActionPerformed(evt);
            }
        });
        jPanel8.add(btnPrintPayroll, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 60, 80, -1));

        jPanel7.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 480, 120));

        jTabbedPane1.addTab("Payroll", jPanel7);

        jPanel1.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 500, 530));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 518, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 601, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try{
            DbConn.SQLQuery = "select pi_nationality, pi_empname,pi_empid from tblpersonalinfo where pi_nationality=?";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.pstmt.setString(1, cmbNationality.getSelectedItem().toString());
            DbConn.rs = DbConn.pstmt.executeQuery();
            tableNationality.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
            DbConn.pstmt.close();
            SetHeaderTables();
            lblNationalityCount.setText(String.valueOf(tableNationality.getRowCount()));
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
          Map param = new HashMap();
            param.put("titleReport", "Department Personnel");
            param.put("GetDepartment", cmbDepartment.getSelectedItem().toString());
            try{
                DbConn.conn.close();
                Class.forName("com.mysql.jdbc.Driver");
                DbConn.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbeshr?autoReconnect=true","root","root");
                JasperDesign jd = JRXmlLoader.load(new File("src\\HRReports\\report2.jrxml"));
                JasperReport jr = JasperCompileManager.compileReport(jd);
                JasperPrint jp = JasperFillManager.fillReport(jr, param,DbConn.conn);
                JasperViewer.viewReport(jp,false);

            }catch(ClassNotFoundException | SQLException | JRException e){
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
        try{
            DbConn.SQLQuery = "select em_seqno,em_name,em_designation,em_emptype from tblemployeeprofile where em_department=? order by em_name";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.pstmt.setString(1, cmbDepartment.getSelectedItem().toString());
            DbConn.rs = DbConn.pstmt.executeQuery();
            tableDepartment.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
            DbConn.pstmt.close();
            SetHeaderTables();
            lblDepartmentCount.setText(String.valueOf(tableDepartment.getRowCount()));
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this,e.getMessage());
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
      
        try{
            DbConn.SQLQuery = "select em_seqno,em_name,em_designation,em_emptype from tblemployeeprofile order by em_department";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.rs = DbConn.pstmt.executeQuery();
            tableDepartment.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
            DbConn.pstmt.close();
            SetHeaderTables();
            lblDepartmentCount.setText(String.valueOf(tableDepartment.getRowCount()));
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this,e.getMessage());
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        try{
            DbConn.SQLQuery = "select pi_nationality, pi_empname,pi_empid from tblpersonalinfo order by pi_nationality";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.rs = DbConn.pstmt.executeQuery();
            tableNationality.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
            DbConn.pstmt.close();
            SetHeaderTables();
            lblNationalityCount.setText(String.valueOf(tableNationality.getRowCount()));
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }        
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        try{
            DbConn.SQLQuery = "select em_seqno,em_name,em_department,em_emptype from tblemployeeprofile "
                    + "where em_designation =? order by em_department";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.pstmt.setString(1, cmbDesignation.getSelectedItem().toString());
            DbConn.rs = DbConn.pstmt.executeQuery();
            tableDesignation.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
            DbConn.pstmt.close();
            SetHeaderTables();
            lblDesignationCount.setText(String.valueOf(tableDesignation.getRowCount()));
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        try{
            DbConn.SQLQuery = "select em_seqno,em_name,em_department,em_emptype from tblemployeeprofile "
                    + "order by em_department";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.rs = DbConn.pstmt.executeQuery();
            tableDesignation.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
            DbConn.pstmt.close();
            SetHeaderTables();
            lblDesignationCount.setText(String.valueOf(tableDesignation.getRowCount()));
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void btnViewPayrollActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewPayrollActionPerformed
            //get the amount allowance
            double GetAllowanceTotal= 0.0, GetFinalBasic = 0.0;
            try{
                DbConn.SQLQuery = "select sum(pd_amount) from tblpayrolldetails where pd_empname=? "
                        + "and pd_datemonth=? and pd_dateyear=?";
                DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
                DbConn.pstmt.setString(1, cmbEmployeePayroll.getSelectedItem().toString());
                DbConn.pstmt.setString(2, cmbMonthPayroll.getSelectedItem().toString());
                DbConn.pstmt.setString(3, cmbYearPayroll.getSelectedItem().toString());
                DbConn.rs = DbConn.pstmt.executeQuery();
                if (DbConn.rs.next()){
                    GetAllowanceTotal = DbConn.rs.getDouble(1);
                }
                DbConn.pstmt.close();
                System.out.println("total payroll ay " + GetAllowanceTotal);
            }catch(SQLException e){
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
            
            //get the final basic salary
            try{
                DbConn.SQLQuery = "select pd_finalbasic from tblpayrolldetails where pd_empname=? "
                        + "and pd_datemonth=? and pd_dateyear=?";
                DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
                DbConn.pstmt.setString(1, cmbEmployeePayroll.getSelectedItem().toString());
                DbConn.pstmt.setString(2, cmbMonthPayroll.getSelectedItem().toString());
                DbConn.pstmt.setString(3, cmbYearPayroll.getSelectedItem().toString());
                DbConn.rs = DbConn.pstmt.executeQuery();
                if (DbConn.rs.next()){
                    GetFinalBasic = DbConn.rs.getDouble(1);
                    //tablePayroll.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
                }
                DbConn.pstmt.close();
                System.out.println("total payroll ay " + GetFinalBasic);
            }catch(SQLException e){
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
            System.out.println("total amount mo ay " + (GetAllowanceTotal + GetFinalBasic));
    }//GEN-LAST:event_btnViewPayrollActionPerformed

    private void btnPrintPayrollActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintPayrollActionPerformed
        //int row = tablePayroll.getSelectedRow();
        //int ba = tablePayroll.convertRowIndexToModel(row);
         Map param = new HashMap();
            param.put("GetYear", cmbYearPayroll.getSelectedItem().toString());
            param.put("GetMonth", cmbMonthPayroll.getSelectedItem().toString());
            param.put("EmpName", cmbEmployeePayroll.getSelectedItem().toString());

            try{                
                //view letter offer
                DbConn.conn.close();
                Class.forName("com.mysql.jdbc.Driver");
                DbConn.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbeshr?autoReconnect=true","root","root");
                JasperDesign jd = JRXmlLoader.load(new File("src\\HRReports\\reportSalarySlip.jrxml"));
                JasperReport jr = JasperCompileManager.compileReport(jd);
                JasperPrint jp = JasperFillManager.fillReport(jr, param,DbConn.conn);
                JasperViewer.viewReport(jp,false);

            }catch(ClassNotFoundException | SQLException | JRException e){
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
            
    }//GEN-LAST:event_btnPrintPayrollActionPerformed

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
            java.util.logging.Logger.getLogger(frmHRReports.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmHRReports.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmHRReports.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmHRReports.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmHRReports().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPrintPayroll;
    private javax.swing.JButton btnViewPayroll;
    private javax.swing.JComboBox<String> cmbDepartment;
    private javax.swing.JComboBox<String> cmbDesignation;
    private javax.swing.JComboBox<String> cmbEmployeePayroll;
    private javax.swing.JComboBox<String> cmbMonthPayroll;
    private javax.swing.JComboBox<String> cmbNationality;
    private javax.swing.JComboBox<String> cmbYearPayroll;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblDepartmentCount;
    private javax.swing.JLabel lblDesignationCount;
    private javax.swing.JLabel lblNationalityCount;
    private javax.swing.JTable tableDepartment;
    private javax.swing.JTable tableDesignation;
    private javax.swing.JTable tableNationality;
    // End of variables declaration//GEN-END:variables
}
