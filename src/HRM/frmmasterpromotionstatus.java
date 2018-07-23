package HRM;

import java.sql.SQLException;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;

public class frmmasterpromotionstatus extends javax.swing.JFrame {
DbConnection DbConn = new DbConnection();
boolean add,edit;
    public frmmasterpromotionstatus() {
        initComponents();
        DbConn.DoConnect();
        FillTable();
        txtDescription.setEnabled(false);
        tableMaster.setAutoCreateRowSorter(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(frmmasterpromotionstatus.DISPOSE_ON_CLOSE);
    }
    private void FillTable(){
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("Select ps_seqno,ps_description from tblpromotestatus order by ps_description");
            DbConn.rs = DbConn.pstmt.executeQuery();
            tableMaster.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
            DbConn.pstmt.close();
            tableMaster.getColumnModel().getColumn(0).setHeaderValue("ID");
            tableMaster.getColumnModel().getColumn(1).setHeaderValue("DESCRIPTION");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Fill Table");
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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableMaster = new javax.swing.JTable();
        txtDescription = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(214, 214, 194));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 430, 20));

        jLabel1.setForeground(new java.awt.Color(102, 102, 0));
        jLabel1.setText("Description:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, -1, -1));

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 0));
        jLabel2.setText("HR -> Promotion Status");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        tableMaster.setModel(new javax.swing.table.DefaultTableModel(
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
        tableMaster.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMasterMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableMaster);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 430, 160));
        jPanel1.add(txtDescription, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 130, 330, -1));

        jButton1.setText("Add");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 60, -1, 50));

        jButton2.setText("Edit");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 60, -1, 50));

        jButton3.setText("Save");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 60, -1, 50));

        jButton4.setText("Delete");
        jPanel1.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 60, -1, 50));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 354, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        add= true;
        edit =false;
        txtDescription.setText("");
        txtDescription.requestFocus();
        txtDescription.setEnabled(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tableMasterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMasterMouseClicked
        int row = tableMaster.getSelectedRow();
        int ba = tableMaster.convertRowIndexToModel(row);
        
        txtDescription.setText(tableMaster.getModel().getValueAt(ba,1).toString());
    }//GEN-LAST:event_tableMasterMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        
        if (add==true && edit==false){
            try{
                DbConn.SQLQuery = "insert into tblpromotestatus (ps_description,ps_addby,ps_date) values(?,?,?)";
                DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
                DbConn.pstmt.setString(1, txtDescription.getText());
//                DbConn.pstmt.setString(2, DbConn.GetLoggedInUserName);
                DbConn.pstmt.setString(2, "Admin");
                DbConn.pstmt.setString(3, DbConn.sdfDate.format(DbConn.DateToday));
                DbConn.pstmt.execute();
                JOptionPane.showMessageDialog(this, "Record Saved");
                FillTable();
                txtDescription.setText("");
                txtDescription.setEnabled(false);
            }catch(SQLException e){
                JOptionPane.showMessageDialog(this, "Save Query");
            }
        }else{
            if (add==false && edit==true){
                int row = tableMaster.getSelectedRow();
        int ba = tableMaster.convertRowIndexToModel(row);
                try{
                    DbConn.SQLQuery = "update tblpromotestatus set ps_description=?,ps_addby=?,ps_date=? where ps_seqno=?";
                    DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
                    DbConn.pstmt.setString(1, txtDescription.getText());
    //                DbConn.pstmt.setString(2, DbConn.GetLoggedInUserName);
                    DbConn.pstmt.setString(2, "Admin");
                    DbConn.pstmt.setString(3, DbConn.sdfDate.format(DbConn.DateToday));
                    DbConn.pstmt.setString(4, tableMaster.getModel().getValueAt(ba, 0).toString());
                    DbConn.pstmt.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Record Updated");
                    FillTable();
                    txtDescription.setText("");
                    txtDescription.setEnabled(false);
                }catch(SQLException e){
                    JOptionPane.showMessageDialog(this, "Edit Query");
                }
            }
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        edit=true;
        add=false;
        txtDescription.setEnabled(true);
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(frmmasterpromotionstatus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmmasterpromotionstatus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmmasterpromotionstatus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmmasterpromotionstatus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmmasterpromotionstatus().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable tableMaster;
    private javax.swing.JTextField txtDescription;
    // End of variables declaration//GEN-END:variables
}