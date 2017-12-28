package it.tirociniofacile.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import it.tirociniofacile.bean.UtenteBean;

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

  
  /**
   * Salva le credenziali generate per un nuovo account amministrativo (presidente area didattica 
   * o impiegato ufficio tirocini).
   * @param email email da salvare nel file per il nuovo account amministrativo
   * @param password password da salvare nel file per il nuovo account amministrativo
   */
  public synchronized void generaCredenziali(String email, String password) {
    //TODO
  }
  
  /**
   * Carica email e password da un db.
   * @param email email dell'account da cercare nel db
   * @param password password dell'account da cercare nel db
   * @return ritorna un bean che rappresenta un utente
   */
  public synchronized UtenteBean caricaAccount(String email, String password) {
    return new UtenteBean();
  }
  
  /**
   * Cerca la password di un utente dalla email.
   * @param email email dell'account da cercare nel db
   * @return ritorna un bean che rappresenta un utente
   */
  public synchronized void cercaAccountPerEmail(String email) {
   //TODO void ma non ne sono sicuro
  }
  
}

