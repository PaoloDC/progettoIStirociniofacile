

package it.tirociniofacile.bean;

public class PaginaAziendaBean {
  private String localit�;
  private String descrizione;
  private String nomeAzienda;
  private String ambito; 
  private String skill;

  public PaginaAziendaBean(String localit�, String descrizione, String nomeAzienda, String ambito, String skill) {
   this.localit�=localit�;
   this.descrizione=descrizione;
   this.nomeAzienda=nomeAzienda;
   this.ambito=ambito;
   this.skill=skill;
   
  }

public String getLocalit�() {
  return localit�;
}

public void setLocalit�(String localit�) {
  this.localit� = localit�;
}

public String getDescrizione() {
  return descrizione;
}

public void setDescrizione(String descrizione) {
  this.descrizione = descrizione;
}

public String getNomeAzienda() {
  return nomeAzienda;
}

public void setNomeAzienda(String nomeAzienda) {
  this.nomeAzienda = nomeAzienda;
}

public String getAmbito() {
  return ambito;
}

public void setAmbito(String ambito) {
  this.ambito = ambito;
}

public String getSkill() {
  return skill;
}

public void setSkill(String skill) {
  this.skill = skill;
}

}
