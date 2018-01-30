package it.tirociniofacile.control;

import it.tirociniofacile.bean.DocumentoQuestionarioBean;
import it.tirociniofacile.bean.PaginaAziendaBean;
import it.tirociniofacile.model.DocumentoModel;
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
  static DocumentoModel modelDoc;
  int indice = 4;

  static {
    model = new PaginaAziendaModel();
  }
  
  static {
    modelDoc = new DocumentoModel();
  }

  /**
   * Costruttore vuoto.
   */
  public GestioneRicercaTirocinio() {
    super();
  }


  /**
   * doGet.
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    HttpSession session = request.getSession();
    String action = request.getParameter("action");

    System.out.println("GestioneRicercaTirocinio action: " + action);
    try {
      if (action != null) {
        if (action.equals("ricercaTuttePagine")) {
          ricercaTuttePagine(request, response);
        } else if (action.equals("ricercaPagina")) {
          ricercaPagina(request, response);
        } else if (action.equals("visualizzaPagina")) {
          visualizzaPagina(request, response);
        } else if (action.equals("creaPagina")) {
          creaPagina(request,response);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }


  /**
   * doPost.
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    doGet(request, response);
  }

  /**
   * Servlet che ricerca tutte le pagine.
   * 
   * @param request
   *          la richiesta http
   * @throws SQLException
   *           eccezione lanciato dal metodo del model
   * @throws IOException
   *           eccezioni input output
   * @throws ServletException
   *           eccezioni servlet
   */
  public void ricercaTuttePagine(HttpServletRequest request, HttpServletResponse response)
      throws SQLException, ServletException, IOException {
    
    this.indice = 4;
    
    ArrayList<PaginaAziendaBean> pabList = model.ricerca();
    request.removeAttribute("listaAziende");
    request.setAttribute("listaAziende", pabList);
    request.setAttribute("action", "ricercaTuttePagine");

    if (request.getParameter("indice") != null) {
      this.indice = Integer.parseInt(request.getParameter("indice"));
    }
    request.removeAttribute("indice");
    request.setAttribute("indice", this.indice);

    String tirocini = request.getParameter("tirocini");
    String compila = request.getParameter("compila");

    //System.out.println(this.getServletName() + " tirocini: " + tirocini + ", compila: " + compila);
    
    if (tirocini != null) {
      if (tirocini.equals("true")) {
        request.getSession().setAttribute("listaAziende", pabList);
        RequestDispatcher rd = request.getRequestDispatcher("/visInfAz.jsp");
        rd.forward(request, response);
      }
    } else if (compila != null) {
      if (compila.equals("true")) {
        request.getSession().setAttribute("listaAziende", pabList);
        RequestDispatcher rd = request.getRequestDispatcher("/compilaQuestionario.jsp");
        rd.forward(request, response);
      }
    } else {
      RequestDispatcher rd = request.getRequestDispatcher("/ricercaAzienda.jsp");
      rd.forward(request, response);
    }
  }

  /**
   * Servlet che ricerca le pagine per una categoria e una chiave.
   * 
   * @param request
   *          la richiesta http
   * @throws SQLException
   *           eccezione lanciato dal metodo del model
   * @throws IOException
   *           eccezioni input output
   * @throws ServletException
   *           eccezioni servlet
   */
  public void ricercaPagina(HttpServletRequest request, HttpServletResponse response)
      throws SQLException, ServletException, IOException {
    
    this.indice = 4;
    String categoria = request.getParameter("categoria");
    String chiave = request.getParameter("chiave");
    
    if (categoria != null) {
      if (categoria.equals("nome")) {
        categoria = "nomeaziendaRappresentata";
      }
    } 
    if (chiave != null) {
      if (chiave.equals("")) {
        ricercaTuttePagine(request,response);
      }
      if (!chiave.equals("")) {
        if (request.getParameter("indice") != null) {
          this.indice = Integer.parseInt(request.getParameter("indice"));
        } 
        request.removeAttribute("indice");
        request.setAttribute("indice", this.indice);
        
        ArrayList<PaginaAziendaBean> pabList = model.ricerca(categoria,chiave);
        request.removeAttribute("listaAziende");
        request.setAttribute("listaAziende", pabList);  
        
        request.removeAttribute("action");
        request.setAttribute("action", "ricercaPagina" + "&categoria=" 
            + categoria + "&chiave=" + chiave);
        
        RequestDispatcher rd = request.getRequestDispatcher("/ricercaAzienda.jsp");
        rd.forward(request, response);
      }
   

      
    }
  }

  /**
   * visualizza pagina per id e partita iva.
   * 
   * @param request
   *          la richiesta http
   * @throws SQLException
   *           eccezione lanciato dal metodo del model
   * @throws IOException
   *           eccezioni input output
   * @throws ServletException
   *           eccezioni servlet
   */
  public void visualizzaPagina(HttpServletRequest request, HttpServletResponse response)
      throws SQLException, ServletException, IOException {
    String id = request.getParameter("id");

    PaginaAziendaBean pab = model.ricerca(Integer.parseInt(id));
    ArrayList<DocumentoQuestionarioBean> listaDoc = 
        modelDoc.getTuttiDocumentiQuestionariPerPagina(pab.getId());
    
    request.removeAttribute("pagina");
    request.setAttribute("pagina", pab);
    request.setAttribute("commSugg", listaDoc);

    RequestDispatcher rd = request.getRequestDispatcher("/visualizzaPagina.jsp");
    rd.forward(request, response);

  }

  /**
   * Servlet che crea una pagina.
   * 
   * @param request
   *          la richiesta http
   * @throws IOException  eccezioni di I/O
   * @throws ServletException eccezioni della servlet
   */
  public void creaPagina(HttpServletRequest request, HttpServletResponse response) 
      throws ServletException, IOException {
    String localita = request.getParameter("localita");
    String descrizione = request.getParameter("descrizione");
    String mailAzienda = request.getParameter("mailAzienda");

    String allambito = request.getParameter("ambito");
    String allskill = request.getParameter("skill");

    ArrayList<String> ambiti = this.separaValoriStringa(allambito);
    ArrayList<String> skill = this.separaValoriStringa(allskill);

    int id = model.aggiungiPagina(localita, descrizione, mailAzienda, ambiti, skill);
    
    RequestDispatcher rd = 
        request.getRequestDispatcher("/GestioneTf?action=visualizzaPagina&id=" + id);
    rd.forward(request, response);
  }
  /**
 * separa i valori delle stringhe seguite dal carattere ",".
 * @param stringa stringa di caratteri
 * @return
 */
  
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
