package it.tirociniofacile.control;

import it.tirociniofacile.bean.PaginaAziendaBean;
import it.tirociniofacile.model.PaginaAziendaModel;
import it.tirociniofacile.model.UtenteModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
  
  static {
    model = new PaginaAziendaModel();
  }
  
  /**
  * Costruttore vuoto. 
  */
  public GestioneRicercaTirocinio() {
    
  }

  /**.
  * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
  */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws ServletException, IOException {
    response.getWriter().append("Served at: ").append(request.getContextPath());
    
    HttpSession session = request.getSession();
    String action = request.getParameter("action");
    
    try {
      model.prova();
    } catch (SQLException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    } 
    
    try {
      if (action != null) {
        if (action.equals("ricercaTuttePagine")) {
          ricercaTuttePagine(request);
        } else if (action.equals("ricercaPagina")) {
          ricercaPagina(request);
        } else if (action.equals("visualizzaPagina")) {
          visualizzaPagina(request);
        } else if (action.equals("ceaPagina")) {
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
   */
  public void ricercaTuttePagine(HttpServletRequest request) 
      throws SQLException {
    ArrayList<PaginaAziendaBean> pabList = model.ricerca();
    
    request.removeAttribute("listaAziende");
    request.setAttribute("listaAziende", pabList);
    
    System.out.println("Ecco" + pabList.get(0));
  }
  
  /**
   * Servlet che ricerca le pagine per una categoria e una chiave.
   * @param request la richiesta http
   * @throws SQLException eccezione lanciato dal metodo del model
   */
  public void ricercaPagina(HttpServletRequest request) 
      throws SQLException {
    String categoria = request.getParameter("categoria");
    String chiave = request.getParameter("chiave");
    
    ArrayList<PaginaAziendaBean> pabList = model.ricerca(categoria,chiave);
    request.removeAttribute("listaAziende");
    request.setAttribute("listaAziende", pabList);
  }
  
  /**
   * Servlet che visualizza una singola pagina.
   * @param request la richiesta http
   * @throws SQLException eccezione lanciato dal metodo del model
   */
  public void visualizzaPagina(HttpServletRequest request) 
      throws SQLException {
    String id = request.getParameter("id");
    
    PaginaAziendaBean pab = model.ricerca(id);
    
    request.removeAttribute("pagina");
    request.setAttribute("pagina", pab);
  }
  
  /**
   * Servlet che crea una pagina.
   * @param request la richiesta http
   * @throws SQLException eccezione lanciato dal metodo del model
   */
  public void creaPagina(HttpServletRequest request) 
      throws SQLException {
    String localita = request.getParameter("localita");
    String descrizione = request.getParameter("descrizione");
    String email = request.getParameter("email");
    
    String[] ambitoArray = request.getParameterValues("ambito");
    String[] skillArray = request.getParameterValues("skill");
    
    List<String> ambitoList = Arrays.asList(ambitoArray);
    List<String> skillList = Arrays.asList(skillArray);
    
    ArrayList<String> ambito = (ArrayList) ambitoList;
    ArrayList<String> skill = (ArrayList) skillList;
    
    //sequenza di operazioni per fare cast da String[] ad ArrayList
    
    model.aggiungiPagina(localita, descrizione, email, ambito, skill);
  }
}
