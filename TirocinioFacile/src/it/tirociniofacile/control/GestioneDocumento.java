package it.tirociniofacile.control;

import it.tirociniofacile.bean.DocumentoConvenzioneBean;
import it.tirociniofacile.bean.DocumentoQuestionarioBean;
import it.tirociniofacile.bean.ProfiloAziendaBean;
import it.tirociniofacile.bean.ProfiloStudenteBean;
import it.tirociniofacile.bean.UtenteBean;
import it.tirociniofacile.model.DocumentoModel;

import java.io.File;
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
import javax.servlet.http.Part;

/**
 * . Servlet implementation class GestioneDocumento
 */
@WebServlet("/GestioneDocumento")
public class GestioneDocumento extends HttpServlet {
  private static final long serialVersionUID = 1L;
  static DocumentoModel model;

  int indice = 4;
  
  static {
    model = new DocumentoModel();
  }

  /**
   * gestione documento costruttore vuoto.
   * 
   * @see HttpServlet#HttpServlet()
   */
  public GestioneDocumento() {
    super();
  }
  
  public static final String SAVE_DIR = "pdf";

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // TODO Auto-generated method stub
    response.getWriter().append("Served at: ").append(request.getContextPath());
    HttpSession session = request.getSession();
    String action = request.getParameter("action");
    System.out.println("Gestione Documento Action: " + action);
    try {
      if (action != null) {
        if (action.equals("visualizzaDocumento")) {
          visualizzaDocumento(request, response);
        } else if (action.equals("scaricaDocumento")) {
          scaricaDocumento(request);
        } else if (action.equals("caricaDocumento")) {
          caricaDocumento(request, response);
        } else if (action.equals("convalidaDocumento")) {
          
          convalidaDocumento(request, response);
        } else if (action.equals("compilaConvenzioneAzienda")) {
          compilaConvenzioneAzienda(request, response);
        } else if (action.equals("compilaQuestionario")) {
          compilaQuestionario(request, response);
        } else if (action.equals("ricercaTuttiDocumentiConvenzioneAzienda")) {
          ricercaTuttiDocumentiConvenzioneAzienda(request, response);
        } else if (action.equals("ricercaTuttiDocumentiQuestionariAzienda")) {
          ricercaTuttiDocumentiQuestionariAzienda(request, response);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * .
   * 
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    doGet(request, response);
  }

  /**
   * Servlet visualizzaDocumento.
   * 
   * @param request
   *          richoesta
   * @throws SQLException
   *           eccezzioni di sql
   * @throws IOException  eccezzioni di input e output
   * @throws ServletException  servlet exception
   */
  public void visualizzaDocumento(HttpServletRequest request ,
      HttpServletResponse response) throws SQLException, ServletException, IOException {
    
    String partitaIva = (request.getParameter("partitaIva"));
    
    if (model.ricercaConvenzionePerPartitaIva(partitaIva) != null) {
      
      request.removeAttribute("convenzione");
      request.setAttribute("convenzione", model.ricercaConvenzionePerPartitaIva(partitaIva));
      RequestDispatcher rd = request.getRequestDispatcher("/visualizzaDocumento.jsp");  
      rd.forward(request, response);
      
    } else {
      
      String id = (request.getParameter("id"));
      int  id1 = Integer.parseInt(id);
      request.removeAttribute("questionario");
      request.setAttribute("questionario", model.ricercaQuestionarioPerId(id1));
      RequestDispatcher rd = request.getRequestDispatcher("/visualizzaDocumento.jsp");  
      rd.forward(request, response);
    }
    
  }

  public void scaricaDocumento(HttpServletRequest request) throws SQLException {
    // da discuterne meglio

  }

  /**
   * servlet caricaDocumento.
   * 
   * @param request
   *          richiesta http
   * @throws SQLException
   *           eccezzioni sql
   * @throws ServletException 
   * @throws IOException 
   */
  public void caricaDocumento(HttpServletRequest request, HttpServletResponse response) 
      throws SQLException, IOException, ServletException {
    //UtenteBean utente = (UtenteBean) request.getSession().getAttribute("account");
    //String email = utente.getEmail();
    
    Part pdf = request.getPart("pdf");
    
    String savePath = "C:/Users/PC1/git/progettoIStirociniofacile/"
        + "TirocinioFacile/WebContent"
        + "/" + SAVE_DIR;
    
    String fileName = extractFileName(pdf);
    pdf.write(savePath + File.separator + fileName);
    //prova
    model.salvaPdfConvenzione(fileName, "spinvector@info.com");
    
    
    
   /* if (utente instanceof ProfiloAziendaBean) {
      model.salvaPdfConvenzione(pdf,email);
    } else if (utente instanceof ProfiloStudenteBean) {
      model.salvaPdfQuestionario(pdf,email);
    }*/
  }
  
  private String extractFileName(Part part) {
    String contentDisp = part.getHeader("content-disposition");
    String[] items = contentDisp.split(";");
    for (String s : items) {
      if (s.trim().startsWith("filename")) {
        return s.substring(s.indexOf("=") + 2, s.length() - 1);
      }
    }
    return "";
  }

  /**
   * servlet convalida documento.
   * 
   * @param request
   *          richiesta http
   * @throws SQLException
   *           eccazzioni sql
   * @throws IOException  inout output eccezzioni
   * @throws ServletException  eccezzioni della servlet
   */
  public void convalidaDocumento(HttpServletRequest request ,
      HttpServletResponse response) throws SQLException, ServletException, IOException {
    String id = (request.getParameter("id"));
    String approvato = (request.getParameter("approvato"));


    if (approvato.equals("false")) {
      model.cancellaDocumento(id);
      RequestDispatcher rd = request.getRequestDispatcher("/approvaDocumento.jsp");  
      rd.forward(request, response);
    } else {
      model.approvaDocumento(id);
      RequestDispatcher rd = request.getRequestDispatcher("/approvaDocumento.jsp");  
      rd.forward(request, response);
    }
  }

  /**
   * servlet compilaConvenzioneAzienda.
   * 
   * @param request
   *          richiesta http
   * @throws SQLException
   *           eccezzioni sql
   */
  public void compilaConvenzioneAzienda(HttpServletRequest request ,
      HttpServletResponse response) throws SQLException {
    String piva = (request.getParameter("piva"));
    String nomeAzienda = (request.getParameter("nomeAzienda"));
    String sedeLegale = (request.getParameter("sedeLegale"));
    String citta = (request.getParameter("citta"));
    String rappLegale = (request.getParameter("rappLegale"));
    String luogoDiNascitaRappLegale = (request.getParameter("luogoDiNascitaRappLegale"));
    String dataDiNascitaRappLegale = (request.getParameter("dataDiNascitaRappLegale"));
    model.salvaConvenzione(piva, nomeAzienda, sedeLegale, citta, rappLegale,
        luogoDiNascitaRappLegale, dataDiNascitaRappLegale);
  }

  /**
   * servlet compila questionario.
   * 
   * @param request
   *          richiesta http
   * @throws SQLException
   *           eccezzioni sql
   */
  public void compilaQuestionario(HttpServletRequest request , HttpServletResponse response) {

    String commenti = (request.getParameter("commenti"));
    String suggerimenti = (request.getParameter("suggerimenti"));

    int giudizioEsperienza = 0;
    int giudizioAzienda = 0;
    int giudizioUniversita = 0;
    for (int i = 0; i < 5; i++) {
      String val = request.getParameter("a" + (i + 1));
      giudizioEsperienza += (Integer.parseInt(val));
    }
    for (int i = 0; i < 5; i++) {
      String val = request.getParameter("b" + (i + 1));
      giudizioAzienda += (Integer.parseInt(val));
    }
    for (int i = 0; i < 6; i++) {
      String val = request.getParameter("c" + (i + 1));
      giudizioUniversita += (Integer.parseInt(val));
    }
    giudizioEsperienza = giudizioEsperienza / 5;
    giudizioAzienda = giudizioAzienda / 5;
    giudizioUniversita = giudizioUniversita / 6;
    System.out.println("giudizioEsperienza: " + giudizioEsperienza);
    System.out.println("giudizioAzienda: " + giudizioAzienda);
    System.out.println("giudizioUniversita: " + giudizioUniversita);

    /*
     * String informazioniSulTirocinio = (request.getParameter("informazioniSulTirocinio"));
     * 
     * String annoAccademico = (request.getParameter("annoAccademico")); String giudizioEsperienza =
     * (request.getParameter("giudizioEsperienza")); float giudizioEsperienzaD =
     * Float.parseFloat(giudizioEsperienza); String giudizioAzienda =
     * (request.getParameter("giudizioAzienda")); float giudizioAziendaD =
     * Float.parseFloat(giudizioAzienda); String giudizioUniversita =
     * (request.getParameter("giudizioUniversita")); float giudizioUniversitaD =
     * Float.parseFloat(giudizioUniversita); String matricola = (request.getParameter("matricola"));
     * 
     * try { // model.salvaQuestionario(informazioniSulTirocinio, commenti, suggerimenti, //
     * annoAccademico, giudizioEsperienzaD, giudizioAziendaD, giudizioUniversitaD, matricola); }
     * catch (SQLException e) { e.printStackTrace(); }
     */
  }
  
  /**
   * Ricerca tutti i DocumentiConvenzioneAzienda.
   * @param request
   * @param response
   * @throws SQLException
   * @throws ServletException
   * @throws IOException
   */
  public void ricercaTuttiDocumentiConvenzioneAzienda(HttpServletRequest request ,
      HttpServletResponse response)  throws SQLException, ServletException, IOException {
    ArrayList<DocumentoConvenzioneBean> listaDocumentiConvenzione =
        model.getTuttiDocumentiConvenzioneAzienda();
    
    request.removeAttribute("listaDocumentiConvenzione");
    request.removeAttribute("listaDocumentiQuestionari");
    request.setAttribute("listaDocumentiConvenzione", listaDocumentiConvenzione);
    
    if (request.getParameter("indice") != null) {
      this.indice = Integer.parseInt(request.getParameter("indice")); 
    }
    request.setAttribute("indice", indice); 
    
    RequestDispatcher rd = request.getRequestDispatcher("/approvaDocumento.jsp");  
    rd.forward(request, response);
   
  }
  
  /**
   * 
   * @param request
   * @param response
   * @throws SQLException
   * @throws ServletException
   * @throws IOException
   */
   public void ricercaTuttiDocumentiQuestionariAzienda(HttpServletRequest request, 
      HttpServletResponse response) throws SQLException , ServletException, IOException  {
    ArrayList<DocumentoQuestionarioBean> listaDocumentiQuestionari =
         model.getTuttiDocumentiQuestionari();
    
    request.removeAttribute("listaDocumentiConvenzione");
    request.removeAttribute("listaDocumentiQuestionari");
    request.setAttribute("listaDocumentiQuestionari", listaDocumentiQuestionari);
     
    if (request.getParameter("indice") != null) {
      this.indice = Integer.parseInt(request.getParameter("indice")); 
    }
    request.setAttribute("indice", indice); 
     
    RequestDispatcher rd = request.getRequestDispatcher("/approvaDocumento.jsp");  
    rd.forward(request, response);
    
  }

}
