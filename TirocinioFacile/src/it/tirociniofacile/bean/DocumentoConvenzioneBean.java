package it.tirociniofacile.bean;

public class DocumentoConvenzioneBean {

  public DocumentoConvenzioneBean() {
    
  }
  
  /**
   * Rappresentari dati salvati dal sistema riguardanti il 
   * documento di convenzione tra azienda e università.
   * @param approvato documento approvato non ancora revisionato
   * @param nomeAzienda nome dell'azienda
   * @param sedeLegale sede legale dell'azienda
   * @param citta citta dell'azienda
   * @param rappresentanteLegale rappresentate legale dell'azienda
   * @param luogoNascitaRappresentanteLegale luogo di nascita del rappresentante legale dell'azienda
   * @param dataNascitaRappresentanteLegale data di nascita del rappresentante legale dell'azienda
   */
  public DocumentoConvenzioneBean(boolean approvato, String nomeAzienda, String sedeLegale, 
      String citta, String rappresentanteLegale, String luogoNascitaRappresentanteLegale, 
      String dataNascitaRappresentanteLegale) {
    this.approvato = approvato;
    this.nomeAzienda = nomeAzienda;
    this.sedeLegale = sedeLegale;
    this.citta = citta;
    this.rappresentanteLegale = rappresentanteLegale;
    this.luogoNascitaRappresentanteLegale = luogoNascitaRappresentanteLegale;
    this.dataNascitaRappresentanteLegale = dataNascitaRappresentanteLegale;
    
  }
  
  public boolean isApprovato() {
    return approvato;
  }
  
  public void setApprovato(boolean approvato) {
    this.approvato = approvato;
  }
  
  public String getNomeAzienda() {
    return nomeAzienda;
  }
  
  public void setNomeAzienda(String nomeAzienda) {
    this.nomeAzienda = nomeAzienda;
  }
  
  public String getCitta() {
    return citta;
  }
  
  public void setCitta(String citta) {
    this.citta = citta;
  }
  
  public String getSedeLegale() {
    return sedeLegale;
  }
  
  public void setSedeLegale(String sedeLegale) {
    this.sedeLegale = sedeLegale;
  }
  
  public String getRappresentanteLegale() {
    return rappresentanteLegale;
  }
  
  public void setRappresentanteLegale(String rappresentanteLegale) {
    this.rappresentanteLegale = rappresentanteLegale;
  }
  
  public String getLuogoNascitaRappresentanteLegale() {
    return luogoNascitaRappresentanteLegale;
  }
  
  public void setLuogoNascitaRappresentanteLegale(String luogoNascitaRappresentanteLegale) {
    this.luogoNascitaRappresentanteLegale = luogoNascitaRappresentanteLegale;
  }
  
  public String getDataNascitaRappresentanteLegale() {
    return dataNascitaRappresentanteLegale;
  }
  
  public void setDataNascitaRappresentanteLegale(String dataNascitaRappresentanteLegale) {
    this.dataNascitaRappresentanteLegale = dataNascitaRappresentanteLegale;
  }
  
  private boolean approvato;
  private String nomeAzienda;
  private String citta;
  private String sedeLegale;
  private String rappresentanteLegale;
  private String luogoNascitaRappresentanteLegale;
  private String dataNascitaRappresentanteLegale;
}