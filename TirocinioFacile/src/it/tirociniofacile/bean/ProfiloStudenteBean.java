package it.tirociniofacile.bean;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

private void writeObject(ObjectOutputStream output) throws IOException {
    
    output.writeObject(this.getEmail());
    output.writeObject(this.getPassword());
    output.writeObject(matricola);
  }
  
  /**
   * 
   * @param input
   * @throws IOException
   * @throws ClassNotFoundException
   */
  private void readObject(ObjectInputStream input) throws IOException, ClassNotFoundException {
    //parchiConvenzionati= (ArrayList<Parco>) input.readObject();
    this.setEmail((String) input.readObject());
    this.setPassword((String) input.readObject());
    matricola = (String) input.readObject();
  }
}
