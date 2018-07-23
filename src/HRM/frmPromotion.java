
package HRM;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;

public class frmPromotion extends javax.swing.JFrame {
DbConnection DbConn = new DbConnection();
JComboBox cmbAllowance = new JComboBox();
    public frmPromotion() {
        initComponents();
        DbConn.DoConnect();
        FillEmployeeCombo();
        FillProposedBy();
        FillPromoteStatus();
        FillProposedDesignation();
        FillDepartment();
        setLocationRelativeTo(null);
        datePropose.setDate(new Date());
        cmbEmployeeName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FillTableAllowance();
                FillDetails();
            }
        });
    }
    private void FillDepartment(){
        cmbProposedDeparment.removeAllItems();
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("Select de_description from tbldepartment order by de_description");
            DbConn.rs = DbConn.pstmt.executeQuery();
            while (DbConn.rs.next()){
                cmbProposedDeparment.addItem(DbConn.rs.getString(1));
            }
            DbConn.pstmt.close();
            cmbProposedDeparment.setSelectedIndex(-1);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Fill Department");
        }
    }
    private void FillPromoteStatus(){
        cmbStatus.removeAllItems();
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("Select ps_description from tblpromotestatus order by ps_description");
            DbConn.rs = DbConn.pstmt.executeQuery();
            while (DbConn.rs.next()){
                cmbStatus.addItem(DbConn.rs.getString(1));
            }
            DbConn.pstmt.close();
            cmbStatus.setSelectedIndex(-1);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Fill Type");
        }
    }
    private void FillProposedDesignation(){
        cmbProposedDesignation.removeAllItems();
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("Select de_description from tbldesignation order by de_description");
            DbConn.rs = DbConn.pstmt.executeQuery();
            while (DbConn.rs.next()){
                cmbProposedDesignation.addItem(DbConn.rs.getString(1));
            }
            DbConn.pstmt.close();
            cmbProposedDesignation.setSelectedIndex(-1);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Proposed Designation");
        }
    }
    private void FillDetails(){
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("select * from tblemployeeprofile where em_name=?");
            DbConn.pstmt.setString(1, cmbEmployeeName.getSelectedItem().toString());
            DbConn.rs = DbConn.pstmt.executeQuery();
            if (DbConn.rs.next()){
                lblEmployeeID.setText(String.valueOf(DbConn.rs.getInt("em_seqno")));
                lblEmployeeDepartment.setText(DbConn.rs.getString("em_department"));
                lblCurrentDesignation.setText(DbConn.rs.getString("em_designation"));
            }
            DbConn.pstmt.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Employee Details");
        }
    }
    private void FillEmployeeCombo(){
        cmbEmployeeName.removeAllItems();
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("select em_name from tblemployeeprofile order by em_name");
            DbConn.rs = DbConn.pstmt.executeQuery();
            while (DbConn.rs.next()){
                cmbEmployeeName.addItem(DbConn.rs.getString(1));
            }
            DbConn.pstmt.close();
            cmbEmployeeName.setSelectedIndex(-1);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Employee Drop Down");
        }
    }
    private void FillProposedBy(){
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
    private void FillTableAllowance(){
     DbConn.GetEmpIdForProfile(cmbEmployeeName.getSelectedItem().toString());
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("Select al_type,al_amount from tblallowance where al_empname =? and al_empid=?");
            DbConn.pstmt.setString(1, cmbEmployeeName.getSelectedItem().toString());
            DbConn.pstmt.setString(2, String.valueOf(DbConn.GetEmpIDToSave));
            DbConn.rs = DbConn.pstmt.executeQuery();
            tableAllowance.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
            DbConn.pstmt.close();
            tableAllowance.getColumnModel().getColumn(0).setHeaderValue("TYPE");
            tableAllowance.getColumnModel().getColumn(1).setHeaderValue("AMOUNT");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Allowance Table");
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
        jLabel2 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btnNew = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        cmbEmployeeName = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        lblEmployeeID = new javax.swing.JLabel();
        lblEmployeeDepartment = new javax.swing.JLabel();
        lblCurrentDesignation = new javax.swing.JLabel();
        cmbProposedDesignation = new javax.swing.JComboBox<>();
        cmbProposedBy = new javax.swing.JComboBox<>();
        datePropose = new com.toedter.calendar.JDateChooser();
        btnSubmit = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        cmbStatus = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        cmbProposedDeparment = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableAllowance = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(214, 214, 194));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 0));
        jLabel2.setText("Promotion Proposal");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 690, 20));

        jPanel2.setBackground(new java.awt.Color(214, 214, 194));
        jPanel2.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(153, 153, 153)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel16.setForeground(new java.awt.Color(0, 0, 0));
        jLabel16.setText("Employee:");
        jPanel2.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 140, 20));

        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("Department:");
        jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 140, 20));

        jLabel14.setForeground(new java.awt.Color(0, 0, 0));
        jLabel14.setText("Proposed Department:");
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 200, 140, 20));

        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Proposed By:");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 140, 20));

        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Proposed Date:");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 140, 20));

        btnNew.setBackground(new java.awt.Color(255, 255, 255));
        btnNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/NewIcon.png"))); // NOI18N
        jPanel2.add(btnNew, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 70, 60));

        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setText("Current Designation:");
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 140, 20));

        cmbEmployeeName.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel2.add(cmbEmployeeName, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 80, 500, -1));

        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Emp ID:");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 140, 20));

        lblEmployeeID.setForeground(new java.awt.Color(0, 0, 0));
        lblEmployeeID.setText("Emp ID:");
        jPanel2.add(lblEmployeeID, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 110, 310, 20));

        lblEmployeeDepartment.setForeground(new java.awt.Color(0, 0, 0));
        lblEmployeeDepartment.setText("Department:");
        jPanel2.add(lblEmployeeDepartment, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 140, 310, 20));

        lblCurrentDesignation.setForeground(new java.awt.Color(0, 0, 0));
        lblCurrentDesignation.setText("Current Designation:");
        jPanel2.add(lblCurrentDesignation, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 170, 310, 20));

        cmbProposedDesignation.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel2.add(cmbProposedDesignation, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 200, 180, -1));

        cmbProposedBy.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel2.add(cmbProposedBy, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 230, 310, -1));

        datePropose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                dateProposeMouseReleased(evt);
            }
        });
        jPanel2.add(datePropose, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 260, 310, 30));

        btnSubmit.setBackground(new java.awt.Color(255, 255, 255));
        btnSubmit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/SaveIcon.png"))); // NOI18N
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
            }
        });
        jPanel2.add(btnSubmit, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, 70, 60));

        jLabel17.setForeground(new java.awt.Color(0, 0, 0));
        jLabel17.setText("Status:");
        jPanel2.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, 140, 20));

        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel2.add(cmbStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 300, 310, -1));

        jLabel15.setForeground(new java.awt.Color(0, 0, 0));
        jLabel15.setText("Proposed Designation:");
        jPanel2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 140, 20));

        cmbProposedDeparment.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel2.add(cmbProposedDeparment, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 200, 160, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 690, 330));

        jPanel3.setBackground(new java.awt.Color(214, 214, 194));
        jPanel3.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(153, 153, 153)));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        jScrollPane1.setViewportView(tableAllowance);

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 660, 220));

        jButton1.setText("Add");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, -1, -1));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 420, 690, 260));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 709, Short.MAX_VALUE)
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

    private void dateProposeMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dateProposeMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_dateProposeMouseReleased

    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
        int GetProposalID = 0;
        try{
            DbConn.SQLQuery = "insert into tblproposalpromote (pp_empname,pp_empid,pp_currentdesignation,pp_proposeddesignation,"
                    + "pp_proposedby,pp_proposeddate,pp_addby,pp_date,pp_status,pp_proposeddepartment) values (?,?,?,?,?,?,?,?,?,?)";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.pstmt.setString(1, cmbEmployeeName.getSelectedItem().toString());
            DbConn.pstmt.setString(2, lblEmployeeID.getText());
            DbConn.pstmt.setString(3, lblCurrentDesignation.getText());
            DbConn.pstmt.setString(4, cmbProposedDesignation.getSelectedItem().toString());
            DbConn.pstmt.setString(5, cmbProposedBy.getSelectedItem().toString());
            Date GetProposeDate = datePropose.getDate();
            DbConn.pstmt.setString(6, String.valueOf(DbConn.sdfDate.format(GetProposeDate)));
            DbConn.pstmt.setString(7, "Admin");
            DbConn.pstmt.setString(8, DbConn.sdfDate.format(new Date()));
            DbConn.pstmt.setString(9, cmbStatus.getSelectedItem().toString());
            DbConn.pstmt.setString(10, cmbProposedDeparment.getSelectedItem().toString());
            DbConn.pstmt.execute();
            DbConn.pstmt.close();
            
            //get ppid
            DbConn.SQLQuery = "select * from tblproposalpromote where pp_empname=? and pp_empid=?";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.pstmt.setString(1, cmbEmployeeName.getSelectedItem().toString());
            DbConn.pstmt.setString(2,lblEmployeeID.getText());
            DbConn.rs = DbConn.pstmt.executeQuery();
            if (DbConn.rs.next()){
                GetProposalID = DbConn.rs.getInt("pp_seqno");
            }
            DbConn.pstmt.close();
            
            //save proposed allowance
            for(int i=0;i<tableAllowance.getRowCount();i++){
                DbConn.SQLQuery = "insert into tblproposeallowance (pa_ppid,pa_allowance,pa_amount,pa_empid,"
                       + "pa_empname,pa_proposeddate,pa_addby,pa_date) values (?,?,?,?,?,?,?,?)";
                DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
                DbConn.pstmt.setInt(1, GetProposalID);
                DbConn.pstmt.setString(2, tableAllowance.getValueAt(i, 0).toString());
                DbConn.pstmt.setString(3, tableAllowance.getValueAt(i, 1).toString());
                DbConn.pstmt.setString(4, lblEmployeeID.getText());
                DbConn.pstmt.setString(5, cmbEmployeeName.getSelectedItem().toString());
                DbConn.pstmt.setString(6, String.valueOf(DbConn.sdfDate.format(GetProposeDate)));
                DbConn.pstmt.setString(7, "Admin");
                DbConn.pstmt.setString(8, DbConn.sdfDate.format(new Date()));
                DbConn.pstmt.execute();
                DbConn.pstmt.close();
            }
            JOptionPane.showMessageDialog(this, "Proposal Submitted");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_btnSubmitActionPerformed
    private void FillAllowance(){
        cmbAllowance.removeAllItems();
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("Select al_description from tblmasterallowance order by al_description");
            DbConn.rs = DbConn.pstmt.executeQuery();
            while (DbConn.rs.next()){
                cmbAllowance.addItem(DbConn.rs.getString(1));
            }
            cmbAllowance.setSelectedIndex(-1);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Allowance Drop Down");
        }
    }
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        DefaultTableModel model = (DefaultTableModel)tableAllowance.getModel();
        Object insertRow[] ={"",""};
        model.addRow(insertRow);
        FillAllowance();
        tableAllowance.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(cmbAllowance));
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(frmPromotion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmPromotion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmPromotion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmPromotion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmPromotion().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JComboBox<String> cmbEmployeeName;
    private javax.swing.JComboBox<String> cmbProposedBy;
    private javax.swing.JComboBox<String> cmbProposedDeparment;
    private javax.swing.JComboBox<String> cmbProposedDesignation;
    private javax.swing.JComboBox<String> cmbStatus;
    private com.toedter.calendar.JDateChooser datePropose;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblCurrentDesignation;
    private javax.swing.JLabel lblEmployeeDepartment;
    private javax.swing.JLabel lblEmployeeID;
    private javax.swing.JTable tableAllowance;
    // End of variables declaration//GEN-END:variables
}
