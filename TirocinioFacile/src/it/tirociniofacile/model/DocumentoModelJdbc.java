package it.tirociniofacile.model;

import com.mysql.jdbc.Statement;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import it.tirociniofacile.bean.DocumentoConvenzioneBean;
import it.tirociniofacile.bean.DocumentoQuestionarioBean;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Classe di supporto per il testing, espone gli stessi metodi della classe DocumentoModel, 
 * ma esegue una connessione diretta al database.
 * @author Paolo De Cristofaro
 *
 */
public class DocumentoModelJdbc {
  // variabili di istanza
  private static Statement stmt;
  private static Connection con;
  public static final String TABLE_NAME_CONVENZIONI = "DomandaConvenzioneAzienda";
  public static final String TABLE_NAME_QUESTIONARI = "QuestionarioValutazioneAzienda";

  /*
   * public static final String SAVE_PATH =
   * "C:/Users/PC1/git/progettoIStirociniofacile/TirocinioFacile/WebContent/pdf/";
   */
  public static final String SAVE_PATH = "C:/Users/Andrea95/git/"
      + "progettoIStirociniofacile/TirocinioFacile/WebContent/pdf/";

  static {
    // Inizia una connessione
    String db = "tirociniofacile";
    String user = "root";
    String pass = "root";

    try {
      // jdbs:mysql://indirizzo dell'host/nome del database
      String url = "jdbc:mysql://127.0.0.1/" + db;

      // Nome utente, password per la connessione al database
      con = (Connection) DriverManager.getConnection(url, user, pass);
      stmt = (Statement) con.createStatement();
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(0);
    }
  }
  /**
   * Ricerca tutti i documenti convenzione bean.
   * @return
   * 
   * @throws SQLException
   * eccezioni di SQL.
   */
  
  public synchronized ArrayList<DocumentoConvenzioneBean> getTuttiDocumentiConvenzioneAzienda()
      throws SQLException {
    Connection connection = con;
    PreparedStatement preparedStatement = null;
    ArrayList<DocumentoConvenzioneBean> listaDocumenti = new ArrayList<DocumentoConvenzioneBean>();
    try {
      String selectSql = "SELECT * FROM " + TABLE_NAME_CONVENZIONI
          + " WHERE approvato = 0 AND url IS NOT NULL";

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
    }

    return listaDocumenti;
  }
  /**
   *  Ricerca tutti i documenti questionari non ancora approvati che hanno un pdf allegato.
   * @return
   * 
   * @throws SQLException
   * eccezione di SQL
   */

  public synchronized ArrayList<DocumentoQuestionarioBean> getTuttiDocumentiQuestionari()
      throws SQLException {
    Connection connection = con;
    PreparedStatement preparedStatement = null;
    ArrayList<DocumentoQuestionarioBean> listaDocumenti = new ArrayList<>();
    try {
      String selectSql = "SELECT * FROM " + TABLE_NAME_QUESTIONARI
          + " WHERE approvato = 0 AND url IS NOT NULL ";

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

    }

    return listaDocumenti;
  }
  
  /**
   *Ricerca tutti i documenti questionari per una pagina azienda.
   * @param id id della pagina dell'azienda
   * @return
   * 
   * @throws SQLException
   * eccezioni di Sql
   */
  public synchronized ArrayList<DocumentoQuestionarioBean> getTuttiDocumentiQuestionariPerPagina(
      int id) throws SQLException {
    Connection connection = con;
    PreparedStatement preparedStatement = null;

    ArrayList<DocumentoQuestionarioBean> listaDocumenti = 
        new ArrayList<DocumentoQuestionarioBean>();

    try {

      String selectSql = "SELECT commenti,suggerimenti,"
          + "giudizioAzienda,giudizioUniversita,giudizioEsperienza FROM " + TABLE_NAME_QUESTIONARI
          + " WHERE paginaAziendaId = ? ";

      preparedStatement = connection.prepareStatement(selectSql);
      preparedStatement.setInt(1, id);
      ResultSet rs = preparedStatement.executeQuery();
      if (rs.first()) {
        do {
          DocumentoQuestionarioBean documento = new DocumentoQuestionarioBean();
          documento.setCommenti(rs.getString(1));
          documento.setSuggerimenti(rs.getString(2));
          documento.setGiudizioAzienda(rs.getFloat(3));
          documento.setGiudizioUniversita(rs.getFloat(4));
          documento.setGiudizioEsperienza(rs.getFloat(5));
          listaDocumenti.add(documento);
        } while (rs.next());
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return listaDocumenti;
  }
  /**
   * Metodo che ricerca tutti i questionari non approvati per un determinato studente.
   * @param mailStudente indirizzo e-mail di uno studente
   * @return
   * 
   * @throws SQLException
   * eccezione di SQL.
   */
  
  public synchronized ArrayList<String> ricercaQuestionariNonApprovatiPerStudente(
      String mailStudente) throws SQLException {

    Connection connection = con;
    PreparedStatement preparedStatement = null;

    ArrayList<String> lista = new ArrayList<String>();

    try {
      String selectSql = "SELECT id,annoAccademico,nomeAzienda,testoQuestionario FROM "
          + TABLE_NAME_QUESTIONARI + " JOIN " + TABLE_NAME_CONVENZIONI + " ON "
          + TABLE_NAME_QUESTIONARI + ".paginaAziendaID = " + TABLE_NAME_CONVENZIONI
          + ".paginaAziendaID WHERE " + TABLE_NAME_QUESTIONARI
          + ".approvato = 0 AND mailStudente = ? ";

      preparedStatement = connection.prepareStatement(selectSql);
      preparedStatement.setString(1, mailStudente);
      ResultSet rs = preparedStatement.executeQuery();

      if (rs.first()) {
        do {
          String id = rs.getString(1);
          String annoAccademico = rs.getString(2);
          String nomeAzienda = rs.getString(3);
          String testoQuest = rs.getString(4);

          lista.add(id + ";" + annoAccademico + ";" + nomeAzienda + ";" + testoQuest);
        } while (rs.next());
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return lista;
  }
  /**
   * Conta il numero di questionari approvati in un anno indicato.
   * @param anno anno axxademico indicato.
   * @return
   * 
   * @throws SQLException
   * eccezioni di SQL
   */
  
  public synchronized int conteggioQuestionariApprovatiPerAnno(String anno) throws SQLException {
    Connection connection = con;
    PreparedStatement preparedStatement = null;
    int numeroQuestionariApprovatiPerAnno = 0;
    try {
      String insertSql = "SELECT COUNT(*) FROM " + TABLE_NAME_QUESTIONARI
          + " WHERE annoAccademico = ? AND approvato = 1";
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
 * @param azienda nome dell'azienda
 * @return
 * 
 * @throws SQLException
 * eccezioni di SQL
 */
  
  public synchronized int conteggioQuestionariApprovatiPerAzienda(String azienda)
      throws SQLException {
    Connection connection = con;
    PreparedStatement preparedStatement = null;
    int numAzienda = 0;
    try {

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
    } catch (SQLException e) {
      e.printStackTrace();  
    }
    return numAzienda;
  }
  /**
   *  Metodo che consente di salvare un nuovo questionario sul database.
   * @param commenti commenti sull'azienda
   * @param suggerimenti suggerimenti all'azienda
   * @param annoAccademico anno in cui si è svolto il tirocinio.
   * @param mailStudente indirizzo e-mail dello studente
   * @param paginaAziendaId id della pagina dell'azienda
   * @param matricola matricola dello studente
   * @param giudizioEsperienza voto sull'esperienza
   * @param giudizioAzienda voto sull'azienda
   * @param giudizioUniversita voto sull'università
   * @param testoQuestionario testo del questionario
   * @return
   */
  
  public synchronized int salvaQuestionario(String commenti, String suggerimenti,
      String annoAccademico, String mailStudente, int paginaAziendaId, String matricola,
      float giudizioEsperienza, float giudizioAzienda, float giudizioUniversita,
      String testoQuestionario) {

    Connection connection = con;
    PreparedStatement preparedStatement = null;
    try {

      String insertSql = "INSERT INTO " + TABLE_NAME_QUESTIONARI + " (commenti, suggerimenti,"
          + " annoAccademico, approvato, mailStudente, paginaAziendaID,"
          + " giudizioEsperienza, giudizioAzienda, giudizioUniversita, testoQuestionario) "
          + " VALUES(?,?,?,?,?,?,?,?,?,?)";

      preparedStatement = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);

      preparedStatement.setString(1, commenti);
      preparedStatement.setString(2, suggerimenti);
      preparedStatement.setString(3, annoAccademico);
      preparedStatement.setInt(4, 0);
      preparedStatement.setString(5, mailStudente);
      preparedStatement.setInt(6, paginaAziendaId);
      preparedStatement.setFloat(7, giudizioEsperienza);
      preparedStatement.setFloat(8, giudizioAzienda);
      preparedStatement.setFloat(9, giudizioUniversita);
      preparedStatement.setString(10, testoQuestionario);

      preparedStatement.executeUpdate();
      ResultSet rs = preparedStatement.getGeneratedKeys();
      rs.next();
      int autoId = rs.getInt(1);

      return autoId;

    } catch (SQLException e) {
      e.printStackTrace();

    }
    return -1;
  }
  /**
 * Salva il documento di convenzione all'interno del database.
 * @param piva partita iva
 * @param nomeAzienda nome dell'azienda
 * @param sedeLegale sede legale dell'azienda
 * @param citta citta dell'azienda
 * @param rappLegale rappresentante legale dell'azienda
 * @param luogoDiNascitaRappLegale luogo di nascita del rappresentante  dell'azienda
 * @param dataDiNascitaRappLegale data di nascita del rappresentante dell'azienda
 * @param testoConvenzione testo della convenzione
 * @return
 */
  
  public synchronized boolean salvaConvenzione(String piva, String nomeAzienda, String sedeLegale,
      String citta, String rappLegale, String luogoDiNascitaRappLegale,
      String dataDiNascitaRappLegale, String testoConvenzione) {
    
    Connection connection = con;
    PreparedStatement preparedStatement = null;
    
    try {
      String insertSql = "INSERT INTO " + TABLE_NAME_CONVENZIONI
          + "(partitaIva, nomeAzienda, sedeLegale,"
          + " citta,rappresentanteLegale, luogoDiNascitaRappresentanteLegale,"
          + " datadiNascitaRappresentanteLegale,approvato,testoConvenzione) "
          + " VALUES(?,?,?,?,?,?,?,?,?)";
      
      preparedStatement = connection.prepareStatement(insertSql);

      preparedStatement.setString(1, piva);
      preparedStatement.setString(2, nomeAzienda);
      preparedStatement.setString(3, sedeLegale);
      preparedStatement.setString(4, citta);
      preparedStatement.setString(5, rappLegale);
      preparedStatement.setString(6, luogoDiNascitaRappLegale);
      preparedStatement.setString(7, dataDiNascitaRappLegale);
      preparedStatement.setInt(8, 0);
      preparedStatement.setString(9, testoConvenzione);

      try {
        preparedStatement.executeUpdate();
      } catch (MySQLIntegrityConstraintViolationException e) {
        return false;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } 
    return true;
  }
  /**
   * Metodo che salva il pdf, documento di convenzione dell'azienda, come file e mantiene un
   * riferimento all'url del pdf sul database.
   * @param url url del pdf
   * @param piva partita iva dell'azienda
   */

  public synchronized void salvaPdfConvenzione(String url, String piva) {
    Connection connection = con;
    PreparedStatement preparedStatement = null;
    try {

      String updateSql = "UPDATE " + TABLE_NAME_CONVENZIONI + " SET url = ? WHERE partitaIva = ?";

      preparedStatement = connection.prepareStatement(updateSql);
      preparedStatement.setString(1, "pdf/" + url);
      preparedStatement.setString(2, piva);

      preparedStatement.executeUpdate();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  /**
 * Metodo che permette il salvataggio di un documento pdf, mantenendo un riferimento, alla
 * posizione del file, sul database.
 * @param url url del questionario
 * @param id id del pdf
 */
  
  public synchronized void salvaPdfQuestionario(String url, int id) {
    Connection connection = con;
    PreparedStatement preparedStatement = null;
    try {

      File fileSaveDir = new File(SAVE_PATH);
      if (!fileSaveDir.exists()) {
        fileSaveDir.mkdir();
      }

      String updateSql = "UPDATE " + TABLE_NAME_QUESTIONARI + " SET url = ? WHERE id = ?";

      preparedStatement = connection.prepareStatement(updateSql);
      preparedStatement.setString(1, "pdf/" + url);
      preparedStatement.setInt(2, id);

      preparedStatement.executeUpdate();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  /**
 * Metodo che permette di ricercare un documento di convenzione di un'azienda inserendo la email
 * del profilo associato.
 * @param email indirizzo e-mail dell'azienda
 * @return
 * 
 * @throws SQLException
 * eccezione di SQL
 */
  
  public DocumentoConvenzioneBean ricercaConvenzionePerEmail(String email) throws SQLException {
    Connection connection = con;
    PreparedStatement preparedStatement = null;

    DocumentoConvenzioneBean dcb = null;

    try {
      String selectSql = "SELECT * FROM " + TABLE_NAME_CONVENZIONI + " JOIN "
          + UtenteModel.TABLE_NAME_AZIENDA + " ON " + TABLE_NAME_CONVENZIONI + ".nomeAzienda = "
          + UtenteModel.TABLE_NAME_AZIENDA + ".nomeAziendaRappresentata WHERE mail = ? ";

      preparedStatement = connection.prepareStatement(selectSql);
      preparedStatement.setString(1, "" + email);

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
        dcb.setTesto(rs.getNString(9));
        dcb.setUrl(rs.getString(10));

      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return dcb;
  }
  /**
 * Metodo che permette di ricercare un documento di convenzione di un'azienda inserendo la email
 * del profilo associato.
 * @param piva partita iva dell'azienda
 * @return
 */
  
  public synchronized DocumentoConvenzioneBean ricercaConvenzionePerPartitaIva(String piva) {
    Connection connection = con;
    PreparedStatement preparedStatement = null;

    DocumentoConvenzioneBean dcb = null;

    try {

      String selectSql = "SELECT * FROM " + TABLE_NAME_CONVENZIONI
          + " WHERE partitaIva = ? AND url IS NOT NULL";

      preparedStatement = connection.prepareStatement(selectSql);
      preparedStatement.setString(1, "" + piva);
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
        dcb.setTesto(rs.getNString(9));
        dcb.setUrl(rs.getString(10));
      }

    } catch (SQLException e) {
      e.printStackTrace();
    } 
    return dcb;
  }
  /**
 * Permette di ricercare un documento questionario fornendo l'id.
 * @param id identificativo del questionario
 * @return
 */
  
  public synchronized DocumentoQuestionarioBean ricercaQuestionarioPerId(int id) {
    Connection connection = con;
    PreparedStatement preparedStatement = null;

    DocumentoQuestionarioBean dqb = null;

    try {

      String selectSql = "SELECT * FROM " + TABLE_NAME_QUESTIONARI + " WHERE id = ? ";

      preparedStatement = connection.prepareStatement(selectSql);
      preparedStatement.setInt(1, id);
      ResultSet rs = preparedStatement.executeQuery();

      if (rs.first()) {
        dqb = new DocumentoQuestionarioBean();
        dqb.setId(rs.getInt(1));
        dqb.setCommenti(rs.getString(2));
        dqb.setSuggerimenti(rs.getString(3));
        dqb.setAnnoAccademico(rs.getString(4));
        dqb.setApprovato(rs.getBoolean(5));
        dqb.setUrl(rs.getString(6));
        dqb.setMailStudente(rs.getString(7));
        dqb.setPaginaAziendaId(rs.getInt(8));
        dqb.setGiudizioEsperienza(rs.getFloat(9));
        dqb.setGiudizioAzienda(rs.getFloat(10));
        dqb.setGiudizioUniversita(rs.getFloat(11));
        dqb.setTestoQuestionario(rs.getString(12));

      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return dqb;
  }
  /**
 *  Cancella il documento il cui id corrisponde a quello passato.
 * @param id id del documento
 */
  
  public synchronized void cancellaDocumento(String id) {
    Connection connection = con;
    PreparedStatement preparedStatement = null;
    try {

      String insertSqlQuest = "DELETE FROM " + TABLE_NAME_QUESTIONARI + " WHERE id = ?";
      preparedStatement = connection.prepareStatement(insertSqlQuest);
      preparedStatement.setString(1, id);

      preparedStatement.executeUpdate();

      String insertSqlConv = "DELETE FROM " + TABLE_NAME_CONVENZIONI + " WHERE partitaIva = ?";
      preparedStatement = connection.prepareStatement(insertSqlConv);
      preparedStatement.setString(1, id);
      preparedStatement.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  /**
 * Approva il documento il cui id corrisponde a quello passato.
 * @param id id del documento
 */
  
  public synchronized void approvaDocumento(String id) {
    Connection connection = con;
    PreparedStatement preparedStatement = null;
    try {

      String insertSqlQuest = "UPDATE " + TABLE_NAME_QUESTIONARI
          + " SET approvato = ? WHERE id = ?";
      preparedStatement = connection.prepareStatement(insertSqlQuest);

      preparedStatement.setInt(1, 1);
      preparedStatement.setString(2, id);

      preparedStatement.executeUpdate();

      String insertSqlConv = "UPDATE " + TABLE_NAME_CONVENZIONI
          + " SET approvato = ? WHERE partitaIva = ?";

      preparedStatement = connection.prepareStatement(insertSqlConv);
      preparedStatement.setInt(1, 1);

      preparedStatement.setString(2, id);

      preparedStatement.executeUpdate();

    } catch (Exception e) {
      e.printStackTrace();
    } 
  }

}
