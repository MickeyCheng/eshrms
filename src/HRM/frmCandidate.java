
package HRM;

import java.awt.Point;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;


public class frmCandidate extends javax.swing.JFrame {
    DbConnection DbConn = new DbConnection();
    String getImagePath;
    boolean add,edit,attachedCV;
    String GetDesignation, GetDepartment;
    int GetCandidateOnBoardNumber;
    public frmCandidate() {
        initComponents();
        DbConn.DoConnect();
        FillAllMethod();
        setLocationRelativeTo(null);
        DisableAllChoice();
        DisableCheckBoxes();
        DisplayCount();
        dateStart.setDate(DbConn.DateToday);
        setDefaultCloseOperation(frmCandidate.DISPOSE_ON_CLOSE);
        tableCandidate.setDefaultEditor(Object.class, null);
        tableCandidate1.setDefaultEditor(Object.class, null);
        tableOffer.setDefaultEditor(Object.class, null);
        cmbSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {ListenSearchCombo();}
        });
        tableCandidate1.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
        JTable table =(JTable) mouseEvent.getSource();
        Point point = mouseEvent.getPoint();
        int row = table.rowAtPoint(point);
        if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
            ViewCVMethod();
        }
    }
    });
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {ListenSearch();}

            @Override
            public void removeUpdate(DocumentEvent e) {ListenSearch();}

            @Override
            public void changedUpdate(DocumentEvent e) {ListenSearch();}
        });
        txtSearchOnBoard.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {ListenSearchOnBoard();}

            @Override
            public void removeUpdate(DocumentEvent e) {ListenSearchOnBoard();}

            @Override
            public void changedUpdate(DocumentEvent e) {ListenSearchOnBoard();}
        });
        txtName1.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {ListenSearchOffer();}

            @Override
            public void removeUpdate(DocumentEvent e) {ListenSearchOffer();}

            @Override
            public void changedUpdate(DocumentEvent e) {ListenSearchOffer();}
        });
    }
    private void FillAllMethod(){
        FillAge();
        FillNationality();
        FillGender();
        FillSource();
        FillPosition();
        FillEducation();
        FillField();
        FillStatus();
        FillTable();
        FillSearch();
        FillDepartment();
        FillGeneratedBy();
        FillTableOffer();
        FillTableOnBoard();
        FillTableCandidateSearch();
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
    private void ListenSearchOffer(){
        try{
             DbConn.SQLQuery = "Select ca_seqno,ca_name,ca_age,ca_nationality,ca_field,"
                    + "ca_school,ca_position from tblcandidate where ca_name like ?";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.pstmt.setString(1, "%"+ txtName1.getText()+"%");
            DbConn.rs = DbConn.pstmt.executeQuery();
            tableOffer.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
            DbConn.pstmt.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Live Search");
        }
    }
    private void FillTableCandidateSearch(){
        try{
            DbConn.SQLQuery = "Select ca_seqno,ca_name,ca_age,ca_nationality,ca_field,"
                    + "ca_school,ca_position from tblcandidate order by ca_seqno";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.rs = DbConn.pstmt.executeQuery();
            tableCandidate1.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
            DbConn.pstmt.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "FillTable");
        }
    }
    private void FillTableOnBoard(){
        try{
            DbConn.SQLQuery = "Select ca_seqno,ca_name,ca_age,ca_nationality,ca_field,"
                    + "ca_school,ca_position from tblcandidate order by ca_seqno";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.rs = DbConn.pstmt.executeQuery();
            tableCandidate2.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
            DbConn.pstmt.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "FillTable");
        }
    }
    private void FillTableOffer(){
        try{
            DbConn.SQLQuery = "Select ca_seqno,ca_name,ca_age,ca_nationality,ca_field,"
                    + "ca_school,ca_position from tblcandidate order by ca_seqno";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.rs = DbConn.pstmt.executeQuery();
            tableOffer.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
            DbConn.pstmt.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "FillTable");
        }
    }
    private void ListenSearchOnBoard(){
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("Select ca_seqno,ca_name,ca_age,ca_nationality,ca_field,ca_school,ca_position from tblcandidate "
                    + "where ca_name like ? order by ca_seqno");
            DbConn.pstmt.setString(1, "%"+ txtSearchOnBoard.getText() +"%");
            DbConn.rs = DbConn.pstmt.executeQuery();
            tableCandidate2.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
            DbConn.pstmt.close();
            SetHeaderTable();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Name Search");
        }
    }
    private void ListenSearch(){
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("Select ca_seqno,ca_name,ca_age,ca_nationality,ca_field,ca_school,ca_position from tblcandidate "
                    + "where ca_name like ? order by ca_seqno");
            DbConn.pstmt.setString(1, "%"+ txtSearch.getText() +"%");
            DbConn.rs = DbConn.pstmt.executeQuery();
            tableCandidate1.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
            DbConn.pstmt.close();
            SetHeaderTable();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Name Search");
        }
    }
    private void ViewCVMethod(){
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("select ca_cv from tblcandidate where ca_seqno=? and ca_cv is not null");
            int row = tableCandidate1.getSelectedRow();
            int ba = tableCandidate1.convertRowIndexToModel(row);
            DbConn.pstmt.setString(1, tableCandidate1.getModel().getValueAt(ba, 0).toString());
            DbConn.rs = DbConn.pstmt.executeQuery();
            if (DbConn.rs.next()){
                Runtime.getRuntime().exec("rundll32 url.dll, FileProtocolHandler " + DbConn.rs.getString(1));
            }else{
                JOptionPane.showMessageDialog(this, "No CV Attached");
            }
            DbConn.pstmt.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "No CV Attached");
        }
    }
    private void ListenSearchCombo(){
        if (cmbSearch.getSelectedIndex() == -1){
            return;
        }
        if (cmbSearch.getSelectedItem().toString().equals("All")){
            EnableAllChoice();
        }else if(cmbSearch.getSelectedItem().toString().equals("Age")){
            DisableAllChoice();
            cmbAgeFrom.setEnabled(true);
            cmbAgeTo.setEnabled(true);
        }else if(cmbSearch.getSelectedItem().toString().equals("Gender")){
            DisableAllChoice();
            radBoth.setEnabled(true);
            radFemale.setEnabled(true);
            radMale.setEnabled(rootPaneCheckingEnabled);
        }else if(cmbSearch.getSelectedItem().toString().equals("Position Applied")){
            DisableAllChoice();
            cmbPosition1.setEnabled(true);
        }else if(cmbSearch.getSelectedItem().toString().equals("Status")){
            DisableAllChoice();
            cmbStatus1.setEnabled(true);
        }else if(cmbSearch.getSelectedItem().toString().equals("CV Source")){
            DisableAllChoice();
            cmbSource1.setEnabled(true);
        }else if(cmbSearch.getSelectedItem().toString().equals("Field")){
            DisableAllChoice();
            cmbField1.setEnabled(true);
        }else if(cmbSearch.getSelectedItem().toString().equals("Educational Background")){
            DisableAllChoice();
            cmbEducational1.setEnabled(true);
        }else if(cmbSearch.getSelectedItem().toString().equals("Nationality")){
            DisableAllChoice();
            cmbNationality1.setEnabled(true);
        }
    }
    private void FillSearch(){
        cmbSearch.removeAllItems();
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("Select sd_description from tblsearchdata order by sd_description");
            DbConn.rs = DbConn.pstmt.executeQuery();
            while (DbConn.rs.next()){
                cmbSearch.addItem(DbConn.rs.getString(1));
            }
            DbConn.pstmt.close();
            cmbSearch.setSelectedIndex(-1);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Fill Search");
        }
    }
    private void DisableCheckBoxes(){
        chkAge.setEnabled(false);
        chkAll.setEnabled(false);
        chkBackground.setEnabled(false);
        chkCvSource.setEnabled(false);
        chkField.setEnabled(false);
        chkGender.setEnabled(false);
        chkNationality.setEnabled(false);
        chkPosition.setEnabled(false);
        chkStatus.setEnabled(false);
    }
    private void FillField(){
        cmbField.removeAllItems();
        cmbField1.removeAllItems();
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("Select fi_description from tblfield order by fi_description");
            DbConn.rs = DbConn.pstmt.executeQuery();
            while (DbConn.rs.next()){
                cmbField.addItem(DbConn.rs.getString(1));
                cmbField1.addItem(DbConn.rs.getString(1));
            }
            DbConn.pstmt.close();
            cmbField.setSelectedIndex(-1);
            cmbField1.setSelectedIndex(-1);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Fill Education");
        }
    }
    private void FillStatus(){
        cmbStatus.removeAllItems();
        cmbStatus1.removeAllItems();
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("Select st_description from tblstatus order by st_description");
            DbConn.rs = DbConn.pstmt.executeQuery();
            while (DbConn.rs.next()){
                cmbStatus.addItem(DbConn.rs.getString(1));
                cmbStatus1.addItem(DbConn.rs.getString(1));
            }
            DbConn.pstmt.close();
            cmbStatus.setSelectedIndex(-1);
            cmbStatus1.setSelectedIndex(-1);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Fill Education");
        }
    }
    private void FillEducation(){
        cmbEducational.removeAllItems();
        cmbEducational1.removeAllItems();
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("Select ed_description from tbleducation order by ed_description");
            DbConn.rs = DbConn.pstmt.executeQuery();
            while (DbConn.rs.next()){
                cmbEducational.addItem(DbConn.rs.getString(1));
                cmbEducational1.addItem(DbConn.rs.getString(1));
            }
            DbConn.pstmt.close();
            cmbEducational.setSelectedIndex(-1);
            cmbEducational1.setSelectedIndex(-1);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Fill Education");
        }
    }
    private void FillSource(){
        cmbSource.removeAllItems();
        cmbSource1.removeAllItems();
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("Select cs_description from tblcvsource order by cs_description");
            DbConn.rs = DbConn.pstmt.executeQuery();
            while (DbConn.rs.next()){
                cmbSource.addItem(DbConn.rs.getString(1));
                cmbSource1.addItem(DbConn.rs.getString(1));
            }
            DbConn.pstmt.close();
            cmbSource.setSelectedIndex(-1);
            cmbSource1.setSelectedIndex(-1);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Fill Source");
        }
    }
    private void FillPosition(){
        cmbPosition.removeAllItems();
        cmbPosition1.removeAllItems();
        cmbPosition2.removeAllItems();
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("Select po_description from tblposition order by po_description");
            DbConn.rs = DbConn.pstmt.executeQuery();
            while (DbConn.rs.next()){
                cmbPosition.addItem(DbConn.rs.getString(1));
                cmbPosition1.addItem(DbConn.rs.getString(1));
                cmbPosition2.addItem(DbConn.rs.getString(1));
            }
            DbConn.pstmt.close();
            cmbPosition.setSelectedIndex(-1);
            cmbPosition1.setSelectedIndex(-1);
            cmbPosition2.setSelectedIndex(-1);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Fill Position");
        }
    }
    private void FillAge(){
        int i =20;
        cmbAge.removeAllItems();
        cmbAgeFrom.removeAllItems();
        cmbAgeTo.removeAllItems();
        while(i<51){
            cmbAge.addItem(String.valueOf(i));
            cmbAgeFrom.addItem(String.valueOf(i));
            cmbAgeTo.addItem(String.valueOf(i));
            i++;
        }
        cmbAge.setSelectedIndex(-1);
        cmbAgeFrom.setSelectedIndex(-1);
        cmbAgeTo.setSelectedIndex(-1);
    }
    private void FillGender(){
        cmbGender.removeAllItems();
        cmbGender.addItem("MALE");
        cmbGender.addItem("FEMALE");
        cmbGender.setSelectedIndex(-1);
    }
    private void FillNationality(){
        cmbNationality.removeAllItems();
        cmbNationality1.removeAllItems();
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("select nm_description from tblnationality order by nm_description");
            DbConn.rs = DbConn.pstmt.executeQuery();
            while (DbConn.rs.next()){
                cmbNationality.addItem(DbConn.rs.getString(1));
                cmbNationality1.addItem(DbConn.rs.getString(1));
            }
            DbConn.pstmt.close();
            cmbNationality.setSelectedIndex(-1);
            cmbNationality1.setSelectedIndex(-1);
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

        db = new javax.swing.JFileChooser();
        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        btnUpload = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        cmbPosition = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtSchool = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        cmbField = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        txtReferred = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        cmbSource = new javax.swing.JComboBox<>();
        cmbStatus = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAreaRemarks = new javax.swing.JTextArea();
        txtName = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cmbAge = new javax.swing.JComboBox<>();
        cmbNationality = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cmbGender = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        cmbEducational = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAreaAddress = new javax.swing.JTextArea();
        jLabel10 = new javax.swing.JLabel();
        btnAdd1 = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        dateReceived = new com.toedter.calendar.JDateChooser();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableCandidate = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        jLabel44 = new javax.swing.JLabel();
        cmbDepartment = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        cmbAgeFrom = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        cmbNationality1 = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        cmbPosition1 = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        cmbEducational1 = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        cmbField1 = new javax.swing.JComboBox<>();
        jLabel21 = new javax.swing.JLabel();
        cmbSource1 = new javax.swing.JComboBox<>();
        jLabel22 = new javax.swing.JLabel();
        cmbStatus1 = new javax.swing.JComboBox<>();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableCandidate1 = new javax.swing.JTable();
        radMale = new javax.swing.JRadioButton();
        radFemale = new javax.swing.JRadioButton();
        radBoth = new javax.swing.JRadioButton();
        cmbAgeTo = new javax.swing.JComboBox<>();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        chkAll = new javax.swing.JCheckBox();
        chkAge = new javax.swing.JCheckBox();
        chkGender = new javax.swing.JCheckBox();
        chkNationality = new javax.swing.JCheckBox();
        chkPosition = new javax.swing.JCheckBox();
        chkStatus = new javax.swing.JCheckBox();
        chkCvSource = new javax.swing.JCheckBox();
        chkField = new javax.swing.JCheckBox();
        chkBackground = new javax.swing.JCheckBox();
        cmbSearch = new javax.swing.JComboBox<>();
        lblCount = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel26 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        txtSearch = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jSeparator6 = new javax.swing.JSeparator();
        jPanel7 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        txtAmount = new javax.swing.JTextField();
        txtName1 = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        cmbPosition2 = new javax.swing.JComboBox<>();
        jLabel35 = new javax.swing.JLabel();
        dateStart = new com.toedter.calendar.JDateChooser();
        jLabel36 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        cmbProposedBy = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        tableOffer = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tableCandidate2 = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        txtSearchOnBoard = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        btnAdd2 = new javax.swing.JButton();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        lblNationality = new javax.swing.JLabel();
        lblGender = new javax.swing.JLabel();
        lblEducation = new javax.swing.JLabel();
        lblSchool = new javax.swing.JLabel();
        lblField = new javax.swing.JLabel();
        lblPosition = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        lblStatus = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
        jLabel45 = new javax.swing.JLabel();
        lblDepartment = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(214, 214, 194));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(214, 214, 194));
        jPanel2.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(153, 153, 153)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnUpload.setBackground(new java.awt.Color(255, 255, 255));
        btnUpload.setForeground(new java.awt.Color(0, 0, 0));
        btnUpload.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/attachmentIconMain.png"))); // NOI18N
        btnUpload.setText(" UPLOAD CV");
        btnUpload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUploadActionPerformed(evt);
            }
        });
        jPanel2.add(btnUpload, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 290, -1, -1));

        btnSave.setBackground(new java.awt.Color(255, 255, 255));
        btnSave.setForeground(new java.awt.Color(0, 0, 0));
        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/SaveIcon.png"))); // NOI18N
        btnSave.setText("SAVE");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        jPanel2.add(btnSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 360, -1, -1));

        cmbPosition.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel2.add(cmbPosition, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 180, 330, -1));

        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("Date Received:");
        jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 230, 100, 20));

        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("School:");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 150, 90, 20));
        jPanel2.add(txtSchool, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 150, 310, -1));

        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Field:");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 120, 90, 20));

        cmbField.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel2.add(cmbField, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 120, 310, -1));

        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setText("Referred By:");
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 90, 90, 20));
        jPanel2.add(txtReferred, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 90, 310, -1));

        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("CV Source:");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 60, 90, 20));

        cmbSource.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel2.add(cmbSource, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 60, 310, -1));

        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel2.add(cmbStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 310, 330, -1));

        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Status:");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 120, 20));

        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Remarks:");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, 120, 20));

        txtAreaRemarks.setColumns(20);
        txtAreaRemarks.setRows(5);
        jScrollPane2.setViewportView(txtAreaRemarks);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 360, 330, 50));
        jPanel2.add(txtName, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 60, 330, -1));

        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Name:");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 100, 20));

        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Age:");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 100, 20));

        cmbAge.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel2.add(cmbAge, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 90, 330, -1));

        cmbNationality.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel2.add(cmbNationality, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 120, 330, -1));

        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Nationality:");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 100, 20));

        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Gender:");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 100, 20));

        cmbGender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel2.add(cmbGender, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 150, 330, -1));

        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Educational Background:");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 180, 150, 20));

        cmbEducational.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel2.add(cmbEducational, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 200, 310, -1));

        txtAreaAddress.setColumns(20);
        txtAreaAddress.setRows(5);
        jScrollPane1.setViewportView(txtAreaAddress);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 260, 330, 30));

        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Address:");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 120, 20));

        btnAdd1.setBackground(new java.awt.Color(255, 255, 255));
        btnAdd1.setForeground(new java.awt.Color(0, 0, 0));
        btnAdd1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/NewIcon.png"))); // NOI18N
        btnAdd1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdd1ActionPerformed(evt);
            }
        });
        jPanel2.add(btnAdd1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, 40));

        jLabel14.setForeground(new java.awt.Color(0, 0, 0));
        jLabel14.setText("Position Applied:");
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 130, 20));

        dateReceived.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                dateReceivedMouseReleased(evt);
            }
        });
        jPanel2.add(dateReceived, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 250, 310, 30));

        tableCandidate.setModel(new javax.swing.table.DefaultTableModel(
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
        tableCandidate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableCandidateMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tableCandidate);

        jPanel2.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, 890, 150));

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setForeground(new java.awt.Color(0, 0, 0));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/viewIcon.png"))); // NOI18N
        jButton2.setText("VIEW CV");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 290, -1, -1));

        btnEdit.setBackground(new java.awt.Color(255, 255, 255));
        btnEdit.setForeground(new java.awt.Color(0, 0, 0));
        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/EditIcon.png"))); // NOI18N
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        jPanel2.add(btnEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 10, 60, 40));

        jLabel44.setForeground(new java.awt.Color(0, 0, 0));
        jLabel44.setText("Department:");
        jPanel2.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 130, 20));

        cmbDepartment.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel2.add(cmbDepartment, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 210, 330, -1));

        jTabbedPane1.addTab("New Candidate", jPanel2);

        jPanel3.setBackground(new java.awt.Color(214, 214, 194));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel15.setForeground(new java.awt.Color(0, 0, 0));
        jLabel15.setText("To:");
        jPanel3.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 60, 20, 20));

        cmbAgeFrom.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel3.add(cmbAgeFrom, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 60, 60, -1));

        jLabel16.setForeground(new java.awt.Color(0, 0, 0));
        jLabel16.setText("Nationality:");
        jPanel3.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 90, 100, 20));

        cmbNationality1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel3.add(cmbNationality1, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 90, 230, -1));

        jLabel17.setForeground(new java.awt.Color(0, 0, 0));
        jLabel17.setText("Name Search:");
        jPanel3.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 380, 100, 20));

        jLabel18.setForeground(new java.awt.Color(0, 0, 0));
        jLabel18.setText("Position Applied:");
        jPanel3.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 160, 130, 20));

        cmbPosition1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel3.add(cmbPosition1, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 180, 230, -1));

        jLabel19.setFont(new java.awt.Font("Dialog", 2, 10)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(102, 0, 0));
        jLabel19.setText("*Double click to view CV");
        jPanel3.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 610, 150, 20));

        cmbEducational1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel3.add(cmbEducational1, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 330, 230, -1));

        jLabel20.setForeground(new java.awt.Color(0, 0, 0));
        jLabel20.setText("Field:");
        jPanel3.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 280, 90, 20));

        cmbField1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel3.add(cmbField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 280, 230, -1));

        jLabel21.setForeground(new java.awt.Color(0, 0, 0));
        jLabel21.setText("CV Source:");
        jPanel3.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 250, 90, 20));

        cmbSource1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel3.add(cmbSource1, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 250, 230, -1));

        jLabel22.setForeground(new java.awt.Color(0, 0, 0));
        jLabel22.setText("Status:");
        jPanel3.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 200, 120, 20));

        cmbStatus1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel3.add(cmbStatus1, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 220, 230, -1));
        jPanel3.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 880, 10));

        tableCandidate1.setModel(new javax.swing.table.DefaultTableModel(
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
        tableCandidate1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableCandidate1MouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tableCandidate1);

        jPanel3.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(13, 410, 870, 160));

        radMale.setText("MALE");
        jPanel3.add(radMale, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 130, 70, -1));

        radFemale.setText("FEMALE");
        jPanel3.add(radFemale, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 130, 90, -1));

        radBoth.setText("BOTH");
        jPanel3.add(radBoth, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 130, 70, -1));

        cmbAgeTo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbAgeTo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbAgeToActionPerformed(evt);
            }
        });
        jPanel3.add(cmbAgeTo, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 60, 60, -1));

        jLabel24.setForeground(new java.awt.Color(0, 0, 0));
        jLabel24.setText("Not Available");
        jPanel3.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 100, 20));

        jLabel25.setForeground(new java.awt.Color(0, 0, 0));
        jLabel25.setText("From:");
        jPanel3.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 60, 40, 20));

        jButton3.setBackground(new java.awt.Color(255, 255, 255));
        jButton3.setForeground(new java.awt.Color(0, 0, 0));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/SearchIcon.png"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 120, 70, -1));

        jButton4.setBackground(new java.awt.Color(255, 255, 255));
        jButton4.setForeground(new java.awt.Color(0, 0, 0));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/viewIcon.png"))); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 350, 50, -1));

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel3.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 60, 10, 300));

        chkAll.setText("All");
        chkAll.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                chkAllMouseReleased(evt);
            }
        });
        jPanel3.add(chkAll, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));

        chkAge.setText("Age");
        chkAge.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                chkAgeMouseReleased(evt);
            }
        });
        jPanel3.add(chkAge, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, -1, -1));

        chkGender.setText("Gender");
        chkGender.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                chkGenderMouseReleased(evt);
            }
        });
        jPanel3.add(chkGender, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, -1, -1));

        chkNationality.setText("Nationality");
        chkNationality.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                chkNationalityMouseReleased(evt);
            }
        });
        jPanel3.add(chkNationality, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, -1, -1));

        chkPosition.setText("Position");
        chkPosition.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                chkPositionMouseReleased(evt);
            }
        });
        jPanel3.add(chkPosition, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, -1, -1));

        chkStatus.setText("Status");
        chkStatus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                chkStatusMouseReleased(evt);
            }
        });
        jPanel3.add(chkStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, -1, -1));

        chkCvSource.setText("CV Source");
        chkCvSource.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                chkCvSourceMouseReleased(evt);
            }
        });
        jPanel3.add(chkCvSource, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, -1, -1));

        chkField.setText("Field");
        chkField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                chkFieldMouseReleased(evt);
            }
        });
        jPanel3.add(chkField, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, -1, -1));

        chkBackground.setText("Background");
        chkBackground.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                chkBackgroundMouseReleased(evt);
            }
        });
        jPanel3.add(chkBackground, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, -1, -1));

        cmbSearch.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel3.add(cmbSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 90, 140, -1));

        lblCount.setForeground(new java.awt.Color(0, 0, 0));
        lblCount.setText("0");
        jPanel3.add(lblCount, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 260, 80, 20));

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel3.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 60, 10, 300));

        jLabel26.setForeground(new java.awt.Color(0, 0, 0));
        jLabel26.setText("Search By:");
        jPanel3.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 70, 100, 20));

        jButton5.setBackground(new java.awt.Color(255, 255, 255));
        jButton5.setForeground(new java.awt.Color(0, 0, 0));
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/resetIcon.png"))); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 120, 50, 50));
        jPanel3.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 250, 230, 10));

        jLabel27.setForeground(new java.awt.Color(0, 0, 0));
        jLabel27.setText("Educational Background:");
        jPanel3.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 310, 150, 20));

        jLabel28.setForeground(new java.awt.Color(0, 0, 0));
        jLabel28.setText("Age:");
        jPanel3.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 60, 100, 20));

        jLabel29.setForeground(new java.awt.Color(0, 0, 0));
        jLabel29.setText("Total Count:");
        jPanel3.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 260, 80, 20));

        jButton6.setBackground(new java.awt.Color(255, 255, 255));
        jButton6.setForeground(new java.awt.Color(0, 0, 0));
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/printericon.png"))); // NOI18N
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 350, 50, -1));
        jPanel3.add(txtSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 374, 300, 30));

        jLabel30.setForeground(new java.awt.Color(0, 0, 0));
        jLabel30.setText("Gender:");
        jPanel3.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 120, 100, 20));

        jButton7.setText("Reset");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 372, -1, 30));

        jTabbedPane1.addTab("Candidate Search", jPanel3);

        jPanel6.setBackground(new java.awt.Color(214, 214, 194));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel6.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 890, 20));

        jPanel7.setBackground(new java.awt.Color(214, 214, 194));
        jPanel7.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(153, 153, 153)));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel32.setForeground(new java.awt.Color(0, 0, 0));
        jLabel32.setText("Name:");
        jPanel7.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 50, 20));

        jLabel33.setForeground(new java.awt.Color(0, 0, 0));
        jLabel33.setText("Amount:");
        jPanel7.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 50, 20));
        jPanel7.add(txtAmount, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 60, 200, -1));
        jPanel7.add(txtName1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, 580, -1));

        jLabel34.setForeground(new java.awt.Color(0, 0, 0));
        jLabel34.setText("Generated By:");
        jPanel7.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 100, 80, 20));

        cmbPosition2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel7.add(cmbPosition2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 100, 200, -1));

        jLabel35.setForeground(new java.awt.Color(0, 0, 0));
        jLabel35.setText("Start Date:");
        jPanel7.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 80, 20));

        dateStart.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                dateStartMouseReleased(evt);
            }
        });
        jPanel7.add(dateStart, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 140, 200, 30));

        jLabel36.setForeground(new java.awt.Color(0, 0, 0));
        jLabel36.setText("ID:");
        jPanel7.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 60, 50, 20));
        jPanel7.add(txtID, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 60, 270, -1));

        jLabel37.setForeground(new java.awt.Color(0, 0, 0));
        jLabel37.setText("Position:");
        jPanel7.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 80, 20));

        cmbProposedBy.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel7.add(cmbProposedBy, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 100, 270, -1));

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setForeground(new java.awt.Color(0, 0, 0));
        jButton1.setText("Generate Job Offer");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel7.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 10, 180, 160));

        jPanel6.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 890, 180));

        tableOffer.setModel(new javax.swing.table.DefaultTableModel(
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
        tableOffer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableOfferMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tableOffer);

        jPanel6.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, 890, 290));

        jTabbedPane1.addTab("Offer Letter", jPanel6);

        jPanel4.setBackground(new java.awt.Color(214, 214, 194));
        jPanel4.setForeground(new java.awt.Color(0, 0, 0));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tableCandidate2.setModel(new javax.swing.table.DefaultTableModel(
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
        tableCandidate2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableCandidate2MouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tableCandidate2);

        jPanel4.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, 900, 250));

        jPanel5.setBackground(new java.awt.Color(214, 214, 194));
        jPanel5.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(153, 153, 153)));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel23.setForeground(new java.awt.Color(0, 0, 0));
        jLabel23.setText("Name:");
        jPanel5.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 90, 20));
        jPanel5.add(txtSearchOnBoard, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 10, 640, -1));

        jLabel31.setForeground(new java.awt.Color(0, 0, 0));
        jLabel31.setText("Gender:");
        jPanel5.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 70, 20));
        jPanel5.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 880, 10));

        btnAdd2.setBackground(new java.awt.Color(255, 255, 255));
        btnAdd2.setForeground(new java.awt.Color(0, 0, 0));
        btnAdd2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/NewIcon.png"))); // NOI18N
        btnAdd2.setText("Process On-Board");
        btnAdd2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnAdd2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnAdd2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdd2ActionPerformed(evt);
            }
        });
        jPanel5.add(btnAdd2, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 150, 190, 120));

        jLabel38.setForeground(new java.awt.Color(0, 0, 0));
        jLabel38.setText("Nationality:");
        jPanel5.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 70, 20));

        jLabel39.setForeground(new java.awt.Color(0, 0, 0));
        jLabel39.setText("Educational Background:");
        jPanel5.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 150, 20));

        jLabel40.setForeground(new java.awt.Color(0, 0, 0));
        jLabel40.setText("School:");
        jPanel5.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 70, 20));

        jLabel41.setForeground(new java.awt.Color(0, 0, 0));
        jLabel41.setText("Field:");
        jPanel5.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 70, 20));

        jLabel42.setForeground(new java.awt.Color(0, 0, 0));
        jLabel42.setText("Position:");
        jPanel5.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 70, 20));

        lblNationality.setForeground(new java.awt.Color(0, 0, 0));
        jPanel5.add(lblNationality, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 70, 370, 20));

        lblGender.setForeground(new java.awt.Color(0, 0, 0));
        jPanel5.add(lblGender, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 100, 370, 20));

        lblEducation.setForeground(new java.awt.Color(0, 0, 0));
        jPanel5.add(lblEducation, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 130, 370, 20));

        lblSchool.setForeground(new java.awt.Color(0, 0, 0));
        jPanel5.add(lblSchool, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 160, 370, 20));

        lblField.setForeground(new java.awt.Color(0, 0, 0));
        jPanel5.add(lblField, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 190, 370, 20));

        lblPosition.setForeground(new java.awt.Color(0, 0, 0));
        jPanel5.add(lblPosition, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 220, 190, 20));

        jLabel43.setForeground(new java.awt.Color(0, 0, 0));
        jLabel43.setText("Status:");
        jPanel5.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 70, 20));

        lblStatus.setForeground(new java.awt.Color(0, 0, 0));
        jPanel5.add(lblStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 250, 370, 20));

        jButton8.setBackground(new java.awt.Color(255, 255, 255));
        jButton8.setForeground(new java.awt.Color(0, 0, 0));
        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/resetIcon.png"))); // NOI18N
        jButton8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 10, 60, 50));

        jLabel45.setForeground(new java.awt.Color(0, 0, 0));
        jLabel45.setText("Department:");
        jPanel5.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 220, 70, 20));

        lblDepartment.setForeground(new java.awt.Color(0, 0, 0));
        jPanel5.add(lblDepartment, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 220, 220, 20));

        jPanel4.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 900, 280));

        jTabbedPane1.addTab("Candidate On-Boarding", jPanel4);

        jPanel1.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 920, 610));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 941, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 652, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnUploadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUploadActionPerformed

        FileFilter ft = new FileNameExtensionFilter("images .pdf,.jpg,.png", "jpg","png","pdf");
        File outputFile  = new File("src\\attachments\\CV" + "-" + txtName.getText());
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
            attachedCV = true;
            getImagePath = db.getSelectedFile().getPath();
        }
    }//GEN-LAST:event_btnUploadActionPerformed
    private void FillTable(){
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("Select ca_seqno,ca_name,ca_age,ca_nationality,ca_field,ca_school,ca_position from tblcandidate order by ca_seqno");
            DbConn.rs = DbConn.pstmt.executeQuery();
            tableCandidate.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
            tableCandidate2.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
            DbConn.pstmt.close();
            SetHeaderTable();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Fill Table");
        }
    }
    private void SetHeaderTable(){
        tableCandidate.getColumnModel().getColumn(0).setHeaderValue("ID");
        tableCandidate.getColumnModel().getColumn(1).setHeaderValue("NAME");
        tableCandidate.getColumnModel().getColumn(2).setHeaderValue("AGE");
        tableCandidate.getColumnModel().getColumn(3).setHeaderValue("NATIONALITY");
        tableCandidate.getColumnModel().getColumn(4).setHeaderValue("FIELD");
        tableCandidate.getColumnModel().getColumn(5).setHeaderValue("SCHOOL");
        tableCandidate.getColumnModel().getColumn(6).setHeaderValue("POSITION APPLIED");
    }
    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if (attachedCV!=true && getImagePath.equals(""))
        {
            int itemSelect = JOptionPane.showConfirmDialog(this, "No CV attached. Proceed?","CV Bank",JOptionPane.YES_NO_OPTION);
            if (itemSelect == JOptionPane.NO_OPTION)
                {
                    return;
                }
        }
        try{
            if (add==true && edit==false){
                DbConn.SQLQuery = "insert into tblcandidate (ca_name,ca_age,ca_nationality,ca_gender,ca_cvsource,ca_field,ca_school,ca_education,"
                        + "ca_position,ca_address,ca_status,ca_remarks,ca_datereceived,ca_referredby,ca_addby,ca_date,ca_cv,ca_department) "
                        + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
//                FileInputStream input = null;
//                File cvFile = new File(getImagePath);
//                input = new FileInputStream(cvFile);)
                DbConn.pstmt =DbConn.conn.prepareStatement(DbConn.SQLQuery);
                DbConn.pstmt.setString(1, txtName.getText());
                DbConn.pstmt.setString(2, cmbAge.getSelectedItem().toString());
                DbConn.pstmt.setString(3, cmbNationality.getSelectedItem().toString());
                DbConn.pstmt.setString(4, cmbGender.getSelectedItem().toString());
                DbConn.pstmt.setString(5, cmbSource.getSelectedItem().toString());
                DbConn.pstmt.setString(6, cmbField.getSelectedItem().toString());
                DbConn.pstmt.setString(7, txtSchool.getText());
                DbConn.pstmt.setString(8, cmbEducational.getSelectedItem().toString());
                DbConn.pstmt.setString(9, cmbPosition.getSelectedItem().toString());
                DbConn.pstmt.setString(10, txtAreaAddress.getText());
                DbConn.pstmt.setString(11, cmbStatus.getSelectedItem().toString());
                DbConn.pstmt.setString(12, txtAreaRemarks.getText());
                DbConn.pstmt.setString(13, DbConn.sdfDate.format(dateReceived.getDate()));
                DbConn.pstmt.setString(14, txtReferred.getText());
                DbConn.pstmt.setString(15, "Admin");
//                DbConn.pstmt.setString(15, DbConn.GetLoggedInUserName);
                DbConn.pstmt.setString(16, DbConn.sdfDate.format(new Date()));
                if (attachedCV ==true){
                    DbConn.pstmt.setString(17,getImagePath);
                }else{
                    DbConn.pstmt.setString(17,"No CV Attached");
                }
//                
                DbConn.pstmt.setString(18, cmbDepartment.getSelectedItem().toString());
                DbConn.pstmt.execute();
                JOptionPane.showMessageDialog(this, "Record Saved");
                FillTable();
                attachedCV = false;
            }else if(edit==true && add ==false){
                int row = tableCandidate.getSelectedRow();
                int ba = tableCandidate.convertRowIndexToModel(row);
                DbConn.SQLQuery = "update tblcandidate set ca_name=?,ca_age=?,ca_nationality=?,ca_gender=?,ca_cvsource=?,ca_field=?,ca_school=?,ca_education=?,"
                        + "ca_position=?,ca_address=?,ca_status=?,ca_remarks=?,ca_datereceived=?,ca_referredby=?,ca_addby=?,ca_date=?,ca_cv=?,ca_department=? "
                        + " where ca_seqno=?";
//                FileInputStream input = null;
//                File cvFile = new File(getImagePath);
//                input = new FileInputStream(cvFile);)
                DbConn.pstmt =DbConn.conn.prepareStatement(DbConn.SQLQuery);
                DbConn.pstmt.setString(1, txtName.getText());
                DbConn.pstmt.setString(2, cmbAge.getSelectedItem().toString());
                DbConn.pstmt.setString(3, cmbNationality.getSelectedItem().toString());
                DbConn.pstmt.setString(4, cmbGender.getSelectedItem().toString());
                DbConn.pstmt.setString(5, cmbSource.getSelectedItem().toString());
                DbConn.pstmt.setString(6, cmbField.getSelectedItem().toString());
                DbConn.pstmt.setString(7, txtSchool.getText());
                DbConn.pstmt.setString(8, cmbEducational.getSelectedItem().toString());
                DbConn.pstmt.setString(9, cmbPosition.getSelectedItem().toString());
                DbConn.pstmt.setString(10, txtAreaAddress.getText());
                DbConn.pstmt.setString(11, cmbStatus.getSelectedItem().toString());
                DbConn.pstmt.setString(12, txtAreaRemarks.getText());
                DbConn.pstmt.setString(13, DbConn.sdfDate.format(dateReceived.getDate()));
                DbConn.pstmt.setString(14, txtReferred.getText());
                DbConn.pstmt.setString(15, "Admin");
//                DbConn.pstmt.setString(15, DbConn.GetLoggedInUserName);
                DbConn.pstmt.setString(16, DbConn.sdfDate.format(new Date()));
                if (!getImagePath.equals("")){
                    DbConn.pstmt.setString(17,getImagePath);
                }else{
                    DbConn.pstmt.setString(17,"No CV Attached");
                }
                DbConn.pstmt.setString(18, cmbDepartment.getSelectedItem().toString());
                DbConn.pstmt.setString(19, tableCandidate.getModel().getValueAt(ba,0).toString());
                DbConn.pstmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Record Updated");
                FillTable();
                attachedCV = false;
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void dateReceivedMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dateReceivedMouseReleased

    }//GEN-LAST:event_dateReceivedMouseReleased

    private void btnAdd1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdd1ActionPerformed
        add=true;
        edit=false;
    }//GEN-LAST:event_btnAdd1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("select ca_cv from tblcandidate where ca_seqno=?");
            int row = tableCandidate.getSelectedRow();
            int ba = tableCandidate.convertRowIndexToModel(row);
            DbConn.pstmt.setString(1, tableCandidate.getModel().getValueAt(ba, 0).toString());
            DbConn.rs = DbConn.pstmt.executeQuery();
            if (DbConn.rs.next()){
                Runtime.getRuntime().exec("rundll32 url.dll, FileProtocolHandler " + DbConn.rs.getString(1));
            }
            DbConn.pstmt.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "No CV Attached");
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void tableCandidateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableCandidateMouseClicked
        int row = tableCandidate.getSelectedRow();
        int ba = tableCandidate.convertRowIndexToModel(row);
        try{
            DbConn.SQLQuery = "select * from tblcandidate where ca_seqno=?";
            DbConn.pstmt= DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.pstmt.setString(1, tableCandidate.getModel().getValueAt(ba, 0).toString());
            DbConn.rs = DbConn.pstmt.executeQuery();
            if (DbConn.rs.next()){
                txtName.setText(DbConn.rs.getString("ca_name"));
                cmbAge.setSelectedItem((DbConn.rs.getString("ca_age")));
                cmbNationality.setSelectedItem((DbConn.rs.getString("ca_nationality")));
                cmbGender.setSelectedItem((DbConn.rs.getString("ca_gender")));
                cmbSource.setSelectedItem((DbConn.rs.getString("ca_cvsource")));
                cmbField.setSelectedItem((DbConn.rs.getString("ca_field")));
                txtSchool.setText(DbConn.rs.getString("ca_school"));
                cmbEducational.setSelectedItem((DbConn.rs.getString("ca_education")));
                cmbPosition.setSelectedItem((DbConn.rs.getString("ca_position")));
                txtAreaAddress.setText(DbConn.rs.getString("ca_address"));
                cmbStatus.setSelectedItem((DbConn.rs.getString("ca_status")));
                txtAreaRemarks.setText(DbConn.rs.getString("ca_remarks"));
                dateReceived.setDate(DbConn.sdfDate.parse(DbConn.rs.getString("ca_datereceived")));
                txtReferred.setText(DbConn.rs.getString("ca_referredby"));
                getImagePath = DbConn.rs.getString("ca_cv");
                cmbDepartment.setSelectedItem((DbConn.rs.getString("ca_department")));
            }
            DbConn.pstmt.close();
        }catch(SQLException | ParseException e){
            JOptionPane.showMessageDialog(this, "Candidate Selection");
        }
    }//GEN-LAST:event_tableCandidateMouseClicked

    private void cmbAgeToActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbAgeToActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbAgeToActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        //Age Selected
        if (cmbAgeFrom.isEnabled()){
            DbConn.SQLQuery = "Select ca_seqno,ca_name,ca_age,ca_nationality,ca_field,ca_school,"
            + "ca_position from tblcandidate where ca_age between ? and ? order by ca_seqno";
            try{
                DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
                DbConn.pstmt.setString(1, cmbAgeFrom.getSelectedItem().toString());
                DbConn.pstmt.setString(2, cmbAgeTo.getSelectedItem().toString());
                DbConn.rs = DbConn.pstmt.executeQuery();
                tableCandidate1.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
                DbConn.pstmt.close();
                SetHeaderTable();
            }catch(SQLException e){
                JOptionPane.showMessageDialog(this, "Age Search");
            }
        }
        //Nationality
        if (cmbNationality1.isEnabled()){
            DbConn.SQLQuery = "Select ca_seqno,ca_name,ca_age,ca_nationality,ca_field,ca_school,"
            + "ca_position from tblcandidate where ca_nationality =? order by ca_seqno";
            try{
                DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
                DbConn.pstmt.setString(1, cmbNationality1.getSelectedItem().toString());
                DbConn.rs = DbConn.pstmt.executeQuery();
                tableCandidate1.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
                DbConn.pstmt.close();
                SetHeaderTable();
            }catch(SQLException e){
                JOptionPane.showMessageDialog(this, "Nationality Search");
            }
        }
        //Gender
        String GetGender = "MALE";
        if (radBoth.isEnabled()){
            try{
                if(radBoth.isSelected()){
                    DbConn.SQLQuery = "Select ca_seqno,ca_name,ca_age,ca_nationality,ca_field,ca_school,"
                    + "ca_position from tblcandidate order by ca_seqno";
                    DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
                }else{
                    DbConn.SQLQuery = "Select ca_seqno,ca_name,ca_age,ca_nationality,ca_field,ca_school,"
                    + "ca_position from tblcandidate where ca_gender=? order by ca_seqno";
                    if(radFemale.isSelected()){
                        GetGender="FEMALE";
                    }
                    DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
                    DbConn.pstmt.setString(1, GetGender);
                }
                DbConn.rs = DbConn.pstmt.executeQuery();
                tableCandidate1.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
                DbConn.pstmt.close();
                SetHeaderTable();
            }catch(SQLException e){
                JOptionPane.showMessageDialog(this, "Gender Search");
            }
        }
        //Position
        if (cmbPosition1.isEnabled()){
            DbConn.SQLQuery = "Select ca_seqno,ca_name,ca_age,ca_nationality,ca_field,ca_school,"
            + "ca_position from tblcandidate where ca_position =? order by ca_seqno";
            try{
                DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
                DbConn.pstmt.setString(1, cmbPosition1.getSelectedItem().toString());
                DbConn.rs = DbConn.pstmt.executeQuery();
                tableCandidate1.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
                DbConn.pstmt.close();
                SetHeaderTable();
            }catch(SQLException e){
                JOptionPane.showMessageDialog(this, "Position Search");
            }
        }
        //Status
        if (cmbStatus1.isEnabled()){
            DbConn.SQLQuery = "Select ca_seqno,ca_name,ca_age,ca_nationality,ca_field,ca_school,"
            + "ca_position from tblcandidate where ca_status =? order by ca_seqno";
            try{
                DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
                DbConn.pstmt.setString(1, cmbStatus1.getSelectedItem().toString());
                DbConn.rs = DbConn.pstmt.executeQuery();
                tableCandidate1.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
                DbConn.pstmt.close();
                SetHeaderTable();
            }catch(SQLException e){
                JOptionPane.showMessageDialog(this, "Status Search");
            }
        }
        //Source
        if (cmbSource1.isEnabled()){
            DbConn.SQLQuery = "Select ca_seqno,ca_name,ca_age,ca_nationality,ca_field,ca_school,"
            + "ca_position from tblcandidate where ca_cvsource =? order by ca_seqno";
            try{
                DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
                DbConn.pstmt.setString(1, cmbSource1.getSelectedItem().toString());
                DbConn.rs = DbConn.pstmt.executeQuery();
                tableCandidate1.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
                DbConn.pstmt.close();
                SetHeaderTable();
            }catch(SQLException e){
                JOptionPane.showMessageDialog(this, "CV Source Search");
            }
        }
        //Field
        if (cmbField1.isEnabled()){
            DbConn.SQLQuery = "Select ca_seqno,ca_name,ca_age,ca_nationality,ca_field,ca_school,"
            + "ca_position from tblcandidate where ca_field =? order by ca_seqno";
            try{
                DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
                DbConn.pstmt.setString(1, cmbField1.getSelectedItem().toString());
                DbConn.rs = DbConn.pstmt.executeQuery();
                tableCandidate1.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
                DbConn.pstmt.close();
                SetHeaderTable();
            }catch(SQLException e){
                JOptionPane.showMessageDialog(this, "Field Search");
            }
        }
        //Educational Background
        if (cmbEducational1.isEnabled()){
            DbConn.SQLQuery = "Select ca_seqno,ca_name,ca_age,ca_nationality,ca_field,ca_school,"
            + "ca_position from tblcandidate where ca_education=? order by ca_seqno";
            try{
                DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
                DbConn.pstmt.setString(1, cmbEducational1.getSelectedItem().toString());
                DbConn.rs = DbConn.pstmt.executeQuery();
                tableCandidate1.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
                DbConn.pstmt.close();
                SetHeaderTable();
            }catch(SQLException e){
                JOptionPane.showMessageDialog(this, "Educational Search");
            }
        }
        DisplayCount();
        //        String GetGender = "MALE";
        //        try{
            //        if (radBoth.isSelected()){
                //            DbConn.SQLQuery = "Select ca_seqno,ca_name,ca_age,ca_nationality,ca_field,ca_school,"
                //                    + "ca_position from tblcandidate where ca_nationality=? and ca_position=? and "
                //                    + "ca_status=? and ca_cvsource=? and ca_field=? and ca_education =? and ca_age between ? and ?"
                //                    + "order by ca_seqno";
                //            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
                //        }else{
                //            DbConn.SQLQuery = "Select ca_seqno,ca_name,ca_age,ca_nationality,ca_field,ca_school,"
                //                    + "ca_position from tblcandidate where ca_nationality=? and ca_position=? and "
                //                    + "ca_status=? and ca_cvsource=? and ca_field=? and ca_education =? and ca_age between ? and ? "
                //                    + "and ca_gender=? order by ca_seqno";
                //            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
                //            if(radFemale.isSelected()){
                    //                GetGender = "FEMALE";
                    //            }
                //            DbConn.pstmt.setString(9, GetGender);
                //        }
            //            DbConn.pstmt.setString(1, cmbNationality.getSelectedItem().toString());
            //            DbConn.pstmt.setString(2, cmbPosition.getSelectedItem().toString());
            //            DbConn.pstmt.setString(3, cmbStatus.getSelectedItem().toString());
            //            DbConn.pstmt.setString(4, cmbSource.getSelectedItem().toString());
            //            DbConn.pstmt.setString(5, cmbField.getSelectedItem().toString());
            //            DbConn.pstmt.setString(6, cmbEducational.getSelectedItem().toString());
            //            DbConn.pstmt.setString(7, cmbAge.getSelectedItem().toString());
            //            DbConn.pstmt.setString(8, cmbAgeTo.getSelectedItem().toString());
            //            DbConn.rs = DbConn.pstmt.executeQuery();
            //            tableCandidate1.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
            //            DbConn.pstmt.close();
            //            SetHeaderTable();
            //        }catch(SQLException e){
            //            JOptionPane.showMessageDialog(this, e.getMessage());
            //        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("select ca_cv from tblcandidate where ca_seqno=?");
            int row = tableCandidate1.getSelectedRow();
            int ba = tableCandidate1.convertRowIndexToModel(row);
            DbConn.pstmt.setString(1, tableCandidate1.getModel().getValueAt(ba, 0).toString());
            DbConn.rs = DbConn.pstmt.executeQuery();
            if (DbConn.rs.next()){
                Runtime.getRuntime().exec("rundll32 url.dll, FileProtocolHandler " + DbConn.rs.getString(1));
            }
            DbConn.pstmt.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "No CV Attached");
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void chkAllMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chkAllMouseReleased
        if (chkAll.isSelected()){
            chkAge.setSelected(true);
            chkBackground.setSelected(true);
            chkCvSource.setSelected(true);
            chkField.setSelected(true);
            chkGender.setSelected(true);
            chkNationality.setSelected(true);
            chkPosition.setSelected(true);
            chkStatus.setSelected(true);
            EnableAllChoice();
        }else{
            chkAge.setSelected(false);
            chkBackground.setSelected(false);
            chkCvSource.setSelected(false);
            chkField.setSelected(false);
            chkGender.setSelected(false);
            chkNationality.setSelected(false);
            chkPosition.setSelected(false);
            chkStatus.setSelected(false);
            DisableAllChoice();
        }
    }//GEN-LAST:event_chkAllMouseReleased

    private void chkAgeMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chkAgeMouseReleased
        if (chkAge.isSelected()){
            cmbAge.setEnabled(true);
            cmbAgeTo.setEnabled(true);
        }else{
            cmbAge.setEnabled(false);
            cmbAgeTo.setEnabled(false);
        }
    }//GEN-LAST:event_chkAgeMouseReleased

    private void chkGenderMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chkGenderMouseReleased
        if (chkGender.isSelected()){
            radBoth.setEnabled(true);
            radFemale.setEnabled(true);
            radMale.setEnabled(true);
        }else{
            radBoth.setEnabled(false);
            radFemale.setEnabled(false);
            radMale.setEnabled(false);
        }
    }//GEN-LAST:event_chkGenderMouseReleased

    private void chkNationalityMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chkNationalityMouseReleased
        if (chkNationality.isSelected()){
            cmbNationality.setEnabled(true);
            cmbNationality.setEnabled(true);
        }else{
            cmbNationality.setEnabled(false);
            cmbNationality.setEnabled(false);
        }
    }//GEN-LAST:event_chkNationalityMouseReleased

    private void chkPositionMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chkPositionMouseReleased
        if (chkPosition.isSelected()){
            cmbPosition.setEnabled(true);
            cmbPosition.setEnabled(true);
        }else{
            cmbPosition.setEnabled(false);
            cmbPosition.setEnabled(false);
        }
    }//GEN-LAST:event_chkPositionMouseReleased

    private void chkStatusMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chkStatusMouseReleased
        if (chkStatus.isSelected()){
            cmbStatus.setEnabled(true);
            cmbStatus.setEnabled(true);
        }else{
            cmbStatus.setEnabled(false);
            cmbStatus.setEnabled(false);
        }
    }//GEN-LAST:event_chkStatusMouseReleased

    private void chkCvSourceMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chkCvSourceMouseReleased
        if (chkCvSource.isSelected()){
            cmbSource.setEnabled(true);
            cmbSource.setEnabled(true);
        }else{
            cmbSource.setEnabled(false);
            cmbSource.setEnabled(false);
        }
    }//GEN-LAST:event_chkCvSourceMouseReleased

    private void chkFieldMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chkFieldMouseReleased
        if (chkField.isSelected()){
            cmbField.setEnabled(true);
            cmbField.setEnabled(true);
        }else{
            cmbField.setEnabled(false);
            cmbField.setEnabled(false);
        }
    }//GEN-LAST:event_chkFieldMouseReleased

    private void chkBackgroundMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chkBackgroundMouseReleased
        if (chkBackground.isSelected()){
            cmbEducational.setEnabled(true);
            cmbEducational.setEnabled(true);
        }else{
            cmbEducational.setEnabled(false);
            cmbEducational.setEnabled(false);
        }
    }//GEN-LAST:event_chkBackgroundMouseReleased
    private void EnableAllChoice(){
        cmbAgeFrom.setEnabled(true);
        cmbAgeTo.setEnabled(true);
        cmbEducational1.setEnabled(true);
        cmbField1.setEnabled(true);
        cmbNationality1.setEnabled(true);
        cmbPosition1.setEnabled(true);
        cmbSource1.setEnabled(true);
        cmbStatus1.setEnabled(true);
        radBoth.setEnabled(true);
        radFemale.setEnabled(true);
        radMale.setEnabled(true);
    }
    private void DisableAllChoice(){
        cmbAgeFrom.setEnabled(false);
        cmbAgeTo.setEnabled(false);
        cmbEducational1.setEnabled(false);
        cmbField1.setEnabled(false);
        cmbNationality1.setEnabled(false);
        cmbPosition1.setEnabled(false);
        cmbSource1.setEnabled(false);
        cmbStatus1.setEnabled(false);
        radBoth.setEnabled(false);
        radFemale.setEnabled(false);
        radMale.setEnabled(false);
    }
    private void DisplayCount(){
        int TotalRows = tableCandidate.getRowCount();
        lblCount.setText(String.valueOf(TotalRows));
    }
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        FillTable();
        cmbSearch.setSelectedIndex(-1);
        DisableAllChoice();
        DisplayCount();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        Map param = new HashMap();
        param.put("SortNationality", cmbNationality.getSelectedItem().toString());
        try{
            DbConn.conn.close();
            Class.forName("com.mysql.jdbc.Driver");
            DbConn.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbeshr?autoReconnect=true","root","root");
            JasperDesign jd = JRXmlLoader.load(new File("src\\HRReports\\report1.jrxml"));
            JasperReport jr = JasperCompileManager.compileReport(jd);
            JasperPrint jp = JasperFillManager.fillReport(jr, param,DbConn.conn);
            JasperViewer.viewReport(jp,false);

        }catch(ClassNotFoundException | SQLException | JRException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        } 
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        FillTable();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void btnAdd2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdd2ActionPerformed
        int itemSelect = JOptionPane.showConfirmDialog(this, "This will add the candidate to employee roster. Proceed?","On Boarding",JOptionPane.YES_NO_OPTION);
        if (itemSelect==JOptionPane.YES_OPTION){
            try{
                
                try{
                //update status to onboarded
                DbConn.pstmt = DbConn.conn.prepareStatement("update tblcandidate set ca_status=? where ca_seqno=? and ca_name =?");
                DbConn.pstmt.setString(1, "On Boarded");
                DbConn.pstmt.setInt(2, GetCandidateOnBoardNumber);
                DbConn.pstmt.setString(3, txtSearchOnBoard.getText());
                DbConn.pstmt.executeUpdate();
                DbConn.pstmt.close();
            }catch(SQLException e){
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
                    DbConn.SQLQuery = "INSERT into tblemployeeprofile (em_name,em_designation,em_addby,em_date,em_department) "
                            + "values (?,?,?,?,?)";
                    DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
                    DbConn.pstmt.setString(1,txtSearchOnBoard.getText());
                    DbConn.pstmt.setString(2,lblPosition.getText());
                    DbConn.pstmt.setString(3,"Admin");
                    DbConn.pstmt.setString(4,String.valueOf(DbConn.sdfDate.format(new Date())));
                    DbConn.pstmt.setString(5,lblDepartment.getText());
                    DbConn.pstmt.execute();
                    DbConn.pstmt.close();
                    JOptionPane.showMessageDialog(this, "Candidate onboarded. Complete details later.");  
            }catch(SQLException e){
                JOptionPane.showMessageDialog(this, "Employee Onboarding");
            }
            
        }else{
            return;
        }
        
    }//GEN-LAST:event_btnAdd2ActionPerformed

    private void dateStartMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dateStartMouseReleased

    }//GEN-LAST:event_dateStartMouseReleased
    private void FillGeneratedBy(){
        cmbProposedBy.removeAllItems();
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("select em_name from tblemployeeprofile order by em_name");
            DbConn.rs = DbConn.pstmt.executeQuery();
            while (DbConn.rs.next()){
                cmbProposedBy.addItem(DbConn.rs.getString(1));
            }
            DbConn.pstmt.close();
            cmbProposedBy.setSelectedIndex(-1);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Proposed Name Drop Down");
        }
    }
    private void GetTitle(){
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("select em_department,em_designation from tblemployeeprofile order by em_name");
            DbConn.rs = DbConn.pstmt.executeQuery();
            if (DbConn.rs.next()){
                GetDepartment = DbConn.rs.getString(1);
                GetDesignation = DbConn.rs.getString(2);
            }
            DbConn.pstmt.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Title Get");
        }
    }
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int itemSelect = JOptionPane.showConfirmDialog(this, "This will update the candidate status to Job Offer Generated. Proceed?","Letter of Offer",JOptionPane.YES_NO_OPTION);
        if (itemSelect == JOptionPane.YES_OPTION){
            GetTitle();

            Map param = new HashMap();
            param.put("CandidateName", txtName1.getText() + ",");
            param.put("GeneratedBy", cmbProposedBy.getSelectedItem().toString());
            param.put("StartDate", String.valueOf(DbConn.sdfDateDisplay.format(dateStart.getDate())));
            param.put("Position", cmbPosition2.getSelectedItem().toString());
            param.put("SalaryAmount", txtAmount.getText());
            param.put("TitleDepartment", GetDepartment);
            param.put("TitleSignature", GetDesignation);
            try{
                //update status to Job Offer Generated
                DbConn.pstmt = DbConn.conn.prepareStatement("update tblcandidate set ca_status=?,ca_position=? where ca_seqno=?");
                DbConn.pstmt.setString(1, "Job Offer Generated");
                DbConn.pstmt.setString(2, cmbPosition2.getSelectedItem().toString());
                DbConn.pstmt.setString(3, tableOffer.getValueAt(0, 0).toString());
                DbConn.pstmt.executeUpdate();
                DbConn.pstmt.close();
            }catch(SQLException e){
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
            try{
                //view letter offer
                DbConn.conn.close();
                Class.forName("com.mysql.jdbc.Driver");
                DbConn.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbeshr?autoReconnect=true","root","root");
                JasperDesign jd = JRXmlLoader.load(new File("src\\HRReports\\reportLetterOffer.jrxml"));
                JasperReport jr = JasperCompileManager.compileReport(jd);
                JasperPrint jp = JasperFillManager.fillReport(jr, param,DbConn.conn);
                JasperViewer.viewReport(jp,false);

            }catch(ClassNotFoundException | SQLException | JRException e){
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tableOfferMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableOfferMouseClicked
        int row = tableOffer.getSelectedRow();
        int ba = tableOffer.convertRowIndexToModel(row);

        txtName1.setText(tableOffer.getModel().getValueAt(ba, 1).toString());
        cmbPosition2.setSelectedItem(tableOffer.getModel().getValueAt(ba, 6).toString());

    }//GEN-LAST:event_tableOfferMouseClicked

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        edit=true;
        add=false;
    }//GEN-LAST:event_btnEditActionPerformed

    private void tableCandidate2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableCandidate2MouseClicked
        int row = tableCandidate2.getSelectedRow();
        int ba = tableCandidate2.convertRowIndexToModel(row);
        try{
            DbConn.SQLQuery = "select * from tblcandidate where ca_seqno=?";
            DbConn.pstmt= DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.pstmt.setString(1, tableCandidate2.getModel().getValueAt(ba, 0).toString());
            DbConn.rs = DbConn.pstmt.executeQuery();
            if (DbConn.rs.next()){
                lblNationality.setText(DbConn.rs.getString("ca_nationality"));
                String a = DbConn.rs.getString("ca_department");
                lblDepartment.setText(DbConn.rs.getString("ca_department"));
                GetCandidateOnBoardNumber = DbConn.rs.getInt("ca_seqno");
                lblGender.setText(DbConn.rs.getString("ca_gender"));
                lblEducation.setText(DbConn.rs.getString("ca_education"));
                lblSchool.setText(DbConn.rs.getString("ca_school"));
                lblField.setText(DbConn.rs.getString("ca_field"));
                lblPosition.setText(DbConn.rs.getString("ca_position"));
                lblStatus.setText(DbConn.rs.getString("ca_status"));
                txtSearchOnBoard.setText(DbConn.rs.getString("ca_name"));
            }
            DbConn.pstmt.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_tableCandidate2MouseClicked
    private void clearTextsOnBoard(){
        lblNationality.setText("");
        lblGender.setText("");
        lblEducation.setText("");
        lblSchool.setText("");
        lblField.setText("");
        lblPosition.setText("");
        lblStatus.setText("");
        txtSearchOnBoard.setText("");
    }
    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        clearTextsOnBoard();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void tableCandidate1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableCandidate1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tableCandidate1MouseClicked

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
            java.util.logging.Logger.getLogger(frmCandidate.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmCandidate.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmCandidate.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmCandidate.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmCandidate().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd1;
    private javax.swing.JButton btnAdd2;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUpload;
    private javax.swing.JCheckBox chkAge;
    private javax.swing.JCheckBox chkAll;
    private javax.swing.JCheckBox chkBackground;
    private javax.swing.JCheckBox chkCvSource;
    private javax.swing.JCheckBox chkField;
    private javax.swing.JCheckBox chkGender;
    private javax.swing.JCheckBox chkNationality;
    private javax.swing.JCheckBox chkPosition;
    private javax.swing.JCheckBox chkStatus;
    private javax.swing.JComboBox<String> cmbAge;
    private javax.swing.JComboBox<String> cmbAgeFrom;
    private javax.swing.JComboBox<String> cmbAgeTo;
    private javax.swing.JComboBox<String> cmbDepartment;
    private javax.swing.JComboBox<String> cmbEducational;
    private javax.swing.JComboBox<String> cmbEducational1;
    private javax.swing.JComboBox<String> cmbField;
    private javax.swing.JComboBox<String> cmbField1;
    private javax.swing.JComboBox<String> cmbGender;
    private javax.swing.JComboBox<String> cmbNationality;
    private javax.swing.JComboBox<String> cmbNationality1;
    private javax.swing.JComboBox<String> cmbPosition;
    private javax.swing.JComboBox<String> cmbPosition1;
    private javax.swing.JComboBox<String> cmbPosition2;
    private javax.swing.JComboBox<String> cmbProposedBy;
    private javax.swing.JComboBox<String> cmbSearch;
    private javax.swing.JComboBox<String> cmbSource;
    private javax.swing.JComboBox<String> cmbSource1;
    private javax.swing.JComboBox<String> cmbStatus;
    private javax.swing.JComboBox<String> cmbStatus1;
    private com.toedter.calendar.JDateChooser dateReceived;
    private com.toedter.calendar.JDateChooser dateStart;
    private javax.swing.JFileChooser db;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblCount;
    private javax.swing.JLabel lblDepartment;
    private javax.swing.JLabel lblEducation;
    private javax.swing.JLabel lblField;
    private javax.swing.JLabel lblGender;
    private javax.swing.JLabel lblNationality;
    private javax.swing.JLabel lblPosition;
    private javax.swing.JLabel lblSchool;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JRadioButton radBoth;
    private javax.swing.JRadioButton radFemale;
    private javax.swing.JRadioButton radMale;
    private javax.swing.JTable tableCandidate;
    private javax.swing.JTable tableCandidate1;
    private javax.swing.JTable tableCandidate2;
    private javax.swing.JTable tableOffer;
    private javax.swing.JTextField txtAmount;
    private javax.swing.JTextArea txtAreaAddress;
    private javax.swing.JTextArea txtAreaRemarks;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtName1;
    private javax.swing.JTextField txtReferred;
    private javax.swing.JTextField txtSchool;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtSearchOnBoard;
    // End of variables declaration//GEN-END:variables
}
