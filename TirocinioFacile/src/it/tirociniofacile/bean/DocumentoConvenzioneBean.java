package it.tirociniofacile.bean;

public class DocumentoConvenzioneBean {
  private boolean approvato;
  private String nomeAzienda;
  private String sedeLegale;
  private String città;
  private String RappresentanteLegale;
  private String LuogoDiNascitaRappresentanteLegale;
  private String DataDiNascitaRappresentanteLegale;
  public DocumentoConvenzioneBean(  boolean approvato, String nomeAzienda,String sedeLegale,String città,String RappresentanteLegale,String LuogoDiNascitaRappresentanteLegale,String DataDiNascitaRappresentanteLegale){
    this.approvato=approvato;
    this.nomeAzienda=nomeAzienda;
    this.sedeLegale=sedeLegale;
    this.città=città;
    this.RappresentanteLegale=RappresentanteLegale;
    this.LuogoDiNascitaRappresentanteLegale=LuogoDiNascitaRappresentanteLegale;
    this.DataDiNascitaRappresentanteLegale=DataDiNascitaRappresentanteLegale;
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
  public String getSedeLegale() {
    return sedeLegale;
  }
  public void setSedeLegale(String sedeLegale) {
    this.sedeLegale = sedeLegale;
  }
  public String getCittà() {
    return città;
  }
  public void setCittà(String città) {
    this.città = città;
  }
  public String getRappresentanteLegale() {
    return RappresentanteLegale;
  }
  public void setRappresentanteLegale(String rappresentanteLegale) {
    RappresentanteLegale = rappresentanteLegale;
  }
  public String getLuogoDiNascitaRappresentanteLegale() {
    return LuogoDiNascitaRappresentanteLegale;
  }
  public void setLuogoDiNascitaRappresentanteLegale(String luogoDiNascitaRappresentanteLegale) {
    LuogoDiNascitaRappresentanteLegale = luogoDiNascitaRappresentanteLegale;
  }
  public String getDataDiNascitaRappresentanteLegale() {
    return DataDiNascitaRappresentanteLegale;
  }
  public void setDataDiNascitaRappresentanteLegale(String dataDiNascitaRappresentanteLegale) {
    DataDiNascitaRappresentanteLegale = dataDiNascitaRappresentanteLegale;
  }

}
