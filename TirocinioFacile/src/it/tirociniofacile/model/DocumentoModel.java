package it.tirociniofacile.model;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import it.tirociniofacile.bean.DocumentoConvenzioneBean;
import it.tirociniofacile.bean.DocumentoQuestionarioBean;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;



/**
 * Classe model per la gestione di lettura e scrittura dei documenti (convenzione e questionario)
 * sul database.
 * 
 * @author Paolo De Cristofaro
 */
public class DocumentoModel {

  private static DataSource ds;
  
  // public static final String SAVE_PATH = "D:/pdf/";
  /*path da cambiare*/
 // public static final String SAVE_PATH = "C:/Users/PC1/git/"
 //     + "progettoIStirociniofacile/TirocinioFacile/WebContent/pdf/";
   public static final String SAVE_PATH =
   "C:/Users/Andrea95/git/progettoIStirociniofacile/TirocinioFacile/WebContent/pdf/";
  
  static {
    
    try {
      Context initCtx = new InitialContext();
      Context envCtx = (Context) initCtx.lookup("java:comp/env");

      ds = (DataSource) envCtx.lookup("jdbc/tirociniofacile");
    } catch (NamingException e) {
      System.out.println("Naming Exception:" + e.getMessage());
    }
  }

  // variabili di istanza
  public static final String TABLE_NAME_CONVENZIONI = "DomandaConvenzioneAzienda";
  public static final String TABLE_NAME_QUESTIONARI = "QuestionarioValutazioneAzienda";

  /**
   * Ricerca tutti i documenti convenzione bean.
   * 
   * @return lista con tutti i documenti
   * @throws SQLException
   *           eccezzioni sql
   */
  public synchronized ArrayList<DocumentoConvenzioneBean> getTuttiDocumentiConvenzioneAzienda() {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ArrayList<DocumentoConvenzioneBean> listaDocumenti = new ArrayList<DocumentoConvenzioneBean>();
    try {
      connection = ds.getConnection();
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

    return listaDocumenti;
  }

  /**
   * Ricerca tutti i documenti questionari non ancora approvati che hanno un pdf allegato.
   * 
   * @return una lista di questionari
   * @throws SQLException
   *           in caso di errata connessione al database
   */
  public synchronized ArrayList<DocumentoQuestionarioBean> getTuttiDocumentiQuestionari()
      throws SQLException {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ArrayList<DocumentoQuestionarioBean> listaDocumenti = new ArrayList<>();
    try {
      connection = ds.getConnection();
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

    return listaDocumenti;
  }

  /**
   * Ricerca tutti i documenti questionari per una pagina azienda.
   * 
   * @param id
   *          l'identificativo della pagina azienda
   * @return una lista di questionari per una certa pagina
   * @throws SQLException
   *           in caso di errata connessione al database
   */
  public synchronized ArrayList<DocumentoQuestionarioBean> getTuttiDocumentiQuestionariPerPagina(
      int id) throws SQLException {
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    ArrayList<DocumentoQuestionarioBean> listaDocumenti = 
        new ArrayList<DocumentoQuestionarioBean>();

    try {
      connection = ds.getConnection();
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

    return listaDocumenti;
  }

  /**
   * Metodo che ricerca tutti i questionari non approvati per un determinato studente.
   * 
   * @param mailStudente
   *          la mail che identifica lo studente
   * @return una lista di questionari non approvati
   */
  public synchronized ArrayList<String> ricercaQuestionariNonApprovatiPerStudente(
      String mailStudente) {

    Connection connection = null;
    PreparedStatement preparedStatement = null;

    ArrayList<String> lista = new ArrayList<String>();

    try {
      connection = ds.getConnection();
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

    return lista;
  }

  /**
   * Conta il numero di questionari approvati in un anno indicato.
   * 
   * @param anno
   *          l'anno in cui contare
   * @return un intero corrispondente al numero di questionari approvati
   */
  public synchronized int conteggioQuestionariApprovatiPerAnno(String anno) throws SQLException {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    int numeroQuestinariApprovatiPerAnno = 0;
    try {
      connection = ds.getConnection();
      String insertSql = "SELECT COUNT(*) FROM " + TABLE_NAME_QUESTIONARI
          + " WHERE annoAccademico = ? AND approvato = 1";
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
   * 
   * @param azienda
   *          l'azienda per cui cercare
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
   * 
   * @param commenti
   *          eventuali commenti dello studente, allegati al questionario
   * @param suggerimenti
   *          eventuali suggerimenti dello studente, allegati al questionario
   * @param annoAccademico
   *          indica l'anno in cui è stato svolto il tirocinio
   * @param mailStudente
   *          mail dello studente che ha svolto il tirocinio
   * @param paginaAziendaId
   *          riferimento all'identificativo sul database della pagina dell'azienda in cui è stato
   *          svolto il tirocinio
   * @param matricola
   *          matricola dello studente che ha svolto il tirocinio
   * @param giudizioEsperienza
   *          media dei giudizi sull'esperienza del tirocinio
   * @param giudizioAzienda
   *          media dei giudizi sull'azienda che ha ospitato del tirocinio
   * @param giudizioUniversita
   *          media dei giudizi sull'università che ha del tirocinio
   * @return un intero che corrisponde all'id del questionario, -1 in caso di errato salvataggio
   */
  public synchronized int salvaQuestionario(String commenti, String suggerimenti,
      String annoAccademico, String mailStudente, int paginaAziendaId, String matricola,
      float giudizioEsperienza, float giudizioAzienda, float giudizioUniversita,
      String testoQuestionario) {

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    try {
      connection = ds.getConnection();
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
    return -1;
  }

  /**
   * Salva il documento di convenzione all'interno del database.
   * 
   * @param nomeAzienda
   *          nome univoco dell'azienda che si convenziona
   * @param sedeLegale
   *          nazione in cui ha sede l'azienda
   * @param citta
   *          città in cui ha sede l'azienda
   * @param rappLegale
   *          nome del rappresentate legale dell'azienda
   * @param luogoDiNascitaRappLegale
   *          luogo di nascita del rappresentate legale dell'azienda
   * @param dataDiNascitaRappLegale
   *          data di nascita del rappresentate legale dell'azienda
   */
  public synchronized boolean salvaConvenzione(String piva, String nomeAzienda, String sedeLegale,
      String citta, String rappLegale, String luogoDiNascitaRappLegale,
      String dataDiNascitaRappLegale, String testoConvenzione) {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    try {
      connection = ds.getConnection();
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
    } finally {
      try {
        if (preparedStatement != null) {
          try {
            preparedStatement.close();
          } catch (SQLException e) {
            e.printStackTrace();
          }
        }
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
    return true;
  }

  /**
   * Metodo che salva il pdf, documento di convenzione dell'azienda, come file e mantiene un
   * riferimento all'url del pdf sul database.
   * 
   * @param url
   *          riferimento alla posizione del file pdf
   
   * @throws SQLException
   *           in caso di errata connessione al database
   * @throws IOException
   *           in caso di errato salvataggio del file
   */
  public synchronized void salvaPdfConvenzione(String url, String piva) {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    try {
      connection = ds.getConnection();

      String updateSql = "UPDATE " + TABLE_NAME_CONVENZIONI + " SET url = ? WHERE partitaIva = ?";

      preparedStatement = connection.prepareStatement(updateSql);
      preparedStatement.setString(1, "pdf/" + url);
      preparedStatement.setString(2, piva);

      preparedStatement.executeUpdate();

    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if (preparedStatement != null) {
          try {
            preparedStatement.close();
          } catch (SQLException e) {
            e.printStackTrace();
          }
        }
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
   * Metodo che permette il salvataggio di un documento pdf, mantenendo un riferimento, alla
   * posizione del file, sul database.
   * 
   * @param url
   *          riferimento alla posizione del pdf
   * @param email
   *          indica la mail dello studente o dell'azienda che sta salvando il documento
   * @throws SQLException
   *           in caso di errato salvataggio sul database
   * @throws IOException
   *           in caso di errato salvataggio del file
   */
  public synchronized void salvaPdfQuestionario(String url, String id) {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    try {
      connection = ds.getConnection();

      File fileSaveDir = new File(SAVE_PATH);
      if (!fileSaveDir.exists()) {
        fileSaveDir.mkdir();
      }

      String updateSql = "UPDATE " + TABLE_NAME_QUESTIONARI + " SET url = ? WHERE id = ?";

      preparedStatement = connection.prepareStatement(updateSql);
      preparedStatement.setString(1, "pdf/" + url);
      preparedStatement.setString(2, id);

      preparedStatement.executeUpdate();

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (preparedStatement != null) {
          try {
            preparedStatement.close();
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      } finally {
        if (connection != null) {
          try {
            connection.close();
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }
    }
  }

  /**
   * Metodo che permette di ricercare un documento di convenzione di un'azienda inserendo la email
   * del profilo associato.
   * 
   * @param email
   *          la mail del profilo associata al documento di convenzione
   * @return il documento di convenzione dell'azienda
   * @throws SQLException
   *           in caso di errata connessione al database
   */
  public DocumentoConvenzioneBean ricercaConvenzionePerEmail(String email) throws SQLException {
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    DocumentoConvenzioneBean dcb = null;

    try {
      connection = ds.getConnection();

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
   * Metodo che permette di ricercare un documento di convenzione di un'azienda inserendo la email
   * del profilo associato.
   * 
  
   * @return il documento di convenzione dell'azienda
   * @throws SQLException
   *           in caso di errata connessione al database
   */
  public synchronized DocumentoConvenzioneBean ricercaConvenzionePerPartitaIva(String piva) {
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    DocumentoConvenzioneBean dcb = null;

    try {
      connection = ds.getConnection();

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
    } finally {
      try {
        if (preparedStatement != null) {
          try {
            preparedStatement.close();
          } catch (SQLException e) {
            e.printStackTrace();
          }

        }
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
    return dcb;
  }

  /**
   * Permette di ricercare un documento questionario fornendo l'id.
   * 
   * @param id
   *          identificativo del documento da ricercare
   * @return un documento questionario
   * @throws SQLException
   *           in caso di errata connessione al database
   */
  public synchronized DocumentoQuestionarioBean ricercaQuestionarioPerId(int id) {
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    DocumentoQuestionarioBean dqb = null;

    try {
      connection = ds.getConnection();

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
    } finally {
      try {
        if (preparedStatement != null) {
          try {
            preparedStatement.close();
          } catch (SQLException e) {
            e.printStackTrace();
          }

        }
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
    return dqb;
  }

  /**
   * Cancella il documento il cui id corrisponde a quello passato.
   * 
   * @param id
   *          identificativo del documento da ricercare
   * @throws SQLException
   *           in caso di errata connessione al database
   */
  public synchronized void cancellaDocumento(String id) {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    try {
      connection = ds.getConnection();
      String insertSqlQuest = "DELETE FROM " + TABLE_NAME_QUESTIONARI + " WHERE id = ?";
      preparedStatement = connection.prepareStatement(insertSqlQuest);
      preparedStatement.setString(1, id);

      preparedStatement.executeUpdate();

      String insertSqlConv = "DELETE FROM " + TABLE_NAME_CONVENZIONI + " WHERE partitaIva = ?";
      preparedStatement = connection.prepareStatement(insertSqlConv);
      preparedStatement.setString(1, id);
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if (preparedStatement != null) {
          try {
            preparedStatement.close();
          } catch (SQLException e) {
            e.printStackTrace();
          }

        }
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
   * Approva il documento il cui id corrisponde a quello passato.
   * 
   * @param id
   *          identificativo del documento da ricercare
   */
  public synchronized void approvaDocumento(String id) {
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

      preparedStatement = connection.prepareStatement(insertSqlConv);
      preparedStatement.setInt(1, 1);

      preparedStatement.setString(2, id);

      preparedStatement.executeUpdate();

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (preparedStatement != null) {
          try {
            preparedStatement.close();
          } catch (SQLException e) {
            e.printStackTrace();
          }

        }
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

}
