package it.tirociniofacile.bean;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class UtenteBean implements Serializable {

  //variabili di istanza
  private static final long serialVersionUID = -2852770759774575836L;
  private String email;
  private String password;

  public UtenteBean() {
  
  }
  
  public UtenteBean(String email, String password) {
    this.email = email;
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
  
  private void writeObject(ObjectOutputStream output) throws IOException {
    output.writeObject(email);
    output.writeObject(password);
  }
  
  /**
   * 
   * @param input
   * @throws IOException
   * @throws ClassNotFoundException
   */
  private void readObject(ObjectInputStream input) throws IOException, ClassNotFoundException {
    //parchiConvenzionati= (ArrayList<Parco>) input.readObject();
    email = (String) input.readObject();
    password = (String) input.readObject();
  }

  @Override
  public String toString() {
    return "UtenteBean [email=" + email + ", password=" + password + "]";
  }
  

}
