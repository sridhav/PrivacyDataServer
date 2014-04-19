/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package privacydataserver;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Sridhar
 */
public class PrivacyDataServer {

    /**
     * @param args the command line arguments
     */
    static MainFrame win;
     private static String dbServerName,dbPortNo,dbName,dbUserName,dbPassword,dbTableName,dbColName=null;
    private static ServerSock mm;
    public static void main(String[] args) {
        // TODO code application logic here
        
        win=new MainFrame("Server");
        win.setVisible(true);
        win.pack();
        win.setSize(300,700);
        win.setResizable(false);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        win.addDatabase.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                //To change body of generated methods, choose Tools | Templates.
               dbServerName=win.getDbServer();
               dbPortNo=win.getDbPort();
               dbName=win.getDbName();
               dbUserName=win.getDbUser();
               dbPassword=win.getDbPass();
               dbTableName=win.getDbTableName();
               dbColName=win.getColName();
                try {
                    mm=new ServerSock(win,"1243","127.0.0.1",dbServerName,dbPortNo,dbName, dbUserName, dbPassword, dbTableName,dbColName);
                    mm.start();
                } catch (UnknownHostException ex) {
                    Logger.getLogger(PrivacyDataServer.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(PrivacyDataServer.class.getName()).log(Level.SEVERE, null, ex);
                }
               JOptionPane.showMessageDialog(win,"Database Details are added");
            }
           
       });
        
        
    }
     public static String encryptMD5(String val) throws NoSuchAlgorithmException{
        
        MessageDigest md=MessageDigest.getInstance("MD5");
        md.update(val.getBytes());
        byte s[]=md.digest();
        String result="";
        for (int i = 0; i < s.length; i++) {
          result += Integer.toHexString((0x000000ff & s[i]) | 0xffffff00).substring(6);
        }
        
   //    System.out.println(result);
        
        return result;
    }
    
}
