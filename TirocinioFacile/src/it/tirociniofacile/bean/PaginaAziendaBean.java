

    package it.tirociniofacile.bean;

public class PaginaAziendaBean {
  private String localitÓ;
  private String descrizione;
  private String nomeAzienda;
  private String ambito; 
  private String skill;

  public PaginaAziendaBean(String localitÓ, String descrizione, String nomeAzienda, String ambito, String skill) {
   this.localitÓ=localitÓ;
   this.descrizione=descrizione;
   this.nomeAzienda=nomeAzienda;
   this.ambito=ambito;
   this.skill=skill;
   
  }

public String getLocalitÓ() {
  return localitÓ;
}

public void setLocalitÓ(String localitÓ) {
  this.localitÓ = localitÓ;
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
