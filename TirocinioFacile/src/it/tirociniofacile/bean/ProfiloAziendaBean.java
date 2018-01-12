package it.tirociniofacile.bean;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class ProfiloAziendaBean extends UtenteBean implements Serializable {

  private String nomeAzienda;
  
  public ProfiloAziendaBean() {

  }
  
  public ProfiloAziendaBean(String email, String password,String nomeAzienda) {
    super(email,password);
    this.nomeAzienda = nomeAzienda;
  }
  
  public String getNomeAzienda() {
    return nomeAzienda;
  }

  public void setNomeAzienda(String nomeAzienda) {
    this.nomeAzienda = nomeAzienda;
  }
  
  private void writeObject(ObjectOutputStream output) throws IOException {
    
    output.writeObject(this.getEmail());
    output.writeObject(this.getPassword());
    output.writeObject(nomeAzienda);
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
    nomeAzienda = (String) input.readObject();
  }

  /**
   * 
   */
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof ProfiloAziendaBean) {
      ProfiloAziendaBean a = (ProfiloAziendaBean) obj;
      return a.getEmail().equals(this.getEmail()) 
          && a.getPassword().equals(this.getPassword())
          && a.getNomeAzienda().equals(this.getNomeAzienda());
    }
    return false;
  }

}
