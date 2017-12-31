package it.tirociniofacile.bean;

public class DocumentoQuestionarioBean {
  private boolean approvato;
  private String informazioniSulTirocinio;
  private String commenti;
  private String suggerimenti;
  private String annoAccademico;
  private String matricola;
  private float giudizioEsperienza;
  private float giudizioAzienda;
  private float giudizioUniversita;
  
  public DocumentoQuestionarioBean() {
    
  }
  
  /**
   * Rappresenta i dati salvati nel sistema riguardanti il questionario di valutazione che 
   * lo studente sull'azienda.
   * @param approvato documento approvato o non ancora revisionato
   * @param informazioniSulTirocinio informazioni sul tirocinio svolto
   * @param commenti commenti sul tirocinio
   * @param suggerimenti suggerimenti dello studente per l'azienda
   * @param annoAccademico anno accdemico del tirocinio
   * @param matricola matricola dello studeete
   * @param giudizioEsperienza media sul giudizio esperienza
   * @param giudizioAzienda media sul giudizio azienda
   * @param giudizioUniversita media sul giudizio universita
   */
  public DocumentoQuestionarioBean(boolean approvato, String informazioniSulTirocinio, 
      String commenti, String suggerimenti, String annoAccademico, 
      String matricola, float giudizioEsperienza, float giudizioAzienda, float giudizioUniversita) {
    this.approvato = approvato;
    this.informazioniSulTirocinio = informazioniSulTirocinio;
    this.commenti = commenti;
    this.suggerimenti = suggerimenti;
    this.annoAccademico = annoAccademico;
    this.matricola = matricola;
    this.giudizioEsperienza = giudizioEsperienza;
    this.giudizioAzienda = giudizioAzienda;
    this.giudizioUniversita = giudizioUniversita;
  }

  public boolean isApprovato() {
    return approvato;
  }

  public void setApprovato(boolean approvato) {
    this.approvato = approvato;
  }

  public String getInformazioniSulTirocinio() {
    return informazioniSulTirocinio;
  }

  public void setInformazioniSulTirocinio(String informazioniSulTirocinio) {
    this.informazioniSulTirocinio = informazioniSulTirocinio;
  }

  public String getCommenti() {
    return commenti;
  }

  public void setCommenti(String commenti) {
    this.commenti = commenti;
  }

  public String getSuggerimenti() {
    return suggerimenti;
  }

  public void setSuggerimenti(String suggerimenti) {
    this.suggerimenti = suggerimenti;
  }

  public String getAnnoAccademico() {
    return annoAccademico;
  }

  public void setAnnoAccademico(String annoAccademico) {
    this.annoAccademico = annoAccademico;
  }

  public String getMatricola() {
    return matricola;
  }

  public void setMatricola(String matricola) {
    this.matricola = matricola;
  }

  public float getGiudizioEsperienza() {
    return giudizioEsperienza;
  }

  public void setGiudizioEsperienza(float giudizioEsperienza) {
    this.giudizioEsperienza = giudizioEsperienza;
  }

  public float getGiudizioAzienda() {
    return giudizioAzienda;
  }

  public void setGiudizioAzienda(float giudizioAzienda) {
    this.giudizioAzienda = giudizioAzienda;
  }

  public float getGiudizioUniversita() {
    return giudizioUniversita;
  }

  public void setGiudizioUniversita(float giudizioUniversita) {
    this.giudizioUniversita = giudizioUniversita;
  }
}