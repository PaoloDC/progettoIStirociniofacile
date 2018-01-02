package it.tirociniofacile.model;

import it.tirociniofacile.bean.DocumentoConvenzioneBean;
import it.tirociniofacile.bean.DocumentoQuestionarioBean;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


/**
 * Classe model per la gestione di lettura e scrittura 
 * dei documenti (convenzione e questionario) sul database.
 * @author Paolo De Cristofaro
 */
public class DocumentoModel {
  private static DataSource ds;

  static {
    try {
      Context initCtx = new InitialContext();
      Context envCtx = (Context) initCtx.lookup("java:comp/env");

      ds = (DataSource) envCtx.lookup("jdbc/tirociniofacile");
    } catch (NamingException e) {
      System.out.println("Naming Exception:" + e.getMessage());
    }
  }
  
  //variabili di istanza
  private static final String TABLE_NAME_CONVENZIONI = "DomandaConvenzioneAzienda";
  private static final String TABLE_NAME_QUESTIONARI = "QuestionarioValutazioneAzienda";
  
  /**
   * Conta il numero di questionari approvati in un anno indicato.
   * @param anno l'anno in cui contare
   * @return un intero corrispondente al numero di questionari approvati
   */
  public synchronized int conteggioQuestionariApprovatiPerAnno(String anno) throws SQLException {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    int numeroQuestinariApprovatiPerAnno = 0;
    try {
      connection = ds.getConnection();
      String insertSql = "SELECT COUNT (*) FROM " 
                + TABLE_NAME_QUESTIONARI + " WHERE annoAccademico = ?";
      preparedStatement = connection.prepareStatement(insertSql);
      preparedStatement.setString(1, anno);
      ResultSet rs = preparedStatement.executeQuery();
      if (rs.next()) {
        numeroQuestinariApprovatiPerAnno = rs.getInt(1);
      }
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
    return numeroQuestinariApprovatiPerAnno;
  }
  
  /**
   * Conta il numero di questionari approvati in una certa azienda.
   * @param azienda l'azienda per cui cercare
   * @return un intero corrispondente al numero di questionari approvati
   */
  public synchronized int conteggioQuestionariApprovatiPerAzienda(String azienda) 
      throws SQLException {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    int numAzienda = 0;
    try {
      connection = ds.getConnection();
      String insertSql = "SELECT COUNT(*) FROM " + TABLE_NAME_QUESTIONARI 
          + " JOIN paginaazienda ON paginaazienda.id = "
          + " questionariovalutazioneazienda.paginaAzienda " 
          + " JOIN profiloazienda ON paginaazienda.profiloAzienda = profiloazienda.mail "
          + " WHERE approvato = 1 AND nomeAziendaRappresentata = ? ; ";
      preparedStatement = connection.prepareStatement(insertSql);
      preparedStatement.setString(1, azienda);
      ResultSet rs = preparedStatement.executeQuery();
      if (rs.next()) {
        numAzienda = rs.getInt(1);
      }
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
    return numAzienda;
  }
  
  /**
   * Salva il questionario all'interno del database.
   * @param informazioniSulTirocinio informazioni generiche sul tirocinio
   * @param commenti commenti dello studente sull'esperienza
   * @param suggerimenti suggerimenti dello studente sull'esperienza
   * @param annoAccademico l'anno in cui è stato svolto il tirocinio
   * @param giudizioEsperienza grado di giudizio sull'esperienza
   * @param giudizioAzienda grado di giudizio sull'azienda
   * @param giudizioUniversita grado di giudizio sull'università
   * @param matricola identificativo dello studente
   */
  public synchronized void salvaQuestionario(String informazioniSulTirocinio, 
      String commenti, String suggerimenti, String annoAccademico, float giudizioEsperienza,
      float giudizioAzienda, float giudizioUniversita, String matricola) throws SQLException {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    try {
      connection = ds.getConnection();
      String insertSql = "INSERT INTO" + TABLE_NAME_QUESTIONARI + "(id,informazioniSulTirocinio,"
          + " commenti, suggerimenti, annoAccademico, giudizioEsperienza,"
          + " giudizioAzienda, giudizioUniversità, matricola) VALUES(?,?,?,?,?,?,?,?)";
      preparedStatement = connection.prepareStatement(insertSql);
      
      //ID???
      preparedStatement.setInt(1, id);
      preparedStatement.setString(2, informazioniSulTirocinio);
      preparedStatement.setString(3, commenti);
      preparedStatement.setString(4, suggerimenti);
      preparedStatement.setString(5, annoAccademico);
      preparedStatement.setFloat(6, giudizioEsperienza);
      preparedStatement.setFloat(7, giudizioAzienda);
      preparedStatement.setFloat(8, giudizioUniversita);
      preparedStatement.setString(9, matricola);
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
    
    
    
    
    
  }
  
  /**
   * Salva il documento di convenzione all'interno del database.
   * @param nomeAzienda nome univoco dell'azienda che si convenziona
   * @param sedeLegale nazione in cui ha sede l'azienda
   * @param citta città in cui ha sede l'azienda
   * @param rappLegale nome del rappresentate legale dell'azienda
   * @param luogoDiNascitaRappLegale luogo di nascita del rappresentate legale dell'azienda
   * @param dataDiNascitaRappLegale data di nascita del rappresentate legale dell'azienda
   */
  public synchronized void salvaConvenzione(String nomeAzienda, String sedeLegale,
      String citta, String rappLegale, String luogoDiNascitaRappLegale,
      String dataDiNascitaRappLegale) throws SQLException {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    try {
      connection = ds.getConnection();
      String insertSql = "INSERT INTO" + TABLE_NAME_CONVENZIONI + "(nomeAzienda, sedeLegale,"
          + " citta,rappLegale, luogoDiNascitaRappLegale," 
          + " dataNascitaRappLegale) VALUES(?,?,?,?,?,?)";
      preparedStatement = connection.prepareStatement(insertSql);
      
     
      preparedStatement.setString(1, nomeAzienda);
      preparedStatement.setString(2, sedeLegale);
      preparedStatement.setString(3, citta);
      preparedStatement.setString(4, rappLegale);
      preparedStatement.setString(5, luogoDiNascitaRappLegale);
      preparedStatement.setString(6, dataDiNascitaRappLegale);
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
    
    
    
    
     
  }
  
  /**
   * Salva il documento PDF all'interno del database.
   * @param pdf il documento da salvare
   * @param id id che collega il pdf ad un documento (convenzione o questionario)
   */
  public synchronized void salvaPdf(File pdf,int id) {
    
  }
  
  /**
   * Permette di ricercare un documento convenzione fornendo l'id.
   * @param id identificativo del documento da ricercare 
   * @return un documento convenzione
   */
  public synchronized DocumentoConvenzioneBean ricercaConvenzionePerId(int id) {
    return null;
  }
  
  /**
   * Permette di ricercare un documento questionario fornendo l'id.
   * @param id identificativo del documento da ricercare 
   * @return un documento questionario
   */
  public synchronized DocumentoQuestionarioBean ricercaQuestionarioPerId(int id) {
    return null;
  }
  
  /**
   * Cancella il documento il cui id corrisponde a quello passato.
   * @param id identificativo del documento da ricercare
   */
  public synchronized void cancellaDocumento(int id) {
    return;
  }
  
  /**
   * Approva il documento il cui id corrisponde a quello passato.
   * @param id identificativo del documento da ricercare
   */
  public synchronized void approvaDocumento(int id) throws SQLException {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    try {
      connection = ds.getConnection();
      String insertSqlQuest = "UPDATE " + TABLE_NAME_QUESTIONARI 
          + "SET approvato = 1 WHERE id = ?";
      preparedStatement = connection.prepareStatement(insertSqlQuest);
      preparedStatement.setInt(1, id);

      preparedStatement.executeUpdate();
      
      String insertSqlConv = "UPDATE " + TABLE_NAME_CONVENZIONI 
          + "SET approvato = 1 WHERE id = ?";
      preparedStatement = connection.prepareStatement(insertSqlConv);
      preparedStatement.setInt(1, id);
      
      
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
      return;
    }
  }
}






