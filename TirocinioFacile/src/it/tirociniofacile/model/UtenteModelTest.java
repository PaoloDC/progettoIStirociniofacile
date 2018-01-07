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
  
/*
  public void testSalvaUtentiNelFile() {
    ArrayList<UtenteBean> listaUtenti = new ArrayList<>();
    listaUtenti.add(new UtenteBean("utente1@unisa.it","utente1"));
    listaUtenti.add(new UtenteBean("utente2@unisa.it","utente2"));
    listaUtenti.add(new UtenteBean("utente3@unisa.it","utente3"));
    listaUtenti.add(new UtenteBean("utente4@unisa.it","utente4"));
    
    model.salvaUtentiNelFile(listaUtenti);
    

    UtenteBean ub = new UtenteBean("utente5@unisa.it","utente5");
    
    
    ArrayList<UtenteBean> lista = model.caricaUtentiDaFile();
    assertEquals(lista.contains(ub), false);
    
    lista.add(ub);
    model.salvaUtentiNelFile(lista);
    
    lista = model.caricaUtentiDaFile();
    assertEquals(lista.contains(ub), true);
    
  }
  
 
  

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
  
  public void testGeneraCredenziali() {
    UtenteBean utenteRandom = new UtenteBean("gino@gmail.com","gino");
    boolean generato1 = model.generaCredenziali(utenteRandom.getEmail());
    assertEquals(generato1, false);
    
    UtenteBean utenteAmministrativo = new UtenteBean("presidente@unisa.it","presidente");
    boolean generato2 = model.generaCredenziali(utenteAmministrativo.getEmail());
    assertEquals(generato2, true);
  }
 

  public void testCaricaUtentiDaFile() {
    UtenteBean ub = new UtenteBean("utente4@unisa.it","utente4");
    
    ArrayList<UtenteBean> lista = model.caricaUtentiDaFile();
    assertEquals(lista.contains(ub), false);
    
    lista.add(ub);
    model.salvaUtentiNelFile(lista);

    ArrayList<UtenteBean> nuovaLista = model.caricaUtentiDaFile();
    assertEquals(nuovaLista.contains(ub), true);
  }
  

  

  public void testCaricaAccount() {
    ProfiloStudenteBean psb = new ProfiloStudenteBean("studente_prova@studenti.unisa.it",
        "prova","0512101010");
    ProfiloAziendaBean pab  = new ProfiloAziendaBean("azienda_prova@gmail.com",
        "prova","Azienda Prova");
    UtenteBean ub = new UtenteBean("utente1@unisa.it","utente1");
    
    try {
      //salva un account azienda e un account studente nel db
      model.salvaAccountAzienda(pab.getEmail(), pab.getPassword(), pab.getNomeAzienda());
      model.salvaAccountStudente(psb.getEmail(), psb.getPassword(), psb.getMatricola());

      //legge lo studente appena salvato
      UtenteBean utente1 = model.caricaAccount(psb.getEmail(), psb.getPassword());
      assertNotNull(utente1);
      ProfiloStudenteBean studente = (ProfiloStudenteBean) utente1;
      assertNotNull(studente);
      assertEquals(studente.getEmail(),psb.getEmail());
      assertEquals(studente.getPassword(),psb.getPassword());
      assertEquals(studente.getMatricola(),psb.getMatricola());
      
      //legge l'azienda appena salvata
      UtenteBean utente2 = model.caricaAccount(pab.getEmail(), pab.getPassword());
      assertNotNull(utente2);
      ProfiloAziendaBean azienda = (ProfiloAziendaBean) utente2;
      assertNotNull(azienda);
      assertEquals(azienda.getEmail(),pab.getEmail());
      assertEquals(azienda.getPassword(),pab.getPassword());
      assertEquals(azienda.getNomeAzienda(),pab.getNomeAzienda());
      
      //legge un utente amministrativo
      UtenteBean utente3 = model.caricaAccount(ub.getEmail(), ub.getPassword());
      assertNotNull(utente3);
      assertEquals(utente3.getEmail(),ub.getEmail());
      assertEquals(utente3.getPassword(),ub.getPassword());
      
    } catch (SQLException e) {
      e.printStackTrace();
    }
    
    
  }
  

  public void testCercaAccountPerEmail() {
    
    ProfiloStudenteBean psb = new ProfiloStudenteBean("decri.paolo@gmail.com",
        "decri.paolo","0512101010");
    try {
      
      model.salvaAccountStudente(psb.getEmail(), psb.getPassword(), psb.getPassword());
      
      model.cercaAccountPerEmail(psb.getEmail());
      
      //TODO è arrivata la mail?
      
    } catch (SQLException e) {
      e.printStackTrace();
    }
    
    
  }
  */
  
}
