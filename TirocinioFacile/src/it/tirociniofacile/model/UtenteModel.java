package it.tirociniofacile.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class UtenteModel {
  private static DataSource ds;

  static {
    try {
      Context initCtx = new InitialContext();
      Context envCtx = (Context) initCtx.lookup("java:comp/env");

      ds = (DataSource) envCtx.lookup("jdbc/tirociniofacile");
    } catch (NamingException e) {
      System.out.println("Error:" + e.getMessage());
    }
  }
  
  private static final String TABLE_NAME_STUDENTE = "ProfiloStudente";
  private static final String TABLE_NAME_AZIENDA = "ProfiloAzienda";

  /**
   * Inserisce nel db un nuovo studente.
   * @param email email del nuovo studente da registrare
   * @param password password del nuovo studente da registrare
   * @param matricola matricola del nuovo studente da registrare
   * @throws SQLException eccezione lanciata in caso di record già esistente
   */
  public synchronized void salvaAccountStudente(String email, String password, String matricola) 
      throws SQLException {
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    try {
      connection = ds.getConnection();
      String insertSql = "INSERT INTO " + TABLE_NAME_STUDENTE
          + " (mail, password, matricola) VALUES (?, ?, ?)";
      preparedStatement = connection.prepareStatement(insertSql);
      preparedStatement.setString(1, email);
      preparedStatement.setString(2, password);
      preparedStatement.setString(3, matricola);

      preparedStatement.executeUpdate();
    } finally {
      try {
        if (preparedStatement != null) {
          preparedStatement.close();
        }
      } finally {
        if (connection != null) {
          connection.close();
        }
      }
    }
    return;
  }

  /**
   * Inserisce nel db una nuova azienda.
   * @param email email della nuova azienda da registrare
   * @param password password della nuova azienda da registrare
   * @throws SQLException eccezione lanciata in caso di record già esistente
   */
  public synchronized void salvaAccountAzienda(String email, String password, String nomeazienda) 
      throws SQLException {
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    try {
      connection = ds.getConnection();
      String insertSql = "INSERT INTO " + TABLE_NAME_AZIENDA
          + " (mail, password, nomeAzienda) VALUES (?, ?, ?)";
      preparedStatement = connection.prepareStatement(insertSql);
      preparedStatement.setString(1, email);
      preparedStatement.setString(2, password);
      preparedStatement.setString(3, nomeazienda);

      preparedStatement.executeUpdate();
    } finally {
      try {
        if (preparedStatement != null) {
          preparedStatement.close();
        }
      } finally {
        if (connection != null) {
          connection.close();
        }
      }
    }
    return;
  }
}
