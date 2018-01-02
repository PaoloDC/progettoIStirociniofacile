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
  
  private static final String TABLE_NAME_PAGINA = "PaginaAzienda";
  private static final String TABLE_NAME_AMBITO = "Ambito";
  private static final String TABLE_NAME_SKILL = "Skill";

  /**
   * Cerca nel db tutte le pagine azienda.
   * @return lista di pagina azienda
   */
  private synchronized ArrayList<PaginaAziendaBean> ricerca() 
      throws SQLException {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    
    ArrayList<PaginaAziendaBean> pabList = new ArrayList<PaginaAziendaBean>();
    try {
      connection = ds.getConnection();
      String insertSql = "SELECT * FROM " + TABLE_NAME_PAGINA;
      preparedStatement = connection.prepareStatement(insertSql);
      
      ResultSet rs = preparedStatement.executeQuery();
      
      PaginaAziendaBean pab = new PaginaAziendaBean();
      
      while (rs.next()) {
        //TODO
        
        pabList.add(pab);
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
    return pabList;
  }
  
  /**
   * Cerca nel db tutte le pagine azienda corrispondenti alla chiave per quella categoria.
   * @return lista di pagina azienda
   */
  private synchronized ArrayList<PaginaAziendaBean> ricerca(String categoria, String chiave) 
      throws SQLException {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    
    ArrayList<PaginaAziendaBean> pabList = new ArrayList<PaginaAziendaBean>();
    
    try {
      connection = ds.getConnection();
      String insertSql = "SELECT * FROM " + TABLE_NAME_PAGINA 
          + " WHERE categoria = ? AND chiave LIKE '%?%'";
      
      preparedStatement = connection.prepareStatement(insertSql);
      preparedStatement.setString(1, categoria);
      preparedStatement.setString(2, chiave);
      
      ResultSet rs = preparedStatement.executeQuery();
      
      PaginaAziendaBean pab = new PaginaAziendaBean();
      
      
      while (rs.next()) {
        //TODO
        
        pabList.add(pab);
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
    return pabList;
  }
  
  /**
   * Cerca nel db una pagina azienda per il suo id.
   * @return una pagina azienda
   */
  private synchronized PaginaAziendaBean ricerca(String id) 
      throws SQLException {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    PaginaAziendaBean pab = new PaginaAziendaBean();
    try {
      connection = ds.getConnection();
      String insertSql = "SELECT * FROM " + TABLE_NAME_PAGINA + " WHERE ID = ?";
      preparedStatement = connection.prepareStatement(insertSql);
      preparedStatement.setString(1, id);
      
      ResultSet rs = preparedStatement.executeQuery();

      while (rs.next()) {
        //TODO
        
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
    return pab;
  }
  
 
  /**
   * Aggiunge una pagina nel db. 
   * @param localita sede dell'azienda
   * @param descrizione descrizione dell'azienda
   * @param nomeAzienda nome dell'azienda
   * @param ambito ambiti dove lavora l'azienda
   * @param skill skills richieste dall'azienda
   * @throws SQLException 
   */
  private synchronized void aggiungiPagina(String localita, String descrizione, String email, 
      ArrayList<String> ambito, ArrayList<String> skill) throws SQLException {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    PreparedStatement preparedStatementSkill = null;
    PreparedStatement preparedStatementJoinSkill = null;
    PreparedStatement preparedStatementAmbito = null;
    PreparedStatement preparedStatementJoinAmbito = null;
    
    PaginaAziendaBean pab = new PaginaAziendaBean();
    
    try {
      connection = ds.getConnection();
      String insertSqlPagAzienda = "INSERT INTO " + TABLE_NAME_PAGINA
          + " (località, descrizione, mailAzienda) VALUES (?, ?, ?)";
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
          + " (paginaAzienda,nomeSkill) VALUES (?,?)";
      preparedStatementSkill = connection.prepareStatement(insertSqlSkill);
      
      for (String s: skill) {
        //ID???
        preparedStatementSkill.setInt(1, autoId);
        preparedStatementSkill.setString(2, s);
        
        preparedStatementSkill.executeUpdate();
      }
      
      String insertSqlAmbito = "INSERT INTO " + TABLE_NAME_AMBITO
          + " (paginaAzienda, nomeAmbito) VALUES (?,?)";
      preparedStatementAmbito = connection.prepareStatement(insertSqlAmbito);
      
      for (String a: ambito) {
        preparedStatementAmbito.setInt(1, autoId);
        preparedStatementAmbito.setString(2, a);

        preparedStatementAmbito.executeUpdate();
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
    return;
  }
}

