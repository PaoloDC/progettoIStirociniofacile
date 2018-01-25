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
          ricercaQuestionariNonApprovatiPerStudente(request, response);
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
    
    String megaTesto = 
        "PARTE I : INFORMAZIONI SUL LAUREATO\n" 
        + "\n1. Cognome: " + cognome 
        + "\n2. Nome: " + nome
        + "\n3. Telefono 1: " + telefono1
        + "\n4. Telefono 2: " + telefono2
        + "\n5. E-mail: " + email
        + "\n6. Comune di Residenza: " + comune 
        + "\n7. Provincia: "  + provincia
        + "\n8. Anno accademico di immatricolazione: " + annoimm1 + " / " + annoimm2
        + "\n9. CdL di Immatricolazione: " + cdlimm
        + "\n10. Matricola: " + matricola
        + "\n11. Azienda/Laboratorio Interno ospitante il tirocinante: " + nomeAzienda
        + "\n12. Comune dell'Azienda / Laboratorio: " + comune
        + "\n13. Provincia: " + provazienda
        + "\n14. Sesso: " + sesso
        + "\n15. Data di nascita: " + datanascita
        + "\n16. Data: "  + data[0]  + " / " + data[1] + " / " + data[2];

    // PARTE 2
    String parte2dom1 = request.getParameter("parte2dom1");
    String parte2dom1altro = request.getParameter("parte2dom1altro");
    String parte2dom3 = request.getParameter("parte2dom3");
    String parte2dom3altro = request.getParameter("parte2dom3altro");
    String parte2dom4 = request.getParameter("parte2dom4");
    String parte2dom4altro = request.getParameter("parte2dom4altro");

    String[] cinque = request.getParameterValues("cinque");
    String[] sei = request.getParameterValues("sei");
    
    megaTesto += "\n\nPARTE II : INFORMAZIONI SULLO STAGE / TIROCINIO\n"
        + "\n1. Come � avvenuta la scelta dello stage?\n";
        
    if (parte2dom1.equals("altro")) {
      megaTesto += "Altro, Specificare: " + parte2dom1altro;
    } else {
      megaTesto += parte2dom1;
    }
          
    megaTesto += "\n2. Lo stage � stato svolto: all'esterno"
        + "\n3. Qual � stato il ruolo del tutor aziendale durante lo stage?\n";
    
    if (parte2dom3.equals("altro")) {
      megaTesto += "Altro, Specificare: " + parte2dom3altro;
    } else {
      megaTesto += parte2dom3;
    }   
    megaTesto += "\n4. Qual � stato il ruolo del tutor universitario durante lo stage?\r\n"; 
    if (parte2dom4.equals("altro")) {
      megaTesto += "Altro, Specificare: " + parte2dom4altro;
    } else {
      megaTesto += parte2dom4;
    } 
     
    
    megaTesto += "\n\n5. In che misura lei possedeva le seguenti caratteristiche "
        + "al momento dell'inizio dello stage?\n"
        + "Capacit� relazionali e di comunicazione:\t\t" +  cinque[0]
        + "\nCapacit� di lavorare in gruppo:\t\t\t\t" + cinque[1]
        + "\nIniziativa / Autonomia:\t\t\t\t\t" + cinque[2]
        + "\nAbilit� nell'uso degli strumenti e tecniche specifiche:\t" + cinque[3]
        + "\nConoscenza di base:\t\t\t\t\t" + cinque[4]
        + "\nConoscenze linguistiche:\t\t\t\t" + cinque[5]
        + "\nConoscenze tecniche:\t\t\t\t\t" + cinque[6]
        + "\n\n6. Quali delle precedenti caratteristiche lei ritiene di aver potenziato "
        + "maggiormente al termine dello stage?\n"
        + "Capacit� relazionali e di comunicazione:\t\t" + sei[0]
        + "\nCapacit� di lavorare in gruppo:\t\t\t\t" + sei[1]
        + "\nIniziativa / Autonomia:\t\t\t\t\t" + sei[2]
        + "\nAbilit� nell'uso degli strumenti e tecniche specifiche: " + sei[3]
        + "\nConoscenza di base:\t\t\t\t\t" + sei[4]
        + "\nConoscenze linguistiche:\t\t\t\t" + sei[5]
        + "\nConoscenze tecniche:\t\t\t\t\t" + sei[6];

    // PARTE 3 OK
    String[] a = request.getParameterValues("a");
    String[] b = request.getParameterValues("b");
    String[] c = request.getParameterValues("c");
    
    megaTesto +=
        "\n\nPARTE III : GRADO DI SODDISFAZIONE DEL TIROCINANTE Giudizio sull'Esperienza\r\n"
        + "\nA.1 L'esperienza di tirocinio ha arricchito il suo bagaglio di conoscenza?\t" + a[0]
        + "\nA.2 L'esperienza di tirocinio � stata utile dal punto di vista professionale?\t" + a[1]
        + "\nA.3 L'esperienza di tirocinio � stata utile per la sua crescita personale?\t" + a[2]
        + "\nA.4 L'accoglienza a lei riservata in azienda � stata buona?\t\t\t" + a[3]
        + "\nA.5 Come valuta l'esperienza rispetto alle sue aspettative iniziali?\t\t" + a[4] 
        + "\n\nGiudizio sull'Azienda\r\n"
        + "\nB.1 Il tutor aziendale l'ha seguito accuratamente durante il tirocinio?\t\t" + b[0]
        + "\nB.2 Il personale l'ha messo in condizione di rendere al meglio?\t\t\t" + b[1]
        + "\nB.3 Il suo lavoro � stato preso in seria considerazione?\t\t\t" + b[2]
        + "\nB.4 Il ritmo di lavoro � stato adeguato?\t\t\t\t\t" + b[3]
        + "\nB.5 Il tempo impiegato � stato adeguato per lo svolgimento del progetto?\t" + b[4]
        + "\n\nGiudizio sull'Universit�\r\n"
        + "\nC.1 Il tempo impiegato per espletare le attivit� burocratiche "
        + "per dare inizio dell'attivit� di tirocinio � stato adeguato?\t" + c[0]
        + "\nC.2 L'azienda era nel settore di suo gradimento?\t\t\t\t\t\t\t\t\t\t" + c[1]
        + "\nC.3 La gestione dei tirocini le � sembrata efficiente?\t\t\t\t\t\t\t\t\t\t" + c[2]
        + "\nC.4 La preparazione fornita dal Corso di Studi � stata "
        + "adeguata ad affrontare l'attivita' proposta?\t\t\t\t" + c[3]
        + "\nC.5 Le informazioni che le sono state fornite per "
        + "affrontare il tirocinio sono state adeguate?\t\t\t\t\t" + c[4]
        + "\nC.6 I compiti e le responsabilit� del tutor "
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
    
    megaTesto += "\n\nCommenti:\n" + commenti + "\n\nSuggerimenti:\n" + suggerimenti;
    
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
    System.out.println("mailStudente: " + mailStudente);

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
