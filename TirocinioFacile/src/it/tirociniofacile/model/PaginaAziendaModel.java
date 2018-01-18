package it.tirociniofacile.model;

import it.tirociniofacile.bean.PaginaAziendaBean;
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

public class PaginaAziendaModel {
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

  public static final String TABLE_NAME_PAGINA = "PaginaAzienda";
  public static final String TABLE_NAME_AMBITO = "Ambito";
  public static final String TABLE_NAME_SKILL = "Skill";

  /**
   * Cerca nel db tutte le pagine azienda.
   * @return lista di pagina azienda
   */
  public synchronized ArrayList<PaginaAziendaBean> ricerca() {
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    ArrayList<PaginaAziendaBean> pabList = new ArrayList<PaginaAziendaBean>();
    try {
      connection = ds.getConnection();
      String selectSql = "SELECT descrizione,localita,nomeaziendaRappresentata,id "
          + "FROM " + TABLE_NAME_PAGINA
          + " JOIN " + UtenteModel.TABLE_NAME_AZIENDA
          + " ON " + TABLE_NAME_PAGINA + ".mailAzienda = " 
          + UtenteModel.TABLE_NAME_AZIENDA + ".mail";
      preparedStatement = connection.prepareStatement(selectSql);

      ResultSet rs = preparedStatement.executeQuery();

      if (rs.first()) {
        do {
          PaginaAziendaBean pab = new PaginaAziendaBean();
          pab.setDescrizione(rs.getString(1));
          pab.setLocalita(rs.getString(2));
          pab.setNomeAzienda(rs.getString(3));
          int id = rs.getInt(4);
          
          pab.setId(id);
          pab.setSkill(this.caricaSkill(id));
          pab.setAmbito(this.caricaAmbito(id));
          pabList.add(pab);
        } while (rs.next());
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    finally {
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
          }
          catch (SQLException e) {
            e.printStackTrace();
          }
        }
      } 
    }
    return pabList;
  }


  /**
   * Cerca nel db tutte le pagine azienda corrispondenti alla chiave per quella categoria.
   * @return lista di pagina azienda
   */
  public synchronized ArrayList<PaginaAziendaBean> ricerca(String categoria, String chiave)  {
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    ArrayList<PaginaAziendaBean> pabList = new ArrayList<PaginaAziendaBean>();

    try {
      connection = ds.getConnection();

      // categoria sta ad indicare un capo della tabella azienda (es. descrizione, località)
      // la chiave permette una ricerca dei valori in quel campo scelto

      String selectSql = "SELECT descrizione,localita,nomeaziendaRappresentata,id "
          + "FROM " + TABLE_NAME_PAGINA
          + " JOIN " + UtenteModel.TABLE_NAME_AZIENDA
          + " ON " + TABLE_NAME_PAGINA + ".mailAzienda = " 
          + UtenteModel.TABLE_NAME_AZIENDA + ".mail WHERE " + categoria + " LIKE ?";

      preparedStatement = connection.prepareStatement(selectSql);
      preparedStatement.setString(1, chiave);

      ResultSet rs = preparedStatement.executeQuery();

      if (rs.first()) {
        do {
          PaginaAziendaBean pab = new PaginaAziendaBean();
          pab.setDescrizione(rs.getString(1));
          pab.setLocalita(rs.getString(2));
          pab.setNomeAzienda(rs.getString(3));
          int id = rs.getInt(4);
          
          pab.setId(id);
          pab.setSkill(this.caricaSkill(id));
          pab.setAmbito(this.caricaAmbito(id));
          pabList.add(pab);
        } while (rs.next());
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    finally {
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
          }
          catch (SQLException e) {
            e.printStackTrace();
          }
        }
      } 
    }
    return pabList;
  }

  /**
   * Cerca nel db una pagina azienda per il suo id.
   * @return una pagina azienda
   */
  public synchronized PaginaAziendaBean ricerca(int id) 
      throws SQLException {
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    PaginaAziendaBean pab = null;

    try {
      connection = ds.getConnection();

      String selectSql = " SELECT descrizione,localita,nomeaziendarappresentata FROM " 
          + TABLE_NAME_PAGINA + " JOIN " + UtenteModel.TABLE_NAME_AZIENDA
          + " ON " + TABLE_NAME_PAGINA + ".mailAzienda = " 
          + UtenteModel.TABLE_NAME_AZIENDA + ".mail WHERE id = ? ";

      preparedStatement = connection.prepareStatement(selectSql);
      preparedStatement.setInt(1, id);

      ResultSet rs = preparedStatement.executeQuery();

      if (rs.first()) {
        pab = new PaginaAziendaBean();
        
        pab.setDescrizione(rs.getString(1));
        pab.setLocalita(rs.getString(2));
        pab.setNomeAzienda(rs.getString(3));
        pab.setId(id);
        pab.setAmbito(this.caricaAmbito(id));
        pab.setSkill(this.caricaSkill(id));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    finally {
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
          }
          catch (SQLException e) {
            e.printStackTrace();
          }
        }
      } 
    }
    return pab;
  }

  /**
   * Ricerca tutte le skill di una certa azienda.
   * @param id identificativo dell'azienda da ricercare
   * @return una lista di stringhe corrispondenti alle skill richieste dall'azienda
   * @throws SQLException in caso di errata connessione al database
   */
  private ArrayList<String> caricaSkill(int id) {
    ArrayList<String> daRestituire = new ArrayList<>();

    Connection connection = null;
    PreparedStatement preparedStatement = null;

    try {
      connection = ds.getConnection();

      String selectSql = "SELECT nomeSkill FROM " + TABLE_NAME_SKILL + " WHERE " 
          + TABLE_NAME_SKILL + ".paginaAziendaID = ? ";

      preparedStatement = connection.prepareStatement(selectSql);
      preparedStatement.setInt(1, id);
      ResultSet rs = preparedStatement.executeQuery();

      while (rs.next()) {
        daRestituire.add(rs.getString(1));

      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
    finally {
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
          }
          catch (SQLException e) {
            e.printStackTrace();
          }
        }
      } 
    }

    return daRestituire;
  }


  /**
   * Ricerca tutti gli ambiti di una certa azienda.
   * @param id identificativo dell'azienda da ricercare
   * @return una lista di stringhe corrispondenti ai vari ambiti di cui si occupa l'azienda
   * @throws SQLException in caso di errata connessione al database
   */
  private ArrayList<String> caricaAmbito(int id) {
    ArrayList<String> daRestituire = new ArrayList<>();

    Connection connection = null;
    PreparedStatement preparedStatement = null;

    try {
      connection = ds.getConnection();

      String selectSql = "SELECT nomeAmbito FROM " + TABLE_NAME_AMBITO + " WHERE " 
          + TABLE_NAME_AMBITO + ".paginaAziendaID = ? ";

      preparedStatement = connection.prepareStatement(selectSql);
      preparedStatement.setInt(1, id);
      ResultSet rs = preparedStatement.executeQuery();

      while (rs.next()) {
        daRestituire.add(rs.getString(1));

      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
    finally {
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
          }
          catch (SQLException e) {
            e.printStackTrace();
          }
        }
      } 
    }

    return daRestituire;
  }


  /**
   * Aggiunge una pagina nel db. 
   * @param localita sede dell'azienda
   * @param descrizione descrizione dell'azienda
   * @param nomeAzienda nome dell'azienda
   * @param ambito ambiti dove lavora l'azienda
   * @param skill skills richieste dall'azienda
   * @throws SQLException in caso di lettura errata dal database
   */
  public synchronized int aggiungiPagina(String localita, String descrizione, String email, 
      ArrayList<String> ambito, ArrayList<String> skill) {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    PreparedStatement preparedStatementSkill = null;
    PreparedStatement preparedStatementAmbito = null;

    PaginaAziendaBean pab = new PaginaAziendaBean();

    try {
      connection = ds.getConnection();
      String insertSqlPagAzienda = "INSERT INTO " + TABLE_NAME_PAGINA
          + " (localita, descrizione, mailAzienda) VALUES (?, ?, ?)";
      preparedStatement = connection.prepareStatement(insertSqlPagAzienda, 
          Statement.RETURN_GENERATED_KEYS);

      preparedStatement.setString(1, localita);
      preparedStatement.setString(2, descrizione);
      preparedStatement.setString(3, email);

      preparedStatement.executeUpdate();

      ResultSet rs = preparedStatement.getGeneratedKeys();
      rs.next();
      int autoId = rs.getInt(1);

      String insertSqlSkill = "INSERT INTO " + TABLE_NAME_SKILL
          + " (paginaAziendaID,nomeSkill) VALUES (?,?)";
      preparedStatementSkill = connection.prepareStatement(insertSqlSkill);

      for (String s: skill) {
        preparedStatementSkill.setInt(1, autoId);
        preparedStatementSkill.setString(2, s);

        preparedStatementSkill.executeUpdate();
      }

      String insertSqlAmbito = "INSERT INTO " + TABLE_NAME_AMBITO
          + " (paginaAziendaID, nomeAmbito) VALUES (?,?)";
      preparedStatementAmbito = connection.prepareStatement(insertSqlAmbito);

      for (String a: ambito) {
        preparedStatementAmbito.setInt(1, autoId);
        preparedStatementAmbito.setString(2, a);

        preparedStatementAmbito.executeUpdate();
      }

      return autoId;

    } catch (SQLException e) {
      e.printStackTrace();
    }
    finally {
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
          }
          catch (SQLException e) {
            e.printStackTrace();
          }
        }
      } 
    }
    return -1;
  }
}

