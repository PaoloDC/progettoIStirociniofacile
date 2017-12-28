package it.tirociniofacile.model;

import it.tirociniofacile.bean.PaginaAziendaBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
    //TODO
    return null;
  }
  
  /**
   * Cerca nel db una pagina azienda per il suo id.
   * @return una pagina azienda
   */
  private synchronized PaginaAziendaBean ricerca(String id) {
    //TODO
    return null;
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

