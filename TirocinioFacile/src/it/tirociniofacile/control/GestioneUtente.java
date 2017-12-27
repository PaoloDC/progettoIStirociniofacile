package it.tirociniofacile.control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** .
 * Servlet implementation class GestioneUtente
 */
@WebServlet("/GestioneUtente")
public class GestioneUtente extends HttpServlet {
  private static final long serialVersionUID = 1L;
       
  /** .
     * @see HttpServlet#HttpServlet()
     */
  public GestioneUtente() {
        super();
  }
  
  /**
   * Esempio di commento, ci vuole il punto finale.
   * ogni riga massimo 100 caratteri
   * spazio sopra e sotto
   * @param request richiesta
   * @param response risposta
   */
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws ServletException, IOException {
    response.getWriter().append("Served at: ").append(request.getContextPath());
  }

  /**
   * Il metodo doPost permette di .
   * @param request richiesta
   * @param response risposta
   */
  
  protected void doPost(HttpServletRequest request, HttpServletResponse response) 
      throws ServletException, IOException {
    doGet(request, response);
  }

}
