package it.tirociniofacile.control;

import it.tirociniofacile.model.DocumentoModel;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**.
 * Servlet implementation class GestioneDocumento
 */
@WebServlet("/GestioneDocumento")
public class GestioneDocumento extends HttpServlet {
  private static final long serialVersionUID = 1L;
  static DocumentoModel model;
  
  static {       
    model = new DocumentoModel();
  }
  
  /**
   * gestione documento costruttore vuoto.
   * @see HttpServlet#HttpServlet()
   */
  public GestioneDocumento() {
      super();
      // TODO Auto-generated constructor stub
  }

  /**.
  * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
  */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws ServletException, IOException {
    // TODO Auto-generated method stub
    response.getWriter().append("Served at: ").append(request.getContextPath());
    HttpSession session = request.getSession();
    String action = request.getParameter("action");
    try {
      if (action != null) { 
        
        
        if (action.equals("visualizzaDocumento")) {
          visualizzaDocumento(request);
        } else if (action.equals("scaricaDocumento")) {
          scaricaDocumento(request);
        } else if (action.equals("caricaDocumento")) {
          caricaDocumento(request);
        } else if (action.equals("convalidaDocumento")) {
          convalidaDocumento(request);
        } else if (action.equals("compilaConvenzioneAzienda")) {
          compilaConvenzioneAzienda(request);
        } else if (action.equals("compilaQuestionario")) { 
          compilaQuestionario(request);
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
    // TODO Auto-generated method stub
    doGet(request, response);
  }
  
  /**
   * Servlet visualizzaDocumento.
   * @param request richoesta 
   * @throws SQLException eccezzioni di sql
   */
  public void visualizzaDocumento(HttpServletRequest request) 
      throws SQLException {
    String id = (request.getParameter("id"));
    int id1 = Integer.parseInt(id);
    if (model.ricercaConvenzionePerId(id) != null) {
      request.removeAttribute("convenzione");
      request.setAttribute("convenzione", model.ricercaConvenzionePerId(id));
    } else {
      request.removeAttribute("questionario");
      request.setAttribute("questionario", model.ricercaQuestionarioPerId(id1));
    }
  }
  
  public void scaricaDocumento(HttpServletRequest request) throws SQLException {
  // da discuterne meglio

  }
  
  /**
   * servlet caricaDocumento.
   * @param request richiesta http
   * @throws SQLException eccezzioni sql
   */
  public void caricaDocumento(HttpServletRequest request) 
      throws SQLException {
    String pdf = (request.getParameter("pdf"));
    String id = (request.getParameter("id"));
    int id1 = Integer.parseInt(id);
    //TODO  model.salvaPdf(pdf, id1);
  }
  
  /**
   * servlet convalida documento.
   * @param request richiesta http
   * @throws SQLException eccazzioni sql
   */
  public void convalidaDocumento(HttpServletRequest request) 
      throws SQLException {
    String id = (request.getParameter("id"));
    String approvato =  (request.getParameter("approvato"));
      
    if (approvato.equals("false")) {
      model.cancellaDocumento(id);
    } else {
      model.approvaDocumento(id);
    }
  }  
  
  /**
   * servlet compilaConvenzioneAzienda.
   * @param request richiesta http
   * @throws SQLException eccezzioni sql
   */
  public void compilaConvenzioneAzienda(HttpServletRequest request) 
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
   *@param request richiesta http
   * @throws SQLException eccezzioni sql
   */
  public void compilaQuestionario(HttpServletRequest request) 
      throws SQLException {
    String informazioniSulTirocinio = (request.getParameter("informazioniSulTirocinio"));
    String commenti = (request.getParameter("commenti"));
    String suggerimenti = (request.getParameter("suggerimenti"));
    String annoAccademico = (request.getParameter("annoAccademico"));
    String giudizioEsperienza = (request.getParameter("giudizioEsperienza"));
    float giudizioEsperienzaD =   Float.parseFloat(giudizioEsperienza); 
    String giudizioAzienda =  (request.getParameter("giudizioAzienda"));
    float giudizioAziendaD =  Float.parseFloat(giudizioAzienda); 
    String giudizioUniversita = (request.getParameter("giudizioUniversita"));
    float giudizioUniversitaD = Float.parseFloat(giudizioUniversita); 
    String matricola = (request.getParameter("matricola"));
    model.salvaQuestionario(informazioniSulTirocinio, commenti, suggerimenti,
        annoAccademico, giudizioEsperienzaD, giudizioAziendaD, giudizioUniversitaD, matricola);
  }
  
  
  
}
  