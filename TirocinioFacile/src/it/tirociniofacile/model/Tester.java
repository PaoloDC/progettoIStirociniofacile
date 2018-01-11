package it.tirociniofacile.model;

import java.sql.SQLException;

import it.tirociniofacile.bean.ProfiloStudenteBean;
import it.tirociniofacile.bean.UtenteBean;

public class Tester {

private static UtenteModel model;
  
  static {
    model = new UtenteModel();
  }
  
  public static void main(String[] args) {
    ProfiloStudenteBean studente = 
        new ProfiloStudenteBean("vittorio@studenti.unisa.it","vittorio","0512103123");
    
    try {
      UtenteBean utente = model.caricaAccount(studente.getEmail(),studente.getPassword());
      
      model.salvaAccountStudente(studente.getEmail(), 
          studente.getPassword(), studente.getMatricola());
      
      utente = model.caricaAccount(studente.getEmail(),studente.getPassword());
      System.out.println(utente.getEmail() + "    "  + utente.getPassword());
      //si cerca di salvare un account studente con gli stessi dati del precedente
      model.salvaAccountStudente(studente.getEmail(), 
          studente.getPassword(), studente.getMatricola());
      //TODO mi aspetto una eccezione SQL
      
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}
