package it.tirociniofacile.model;

import java.sql.SQLException;
import java.util.ArrayList;

import it.tirociniofacile.bean.PaginaAziendaBean;
import junit.framework.TestCase;

public class PaginaAziendaModelTest_jdbc extends TestCase {
  private static PaginaAziendaModel_jdbc model;
  
  static {
    model = new PaginaAziendaModel_jdbc();
  }

  /**
   * 
   */
  public void testRicerca() {
    try {
      ArrayList<String> ambitiAzienda1 = new   ArrayList<String>();
      ambitiAzienda1.add("Realtà Aumentata");
      ambitiAzienda1.add("Realtà Virtuale");
      ambitiAzienda1.add("Sviluppo videogiochi");
      ArrayList<String> skillAzienda1 = new  ArrayList<String>();
      skillAzienda1.add("Conoscenza del FrameWork AngularJS");
      skillAzienda1.add("Conoscenza di Java");
      skillAzienda1.add("Conoscenza dei linguaggi HTML, CSS, JS");
         
      PaginaAziendaBean pabSpinVector = new PaginaAziendaBean("Benevento",
          "Affermata azienda nel campo dei videogiochi","Spin Vector",ambitiAzienda1,skillAzienda1);
      
      ArrayList<String> ambitiAzienda2 = new   ArrayList<String>();
      ambitiAzienda2.add("Realtà Aumentata");
      ambitiAzienda2.add("Internet of Things");
      ambitiAzienda2.add("Automotive");
      ArrayList<String> skillAzienda2 = new  ArrayList<String>();
      skillAzienda1.add("Conoscenza di programmazione parallela");
      skillAzienda1.add("Conoscenza di Java");
      skillAzienda1.add("Conoscenza di iOs e Android");
         
      PaginaAziendaBean pabKineton = new PaginaAziendaBean("Napoli",
          "Azienda che lavora nel campo della realtà aumentata","Kineton",ambitiAzienda2,skillAzienda2);
      ArrayList<PaginaAziendaBean> listaCorretta = new ArrayList<PaginaAziendaBean>();
      listaCorretta.add(pabSpinVector);
      listaCorretta.add(pabKineton);
      
      model.aggiungiPagina("Benevento",
          "Affermata azienda nel campo dei videogiochi",
          "spinvector@info.com",ambitiAzienda1,skillAzienda1);
      
      model.aggiungiPagina("Napoli",
          "Azienda che lavora nel campo della realtà aumentata",
          "kineton@info.com",ambitiAzienda2,skillAzienda2);
       
      ArrayList<PaginaAziendaBean>  listaResult = new ArrayList<PaginaAziendaBean>();
      listaResult = model.ricerca();
      assertNotNull(listaResult);
      
      assertEquals(listaCorretta.get(0).getLocalita(), listaResult.get(0).getLocalita());
      assertEquals(listaCorretta.get(0).getDescrizione(), listaResult.get(0).getDescrizione());
      assertEquals(listaCorretta.get(0).getNomeAzienda(), listaResult.get(0).getNomeAzienda());
      assertEquals(listaCorretta.get(0).getAmbito(), listaResult.get(0).getAmbito());
      assertEquals(listaCorretta.get(0).getSkill(), listaResult.get(0).getSkill());
      
      assertEquals(listaCorretta.get(1).getLocalita(), listaResult.get(1).getLocalita());
      assertEquals(listaCorretta.get(1).getDescrizione(), listaResult.get(1).getDescrizione());
      assertEquals(listaCorretta.get(1).getNomeAzienda(), listaResult.get(1).getNomeAzienda());
      assertEquals(listaCorretta.get(1).getAmbito(), listaResult.get(1).getAmbito());
      assertEquals(listaCorretta.get(1).getSkill(), listaResult.get(1).getSkill());
      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  /**
   * 
   */
  public void testRicercaPerId() {
    try {
      ArrayList<String> ambiti = new   ArrayList<String>();
      ambiti.add("Sviluppo applicazioni web");
      ambiti.add("Reti");
      ambiti.add("Web designer");
      ArrayList<String> skill = new  ArrayList<String>();
      skill.add("Conoscenza di logica client server e java EE 7");
      skill.add("Conoscenza di Java  (base)");
      skill.add("Conoscenza dei linguaggi HTML, CSS, JSP");
      
      PaginaAziendaBean pabCorretta = new PaginaAziendaBean("Milano",
          "Affermata azienda nel campo  dello sviluppo web","AK INFORMATICA",ambiti,skill);
      
      model.aggiungiPagina("Milano","Affermata azienda nel campo  dello sviluppo web",
          "akinformatica@info.com", ambiti, skill);
      
      PaginaAziendaBean pabResult = model.ricerca("3");
      
      assertNotNull(pabResult);
      assertEquals(pabCorretta.getDescrizione(), pabResult.getDescrizione());
      assertEquals(pabCorretta.getLocalita(), pabResult.getLocalita());
      assertEquals(pabCorretta.getNomeAzienda(), pabResult.getNomeAzienda());
      assertEquals(pabCorretta.getAmbito(), pabResult.getAmbito());
      assertEquals(pabCorretta.getSkill(), pabResult.getSkill());
      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  
  public void testRicercaParamtrica() throws SQLException { 
    try {
   

      ArrayList<String> ambiti = new   ArrayList<String>();
      ambiti.add("Sviluppo applicazioni web");
      ambiti.add("Reti");
      ambiti.add("Web designer");
      ArrayList<String> skill = new  ArrayList<String>();
      skill.add("Conoscenza di logica client server e java EE 7");
      skill.add("Conoscenza di Java  (base)");
      skill.add("Conoscenza dei linguaggi HTML, CSS, JSP");
       
      PaginaAziendaBean pab = new PaginaAziendaBean("Milano",
          "Affermata azienda nel campo  dello sviluppo web","AK INFORMATICA",ambiti,skill);
      ArrayList<PaginaAziendaBean> listaCorretta = new ArrayList<PaginaAziendaBean>();
      listaCorretta.add(pab);
     
      model.aggiungiPagina("Milano","Affermata azienda nel campo  dello sviluppo web",
          "akinformatica@info.com", ambiti, skill);
      
      ArrayList<PaginaAziendaBean>  listaResult = new ArrayList<PaginaAziendaBean>();
      //Categoria = nome chiave = AK
      listaResult = model.ricerca("Nome", "AK");
      assertNotNull(listaResult);
      assertEquals(listaCorretta.get(0).getLocalita(), listaResult.get(0).getLocalita());
      assertEquals(listaCorretta.get(0).getDescrizione(), listaResult.get(0).getDescrizione());
      assertEquals(listaCorretta.get(0).getNomeAzienda(), listaResult.get(0).getNomeAzienda());
      assertEquals(listaCorretta.get(0).getAmbito(), listaResult.get(0).getAmbito());
      assertEquals(listaCorretta.get(0).getSkill(), listaResult.get(0).getSkill());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}