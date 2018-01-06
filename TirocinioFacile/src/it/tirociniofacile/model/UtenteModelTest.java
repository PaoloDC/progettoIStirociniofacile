package it.tirociniofacile.model;

import java.sql.SQLException;

import it.tirociniofacile.bean.*;
import junit.framework.TestCase;

public class UtenteModelTest extends TestCase {

  private static UtenteModel model;
  
  static {
    model = new UtenteModel();
  }
  
  /**
   * 
   */
  public void testSalvaAccountStudente() {
    
    ProfiloStudenteBean studente = 
        new ProfiloStudenteBean("vittorio@studenti.unisa.it","vittorio","0512103123");
    
    try {
      UtenteBean utente = model.caricaAccount(studente.getEmail(),studente.getPassword());
      assertEquals(utente, null);
      
      model.salvaAccountStudente(studente.getEmail(), 
          studente.getPassword(), studente.getMatricola());
      
      utente = model.caricaAccount(studente.getEmail(),studente.getPassword());
      assertEquals(utente, studente);
      
      //si cerca di salvare un account studente con gli stessi dati del precedente
      model.salvaAccountStudente(studente.getEmail(), 
          studente.getPassword(), studente.getMatricola());
      //TODO mi aspetto una eccezione SQL
      
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * 
   */
  public void testSalvaAccountAzienda() {
    ProfiloAziendaBean azienda = 
        new ProfiloAziendaBean("kineton@gmail.com","kineton","Kineton");
    
    try {
      UtenteBean utente = model.caricaAccount(azienda.getEmail(),azienda.getPassword());
      assertEquals(utente, null);
      
      model.salvaAccountAzienda(azienda.getEmail(), 
          azienda.getPassword(), azienda.getNomeAzienda());
      
      utente = model.caricaAccount(azienda.getEmail(),azienda.getPassword());
      assertEquals(utente, azienda);
      
      //si cerca di salvare un account azienda con gli stessi dati del precedente
      model.salvaAccountAzienda(azienda.getEmail(), 
          azienda.getPassword(), azienda.getNomeAzienda());
      
      //TODO mi aspetto una eccezione SQL
      
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * 
   */
  public void testGeneraCredenziali() {
    UtenteBean utenteRandom = new UtenteBean("gino@gmail.com","gino");
    boolean generato1 = model.generaCredenziali(utenteRandom.getEmail());
    assertEquals(generato1, false);
    
    UtenteBean utenteAmministrativo = new UtenteBean("presidente@unisa.it","presidente");
    boolean generato2 = model.generaCredenziali(utenteAmministrativo.getEmail());
    assertEquals(generato2, true);
  }
 
  public void testCaricaUtentiDaFile() {
    
  }
  
  public void testSalvaUtentiNelFile() {
    
  }
  
  public void testCaricaAccount() {
    
  }
  
  public void testCercaAccountPerEmail() {
    
  }
  
  public static void main(String[] args) {
    
  }
}
