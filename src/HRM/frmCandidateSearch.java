
package HRM;

import com.sun.faces.renderkit.html_basic.ButtonRenderer;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class frmCandidateSearch extends javax.swing.JFrame {
DbConnection DbConn = new DbConnection();
JButton btnView = new JButton();
    public frmCandidateSearch() {
        initComponents();
        DbConn.DoConnect();
        FillAge();
        FillNationality();
        FillSource();
        FillPosition();
        FillEducation();
        FillField();
        FillStatus();
        FillTable();
        FillSearch();
        DisableAllChoice();
        DisableCheckBoxes();
        DisplayCount();
        tableCandidate.setDefaultEditor(Object.class, null);
        cmbSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {ListenSearchCombo();}
        });
        tableCandidate.addMouseListener(new MouseAdapter() {
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
    }
    private void ListenSearch(){
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("Select ca_seqno,ca_name,ca_age,ca_nationality,ca_field,ca_school,ca_position from tblcandidate "
                    + "where ca_name like ? order by ca_seqno");
            DbConn.pstmt.setString(1, "%"+ txtSearch.getText() +"%");
            DbConn.rs = DbConn.pstmt.executeQuery();
            tableCandidate.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
            DbConn.pstmt.close();
            SetHeaderTable();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Name Search");
        }
    }
    private void DisplayCount(){
        int TotalRows = tableCandidate.getRowCount();
        lblCount.setText(String.valueOf(TotalRows));
    }
    private void ListenSearchCombo(){
        if (cmbSearch.getSelectedIndex() == -1){
            return;
        }
        if (cmbSearch.getSelectedItem().toString().equals("All")){
            EnableAllChoice();
        }else if(cmbSearch.getSelectedItem().toString().equals("Age")){
            DisableAllChoice();
            cmbAge.setEnabled(true);
            cmbAgeTo.setEnabled(true);
        }else if(cmbSearch.getSelectedItem().toString().equals("Gender")){
            DisableAllChoice();
            radBoth.setEnabled(true);
            radFemale.setEnabled(true);
            radMale.setEnabled(rootPaneCheckingEnabled);
        }else if(cmbSearch.getSelectedItem().toString().equals("Position Applied")){
            DisableAllChoice();
            cmbPosition.setEnabled(true);
        }else if(cmbSearch.getSelectedItem().toString().equals("Status")){
            DisableAllChoice();
            cmbStatus.setEnabled(true);
        }else if(cmbSearch.getSelectedItem().toString().equals("CV Source")){
            DisableAllChoice();
            cmbSource.setEnabled(true);
        }else if(cmbSearch.getSelectedItem().toString().equals("Field")){
            DisableAllChoice();
            cmbField.setEnabled(true);
        }else if(cmbSearch.getSelectedItem().toString().equals("Educational Background")){
            DisableAllChoice();
            cmbEducational.setEnabled(true);
        }else if(cmbSearch.getSelectedItem().toString().equals("Nationality")){
            DisableAllChoice();
            cmbNationality.setEnabled(true);
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
    private void FillTable(){
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("Select ca_seqno,ca_name,ca_age,ca_nationality,ca_field,ca_school,ca_position from tblcandidate order by ca_seqno");
            DbConn.rs = DbConn.pstmt.executeQuery();
            tableCandidate.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
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
private void FillField(){
        cmbField.removeAllItems();
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("Select fi_description from tblfield order by fi_description");
            DbConn.rs = DbConn.pstmt.executeQuery();
            while (DbConn.rs.next()){
                cmbField.addItem(DbConn.rs.getString(1));
            }
            DbConn.pstmt.close();
            cmbField.setSelectedIndex(-1);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Fill Education");
        }
    }
    private void FillStatus(){
        cmbStatus.removeAllItems();
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("Select st_description from tblstatus order by st_description");
            DbConn.rs = DbConn.pstmt.executeQuery();
            while (DbConn.rs.next()){
                cmbStatus.addItem(DbConn.rs.getString(1));
            }
            DbConn.pstmt.close();
            cmbStatus.setSelectedIndex(-1);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Fill Education");
        }
    }
    private void FillEducation(){
        cmbEducational.removeAllItems();
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("Select ed_description from tbleducation order by ed_description");
            DbConn.rs = DbConn.pstmt.executeQuery();
            while (DbConn.rs.next()){
                cmbEducational.addItem(DbConn.rs.getString(1));
            }
            DbConn.pstmt.close();
            cmbEducational.setSelectedIndex(-1);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Fill Education");
        }
    }
    private void FillSource(){
        cmbSource.removeAllItems();
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("Select cs_description from tblcvsource order by cs_description");
            DbConn.rs = DbConn.pstmt.executeQuery();
            while (DbConn.rs.next()){
                cmbSource.addItem(DbConn.rs.getString(1));
            }
            DbConn.pstmt.close();
            cmbSource.setSelectedIndex(-1);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Fill Source");
        }
    }
    private void FillPosition(){
        cmbPosition.removeAllItems();
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("Select po_description from tblposition order by po_description");
            DbConn.rs = DbConn.pstmt.executeQuery();
            while (DbConn.rs.next()){
                cmbPosition.addItem(DbConn.rs.getString(1));
            }
            DbConn.pstmt.close();
            cmbPosition.setSelectedIndex(-1);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Fill Position");
        }
    }
    private void FillAge(){
        int i =20;
        cmbAge.removeAllItems();
        cmbAgeTo.removeAllItems();
        while(i<51){
            cmbAge.addItem(String.valueOf(i));
            cmbAgeTo.addItem(String.valueOf(i));
            i++;
        }
        cmbAge.setSelectedIndex(-1);
        cmbAgeTo.setSelectedIndex(-1);
    }
   
    private void FillNationality(){
        cmbNationality.removeAllItems();
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("select DISTINCT ca_nationality from tblcandidate order by ca_nationality");
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
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        GrpGender = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cmbAge = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        cmbNationality = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        cmbPosition = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        cmbEducational = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        cmbField = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        cmbSource = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        cmbStatus = new javax.swing.JComboBox<>();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableCandidate = new javax.swing.JTable();
        radMale = new javax.swing.JRadioButton();
        radFemale = new javax.swing.JRadioButton();
        radBoth = new javax.swing.JRadioButton();
        cmbAgeTo = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
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
        jLabel13 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        txtSearch = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(214, 214, 194));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("To:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 60, 20, 20));

        cmbAge.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(cmbAge, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 60, 60, -1));

        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Nationality:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 90, 100, 20));

        cmbNationality.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(cmbNationality, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 90, 230, -1));

        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Name Search:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 380, 100, 20));

        jLabel14.setForeground(new java.awt.Color(0, 0, 0));
        jLabel14.setText("Position Applied:");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 160, 130, 20));

        cmbPosition.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(cmbPosition, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 180, 230, -1));

        jLabel5.setFont(new java.awt.Font("Dialog", 2, 10)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 0, 0));
        jLabel5.setText("*Double click to view CV");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 610, 150, 20));

        cmbEducational.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(cmbEducational, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 330, 230, -1));

        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Field:");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 280, 90, 20));

        cmbField.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(cmbField, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 280, 230, -1));

        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("CV Source:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 250, 90, 20));

        cmbSource.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(cmbSource, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 250, 230, -1));

        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Status:");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 200, 120, 20));

        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(cmbStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 220, 230, -1));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 880, 10));

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 102, 0));
        jLabel4.setText("HR -> Candidate Search");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

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
        jScrollPane1.setViewportView(tableCandidate);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(13, 410, 870, 200));

        GrpGender.add(radMale);
        radMale.setText("MALE");
        jPanel1.add(radMale, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 130, 70, -1));

        GrpGender.add(radFemale);
        radFemale.setText("FEMALE");
        jPanel1.add(radFemale, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 130, 90, -1));

        GrpGender.add(radBoth);
        radBoth.setText("BOTH");
        jPanel1.add(radBoth, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 130, 70, -1));

        cmbAgeTo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbAgeTo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbAgeToActionPerformed(evt);
            }
        });
        jPanel1.add(cmbAgeTo, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 60, 60, -1));

        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Not Available");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 100, 20));

        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("From:");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 60, 40, 20));

        jButton1.setText("Search");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 60, -1, -1));

        jButton2.setText("View CV");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 110, -1, -1));

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel1.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 60, 10, 300));

        chkAll.setText("All");
        chkAll.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                chkAllMouseReleased(evt);
            }
        });
        jPanel1.add(chkAll, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));

        chkAge.setText("Age");
        chkAge.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                chkAgeMouseReleased(evt);
            }
        });
        jPanel1.add(chkAge, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, -1, -1));

        chkGender.setText("Gender");
        chkGender.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                chkGenderMouseReleased(evt);
            }
        });
        jPanel1.add(chkGender, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, -1, -1));

        chkNationality.setText("Nationality");
        chkNationality.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                chkNationalityMouseReleased(evt);
            }
        });
        jPanel1.add(chkNationality, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, -1, -1));

        chkPosition.setText("Position");
        chkPosition.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                chkPositionMouseReleased(evt);
            }
        });
        jPanel1.add(chkPosition, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, -1, -1));

        chkStatus.setText("Status");
        chkStatus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                chkStatusMouseReleased(evt);
            }
        });
        jPanel1.add(chkStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, -1, -1));

        chkCvSource.setText("CV Source");
        chkCvSource.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                chkCvSourceMouseReleased(evt);
            }
        });
        jPanel1.add(chkCvSource, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, -1, -1));

        chkField.setText("Field");
        chkField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                chkFieldMouseReleased(evt);
            }
        });
        jPanel1.add(chkField, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, -1, -1));

        chkBackground.setText("Background");
        chkBackground.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                chkBackgroundMouseReleased(evt);
            }
        });
        jPanel1.add(chkBackground, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, -1, -1));

        cmbSearch.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(cmbSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 90, 140, -1));

        lblCount.setForeground(new java.awt.Color(0, 0, 0));
        lblCount.setText("0");
        jPanel1.add(lblCount, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 260, 80, 20));

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel1.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 60, 10, 300));

        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setText("Search By:");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 70, 100, 20));

        jButton4.setText("Reset");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 100, -1, -1));
        jPanel1.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 250, 230, 10));

        jLabel15.setForeground(new java.awt.Color(0, 0, 0));
        jLabel15.setText("Educational Background:");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 310, 150, 20));

        jLabel16.setForeground(new java.awt.Color(0, 0, 0));
        jLabel16.setText("Age:");
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 60, 100, 20));

        jLabel17.setForeground(new java.awt.Color(0, 0, 0));
        jLabel17.setText("Total Count:");
        jPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 260, 80, 20));

        jButton3.setText("Print");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 160, -1, -1));
        jPanel1.add(txtSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 374, 300, 30));

        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("Gender:");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 120, 100, 20));

        jButton6.setText("Reset");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 372, -1, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void ViewCVMethod(){
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("select ca_cv from tblcandidate where ca_seqno=? and ca_cv is not null");
            int row = tableCandidate.getSelectedRow();
            int ba = tableCandidate.convertRowIndexToModel(row);
            DbConn.pstmt.setString(1, tableCandidate.getModel().getValueAt(ba, 0).toString());
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
   
       
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        //Age Selected
        if (cmbAge.isEnabled()){
             DbConn.SQLQuery = "Select ca_seqno,ca_name,ca_age,ca_nationality,ca_field,ca_school,"
                    + "ca_position from tblcandidate where ca_age between ? and ? order by ca_seqno";
             try{
                DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
                DbConn.pstmt.setString(1, cmbAge.getSelectedItem().toString());
                DbConn.pstmt.setString(2, cmbAgeTo.getSelectedItem().toString()); 
                DbConn.rs = DbConn.pstmt.executeQuery();
                tableCandidate.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
                DbConn.pstmt.close();
                SetHeaderTable();
             }catch(SQLException e){
                 JOptionPane.showMessageDialog(this, "Age Search");
             }
        }
        //Nationality
        if (cmbNationality.isEnabled()){
             DbConn.SQLQuery = "Select ca_seqno,ca_name,ca_age,ca_nationality,ca_field,ca_school,"
                    + "ca_position from tblcandidate where ca_nationality =? order by ca_seqno";
             try{
                DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
                DbConn.pstmt.setString(1, cmbNationality.getSelectedItem().toString());
                DbConn.rs = DbConn.pstmt.executeQuery();
                tableCandidate.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
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
                tableCandidate.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
                DbConn.pstmt.close();
                SetHeaderTable();
             }catch(SQLException e){
                 JOptionPane.showMessageDialog(this, "Gender Search");
             }
        }
        //Position
        if (cmbPosition.isEnabled()){
             DbConn.SQLQuery = "Select ca_seqno,ca_name,ca_age,ca_nationality,ca_field,ca_school,"
                    + "ca_position from tblcandidate where ca_position =? order by ca_seqno";
             try{
                DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
                DbConn.pstmt.setString(1, cmbPosition.getSelectedItem().toString());
                DbConn.rs = DbConn.pstmt.executeQuery();
                tableCandidate.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
                DbConn.pstmt.close();
                SetHeaderTable();
             }catch(SQLException e){
                 JOptionPane.showMessageDialog(this, "Position Search");
             }
        }
        //Status
        if (cmbStatus.isEnabled()){
             DbConn.SQLQuery = "Select ca_seqno,ca_name,ca_age,ca_nationality,ca_field,ca_school,"
                    + "ca_position from tblcandidate where ca_status =? order by ca_seqno";
             try{
                DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
                DbConn.pstmt.setString(1, cmbStatus.getSelectedItem().toString());
                DbConn.rs = DbConn.pstmt.executeQuery();
                tableCandidate.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
                DbConn.pstmt.close();
                SetHeaderTable();
             }catch(SQLException e){
                 JOptionPane.showMessageDialog(this, "Status Search");
             }
        }
        //Source
        if (cmbSource.isEnabled()){
             DbConn.SQLQuery = "Select ca_seqno,ca_name,ca_age,ca_nationality,ca_field,ca_school,"
                    + "ca_position from tblcandidate where ca_cvsource =? order by ca_seqno";
             try{
                DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
                DbConn.pstmt.setString(1, cmbSource.getSelectedItem().toString());
                DbConn.rs = DbConn.pstmt.executeQuery();
                tableCandidate.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
                DbConn.pstmt.close();
                SetHeaderTable();
             }catch(SQLException e){
                 JOptionPane.showMessageDialog(this, "CV Source Search");
             }
        }
        //Field
        if (cmbField.isEnabled()){
             DbConn.SQLQuery = "Select ca_seqno,ca_name,ca_age,ca_nationality,ca_field,ca_school,"
                    + "ca_position from tblcandidate where ca_field =? order by ca_seqno";
             try{
                DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
                DbConn.pstmt.setString(1, cmbField.getSelectedItem().toString());
                DbConn.rs = DbConn.pstmt.executeQuery();
                tableCandidate.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
                DbConn.pstmt.close();
                SetHeaderTable();
             }catch(SQLException e){
                 JOptionPane.showMessageDialog(this, "Field Search");
             }
        }
        //Educational Background
        if (cmbEducational.isEnabled()){
             DbConn.SQLQuery = "Select ca_seqno,ca_name,ca_age,ca_nationality,ca_field,ca_school,"
                    + "ca_position from tblcandidate where ca_education=? order by ca_seqno";
             try{
                DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
                DbConn.pstmt.setString(1, cmbEducational.getSelectedItem().toString());
                DbConn.rs = DbConn.pstmt.executeQuery();
                tableCandidate.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
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
//            tableCandidate.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
//            DbConn.pstmt.close();
//            SetHeaderTable();
//        }catch(SQLException e){
//            JOptionPane.showMessageDialog(this, e.getMessage());
//        }
    }//GEN-LAST:event_jButton1ActionPerformed
    private void EnableAllChoice(){
        cmbAge.setEnabled(true);
        cmbAgeTo.setEnabled(true);
        cmbEducational.setEnabled(true);
        cmbEducational.setEnabled(true);
        cmbField.setEnabled(true);
        cmbNationality.setEnabled(true);
        cmbPosition.setEnabled(true);
        cmbSource.setEnabled(true);
        cmbStatus.setEnabled(true);
        radBoth.setEnabled(true);
        radFemale.setEnabled(true);
        radMale.setEnabled(true);
    }
    private void DisableAllChoice(){
        cmbAge.setEnabled(false);
        cmbAgeTo.setEnabled(false);
        cmbEducational.setEnabled(false);
        cmbEducational.setEnabled(false);
        cmbField.setEnabled(false);
        cmbNationality.setEnabled(false);
        cmbPosition.setEnabled(false);
        cmbSource.setEnabled(false);
        cmbStatus.setEnabled(false);
        radBoth.setEnabled(false);
        radFemale.setEnabled(false);
        radMale.setEnabled(false);
    }
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

    private void chkNationalityMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chkNationalityMouseReleased
        if (chkNationality.isSelected()){
            cmbNationality.setEnabled(true);
            cmbNationality.setEnabled(true);
        }else{
            cmbNationality.setEnabled(false);
            cmbNationality.setEnabled(false);
        }
    }//GEN-LAST:event_chkNationalityMouseReleased

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

    private void cmbAgeToActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbAgeToActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbAgeToActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        FillTable();
        cmbSearch.setSelectedIndex(-1);
        DisableAllChoice();
        DisplayCount();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
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
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        FillTable();
    }//GEN-LAST:event_jButton6ActionPerformed

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
            java.util.logging.Logger.getLogger(frmCandidateSearch.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmCandidateSearch.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmCandidateSearch.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmCandidateSearch.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmCandidateSearch().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup GrpGender;
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
    private javax.swing.JComboBox<String> cmbAgeTo;
    private javax.swing.JComboBox<String> cmbEducational;
    private javax.swing.JComboBox<String> cmbField;
    private javax.swing.JComboBox<String> cmbNationality;
    private javax.swing.JComboBox<String> cmbPosition;
    private javax.swing.JComboBox<String> cmbSearch;
    private javax.swing.JComboBox<String> cmbSource;
    private javax.swing.JComboBox<String> cmbStatus;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JLabel lblCount;
    private javax.swing.JRadioButton radBoth;
    private javax.swing.JRadioButton radFemale;
    private javax.swing.JRadioButton radMale;
    private javax.swing.JTable tableCandidate;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
