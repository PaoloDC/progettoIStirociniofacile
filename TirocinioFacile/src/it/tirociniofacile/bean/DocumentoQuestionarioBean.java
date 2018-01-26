package it.tirociniofacile.bean;

/**
 * Classe che modella un documento questionario
 * 
 * @author Paolo De Cristofaro
 *
 */
public class DocumentoQuestionarioBean {
  // variabili di istanza
  private int id;
  private String commenti;
  private String suggerimenti;
  private String annoAccademico;
  private boolean approvato;
  private String url;
  private String mailStudente;
  private int paginaAziendaId;
  private float giudizioEsperienza;
  private float giudizioAzienda;
  private float giudizioUniversita;

  // costruttore vuoto
  public DocumentoQuestionarioBean() {
  }

  /**
   * Rappresenta i dati salvati nel sistema riguardanti il questionario di valutazione che lo
   * studente sull'azienda.
   * 
   * @param commenti
   *          commenti sul tirocinio
   * @param suggerimenti
   *          suggerimenti dello studente per l'azienda
   * @param annoAccademico
   *          anno accdemico del tirocinio
   * @param approvato
   *          documento approvato o non ancora revisionato
   * @param url
   *          indica il riferimento al pdf del questionario
   * @param mailStudente
   *          mail dello studente
   * @param paginaAziendaId
   *          id della pagina su cui va salvato un estratto del questionario
   * @param giudizioEsperienza
   *          media sul giudizio esperienza
   * @param giudizioAzienda
   *          media sul giudizio azienda
   * @param giudizioUniversita
   *          media sul giudizio universita
   */
  public DocumentoQuestionarioBean(String commenti, String suggerimenti, String annoAccademico,
      boolean approvato, String url, String mailStudente, int paginaAziendaId,
      float giudizioEsperienza, float giudizioAzienda, float giudizioUniversita) {
    super();
    this.commenti = commenti;
    this.suggerimenti = suggerimenti;
    this.annoAccademico = annoAccademico;
    this.approvato = approvato;
    this.url = url;
    this.mailStudente = mailStudente;
    this.paginaAziendaId = paginaAziendaId;
    this.giudizioEsperienza = giudizioEsperienza;
    this.giudizioAzienda = giudizioAzienda;
    this.giudizioUniversita = giudizioUniversita;
  }

  public String getAnnoAccademico() {
    return annoAccademico;
  }

  public String getCommenti() {
    return commenti;
  }

  public float getGiudizioAzienda() {
    return giudizioAzienda;
  }

  public float getGiudizioEsperienza() {
    return giudizioEsperienza;
  }

  public float getGiudizioUniversita() {
    return giudizioUniversita;
  }

  public int getId() {
    return id;
  }

  public String getMailStudente() {
    return mailStudente;
  }

  public int getPaginaAziendaId() {
    return paginaAziendaId;
  }

  public String getSuggerimenti() {
    return suggerimenti;
  }

  public String getUrl() {
    return url;
  }

  public void setAnnoAccademico(String annoAccademico) {
    this.annoAccademico = annoAccademico;
  }

  public void setApprovato(boolean approvato) {
    this.approvato = approvato;
  }

  public void setCommenti(String commenti) {
    this.commenti = commenti;
  }

  public void setGiudizioAzienda(float giudizioAzienda) {
    this.giudizioAzienda = giudizioAzienda;
  }

  public void setGiudizioEsperienza(float giudizioEsperienza) {
    this.giudizioEsperienza = giudizioEsperienza;
  }

  public void setGiudizioUniversita(float giudizioUniversita) {
    this.giudizioUniversita = giudizioUniversita;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setMailStudente(String mailStudente) {
    this.mailStudente = mailStudente;
  }

  public void setPaginaAziendaId(int paginaAziendaId) {
    this.paginaAziendaId = paginaAziendaId;
  }

  public void setSuggerimenti(String suggerimenti) {
    this.suggerimenti = suggerimenti;
  }

  public void setUrl(String url) {
    this.url = url;
  }
//prova push
  @Override
  public String toString() {
    return "Questionario [Id: " + id 
        + ", Commenti: " + commenti 
        + ", Suggerimenti: " + suggerimenti 
        + ", Anno Accademico: " + annoAccademico 
        + ", Approvato: " + approvato 
        + ", Url: " + url 
        + ", Pagina Azienda ID: " + paginaAziendaId 
        + ", Mail Studente: " + mailStudente 
        + ", Giudizio Azienda: " + giudizioAzienda 
        + ", Giudizio Esperienza: " + giudizioEsperienza 
        + ", Giudizio Università: " + giudizioUniversita 
        + " ]";
  }
}