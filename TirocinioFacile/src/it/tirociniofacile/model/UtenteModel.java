package it.tirociniofacile.model;

import it.tirociniofacile.bean.ProfiloAziendaBean;
import it.tirociniofacile.bean.ProfiloStudenteBean;
import it.tirociniofacile.bean.UtenteBean;

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



public class UtenteModel {
  private static DataSource ds;
  public static final int LUNGHEZZA_PASSWORD = 20;
  
  static {
    try {
      Context initCtx = new InitialContext();
      Context envCtx = (Context) initCtx.lookup("java:comp/env");

      ds = (DataSource) envCtx.lookup("jdbc/tirociniofacile");
    } catch (NamingException e) {
      System.out.println("Error:" + e.getMessage());
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
   * @throws SQLException eccezione lanciata in caso di record già esistente
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
   * @throws SQLException eccezione lanciata in caso di record già esistente
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
   */
  public synchronized void generaCredenziali(String email) {
        
    if (email.contains("@unisa.it")) {
      ArrayList<UtenteBean> listaUtenti = caricaUtentiDaFile();
      boolean trovato = false;
      for (int i = 0; i < listaUtenti.size() && !trovato; i++) {
        if (listaUtenti.get(i).getEmail().equalsIgnoreCase(email)) {
          trovato = true;
        }
      }
      
      if (trovato) {
        this.cercaAccountPerEmail(email); 
        //se trova una corrispondenza viene avviata la procedura di recupera password
      } else {
        
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
        String corpo = "Salve utente, questa è la sua nuova password per accedere alla piattaforma"
            + " tirocinio facile: ' " + nuovaPassword + " '.\nBuona navigazione.";
      
        Email.sendFromGMail(mailMittente, passwordMittente, destinari, oggetto, corpo);
      
      } 
    }
  }
  
  /**
   * Carica tutti gli utenti dal file.
   * @return un arraylist contenente tutti gli utenti presenti sul file
   */
  public ArrayList<UtenteBean> caricaUtentiDaFile() {
    try {
      
      ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME));
      ArrayList<UtenteBean> listaUtenti = (ArrayList<UtenteBean>) in.readObject();
      in.close();
      return listaUtenti;
      
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
   * @param tipo indica il tio di utente da ricercare
   * @return ritorna un bean che rappresenta un utente
   * @throws SQLException in caso di errore di connesione al db 
   */
  public synchronized UtenteBean caricaAccount(String email, int tipo) throws SQLException {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    try {
      connection = ds.getConnection();
      if (tipo == 0) {   
        String insertSql = "SELECT * FROM " + TABLE_NAME_STUDENTE + " WHERE mail  = ? ";
        preparedStatement = connection.prepareStatement(insertSql);
        preparedStatement.setString(1, email);
        ResultSet rs = preparedStatement.executeQuery();
        
        ProfiloStudenteBean ps = new ProfiloStudenteBean();
        
        while (rs.next()) {
          ps.setEmail(rs.getString(1));
          ps.setPassword(rs.getString(2)); 
          ps.setMatricola(rs.getString(3));
        }  
        
        return ps;
        
      } else if (tipo == 1) {  
        
        String insertSql = "SELECT * FROM " + TABLE_NAME_AZIENDA + " WHERE mail  = ? ";
        preparedStatement = connection.prepareStatement(insertSql);
        preparedStatement.setString(1, email);
        ResultSet rs = preparedStatement.executeQuery();
        ProfiloAziendaBean pa = new ProfiloAziendaBean();
        
        while (rs.next()) {
          pa.setEmail(rs.getString(1));
          pa.setPassword(rs.getString(2));
          pa.setNomeAzienda(rs.getString(3));
        }
        
        return pa;
        
      } else if (tipo == 2) {
        ArrayList<UtenteBean> listaUtenti = caricaUtentiDaFile();
        for (int i = 0; i < listaUtenti.size(); i++) {
          if (listaUtenti.get(i).getEmail().equalsIgnoreCase(email)) {
            return listaUtenti.get(i);
          }
        }
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
    return null;
  }
  
  /**
   * Cerca la password di un utente dalla email e la invia alla mail.
   * @param email email dell'account da cercare nel db
   */
  public synchronized void cercaAccountPerEmail(String email) {
    try {
      UtenteBean ub = caricaAccount(email, 0);
      if (ub == null) {
        ub = caricaAccount(email, 1);
      } else if (ub == null) {
        ub = caricaAccount(email, 2);
      }
      
      if (ub == null) {
        //utente non esiste nel database
      } else {
        String mailMittente = Email.USER_NAME;
        String passwordMittente = Email.PASSWORD;
        String[] destinari = { email }; // list of recipient email addresses
        String oggetto = "Recupera Password Tirocinio Facile";
        String corpo = "Salve utente, questa mail le è stata inviata per sua richiesta di "
            + "recupero passowrd.\nLa sua password per accedere alla piattaforma"
            + " tirocinio facile è: ' " + ub.getPassword() + " '.\n\nBuona navigazione.";
      
        Email.sendFromGMail(mailMittente, passwordMittente, destinari, oggetto, corpo);
      }
    } catch (SQLException e) {
      e.printStackTrace();
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

