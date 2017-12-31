package it.tirociniofacile.model;

import it.tirociniofacile.bean.PaginaAziendaBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
  private static final String TABLE_NAME_JOINAMBITO = "PaginaAziendaAmbito";
  private static final String TABLE_NAME_JOINSKILL = "PaginaAziendaSkill";
  

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
   */
  private synchronized void aggiungiPagina(String localita, String descrizione, String email, 
      ArrayList<String> ambito, ArrayList<String> skill) {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    PaginaAziendaBean pab = new PaginaAziendaBean();
    
    try {
      connection = ds.getConnection();
      String insertSqlPagAzienda = "INSERT INTO " + TABLE_NAME_PAGINA
          + " (id, località, descrizione, profiloAzienda) VALUES (?, ?, ?, ?)";
      preparedStatement = connection.prepareStatement(insertSqlPagAzienda);
      //ID???
      preparedStatement.setInt(1, x);
      preparedStatement.setString(2, localita);
      preparedStatement.setString(3, descrizione);
      preparedStatement.setString(4, email);

      preparedStatement.executeUpdate();
      
      for (String s: skill) {
        String insertSqlSkill = "INSERT INTO " + TABLE_NAME_SKILL
            + " (id, nome) VALUES (?, ?)";
        preparedStatement = connection.prepareStatement(insertSqlSkill);
      
        //ID???
        preparedStatement.setInt(1, y);
        preparedStatement.setString(2, s);
      
      
        String insertSqlJoinSkill = "INSERT INTO " + TABLE_NAME_JOINSKILL
            + " (paginaAzienda, skill) VALUES (?, ?)";
        preparedStatement = connection.prepareStatement(insertSqlSkill);
      
        //ID???
        preparedStatement.setInt(1, x);
        preparedStatement.setInt(2, y);
      
        preparedStatement.executeUpdate();
      }
      
      for (String a: ambito) {
        String insertSqlSkill = "INSERT INTO " + TABLE_NAME_AMBITO
            + " (id, nome) VALUES (?, ?)";
        preparedStatement = connection.prepareStatement(insertSqlSkill);
      
        //ID???
        preparedStatement.setInt(1, y);
        preparedStatement.setString(2, a);
      
      
        String insertSqlJoinSkill = "INSERT INTO " + TABLE_NAME_JOINAMBITO
            + " (paginaAzienda, skill) VALUES (?, ?)";
        preparedStatement = connection.prepareStatement(insertSqlSkill);
      
        //ID???
        preparedStatement.setInt(1, x);
        preparedStatement.setInt(2, y);
      
        preparedStatement.executeUpdate();
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

