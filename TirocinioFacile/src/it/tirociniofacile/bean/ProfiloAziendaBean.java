package it.tirociniofacile.bean;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class ProfiloAziendaBean extends UtenteBean implements Serializable {
  //variabili di istanza
  private static final long serialVersionUID = 4L;
  private String nomeAzienda;
  
  //costruttore vuoto
  public ProfiloAziendaBean() {
  }
  
  /**
   * Rappresenta il profilo di un azienda registrata sulla
   * piattaforma.
   * @param email indirizzo email dell'azienda
   * @param password password del profilo dell'azienda
   * @param nomeAzienda nome dell'azienda
   */
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
  
  /**
   * Permette di scrivere l'oggetto profiloAziendaBean nello stream.
   * @param output output
   * @throws IOException eccezioni di  I/O
   */
  private void writeObject(ObjectOutputStream output) throws IOException {
    
    output.writeObject(this.getEmail());
    output.writeObject(this.getPassword());
    output.writeObject(nomeAzienda);
  }
  /**
   * permette di serializzare i parametri di profilo azienda bean su un file.
   * @param input ObjectInputStream input
   * @throws IOException eccezioni di I/O
   * @throws ClassNotFoundException eccezione file non trovato
   */
  
  private void readObject(ObjectInputStream input) throws IOException, ClassNotFoundException {
    this.setEmail((String) input.readObject());
    this.setPassword((String) input.readObject());
    nomeAzienda = (String) input.readObject();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof ProfiloAziendaBean) {
      ProfiloAziendaBean a = (ProfiloAziendaBean) obj;
      return (a.getEmail().equals(this.getEmail()) 
          && a.getPassword().equals(this.getPassword())
          && a.getNomeAzienda().equals(this.getNomeAzienda()));
    }
    return false;
  }
  
  @Override
  public String toString() {
    return "PROFILO AZIENDA [MAIL: " + getEmail() 
      + ", PASSWORD: " + getPassword() 
      + ", NOME AZIENDA: " + nomeAzienda + "]";
  }

}
