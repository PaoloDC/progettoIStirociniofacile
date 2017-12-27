package it.tirociniofacile.control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet implementation class GestioneTf.
 */
@WebServlet("/GestioneTf")
public class GestioneTf extends HttpServlet {
  private static final long serialVersionUID = 1L;
       
  /** costruttore.
  */
  public GestioneTf() {
        super();
  }

  /** doGet.
   * 
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws ServletException, IOException {
    response.getWriter().append("Served at: ").append(request.getContextPath());
  }


  /** doPost.
   * 
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response) 
      throws ServletException, IOException {
    doGet(request, response);
  }

}
