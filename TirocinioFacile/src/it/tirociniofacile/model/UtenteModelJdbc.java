package it.tirociniofacile.model;

import com.mysql.jdbc.Statement;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import it.tirociniofacile.bean.ProfiloAziendaBean;
import it.tirociniofacile.bean.ProfiloStudenteBean;
import it.tirociniofacile.bean.UtenteBean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Classe di supporto per il testing, espone gli stessi metodi della classe UtenteModel, 
 * ma esegue una connessione diretta al database.
 * @author Paolo De Cristofaro
 *
 */
public class UtenteModelJdbc {

  //variabili di istanza
  private static Statement stmt;
  private static Connection con;
  public static final int LUNGHEZZA_PASSWORD = 20;
  private static final String TABLE_NAME_STUDENTE = "ProfiloStudente";
  private static final String TABLE_NAME_AZIENDA = "ProfiloAzienda";
  public static final String FILE_NAME = "utenti.dat";  

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
   * Inserisce nel db un nuovo studente.
   * 
   * @param email
   *          email del nuovo studente da registrare
   * @param password
   *          password del nuovo studente da registrare
   * @param matricola
   *          matricola del nuovo studente da registrare
   */
  public synchronized boolean salvaAccountStudente(String email, String password,
      String matricola) {
    Connection connection = con;
    PreparedStatement preparedStatement = null;

    try {
      String insertSql = "INSERT INTO " + TABLE_NAME_STUDENTE
          + " (mail, password, matricola) VALUES (?, ?, ?)";
      preparedStatement = connection.prepareStatement(insertSql);
      preparedStatement.setString(1, email);
      preparedStatement.setString(2, password);
      preparedStatement.setString(3, matricola);

      try {
        preparedStatement.executeUpdate();
      } catch (MySQLIntegrityConstraintViolationException e) {
        System.out.println("Entry duplicata per studente con email: " + email);
        return false;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return true;
  }


  /**
   * Inserisce nel db una nuova azienda.
   * @param email email della nuova azienda da registrare
   * @param password password della nuova azienda da registrare
   * @param nomeazienda il nome dell'azienda
   * @return
   */
  public synchronized boolean salvaAccountAzienda(String email, String password,
      String nomeazienda) {
    Connection connection = con;
    PreparedStatement preparedStatement = null;

    try {
      String insertSql = "INSERT INTO " + TABLE_NAME_AZIENDA
          + " (mail, password, nomeAziendaRappresentata) VALUES (?, ?, ?)";
      preparedStatement = connection.prepareStatement(insertSql);
      preparedStatement.setString(1, email);
      preparedStatement.setString(2, password);
      preparedStatement.setString(3, nomeazienda);
      
      preparedStatement.executeUpdate();
      
    } catch (MySQLIntegrityConstraintViolationException e) {
      System.out.println("Entry duplicata per azienda con email: " + email);
      return false;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return true;
  }

  /**
   * Carica tutti gli utenti dal file.
   * 
   * @return un arraylist contenente tutti gli utenti presenti sul file
   */
  public ArrayList<UtenteBean> caricaUtentiDaFile() {
    try {

      File f = new File(FILE_NAME);
      if (f.exists()) {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(f));
        ArrayList<UtenteBean> listaUtentiRead = (ArrayList<UtenteBean>) in.readObject();
        in.close();
        
        return listaUtentiRead;
      } else {
        return new ArrayList<>();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Salva tutti gli utenti nel file.
   * 
   * @param listaUtenti
   *          lista degli utenti da salvare
   */
  public void salvaUtentiNelFile(ArrayList<UtenteBean> listaUtenti) {
    try {
      
      File file = new File(FILE_NAME);
      file.delete();
      file.createNewFile();
      ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
      out.writeObject(listaUtenti);
      out.close();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Carica email e password da un db.
   * 
   * @param email
   *          email dell'account da cercare nel db
   * @param password
   *          password dell'account da ricercare nel db
   * @return ritorna un bean che rappresenta un utente
   */
  public synchronized UtenteBean caricaAccount(String email, String password) {
    Connection connection = con;
    PreparedStatement preparedStatement = null;
    try {

      String selectSqlStudente = "SELECT * FROM " + TABLE_NAME_STUDENTE
          + " WHERE mail  = ? AND password = ? ";
      preparedStatement = connection.prepareStatement(selectSqlStudente);
      preparedStatement.setString(1, email);
      preparedStatement.setString(2, password);
      ResultSet rsStudente = preparedStatement.executeQuery();

      ProfiloStudenteBean ps = new ProfiloStudenteBean();

      if (rsStudente.first()) {
        ps.setEmail(rsStudente.getString(1));
        ps.setPassword(rsStudente.getString(2));
        ps.setMatricola(rsStudente.getString(3));

        return ps;
      } else {

        String selectSqlAzienda = "SELECT * FROM " + TABLE_NAME_AZIENDA
            + " WHERE mail  = ? AND password = ? ";

        preparedStatement.close();
        preparedStatement = connection.prepareStatement(selectSqlAzienda);
        preparedStatement.setString(1, email);
        preparedStatement.setString(2, password);
        ResultSet rsAzienda = preparedStatement.executeQuery();

        ProfiloAziendaBean pa = new ProfiloAziendaBean();

        if (rsAzienda.first()) {
          pa.setEmail(rsAzienda.getString(1));
          pa.setPassword(rsAzienda.getString(2));
          pa.setNomeAzienda(rsAzienda.getString(3));

          return pa;
        } else {

          ArrayList<UtenteBean> listaUtenti = caricaUtentiDaFile();
          for (int i = 0; i < listaUtenti.size(); i++) {
            UtenteBean ub = listaUtenti.get(i);
            if (ub.getEmail().equalsIgnoreCase(email) && ub.getPassword().equals(password)) {
              return ub;
            }
          }

        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } 

    return null;
  }

  /**
   * Cerca la password di un utente dalla email e la invia alla mail.
   * 
   * @param email
   *          email dell'account da cercare nel db
   * @return true se l'utente � presente, false altrimenti
   */
  public synchronized boolean cercaAccountPerEmail(String email) {
    Connection connection = con;
    PreparedStatement preparedStatement = null;

    String passwordDaInviare = null;

    // cerca la mail tra gli utenti amministrativi
    ArrayList<UtenteBean> listaUtenti = caricaUtentiDaFile();
    for (int i = 0; i < listaUtenti.size() && passwordDaInviare != null; i++) {
      UtenteBean ub = listaUtenti.get(i);
      if (ub.getEmail().equalsIgnoreCase(email)) {
        passwordDaInviare = ub.getPassword();
      }
    }

    // se non trova la password tra gli utenti amministrativi la cerca tra gli studenti e le aziende
    if (passwordDaInviare == null) {
      try {

        String selectSql = "SELECT password FROM " + TABLE_NAME_STUDENTE + " WHERE mail  = ? ";

        preparedStatement = connection.prepareStatement(selectSql);
        preparedStatement.setString(1, email);
        ResultSet rs = preparedStatement.executeQuery();

        if (rs.first()) {
          passwordDaInviare = rs.getString(1);
        } else {

          selectSql = "SELECT password FROM " + TABLE_NAME_AZIENDA + " WHERE mail  = ? ";
          preparedStatement = connection.prepareStatement(selectSql);
          preparedStatement.setString(1, email);
          rs = preparedStatement.executeQuery();
          if (rs.first()) {
            passwordDaInviare = rs.getString(1);
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    if (passwordDaInviare == null) {
      return false;
      // eccezione utente non presente
    } else {
      String mailMittente = Email.USER_NAME;
      String passwordMittente = Email.PASSWORD;
      String[] destinari = { email }; // lista dei destinatari
      String oggetto = "Recupera Password Tirocinio Facile";
      String corpo = "Salve utente, questa mail le � stata inviata per sua richiesta di "
          + "recupero password.\nLa sua password per accedere alla piattaforma"
          + " tirocinio facile �: ' " + passwordDaInviare + " '.\n\nBuona navigazione.";

      Email.sendFromGMail(mailMittente, passwordMittente, destinari, oggetto, corpo);
      return true;
    }
  }

  /**
   * Classe interna per l'invio di una mail per il recupero delle credenziali.
   * 
   * @author Paolo De Cristofaro
   */
  public static class Email {

    // variabili di istanza, parametri della mail utilizzata
    private static final String USER_NAME = "tirociniofacile";
    private static final String PASSWORD = "tirociniofacile12";

    /**
     * Metodo per inviare una email.
     * 
     * @param from
     *          email mittente
     * @param pass
     *          password mittente
     * @param to
     *          destinatario
     * @param subject
     *          oggetto della mail
     * @param body
     *          corpo della mail
     */
    public static void sendFromGMail(String from, String pass, String[] to, String subject,
        String body) {
      Properties props = System.getProperties();
      String host = "smtp.gmail.com";
      props.put("mail.smtp.starttls.enable", "true");
      props.put("mail.smtp.host", host);
      props.put("mail.smtp.user", from);
      props.put("mail.smtp.password", pass);
      props.put("mail.smtp.port", "587");
      props.put("mail.smtp.auth", "true");

      Session session = Session.getDefaultInstance(props);
      MimeMessage message = new MimeMessage(session);
      try {
        message.setFrom(new InternetAddress(from));
        InternetAddress[] toAddress = new InternetAddress[to.length];

        // To get the array of addresses
        for (int i = 0; i < to.length; i++) {
          toAddress[i] = new InternetAddress(to[i]);
        }

        for (int i = 0; i < toAddress.length; i++) {
          message.addRecipient(Message.RecipientType.TO, toAddress[i]);
        }

        message.setSubject(subject);
        message.setText(body);
        Transport transport = session.getTransport("smtp");
        transport.connect(host, from, pass);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();

        System.out.println("MAIL INVIATA");
      } catch (AddressException ae) {
        ae.printStackTrace();
      } catch (MessagingException me) {
        me.printStackTrace();
      }
    }
  }

  /**
   * elimina un profilo studente, usato per test.
   * @param s profilo studente da eliminare
   */
  public synchronized void eliminaProfiloStudente(ProfiloStudenteBean s) {
    Connection connection = con;
    PreparedStatement preparedStatement = null;

    try {
      String deleteSql = "DELETE FROM " + TABLE_NAME_STUDENTE + " WHERE mail = ? ";
      preparedStatement = connection.prepareStatement(deleteSql);
      preparedStatement.setString(1, s.getEmail());
      preparedStatement.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }   
  }

  /**
   * elimina un utente dal file, usato per test.
   * @param u utente da eliminare
   */
  public synchronized void eliminaAccountAmministrativo(UtenteBean u) {
    ArrayList<UtenteBean> lista = this.caricaUtentiDaFile();
    if (lista.contains(u)) {
      lista.remove(u);
    }
    this.salvaUtentiNelFile(lista);
  }
  
  /**
   * Cancella un account azienda.
   * 
   * @param email
   *          identificativo dell'account
   */
  public synchronized void cancellaAccountAzienda(String email) {
    try {
      Connection connection = con;
      PreparedStatement preparedStatement = null;
      
      String deleteSql = "DELETE FROM " + UtenteModel.TABLE_NAME_AZIENDA + " WHERE mail = ?";
      preparedStatement = connection.prepareStatement(deleteSql);
      preparedStatement.setString(1, email);

      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}