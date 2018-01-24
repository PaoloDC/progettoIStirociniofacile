package it.tirociniofacile.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
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

import it.tirociniofacile.bean.DocumentoConvenzioneBean;
import it.tirociniofacile.bean.DocumentoQuestionarioBean;
import it.tirociniofacile.bean.PaginaAziendaBean;
import it.tirociniofacile.bean.UtenteBean;
import it.tirociniofacile.model.DocumentoModel;
import it.tirociniofacile.model.PaginaAziendaModel;

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
        } else if (action.equals("ricercaQuestionariNonApprovatiPerStudente")) {
          ricercaQuestionariNonApprovatiPerStudente(request,response);
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
   *          richiesta
   * @throws SQLException
   *           eccezzioni di sql
   * @throws IOException
   *           eccezzioni di input e output
   * @throws ServletException
   *           servlet exception
   */
  public void visualizzaDocumento(HttpServletRequest request, HttpServletResponse response)
      throws SQLException, ServletException, IOException {

    String partitaIva = (request.getParameter("partitaIva"));

    if (model.ricercaConvenzionePerPartitaIva(partitaIva) != null) {

      request.removeAttribute("convenzione");
      request.setAttribute("convenzione", model.ricercaConvenzionePerPartitaIva(partitaIva));
      RequestDispatcher rd = request.getRequestDispatcher("/visualizzaDocumento.jsp");
      rd.forward(request, response);

    } else {

      String id = (request.getParameter("id"));
      int id1 = Integer.parseInt(id);
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
   *           eccezioni sql
   * @throws ServletException
   * @throws IOException
   */
  public void caricaDocumento(HttpServletRequest request, HttpServletResponse response)
      throws SQLException, IOException, ServletException {

    String tipologiaAccount = (String) request.getSession().getAttribute("tipologiaAccount");

    String email = request.getParameter("email");
    
    Part pdf = request.getPart("file");

    String fileName = extractFileName(pdf);

    pdf.write(DocumentoModel.SAVE_PATH + fileName);

    String idDocumento = request.getParameter("idDocumento");
    
    if (tipologiaAccount.equals("studente")) {
      model.salvaPdfQuestionario(fileName, email, idDocumento);
      
      RequestDispatcher rd = request.getRequestDispatcher("/homeStudente.jsp");
      rd.forward(request, response);
      
    } else if (tipologiaAccount.equals("azienda")) {
      model.salvaPdfConvenzione(fileName, email);
      
      RequestDispatcher rd = request.getRequestDispatcher("/creaPagina.jsp");
      rd.forward(request, response);
      
    }

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
   * @throws IOException
   *           inout output eccezzioni
   * @throws ServletException
   *           eccezzioni della servlet
   */
  public void convalidaDocumento(HttpServletRequest request, HttpServletResponse response)
      throws SQLException, ServletException, IOException {
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
  public void compilaConvenzioneAzienda(HttpServletRequest request, HttpServletResponse response)
      throws SQLException {
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
  public void compilaQuestionario(HttpServletRequest request, HttpServletResponse response) {

    /*
     * //PARTE 1 OK String cognome = request.getParameter("cognome"); String nome =
     * request.getParameter("nome"); String telefono1 = request.getParameter("telefono1"); String
     * telefono2 = request.getParameter("telefono2"); String email = request.getParameter("email");
     * String comune = request.getParameter("comune"); String provincia =
     * request.getParameter("provincia"); String annoimm = request.getParameter("annoimm"); String
     * cdlimm = request.getParameter("cdlimm"); String provazienda =
     * request.getParameter("provazienda"); String sesso = request.getParameter("sesso"); String
     * datanascita = request.getParameter("datanascita");
     */

    // String nomeAzienda = a[1];

    // PARTE 2
    /*
     * String parte2dom1 = request.getParameter("parte2dom1"); String parte2dom1altro =
     * request.getParameter("parte2dom1altro"); String parte2dom3 =
     * request.getParameter("parte2dom3"); String parte2dom3altro =
     * request.getParameter("parte2dom3altro"); String parte2dom4 =
     * request.getParameter("parte2dom4"); String parte2dom4altro =
     * request.getParameter("parte2dom4altro");
     * 
     * String[] cinque = request.getParameterValues("cinque"); String[] sei =
     * request.getParameterValues("sei");
     */

    // PARTE 3 OK
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

    String[] data = request.getParameterValues("data");
    String annoAccademico = data[2];
    String mailStudente = request.getParameter("mailStudente");
    String valAzienda = request.getParameter("azienda");
    String[] a = valAzienda.split(",");
    String id = a[0];
    String commenti = request.getParameter("commenti");
    String suggerimenti = request.getParameter("suggerimenti");
    String matricola = request.getParameter("matricola");

    model.salvaQuestionario(commenti, suggerimenti, annoAccademico, mailStudente,
        Integer.parseInt(id), matricola, giudizioEsperienza, giudizioAzienda, giudizioUniversita);

    try {
      response.setContentType("text/html;charset=UTF-8");
      PrintWriter out = response.getWriter();
      out.println("<script type=\"text/javascript\">");
      out.println("alert('Questionario inviato correttamente');");
      out.println("location='homeStudente.jsp';");
      out.println("</script>");

    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  /**
   * Ricerca tutti i DocumentiConvenzioneAzienda.
   * 
   * @param request
   * @param response
   * @throws SQLException
   * @throws ServletException
   * @throws IOException
   */
  public void ricercaTuttiDocumentiConvenzioneAzienda(HttpServletRequest request,
      HttpServletResponse response) throws SQLException, ServletException, IOException {
    ArrayList<DocumentoConvenzioneBean> listaDocumentiConvenzione = model
        .getTuttiDocumentiConvenzioneAzienda();

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
      HttpServletResponse response) throws SQLException, ServletException, IOException {

    ArrayList<DocumentoQuestionarioBean> listaDocumentiQuestionari = model
        .getTuttiDocumentiQuestionari();

    request.removeAttribute("listaDocumentiConvenzione");
    request.setAttribute("listaDocumentiQuestionari", listaDocumentiQuestionari);

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
  public void ricercaQuestionariNonApprovatiPerStudente(HttpServletRequest request,
      HttpServletResponse response) throws SQLException, ServletException, IOException {

    String mailStudente = request.getParameter("mailStudente");
    System.out.println("mailStudente: " + mailStudente);

    if (mailStudente != null) {
      ArrayList<String> questionariStudente = 
          model.ricercaQuestionariNonApprovatiPerStudente(mailStudente);
      
      PaginaAziendaModel pam = new PaginaAziendaModel();
      ArrayList<PaginaAziendaBean> listaPagine = pam.ricerca();
      
      request.getSession().setAttribute("questionariStudente", questionariStudente);
      request.getSession().setAttribute("listaPagine", listaPagine);
      
      RequestDispatcher rd = request.getRequestDispatcher("/caricaDocumento.jsp");
      rd.forward(request, response);
    }
    
  }

}
