

package it.tirociniofacile.bean;

import java.sql.Date;

public class DocumentoQuestionarioBean {

  public DocumentoQuestionarioBean(boolean approvato, String InformazioniSulTirocinio, String Commenti, String Suggerimenti, Date AnnoAccademico, float GiudizioEsperienza, float GiudizioAzienda, float GiudizioUniversità, String Matricola ) {
    this.approvato=approvato;
    this.InformazioniSulTirocinio=InformazioniSulTirocinio;
    this.Commenti=Commenti;
    this.Suggerimenti=Suggerimenti;
    this.AnnoAccademico=AnnoAccademico;
    this.GiudizioEsperienza=GiudizioEsperienza;
    this.GiudizioAzienda=GiudizioAzienda;
    this.GiudizioUniversità=GiudizioUniversità;

  }
  
  public boolean getApprovato() {
    return approvato;
  }
  public void setApprovato(boolean approvato) {
    this.approvato = approvato;
  }
  public String getInformazioniSulTirocinio() {
    return InformazioniSulTirocinio;
  }
  public void setInformazioniSulTirocinio(String informazioniSulTirocinio) {
    InformazioniSulTirocinio = informazioniSulTirocinio;
  }
  public String getCommenti() {
    return Commenti;
  }
  public void setCommenti(String commenti) {
    Commenti = commenti;
  }
  public String getSuggerimenti() {
    return Suggerimenti;
  }
  public void setSuggerimenti(String suggerimenti) {
    Suggerimenti = suggerimenti;
  }
  public Date getAnnoAccademico() {
    return AnnoAccademico;
  }
  public void setAnnoAccademico(Date annoAccademico) {
    AnnoAccademico = annoAccademico;
  }
  public float getGiudizioEsperienza() {
    return GiudizioEsperienza;
  }
  public void setGiudizioEsperienza(float giudizioEsperienza) {
    GiudizioEsperienza = giudizioEsperienza;
  }
  public float getGiudizioAzienda() {
    return GiudizioAzienda;
  }
  public void setGiudizioAzienda(float giudizioAzienda) {
    GiudizioAzienda = giudizioAzienda;
  }
  public float getGiudizioUniversità() {
    return GiudizioUniversità;
  }
  public void setGiudizioUniversità(float giudizioUniversità) {
    GiudizioUniversità = giudizioUniversità;
  }
  public String getMatricola() {
    return Matricola;
  }
  public void setMatricola(String matricola) {
    Matricola = matricola;
  }

  private boolean approvato;
  private String InformazioniSulTirocinio;
  private String Commenti;
  private String Suggerimenti;
  private Date AnnoAccademico;
  private float GiudizioEsperienza;
  private float GiudizioAzienda;
  private float GiudizioUniversità;
  private String Matricola;

}

