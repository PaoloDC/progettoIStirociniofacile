package it.tirociniofacile.bean;


public class DocumentoConvenzioneBean {


  public DocumentoConvenzioneBean(boolean approvato, String nomeAzienda, String sedeLegale, String città, String RappresentanteLegale, String LuogoNascitaRappresentanteLegale, String DataNascitaRappresentanteLegale){
    this.approvato=approvato;
    this.nomeAzienda=nomeAzienda;
    this.sedeLegale=sedeLegale;
    this.città=città;
    this.RappresentanteLegale=RappresentanteLegale;
    this.LuogoNascitaRappresentanteLegale=LuogoNascitaRappresentanteLegale;
    this.DataNascitaRappresentanteLegale=DataNascitaRappresentanteLegale;
    
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
public String getLuogoNascitaRappresentanteLegale() {
  return LuogoNascitaRappresentanteLegale;
}
public void setLuogoNascitaRappresentanteLegale(String luogoNascitaRappresentanteLegale) {
  LuogoNascitaRappresentanteLegale = luogoNascitaRappresentanteLegale;
}
public String getDataNascitaRappresentanteLegale() {
  return DataNascitaRappresentanteLegale;
}
public void setDataNascitaRappresentanteLegale(String dataNascitaRappresentanteLegale) {
  DataNascitaRappresentanteLegale = dataNascitaRappresentanteLegale;
}


  private boolean approvato;
  private String nomeAzienda;
  private String sedeLegale;
  private String città;
  private String RappresentanteLegale;
  private String LuogoNascitaRappresentanteLegale;
  private String DataNascitaRappresentanteLegale;

}
