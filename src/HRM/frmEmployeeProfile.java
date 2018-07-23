package HRM;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import net.proteanit.sql.DbUtils;


public class frmEmployeeProfile extends javax.swing.JFrame {
DbConnection DbConn = new DbConnection();
boolean AddBasicDetails,EditBasicDetails;
String getImagePath;
public static String PromoteID,PromoteName,PromoteCurrentDes,PromoteProposedDes,PromoteCurrentSal,
        PromoteProposedSal,PromoteCurrentDepartment,PromoteProposedDep;
    public frmEmployeeProfile() {
        initComponents();
        DbConn.DoConnect();
        FillTableEmployee();
        FillDepartment();
        FillDesignation();
        FillType();
        FillRpType();
        FillGrade();
        FillAllowance();
        FillPaymentMode();
        FillNationality();
        FillCivilStatus();
        FillGender();
        FillOTEligible();
        FillTraining();
        FillTrainingDays();
        FillTrainingTable();
        FillDocAttach();
        FillBank();
        FillPromoteStatus();
        FillTrainingDocumentTable();
        FillPromotionList();
        FillApprovingPersonnel();
        SetTablesSorter();
        setDefaultCloseOperation(frmEmployeeProfile.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLocationRelativeTo(null);
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {TextSearchListen();}

            @Override
            public void removeUpdate(DocumentEvent e) {TextSearchListen();}

            @Override
            public void changedUpdate(DocumentEvent e) {TextSearchListen();}
        });
        tableDocument.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
        JTable table =(JTable) mouseEvent.getSource();
        Point point = mouseEvent.getPoint();
        int row = table.rowAtPoint(point);
        if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
            ViewAttachment();
        }
    }
    });

    }
    private void TextSearchListen(){
        try{
            DbConn.SQLQuery = "select * from tblemployeeprofile where em_name like ?";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.pstmt.setString(1, "%" + txtSearch.getText() + "%");
            DbConn.rs = DbConn.pstmt.executeQuery();
            tableEmployee.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
            DbConn.pstmt.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    private void FillApprovingPersonnel(){
        cmbApprovingPersonnel.removeAllItems();
        try{
            DbConn.SQLQuery = "select em_name from tblemployeeprofile where em_approver=? order by em_name";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.pstmt.setString(1, "Yes");
            DbConn.rs = DbConn.pstmt.executeQuery();
            while(DbConn.rs.next())
            {
                cmbApprovingPersonnel.addItem(DbConn.rs.getString(1));
            }
            DbConn.pstmt.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    private void SetTablesSorter(){
        tablePromotionList.setAutoCreateRowSorter(true);
        tableDocument.setAutoCreateRowSorter(true);
        tableEmployee.setAutoCreateRowSorter(true);
        tableTraining.setAutoCreateRowSorter(true);
        tableAllowance.setAutoCreateRowSorter(true);
    }
    private void FillPromotionList(){
        try{
            DbConn.SQLQuery ="select pp_empname,pp_currentdesignation,pp_proposeddesignation,"
                    + "pp_proposedby,pp_proposeddate,pp_status from tblproposalpromote order by pp_proposeddate";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.rs = DbConn.pstmt.executeQuery();
            tablePromotionList.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
            DbConn.pstmt.close();
            SetHeaderPromoteList();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "FIll Promotion List");
        }
    }
    private void SetHeaderPromoteList(){
        tablePromotionList.getColumnModel().getColumn(0).setHeaderValue("NAME");
        tablePromotionList.getColumnModel().getColumn(1).setHeaderValue("CURRENT");
        tablePromotionList.getColumnModel().getColumn(2).setHeaderValue("PROPOSED");
        tablePromotionList.getColumnModel().getColumn(3).setHeaderValue("BY");
        tablePromotionList.getColumnModel().getColumn(4).setHeaderValue("DATE");
        tablePromotionList.getColumnModel().getColumn(5).setHeaderValue("STATUS");
    }
    private void ViewAttachment(){
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("select at_path from tblattachment where at_seqno=?");
            int row = tableDocument.getSelectedRow();
            int ba = tableDocument.convertRowIndexToModel(row);
            DbConn.pstmt.setString(1, tableDocument.getModel().getValueAt(ba, 0).toString());
            DbConn.rs = DbConn.pstmt.executeQuery();
            if (DbConn.rs.next()){
                Runtime.getRuntime().exec("rundll32 url.dll, FileProtocolHandler " + DbConn.rs.getString(1));
            }
            DbConn.pstmt.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "No CV Attached");
        }
    }
    private void FillDocAttach(){
        cmbDocType.removeAllItems();
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("select do_description from tblmasterdocattach order by do_description");
            DbConn.rs = DbConn.pstmt.executeQuery();
            while (DbConn.rs.next()){
                cmbDocType.addItem(DbConn.rs.getString(1));
            }
            DbConn.pstmt.close();
            cmbDocType.setSelectedIndex(-1);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    private void FillTrainingDays(){
        int i = 0;
        cmbTrainingDays.removeAllItems();
        while (i<365){
            cmbTrainingDays.addItem(String.valueOf(i));
            i++;
        }
    }
    private void FillTraining(){
        cmbTrainingType.removeAllItems();
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("select tt_description from tblmastertrainingtype order by tt_description");
            DbConn.rs = DbConn.pstmt.executeQuery();
            while (DbConn.rs.next()){
                cmbTrainingType.addItem(DbConn.rs.getString(1));
            }
            DbConn.pstmt.close();
            cmbTrainingType.setSelectedIndex(-1);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    private void FillOTEligible(){
        cmbOTEligible.removeAllItems();
        cmbOTEligible.addItem("Yes");
        cmbOTEligible.addItem("No");
        cmbOTEligible.setSelectedIndex(-1);
    }
    private void FillGender(){
        cmbGender.removeAllItems();
        cmbGender.addItem("Male");
        cmbGender.addItem("Female");
        cmbGender.setSelectedIndex(-1);
    }
    private void FillCivilStatus(){
        cmbCivilStatus.removeAllItems();
        cmbCivilStatus.addItem("Single");
        cmbCivilStatus.addItem("Married");
        cmbCivilStatus.addItem("Window");
        cmbCivilStatus.addItem("Other");
        cmbCivilStatus.setSelectedIndex(-1);
    }
    private void FillNationality(){
        cmbNationality.removeAllItems();
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("select nm_description from tblnationality order by nm_description");
            DbConn.rs = DbConn.pstmt.executeQuery();
            while (DbConn.rs.next()){
                cmbNationality.addItem(DbConn.rs.getString(1));
            }
            DbConn.pstmt.close();
            cmbNationality.setSelectedIndex(-1);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    private void FillPaymentMode(){
        cmbPaymentMode.removeAllItems();
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("Select pm_description from tblmasterpaymentmode order by pm_description");
            DbConn.rs = DbConn.pstmt.executeQuery();
            while (DbConn.rs.next()){
                cmbPaymentMode.addItem(DbConn.rs.getString(1));
            }
            DbConn.pstmt.close();
            cmbPaymentMode.setSelectedIndex(-1);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Fill Payment Mode");
        }
    }
    private void FillAllowance(){
        cmbAllowance.removeAllItems();
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("Select al_description from tblmasterallowance order by al_description");
            DbConn.rs = DbConn.pstmt.executeQuery();
            while (DbConn.rs.next()){
                cmbAllowance.addItem(DbConn.rs.getString(1));
            }
            DbConn.pstmt.close();
            cmbAllowance.setSelectedIndex(-1);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Fill Allowance");
        }
    }
    private void FillGrade(){
        cmbEmpGrade.removeAllItems();
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("Select gt_description from tblgradetype order by gt_description");
            DbConn.rs = DbConn.pstmt.executeQuery();
            while (DbConn.rs.next()){
                cmbEmpGrade.addItem(DbConn.rs.getString(1));
            }
            DbConn.pstmt.close();
            cmbEmpGrade.setSelectedIndex(-1);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Fill RP");
        }
    }
    private void FillBank(){
        cmbBank.removeAllItems();
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("Select ba_description from tblmasterbank order by ba_description");
            DbConn.rs = DbConn.pstmt.executeQuery();
            while (DbConn.rs.next()){
                cmbBank.addItem(DbConn.rs.getString(1));
            }
            DbConn.pstmt.close();
            cmbBank.setSelectedIndex(-1);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Fill Bank");
        }
    }
    private void FillRpType(){
        cmbRpType.removeAllItems();
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("Select rp_description from tblrptype order by rp_description");
            DbConn.rs = DbConn.pstmt.executeQuery();
            while (DbConn.rs.next()){
                cmbRpType.addItem(DbConn.rs.getString(1));
            }
            DbConn.pstmt.close();
            cmbRpType.setSelectedIndex(-1);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Fill RP");
        }
    }
     private void FillPromoteStatus(){
        cmbPromoteStatus.removeAllItems();
        cmbPromoteStatus1.removeAllItems();
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("Select ps_description from tblpromotestatus order by ps_description");
            DbConn.rs = DbConn.pstmt.executeQuery();
            while (DbConn.rs.next()){
                cmbPromoteStatus.addItem(DbConn.rs.getString(1));
                cmbPromoteStatus1.addItem(DbConn.rs.getString(1));
            }
            DbConn.pstmt.close();
            cmbPromoteStatus.setSelectedIndex(-1);
            cmbPromoteStatus1.setSelectedIndex(-1);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Fill Type");
        }
    }
    private void FillType(){
        cmbEmployeeType.removeAllItems();
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("Select et_description from tblemployeetype order by et_description");
            DbConn.rs = DbConn.pstmt.executeQuery();
            while (DbConn.rs.next()){
                cmbEmployeeType.addItem(DbConn.rs.getString(1));
            }
            DbConn.pstmt.close();
            cmbEmployeeType.setSelectedIndex(-1);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Fill Type");
        }
    }
    private void FillDesignation(){
        cmbDesignation.removeAllItems();
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("Select de_description from tbldesignation order by de_description");
            DbConn.rs = DbConn.pstmt.executeQuery();
            while (DbConn.rs.next()){
                cmbDesignation.addItem(DbConn.rs.getString(1));
            }
            DbConn.pstmt.close();
            cmbDesignation.setSelectedIndex(-1);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Fill Designation");
        }
    }
    private void FillDepartment(){
        cmbDepartment.removeAllItems();
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("Select de_description from tbldepartment order by de_description");
            DbConn.rs = DbConn.pstmt.executeQuery();
            while (DbConn.rs.next()){
                cmbDepartment.addItem(DbConn.rs.getString(1));
            }
            DbConn.pstmt.close();
            cmbDepartment.setSelectedIndex(-1);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Fill Department");
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

        db = new javax.swing.JFileChooser();
        jPanel1 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableEmployee = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        lblEmpID = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtEmployeeName = new javax.swing.JTextField();
        cmbDepartment = new javax.swing.JComboBox<>();
        cmbDesignation = new javax.swing.JComboBox<>();
        cmbEmployeeType = new javax.swing.JComboBox<>();
        dateJoinDate = new com.toedter.calendar.JDateChooser();
        cmbRpType = new javax.swing.JComboBox<>();
        cmbEmpGrade = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAreaAddress = new javax.swing.JTextArea();
        jButton13 = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        txtNationalID = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        cmbApprovingPersonnel = new javax.swing.JComboBox<>();
        chkApprover = new javax.swing.JCheckBox();
        jPanel4 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        cmbPaymentMode = new javax.swing.JComboBox<>();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        txtAccountNumber = new javax.swing.JTextField();
        txtIban = new javax.swing.JTextField();
        cmbOTEligible = new javax.swing.JComboBox<>();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        cmbNationality = new javax.swing.JComboBox<>();
        cmbCivilStatus = new javax.swing.JComboBox<>();
        dateBirth = new com.toedter.calendar.JDateChooser();
        cmbGender = new javax.swing.JComboBox<>();
        txtContactDetails = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        txtEmergencyName = new javax.swing.JTextField();
        txtEmergencyRelation = new javax.swing.JTextField();
        txtEmergencyContact = new javax.swing.JTextField();
        txtEmailAddress = new javax.swing.JTextField();
        cmbBank = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableAllowance = new javax.swing.JTable();
        cmbAllowance = new javax.swing.JComboBox<>();
        txtAmount = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        dateTrainingStart = new com.toedter.calendar.JDateChooser();
        txtLocation = new javax.swing.JTextField();
        cmbTrainingType = new javax.swing.JComboBox<>();
        jScrollPane5 = new javax.swing.JScrollPane();
        tableTraining = new javax.swing.JTable();
        jButton12 = new javax.swing.JButton();
        cmbTrainingDays = new javax.swing.JComboBox<>();
        jLabel40 = new javax.swing.JLabel();
        dateTrainingEnd = new com.toedter.calendar.JDateChooser();
        jLabel43 = new javax.swing.JLabel();
        txtTrainingDescription = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableDocument = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        cmbDocType = new javax.swing.JComboBox<>();
        txtDocumentId = new javax.swing.JTextField();
        dateDocIssue = new com.toedter.calendar.JDateChooser();
        dateDocExpiry = new com.toedter.calendar.JDateChooser();
        jButton11 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTable6 = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jLabel44 = new javax.swing.JLabel();
        cmbPromoteStatus = new javax.swing.JComboBox<>();
        jButton15 = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        tablePromotionList = new javax.swing.JTable();
        jPanel10 = new javax.swing.JPanel();
        lblPromoteProposedDesignation = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        lblPromoteName = new javax.swing.JLabel();
        lblPromoteID = new javax.swing.JLabel();
        lblPromoteCurrentDesignation = new javax.swing.JLabel();
        cmbPromoteStatus1 = new javax.swing.JComboBox<>();
        jLabel54 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        txtAreaProposeRemarks = new javax.swing.JTextArea();
        jButton16 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        lblEmployeeName = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        lblDepartment = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(214, 214, 194));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 1270, 10));

        tableEmployee.setModel(new javax.swing.table.DefaultTableModel(
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
        tableEmployee.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableEmployeeMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tableEmployeeMouseEntered(evt);
            }
        });
        jScrollPane1.setViewportView(tableEmployee);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 430, 1270, 190));

        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Name Search:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 400, 100, 20));
        jPanel1.add(txtSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 390, 380, 30));

        jPanel2.setBackground(new java.awt.Color(214, 214, 194));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblEmpID.setForeground(new java.awt.Color(0, 0, 0));
        lblEmpID.setText("Auto-Generate");
        jPanel2.add(lblEmpID, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 20, 160, 20));

        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Employee Name:");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 100, 20));

        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("Nat'l ID:");
        jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 100, 20));

        jLabel14.setForeground(new java.awt.Color(0, 0, 0));
        jLabel14.setText("Approving Personnel:");
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 140, 20));

        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Address:");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 170, 100, 20));

        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setText("Employee Type:");
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 20, 110, 20));

        jLabel15.setForeground(new java.awt.Color(0, 0, 0));
        jLabel15.setText("Join Date:");
        jPanel2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 50, 100, 20));

        jLabel17.setForeground(new java.awt.Color(0, 0, 0));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Upload Photo");
        jPanel2.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 10, 190, 130));

        jLabel18.setForeground(new java.awt.Color(0, 0, 0));
        jLabel18.setText("Employee ID:");
        jPanel2.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 100, 20));
        jPanel2.add(txtEmployeeName, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 50, 340, -1));

        cmbDepartment.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel2.add(cmbDepartment, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 120, 340, -1));

        cmbDesignation.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel2.add(cmbDesignation, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 160, 340, -1));

        cmbEmployeeType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel2.add(cmbEmployeeType, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 20, 330, -1));

        dateJoinDate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                dateJoinDateMouseReleased(evt);
            }
        });
        jPanel2.add(dateJoinDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 50, 330, 30));

        cmbRpType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel2.add(cmbRpType, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 90, 330, -1));

        cmbEmpGrade.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel2.add(cmbEmpGrade, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 130, 330, -1));

        jLabel19.setForeground(new java.awt.Color(0, 0, 0));
        jLabel19.setText("RP Type:");
        jPanel2.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 90, 100, 20));

        jButton5.setBackground(new java.awt.Color(255, 255, 255));
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/attachmentIconMain.png"))); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(1140, 150, 70, 50));

        jLabel20.setForeground(new java.awt.Color(0, 0, 0));
        jLabel20.setText("Grade:");
        jPanel2.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 130, 100, 20));

        txtAreaAddress.setColumns(20);
        txtAreaAddress.setRows(5);
        jScrollPane2.setViewportView(txtAreaAddress);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 170, 330, 70));

        jButton13.setBackground(new java.awt.Color(255, 255, 255));
        jButton13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/SaveIcon.png"))); // NOI18N
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton13, new org.netbeans.lib.awtextra.AbsoluteConstraints(1150, 210, 50, 50));

        jLabel16.setForeground(new java.awt.Color(0, 0, 0));
        jLabel16.setText("Department:");
        jPanel2.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 100, 20));
        jPanel2.add(txtNationalID, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 90, 340, -1));

        jLabel45.setForeground(new java.awt.Color(0, 0, 0));
        jLabel45.setText("Designation:");
        jPanel2.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 120, 20));

        cmbApprovingPersonnel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel2.add(cmbApprovingPersonnel, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 200, 340, -1));

        chkApprover.setText("Approver");
        jPanel2.add(chkApprover, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 230, 120, -1));

        jTabbedPane1.addTab("Basic Details", jPanel2);

        jPanel4.setBackground(new java.awt.Color(214, 214, 194));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Bank:");
        jPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 100, 20));

        jLabel22.setForeground(new java.awt.Color(0, 0, 0));
        jLabel22.setText("Payment Mode:");
        jPanel4.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 100, 20));

        cmbPaymentMode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel4.add(cmbPaymentMode, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 50, 320, -1));

        jLabel23.setForeground(new java.awt.Color(0, 0, 0));
        jLabel23.setText("Account Number:");
        jPanel4.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 120, 20));

        jLabel24.setForeground(new java.awt.Color(0, 0, 0));
        jLabel24.setText("IBAN:");
        jPanel4.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 120, 20));

        jLabel25.setForeground(new java.awt.Color(0, 0, 0));
        jLabel25.setText("OT Eligible:");
        jPanel4.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 80, 20));
        jPanel4.add(txtAccountNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 90, 320, -1));
        jPanel4.add(txtIban, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 130, 320, -1));

        cmbOTEligible.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel4.add(cmbOTEligible, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 170, 320, -1));

        jButton9.setBackground(new java.awt.Color(255, 255, 255));
        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/SaveIcon.png"))); // NOI18N
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 200, 50, 60));

        jButton10.setText("Save");
        jPanel4.add(jButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(1490, 210, -1, -1));

        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Nationality:");
        jPanel4.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 10, 100, 20));

        jLabel26.setForeground(new java.awt.Color(0, 0, 0));
        jLabel26.setText("Civil Status:");
        jPanel4.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 50, 100, 20));

        jLabel27.setForeground(new java.awt.Color(0, 0, 0));
        jLabel27.setText("Date of Birth:");
        jPanel4.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 90, 100, 20));

        jLabel28.setForeground(new java.awt.Color(0, 0, 0));
        jLabel28.setText("Gender:");
        jPanel4.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 130, 100, 20));

        jLabel29.setForeground(new java.awt.Color(0, 0, 0));
        jLabel29.setText("Contact Details:");
        jPanel4.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 170, 110, 20));

        cmbNationality.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel4.add(cmbNationality, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 10, 260, -1));

        cmbCivilStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel4.add(cmbCivilStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 50, 260, -1));

        dateBirth.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                dateBirthMouseReleased(evt);
            }
        });
        jPanel4.add(dateBirth, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 90, 260, 30));

        cmbGender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel4.add(cmbGender, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 130, 260, -1));
        jPanel4.add(txtContactDetails, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 170, 260, -1));

        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Email:");
        jPanel4.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 10, 100, 20));

        jLabel30.setForeground(new java.awt.Color(0, 0, 0));
        jLabel30.setText("Emergency Contact:");
        jPanel4.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 60, 190, 20));

        jLabel31.setForeground(new java.awt.Color(0, 0, 0));
        jLabel31.setText("Name:");
        jPanel4.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 90, 100, 20));

        jLabel32.setForeground(new java.awt.Color(0, 0, 0));
        jLabel32.setText("Relationship:");
        jPanel4.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 130, 100, 20));

        jLabel33.setForeground(new java.awt.Color(0, 0, 0));
        jLabel33.setText("Contact Details:");
        jPanel4.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 170, 110, 20));
        jPanel4.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 50, 380, 10));
        jPanel4.add(txtEmergencyName, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 90, 250, -1));
        jPanel4.add(txtEmergencyRelation, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 130, 250, -1));
        jPanel4.add(txtEmergencyContact, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 170, 250, -1));
        jPanel4.add(txtEmailAddress, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 10, 250, -1));

        cmbBank.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel4.add(cmbBank, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, 320, -1));

        jTabbedPane1.addTab("Payment Type and Personal Info", jPanel4);

        jPanel3.setBackground(new java.awt.Color(214, 214, 194));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel21.setForeground(new java.awt.Color(0, 0, 0));
        jLabel21.setText("Allowance Type:");
        jPanel3.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 100, 20));

        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Amount:");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 100, 20));

        tableAllowance.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(tableAllowance);

        jPanel3.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 20, 920, 220));

        cmbAllowance.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel3.add(cmbAllowance, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 60, 220, -1));
        jPanel3.add(txtAmount, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 100, 220, -1));

        jButton6.setText("Edit");
        jPanel3.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 10, -1, -1));

        jButton7.setText("Add");
        jPanel3.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        jButton8.setBackground(new java.awt.Color(255, 255, 255));
        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/SaveIcon.png"))); // NOI18N
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 150, 60, 50));

        jTabbedPane1.addTab("Salary and Allowance", jPanel3);

        jPanel6.setBackground(new java.awt.Color(214, 214, 194));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Training Type:");
        jPanel6.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 100, 20));

        jLabel37.setForeground(new java.awt.Color(0, 0, 0));
        jLabel37.setText("Description:");
        jPanel6.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 100, 20));

        jLabel38.setForeground(new java.awt.Color(0, 0, 0));
        jLabel38.setText("Start:");
        jPanel6.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 100, 20));

        jLabel39.setForeground(new java.awt.Color(0, 0, 0));
        jLabel39.setText("Duration (Days):");
        jPanel6.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 100, 20));

        dateTrainingStart.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                dateTrainingStartMouseReleased(evt);
            }
        });
        jPanel6.add(dateTrainingStart, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 110, 350, 30));
        jPanel6.add(txtLocation, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, 350, -1));

        cmbTrainingType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel6.add(cmbTrainingType, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 10, 350, -1));

        tableTraining.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane5.setViewportView(tableTraining);

        jPanel6.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 10, 770, 230));

        jButton12.setBackground(new java.awt.Color(255, 255, 255));
        jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/SaveIcon.png"))); // NOI18N
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        jPanel6.add(jButton12, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 220, 60, 40));

        cmbTrainingDays.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel6.add(cmbTrainingDays, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 190, 350, -1));

        jLabel40.setForeground(new java.awt.Color(0, 0, 0));
        jLabel40.setText("End:");
        jPanel6.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 100, 20));

        dateTrainingEnd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                dateTrainingEndMouseReleased(evt);
            }
        });
        jPanel6.add(dateTrainingEnd, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 150, 350, 30));

        jLabel43.setForeground(new java.awt.Color(0, 0, 0));
        jLabel43.setText("Location:");
        jPanel6.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 100, 20));
        jPanel6.add(txtTrainingDescription, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 40, 350, -1));

        jTabbedPane1.addTab("Training", jPanel6);

        jPanel5.setBackground(new java.awt.Color(214, 214, 194));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tableDocument.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(tableDocument);

        jPanel5.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 10, 770, 230));

        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Document Type:");
        jPanel5.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 100, 20));

        jLabel34.setForeground(new java.awt.Color(0, 0, 0));
        jLabel34.setText("Document ID:");
        jPanel5.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 100, 20));

        jLabel35.setForeground(new java.awt.Color(0, 0, 0));
        jLabel35.setText("Issue Date:");
        jPanel5.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 100, 20));

        jLabel36.setForeground(new java.awt.Color(0, 0, 0));
        jLabel36.setText("Expiry Date:");
        jPanel5.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 100, 20));

        cmbDocType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel5.add(cmbDocType, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 10, 320, -1));
        jPanel5.add(txtDocumentId, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 50, 320, -1));

        dateDocIssue.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                dateDocIssueMouseReleased(evt);
            }
        });
        jPanel5.add(dateDocIssue, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 90, 320, 30));

        dateDocExpiry.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                dateDocExpiryMouseReleased(evt);
            }
        });
        jPanel5.add(dateDocExpiry, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 130, 320, 30));

        jButton11.setBackground(new java.awt.Color(255, 255, 255));
        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/SaveIcon.png"))); // NOI18N
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton11, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 170, 60, 50));

        jButton14.setBackground(new java.awt.Color(255, 255, 255));
        jButton14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/attachmentIconMain.png"))); // NOI18N
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton14, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 170, 60, 50));

        jTabbedPane1.addTab("Attached Documents", jPanel5);

        jPanel8.setBackground(new java.awt.Color(214, 214, 194));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable6.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane7.setViewportView(jTable6);

        jPanel8.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 1250, 230));

        jTabbedPane1.addTab("Promotion History", jPanel8);

        jPanel7.setBackground(new java.awt.Color(214, 214, 194));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable5.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane6.setViewportView(jTable5);

        jPanel7.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 1250, 230));

        jTabbedPane1.addTab("Leave History", jPanel7);

        jPanel9.setBackground(new java.awt.Color(214, 214, 194));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel44.setForeground(new java.awt.Color(0, 0, 0));
        jLabel44.setText("Status:");
        jPanel9.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 100, 20));

        cmbPromoteStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel9.add(cmbPromoteStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, 360, -1));

        jButton15.setBackground(new java.awt.Color(255, 255, 255));
        jButton15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/SearchIcon.png"))); // NOI18N
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });
        jPanel9.add(jButton15, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 10, 60, 30));

        tablePromotionList.setModel(new javax.swing.table.DefaultTableModel(
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
        tablePromotionList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablePromotionListMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(tablePromotionList);

        jPanel9.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 10, 640, 240));

        jPanel10.setBackground(new java.awt.Color(214, 214, 194));
        jPanel10.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(153, 153, 153)));
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblPromoteProposedDesignation.setForeground(new java.awt.Color(0, 0, 0));
        jPanel10.add(lblPromoteProposedDesignation, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 270, 20));

        jLabel46.setForeground(new java.awt.Color(0, 0, 0));
        jLabel46.setText("Emp ID:");
        jPanel10.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 80, 20));

        jLabel47.setForeground(new java.awt.Color(0, 0, 0));
        jLabel47.setText("Current:");
        jPanel10.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 80, 20));

        jLabel48.setForeground(new java.awt.Color(0, 0, 0));
        jLabel48.setText("Remarks:");
        jPanel10.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 60, 140, 20));

        jLabel49.setForeground(new java.awt.Color(0, 0, 0));
        jLabel49.setText("Proposed Designation:");
        jPanel10.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 140, 20));

        jLabel50.setForeground(new java.awt.Color(0, 0, 0));
        jLabel50.setText("Employee:");
        jPanel10.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 80, 20));

        lblPromoteName.setForeground(new java.awt.Color(0, 0, 0));
        jPanel10.add(lblPromoteName, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, 210, 20));

        lblPromoteID.setForeground(new java.awt.Color(0, 0, 0));
        jPanel10.add(lblPromoteID, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 40, 210, 20));

        lblPromoteCurrentDesignation.setForeground(new java.awt.Color(0, 0, 0));
        jPanel10.add(lblPromoteCurrentDesignation, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 70, 210, 20));

        cmbPromoteStatus1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel10.add(cmbPromoteStatus1, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 30, 250, -1));

        jLabel54.setForeground(new java.awt.Color(0, 0, 0));
        jLabel54.setText("Status:");
        jPanel10.add(jLabel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 10, 140, 20));

        txtAreaProposeRemarks.setColumns(20);
        txtAreaProposeRemarks.setRows(5);
        jScrollPane9.setViewportView(txtAreaProposeRemarks);

        jPanel10.add(jScrollPane9, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 90, 250, 60));

        jPanel9.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 590, 160));

        jButton16.setBackground(new java.awt.Color(255, 255, 255));
        jButton16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/EditIcon.png"))); // NOI18N
        jPanel9.add(jButton16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 70, 40));

        jButton17.setBackground(new java.awt.Color(255, 255, 255));
        jButton17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/SaveIcon.png"))); // NOI18N
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });
        jPanel9.add(jButton17, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 40, 70, 40));

        jTabbedPane1.addTab("Employee Promotion List", jPanel9);

        jPanel1.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 1270, 290));

        jButton1.setText("Add");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, -1, -1));
        jPanel1.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 370, 1020, 10));

        jButton2.setText("Edit");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, -1, -1));

        jButton3.setText("Save");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 10, -1, -1));

        jButton4.setText("Delete");
        jPanel1.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 10, -1, -1));

        lblEmployeeName.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lblEmployeeName.setForeground(new java.awt.Color(102, 102, 0));
        lblEmployeeName.setText("Employee Name");
        jPanel1.add(lblEmployeeName, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 10, 240, -1));

        jLabel41.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(102, 102, 0));
        jLabel41.setText("Name:");
        jPanel1.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 10, -1, -1));

        jLabel42.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(102, 102, 0));
        jLabel42.setText("Deparment:");
        jPanel1.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 10, -1, -1));

        lblDepartment.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lblDepartment.setForeground(new java.awt.Color(102, 102, 0));
        lblDepartment.setText("Department");
        jPanel1.add(lblDepartment, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 10, 240, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1292, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 628, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void dateJoinDateMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dateJoinDateMouseReleased

    }//GEN-LAST:event_dateJoinDateMouseReleased

    private void dateBirthMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dateBirthMouseReleased

    }//GEN-LAST:event_dateBirthMouseReleased

    private void dateDocIssueMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dateDocIssueMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_dateDocIssueMouseReleased

    private void dateDocExpiryMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dateDocExpiryMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_dateDocExpiryMouseReleased

    private void dateTrainingStartMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dateTrainingStartMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_dateTrainingStartMouseReleased
    private void FillTableEmployee(){
        try{
            DbConn.pstmt =DbConn.conn.prepareStatement("Select * from tblemployeeprofile order by em_seqno");
            DbConn.rs = DbConn.pstmt.executeQuery();
            tableEmployee.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
            DbConn.pstmt.close();
            tableEmployee.removeColumn(tableEmployee.getColumnModel().getColumn(10));
            tableEmployee.removeColumn(tableEmployee.getColumnModel().getColumn(9));
            SetHeaderTableEmployee();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Table Employee Profile");
        }
    }
    private void SetHeaderTableEmployee(){
           tableEmployee.getColumnModel().getColumn(0).setHeaderValue("ID");
           tableEmployee.getColumnModel().getColumn(1).setHeaderValue("NAME");
           tableEmployee.getColumnModel().getColumn(2).setHeaderValue("DEPARTMENT");
           tableEmployee.getColumnModel().getColumn(3).setHeaderValue("DESIGNATION");
           tableEmployee.getColumnModel().getColumn(4).setHeaderValue("TYPE");
           tableEmployee.getColumnModel().getColumn(5).setHeaderValue("JOIN DATE");
           tableEmployee.getColumnModel().getColumn(6).setHeaderValue("RP TYPE");
           tableEmployee.getColumnModel().getColumn(7).setHeaderValue("GRADE");
           tableEmployee.getColumnModel().getColumn(8).setHeaderValue("ADDRESS");
    }
    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
            try{
                if(AddBasicDetails == true && EditBasicDetails==false){
                    DbConn.SQLQuery = "INSERT into tblemployeeprofile (em_name,em_department,em_designation,"
                            + "em_emptype,em_joindate,em_rptype,em_grade,em_address,em_addby,em_date,em_nationalid,"
                            + "em_approver,em_approvingpersonnel) "
                            + "values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
                    DbConn.pstmt.setString(1,txtEmployeeName.getText());
                    DbConn.pstmt.setString(2,cmbDepartment.getSelectedItem().toString());
                    DbConn.pstmt.setString(3,cmbDesignation.getSelectedItem().toString());
                    DbConn.pstmt.setString(4,cmbEmployeeType.getSelectedItem().toString());
                    Date JoinDate = dateJoinDate.getDate();
                    DbConn.pstmt.setString(5,String.valueOf(DbConn.sdfDate.format(JoinDate)));
                    DbConn.pstmt.setString(6,cmbRpType.getSelectedItem().toString());
                    DbConn.pstmt.setString(7,cmbEmpGrade.getSelectedItem().toString());
                    DbConn.pstmt.setString(8,txtAreaAddress.getText());
                    DbConn.pstmt.setString(9,"Admin");
                    DbConn.pstmt.setString(10,String.valueOf(DbConn.sdfDate.format(new Date())));
                    DbConn.pstmt.setString(11,txtNationalID.getText());
                    String GetApprover="No";
                    if (chkApprover.isSelected()){
                        GetApprover="Yes";
                    }
                    DbConn.pstmt.setString(12,GetApprover);
                    DbConn.pstmt.setString(13,cmbApprovingPersonnel.getSelectedItem().toString());
                    DbConn.pstmt.execute();
                    DbConn.pstmt.close();
                    FillTableEmployee();
                    JOptionPane.showMessageDialog(this, "Saved Successfully");
                    lblEmployeeName.setText(txtEmployeeName.getText());
                    lblDepartment.setText(cmbDepartment.getSelectedItem().toString());
                }else if(AddBasicDetails==false && EditBasicDetails == true){
                    DbConn.SQLQuery = "update tblemployeeprofile set em_name=?,em_department=?,em_designation=?,"
                            + "em_emptype=?,em_joindate=?,em_rptype=?,em_grade=?,em_address=?,em_addby=?,em_date=?,em_nationalid=?,"
                            + "em_approver=?,em_approvingpersonnel=? where em_seqno=?";
                    DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
                    DbConn.pstmt.setString(1,txtEmployeeName.getText());
                    DbConn.pstmt.setString(2,cmbDepartment.getSelectedItem().toString());
                    DbConn.pstmt.setString(3,cmbDesignation.getSelectedItem().toString());
                    DbConn.pstmt.setString(4,cmbEmployeeType.getSelectedItem().toString());
                    Date JoinDate = dateJoinDate.getDate();
                    DbConn.pstmt.setString(5,String.valueOf(DbConn.sdfDate.format(JoinDate)));
                    DbConn.pstmt.setString(6,cmbRpType.getSelectedItem().toString());
                    DbConn.pstmt.setString(7,cmbEmpGrade.getSelectedItem().toString());
                    DbConn.pstmt.setString(8,txtAreaAddress.getText());
                    DbConn.pstmt.setString(9,"Admin");
                    DbConn.pstmt.setString(10,String.valueOf(DbConn.sdfDate.format(new Date())));
                    DbConn.pstmt.setString(11,txtNationalID.getText());
                    String GetApprover="No";
                    if (chkApprover.isSelected()){
                        GetApprover="Yes";
                    }
                    DbConn.pstmt.setString(12,GetApprover);
                    DbConn.pstmt.setString(13,cmbApprovingPersonnel.getSelectedItem().toString());
                    DbConn.pstmt.setString(14,lblEmpID.getText());
                    DbConn.pstmt.executeUpdate();
                    DbConn.pstmt.close();
                    FillTableEmployee();
                    JOptionPane.showMessageDialog(this, "Updated Successfully");
                    lblEmployeeName.setText(txtEmployeeName.getText());
                    lblDepartment.setText(cmbDepartment.getSelectedItem().toString());
                }
            }catch(SQLException e){
                JOptionPane.showMessageDialog(this, "Basic Details Query");
            }
        
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        AddBasicDetails=true;
        EditBasicDetails=false;
    }//GEN-LAST:event_jButton1ActionPerformed
    private void FillTableAllowance(){
        DbConn.GetEmpIdForProfile(lblEmployeeName.getText());
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("Select al_seqno,al_type,al_amount from tblallowance where al_empname =? and al_empid=?");
            DbConn.pstmt.setString(1, lblEmployeeName.getText());
            DbConn.pstmt.setString(2, String.valueOf(DbConn.GetEmpIDToSave));
            DbConn.rs = DbConn.pstmt.executeQuery();
            tableAllowance.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
            DbConn.pstmt.close();
            SetHeaderAllowanceTable();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Allowance Table");
        }
    }
    private void SetHeaderAllowanceTable(){
        tableAllowance.getColumnModel().getColumn(0).setHeaderValue("ID");
        tableAllowance.getColumnModel().getColumn(1).setHeaderValue("TYPE");
        tableAllowance.getColumnModel().getColumn(2).setHeaderValue("AMOUNT");
    }
    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        DbConn.GetEmpIdForProfile(lblEmployeeName.getText());
        boolean ExistingRecords = false;
        try{
            DbConn.SQLQuery = "select * from tblallowance where al_type=? and al_empname=? and al_empid=?";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.pstmt.setString(1, cmbAllowance.getSelectedItem().toString());
            DbConn.pstmt.setString(2, lblEmployeeName.getText());
            DbConn.pstmt.setString(3, lblEmpID.getText());
            DbConn.rs = DbConn.pstmt.executeQuery();
            if (DbConn.rs.next()){
                ExistingRecords = true;
            }else{
                ExistingRecords=false;
            }
            DbConn.pstmt.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        try{
            if (ExistingRecords==false){
            DbConn.pstmt = DbConn.conn.prepareStatement("Insert into tblallowance (al_type,al_amount,al_empname,al_empid,al_addby,al_date) "
                    + "values (?,?,?,?,?,?)");
            DbConn.pstmt.setString(1, cmbAllowance.getSelectedItem().toString());
            Double GetAmount = Double.valueOf(txtAmount.getText());
            DbConn.pstmt.setDouble(2, Double.valueOf(DbConn.df.format(GetAmount)));
            DbConn.pstmt.setString(3, lblEmployeeName.getText());
            DbConn.pstmt.setString(4, String.valueOf(DbConn.GetEmpIDToSave));
            DbConn.pstmt.setString(5, "Admin");
            DbConn.pstmt.setString(6, DbConn.sdfDate.format(new Date()));
            DbConn.pstmt.execute();
            DbConn.pstmt.close();
            JOptionPane.showMessageDialog(this, "Allowance Saved Successfully");
            FillTableAllowance();
            }else if (ExistingRecords ==true){
            DbConn.pstmt = DbConn.conn.prepareStatement("update tblallowance set al_amount=?,al_addby=?,al_date=? where"
                    + " al_empid=? and al_empname=? and al_type=?");
            Double GetAmount = Double.valueOf(txtAmount.getText());
            DbConn.pstmt.setDouble(1, Double.valueOf(DbConn.df.format(GetAmount)));
            DbConn.pstmt.setString(2, "Admin");
            DbConn.pstmt.setString(3, DbConn.sdfDate.format(new Date()));
            DbConn.pstmt.setString(4, lblEmpID.getText());
            DbConn.pstmt.setString(5, lblEmployeeName.getText());
            DbConn.pstmt.setString(6, cmbAllowance.getSelectedItem().toString());
            DbConn.pstmt.executeUpdate();
            DbConn.pstmt.close();
            JOptionPane.showMessageDialog(this, "Allowance Updated Successfully");
            FillTableAllowance();
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void tableEmployeeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableEmployeeMouseClicked
        int row = tableEmployee.getSelectedRow();
        int ba = tableEmployee.convertRowIndexToModel(row); 
        try{
            DbConn.SQLQuery = "select * from tblemployeeprofile where em_name=? and em_seqno=?";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.pstmt.setString(1, tableEmployee.getModel().getValueAt(ba,1).toString());
            DbConn.pstmt.setInt(2, Integer.valueOf(tableEmployee.getModel().getValueAt(ba,0).toString()));
            DbConn.rs =  DbConn.pstmt.executeQuery();
            if (DbConn.rs.next()){
                lblEmpID.setText(String.valueOf(DbConn.rs.getInt("em_seqno")));
                txtEmployeeName.setText(DbConn.rs.getString("em_name"));
                cmbDepartment.setSelectedItem(DbConn.rs.getString("em_department"));
                cmbDesignation.setSelectedItem(DbConn.rs.getString("em_designation"));
                cmbEmployeeType.setSelectedItem(DbConn.rs.getString("em_emptype"));
                cmbRpType.setSelectedItem(DbConn.rs.getString("em_rptype"));
                cmbEmpGrade.setSelectedItem(DbConn.rs.getString("em_grade"));
                txtAreaAddress.setText(DbConn.rs.getString("em_address"));
                dateJoinDate.setDate(DbConn.sdfDate.parse(String.valueOf(DbConn.rs.getString("em_joindate"))));
                cmbApprovingPersonnel.setSelectedItem(DbConn.rs.getString("em_approvingpersonnel"));
            }
            DbConn.pstmt.close();
        }catch(SQLException | ParseException e){
            JOptionPane.showMessageDialog(this, "Fill Basic Details");
        }
        lblEmployeeName.setText(tableEmployee.getModel().getValueAt(ba, 1).toString());
        lblDepartment.setText(tableEmployee.getModel().getValueAt(ba, 2).toString());
        FillTableAllowance();
        FillTrainingDocumentTable();
        FillTrainingTable();
    }//GEN-LAST:event_tableEmployeeMouseClicked
   
    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        DbConn.GetEmpIdForProfile(lblEmployeeName.getText());
        boolean ExistingRecord = false;
        //check if record exists
         try{
            DbConn.pstmt = DbConn.conn.prepareStatement("Select * from tblpaymenttype where pt_empid=? and pt_empname=?");
            DbConn.pstmt.setString(1, lblEmpID.getText());
            DbConn.pstmt.setString(2, lblEmployeeName.getText());
            DbConn.rs = DbConn.pstmt.executeQuery();
            if (DbConn.rs.next()){
                ExistingRecord=true;
            }else{
                ExistingRecord=false;
            }
            DbConn.pstmt.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Payment and Personal Info Existing Records");
        }
        
        try{
            if (ExistingRecord==false){
                DbConn.SQLQuery = "insert into tblpaymenttype (pt_bank,pt_paymentmode,pt_acctnumber,pt_iban,pt_oteligible,pt_empid,pt_empname,pt_addby,pt_date) "
                        + "values (?,?,?,?,?,?,?,?,?)";
                DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
                DbConn.pstmt.setString(1, cmbBank.getSelectedItem().toString());
                DbConn.pstmt.setString(2, cmbPaymentMode.getSelectedItem().toString());
                DbConn.pstmt.setString(3, txtAccountNumber.getText());
                DbConn.pstmt.setString(4, txtIban.getText());
                DbConn.pstmt.setString(5, cmbOTEligible.getSelectedItem().toString());
                DbConn.pstmt.setString(6, String.valueOf(DbConn.GetEmpIDToSave));
                DbConn.pstmt.setString(7, lblEmployeeName.getText());
                DbConn.pstmt.setString(8, "Admin");
                DbConn.pstmt.setString(9, String.valueOf(DbConn.sdfDate.format(new Date())));
                DbConn.pstmt.execute();
                DbConn.pstmt.close();
                //Personal Info
                DbConn.SQLQuery = "insert into tblpersonalinfo (pi_nationality,pi_civilstatus,pi_dateofbirth,pi_gender,pi_contactdetails,"
                        + "pi_email,pi_emergencyname,pi_emergencyrelation,pi_emergencycontact,pi_empname,pi_empid,pi_addby,pi_date) "
                        + "values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
                DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
                DbConn.pstmt.setString(1, cmbNationality.getSelectedItem().toString());
                DbConn.pstmt.setString(2, cmbCivilStatus.getSelectedItem().toString());
                Date GetDob = dateBirth.getDate();
                DbConn.pstmt.setString(3, String.valueOf(DbConn.sdfDate.format(GetDob)));
                DbConn.pstmt.setString(4, txtContactDetails.getText());
                DbConn.pstmt.setString(5, txtEmailAddress.getText());
                DbConn.pstmt.setString(6, String.valueOf(DbConn.sdfDate.format(GetDob)));
                DbConn.pstmt.setString(7, txtEmergencyName.getText());
                DbConn.pstmt.setString(8, txtEmergencyRelation.getText());
                DbConn.pstmt.setString(9, txtEmergencyContact.getText());
                DbConn.pstmt.setString(10, lblEmployeeName.getText());
                DbConn.pstmt.setString(11, String.valueOf(DbConn.GetEmpIDToSave));
                DbConn.pstmt.setString(12, "Admin");
                DbConn.pstmt.setString(13, String.valueOf(DbConn.sdfDate.format(new Date())));
                DbConn.pstmt.execute();
                DbConn.pstmt.close();
                JOptionPane.showMessageDialog(this, "Successfully Saved");
            }else if (ExistingRecord==true){
                DbConn.SQLQuery = "update tblpaymenttype set pt_bank=?,pt_paymentmode=?,pt_acctnumber=?,pt_iban=?,"
                        + "pt_oteligible=?, pt_addby=?,pt_date=? "
                        + "where pt_empid=? and pt_empname=?";
                DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
                DbConn.pstmt.setString(1, cmbBank.getSelectedItem().toString());
                DbConn.pstmt.setString(2, cmbPaymentMode.getSelectedItem().toString());
                DbConn.pstmt.setString(3, txtAccountNumber.getText());
                DbConn.pstmt.setString(4, txtIban.getText());
                DbConn.pstmt.setString(5, cmbOTEligible.getSelectedItem().toString());
                DbConn.pstmt.setString(6, "Admin");
                DbConn.pstmt.setString(7, String.valueOf(DbConn.sdfDate.format(new Date())));
                DbConn.pstmt.setString(8, String.valueOf(DbConn.GetEmpIDToSave));
                DbConn.pstmt.setString(9, lblEmployeeName.getText());
                DbConn.pstmt.executeUpdate();
                DbConn.pstmt.close();
                //Personal Info
                DbConn.SQLQuery = "update tblpersonalinfo set pi_nationality=?,pi_civilstatus=?,pi_dateofbirth=?,pi_gender=?,"
                        + "pi_contactdetails=?,pi_email=?,pi_emergencyname=?,pi_emergencyrelation=?,pi_emergencycontact=?,"
                        + "pi_addby=?,pi_date=? "
                        + "where pi_empname=? and pi_empid=?";
                DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
                DbConn.pstmt.setString(1, cmbNationality.getSelectedItem().toString());
                DbConn.pstmt.setString(2, cmbCivilStatus.getSelectedItem().toString());
                Date GetDob = dateBirth.getDate();
                DbConn.pstmt.setString(3, String.valueOf(DbConn.sdfDate.format(GetDob)));
                DbConn.pstmt.setString(4, txtContactDetails.getText());
                DbConn.pstmt.setString(5, txtEmailAddress.getText());
                DbConn.pstmt.setString(6, String.valueOf(DbConn.sdfDate.format(GetDob)));
                DbConn.pstmt.setString(7, txtEmergencyName.getText());
                DbConn.pstmt.setString(8, txtEmergencyRelation.getText());
                DbConn.pstmt.setString(9, txtEmergencyContact.getText());
                DbConn.pstmt.setString(10, "Admin");
                DbConn.pstmt.setString(11, String.valueOf(DbConn.sdfDate.format(new Date())));
                DbConn.pstmt.setString(12, lblEmployeeName.getText());
                DbConn.pstmt.setString(13, String.valueOf(DbConn.GetEmpIDToSave));
                DbConn.pstmt.executeUpdate();
                DbConn.pstmt.close();
                JOptionPane.showMessageDialog(this, "Successfully Updated");
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Payment Type");
        }
    }//GEN-LAST:event_jButton9ActionPerformed
    private void FillTrainingTable(){
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("Select * from tbltraining where tr_empname =? and tr_empid=?");
            DbConn.pstmt.setString(1, lblEmployeeName.getText());
            DbConn.pstmt.setString(2, String.valueOf(DbConn.GetEmpIDToSave));
            DbConn.rs = DbConn.pstmt.executeQuery();
            tableTraining.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
            SetTrainingHeaderTable();
            DbConn.pstmt.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Training Table");
        }
    }
    private void SetTrainingHeaderTable(){
        tableTraining.getColumnModel().getColumn(0).setHeaderValue("ID");
        tableTraining.getColumnModel().getColumn(1).setHeaderValue("TYPE");
        tableTraining.getColumnModel().getColumn(2).setHeaderValue("LOCATION");
        tableTraining.getColumnModel().getColumn(3).setHeaderValue("START DATE");
        tableTraining.getColumnModel().getColumn(4).setHeaderValue("END DATE");
        tableTraining.getColumnModel().getColumn(5).setHeaderValue("DURATION");
        tableTraining.getColumnModel().getColumn(6).setHeaderValue("");
        tableTraining.getColumnModel().getColumn(7).setHeaderValue("");
        tableTraining.getColumnModel().getColumn(8).setHeaderValue("");
        tableTraining.getColumnModel().getColumn(9).setHeaderValue("");
        tableTraining.getColumnModel().getColumn(10).setHeaderValue("DESCRIPTION");
        tableTraining.removeColumn(tableTraining.getColumnModel().getColumn(9));
        tableTraining.removeColumn(tableTraining.getColumnModel().getColumn(8));
        tableTraining.removeColumn(tableTraining.getColumnModel().getColumn(7));
        tableTraining.removeColumn(tableTraining.getColumnModel().getColumn(6));
    }
    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        DbConn.GetEmpIdForProfile(lblEmployeeName.getText());
        try{
            DbConn.SQLQuery= "Insert into tbltraining (tr_traintype,tr_trainlocation,tr_traindatestart,tr_traindateend,"
                    + "tr_trainduration,tr_empname,tr_empid,tr_addby,tr_date,tr_description) "
                    + "values (?,?,?,?,?,?,?,?,?,?)";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.pstmt.setString(1, cmbTrainingType.getSelectedItem().toString());
            DbConn.pstmt.setString(2, txtLocation.getText());
            Date StartDate = dateTrainingStart.getDate();
            Date EndDate = dateTrainingEnd.getDate();
            DbConn.pstmt.setString(3, String.valueOf(DbConn.sdfDate.format(StartDate)));
            DbConn.pstmt.setString(4, String.valueOf(DbConn.sdfDate.format(EndDate)));
            DbConn.pstmt.setString(5, cmbTrainingDays.getSelectedItem().toString());
            DbConn.pstmt.setString(6, lblEmployeeName.getText());
            DbConn.pstmt.setString(7, String.valueOf(DbConn.GetEmpIDToSave));
            DbConn.pstmt.setString(8, "Admin");
            DbConn.pstmt.setString(9, DbConn.sdfDate.format(new Date()));
            DbConn.pstmt.setString(10, txtTrainingDescription.getText());
            DbConn.pstmt.execute();
            DbConn.pstmt.close();
            JOptionPane.showMessageDialog(this, "Saved Successfully");
            txtTrainingDescription.setText("");
            FillTrainingTable();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_jButton12ActionPerformed

    private void dateTrainingEndMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dateTrainingEndMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_dateTrainingEndMouseReleased
private void FillTrainingDocumentTable(){
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("Select * from tblattachment where at_empname =? and at_empid=?");
            DbConn.pstmt.setString(1, lblEmployeeName.getText());
            DbConn.pstmt.setString(2, String.valueOf(DbConn.GetEmpIDToSave));
            DbConn.rs = DbConn.pstmt.executeQuery();
            tableDocument.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
            DbConn.pstmt.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Document Table");
        }
    }
    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        DbConn.GetEmpIdForProfile(lblEmployeeName.getText());
        try{
            DbConn.SQLQuery= "Insert into tblattachment (at_doctype,at_docid,at_docdate,at_expirydate,at_path,at_empid,at_empname,at_addby,at_date) values (?,?,?,?,?,?,?,?,?)";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.pstmt.setString(1, cmbDocType.getSelectedItem().toString());
            DbConn.pstmt.setString(2, txtDocumentId.getText());
            Date DocDateIssue = dateDocIssue.getDate();
            Date DocDateExpiry = dateDocExpiry.getDate();
            DbConn.pstmt.setString(3, String.valueOf(DbConn.sdfDate.format(DocDateIssue)));
            DbConn.pstmt.setString(4, String.valueOf(DbConn.sdfDate.format(DocDateExpiry)));
            DbConn.pstmt.setString(5, getImagePath);
            DbConn.pstmt.setString(6, String.valueOf(DbConn.GetEmpIDToSave));
            DbConn.pstmt.setString(7, lblEmployeeName.getText());
            DbConn.pstmt.setString(8, "Admin");
            DbConn.pstmt.setString(9, DbConn.sdfDate.format(new Date()));
            DbConn.pstmt.execute();
            DbConn.pstmt.close();
            JOptionPane.showMessageDialog(this, "Saved Successfully");
            FillTrainingDocumentTable();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        
        FileFilter ft = new FileNameExtensionFilter("images .pdf,.jpg,.png", "jpg","png","pdf");
        File outputFile  = new File("src\\attachments\\attachment" + "-" + txtDocumentId.getText());
        db.addChoosableFileFilter(ft);
        int returnVal = db.showOpenDialog(this);
        if(returnVal == javax.swing.JFileChooser.APPROVE_OPTION){
//        try{
//           BufferedImage input = ImageIO.read(db.getSelectedFile());
//           ImageIO.write(input, "PDF", outputFile);
//        }catch(Exception e){
//            JOptionPane.showMessageDialog(this, e.getMessage());
//        }
//            getImagePath = outputFile.toString();
            getImagePath = db.getSelectedFile().getPath();
        }
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        AddBasicDetails=false;
        EditBasicDetails=true;
    }//GEN-LAST:event_jButton2ActionPerformed

    private void tableEmployeeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableEmployeeMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_tableEmployeeMouseEntered

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        try{
            DbConn.SQLQuery ="select pp_empname,pp_currentdesignation,pp_proposeddesignation,"
                    + "pp_proposedby,pp_proposeddate,pp_status from tblproposalpromote where pp_status=?  order by pp_proposeddate";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.pstmt.setString(1, cmbPromoteStatus.getSelectedItem().toString());
            DbConn.rs = DbConn.pstmt.executeQuery();
            tablePromotionList.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
            DbConn.pstmt.close();
            SetHeaderPromoteList();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_jButton15ActionPerformed

    private void tablePromotionListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablePromotionListMouseClicked
        int row = tablePromotionList.getSelectedRow();
        int ba = tablePromotionList.convertRowIndexToModel(row);
        try{
            DbConn.SQLQuery ="select * from tblproposalpromote where pp_empname=? and pp_currentdesignation=?";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.pstmt.setString(1, tablePromotionList.getModel().getValueAt(ba, 0).toString());
            DbConn.pstmt.setString(2, tablePromotionList.getModel().getValueAt(ba, 1).toString());
            DbConn.rs = DbConn.pstmt.executeQuery();
            if (DbConn.rs.next()){
                lblPromoteName.setText(DbConn.rs.getString("pp_empname"));
                lblPromoteID.setText(DbConn.rs.getString("pp_empid"));
                lblPromoteProposedDesignation.setText(DbConn.rs.getString("pp_proposeddesignation"));
                lblPromoteCurrentDesignation.setText(DbConn.rs.getString("pp_currentdesignation"));
                cmbPromoteStatus1.setSelectedItem(DbConn.rs.getString("pp_status"));
                txtAreaProposeRemarks.setText(DbConn.rs.getString("pp_remarks"));
            }
            DbConn.pstmt.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Retrieve Promotion List");
        }
    }//GEN-LAST:event_tablePromotionListMouseClicked

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        int itemSelect = JOptionPane.showConfirmDialog(this, "Are you sure you want to update the record?","Update Record",JOptionPane.YES_NO_OPTION);
        if (itemSelect == JOptionPane.YES_OPTION){
            try{
                DbConn.SQLQuery = "update tblproposalpromote set pp_status=?,pp_remarks=? where pp_empname=? and pp_empid=?";
                DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
                DbConn.pstmt.setString(1, cmbPromoteStatus1.getSelectedItem().toString());
                DbConn.pstmt.setString(2, txtAreaProposeRemarks.getText());
                DbConn.pstmt.setString(3, lblPromoteName.getText());
                DbConn.pstmt.setString(4, lblPromoteID.getText());
                DbConn.pstmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Status Updated");
                FillPromotionList();
                lblPromoteID.setText("");
                lblPromoteCurrentDesignation.setText("");
                lblPromoteName.setText("");
                lblPromoteProposedDesignation.setText("");
                cmbPromoteStatus.setSelectedIndex(-1);
                cmbPromoteStatus1.setSelectedIndex(-1);
            }catch(SQLException e){
                JOptionPane.showMessageDialog(this, "Promote Status Update");
            }
        }
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(frmEmployeeProfile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmEmployeeProfile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmEmployeeProfile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmEmployeeProfile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmEmployeeProfile().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox chkApprover;
    private javax.swing.JComboBox<String> cmbAllowance;
    private javax.swing.JComboBox<String> cmbApprovingPersonnel;
    private javax.swing.JComboBox<String> cmbBank;
    private javax.swing.JComboBox<String> cmbCivilStatus;
    private javax.swing.JComboBox<String> cmbDepartment;
    private javax.swing.JComboBox<String> cmbDesignation;
    private javax.swing.JComboBox<String> cmbDocType;
    private javax.swing.JComboBox<String> cmbEmpGrade;
    private javax.swing.JComboBox<String> cmbEmployeeType;
    private javax.swing.JComboBox<String> cmbGender;
    private javax.swing.JComboBox<String> cmbNationality;
    private javax.swing.JComboBox<String> cmbOTEligible;
    private javax.swing.JComboBox<String> cmbPaymentMode;
    private javax.swing.JComboBox<String> cmbPromoteStatus;
    private javax.swing.JComboBox<String> cmbPromoteStatus1;
    private javax.swing.JComboBox<String> cmbRpType;
    private javax.swing.JComboBox<String> cmbTrainingDays;
    private javax.swing.JComboBox<String> cmbTrainingType;
    private com.toedter.calendar.JDateChooser dateBirth;
    private com.toedter.calendar.JDateChooser dateDocExpiry;
    private com.toedter.calendar.JDateChooser dateDocIssue;
    private com.toedter.calendar.JDateChooser dateJoinDate;
    private com.toedter.calendar.JDateChooser dateTrainingEnd;
    private com.toedter.calendar.JDateChooser dateTrainingStart;
    private javax.swing.JFileChooser db;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable5;
    private javax.swing.JTable jTable6;
    private javax.swing.JLabel lblDepartment;
    private javax.swing.JLabel lblEmpID;
    private javax.swing.JLabel lblEmployeeName;
    private javax.swing.JLabel lblPromoteCurrentDesignation;
    private javax.swing.JLabel lblPromoteID;
    private javax.swing.JLabel lblPromoteName;
    private javax.swing.JLabel lblPromoteProposedDesignation;
    private javax.swing.JTable tableAllowance;
    private javax.swing.JTable tableDocument;
    private javax.swing.JTable tableEmployee;
    private javax.swing.JTable tablePromotionList;
    private javax.swing.JTable tableTraining;
    private javax.swing.JTextField txtAccountNumber;
    private javax.swing.JTextField txtAmount;
    private javax.swing.JTextArea txtAreaAddress;
    private javax.swing.JTextArea txtAreaProposeRemarks;
    private javax.swing.JTextField txtContactDetails;
    private javax.swing.JTextField txtDocumentId;
    private javax.swing.JTextField txtEmailAddress;
    private javax.swing.JTextField txtEmergencyContact;
    private javax.swing.JTextField txtEmergencyName;
    private javax.swing.JTextField txtEmergencyRelation;
    private javax.swing.JTextField txtEmployeeName;
    private javax.swing.JTextField txtIban;
    private javax.swing.JTextField txtLocation;
    private javax.swing.JTextField txtNationalID;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtTrainingDescription;
    // End of variables declaration//GEN-END:variables
}
