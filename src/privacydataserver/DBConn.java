/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package privacydataserver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 *
 * @author Patron
 */
public class DBConn {
    private String serverName;
    private String portNo;
    private String dbName;
    private String userName;
    private String password; 
    private String tableName;
    private String dbms="mysql";
    public DBConn(String server, String port, String db, String user, String pass,String table){
        serverName=server;
        portNo=port;
        dbName=db;
        userName=user;
        password=pass;
        tableName=table;
       
    }
    
    public void setDb(String db){
        dbms=db;
    }
    
    public Connection getConnection() throws SQLException{
        
        Connection con=null;
        
        Properties conProps=new Properties();
        conProps.put("user",userName);
        conProps.put("password", password);
       
        try {
        Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
    } 

        
        switch (this.dbms) {
            case "mysql":
                con=DriverManager.getConnection(
                       "jdbc:" + this.dbms + "://" +
                       this.serverName +
                       ":" + this.portNo + "/"+dbName,
                       userName,password);
               /*con=DriverManager.getConnection(
                       "jdbc:mysql://208.97.162.178:3306/prateek_griet",
                       "ewebadmin","w3bdb97"); 
                       *            */
                break;
            case "derby":
            
                con = DriverManager.getConnection(
                       "jdbc:" + this.dbms + ":" +
                       this.dbName +
                       ";create=true",
                       conProps);
                break;
        }
        System.out.println("Connected to database");
        return con;
    }
    
    public ResultSet execute(Connection con, String query) throws SQLException{
        Statement stmt=con.createStatement();
        ResultSet rs=stmt.executeQuery(query);
        return rs;    
    }

    public String displayValues(ResultSet rs) throws SQLException{
        String temp="";
        int noOfCols;
        ResultSetMetaData rsmd=rs.getMetaData();
        noOfCols=rsmd.getColumnCount();
        while(rs.next()){
            for(int i=0;i<noOfCols;i++){
                temp=temp+rs.getString(i)+"\t";
            }
            temp=temp+"\n";
        }
        return temp;
    }
   
    
    
}
