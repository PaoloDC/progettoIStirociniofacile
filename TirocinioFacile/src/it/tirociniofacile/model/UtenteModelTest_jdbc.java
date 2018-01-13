package it.tirociniofacile.model;

import java.sql.SQLException;
import java.util.ArrayList;

import it.tirociniofacile.bean.*;
import junit.framework.TestCase;

public class UtenteModelTest_jdbc extends TestCase {

  private static UtenteModel_jdbc model;
  
  static {
    model = new UtenteModel_jdbc();
  }
  
  public void testSalvaAccountStudente() {
    
    ProfiloStudenteBean studente = 
        new ProfiloStudenteBean("vittorio@studenti.unisa.it","vittorio","0512103123");
    
    try {
      
      UtenteBean utente = model.caricaAccount(studente.getEmail(),studente.getPassword());
      assertNull(utente);
      
      model.salvaAccountStudente(studente.getEmail(), 
          studente.getPassword(), studente.getMatricola());
      
      UtenteBean utente2 = model.caricaAccount(studente.getEmail(),studente.getPassword());
      assertNotNull(utente2);
      
      ProfiloStudenteBean s = (ProfiloStudenteBean) utente2;
      assertEquals(studente, s);
      
      model.eliminaProfiloStudente(s);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  
  
  public void testSalvaAccountAzienda() {
    ProfiloAziendaBean azienda = 
        new ProfiloAziendaBean("azienda_test@gmail.com","azienda_test","azienda_test");
    
    try {
      UtenteBean utente = model.caricaAccount(azienda.getEmail(),azienda.getPassword());
      assertNull(utente);
      
      model.salvaAccountAzienda(azienda.getEmail(), 
          azienda.getPassword(), azienda.getNomeAzienda());
      
      utente = model.caricaAccount(azienda.getEmail(),azienda.getPassword());
      assertEquals(utente, azienda);
      ProfiloAziendaBean a = (ProfiloAziendaBean) utente;
      assertEquals(a, azienda);
      
      model.eliminaProfiloAzienda(azienda);
     
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  
 
  public void testSalvaUtentiNelFile() {
    ArrayList<UtenteBean> listaUtenti = new ArrayList<>();
    
    model.salvaUtentiNelFile(listaUtenti);  //svuota il file dal contenuto precedente
    
    UtenteBean u1 = new UtenteBean("utente1@unisa.it","utente1");
    UtenteBean u2 = new UtenteBean("utente2@unisa.it","utente2");
    UtenteBean u3 = new UtenteBean("utente3@unisa.it","utente3");
    UtenteBean u4 = new UtenteBean("utente4@unisa.it","utente4");
    
    listaUtenti.add(u1);
    listaUtenti.add(u2);
    listaUtenti.add(u3);
    listaUtenti.add(u4);
    
    model.salvaUtentiNelFile(listaUtenti);  //salva una lista fittizia nel file
    
    ArrayList<UtenteBean> newList = model.caricaUtentiDaFile();
    
    assertEquals(newList, listaUtenti);
    
    ArrayList<UtenteBean> svuota = new ArrayList<>();
    model.salvaUtentiNelFile(svuota);
    
  }
  

  public void testGeneraCredenziali() {
    UtenteBean utenteRandom = new UtenteBean("gino@gmail.com","gino");
    boolean generato1 = model.generaCredenziali(utenteRandom.getEmail());
    assertFalse(generato1);
    
    UtenteBean utenteAmministrativo = new UtenteBean("presidente@unisa.it","presidente");
    boolean generato2 = model.generaCredenziali(utenteAmministrativo.getEmail());
    assertTrue(generato2);
  }
 

  public void testCaricaUtentiDaFile() {
    ArrayList<UtenteBean> lista = null;
    assertNull(lista);
    
    lista = model.caricaUtentiDaFile();
    assertNotNull(lista);
  }
  

  
  public void testCaricaAccount() {
    
    ProfiloStudenteBean psb = new ProfiloStudenteBean("studente_prova@studenti.unisa.it",
        "prova","0512101010");
    ProfiloAziendaBean pab  = new ProfiloAziendaBean("azienda_prova@gmail.com",
        "prova","Azienda Prova");
    UtenteBean ub = new UtenteBean("utente1@unisa.it","utente1");
    
    try {
      //salva un account azienda e un account studente nel db, ed un account amministrativo nel file
      model.salvaAccountAzienda(pab.getEmail(), pab.getPassword(), pab.getNomeAzienda());
      model.salvaAccountStudente(psb.getEmail(), psb.getPassword(), psb.getMatricola());
      ArrayList<UtenteBean> lista = new ArrayList<>();
      lista.add(ub);
      model.salvaUtentiNelFile(lista);
      
      
      //legge lo studente appena salvato
      UtenteBean utente1 = model.caricaAccount(psb.getEmail(), psb.getPassword());
      assertNotNull(utente1);
      
      ProfiloStudenteBean studente = (ProfiloStudenteBean) utente1;
      assertEquals(studente.getEmail(),psb.getEmail());
      assertEquals(studente.getPassword(),psb.getPassword());
      assertEquals(studente.getMatricola(),psb.getMatricola());
      
      //legge l'azienda appena salvata
      UtenteBean utente2 = model.caricaAccount(pab.getEmail(), pab.getPassword());
      assertNotNull(utente2);
      
      ProfiloAziendaBean azienda = (ProfiloAziendaBean) utente2;
      assertEquals(azienda.getEmail(),pab.getEmail());
      assertEquals(azienda.getPassword(),pab.getPassword());
      assertEquals(azienda.getNomeAzienda(),pab.getNomeAzienda());
      
      //legge un utente amministrativo
      UtenteBean utente3 = model.caricaAccount(ub.getEmail(), ub.getPassword());
      assertNotNull(utente3);
      assertEquals(utente3.getEmail(),ub.getEmail());
      assertEquals(utente3.getPassword(),ub.getPassword());
      
      
      //elimina gli account inseriti per il test
      model.eliminaProfiloAzienda(pab);
      model.eliminaProfiloStudente(psb);
      ArrayList<UtenteBean> svuota = new ArrayList<>();
      model.salvaUtentiNelFile(svuota);
      
    } catch (SQLException e) {
      e.printStackTrace();
    }
    
    
  }
  
  /**
   * 
   */
  public void testCercaAccountPerEmail() {
    
    ProfiloStudenteBean psb = new ProfiloStudenteBean("decri.paolo@gmail.com",
        "decri.paolo","0512101010");
    try {
      boolean res1 = model.cercaAccountPerEmail(psb.getEmail());
      assertFalse(res1);
      
      model.salvaAccountStudente(psb.getEmail(), psb.getPassword(), psb.getMatricola());
      
      boolean res2 = model.cercaAccountPerEmail(psb.getEmail());
      assertTrue(res2);
      
      model.eliminaProfiloStudente(psb);
      
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  
}
