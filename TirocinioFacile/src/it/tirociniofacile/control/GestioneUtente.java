package it.tirociniofacile.control;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.tirociniofacile.model.UtenteModel;

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
    response.getWriter().append("Served at: ").append(request.getContextPath());
    HttpSession session = request.getSession();
    String action = request.getParameter("action");
    
    try {
      if (action != null) {
        if (action.equals("log-out")) {
          synchronized (session) {
            session.invalidate();
          }
        } else if (action.equals("registrazioneStudente")) {
          registrazioneStudente(request);
          
              
        } else if (action.equals("salvaAccountAzienda")) {
          String email = (request.getParameter("email"));
          String password = (request.getParameter("password"));
          String nomeazienda = (request.getParameter("nomeazienda"));
          model.salvaAccountAzienda(email, password, nomeazienda);
        } else if(action.equals("generaCredenziali")) {
          String email = (request.getParameter("email"));
          model.generaCredenziali(email);
        } else if(action.equals("caricaUtentiDaFile")) {
          /*rimuovo e poi setto l'attributo accounts presente nella jsp*/
          request.removeAttribute("accounts");
          request.setAttribute("accounts", model.caricaUtentiDaFile());
        } else if(action.equals("caricaAccount")){
          String email = (request.getParameter("email"));
          String password = (request.getParameter("password"));  
          request.removeAttribute("account");
          request.setAttribute("account",  model.caricaAccount(email, password));
        }else if (action.equals("cercaAccountPerEmail")) {
          String email = (request.getParameter("email"));
          model.cercaAccountPerEmail(email);
        }
      }
    } catch(SQLException e) {
      e.printStackTrace();
    }
    
    /*manca il dispatcher per caricare le pagine jsp */
    
  }

  /**
   * Il metodo doPost permette di .
   * @param request richiesta
   * @param response risposta
   */
  
  protected void doPost(HttpServletRequest request, HttpServletResponse response) 
      throws ServletException, IOException {
    doGet(request, response);
  }

  
  public void registrazioneStudente(HttpServletRequest request) 
      throws SQLException {
    String email = (request.getParameter("email"));
    String password = (request.getParameter("password"));
    String matricola = (request.getParameter("matricola"));
    model.salvaAccountStudente(email, password, matricola);
  }
  
}
