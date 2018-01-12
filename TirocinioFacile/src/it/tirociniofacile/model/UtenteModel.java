package it.tirociniofacile.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import it.tirociniofacile.bean.ProfiloAziendaBean;
import it.tirociniofacile.bean.ProfiloStudenteBean;
import it.tirociniofacile.bean.UtenteBean;

public class UtenteModel {
  
  private static DataSource ds;
  public static final int LUNGHEZZA_PASSWORD = 20;

  static {
    try {
      System.out.println("Qui ci arriva1");
      Context initCtx = new InitialContext();
      System.out.println("Qui ci arriva2");
      Context envCtx = (Context) initCtx.lookup("java:comp/env");
      System.out.println("Qui ci arriva3");
      ds = (DataSource) envCtx.lookup("jdbc/tirociniofacile");

    } catch (NamingException e) {
      System.out.println("Error PIPI:" + e.getMessage());
    }
  }

  private static final String TABLE_NAME_STUDENTE = "ProfiloStudente";
  private static final String TABLE_NAME_AZIENDA = "ProfiloAzienda";
  public static final String FILE_NAME = "utenti.dat";

  /**
   * Inserisce nel db un nuovo studente.
   * @param email email del nuovo studente da registrare
   * @param password password del nuovo studente da registrare
   * @param matricola matricola del nuovo studente da registrare
   * @throws SQLException eccezione lanciata in caso di record gi� esistente
   */
  public synchronized void salvaAccountStudente(String email, String password, String matricola) 
      throws SQLException {
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    try {
      connection = ds.getConnection();
      String insertSql = "INSERT INTO " + TABLE_NAME_STUDENTE
          + " (mail, password, matricola) VALUES (?, ?, ?)";
      preparedStatement = connection.prepareStatement(insertSql);
      preparedStatement.setString(1, email);
      preparedStatement.setString(2, password);
      preparedStatement.setString(3, matricola);

      preparedStatement.executeUpdate();
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

  /**
   * Inserisce nel db una nuova azienda.
   * @param email email della nuova azienda da registrare
   * @param password password della nuova azienda da registrare
   * @throws SQLException eccezione lanciata in caso di record gi� esistente
   */
  public synchronized void salvaAccountAzienda(String email, String password, String nomeazienda) 
      throws SQLException {
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    try {
      connection = ds.getConnection();
      String insertSql = "INSERT INTO " + TABLE_NAME_AZIENDA
          + " (mail, password, nomeAzienda) VALUES (?, ?, ?)";
      preparedStatement = connection.prepareStatement(insertSql);
      preparedStatement.setString(1, email);
      preparedStatement.setString(2, password);
      preparedStatement.setString(3, nomeazienda);

      preparedStatement.executeUpdate();
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

  /**
   * Salva le credenziali generate per un nuovo account amministrativo (presidente area didattica 
   * o impiegato ufficio tirocini).
   * @param email email da salvare nel file per il nuovo account amministrativo
   * @return true se genera correttamente le credenziali, false se l'utente esiste gi�
   */
  public synchronized boolean generaCredenziali(String email) {

    if (email.contains("@unisa.it")) {
      ArrayList<UtenteBean> listaUtenti = caricaUtentiDaFile();

      for (int i = 0; i < listaUtenti.size(); i++) {
        if (listaUtenti.get(i).getEmail().equalsIgnoreCase(email)) {
          return false;
        }
      }

      Random gen = new Random();

      String maiuscole = "QWERTYUIOPASDFGHJKLZXCVBNM";
      String minuscole = "qwertyuiopasdfghjklzxcvbnm";
      String cifre = "0123456789";

      String nuovaPassword = "";
      while (nuovaPassword.length() < LUNGHEZZA_PASSWORD) {
        nuovaPassword += maiuscole.substring(gen.nextInt(maiuscole.length()));
        nuovaPassword += minuscole.substring(gen.nextInt(minuscole.length()));
        nuovaPassword += cifre.substring(gen.nextInt(cifre.length()));
      }

      listaUtenti.add(new UtenteBean(email,nuovaPassword));
      this.salvaUtentiNelFile(listaUtenti);

      String mailMittente = Email.USER_NAME;
      String passwordMittente = Email.PASSWORD;
      String[] destinari = { email }; // list of recipient email addresses
      String oggetto = "Nuova Password Tirocinio Facile";
      String corpo = "Salve utente, questa � la sua nuova password per accedere alla piattaforma"
          + " tirocinio facile: ' " + nuovaPassword + " '.\nBuona navigazione.";

      Email.sendFromGMail(mailMittente, passwordMittente, destinari, oggetto, corpo);
      return true;

    }
    return false;
  }

  /**
   * Carica tutti gli utenti dal file.
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
   * @param listaUtenti lista degli utenti da salvare
   */
  public void salvaUtentiNelFile(ArrayList<UtenteBean> listaUtenti) {
    try {      
      ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME));
      out.writeObject(listaUtenti);
      out.close();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Carica email e password da un db.
   * @param email email dell'account da cercare nel db
   * @param password password dell'account da ricercare nel db
   * @return ritorna un bean che rappresenta un utente
   * @throws SQLException in caso di errore di connesione al db 
   */
  public synchronized UtenteBean caricaAccount(String email, String password) {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    try {
      connection = ds.getConnection();

      String selectSqlStudente = "SELECT * FROM " + TABLE_NAME_STUDENTE 
          + " WHERE mail  = ? AND password = ? ";
      preparedStatement = connection.prepareStatement(selectSqlStudente);
      preparedStatement.setString(1, email);
      preparedStatement.setString(2, password);
      ResultSet rsStudente = preparedStatement.executeQuery();

      ProfiloStudenteBean ps = new ProfiloStudenteBean();

      if (rsStudente.first()) {
        while (rsStudente.next()) {
          ps.setEmail(rsStudente.getString(1));
          ps.setPassword(rsStudente.getString(2)); 
          ps.setMatricola(rsStudente.getString(3));
        }  

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
          while (rsAzienda.next()) {
            pa.setEmail(rsAzienda.getString(1));
            pa.setPassword(rsAzienda.getString(2));
            pa.setNomeAzienda(rsAzienda.getString(3));
          }

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
    
    return null;
  }



  /**
   * Cerca la password di un utente dalla email e la invia alla mail.
   * @param email email dell'account da cercare nel db
   */
  public synchronized void cercaAccountPerEmail(String email) {
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    String passwordDaInviare = null;

    //cerca la mail tra gli utenti amministrativi
    ArrayList<UtenteBean> listaUtenti = caricaUtentiDaFile();
    for (int i = 0; i < listaUtenti.size() && passwordDaInviare != null; i++) {
      UtenteBean ub = listaUtenti.get(i);
      if (ub.getEmail().equalsIgnoreCase(email)) {
        passwordDaInviare = ub.getPassword();
      }
    }
    //se non trova la password tra gli utenti amministrativi la cerca tra gli studenti e le aziende
    if (passwordDaInviare == null) {
      try {
        connection = ds.getConnection();

        String selectSql = "SELECT password FROM " + TABLE_NAME_STUDENTE 
            + " JOIN " + TABLE_NAME_AZIENDA + " WHERE mail  = ? ";

        preparedStatement = connection.prepareStatement(selectSql);
        preparedStatement.setString(1, email);
        ResultSet rs = preparedStatement.executeQuery();

        if (rs.first()) {
          passwordDaInviare = rs.getString(1);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    if (passwordDaInviare == null) { 
      //TODO 
      //eccezione utente non presente
    } else {
      String mailMittente = Email.USER_NAME;
      String passwordMittente = Email.PASSWORD;
      String[] destinari = { email }; // list of recipient email addresses
      String oggetto = "Recupera Password Tirocinio Facile";
      String corpo = "Salve utente, questa mail le � stata inviata per sua richiesta di "
          + "recupero passowrd.\nLa sua password per accedere alla piattaforma"
          + " tirocinio facile �: ' " + passwordDaInviare + " '.\n\nBuona navigazione.";

      Email.sendFromGMail(mailMittente, passwordMittente, destinari, oggetto, corpo);
    }
  }


  /**
   * Classe interna per l'invio di una mail con le nuove credenziali generate.
   * @author Paolo De Cristofaro
   */
  public static class Email {

    //variabili di istanza, parametri della mail utilizzata
    private static final String USER_NAME = "tirociniofacile"; 
    private static final String PASSWORD = "tirociniofacile12"; 

    /**
     * Metodo per inviare una email.
     * @param from email mittente
     * @param pass password mittente
     * @param to destinatario
     * @param subject oggetto della mail
     * @param body corpo della mail
     */
    public static void sendFromGMail(String from, String pass, String[] to, 
        String subject, String body) {
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


}

