package it.tirociniofacile.control;

import it.tirociniofacile.model.UtenteModel;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/** .
 * Servlet implementation class GestioneUtente
 */
@WebServlet("/GestioneUtente")
public class GestioneUtente extends HttpServlet {
  private static final long serialVersionUID = 1L;
  static UtenteModel model;
  
  static {
    model = new UtenteModel();
  }
       
  /** .
     * @see HttpServlet#HttpServlet()
     */
  public GestioneUtente() {
        super();
  }
  
  /**
   * Esempio di commento, ci vuole il punto finale.
   * ogni riga massimo 100 caratteri
   * spazio sopra e sotto
   * @param request richiesta
   * @param response risposta
   */
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws ServletException, IOException {
    doPost(request,response);
  }

  /**
   * Il metodo doPost permette di .
   * @param request richiesta
   * @param response risposta
   */
  
  protected void doPost(HttpServletRequest request, HttpServletResponse response) 
      throws ServletException, IOException {
    doGet(request, response);
    HttpSession session = request.getSession();
    String action = request.getParameter("action");
    
    System.out.println(action);
    try {
      if (action != null) {
        if (action.equals("log-out")) {
          synchronized (session) {
            session.invalidate();
          }
        } else if (action.equals("registrazioneStudente")) {
          registrazioneStudente(request);
          
        } else if (action.equals("registrazioneAziendatAzienda")) {
          registrazioneAzienda(request);
          
        } else if (action.equals("generaCredenziali")) {
          generaCredenziali(request);
          
 
        } else if (action.equals("log-in")) { 
          logIn(request);
          
        } else if (action.equals("recuperaPassword")) { 
          recuperaPassword(request);
        }
      }
    } catch(SQLException e) {
      e.printStackTrace();
    }
    
    /*manca il dispatcher per caricare le pagine jsp */
    
  }

  /**
   * RegistrazioneStudente effettua la registrazione di un account studente.
   * @param request richiesta http
   * @throws SQLException eccezzione sql
   */
  public void registrazioneStudente(HttpServletRequest request) 
      throws SQLException {
    String email = (request.getParameter("email"));
    String password = (request.getParameter("password"));
    String matricola = (request.getParameter("matricola"));
    model.salvaAccountStudente(email, password, matricola);
  }
  
  /**
   * RegistrazioneAzienda effettua la registrazione di un account azienda.
   * @param request richiesta http
   * @throws SQLException eccezzione sql
   */
  public void registrazioneAzienda(HttpServletRequest request)
       throws SQLException {
    String email = (request.getParameter("email"));
    String password = (request.getParameter("password"));
    String nomeazienda = (request.getParameter("nomeazienda"));
    model.salvaAccountAzienda(email, password, nomeazienda);
  }
  
  /**
   * Genera nuove credenziali per gli utenti Impiegati uff. tirocini e presidente area didattica
   * @param request richiesta http
   * @throws SQLException eccezzione sql
   */
  public void generaCredenziali(HttpServletRequest request) 
      throws SQLException {
    String email = (request.getParameter("email"));
    model.generaCredenziali(email);
  }
  
  /**
   * Effettua la log in.
   * @param request richiesta http
   * @throws SQLException eccezzione sql
   */
  public void logIn(HttpServletRequest request)  // aggiustare log in , attenzione alla sessione, usare modo per capire di che tipo di utente.
      throws SQLException {
    String email = (request.getParameter("email"));
    String password = (request.getParameter("password"));  
    request.removeAttribute("account");
    request.setAttribute("account",  model.caricaAccount(email, password));
  }
  
  /**
   * Recupera password invia una mail alla mail inviata contenente una nuova password.
   * @param request richiesta http
   * @throws SQLException eccezzione sql
   */
  public void recuperaPassword(HttpServletRequest request) 
     throws SQLException {
    String email = (request.getParameter("email"));
    model.cercaAccountPerEmail(email);
  }
}
