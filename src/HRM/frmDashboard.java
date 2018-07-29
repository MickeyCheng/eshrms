
package HRM;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
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


public class frmDashboard extends javax.swing.JFrame {

DbConnection DbConn = new DbConnection();
String getApprovingPersonnel;
    public frmDashboard() {
        initComponents();
        DbConn.DoConnect();
        lblName.setText(DbConn.GetLoggedInUserName);
        lblEmpID.setText(DbConn.GetLoggedInID);
        lblDepartment.setText(DbConn.GetLoggedInDepartment);
        SetDate();
        FillLeaveType();
        FillTableLeave();
        FillDestinationClass();
        FillLeaveWorkLoad();
        FillTicketWL();
        FillTemplateReqWL();
        FillTableRequest();
        FillTableTemplateRequest();
        txtAreaLeaveRemarksWL.setEditable(false);
        setLocationRelativeTo(null);
        dateReturn.setEnabled(false);
        radAnnualTicket.setSelected(true);
        SetHeaderTables();
        //PanelWorkLoad.setVisible(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(frmDashboard.DISPOSE_ON_CLOSE);
        dateEndLeave.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                CalculateDays();
            }
        });
        dateStartLeave.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                CalculateDays();
            }
        });
    }
    private void SetHeaderTables(){
        //leave details
        tblLeaveApplication.getColumnModel().getColumn(0).setHeaderValue("FILED");
        tblLeaveApplication.getColumnModel().getColumn(1).setHeaderValue("DAYS");
        tblLeaveApplication.getColumnModel().getColumn(2).setHeaderValue("FROM");
        tblLeaveApplication.getColumnModel().getColumn(3).setHeaderValue("TO");
        tblLeaveApplication.getColumnModel().getColumn(4).setHeaderValue("TYPE");
        tblLeaveApplication.getColumnModel().getColumn(5).setHeaderValue("REMARKS");
        tblLeaveApplication.getColumnModel().getColumn(6).setHeaderValue("STATUS");
        
        //air ticket requests
        tableTicketRequest.getColumnModel().getColumn(0).setHeaderValue("ID");
        tableTicketRequest.getColumnModel().getColumn(1).setHeaderValue("DEPARTURE");
        tableTicketRequest.getColumnModel().getColumn(2).setHeaderValue("RETURN");
        tableTicketRequest.getColumnModel().getColumn(3).setHeaderValue("FILED");
        tableTicketRequest.getColumnModel().getColumn(4).setHeaderValue("DESTINATION");
        tableTicketRequest.getColumnModel().getColumn(5).setHeaderValue("TYPE");
        tableTicketRequest.getColumnModel().getColumn(6).setHeaderValue("STATUS");
        
        //WORKLOAD
        //LEAVE APPROVAL
        tblLeaveApproval.getColumnModel().getColumn(0).setHeaderValue("ID");
        tblLeaveApproval.getColumnModel().getColumn(1).setHeaderValue("NAME");
        tblLeaveApproval.getColumnModel().getColumn(2).setHeaderValue("FILED");
        tblLeaveApproval.getColumnModel().getColumn(3).setHeaderValue("DAYS");
        tblLeaveApproval.getColumnModel().getColumn(4).setHeaderValue("TYPE");
        tblLeaveApproval.getColumnModel().getColumn(5).setHeaderValue("REMARKS");
        //AIR TICKET REQ
        tableAirTicketWL.getColumnModel().getColumn(0).setHeaderValue("ID");
        tableAirTicketWL.getColumnModel().getColumn(1).setHeaderValue("NAME");
        tableAirTicketWL.getColumnModel().getColumn(2).setHeaderValue("FILED");
        tableAirTicketWL.getColumnModel().getColumn(3).setHeaderValue("TYPE");
        //TABLE REQUESTS
        tableRequestsWL.getColumnModel().getColumn(0).setHeaderValue("ID");
        tableRequestsWL.getColumnModel().getColumn(1).setHeaderValue("NAME");
        tableRequestsWL.getColumnModel().getColumn(2).setHeaderValue("FILED");
        tableRequestsWL.getColumnModel().getColumn(3).setHeaderValue("REQUEST");
        tableRequestsWL.getColumnModel().getColumn(4).setHeaderValue("STATUS");
    }
    
    private void  FillTableTemplateRequest(){
        try{
            DbConn.SQLQuery = "select tr_seqno,tr_requestdate,tr_request,tr_datefiled,tr_status "
                    + "from tbltemplaterequest where tr_empname =? and tr_empid=? order by tr_status";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.pstmt.setString(1, lblName.getText());
            DbConn.pstmt.setString(2, lblEmpID.getText());
            DbConn.rs = DbConn.pstmt.executeQuery();
            tableTemplateRequest.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
            DbConn.pstmt.close();
            System.out.println("test");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    private void FillTemplateReqWL(){
         try{
            DbConn.SQLQuery ="Select tr_seqno,tr_empname,tr_datefiled,tr_request,tr_status "
                    + "from tbltemplaterequest where tr_status=?";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.pstmt.setString(1, "Pending");
            DbConn.rs = DbConn.pstmt.executeQuery();
            tableRequestsWL.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
            DbConn.pstmt.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    private void FillTicketWL(){
        try{
            DbConn.SQLQuery ="Select tr_seqno,tr_empname,tr_datefiled,tr_type "
                    + "from tblticketrequest where tr_status=?";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.pstmt.setString(1, "Pending");
            DbConn.rs = DbConn.pstmt.executeQuery();
            tableAirTicketWL.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
            DbConn.pstmt.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    private void FillDestinationClass(){
    cmbDestination.removeAllItems();
    cmbTicketClass.removeAllItems();
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("Select td_description from tblticketdestination order by td_description");
            DbConn.rs = DbConn.pstmt.executeQuery();
            while (DbConn.rs.next()){
                cmbDestination.addItem(DbConn.rs.getString(1));
            }
            DbConn.pstmt.close();
            cmbDestination.setSelectedIndex(-1);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Fill RP");
        }
        
        cmbTicketClass.addItem("Economy");
        cmbTicketClass.addItem("First Class");
        cmbTicketClass.addItem("Business");
        cmbTicketClass.addItem("Any");
    }
    private void FillLeaveWorkLoad(){
        try{
            DbConn.SQLQuery ="Select le_seqno,le_empname,le_datefiled,le_days,le_leavetype,le_remarks "
                    + "from tblleave where le_approvedby=? and le_status=?";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.pstmt.setString(1, lblName.getText());
            DbConn.pstmt.setString(2, "Pending");
            DbConn.rs = DbConn.pstmt.executeQuery();
            tblLeaveApproval.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
            DbConn.pstmt.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    private void SetDate(){
        dateStartLeave.setDate(new Date());
        dateEndLeave.setDate(new Date());
        dateDeparture.setDate(new Date());
        dateReturn.setDate(new Date());
    }
    private void FillTableLeave(){
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("Select le_datefiled,le_days,le_datefrom,le_dateto,le_leavetype,le_remarks,"
                    + "le_status from tblleave where le_empname=? and le_empid=? order by le_datefiled");
            DbConn.pstmt.setString(1, lblName.getText());
            DbConn.pstmt.setString(2, lblEmpID.getText());
            DbConn.rs = DbConn.pstmt.executeQuery();
            tblLeaveApplication.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
            DbConn.pstmt.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    private void FillLeaveType(){
        cmbLeaveType.removeAllItems();
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("Select le_description from tblmasterleavetype order by le_description");
            DbConn.rs = DbConn.pstmt.executeQuery();
            while(DbConn.rs.next())
            {
                cmbLeaveType.addItem(DbConn.rs.getString(1));
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

        ticketGroup = new javax.swing.ButtonGroup();
        templateGroup = new javax.swing.ButtonGroup();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jSeparator7 = new javax.swing.JSeparator();
        jLabel28 = new javax.swing.JLabel();
        dateTemplateReq = new com.toedter.calendar.JDateChooser();
        radExperience = new javax.swing.JRadioButton();
        radSalaryCertificate = new javax.swing.JRadioButton();
        jLabel29 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        txtAreaTemplateComment = new javax.swing.JTextArea();
        jLabel30 = new javax.swing.JLabel();
        btnSaveTemplateReq = new javax.swing.JButton();
        jLabel32 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tableTemplateRequest = new javax.swing.JTable();
        jSeparator8 = new javax.swing.JSeparator();
        jButton8 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        lblEmpID = new javax.swing.JLabel();
        lblDepartment = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        PanelWorkLoad = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblLeaveApproval = new javax.swing.JTable();
        jSeparator3 = new javax.swing.JSeparator();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtAreaLeaveRemarksWL = new javax.swing.JTextArea();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        lblStartDateWL = new javax.swing.JLabel();
        lblEndDateWL = new javax.swing.JLabel();
        lblDaysWL = new javax.swing.JLabel();
        lblDateFiledWL = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        txtAreaLeaveApproverNotes = new javax.swing.JTextArea();
        jLabel8 = new javax.swing.JLabel();
        PanelAirTicketRequestWL = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tableAirTicketWL = new javax.swing.JTable();
        jSeparator4 = new javax.swing.JSeparator();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        lblDateFiledTickedWL = new javax.swing.JLabel();
        lblTicketTypeWL = new javax.swing.JLabel();
        lblReturnWL = new javax.swing.JLabel();
        lblDepartureWL = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        PanelAirTicketRequestWL1 = new javax.swing.JPanel();
        jSeparator9 = new javax.swing.JSeparator();
        jScrollPane11 = new javax.swing.JScrollPane();
        tableRequestsWL = new javax.swing.JTable();
        jScrollPane12 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jLabel37 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        dateEndLeave = new com.toedter.calendar.JDateChooser();
        jLabel9 = new javax.swing.JLabel();
        dateStartLeave = new com.toedter.calendar.JDateChooser();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel10 = new javax.swing.JLabel();
        chkHalfDay = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAreaReasonLeave = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        cmbLeaveType = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        lblDays = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblLeaveApplication = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jSeparator5 = new javax.swing.JSeparator();
        dateDeparture = new com.toedter.calendar.JDateChooser();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        dateReturn = new com.toedter.calendar.JDateChooser();
        jLabel20 = new javax.swing.JLabel();
        cmbDestination = new javax.swing.JComboBox<>();
        jLabel21 = new javax.swing.JLabel();
        cmbTicketClass = new javax.swing.JComboBox<>();
        chkRoundTrip = new javax.swing.JCheckBox();
        radSalaryDeduct = new javax.swing.JRadioButton();
        radAnnualTicket = new javax.swing.JRadioButton();
        btnSaveAirTicket = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tableTicketRequest = new javax.swing.JTable();
        jSeparator6 = new javax.swing.JSeparator();
        jButton5 = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();

        jPanel8.setBackground(new java.awt.Color(214, 214, 194));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel9.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(153, 153, 153)));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jSeparator7.setForeground(new java.awt.Color(255, 255, 255));
        jPanel9.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, 410, 10));

        jLabel28.setText("Comment:");
        jPanel9.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 120, 20));

        dateTemplateReq.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                dateTemplateReqMouseReleased(evt);
            }
        });
        jPanel9.add(dateTemplateReq, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, 260, 30));

        templateGroup.add(radExperience);
        radExperience.setText("Experience Letter");
        jPanel9.add(radExperience, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 60, -1, -1));

        templateGroup.add(radSalaryCertificate);
        radSalaryCertificate.setText("Salary Certificate");
        jPanel9.add(radSalaryCertificate, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 100, -1, -1));

        jLabel29.setText("Request Date:");
        jPanel9.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 120, 20));

        txtAreaTemplateComment.setColumns(20);
        txtAreaTemplateComment.setRows(5);
        jScrollPane9.setViewportView(txtAreaTemplateComment);

        jPanel9.add(jScrollPane9, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 170, 250, 90));

        jLabel30.setText("Letter Template:");
        jPanel9.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 120, 20));

        btnSaveTemplateReq.setBackground(new java.awt.Color(0, 0, 153));
        btnSaveTemplateReq.setForeground(new java.awt.Color(255, 255, 255));
        btnSaveTemplateReq.setText("Submit Request");
        btnSaveTemplateReq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveTemplateReqActionPerformed(evt);
            }
        });
        jPanel9.add(btnSaveTemplateReq, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 320, -1, -1));

        jPanel8.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 430, 400));

        jLabel32.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(0, 51, 51));
        jLabel32.setText("Template Requests");
        jPanel8.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 230, -1));

        jLabel31.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(0, 51, 51));
        jLabel31.setText("My Template Requests");
        jPanel8.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 20, 230, -1));

        jPanel14.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tableTemplateRequest.setModel(new javax.swing.table.DefaultTableModel(
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
        tableTemplateRequest.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableTemplateRequestMouseClicked(evt);
            }
        });
        jScrollPane10.setViewportView(tableTemplateRequest);

        jPanel14.add(jScrollPane10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 440, 290));

        jSeparator8.setForeground(new java.awt.Color(255, 255, 255));
        jPanel14.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, 430, 10));

        jButton8.setBackground(new java.awt.Color(153, 0, 0));
        jButton8.setForeground(new java.awt.Color(255, 255, 255));
        jButton8.setText("Cancel Request");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jPanel14.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 320, 120, -1));

        jPanel8.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 50, 460, 400));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(214, 214, 194));
        jPanel1.setForeground(new java.awt.Color(214, 214, 194));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 1510, 10));

        jLabel1.setForeground(new java.awt.Color(0, 51, 51));
        jLabel1.setText("Department:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 130, -1));

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 51));
        jLabel2.setText("My Dashboard");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 130, -1));

        jLabel3.setForeground(new java.awt.Color(0, 51, 51));
        jLabel3.setText("Name:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 130, -1));

        jLabel4.setForeground(new java.awt.Color(0, 51, 51));
        jLabel4.setText("ID:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 130, -1));

        lblName.setForeground(new java.awt.Color(0, 51, 51));
        lblName.setText("Joefrey Bartolome");
        jPanel1.add(lblName, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 50, 340, -1));

        lblEmpID.setForeground(new java.awt.Color(0, 51, 51));
        lblEmpID.setText("1");
        jPanel1.add(lblEmpID, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 70, 340, -1));

        lblDepartment.setForeground(new java.awt.Color(0, 51, 51));
        lblDepartment.setText("Information Technology");
        jPanel1.add(lblDepartment, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 90, 340, -1));

        PanelWorkLoad.setBackground(new java.awt.Color(214, 214, 194));
        PanelWorkLoad.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(153, 153, 153)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblLeaveApproval.setBackground(new java.awt.Color(214, 214, 194));
        tblLeaveApproval.setModel(new javax.swing.table.DefaultTableModel(
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
        tblLeaveApproval.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblLeaveApprovalMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblLeaveApproval);

        jPanel2.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 410, 170));

        jSeparator3.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, 410, 10));

        txtAreaLeaveRemarksWL.setColumns(20);
        txtAreaLeaveRemarksWL.setRows(5);
        jScrollPane4.setViewportView(txtAreaLeaveRemarksWL);

        jPanel2.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 190, 170, 100));

        jLabel13.setText("Start Date:");
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 80, 20));

        jLabel14.setText("End Date:");
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 80, 20));

        jLabel15.setText("Date Filed:");
        jPanel2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, -1, -1));

        jLabel16.setText("Days:");
        jPanel2.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, -1, -1));

        lblStartDateWL.setText("Start Date:");
        jPanel2.add(lblStartDateWL, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 180, 80, 20));

        lblEndDateWL.setText("End Date:");
        jPanel2.add(lblEndDateWL, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 210, 80, 20));

        lblDaysWL.setText("Days:");
        jPanel2.add(lblDaysWL, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 240, -1, -1));

        lblDateFiledWL.setText("Date Filed:");
        jPanel2.add(lblDateFiledWL, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 270, -1, -1));

        jButton2.setBackground(new java.awt.Color(153, 0, 0));
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Reject");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 360, 80, -1));

        jButton4.setBackground(new java.awt.Color(51, 153, 0));
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Approve");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 320, 80, -1));

        txtAreaLeaveApproverNotes.setColumns(20);
        txtAreaLeaveApproverNotes.setRows(5);
        jScrollPane6.setViewportView(txtAreaLeaveApproverNotes);

        jPanel2.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, 320, 70));

        PanelWorkLoad.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 430, 400));

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 51, 51));
        jLabel8.setText("Air  Ticket Requests");
        PanelWorkLoad.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 20, 230, -1));

        PanelAirTicketRequestWL.setBackground(new java.awt.Color(214, 214, 194));
        PanelAirTicketRequestWL.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(153, 153, 153)));
        PanelAirTicketRequestWL.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tableAirTicketWL.setBackground(new java.awt.Color(214, 214, 194));
        tableAirTicketWL.setModel(new javax.swing.table.DefaultTableModel(
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
        tableAirTicketWL.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableAirTicketWLMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tableAirTicketWL);

        PanelAirTicketRequestWL.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 380, 170));

        jSeparator4.setForeground(new java.awt.Color(255, 255, 255));
        PanelAirTicketRequestWL.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, 380, 10));

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane7.setViewportView(jTextArea2);

        PanelAirTicketRequestWL.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, 270, 70));

        jButton6.setBackground(new java.awt.Color(51, 153, 0));
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("Approve");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        PanelAirTicketRequestWL.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 320, 80, -1));

        jButton7.setBackground(new java.awt.Color(153, 0, 0));
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("Reject");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        PanelAirTicketRequestWL.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 360, 80, -1));

        jLabel24.setForeground(new java.awt.Color(0, 0, 0));
        jLabel24.setText("Departure:");
        PanelAirTicketRequestWL.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 80, 20));

        jLabel25.setForeground(new java.awt.Color(0, 0, 0));
        jLabel25.setText("Return:");
        PanelAirTicketRequestWL.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 80, 20));

        jLabel26.setForeground(new java.awt.Color(0, 0, 0));
        jLabel26.setText("Type:");
        PanelAirTicketRequestWL.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, -1, -1));

        jLabel27.setForeground(new java.awt.Color(0, 0, 0));
        jLabel27.setText("Date Filed:");
        PanelAirTicketRequestWL.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, -1, -1));

        lblDateFiledTickedWL.setForeground(new java.awt.Color(0, 0, 0));
        lblDateFiledTickedWL.setText("Date Filed:");
        PanelAirTicketRequestWL.add(lblDateFiledTickedWL, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 280, -1, -1));

        lblTicketTypeWL.setForeground(new java.awt.Color(0, 0, 0));
        lblTicketTypeWL.setText("Days:");
        PanelAirTicketRequestWL.add(lblTicketTypeWL, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 250, -1, -1));

        lblReturnWL.setForeground(new java.awt.Color(0, 0, 0));
        lblReturnWL.setText("End Date:");
        PanelAirTicketRequestWL.add(lblReturnWL, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 220, 80, 20));

        lblDepartureWL.setForeground(new java.awt.Color(0, 0, 0));
        lblDepartureWL.setText("Start Date:");
        PanelAirTicketRequestWL.add(lblDepartureWL, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 190, 80, 20));

        PanelWorkLoad.add(PanelAirTicketRequestWL, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 50, 400, 400));

        jLabel17.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 51, 51));
        jLabel17.setText("Leave Approval");
        PanelWorkLoad.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 230, -1));

        PanelAirTicketRequestWL1.setBackground(new java.awt.Color(214, 214, 194));
        PanelAirTicketRequestWL1.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(153, 153, 153)));
        PanelAirTicketRequestWL1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jSeparator9.setForeground(new java.awt.Color(255, 255, 255));
        PanelAirTicketRequestWL1.add(jSeparator9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, 380, 10));

        tableRequestsWL.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane11.setViewportView(tableRequestsWL);

        PanelAirTicketRequestWL1.add(jScrollPane11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 380, 290));

        jTextArea3.setColumns(20);
        jTextArea3.setRows(5);
        jScrollPane12.setViewportView(jTextArea3);

        PanelAirTicketRequestWL1.add(jScrollPane12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, 270, 70));

        jButton9.setBackground(new java.awt.Color(51, 153, 0));
        jButton9.setForeground(new java.awt.Color(255, 255, 255));
        jButton9.setText("Approve");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        PanelAirTicketRequestWL1.add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 320, 80, -1));

        jButton10.setBackground(new java.awt.Color(153, 0, 0));
        jButton10.setForeground(new java.awt.Color(255, 255, 255));
        jButton10.setText("Reject");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        PanelAirTicketRequestWL1.add(jButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 360, 80, -1));

        PanelWorkLoad.add(PanelAirTicketRequestWL1, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 50, 400, 400));

        jLabel37.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(0, 51, 51));
        jLabel37.setText("Template Requests");
        PanelWorkLoad.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 20, 230, -1));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PanelWorkLoad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PanelWorkLoad, javax.swing.GroupLayout.DEFAULT_SIZE, 462, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("My Workload", jPanel4);

        jPanel5.setBackground(new java.awt.Color(214, 214, 194));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setText("Type:");
        jPanel6.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 80, 20));

        dateEndLeave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                dateEndLeaveMouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                dateEndLeaveMouseReleased(evt);
            }
        });
        jPanel6.add(dateEndLeave, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 50, 240, 30));

        jLabel9.setText("Reason:");
        jPanel6.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 80, 30));

        dateStartLeave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                dateStartLeaveMouseReleased(evt);
            }
        });
        jPanel6.add(dateStartLeave, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, 240, 30));

        jSeparator2.setForeground(new java.awt.Color(255, 255, 255));
        jPanel6.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, 320, 10));

        jLabel10.setText("Start Date:");
        jPanel6.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 80, 30));

        chkHalfDay.setText("Half-Day");
        chkHalfDay.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chkHalfDayMouseClicked(evt);
            }
        });
        jPanel6.add(chkHalfDay, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 100, 90, -1));

        txtAreaReasonLeave.setColumns(20);
        txtAreaReasonLeave.setRows(5);
        jScrollPane1.setViewportView(txtAreaReasonLeave);

        jPanel6.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 310, -1));

        jButton1.setBackground(new java.awt.Color(0, 0, 153));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Submit");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel6.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 350, -1, -1));

        jLabel11.setText("End Date:");
        jPanel6.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 80, 30));

        cmbLeaveType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel6.add(cmbLeaveType, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 180, 220, -1));

        jLabel12.setText("Days:");
        jPanel6.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, -1, -1));

        lblDays.setText("jLabel13");
        jPanel6.add(lblDays, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 130, 100, -1));

        jPanel5.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 340, 390));

        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblLeaveApplication.setBackground(new java.awt.Color(214, 214, 194));
        tblLeaveApplication.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblLeaveApplication);

        jPanel7.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(13, 10, 520, 370));

        jPanel5.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 50, 540, 390));

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 51, 51));
        jLabel5.setText("Leave Application");
        jPanel5.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 230, -1));

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 51, 51));
        jLabel6.setText("Leave History");
        jPanel5.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 20, 230, -1));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Leave Details", jPanel3);

        jPanel10.setBackground(new java.awt.Color(214, 214, 194));
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel11.setBackground(new java.awt.Color(214, 214, 194));
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel10.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 6, -1, -1));

        jPanel12.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(153, 153, 153)));
        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jSeparator5.setForeground(new java.awt.Color(255, 255, 255));
        jPanel12.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 410, 10));

        dateDeparture.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                dateDepartureMouseReleased(evt);
            }
        });
        jPanel12.add(dateDeparture, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, 240, 30));

        jLabel18.setText("Departure Date:");
        jPanel12.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 120, 20));

        jLabel19.setText("Destination:");
        jPanel12.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 120, 20));

        dateReturn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                dateReturnMouseReleased(evt);
            }
        });
        jPanel12.add(dateReturn, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 50, 240, 30));

        jLabel20.setText("Return Date:");
        jPanel12.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 120, 20));

        cmbDestination.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel12.add(cmbDestination, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 130, 240, -1));

        jLabel21.setText("Ticket Class:");
        jPanel12.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 120, 20));

        cmbTicketClass.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel12.add(cmbTicketClass, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 170, 240, -1));

        chkRoundTrip.setText("Round-trip");
        chkRoundTrip.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chkRoundTripStateChanged(evt);
            }
        });
        chkRoundTrip.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chkRoundTripMouseClicked(evt);
            }
        });
        chkRoundTrip.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                chkRoundTripPropertyChange(evt);
            }
        });
        jPanel12.add(chkRoundTrip, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 90, -1, -1));

        ticketGroup.add(radSalaryDeduct);
        radSalaryDeduct.setText("Salary Deduction");
        jPanel12.add(radSalaryDeduct, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 210, -1, -1));

        ticketGroup.add(radAnnualTicket);
        radAnnualTicket.setText("Annual Ticket");
        jPanel12.add(radAnnualTicket, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 210, -1, -1));

        btnSaveAirTicket.setBackground(new java.awt.Color(0, 0, 153));
        btnSaveAirTicket.setForeground(new java.awt.Color(255, 255, 255));
        btnSaveAirTicket.setText("Submit");
        btnSaveAirTicket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveAirTicketActionPerformed(evt);
            }
        });
        jPanel12.add(btnSaveAirTicket, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 270, -1, -1));

        jPanel10.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 430, 400));

        jLabel22.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(0, 51, 51));
        jLabel22.setText("Requests");
        jPanel10.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 20, 230, -1));

        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tableTicketRequest.setModel(new javax.swing.table.DefaultTableModel(
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
        tableTicketRequest.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableTicketRequestMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(tableTicketRequest);

        jPanel13.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 440, 230));

        jSeparator6.setForeground(new java.awt.Color(255, 255, 255));
        jPanel13.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 430, 10));

        jButton5.setBackground(new java.awt.Color(153, 0, 0));
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Cancel Request");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel13.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 260, 120, -1));

        jPanel10.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 50, 460, 400));

        jLabel23.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(0, 51, 51));
        jLabel23.setText("New Request");
        jPanel10.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 230, -1));

        jTabbedPane1.addTab("Air Ticket Request", jPanel10);

        jPanel1.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 1510, 500));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1533, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void dateEndLeaveMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dateEndLeaveMouseReleased

    }//GEN-LAST:event_dateEndLeaveMouseReleased

    private void dateStartLeaveMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dateStartLeaveMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_dateStartLeaveMouseReleased
    private void GetApprover(){
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("Select em_approvingpersonnel from tblemployeeprofile where em_name=? "
                    + "and em_seqno=?");
            DbConn.pstmt.setString(1, lblName.getText());
            DbConn.pstmt.setString(2, lblEmpID.getText());
            DbConn.rs = DbConn.pstmt.executeQuery();
            if (DbConn.rs.next()){
                getApprovingPersonnel = DbConn.rs.getString(1);
            }
            DbConn.pstmt.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        GetApprover();
        try{
            DbConn.SQLQuery = "insert into tblleave (le_empname,le_empid,le_datefiled,le_days,le_datefrom,le_dateto,le_leavetype,"
                    + "le_remarks,le_addby,le_approvedby,le_status) values (?,?,?,?,?,?,?,?,?,?,?)";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.pstmt.setString(1, lblName.getText());
            DbConn.pstmt.setString(2, lblEmpID.getText());
            DbConn.pstmt.setString(3, String.valueOf(DbConn.sdfDate.format(new Date())));
            DbConn.pstmt.setString(4, lblDays.getText());
            DbConn.pstmt.setString(5, String.valueOf(DbConn.sdfDate.format(dateStartLeave.getDate())));
            DbConn.pstmt.setString(6, String.valueOf(DbConn.sdfDate.format(dateEndLeave.getDate())));
            DbConn.pstmt.setString(7, cmbLeaveType.getSelectedItem().toString());
            DbConn.pstmt.setString(8, txtAreaReasonLeave.getText());
            DbConn.pstmt.setString(9, lblName.getText());
            DbConn.pstmt.setString(10, getApprovingPersonnel);
            DbConn.pstmt.setString(11, "Pending");
            DbConn.pstmt.execute();
            DbConn.pstmt.close();
            JOptionPane.showMessageDialog(this, "Leave Submitted");
            ClearTexts();
            FillTableLeave();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_jButton1ActionPerformed
    private void CalculateDays(){
        long difference = dateEndLeave.getDate().getTime() - dateStartLeave.getDate().getTime();
        double DiffInt = (double)(difference/1000/60/60/24) +1.0;
        lblDays.setText(String.valueOf(DiffInt));
    }
    private void chkHalfDayMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chkHalfDayMouseClicked
        if (chkHalfDay.isSelected()){
            lblDays.setText("0.5");
        }else{
            lblDays.setText("0.0");
        }
    }//GEN-LAST:event_chkHalfDayMouseClicked

    private void dateEndLeaveMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dateEndLeaveMouseExited

    }//GEN-LAST:event_dateEndLeaveMouseExited

    private void tblLeaveApprovalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblLeaveApprovalMouseClicked
        int row = tblLeaveApproval.getSelectedRow();
        int ba = tblLeaveApproval.convertRowIndexToModel(row);
        
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("Select * from tblleave where le_seqno=? and le_empname=?");
            DbConn.pstmt.setString(1, tblLeaveApproval.getModel().getValueAt(ba, 0).toString());
            DbConn.pstmt.setString(2, tblLeaveApproval.getModel().getValueAt(ba, 1).toString());
            DbConn.rs = DbConn.pstmt.executeQuery();
            if (DbConn.rs.next()){
                lblDateFiledWL.setText(DbConn.rs.getString("le_datefiled"));
                lblStartDateWL.setText(DbConn.rs.getString("le_datefrom"));
                lblEndDateWL.setText(DbConn.rs.getString("le_dateto"));
                lblDaysWL.setText(DbConn.rs.getString("le_days"));
                txtAreaLeaveRemarksWL.setText(DbConn.rs.getString("le_remarks"));
            }
            DbConn.pstmt.close();
        }catch(SQLException e){
            e.getMessage();
        }
        
    }//GEN-LAST:event_tblLeaveApprovalMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        int row = tblLeaveApproval.getSelectedRow();
        int ba = tblLeaveApproval.convertRowIndexToModel(row);
        
        try{
            DbConn.SQLQuery="update tblleave set le_status=?,le_comments=? where le_seqno=? and le_empname=?";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.pstmt.setString(1, "Approved");
            DbConn.pstmt.setString(2, txtAreaLeaveApproverNotes.getText());
            DbConn.pstmt.setString(3, tblLeaveApproval.getModel().getValueAt(ba, 0).toString());
            DbConn.pstmt.setString(4, tblLeaveApproval.getModel().getValueAt(ba, 1).toString());
            DbConn.pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Leave Approved");
            ClearTexts();
            FillLeaveWorkLoad();
            txtAreaLeaveApproverNotes.setText("");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void dateDepartureMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dateDepartureMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_dateDepartureMouseReleased

    private void dateReturnMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dateReturnMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_dateReturnMouseReleased

    private void chkRoundTripPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_chkRoundTripPropertyChange
        
    }//GEN-LAST:event_chkRoundTripPropertyChange

    private void chkRoundTripStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chkRoundTripStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_chkRoundTripStateChanged

    private void chkRoundTripMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chkRoundTripMouseClicked
        if(chkRoundTrip.isSelected()){
            dateReturn.setEnabled(true);
        }else{
            dateReturn.setEnabled(false);
        }
    }//GEN-LAST:event_chkRoundTripMouseClicked

    private void btnSaveAirTicketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveAirTicketActionPerformed
        GetApprover();
        try{
            DbConn.SQLQuery="insert into tblticketrequest (tr_empname,tr_empid,tr_empdepartment,tr_departure,tr_return,"
                    + "tr_roundtrip,tr_destination,tr_ticketclass,tr_type,tr_datefiled,tr_reviewedby,tr_status) "
                    + "values (?,?,?,?,?,?,?,?,?,?,?,?)";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.pstmt.setString(1, lblName.getText());
            DbConn.pstmt.setString(2, lblEmpID.getText());
            DbConn.pstmt.setString(3, lblDepartment.getText());
            DbConn.pstmt.setString(4, DbConn.sdfDate.format(dateDeparture.getDate()));
            DbConn.pstmt.setString(5, DbConn.sdfDate.format(dateReturn.getDate()));
            String GetRoundTrip= "Yes";
            if (!chkRoundTrip.isSelected()){
                GetRoundTrip="No";
            }
            DbConn.pstmt.setString(6, GetRoundTrip);
            DbConn.pstmt.setString(7, cmbDestination.getSelectedItem().toString());
            DbConn.pstmt.setString(8, cmbTicketClass.getSelectedItem().toString());
            String GetTicketType = "Annual Leave";
            if (!radAnnualTicket.isSelected()){
                GetTicketType="Salary Deduction";
            }
            DbConn.pstmt.setString(9, GetTicketType);
            DbConn.pstmt.setString(10, DbConn.sdfDate.format(new Date()));
            DbConn.pstmt.setString(11, getApprovingPersonnel);
            DbConn.pstmt.setString(12, "Pending");
            DbConn.pstmt.execute();
            DbConn.pstmt.close();
            ClearTicketField();
            JOptionPane.showMessageDialog(this, "Request Submitted");
            FillTableRequest();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_btnSaveAirTicketActionPerformed
    private void FillTableRequest(){
        try{
            DbConn.SQLQuery="select tr_seqno,tr_departure,tr_return,tr_datefiled,tr_destination,tr_type,tr_status "
                    + "from tblticketrequest where tr_empname=? and tr_empid=? order by tr_datefiled";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.pstmt.setString(1, DbConn.GetLoggedInUserName);
            DbConn.pstmt.setString(2, DbConn.GetLoggedInID);
            DbConn.rs = DbConn.pstmt.executeQuery();
            tableTicketRequest.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
            DbConn.pstmt.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        int row = tableTicketRequest.getSelectedRow();
        int ba = tableTicketRequest.convertRowIndexToModel(row);
        int itemSelected = JOptionPane.showConfirmDialog(this,"Cancel Air Ticket Request " 
                + tableTicketRequest.getModel().getValueAt(ba, 3).toString() + "?", "Air Ticket",JOptionPane.YES_NO_OPTION );
       if (itemSelected == JOptionPane.YES_OPTION){ 
            try{
                DbConn.SQLQuery="update tblticketrequest set tr_status=? where tr_seqno =?";
                DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
                DbConn.pstmt.setString(1, "Cancelled");
                DbConn.pstmt.setString(2, tableTicketRequest.getModel().getValueAt(ba, 0).toString());
                DbConn.pstmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Air Ticket Request Cancelled");
                ClearTexts();
                FillTableRequest();
            }catch(SQLException e){
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
       }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void tableTicketRequestMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableTicketRequestMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tableTicketRequestMouseClicked

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton7ActionPerformed

    private void tableAirTicketWLMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableAirTicketWLMouseClicked
        int row = tableAirTicketWL.getSelectedRow();
        int ba = tableAirTicketWL.convertRowIndexToModel(row);
        try{
            DbConn.SQLQuery = "select * from tblticketrequest where tr_seqno =?";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.pstmt.setString(1, tableAirTicketWL.getModel().getValueAt(ba, 0).toString());
            DbConn.rs = DbConn.pstmt.executeQuery();
            if (DbConn.rs.next()){
                lblDepartureWL.setText(DbConn.rs.getString("tr_departure"));
                lblReturnWL.setText(DbConn.rs.getString("tr_return"));
                lblTicketTypeWL.setText(DbConn.rs.getString("tr_type"));
                lblDateFiledTickedWL.setText(DbConn.rs.getString("tr_datefiled"));
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_tableAirTicketWLMouseClicked

    private void btnSaveTemplateReqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveTemplateReqActionPerformed
        
        try{
            DbConn.SQLQuery = "insert into tbltemplaterequest (tr_empname,tr_empid,tr_empdepartment,tr_requestdate,"
                    + "tr_request,tr_comment,tr_datefiled,tr_status) values (?,?,?,?,?,?,?,?)";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.pstmt.setString(1, lblName.getText());
            DbConn.pstmt.setString(2, lblEmpID.getText());
            DbConn.pstmt.setString(3, lblDepartment.getText());
            DbConn.pstmt.setString(4, DbConn.sdfDate.format(dateTemplateReq.getDate()));
            String GetRequest = "Experience Letter";
            if (!radExperience.isSelected()){
                GetRequest = "Salary Certificate";
            }
            DbConn.pstmt.setString(5, GetRequest);
            DbConn.pstmt.setString(6, txtAreaTemplateComment.getText());
            DbConn.pstmt.setString(7, DbConn.sdfDate.format(new Date()));
            DbConn.pstmt.setString(8, "Pending");
            DbConn.pstmt.execute();
            DbConn.pstmt.close();
            JOptionPane.showMessageDialog(this, "Request Submitted");
            FillTableTemplateRequest();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_btnSaveTemplateReqActionPerformed

    private void dateTemplateReqMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dateTemplateReqMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_dateTemplateReqMouseReleased

    private void tableTemplateRequestMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableTemplateRequestMouseClicked
        
    }//GEN-LAST:event_tableTemplateRequestMouseClicked

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        int itemSelected = JOptionPane.showConfirmDialog(this, "Template Request","Cancel Selected Template Request?",JOptionPane.YES_NO_OPTION);
        if (itemSelected == JOptionPane.YES_OPTION){
            int row = tableTemplateRequest.getSelectedRow();
            int ba = tableTemplateRequest.convertRowIndexToModel(row);
            try{
                DbConn.SQLQuery = "update tbltemplaterequest set tr_status=? where tr_seqno=?";
                DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
                DbConn.pstmt.setString(1, "Cancelled");
                DbConn.pstmt.setString(2, tableTicketRequest.getModel().getValueAt(ba, 0).toString());
                DbConn.pstmt.executeUpdate();
                DbConn.pstmt.close();
                JOptionPane.showMessageDialog(this, "Template Request Cancelled");
                FillTableTemplateRequest();
            }catch(SQLException e){
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
//        int row = tableRequestsWL.getSelectedRow();
//        int ba = tableRequestsWL.convertRowIndexToModel(row);
//        try{
//            DbConn.SQLQuery="update tbltemplaterequest set tr_status=? where tr_seqno=?";
//            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
//            DbConn.pstmt.setString(1, "Approved");
//            DbConn.pstmt.setString(2, tableRequestsWL.getModel().getValueAt(ba, 0).toString());
//            DbConn.pstmt.executeUpdate();
//            DbConn.pstmt.close();
//            JOptionPane.showMessageDialog(this, "Request status updated successfully");
//            FillTemplateReqWL();
//            FillTableTemplateRequest();
            PrintSalaryCertificate();
//        }catch(SQLException e){
//            JOptionPane.showMessageDialog(this, e.getMessage());
//        }
    }//GEN-LAST:event_jButton9ActionPerformed
    private void PrintSalaryCertificate(){
            int row = tableRequestsWL.getSelectedRow();
            int ba = tableRequestsWL.convertRowIndexToModel(row);
            Map param = new HashMap();
            param.put("GetEmpName", tableRequestsWL.getModel().getValueAt(ba, 1).toString());
            try{                
                //view letter offer
                DbConn.conn.close();
                Class.forName("com.mysql.jdbc.Driver");
                DbConn.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbeshr?autoReconnect=true","root","root");
                JasperDesign jd = JRXmlLoader.load(new File("src\\HRReports\\reportSalaryCertificate.jrxml"));
                JasperReport jr = JasperCompileManager.compileReport(jd);
                JasperPrint jp = JasperFillManager.fillReport(jr, param,DbConn.conn);
                JasperViewer.viewReport(jp,false);
            }catch(ClassNotFoundException | SQLException | JRException e){
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
    }
    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton10ActionPerformed
    private void ClearTicketField(){
        dateDeparture.setDate(new Date());
        dateReturn.setDate(new Date());
        chkRoundTrip.setSelected(false);
        cmbDestination.setSelectedIndex(-1);
        cmbTicketClass.setSelectedIndex(-1);
        
    }
    private void ClearTexts(){
        dateStartLeave.setDate(new Date());
        dateEndLeave.setDate(new Date());
        chkHalfDay.setSelected(false);
        cmbLeaveType.setSelectedIndex(-1);
        lblDays.setText("");
    }
    
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
            java.util.logging.Logger.getLogger(frmDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmDashboard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelAirTicketRequestWL;
    private javax.swing.JPanel PanelAirTicketRequestWL1;
    private javax.swing.JPanel PanelWorkLoad;
    private javax.swing.JButton btnSaveAirTicket;
    private javax.swing.JButton btnSaveTemplateReq;
    private javax.swing.JCheckBox chkHalfDay;
    private javax.swing.JCheckBox chkRoundTrip;
    private javax.swing.JComboBox<String> cmbDestination;
    private javax.swing.JComboBox<String> cmbLeaveType;
    private javax.swing.JComboBox<String> cmbTicketClass;
    private com.toedter.calendar.JDateChooser dateDeparture;
    private com.toedter.calendar.JDateChooser dateEndLeave;
    private com.toedter.calendar.JDateChooser dateReturn;
    private com.toedter.calendar.JDateChooser dateStartLeave;
    private com.toedter.calendar.JDateChooser dateTemplateReq;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
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
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
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
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JLabel lblDateFiledTickedWL;
    private javax.swing.JLabel lblDateFiledWL;
    private javax.swing.JLabel lblDays;
    private javax.swing.JLabel lblDaysWL;
    private javax.swing.JLabel lblDepartment;
    private javax.swing.JLabel lblDepartureWL;
    private javax.swing.JLabel lblEmpID;
    private javax.swing.JLabel lblEndDateWL;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblReturnWL;
    private javax.swing.JLabel lblStartDateWL;
    private javax.swing.JLabel lblTicketTypeWL;
    private javax.swing.JRadioButton radAnnualTicket;
    private javax.swing.JRadioButton radExperience;
    private javax.swing.JRadioButton radSalaryCertificate;
    private javax.swing.JRadioButton radSalaryDeduct;
    private javax.swing.JTable tableAirTicketWL;
    private javax.swing.JTable tableRequestsWL;
    private javax.swing.JTable tableTemplateRequest;
    private javax.swing.JTable tableTicketRequest;
    private javax.swing.JTable tblLeaveApplication;
    private javax.swing.JTable tblLeaveApproval;
    private javax.swing.ButtonGroup templateGroup;
    private javax.swing.ButtonGroup ticketGroup;
    private javax.swing.JTextArea txtAreaLeaveApproverNotes;
    private javax.swing.JTextArea txtAreaLeaveRemarksWL;
    private javax.swing.JTextArea txtAreaReasonLeave;
    private javax.swing.JTextArea txtAreaTemplateComment;
    // End of variables declaration//GEN-END:variables
}
