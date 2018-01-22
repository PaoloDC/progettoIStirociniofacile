package it.tirociniofacile.model;

import it.tirociniofacile.bean.DocumentoConvenzioneBean;
import it.tirociniofacile.bean.DocumentoQuestionarioBean;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
  public static final String TABLE_NAME_CONVENZIONI = "DomandaConvenzioneAzienda";
  public static final String TABLE_NAME_QUESTIONARI = "QuestionarioValutazioneAzienda";
  public static final String SAVE_DIR = "pdf";

  /**
   * Ricerca tutti i documenti convenzione bean.
   * @return lista con tutti i documenti
   * @throws SQLException eccezzioni sql
   */
  public synchronized ArrayList<DocumentoConvenzioneBean>
      getTuttiDocumentiConvenzioneAzienda() throws SQLException {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ArrayList<DocumentoConvenzioneBean> listaDocumenti = new ArrayList<DocumentoConvenzioneBean>();
    try {
      connection = ds.getConnection();
      String selectSql = "SELECT * FROM " + TABLE_NAME_CONVENZIONI 
          +   " WHERE approvato = 0 ";
      
      preparedStatement = connection.prepareStatement(selectSql);
      ResultSet rs = preparedStatement.executeQuery();
      if (rs.first()) {
        do {
          DocumentoConvenzioneBean documento = new DocumentoConvenzioneBean();
          documento.setPartitaIva(rs.getString(1));
          documento.setNomeAzienda(rs.getString(2));
          documento.setSedeLegale(rs.getString(3));
          documento.setCitta(rs.getString(4));
          documento.setRappresentanteLegale(rs.getString(5));
          documento.setDataNascitaRappresentanteLegale(rs.getString(6));
          documento.setLuogoNascitaRappresentanteLegale(rs.getString(7));
          documento.setApprovato(rs.getBoolean(8));
          listaDocumenti.add(documento);
        } while (rs.next());
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if (preparedStatement != null) {
          preparedStatement.close();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      } finally {
        if (connection != null) {
          try { 
            connection.close();
          } catch  (SQLException e) {
            e.printStackTrace();
          }
        }
      }
    }
    
    return listaDocumenti;
  }
 
  
  /**
   * Ricerca tutti i documenti questionari.
   * @return lista con tutti i documenti
   * @throws SQLException eccezzioni sql
   */
  public synchronized ArrayList<DocumentoQuestionarioBean>
      getTuttiDocumentiQuestionari() throws SQLException {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ArrayList<DocumentoQuestionarioBean> listaDocumenti = 
        new ArrayList<DocumentoQuestionarioBean>();
    try {
      connection = ds.getConnection();
      String selectSql = "SELECT * FROM " + TABLE_NAME_QUESTIONARI 
          +   " WHERE approvato = 0 ";
      
      preparedStatement = connection.prepareStatement(selectSql);
      ResultSet rs = preparedStatement.executeQuery();
      if (rs.first()) {
        do {
          DocumentoQuestionarioBean documento = new DocumentoQuestionarioBean();
          documento.setId(rs.getInt(1));
          documento.setCommenti(rs.getString(2));
          documento.setSuggerimenti(rs.getString(3));
          documento.setAnnoAccademico(rs.getString(4));
          documento.setApprovato(rs.getBoolean(5));
          documento.setMailStudente(rs.getString(7));
          listaDocumenti.add(documento);
        } while (rs.next());
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if (preparedStatement != null) {
          preparedStatement.close();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      } finally {
        if (connection != null) {
          try { 
            connection.close();
          } catch  (SQLException e) {
            e.printStackTrace();
          }
        }
      }
    }
    
    return listaDocumenti;
  }  
  
  
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
      String insertSql = "SELECT COUNT(*) FROM " 
          + TABLE_NAME_QUESTIONARI + " WHERE annoAccademico = ? AND approvato = 1";
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
          + " questionariovalutazioneazienda.paginaAziendaID " 
          + " JOIN profiloazienda ON paginaazienda.mailAzienda = profiloazienda.mail "
          + " WHERE approvato = 1 AND nomeAziendaRappresentata = ? ";
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
   * Metodo che consente di salvare un nuovo questionario sul database.
   * @param commenti eventuali commenti dello studente, allegati al questionario
   * @param suggerimenti eventuali suggerimenti dello studente, allegati al questionario
   * @param annoAccademico indica l'anno in cui � stato svolto il tirocinio
   * @param mailStudente mail dello studente che ha svolto il tirocinio
   * @param paginaAziendaId riferimento all'identificativo sul database 
   *     della pagina dell'azienda in cui � stato svolto il tirocinio
   * @param matricola matricola dello studente che ha svolto il tirocinio
   * @param giudizioEsperienza media dei giudizi sull'esperienza del tirocinio
   * @param giudizioAzienda  media dei giudizi sull'azienda che ha ospitato del tirocinio
   * @param giudizioUniversita media dei giudizi sull'universit� che ha  del tirocinio
   */
  public synchronized void salvaQuestionario(String commenti, String suggerimenti, 
      String annoAccademico,  String mailStudente, int paginaAziendaId, String matricola,
      float giudizioEsperienza, float giudizioAzienda, float giudizioUniversita) {
    
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    try {
      connection = ds.getConnection();
      String insertSql = "INSERT INTO " + TABLE_NAME_QUESTIONARI + " (commenti, suggerimenti,"
          + " annoAccademico, approvato, mailStudente, paginaAziendaID,"
          + " giudizioEsperienza, giudizioAzienda, giudizioUniversita) "
          + " VALUES(?,?,?,?,?,?,?,?,?)";
      
      preparedStatement = connection.prepareStatement(insertSql);

      preparedStatement.setString(1, commenti);
      preparedStatement.setString(2, suggerimenti);
      preparedStatement.setString(3, annoAccademico);
      preparedStatement.setInt(4, 0);
      preparedStatement.setString(5, mailStudente);
      preparedStatement.setInt(6, paginaAziendaId);
      preparedStatement.setFloat(7, giudizioEsperienza);
      preparedStatement.setFloat(8, giudizioAzienda);
      preparedStatement.setFloat(9, giudizioUniversita);
      
      System.out.println(preparedStatement);
      
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally { 
      try {
        if (preparedStatement != null) {
          preparedStatement.close();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      } finally {
        if (connection != null) {
          try {
            connection.close();
          } catch (SQLException e) {
            e.printStackTrace();
          }
        }
      }
    }
  }

  /**
   * Salva il documento di convenzione all'interno del database.
   * @param nomeAzienda nome univoco dell'azienda che si convenziona
   * @param sedeLegale nazione in cui ha sede l'azienda
   * @param citta citt� in cui ha sede l'azienda
   * @param rappLegale nome del rappresentate legale dell'azienda
   * @param luogoDiNascitaRappLegale luogo di nascita del rappresentate legale dell'azienda
   * @param dataDiNascitaRappLegale data di nascita del rappresentate legale dell'azienda
   */
  public synchronized void salvaConvenzione(String piva, String nomeAzienda, String sedeLegale,
      String citta, String rappLegale, String luogoDiNascitaRappLegale,
      String dataDiNascitaRappLegale) throws SQLException {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    try {
      connection = ds.getConnection();
      String insertSql = "INSERT INTO " + TABLE_NAME_CONVENZIONI 
          + "(partitaIva, nomeAzienda, sedeLegale,"
          + " citta,rappresentanteLegale, luogoDiNascitaRappresentanteLegale," 
          + " datadiNascitaRappresentanteLegale,approvato) VALUES(?,?,?,?,?,?,?,?)";
      preparedStatement = connection.prepareStatement(insertSql);

      preparedStatement.setString(1, piva);
      preparedStatement.setString(2, nomeAzienda);
      preparedStatement.setString(3, sedeLegale);
      preparedStatement.setString(4, citta);
      preparedStatement.setString(5, rappLegale);
      preparedStatement.setString(6, luogoDiNascitaRappLegale);
      preparedStatement.setString(7, dataDiNascitaRappLegale);
      preparedStatement.setInt(8, 0);
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
   * Metodo che salva il pdf, documento di convenzione dell'azienda, come file
   *   e mantiene un riferimento all'url del pdf sul database.
   * @param url riferimento alla posizione del file pdf
   * @param email mail dell'azienda di cui si sta salvando il documento
   * @throws SQLException in caso di errata connessione al database
   * @throws IOException in caso di errato salvataggio del file
   */
  public synchronized void salvaPdfConvenzione(String url, String email) 
      throws SQLException, IOException {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    try {
      connection = ds.getConnection();
      String selectSql = "SELECT partitaIva FROM " + PaginaAziendaModel.TABLE_NAME_PAGINA 
          + " JOIN " 
          + TABLE_NAME_CONVENZIONI + " ON "
          + " paginaAzienda.id = domandaconvenzioneazienda.paginaaziendaID "
          + " WHERE paginaazienda.mailAzienda = ? ";
      
      preparedStatement = connection.prepareStatement(selectSql);
      preparedStatement.setString(1, email);
      ResultSet rs = preparedStatement.executeQuery();
      
      String savePath = "C:/Users/PC1/git/progettoIStirociniofacile/"
          + "TirocinioFacile/WebContent"
          + "/" + SAVE_DIR;
      
      File fileSaveDir = new File(savePath);
      if (!fileSaveDir.exists()) {
        fileSaveDir.mkdir();
      }

      String updateSql = "UPDATE " + TABLE_NAME_CONVENZIONI 
          + " SET url = ? WHERE id = ?";
      
      rs.next();
      preparedStatement.close();
      
      String piva = rs.getString(1);
      preparedStatement = connection.prepareStatement(updateSql);
      preparedStatement.setString(1, savePath);
      preparedStatement.setString(2, piva);
      
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
   * Metodo che permette il salvataggio di un documento pdf, 
   * mantenendo un riferimento, alla posizione del file, sul database.
   * @param url riferimento alla posizione del pdf
   * @param email indica la mail dello studente o dell'azienda che sta salvando il documento
   * @throws SQLException in caso di errato salvataggio sul database
   * @throws IOException in caso di errato salvataggio del file
   */
  public synchronized void salvaPdfQuestionario(String url,String email) 
      throws SQLException, IOException {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    try {
      connection = ds.getConnection();
      String selectSql = "SELECT id FROM " 
          + TABLE_NAME_QUESTIONARI
          + " WHERE mailStudente = ? ";
      
      preparedStatement = connection.prepareStatement(selectSql);
      preparedStatement.setString(1, email);
      ResultSet rs = preparedStatement.executeQuery();

      String savePath = "C:/Users/PC1/git/progettoIStirociniofacile/"
          + "TirocinioFacile/WebContent"
          + "/" + SAVE_DIR;
      
      File fileSaveDir = new File(savePath);
      if (!fileSaveDir.exists()) {
        fileSaveDir.mkdir();
      }
      
      
      String updateSql = "UPDATE " + TABLE_NAME_QUESTIONARI 
          + " SET url = ? WHERE id = ?";
      
      rs.next();
      preparedStatement.close();
      
      int id = rs.getInt(1);
      preparedStatement = connection.prepareStatement(updateSql);
      preparedStatement.setString(1, url);
      preparedStatement.setInt(2, id);
      
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
   * Metodo che permette di ricercare un documento di convenzione 
   *    di un'azienda inserendo il suo identificativo.
   * @param partitaIva l'identificativo dell'azienda
   * @return un documento di convenzione bean
   * @throws SQLException in caso di problemi di connessione al database
   */
  public synchronized DocumentoConvenzioneBean ricercaConvenzionePerPartitaIva(String partitaIva)
      throws SQLException {
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    DocumentoConvenzioneBean dcb = null;

    try {
      connection = ds.getConnection();

      String selectSql = "SELECT * FROM " + TABLE_NAME_CONVENZIONI + " JOIN "
          + PaginaAziendaModel.TABLE_NAME_PAGINA + " WHERE partitaIva = ?";

      preparedStatement = connection.prepareStatement(selectSql);
      preparedStatement.setString(1, "" + partitaIva);
      ResultSet rs = preparedStatement.executeQuery();

      if (rs.first()) {
        dcb = new DocumentoConvenzioneBean();
        
        dcb.setPartitaIva(rs.getString(1));
        dcb.setNomeAzienda(rs.getString(2));
        dcb.setSedeLegale(rs.getString(3));
        dcb.setCitta(rs.getString(4));
        dcb.setRappresentanteLegale(rs.getString(5));
        dcb.setDataNascitaRappresentanteLegale(rs.getString(6));
        dcb.setLuogoNascitaRappresentanteLegale(rs.getString(7));
        dcb.setApprovato(rs.getBoolean(8));
        dcb.setUrl(rs.getString(9));
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
    return dcb;
  }

  /**
   * Permette di ricercare un documento questionario fornendo l'id.
   * @param id identificativo del documento da ricercare 
   * @return un documento questionario
   * @throws SQLException in caso di errata connessione al database
   */
  public synchronized DocumentoQuestionarioBean ricercaQuestionarioPerId(int id)
      throws SQLException {
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    DocumentoQuestionarioBean dqb = null;

    try {
      connection = ds.getConnection();

      String selectSql = "SELECT * FROM " + TABLE_NAME_QUESTIONARI + " WHERE id = ? ";

      preparedStatement = connection.prepareStatement(selectSql);
      preparedStatement.setString(1, "" + id);
      ResultSet rs = preparedStatement.executeQuery();

      if (rs.first()) {
        dqb = new DocumentoQuestionarioBean();
        dqb.setId(rs.getInt(1));
        dqb.setCommenti(rs.getString(2));
        dqb.setSuggerimenti(rs.getString(3));
        dqb.setAnnoAccademico(rs.getString(4));
        dqb.setApprovato(rs.getBoolean(5));
        dqb.setMailStudente(rs.getString(7));
    

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
    return dqb;
  }

  /**
   * Cancella il documento il cui id corrisponde a quello passato.
   * @param id identificativo del documento da ricercare
   * @throws SQLException in caso di errata connessione al database 
   */
  public synchronized void cancellaDocumento(String id) 
      throws SQLException {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    connection = ds.getConnection();
    String insertSqlQuest = "DELETE FROM " + TABLE_NAME_QUESTIONARI 
        + " WHERE id = ?";
    preparedStatement = connection.prepareStatement(insertSqlQuest);
    preparedStatement.setString(1, id);

    preparedStatement.executeUpdate();

    String insertSqlConv = "DELETE FROM " + TABLE_NAME_CONVENZIONI 
        + " WHERE partitaIva = ?";
    preparedStatement = connection.prepareStatement(insertSqlConv);
    preparedStatement.setString(1, id);
    preparedStatement.executeUpdate();
  }


  /**
   * Approva il documento il cui id corrisponde a quello passato.
   * @param id identificativo del documento da ricercare
   */
  public synchronized void approvaDocumento(String id) throws SQLException {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    try {
      connection = ds.getConnection();
     
      String insertSqlQuest = "UPDATE " + TABLE_NAME_QUESTIONARI 
          + " SET approvato = ? WHERE id = ?";
      preparedStatement = connection.prepareStatement(insertSqlQuest);
     
      preparedStatement.setInt(1, 1);
      preparedStatement.setString(2, id);

      preparedStatement.executeUpdate();
     
      String insertSqlConv = "UPDATE " + TABLE_NAME_CONVENZIONI 
          + " SET approvato = ? WHERE partitaIva = ?";
      
      preparedStatement.close();
      preparedStatement = connection.prepareStatement(insertSqlConv);
      preparedStatement.setInt(1, 1);
      
      preparedStatement.setString(2, id);

      preparedStatement.executeUpdate();
      
    } catch (Exception e) {
      e.printStackTrace();
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