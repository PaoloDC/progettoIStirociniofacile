package it.tirociniofacile.control;

import it.tirociniofacile.bean.PaginaAziendaBean;
import it.tirociniofacile.model.PaginaAziendaModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class GestioneRicercaTirocinio.
 */
@WebServlet("/GestioneRicercaTirocinio")
public class GestioneRicercaTirocinio extends HttpServlet {
  private static final long serialVersionUID = 1L;
  static PaginaAziendaModel model;
  int indice = 4;
  
  static {
    model = new PaginaAziendaModel();
  }

  /**
   * Costruttore vuoto. 
   */
  public GestioneRicercaTirocinio() {
    super();
  }

  /**.
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws ServletException, IOException {

    HttpSession session = request.getSession();
    String action = request.getParameter("action");

    System.out.println("AZIONE: " + action);
    try {
      if (action != null) {
        if (action.equals("ricercaTuttePagine")) {
          ricercaTuttePagine(request,response);
        } else if (action.equals("ricercaPagina")) {
          ricercaPagina(request,response);
        } else if (action.equals("visualizzaPagina")) {
          visualizzaPagina(request,response);
        } else if (action.equals("creaPagina")) {
          creaPagina(request);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**.
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response) 
      throws ServletException, IOException {
    doGet(request, response);
  }

  /**
   * Servlet che ricerca tutte le pagine.
   * @param request la richiesta http
   * @throws SQLException eccezione lanciato dal metodo del model
   * @throws IOException  eccezzioni input output
   * @throws ServletException  eccezzioni servlet
   */
  public void ricercaTuttePagine(HttpServletRequest request, HttpServletResponse response) 
      throws SQLException, ServletException, IOException {
    ArrayList<PaginaAziendaBean> pabList = model.ricerca();
    request.removeAttribute("listaAziende");
    request.setAttribute("listaAziende", pabList);
    
    if (request.getParameter("indice") != null) {
      this.indice = Integer.parseInt(request.getParameter("indice")); 
    }
    request.setAttribute("indice", indice); 

    String tirocini = request.getParameter("tirocini");

    if (tirocini != null) {
      if (tirocini.equals("true")) {
        request.getSession().setAttribute("listaAziende", pabList);
        RequestDispatcher rd = request.getRequestDispatcher("/visInfAz.jsp");  
        rd.forward(request, response);
      }
    }

    RequestDispatcher rd = request.getRequestDispatcher("/ricercaAzienda.jsp");  
    rd.forward(request, response);
  }

  /**
   * Servlet che ricerca le pagine per una categoria e una chiave.
    * @param request la richiesta http
   * @throws SQLException eccezione lanciato dal metodo del model
   * @throws IOException  eccezzioni input output
   * @throws ServletException  eccezzioni servlet
   */
  public void ricercaPagina(HttpServletRequest request, HttpServletResponse response) 
      throws SQLException, ServletException, IOException {
    String categoria = request.getParameter("categoria");
    String chiave = request.getParameter("chiave");
    
    if (categoria.equals("nome")) {
      categoria = "nomeaziendaRappresentata";
    }

    ArrayList<PaginaAziendaBean> pabList = model.ricerca(categoria,chiave);
    request.removeAttribute("listaAziende");
    request.setAttribute("listaAziende", pabList);

    RequestDispatcher rd = request.getRequestDispatcher("/ricercaAzienda.jsp");  
    rd.forward(request, response);
  }

  /**
   * visualizza pagina  per id e partita iva.
   * @param request la richiesta http
   * @throws SQLException eccezione lanciato dal metodo del model
   * @throws IOException  eccezzioni input output
   * @throws ServletException  eccezzioni servlet
   */
  public void visualizzaPagina(HttpServletRequest request, HttpServletResponse response) 
      throws SQLException, ServletException, IOException {
    String id = request.getParameter("id");

    PaginaAziendaBean pab = model.ricerca(Integer.parseInt(id));

    request.removeAttribute("pagina");
    request.setAttribute("pagina", pab);

    RequestDispatcher rd = request.getRequestDispatcher("/visualizzaPagina.jsp");  
    rd.forward(request, response);

  }

  /**
   * Servlet che crea una pagina.
   * @param request la richiesta http
   * @throws SQLException eccezione lanciato dal metodo del model
   */
  public void creaPagina(HttpServletRequest request) {
    String localita = request.getParameter("localita");
    String descrizione = request.getParameter("descrizione");
    String mailAzienda = request.getParameter("mailAzienda");

    String allambito = request.getParameter("ambito");
    String allskill = request.getParameter("skill");

    ArrayList<String> ambiti = this.separaValoriStringa(allambito);
    ArrayList<String> skill = this.separaValoriStringa(allskill);

    model.aggiungiPagina(localita, descrizione, mailAzienda, ambiti, skill);
  }

  private ArrayList<String> separaValoriStringa(String stringa) {
    final String separatore = ",";
    ArrayList<String> lista = new ArrayList<>();
    
    String[] divise = stringa.split(separatore);
    for (int i = 0; i < divise.length; i++) {
      lista.add(divise[i]);
    }
    
    return lista;
  }
}
