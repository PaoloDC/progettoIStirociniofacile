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

  /**
   * Cerca nel db tutte le pagine azienda.
   * @return lista di pagina azienda
   */
  private synchronized ArrayList<PaginaAziendaBean> ricerca() {
    //TODO
    return null;
  }
  
  /**
   * Cerca nel db tutte le pagine azienda corrispondenti alla chiave per quella categoria.
   * @return lista di pagina azienda
   */
  private synchronized ArrayList<PaginaAziendaBean> ricerca(String categoria, String chiave) {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    try {
      connection = ds.getConnection();
      String insertSql = "SELECT * FROM " + TABLE_NAME_PAGINA;
      preparedStatement = connection.prepareStatement(insertSql);
      
      ResultSet rs = preparedStatement.executeQuery();
      
      PaginaAziendaBean pab = new PaginaAziendaBean();
      ArrayList<PaginaAziendaBean> pabList = new ArrayList<PaginaAziendaBean>();
      
      while (rs.next()) {
        //TODO
        
        pabList.add(pab);
      }
    }
    return pabList;
  }
  
  /**
   * Cerca nel db una pagina azienda per il suo id.
   * @return una pagina azienda
   */
  private synchronized PaginaAziendaBean ricerca(String id) {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    try {
      connection = ds.getConnection();
      String insertSql = "SELECT * FROM " + TABLE_NAME_PAGINA + " WHERE ID = ?";
      preparedStatement = connection.prepareStatement(insertSql);
      preparedStatement.setString(1, id);
      
      ResultSet rs = preparedStatement.executeQuery();
      
      PaginaAziendaBean pab = new PaginaAziendaBean();
      
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
  private synchronized void aggiungiPagina(String localita, String descrizione, String nomeAzienda, 
      ArrayList<String> ambito, ArrayList<String> skill) {
    //TODO
    return;
  }
}

