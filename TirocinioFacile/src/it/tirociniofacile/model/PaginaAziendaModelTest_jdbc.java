package it.tirociniofacile.model;

import it.tirociniofacile.bean.PaginaAziendaBean;
import it.tirociniofacile.bean.ProfiloAziendaBean;

import java.util.ArrayList;

import junit.framework.TestCase;

public class PaginaAziendaModelTest_jdbc extends TestCase {
  
  private static PaginaAziendaModel_jdbc model;
  
  static {
    model = new PaginaAziendaModel_jdbc();
  }


  public void testRicerca() {
    try {
      
      //inserisco una pagina di prova, utilizzando uno stub di profiloAziendaBean
      ArrayList<String> ambiti = new   ArrayList<String>();
      ambiti.add("Sviluppo applicazioni web");
      ambiti.add("Reti");
      ambiti.add("Web designer");
      
      ArrayList<String> skill = new  ArrayList<String>();
      skill.add("Conoscenza di logica client server e java EE 7");
      skill.add("Conoscenza di Java  (base)");
      skill.add("Conoscenza dei linguaggi HTML, CSS, JSP");
       
      ProfiloAziendaBean profilo = new ProfiloAziendaBean();
      profilo.setEmail("mail prova");
      profilo.setPassword("password prova");
      profilo.setNomeAzienda("Nome azienda prova");
      
      PaginaAziendaBean pab = new PaginaAziendaBean("Localita prova",
          "descrizione prova","nome prova",ambiti,skill);
      pab.setAmbito(ambiti);
      pab.setSkill(skill);
      pab.setLocalita("Località Prova");
      pab.setDescrizione("Descrizione Prova");
      pab.setNomeAzienda(profilo.getNomeAzienda());
      
      UtenteModel_jdbc um = new UtenteModel_jdbc();
      
      um.salvaAccountAzienda(profilo.getEmail(), profilo.getPassword(),
          profilo.getNomeAzienda());
      
      ArrayList<PaginaAziendaBean> listaIniziale = model.ricerca();
      
      int id = model.aggiungiPagina(pab.getLocalita(),
          pab.getDescrizione(),
          profilo.getEmail(),
          pab.getAmbito(),
          pab.getSkill());
      
      pab.setId(id);
            
      ArrayList<PaginaAziendaBean> nuovaLista = model.ricerca();

      assertEquals(nuovaLista.size(), (listaIniziale.size() + 1));
      assertEquals(nuovaLista.contains(pab), true);
      
      model.eliminaPagina(pab.getId());
      um.cancellaAccountAzienda(profilo.getEmail());
      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public void testRicercaPerId() {
    try {
      //inserisco una pagina di prova, utilizzando uno stub di profiloAziendaBean
      ArrayList<String> ambiti = new   ArrayList<String>();
      ambiti.add("Sviluppo applicazioni web");
      ambiti.add("Reti");
      ambiti.add("Web designer");
      
      ArrayList<String> skill = new  ArrayList<String>();
      skill.add("Conoscenza di logica client server e java EE 7");
      skill.add("Conoscenza di Java  (base)");
      skill.add("Conoscenza dei linguaggi HTML, CSS, JSP");
       
      ProfiloAziendaBean profilo = new ProfiloAziendaBean();
      profilo.setEmail("mail prova");
      profilo.setPassword("password prova");
      profilo.setNomeAzienda("Nome azienda prova");
      
      PaginaAziendaBean pab = new PaginaAziendaBean("Localita prova",
          "descrizione prova","nome prova",ambiti,skill);
      pab.setAmbito(ambiti);
      pab.setSkill(skill);
      pab.setLocalita("Località Prova");
      pab.setDescrizione("Descrizione Prova");
      pab.setNomeAzienda(profilo.getNomeAzienda());
      
      UtenteModel_jdbc um = new UtenteModel_jdbc();
      
      um.salvaAccountAzienda(profilo.getEmail(), 
          profilo.getPassword(),
          profilo.getNomeAzienda());
      
      int id = model.aggiungiPagina(pab.getLocalita(),
          pab.getDescrizione(),
          profilo.getEmail(),
          pab.getAmbito(),
          pab.getSkill());
      
      pab.setId(id);
      
      PaginaAziendaBean paginaCaricata = model.ricerca(pab.getId());
      assertNotNull(paginaCaricata);
      assertEquals(pab, paginaCaricata);
      
      model.eliminaPagina(pab.getId());
      um.cancellaAccountAzienda(profilo.getEmail());
      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public void testRicercaParametrica() { 
    try {
  
      //inserisco una pagina di prova, utilizzando uno stub di profiloAziendaBean
      ArrayList<String> ambiti = new   ArrayList<String>();
      ambiti.add("Sviluppo applicazioni web");
      ambiti.add("Reti");
      ambiti.add("Web designer");
      
      ArrayList<String> skill = new  ArrayList<String>();
      skill.add("Conoscenza di logica client server e java EE 7");
      skill.add("Conoscenza di Java  (base)");
      skill.add("Conoscenza dei linguaggi HTML, CSS, JSP");
       
      ProfiloAziendaBean profilo = new ProfiloAziendaBean();
      profilo.setEmail("mail prova");
      profilo.setPassword("password prova");
      profilo.setNomeAzienda("Nome azienda prova");
      
      PaginaAziendaBean pab = new PaginaAziendaBean("Localita prova",
          "descrizione prova","nome prova",ambiti,skill);
      pab.setAmbito(ambiti);
      pab.setSkill(skill);
      pab.setLocalita("Località Prova");
      pab.setDescrizione("Descrizione Prova");
      pab.setNomeAzienda(profilo.getNomeAzienda());
      
      UtenteModel_jdbc um = new UtenteModel_jdbc();
      
      um.salvaAccountAzienda(profilo.getEmail(), profilo.getPassword(),
          profilo.getNomeAzienda());
      
      int id = model.aggiungiPagina(pab.getLocalita(),
          pab.getDescrizione(),
          profilo.getEmail(),
          pab.getAmbito(),
          pab.getSkill());
      
      pab.setId(id);
      
      //eseguo la ricerca per località = "Località Prova"
      ArrayList<PaginaAziendaBean> lista = model.ricerca("localita", pab.getLocalita());      
      
      assertEquals(lista.isEmpty(), false);
      PaginaAziendaBean pabRicerca = lista.get(0);
      assertEquals(pabRicerca,pab);
      
      lista = model.ricerca("nomeAziendaRappresentata",pab.getNomeAzienda());
      assertEquals(lista.isEmpty(), false);
      pabRicerca = lista.get(0);
      assertEquals(pabRicerca,pab);
      
      model.eliminaPagina(pab.getId());
      um.cancellaAccountAzienda(profilo.getEmail());
      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  /**
   * test metodo aggiungipagina.
   */
  public void testAggiungiPagina() {
    ArrayList<String> ambiti = new   ArrayList<String>();
    ambiti.add("Sviluppo applicazioni web");
    ambiti.add("Reti");
    ambiti.add("Web designer");
    
    ArrayList<String> skill = new  ArrayList<String>();
    skill.add("Conoscenza di logica client server e java EE 7");
    skill.add("Conoscenza di Java  (base)");
    skill.add("Conoscenza dei linguaggi HTML, CSS, JSP");
    
    ProfiloAziendaBean azienda = new ProfiloAziendaBean();
    azienda.setEmail("mail");
    azienda.setNomeAzienda("Ak Informatica");
    azienda.setPassword("mail");
    
    UtenteModel_jdbc u = new UtenteModel_jdbc();
    
    boolean b = u.salvaAccountAzienda(azienda.getEmail(), 
        azienda.getPassword(), azienda.getNomeAzienda());
    
    assertEquals(true, b);
    
    PaginaAziendaBean pab = new PaginaAziendaBean();
    pab.setLocalita("Milano");
    pab.setDescrizione("Affermata azienda nel campo  dello sviluppo web");
    pab.setSkill(skill);
    pab.setAmbito(ambiti);
    pab.setNomeAzienda(azienda.getNomeAzienda());
    
    int x = model.aggiungiPagina(pab.getLocalita(),
        pab.getDescrizione(), azienda.getEmail(), ambiti, skill);
    
    pab.setId(x);
    
    PaginaAziendaBean nuovapagina = model.ricerca(pab.getId());
    assertEquals(nuovapagina, pab);
    
    model.eliminaPagina(x);
    u.cancellaAccountAzienda(azienda.getEmail());
  }
}