package it.tirociniofacile.bean;

public class DocumentoConvenzioneBean {

  public DocumentoConvenzioneBean(boolean approvato, String nomeAzienda, String sedeLegale, String citt�, String rappresentanteLegale, String luogoNascitaRappresentanteLegale, String dataNascitaRappresentanteLegale){
    this.approvato=approvato;
    this.nomeAzienda=nomeAzienda;
    this.sedeLegale=sedeLegale;
    this.citt�=citt�;
    this.rappresentanteLegale=rappresentanteLegale;
    this.luogoNascitaRappresentanteLegale=luogoNascitaRappresentanteLegale;
    this.dataNascitaRappresentanteLegale=dataNascitaRappresentanteLegale;
    
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
  public String getCitt�() {
    return citt�;
  }
  public void setCitt�(String citt�) {
    this.citt� = citt�;
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
  private String citt�;
  private String sedeLegale;
  private String rappresentanteLegale;
  private String luogoNascitaRappresentanteLegale;
  private String dataNascitaRappresentanteLegale;
}