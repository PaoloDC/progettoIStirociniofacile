package it.tirociniofacile.bean;

import java.util.ArrayList;

public class PaginaAziendaBean {
  private String localit�;
  private String nomeAzienda;
  private String skill;
  private String descrizione;
  ArrayList<String> ambito=new ArrayList();
  public PaginaAziendaBean(String localit�, String nomeAzienda, String skill, String descrizione, ArrayList<String> ambito){
    this.localit�=localit�;
    this.nomeAzienda=nomeAzienda;
    this.skill=skill;
    this.descrizione=descrizione;
    this.ambito=ambito;
  }
  public String getLocalit�() {
    return localit�;
  }
  public void setLocalit�(String localit�) {
    this.localit� = localit�;
  }
  public String getNomeAzienda() {
    return nomeAzienda;
  }
  public void setNomeAzienda(String nomeAzienda) {
    this.nomeAzienda = nomeAzienda;
  }
  public String getSkill() {
    return skill;
  }
  public void setSkill(String skill) {
    this.skill = skill;
  }
  public String getDescrizione() {
    return descrizione;
  }
  public void setDescrizione(String descrizione) {
    this.descrizione = descrizione;
  }
  public ArrayList<String> getAmbito() {
    return ambito;
  }
  public void setAmbito(ArrayList<String> ambito) {
    this.ambito = ambito;
  }

}
