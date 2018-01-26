package it.tirociniofacile.control;

import it.tirociniofacile.model.DocumentoModel;
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
 * Servlet implementation class GestioneInformazioniTirociniConclusi.
 */
@WebServlet("/GestioneInformazioniTirociniConclusi")
public class GestioneInformazioniTirociniConclusi extends HttpServlet {
  private static final long serialVersionUID = 1L;
  static DocumentoModel model;
  
  static {
    model = new DocumentoModel();
  }
  
  public GestioneInformazioniTirociniConclusi() {
        super();
  }

  /**.
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
  */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws ServletException, IOException {
  
    HttpSession session = request.getSession();
    String action = request.getParameter("action");
    try {
      if (action != null) {
        if (action.equals("visualizzaInformazioniPerAnnoAccademico")) {
          visualizzaInformazioniPerAnnoAccademico(request,response);
        } else if (action.equals("visualizzaInformazioniPerAzienda")) {
          visualizzaInformazioniPerAzienda(request,response);
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
   *  servlet visualizzaInformazioniPerAnnoAccademico.
   * @param request richiesta http
   * @param response risponse http 
   * @throws SQLException eccezzione sql
   * @throws ServletException eccezzioni servlet 
   * @throws IOException eccezzioni input output
   */
  public void visualizzaInformazioniPerAnnoAccademico(HttpServletRequest request, 
      HttpServletResponse response)
      throws SQLException, ServletException, IOException {
    String anno = request.getParameter("anno");
    request.removeAttribute("numeroQuestionari");
    request.setAttribute("numeroQuestionari", model.conteggioQuestionariApprovatiPerAnno(anno));
    
    RequestDispatcher rd = request.getRequestDispatcher("/visInfAnno.jsp");  
    rd.forward(request, response);
  }
  
  /**
   * Metodo visuallizza informazioni per azienda.
   * @param request richiesta http
   * @param response risposta servlet http
   * @throws SQLException eccezzione sql 
   * @throws ServletException eccazzione servlet
   * @throws IOException eccazzioni input output
   */
  public void visualizzaInformazioniPerAzienda(HttpServletRequest request,
      HttpServletResponse response) 
      throws SQLException, ServletException, IOException {
    String azienda = request.getParameter("azienda");
    request.removeAttribute("numeroQuestionari");
    request.setAttribute("numeroQuestionari",
        model.conteggioQuestionariApprovatiPerAzienda(azienda));
  
    System.out.println("QUIIIIIIIIIIII" + request.getAttribute("numeroQuestionari"));
    RequestDispatcher rd = request.getRequestDispatcher("/visInfAz.jsp");  
    rd.forward(request, response);
  }

}
