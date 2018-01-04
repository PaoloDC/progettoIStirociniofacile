package it.tirociniofacile.bean;

public class DocumentoQuestionarioBean {
  private boolean approvato;
  private String informazioniSulTirocinio;
  private String commenti;
  private String suggerimenti;
  private String annoAccademico;
  private String mailStudente;
  private String gradoDiSoddisfazioneDelTirocinante;
  private String giudizioEsperienza;
  private String giudizioAzienda;
  private String giudizioUniversita;
  
  
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
   * @param mailStudente mail dello studente 
   * @param giudizioEsperienza media sul giudizio esperienza
   * @param giudizioAzienda media sul giudizio azienda
   * @param giudizioUniversita media sul giudizio universita
   */
  public DocumentoQuestionarioBean(boolean approvato, String informazioniSulTirocinio, 
      String commenti, String suggerimenti, String annoAccademico, 
      String mailStudente, String giudizioEsperienza, String giudizioAzienda, 
      String giudizioUniversita, String gradoDiSoddisfazioneDelTirocinante) {
    this.approvato = approvato;
    this.informazioniSulTirocinio = informazioniSulTirocinio;
    this.commenti = commenti;
    this.suggerimenti = suggerimenti;
    this.annoAccademico = annoAccademico;
    this.mailStudente = mailStudente;
    this.giudizioEsperienza = giudizioEsperienza;
    this.giudizioAzienda = giudizioAzienda;
    this.giudizioUniversita = giudizioUniversita;
    this.gradoDiSoddisfazioneDelTirocinante = gradoDiSoddisfazioneDelTirocinante;
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

  public String getMailStudente() {
    return mailStudente;
  }
  
  public void setMailStudente(String mailStudente) {
    this.mailStudente = mailStudente;
  }

  public String getGiudizioEsperienza() {
    return giudizioEsperienza;
  }

  public void setGiudizioEsperienza(String giudizioEsperienza) {
    this.giudizioEsperienza = giudizioEsperienza;
  }

  public String getGiudizioAzienda() {
    return giudizioAzienda;
  }

  public void setGiudizioAzienda(String giudizioAzienda) {
    this.giudizioAzienda = giudizioAzienda;
  }

  public String getGiudizioUniversita() {
    return giudizioUniversita;
  }

  public void setGiudizioUniversita(String giudizioUniversita) {
    this.giudizioUniversita = giudizioUniversita;
  }
  
  public String getGradoDiSoddisfazioneDelTirocinante() {
    return gradoDiSoddisfazioneDelTirocinante;
  }
  
  public void setGradoDiSoddisfazioneDelTirocinante(String gradoDiSoddisfazioneDelTirocinante) {
    this.gradoDiSoddisfazioneDelTirocinante = gradoDiSoddisfazioneDelTirocinante;
  }
}