package it.tirociniofacile.control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GestioneTf.
 */
@WebServlet("/GestioneTf")
public class GestioneTf extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * costruttore.
   */
  public GestioneTf() {
    super();
  }

  /**
   * doGet.
   * 
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String action = request.getParameter("action");
    if (action != null) {
      if (action.equals("ricercaTuttePagine") || action.equals("ricercaPagina")
          || action.equals("visualizzaPagina") || action.equals("ceaPagina")) {
        RequestDispatcher rd = request.getRequestDispatcher("GestioneRicercaTirocinio");
        rd.forward(request, response);
      } else if (action.equals("visualizzaInformazioniPerAnnoAccademico") 
          || action.equals("visualizzaInformazioniPerAzienda")) {
        RequestDispatcher rd = request.getRequestDispatcher("GestioneInformazioniTirociniConclusi");
        rd.forward(request, response);
      } else if (action.equals("visualizzaDocumento") 
          || action.equals("scaricaDocumento") || action.equals("caricaDocumento") 
          || action.equals("convalidaDocumento") || action.equals("compilaConvenzioneAzienda")
          || action.equals("compilaQuestionario")) {
        RequestDispatcher rd = request.getRequestDispatcher("GestioneDocumento");
        rd.forward(request, response);
      }
    }
  }

  /**
   * doPost.
   * 
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String action = request.getParameter("action");
    if (action != null) {
      if (action.equals("log-out") || action.equals("registrazioneStudente")
          || action.equals("registrazioneAzienda") || action.equals("generaCredenziali")
          || action.equals("log-in") || action.equals("recuperaPassword")) {
        RequestDispatcher rd = request.getRequestDispatcher("GestioneUtente");
        rd.forward(request, response);
      }
    }
  }
}

