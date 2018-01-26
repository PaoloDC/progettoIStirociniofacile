package it.tirociniofacile.control;

import java.io.IOException;
import java.io.PrintWriter;
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
        } else if (action.equals("caricaDocumento")) {
          caricaDocumento(request, response);
        } else if (action.equals("convalidaDocumento")) {
          convalidaDocumento(request, response);
        } else if (action.equals("compilaConvenzioneAzienda")) {
          //compilaConvenzioneAzienda(request, response);
        } else if (action.equals("compilaQuestionario")) {
          compilaQuestionario(request, response);
        } else if (action.equals("ricercaTuttiDocumentiConvenzioneAzienda")) {
          ricercaTuttiDocumentiConvenzioneAzienda(request, response);
        } else if (action.equals("ricercaTuttiDocumentiQuestionariAzienda")) {
          ricercaTuttiDocumentiQuestionariAzienda(request, response);
        } else if (action.equals("ricercaQuestionariNonApprovatiPerStudente")) {
          ricercaQuestionariNonApprovatiPerStudente(request, response);
        } else if (action.equals("ricercaConvenzionePerAzienda")) {
          ricercaConvenzionePerAzienda(request,response);
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

    String piva = (request.getParameter("partitaIva"));
    System.out.println("akjsak" + piva);
    if (model.ricercaConvenzionePerPartitaIva(piva) != null) {

      request.removeAttribute("convenzione");
      request.setAttribute("convenzione", model.ricercaConvenzionePerPartitaIva(piva));
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

  public void ricercaConvenzionePerAzienda(HttpServletRequest request, 
      HttpServletResponse response) 
        throws SQLException {
    String email = request.getParameter("email");
    
    DocumentoConvenzioneBean doc = model.ricercaConvenzionePerEmail(email);
    
    if (doc != null) {
      request.removeAttribute("convenzione");
      request.setAttribute("convenzione", doc);
      
    }

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
    
    String piva = request.getParameter("piva");

    Part pdf = request.getPart("file");

    String fileName = extractFileName(pdf);

    pdf.write(DocumentoModel.SAVE_PATH + fileName);

    String idDocumento = request.getParameter("idDocumento");

    if (tipologiaAccount.equals("studente")) {
      model.salvaPdfQuestionario(fileName, email, idDocumento);

      RequestDispatcher rd = request.getRequestDispatcher("/homeStudente.jsp");
      rd.forward(request, response);

    } else if (tipologiaAccount.equals("azienda")) {
      model.salvaPdfConvenzione(fileName, piva);
      UtenteBean ub = (UtenteBean) request.getSession().getAttribute("account");

      DocumentoModel docModel = new DocumentoModel();
      DocumentoConvenzioneBean conv = docModel.ricercaConvenzionePerEmail(ub.getEmail());
      
      request.getSession().removeAttribute("convenzioneAzienda");
      request.getSession().setAttribute("convenzioneAzienda", conv);
      
      RequestDispatcher rd = request.getRequestDispatcher("/homeAzienda.jsp");
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
  /*
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
        luogoDiNascitaRappLegale, dataDiNascitaRappLegale,null);
  }
*/
  /**
   * servlet compila questionario.
   * 
   * @param request
   *          richiesta http
   * @throws SQLException
   *           eccezzioni sql
   */
  public void compilaQuestionario(HttpServletRequest request, HttpServletResponse response) {

    // PARTE 1 OK
    String cognome = request.getParameter("cognome");
    String nome = request.getParameter("nome");
    String telefono1 = request.getParameter("telefono1");
    String telefono2 = request.getParameter("telefono2");
    String email = request.getParameter("email");
    String comune = request.getParameter("comune");
    String provincia = request.getParameter("provincia");
    String annoimm1 = request.getParameter("annoimm1");
    String annoimm2 = request.getParameter("annoimm2");
    String cdlimm = request.getParameter("cdlimm");
    String matricola = request.getParameter("matricola");
    String valAzienda = request.getParameter("azienda");
    String[] valoriAzienda = valAzienda.split(",");
    String id = valoriAzienda[0];
    String nomeAzienda = valoriAzienda[1];
    String provazienda = request.getParameter("provazienda");
    String sesso = request.getParameter("sesso");
    String datanascita = request.getParameter("datanascita");
    String[] data = request.getParameterValues("data");
    
    String megaTesto = "PARTE I : INFORMAZIONI SUL LAUREATO<br>" 
        + "<br>1. Cognome: " + cognome 
        + "<br>2. Nome: " + nome
        + "<br>3. Telefono 1: " + telefono1
        + "<br>4. Telefono 2: " + telefono2
        + "<br>5. E-mail: " + email
        + "<br>6. Comune di Residenza: " + comune 
        + "<br>7. Provincia: "  + provincia
        + "<br>8. Anno accademico di immatricolazione: " + annoimm1 + " / " + annoimm2
        + "<br>9. CdL di Immatricolazione: " + cdlimm
        + "<br>10. Matricola: " + matricola
        + "<br>11. Azienda/Laboratorio Interno ospitante il tirocinante: " + nomeAzienda
        + "<br>12. Comune dell'Azienda / Laboratorio: " + comune
        + "<br>13. Provincia: " + provazienda
        + "<br>14. Sesso: " + sesso
        + "<br>15. Data di nascita: " + datanascita
        + "<br>16. Data: "  + data[0]  + " / " + data[1] + " / " + data[2];

    // PARTE 2
    String parte2dom1 = request.getParameter("parte2dom1");
    String parte2dom1altro = request.getParameter("parte2dom1altro");
    String parte2dom3 = request.getParameter("parte2dom3");
    String parte2dom3altro = request.getParameter("parte2dom3altro");
    String parte2dom4 = request.getParameter("parte2dom4");
    String parte2dom4altro = request.getParameter("parte2dom4altro");

    String[] cinque = request.getParameterValues("cinque");
    String[] sei = request.getParameterValues("sei");
    
    megaTesto += "<br><br>PARTE II : INFORMAZIONI SULLO STAGE / TIROCINIO<br>"
        + "<br>1. Come è avvenuta la scelta dello stage?<br>";
        
    if (parte2dom1.equals("altro")) {
      megaTesto += "Altro, Specificare: " + parte2dom1altro;
    } else {
      megaTesto += parte2dom1;
    }
          
    megaTesto += "<br>2. Lo stage è stato svolto: all'esterno"
        + "<br>3. Qual è stato il ruolo del tutor aziendale durante lo stage?<br>";
    
    if (parte2dom3.equals("altro")) {
      megaTesto += "Altro, Specificare: " + parte2dom3altro;
    } else {
      megaTesto += parte2dom3;
    }   
    megaTesto += "<br>4. Qual è stato il ruolo del tutor universitario durante lo stage?<br>"; 
    if (parte2dom4.equals("altro")) {
      megaTesto += "Altro, Specificare: " + parte2dom4altro;
    } else {
      megaTesto += parte2dom4;
    } 
    
    megaTesto += "<br><br>5. In che misura lei possedeva le seguenti caratteristiche "
        + "al momento dell'inizio dello stage?<br>"
        + "Capacità relazionali e di comunicazione:\t\t" +  cinque[0]
        + "<br>Capacità di lavorare in gruppo:\t\t\t\t" + cinque[1]
        + "<br>Iniziativa / Autonomia:\t\t\t\t\t" + cinque[2]
        + "<br>Abilità nell'uso degli strumenti e tecniche specifiche:\t" + cinque[3]
        + "<br>Conoscenza di base:\t\t\t\t\t" + cinque[4]
        + "<br>Conoscenze linguistiche:\t\t\t\t" + cinque[5]
        + "<br>Conoscenze tecniche:\t\t\t\t\t" + cinque[6]
        + "<br><br>6. Quali delle precedenti caratteristiche lei ritiene di aver potenziato "
        + "maggiormente al termine dello stage?<br>"
        + "Capacità relazionali e di comunicazione:\t\t" + sei[0]
        + "<br>Capacità di lavorare in gruppo:\t\t\t\t" + sei[1]
        + "<br>Iniziativa / Autonomia:\t\t\t\t\t" + sei[2]
        + "<br>Abilità nell'uso degli strumenti e tecniche specifiche: " + sei[3]
        + "<br>Conoscenza di base:\t\t\t\t\t" + sei[4]
        + "<br>Conoscenze linguistiche:\t\t\t\t" + sei[5]
        + "<br>Conoscenze tecniche:\t\t\t\t\t" + sei[6];

    // PARTE 3 OK
    String[] a = request.getParameterValues("a");
    String[] b = request.getParameterValues("b");
    String[] c = request.getParameterValues("c");
    
    megaTesto +=
        "<br><br>PARTE III : GRADO DI SODDISFAZIONE DEL TIROCINANTE Giudizio sull'Esperienza<br>"
        + "<br>A.1 L'esperienza di tirocinio ha arricchito il suo bagaglio di conoscenza?\t" + a[0]
        + "<br>A.2 L'esperienza di tirocinio è stata utile dal punto di vista professionale?\t" + a[1]
        + "<br>A.3 L'esperienza di tirocinio è stata utile per la sua crescita personale?\t" + a[2]
        + "<br>A.4 L'accoglienza a lei riservata in azienda è stata buona?\t\t\t" + a[3]
        + "<br>A.5 Come valuta l'esperienza rispetto alle sue aspettative iniziali?\t\t" + a[4] 
        + "<br><br>Giudizio sull'Azienda<br>"
        + "<br>B.1 Il tutor aziendale l'ha seguito accuratamente durante il tirocinio?\t\t" + b[0]
        + "<br>B.2 Il personale l'ha messo in condizione di rendere al meglio?\t\t\t" + b[1]
        + "<br>B.3 Il suo lavoro è stato preso in seria considerazione?\t\t\t" + b[2]
        + "<br>B.4 Il ritmo di lavoro è stato adeguato?\t\t\t\t\t" + b[3]
        + "<br>B.5 Il tempo impiegato è stato adeguato per lo svolgimento del progetto?\t" + b[4]
        + "<br><br>Giudizio sull'Università<br>"
        + "<br>C.1 Il tempo impiegato per espletare le attività burocratiche "
        + "per dare inizio dell'attività di tirocinio è stato adeguato?\t" + c[0]
        + "<br>C.2 L'azienda era nel settore di suo gradimento?\t\t\t\t\t\t\t\t\t\t" + c[1]
        + "<br>C.3 La gestione dei tirocini le è sembrata efficiente?\t\t\t\t\t\t\t\t\t\t" + c[2]
        + "<br>C.4 La preparazione fornita dal Corso di Studi è stata "
        + "adeguata ad affrontare l'attivita' proposta?\t\t\t\t" + c[3]
        + "<br>C.5 Le informazioni che le sono state fornite per "
        + "affrontare il tirocinio sono state adeguate?\t\t\t\t\t" + c[4]
        + "<br>C.6 I compiti e le responsabilità del tutor "
        + "interno vanno aumentati?\t\t\t\t\t\t\t\t" + c[5];
    
    
    
    float giudizioEsperienza = 0;
    for (int i = 0; i < a.length; i++) {
      giudizioEsperienza += (Integer.parseInt(a[i]));
    }
    giudizioEsperienza = giudizioEsperienza / a.length;
    
    float giudizioAzienda = 0;
    for (int i = 0; i < 5; i++) {
      giudizioEsperienza += (Integer.parseInt(b[i]));
    }
    giudizioAzienda = giudizioAzienda / b.length;
    
    float giudizioUniversita = 0;
    for (int i = 0; i < 6; i++) {
      giudizioEsperienza += (Integer.parseInt(c[i]));
    }
    giudizioUniversita = giudizioUniversita / c.length;
    
    String annoAccademico = data[2];
    String mailStudente = request.getParameter("mailStudente");
    String commenti = request.getParameter("commenti");
    String suggerimenti = request.getParameter("suggerimenti");
    
    megaTesto += "<br><br>Commenti:<br>" + commenti + "<br><br>Suggerimenti:<br>" + suggerimenti;
    
    System.out.print("MEGAAAAAAAAAAA:\n" + megaTesto);
    
    model.salvaQuestionario(commenti, suggerimenti, annoAccademico, mailStudente,
        Integer.parseInt(id), matricola, giudizioEsperienza, 
        giudizioAzienda, giudizioUniversita,megaTesto);
    
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

    if (mailStudente != null) {
      ArrayList<String> questionariStudente = model
          .ricercaQuestionariNonApprovatiPerStudente(mailStudente);

      PaginaAziendaModel pam = new PaginaAziendaModel();
      ArrayList<PaginaAziendaBean> listaPagine = pam.ricerca();

      request.getSession().setAttribute("questionariStudente", questionariStudente);
      request.getSession().setAttribute("listaPagine", listaPagine);

      RequestDispatcher rd = request.getRequestDispatcher("/caricaDocumento.jsp");
      rd.forward(request, response);
    }

  }

}
