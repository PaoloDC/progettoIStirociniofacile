package it.tirociniofacile.model;

import java.sql.SQLException;
import java.util.ArrayList;

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
 
  /**
   * 
   */
  public void testCaricaUtentiDaFile() {
    UtenteBean ub = new UtenteBean("utente4@unisa.it","utente4");
    
    ArrayList<UtenteBean> lista = model.caricaUtentiDaFile();
    assertEquals(lista.contains(ub), false);
    
    lista.add(ub);
    model.salvaUtentiNelFile(lista);

    ArrayList<UtenteBean> nuovaLista = model.caricaUtentiDaFile();
    assertEquals(nuovaLista.contains(ub), true);
  }
  
  /**
   * 
   */
  public void testSalvaUtentiNelFile() {
    UtenteBean ub = new UtenteBean("utente5@unisa.it","utente5");
    
    ArrayList<UtenteBean> lista = model.caricaUtentiDaFile();
    assertEquals(lista.contains(ub), false);
    
    lista.add(ub);
    model.salvaUtentiNelFile(lista);
    
    lista = model.caricaUtentiDaFile();
    assertEquals(lista.contains(ub), true);
    
  }
  
  
  public void testCaricaAccount() {
    ProfiloStudenteBean psb = new ProfiloStudenteBean("studente_prova@studenti.unisa.it",
        "prova","0512101010");
    ProfiloAziendaBean pab  = new ProfiloAziendaBean("azienda_prova@gmail.com",
        "prova","Azienda Prova");
    UtenteBean ub = new UtenteBean("utente_prova@unisa.it","prova");
    
    
    
  }
  
  public void testCercaAccountPerEmail() {
    
  }
}
