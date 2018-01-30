package it.tirociniofacile.control;

import it.tirociniofacile.bean.DocumentoConvenzioneBean;
import it.tirociniofacile.bean.UtenteBean;
import it.tirociniofacile.model.DocumentoModel;
import it.tirociniofacile.model.UtenteModel;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *  Servlet implementation class GestioneUtente.
 */
@WebServlet("/GestioneUtente")
public class GestioneUtente extends HttpServlet {
  private static final long serialVersionUID = 1L;
  static UtenteModel model;
  static DocumentoModel docModel;

  static {
    model = new UtenteModel();
    docModel = new DocumentoModel();
  }

  public GestioneUtente() {
    super();
  }


  /**
   * doGet.
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    doPost(request, response);
  }

  /**
   * doPost.
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    HttpSession session = request.getSession();
    String action = request.getParameter("action");

    // Gestione utente
    System.out.println("GestioneUtente action: " + action);
    try {
      if (action != null) {
        if (action.equals("log-out")) {
          synchronized (session) {
            session.invalidate();
            RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
            rd.forward(request, response);
          }
        } else if (action.equals("registrazioneStudente")) {
          registrazioneStudente(request, response);

        } else if (action.equals("registrazioneAzienda")) {
          registrazioneAzienda(request, response);

        } else if (action.equals("log-in")) {
          logIn(request, response);

        } else if (action.equals("recuperaPassword")) {
          recuperaPassword(request, response);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    /* manca il dispatcher per caricare le pagine jsp */

  }

  /**
   * RegistrazioneStudente effettua la registrazione di un account studente.
   * 
   * @param request
   *          richiesta http
   * @throws SQLException
   *           eccezzione sql
   * @throws IOException eccezioni di I/O
   * @throws ServletException eccezioni della servlet
   */
  public void registrazioneStudente(HttpServletRequest request, HttpServletResponse response)
      throws SQLException, ServletException, IOException {

    String email = (request.getParameter("email"));
    String password = (request.getParameter("password"));
    String matricola = (request.getParameter("matricola"));

    String emailIntera = email + "@studenti.unisa.it";
    String matricolaIntera = "05121" + matricola;

    boolean errore = model.salvaAccountStudente(emailIntera, password, matricolaIntera);

    if (!errore) {
      RequestDispatcher rd = request.getRequestDispatcher("/registraProfiloStudente.jsp");
      request.removeAttribute("noRegistrazione");
      request.setAttribute("noRegistrazione", "Email o Matricola Gia' Esistenti");
      rd.forward(request, response);
    }

    RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
    rd.forward(request, response);
  }

  /**
   * RegistrazioneAzienda effettua la registrazione di un account azienda.
   * 
   * @param request
   *          richiesta http
   * @throws IOException eccezioni di I/O
   * @throws ServletException eccezioni della servlet
   * @throws IOException eccezione di io
   */
  public void registrazioneAzienda(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String email = (request.getParameter("email"));
    String password = (request.getParameter("password"));
    String nomeazienda = (request.getParameter("nomeazienda"));

    boolean errore = model.salvaAccountAzienda(email, password, nomeazienda);

    if (!errore) {
      RequestDispatcher rd = request.getRequestDispatcher("/registraProfiloAzienda.jsp");
      request.removeAttribute("noRegistrazione");
      request.setAttribute("noRegistrazione", "Email o Nome Azienda Gia' Esistenti");
      rd.forward(request, response);
    } else {
      String piva = request.getParameter("piva");
      String sedeLegale = request.getParameter("sedeLegale");
      String citta = request.getParameter("citta");
      String rappLegale = request.getParameter("rappLegale");
      String luogoDiNascitaRappLegale = request.getParameter("luogoDiNascitaRappLegale");
      String dataDiNascitaRappLegale = request.getParameter("dataDiNascitaRappLegale");

      String megaTesto = "L'Azienda/Ente " + nomeazienda + " con sede legale in " + sedeLegale
          + ", città " + citta + ", Partita IVA " + piva
          + ",<br> d'ora in poi denominato 'Soggetto Ospitante', rappresentato da " + rappLegale
          + ", nato a " + luogoDiNascitaRappLegale + " il " + dataDiNascitaRappLegale
          + ",<br> in qualità legale rappresentante.";

      errore = docModel.salvaConvenzione(piva, nomeazienda, sedeLegale, citta, rappLegale,
          luogoDiNascitaRappLegale, dataDiNascitaRappLegale, megaTesto);

      if (!errore) {
        model.cancellaAccountAzienda(email);
        RequestDispatcher rd = request.getRequestDispatcher("/registraProfiloAzienda.jsp");
        request.removeAttribute("noPartitaIva");
        request.setAttribute("noPartitaIva", "Partita Iva Gia' Esistente");
        rd.forward(request, response);
      }

      RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
      rd.forward(request, response);

    }
  }

  /**
   * Effettua la log in.
   * 
   * @param request
   *          richiesta http
   * @throws SQLException
   *           eccezione sql
   * @throws IOException
   *           input output eccezioni
   * @throws ServletException
   *           servlet eccezioni
   */
  public void logIn(HttpServletRequest request, HttpServletResponse response)
      throws SQLException, ServletException, IOException {
    String email = (request.getParameter("email"));
    String password = (request.getParameter("password"));
    request.removeAttribute("account");

    UtenteBean utente = model.caricaAccount(email, password);
    if (utente != null) {
      request.getSession().setAttribute("account", utente);
      if (utente.getEmail().equals("fferrucci@unisa.it")) {
        request.getSession().setAttribute("tipologiaAccount", "presidente");

        RequestDispatcher rd = request.getRequestDispatcher("/visualizzaInformazioni.jsp");
        rd.forward(request, response);
      } else if (utente.getEmail().contains("@studenti.unisa.it")) {
        request.getSession().setAttribute("tipologiaAccount", "studente");

        RequestDispatcher rd = request.getRequestDispatcher("/homeStudente.jsp");
        rd.forward(request, response);
      } else if (utente.getEmail().contains("@unisa.it")) {
        request.getSession().setAttribute("tipologiaAccount", "impiegato");

        RequestDispatcher rd = request.getRequestDispatcher("/approvaDocumento.jsp");
        rd.forward(request, response);
      } else {
        request.getSession().setAttribute("tipologiaAccount", "azienda");

        // domanda convenzione
        DocumentoModel docModel = new DocumentoModel();
        DocumentoConvenzioneBean conv = docModel.ricercaConvenzionePerEmail(utente.getEmail());

        request.getSession().setAttribute("convenzioneAzienda", conv);

        RequestDispatcher rd = request.getRequestDispatcher("/homeAzienda.jsp");
        rd.forward(request, response);
      }
    } else {
      RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
      request.setAttribute("noUtente", "nessun utente con queste credenziali");
      rd.forward(request, response);
    }
  }

  /**
   * Recupera password invia una mail alla mail inviata contenente una nuova password.
   * @param request  richiesta http
   * @param response risposta http
   * @throws ServletException eccezione della servlet
   * @throws IOException errore di io
   */
  public void recuperaPassword(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String email = request.getParameter("email");
    boolean trovato = model.cercaAccountPerEmail(email);

    String msg = "Corrispondenza non trovata!";
    if (trovato) {
      msg = "Corrispondenza trovata, mail inviata!";
    }
    request.setAttribute("trovato", msg);
    RequestDispatcher rd = request.getRequestDispatcher("/recuperaPassword.jsp");
    rd.forward(request, response);

  }
}
