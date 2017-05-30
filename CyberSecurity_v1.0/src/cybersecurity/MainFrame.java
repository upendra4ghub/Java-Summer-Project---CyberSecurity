/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MainFrame.java
 *
 */
package cybersecurity;

import cybersecurity.alpha.DBOperations;
import cybersecurity.alpha.UsermasterBean;
import cybersecurity.util.CaptureScreenshots;
import cybersecurity.util.UploadThread;
import cybersecurity.util.ZipUnzipCapturedImages;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JOptionPane;

/**
 *
 * @author vinay
 */
public class MainFrame extends javax.swing.JFrame implements WindowListener {

    UsermasterBean objUsermasterBean;
    static Container container;
    static int userId;
    public static String username;
    int userActivityID;
    String grabFolder;

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        //initComponents();
        //setVisible(true);
    }

    public MainFrame(UsermasterBean objUsermasterBean, int userActivityID, String grabFolder) {
        initComponents();
        this.objUsermasterBean = objUsermasterBean;
        userId = objUsermasterBean.getUserId();
        username = objUsermasterBean.getUsername();
        this.userActivityID = userActivityID;
        this.grabFolder = grabFolder;
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setResizable(false);
        container = getContentPane();
        addWindowListener(this);
        setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mbMainFrame = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        miLogout = new javax.swing.JMenuItem();
        mnuProfile = new javax.swing.JMenu();
        miProfileDetail = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CyberSecurity");

        jMenu1.setText("File");

        miLogout.setText("Logout");
        miLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miLogoutActionPerformed(evt);
            }
        });
        jMenu1.add(miLogout);

        mbMainFrame.add(jMenu1);

        mnuProfile.setText("Profile");

        miProfileDetail.setText("Profile Detail");
        miProfileDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miProfileDetailActionPerformed(evt);
            }
        });
        mnuProfile.add(miProfileDetail);

        mbMainFrame.add(mnuProfile);

        setJMenuBar(mbMainFrame);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 277, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void miLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miLogoutActionPerformed
        // TODO add your handling code here:
        logout();

}//GEN-LAST:event_miLogoutActionPerformed

    private void miProfileDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miProfileDetailActionPerformed
        // TODO add your handling code here:
        container.removeAll();
        ProfileDetail objUserDetail = new ProfileDetail();
        objUserDetail.setBounds(250, 100, 438, 400);
        container.setVisible(false);
        container.add(objUserDetail);
        container.setVisible(true);
    }//GEN-LAST:event_miProfileDetailActionPerformed

    void logout() {

        dispose();
        DBOperations objDB = new DBOperations();
        objDB.updateUserActivity(userActivityID);
        CaptureScreenshots.stop = true;

        new Logout().setVisible(true);
        Thread t = new Thread(new Runnable() {

            public void run() {


                ZipUnzipCapturedImages objCapture = new ZipUnzipCapturedImages(grabFolder);
                objCapture.start();

                try {
                    objCapture.join();
                } catch (Exception e) {
                    System.out.println("Exception in  logout");
                }

                UploadThread uploadThread = new UploadThread(grabFolder + ".zip");

                try {
                    uploadThread.start();
                    uploadThread.join();
                } catch (Exception e) {
                    System.out.println("Exception in logout:" + e);
                }
                JOptionPane.showMessageDialog(null, "Work Uploaded Successfully", "", JOptionPane.INFORMATION_MESSAGE);

                System.exit(0);

            }
        });
        t.start();




    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        logout();
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar mbMainFrame;
    private javax.swing.JMenuItem miLogout;
    private javax.swing.JMenuItem miProfileDetail;
    private javax.swing.JMenu mnuProfile;
    // End of variables declaration//GEN-END:variables
}