/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package privacydataserver;

import java.awt.Component;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;

/**
 *
 * @author Sridhar
 */
public class MainFrame extends JFrame{
    JTextField dbServerInput=new JTextField();
    JTextField dbPortInput=new JTextField();
    JTextField dbInput=new JTextField();
    JTextField dbUserInput=new JTextField();
    JTextField dbTableName=new JTextField();
    JPasswordField dbPassInput=new JPasswordField();
    JTextArea outputArea=new JTextArea();
    JScrollPane scroll=new JScrollPane(outputArea);
    JTextField dbColName=new JTextField();
    JButton addDatabase=new JButton("Add Database Details");
    
    public MainFrame(String title){
        this.setTitle(title);
        frameCreator();
    }
    
    private void frameCreator(){
        JPanel mainPan=new JPanel();
        JPanel dataPan=new JPanel();
         BoxLayout box=new BoxLayout(mainPan,BoxLayout.PAGE_AXIS);
        
        String[] names={"Host Name","Port No","Database","User Name","Password","Table Name","Col Name"};
        JLabel[] labels=new JLabel[names.length];
        for(int i=0;i<names.length;i++){
            labels[i]=new JLabel(names[i]);
        }
        
        mainPan.setLayout(box);
        dataPan.setLayout(new SpringLayout());
    
         JLabel db=new JLabel("Database");
        db.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPan.add(db);
        
        dataPan.add(labels[0]);
        dataPan.add(dbServerInput);
        dbServerInput.setText("grietdb.gokaraju.info");
        
        dataPan.add(labels[1]);
        dataPan.add(dbPortInput);
        dbPortInput.setText("3306");

        dataPan.add(labels[2]);
        dataPan.add(dbInput);
        dbInput.setText("class_griet");
        
        dataPan.add(labels[3]);
        dataPan.add(dbUserInput);
        dbUserInput.setText("cseadmin");
        
        dataPan.add(labels[4]);
        dataPan.add(dbPassInput); 
        
        dataPan.add(labels[5]);
        dataPan.add(dbTableName);
        dbTableName.setText("med2");
        
        dataPan.add(labels[6]);
        dataPan.add(dbColName);
        dbColName.setText("ssn_no");
        
         scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        mainPan.add(dataPan);
         mainPan.add(new JLabel(""));
      
        
        mainPan.add(addDatabase);
     
        mainPan.add(new JLabel(""));
      
        JLabel out=new JLabel("Output");
        out.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPan.add(out);
        mainPan.add(scroll);
        mainPan.add(outputArea);
        
        SpringUtilities.makeCompactGrid(dataPan, 7,2 ,10, 10, 10, 10);
    
        this.add(mainPan);
        
    }
      public String getDbServer(){
        return dbServerInput.getText();
    }

    public String getDbPort(){
        return dbPortInput.getText();
    }
    
     public String getDbName(){
        return dbInput.getText();
    }
    
     public String getDbUser(){
        return dbUserInput.getText();
    }
    
    public String getDbPass(){
        return new String(dbPassInput.getPassword());
    }
    public String getDbTableName(){
        return dbTableName.getText();
    }
    public String getColName(){
        return dbColName.getText();
    }
}
