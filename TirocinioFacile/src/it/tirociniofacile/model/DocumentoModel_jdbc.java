package it.tirociniofacile.model;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

import it.tirociniofacile.bean.DocumentoConvenzioneBean;
import it.tirociniofacile.bean.DocumentoQuestionarioBean;

public class DocumentoModel_jdbc {
  //variabili di istanza
  private static Statement stmt;
  private static Connection con;
  public static final String TABLE_NAME_CONVENZIONI = "DomandaConvenzioneAzienda";
  public static final String TABLE_NAME_QUESTIONARI = "QuestionarioValutazioneAzienda";
  
  static {
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
  
  /**
   * Conta il numero di questionari approvati in un anno indicato.
   * @param anno l'anno in cui contare
   * @return un intero corrispondente al numero di questionari approvati
   */
  public synchronized int conteggioQuestionariApprovatiPerAnno(String anno) throws SQLException {
    Connection connection = con;
    PreparedStatement preparedStatement = null;
    int numeroQuestionariApprovatiPerAnno = 0;
    try {
      String insertSql = "SELECT COUNT(*) FROM " 
          + TABLE_NAME_QUESTIONARI + " WHERE annoAccademico = ? AND approvato = 1";
      preparedStatement = connection.prepareStatement(insertSql);
      preparedStatement.setString(1, anno);
      ResultSet rs = preparedStatement.executeQuery();
      if (rs.next()) {
        numeroQuestionariApprovatiPerAnno = rs.getInt(1);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return numeroQuestionariApprovatiPerAnno;
  }

  /**
   * Conta il numero di questionari approvati in una certa azienda.
   * @param azienda l'azienda per cui cercare
   * @return un intero corrispondente al numero di questionari approvati
   */
  public synchronized int conteggioQuestionariApprovatiPerAzienda(String azienda) 
      throws SQLException {
    Connection connection = con;
    PreparedStatement preparedStatement = null;
    int numAzienda = 0;
    try {
      String insertSql = "SELECT COUNT(*) FROM " + TABLE_NAME_QUESTIONARI 
          + " JOIN paginaazienda ON paginaazienda.id = "
          + " questionariovalutazioneazienda.paginaAzienda " 
          + " JOIN profiloazienda ON paginaazienda.profiloAzienda = profiloazienda.mail "
          + " WHERE approvato = 1 AND nomeAziendaRappresentata = ?";
      preparedStatement = connection.prepareStatement(insertSql);
      preparedStatement.setString(1, azienda);
      ResultSet rs = preparedStatement.executeQuery();
      if (rs.next()) {
        numAzienda = rs.getInt(1);
      }
    } catch (SQLException e) {
      e.printStackTrace();
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
      connection = con;
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
    Connection connection = con;
    PreparedStatement preparedStatement = null;
    try {
      String insertSql = "INSERT INTO " + TABLE_NAME_CONVENZIONI 
          + "(partitaIva, nomeAzienda, sedeLegale,"
          + " citta,rappresentanteLegale, luogoDiNascitaRappresentanteLegale," 
          + " dataDiNascitaRappresentanteLegale,approvato) VALUES(?,?,?,?,?,?,?,?)";
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
    } catch (SQLException e) {
      e.printStackTrace();
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
   * @throws SQLException in caso di errata connessione
   */
  public synchronized DocumentoConvenzioneBean ricercaConvenzionePerId(String id)
      throws SQLException {
    Connection connection = con;
    PreparedStatement preparedStatement = null;

    DocumentoConvenzioneBean dcb = null;

    try {
      String selectSql = "SELECT * FROM " + TABLE_NAME_CONVENZIONI + " JOIN "
          + PaginaAziendaModel.TABLE_NAME_PAGINA + " WHERE partitaIva = ?";

      preparedStatement = connection.prepareStatement(selectSql);
      preparedStatement.setString(1,id);
      ResultSet rs = preparedStatement.executeQuery();

      if (rs.first()) {
        dcb = new DocumentoConvenzioneBean();
        
        dcb.setNomeAzienda(rs.getString(2));
        dcb.setSedeLegale(rs.getString(3));
        dcb.setCitta(rs.getString(4));
        dcb.setRappresentanteLegale(rs.getString(5));
        dcb.setDataNascitaRappresentanteLegale(rs.getString(6));
        dcb.setLuogoNascitaRappresentanteLegale(rs.getString(7));
        dcb.setApprovato(rs.getBoolean(8));
      }
      
    } catch (SQLException e) {
      e.printStackTrace();
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
    Connection connection = con;
    PreparedStatement preparedStatement = null;

    DocumentoQuestionarioBean dqb = null;

    try {

      
      String selectSql = "SELECT * FROM " + TABLE_NAME_QUESTIONARI + " WHERE id = ? ";

      preparedStatement = connection.prepareStatement(selectSql);
      preparedStatement.setString(1, "" + id);
      ResultSet rs = preparedStatement.executeQuery();

      if (rs.first()) {
        dqb = new DocumentoQuestionarioBean();

      /*  dqb.setInformazioniSulTirocinio(rs.getString(2));
        dqb.setGradoDiSoddisfazioneDelTirocinante(rs.getString(3));
        dqb.setCommenti(rs.getString(4));
        dqb.setSuggerimenti(rs.getString(5));
        dqb.setAnnoAccademico(rs.getString(6));
        dqb.setApprovato(rs.getBoolean(7));
        dqb.setMailStudente(rs.getString(8));
        dqb.setGiudizioEsperienza(rs.getString(10));
        dqb.setGiudizioAzienda(rs.getString(11));
        dqb.setGiudizioUniversita(rs.getString(12));;*/

      }
    } catch (SQLException e) {
      e.printStackTrace();
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
    Connection connection = con;
    PreparedStatement preparedStatement = null;
    try {
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
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return;
  }



  /**
   * Approva il documento il cui id corrisponde a quello passato.
   * @param id identificativo del documento da ricercare
   */
  public synchronized void approvaDocumento(int id) throws SQLException {
    Connection connection = con;
    PreparedStatement preparedStatement = null;
    try {
      String insertSqlQuest = "UPDATE " + TABLE_NAME_QUESTIONARI 
          + "SET approvato = 1 WHERE id = ?";
      preparedStatement = connection.prepareStatement(insertSqlQuest);
      preparedStatement.setInt(1, id);

      preparedStatement.executeUpdate();

      String insertSqlConv = "UPDATE " + TABLE_NAME_CONVENZIONI 
          + "SET approvato = 1 WHERE partitaIva = ?";
      preparedStatement = connection.prepareStatement(insertSqlConv);
      preparedStatement.setInt(1, id);

      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return;
  }
}
