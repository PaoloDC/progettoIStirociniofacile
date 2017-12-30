package it.tirociniofacile.bean;

import java.io.Serializable;

public class ProfiloAziendaBean extends UtenteBean implements Serializable {

  private String nomeAzienda;
  
  public ProfiloAziendaBean() {

  }
  
  public ProfiloAziendaBean(String nomeAzienda, String email, String password) {
    super(email,password);
    this.nomeAzienda = nomeAzienda;
  }
  
  public String getNomeAzienda() {
    return nomeAzienda;
  }

  public void setNomeAzienda(String nomeAzienda) {
    this.nomeAzienda = nomeAzienda;
  }

 

}
