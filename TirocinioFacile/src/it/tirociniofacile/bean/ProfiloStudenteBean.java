package it.tirociniofacile.bean;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class ProfiloStudenteBean extends UtenteBean implements Serializable {

  //variabile di istanza
  private String matricola;
  
  //costruttore vuoto
  public ProfiloStudenteBean() {
  }
  
  //costruttore
  /**
   * Rappresenta il profilo degli studenti registrati sulla piattaforma.
   * @param email indirizzo email dello studente registrato sulla piattaforma
   * @param password password dell'account dello studente
   * @param matricola numero di matricola dello studente iscritto all'università
   */
  public ProfiloStudenteBean(String email, String password,String matricola) {
    super(email,password);
    this.matricola = matricola;
  }
  
  //setter & getter
  public String getMatricola() {
    return matricola;
  }

  public void setMatricola(String matricola) {
    this.matricola = matricola;
  }
  /**
   * permette di scrivere l'oggetto profilo studente bean in uno stream.
   * @param output output
   * @throws IOException eccezioni di I/O
   */
  
  private void writeObject(ObjectOutputStream output) throws IOException {  
    output.writeObject(this.getEmail());
    output.writeObject(this.getPassword());
    output.writeObject(matricola);
  }
  
  /**
  * permette di serializzare in un file i parametri di profiloStudenteBean.
  * @param input input
  * @throws IOException eccezioni di I/O
  * @throws ClassNotFoundException file non trovato
  */
  
  private void readObject(ObjectInputStream input) throws IOException, ClassNotFoundException {
    //parchiConvenzionati= (ArrayList<Parco>) input.readObject();
    this.setEmail((String) input.readObject());
    this.setPassword((String) input.readObject());
    matricola = (String) input.readObject();
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof ProfiloStudenteBean) {
      ProfiloStudenteBean s = (ProfiloStudenteBean) obj;
      return (s.getEmail().equals(this.getEmail()) 
          && s.getPassword().equals(this.getPassword())
          && s.getMatricola().equals(this.getMatricola()));
    }
    return false;
  }
  
  @Override
  public String toString() {
    return "PROFILO STUDENTE [MAIL: " + getEmail() 
      + ", PASSWORD: " + getPassword() 
      + ", MATRICOLA: " + matricola + "]";
  }
}
