/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

/**
 *
 * @author Ricardo
 */
import java.sql.Connection;
import java.sql.DriverManager;

public class AccesoDB {
 // constructor
 public AccesoDB() {
 }
 
 public static Connection getConnection() throws Exception{
 // variable de tipo conexion
  Connection cn=null;
  try {
   // cargar driver en memoria
   Class.forName("com.mysql.jdbc.Driver");
   // variable de la url de la base de datos
   String url="jdbc:mysql://localhost:3306/pagopostgrado?zeroDateTimeBehavior=convertToNull";
   // abrir conexion a BD
   cn= DriverManager.getConnection(url, "root", "123456");
  } catch (Exception e) {
   throw e;
  }
  return cn;
}

 
}