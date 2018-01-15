package it.tirociniofacile.control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

//import it.tirociniofacile.model.UtenteModelTest;
import junit.framework.TestCase;

@WebServlet("/GestioneUtenteTest")
public class GestioneUtenteTest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestioneUtenteTest() {
        super();
        // TODO Auto-generated constructor stub
    }
   /* 
    @Test
    public void testServlet() {
      UtenteModelTest t = new UtenteModelTest();
      
      t.testSalvaAccountAzienda();
    }*/
}
