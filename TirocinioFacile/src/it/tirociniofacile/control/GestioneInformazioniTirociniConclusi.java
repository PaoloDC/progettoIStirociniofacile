package it.tirociniofacile.control;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.tirociniofacile.model.DocumentoModel;

/**
 * Servlet implementation class GestioneInformazioniTirociniConclusi.
 */
@WebServlet("/GestioneInformazioniTirociniConclusi")
public class GestioneInformazioniTirociniConclusi extends HttpServlet {
  private static final long serialVersionUID = 1L;
  static DocumentoModel model;
  static {
    model=new DocumentoModel();
  }
  /**.
  * @see HttpServlet#HttpServlet()
  */
  public GestioneInformazioniTirociniConclusi() {
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
      if(action!= null ) {
        if(action.equals("visualizzaInformazioniPerAnnoAccademico")) {
          visualizzaInformazioniPerAnnoAccademico(request);
        }else if (action.equals("visualizzaInformazioniPerAzienda")) {
          visualizzaInformazioniPerAzienda(request);
        }
      }
    }catch(SQLException e) {
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
  public void visualizzaInformazioniPerAnnoAccademico(HttpServletRequest request)
      throws SQLException {
  String anno = request.getParameter("anno");
  request.removeAttribute("numeroQuestionari");
  request.setAttribute("numeroQuestionari",model.conteggioQuestionariApprovatiPerAnno(anno) );
}
  public void visualizzaInformazioniPerAzienda(HttpServletRequest request) 
      throws SQLException {
  String azienda = request.getParameter("azienda");
  request.removeAttribute("numeroQuestionari");
  request.setAttribute("numeroQuestionari",
      model.conteggioQuestionariApprovatiPerAzienda(azienda));
}

}
