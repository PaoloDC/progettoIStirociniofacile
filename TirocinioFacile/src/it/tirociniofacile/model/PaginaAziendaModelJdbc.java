package it.tirociniofacile.model;

import com.mysql.jdbc.Statement;

import it.tirociniofacile.bean.PaginaAziendaBean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Classe di supporto per il testing, espone gli stessi metodi della classe PaginaAziendaModel, 
 * ma esegue una connessione diretta al database.
 * @author Paolo De Cristofaro
 *
 */
public class PaginaAziendaModelJdbc {
  
  //variabili di istanza
  private static Statement stmt;
  private static Connection con;
  public static final int LUNGHEZZA_PASSWORD = 20;
  public static final String TABLE_NAME_PAGINA = "PaginaAzienda";
  public static final String TABLE_NAME_AMBITO = "Ambito";
  public static final String TABLE_NAME_SKILL = "Skill";

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
   * Cerca nel db tutte le pagine azienda.
   * @return lista di pagina azienda
   */
  public synchronized ArrayList<PaginaAziendaBean> ricerca() {
    Connection connection = con;
    PreparedStatement preparedStatement = null;

    ArrayList<PaginaAziendaBean> pabList = new ArrayList<PaginaAziendaBean>();
    try {
      
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
    return pabList;
  }



  /**
   * Cerca nel db tutte le pagine azienda corrispondenti alla chiave per quella categoria.
   * @param categoria un parametro per cui ricercare (deve essere: località, nome, ambito, skill)
   * @param chiave sequenza di caratteri per effettuare la ricerca
   * @return un arraylist di paginaAziendaBean che soddisfano la ricerca
   */
  public synchronized ArrayList<PaginaAziendaBean> ricerca(String categoria, String chiave)  {
    Connection connection = con;
    PreparedStatement preparedStatement = null;

    ArrayList<PaginaAziendaBean> pabList = new ArrayList<PaginaAziendaBean>();

    try {

      // categoria sta ad indicare un capo della tabella azienda (es. descrizione, località)
      // la chiave permette una ricerca dei valori in quel campo scelto

      String selectSql = "SELECT DISTINCT descrizione,localita,nomeaziendaRappresentata,id "
          + "FROM " + TABLE_NAME_PAGINA
          + " JOIN " + UtenteModel.TABLE_NAME_AZIENDA
          + " ON " + TABLE_NAME_PAGINA + ".mailAzienda = " 
          + UtenteModel.TABLE_NAME_AZIENDA + ".mail WHERE " + categoria + " LIKE ? ";

      if (categoria.equals("skill")) {
        selectSql = "SELECT DISTINCT descrizione,localita,nomeaziendaRappresentata,id,nomeSkill " 
                    + "FROM " + TABLE_NAME_PAGINA
                    + " JOIN " + UtenteModel.TABLE_NAME_AZIENDA
                    + " ON " + TABLE_NAME_PAGINA + ".mailAzienda = " 
                    + UtenteModel.TABLE_NAME_AZIENDA + ".mail"
                    + " JOIN skill ON "
                    + TABLE_NAME_PAGINA + ".id = skill.paginaAziendaID " 
                    + " WHERE nomeSkill LIKE ? GROUP BY id";
        
        
      }
      
      if (categoria.equals("ambito")) {
        selectSql = "SELECT DISTINCT descrizione,localita,nomeaziendaRappresentata,id,nomeAmbito " 
            + "FROM " + TABLE_NAME_PAGINA
            + " JOIN " + UtenteModel.TABLE_NAME_AZIENDA
            + " ON " + TABLE_NAME_PAGINA + ".mailAzienda = "
            + UtenteModel.TABLE_NAME_AZIENDA + ".mail"
            + " JOIN ambito ON "
            + TABLE_NAME_PAGINA + ".id = ambito.paginaAziendaID " 
            + " WHERE nomeAmbito LIKE ? GROUP BY id";
        
      }
      
      preparedStatement = connection.prepareStatement(selectSql);
      preparedStatement.setString(1, "%" + chiave + "%");
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
    return pabList;
  }

  /**
   * Cerca nel db una pagina azienda per il suo id.
   * @return una pagina azienda
   */
  public synchronized PaginaAziendaBean ricerca(int id) {
    Connection connection = con;
    PreparedStatement preparedStatement = null;

    PaginaAziendaBean pab = null;

    try {

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

    Connection connection = con;
    PreparedStatement preparedStatement = null;

    try {

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

    Connection connection = con;
    PreparedStatement preparedStatement = null;

    try {

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

    return daRestituire;
  }


  /**
   * Aggiunge una pagina nel db.
   * @param localita sede dell'azienda
   * @param descrizione descrizione dell'azienda
   * @param email mail dell'account azienda
   * @param ambito dove lavora l'azienda
   * @param skill skills richieste dall'azienda
   * @return -1 in caso di errore di salvataggio sul db, altrimenti l'intero 
   */
  public synchronized int aggiungiPagina(String localita, String descrizione, String email, 
      ArrayList<String> ambito, ArrayList<String> skill) {
    
    Connection connection = con;
    PreparedStatement preparedStatement = null;
    PreparedStatement preparedStatementSkill = null;
    PreparedStatement preparedStatementAmbito = null;

    try {
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

      //Query per avvalorare il campo paginaAziendaID della convenzione
      String updateSql = "UPDATE " + DocumentoModel.TABLE_NAME_CONVENZIONI 
          + " SET paginaAziendaID = ? WHERE nomeAzienda = ( SELECT nomeAziendaRappresentata FROM "
          + UtenteModel.TABLE_NAME_AZIENDA + " WHERE mail = ? ); ";
      
      PreparedStatement preparedStatementUpdate = connection.prepareStatement(updateSql);
      preparedStatementUpdate.setInt(1, autoId);
      preparedStatementUpdate.setString(2, email);
      preparedStatementUpdate.executeUpdate(); 
      
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

    } catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e) {
      //entry duplicata
      return -1;
    } catch (SQLException e) {
      e.printStackTrace();
    } 
    return -2;
  }
  
  /**
   * elimina una pagina azienda, metodo per test.
   * @param id id della pagina da eliminare
   */
  public void eliminaPagina(int id) {
    Connection connection = con;
    PreparedStatement preparedStatement = null;

    String deleteSql = "delete FROM " + TABLE_NAME_PAGINA + " where id = ? ";
    
    try {
      
      preparedStatement = connection.prepareStatement(deleteSql);
      preparedStatement.setInt(1, id);
      preparedStatement.executeUpdate();
      
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  
  
}

