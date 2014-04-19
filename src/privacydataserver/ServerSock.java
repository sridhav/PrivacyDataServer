/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package privacydataserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

/**
 *
 * @author Sridhar
 */
public class ServerSock extends Thread {
  String serverName,portNo;
  ServerSocket serv;
  String arrEle[];
  MainFrame x;
  private String dbServerName,dbPortNo,dbName,dbUserName,dbPassword,dbTableName,colName=null;
  public ServerSock(MainFrame y,String port,String server,String dbServer,String dbPort, String dbName, String dbUser, String dbPass, String dbTable,String col ) throws UnknownHostException, IOException{
   //   serverName=server;
      this.x=y;
      portNo=port;
      serverName=server;
      serv=new ServerSocket(Integer.parseInt(port)); 
      dbServerName=dbServer;
      dbPortNo=dbPort;
      this.dbName=dbName;
      dbUserName=dbUser;
      dbPassword=dbPass;
      dbTableName=dbTable;
      colName=col;
      
  }  
  
  @Override
  public void run(){
      x.outputArea.append("Server Started with "+"127.0.0.1:"+serv.getLocalPort()+"\n");
      System.out.println("Waiting For Connection\n");
      try {
          try (Socket server = serv.accept()) {
              x.outputArea.append("Accepted connection from Client "+server.getRemoteSocketAddress()+"\n");
              DataInputStream in=new DataInputStream(server.getInputStream());
              String inp=in.readUTF();
              StringTokenizer ss=new StringTokenizer(inp,"\n");
              arrEle=new String[ss.countTokens()];
              int i=0;
              x.outputArea.append("\nRecieving Data\n");
              while(ss.hasMoreElements()){
                  arrEle[i]=(String)ss.nextElement();
                  x.outputArea.append(arrEle[i]+"\n");
                  i++;
              }
              System.out.println("Sending Data\n");
              x.outputArea.append("\nSending Data\n");
              
              String data=getData();
              x.outputArea.append(data);
              
              DataOutputStream out=new DataOutputStream(server.getOutputStream());
              out.writeUTF(data);
              x.outputArea.append("Data Sent\n");
              
          } catch (  SQLException | NoSuchAlgorithmException ex) {
              Logger.getLogger(ServerSock.class.getName()).log(Level.SEVERE, null, ex);
          }
      } catch (IOException ex) {
          Logger.getLogger(ServerSock.class.getName()).log(Level.SEVERE, null, ex);
      }
  }
  
  private String getData() throws SQLException, NoSuchAlgorithmException{
      //  x.outputArea.append("Connecting to Local Database\n");
        DBConn x=new DBConn(dbServerName,dbPortNo,dbName,dbUserName,dbPassword,dbTableName);
        Connection conn=x.getConnection();
        String query="select * from "+dbTableName+";";
        ResultSet rs=x.execute(conn, query);
        String res="";
        while(rs.next()){
            String encryptVal=PrivacyDataServer.encryptMD5(rs.getString(colName));
             int noOfCols;
             //System.out.println(encryptVal);
            ResultSetMetaData rsmd=rs.getMetaData();
            noOfCols=rsmd.getColumnCount();
            for(int i=0;i<arrEle.length;i++){
                if(encryptVal.equals(arrEle[i])){
                    System.out.println("hellp");
                    for(int j=1;j<=noOfCols;j++){
                        res=res+rs.getString(j)+"\t";
                    }
                    res=res+"\n";
                    break;
                }
            }
        }
        return res;
  }
  
  
}
    
//