package it.tirociniofacile.bean;

import java.io.Serializable;

public class ProfiloStudenteBean extends UtenteBean implements Serializable {

  private String matricola;
  
  public ProfiloStudenteBean() {
 
   
  }
  
  public ProfiloStudenteBean(String matricola, String email, String password) {
    super(email,password);
    this.matricola = matricola;
  }

  public String getMatricola() {
    return matricola;
  }

  public void setMatricola(String matricola) {
    this.matricola = matricola;
  }

  
}
