package com.ecobank.postingservice.Entity;

import java.sql.Connection;
import java.sql.DriverManager;

public class Utilities {
 public static Connection getConnection() throws Exception {
      Connection conn = null;
      String host = "";
      String userid = "";
      String passwords = "";
      String instance ="";

//     host = "localhost";
//     userid = "festus";
//     passwords = "festus";
//     instance = "ORCLPDB1";

//     host = "rac-eng-scan";
//     userid = "FCUBSAWA";
//     passwords = "AWAUAT123$";
//     instance = "FCAWAUAT";

//     host = "10.8.184.140";
//     userid = "FCUBSNIG";
//     passwords = "NIGUAT123$";
//     instance = "FCUBSNIGLDN";

     host = "10.8.184.140";
     userid = "BLSSCH";
     passwords = "BLSSCH";
     instance = "FCUBSNIGLDN";

     Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();

     conn = DriverManager.getConnection("jdbc:oracle:thin:@"+ host +":1521/"+ instance +"", userid, passwords);
     System.out.println("Connected to DB");
      return conn ;
 }

}
