package it.tirociniofacile.model;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import java.sql.DriverManager;
import java.util.Scanner;

public class Jdbc {
  
  //variabili di istanza
  private static Statement stmt;
  private static Connection con;
  
  /**
   * Main per avviare la connessione del database.
   * @param args
   */
  public static void main(String[] args) {
    
    //Inizia una connessione
    String db = "tirociniofacile";
    String user = "root";
    String pass = "root";
    
    try {
      // jdbs:mysql://indirizzo dell'host/nome del database
      String url = "jdbc:mysql://127.0.0.1/" + db;
     
      //Nome utente, password per la connessione al database
      con = (Connection) DriverManager.getConnection(url, user, pass);
      stmt = (Statement) con.createStatement();
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(0);
    }
  }
}
