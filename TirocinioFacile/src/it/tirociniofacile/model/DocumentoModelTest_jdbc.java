package it.tirociniofacile.model;

import java.sql.SQLException;
import java.util.ArrayList;

import it.tirociniofacile.bean.DocumentoConvenzioneBean;
import it.tirociniofacile.bean.DocumentoQuestionarioBean;
import junit.framework.TestCase;

public class DocumentoModelTest_jdbc  extends TestCase{
 
  private static DocumentoModel_jdbc model;
  private static PaginaAziendaModel_jdbc modelAzienda;
 
  private final int MYSQL_DUPLICATE_PK= 1062;
  
  static {
    model = new DocumentoModel_jdbc();
    modelAzienda = new PaginaAziendaModel_jdbc();
  }

  public void testConteggioQuestionariApprovatiPerAnno() {
    //anno che esiste
    String annoEs = "2017";
    //anno che non esiste
    String annoNo = "1111";
    
    try {
      int numero = model.conteggioQuestionariApprovatiPerAnno(annoEs);
      
      assertNotNull(numero);
      
      numero = model.conteggioQuestionariApprovatiPerAnno(annoNo);
      
      assertEquals(numero,0);
      
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  public void testgetTuttiDocumentiConvenzioneAzienda() {
    
    try {
      ArrayList<DocumentoConvenzioneBean> listaDocumenti = new ArrayList<DocumentoConvenzioneBean>(); 
      listaDocumenti= model.getTuttiDocumentiConvenzioneAzienda();
      
      assertNotNull(listaDocumenti);
      
      assertNull(listaDocumenti);
      
    }catch (SQLException e) {
      e.printStackTrace();
    }
  }
  
  public void testgetTuttiDocumentiQuestionari() {
    try {
      model.salvaQuestionario("sugg","comm","2017","paolo@studenti.unisa.it",1,"0512103488",2,5,4,"Testooooo");
      
      ArrayList<DocumentoQuestionarioBean> listaDocumenti = new ArrayList<>();
      listaDocumenti= model.getTuttiDocumentiQuestionari();
      
      System.out.println("EHII= " + listaDocumenti);
      assertNotNull(listaDocumenti);
      
      boolean maggDiZero = listaDocumenti.size()==0;
      assertEquals(maggDiZero,true);
      
    }catch (SQLException e) {
      e.printStackTrace();
    }
  }
  public void testgetTuttiDocumentiQuestionariPerPagina() {
    try {
      ArrayList<DocumentoQuestionarioBean> listaDocumenti = new ArrayList<>();
      listaDocumenti= model.getTuttiDocumentiQuestionariPerPagina(1);
      
      assertNotNull(listaDocumenti);
      
      
      
      listaDocumenti= model.getTuttiDocumentiQuestionariPerPagina(78965);
      assertEquals(0, listaDocumenti.size());
      
    }catch (SQLException e) {
      
    }
  }
  public void testricercaQuestionariNonApprovatiPerStudente() {
    try {
      ArrayList<String> lista = new ArrayList<String>();
      lista= model.ricercaQuestionariNonApprovatiPerStudente("paolo@studenti.unisa.it");
      assertNotNull(lista);
      
      
      
      lista= model.ricercaQuestionariNonApprovatiPerStudente("prereaolo@studenti.unisa.it");
      //assertNull(lista);
      assertEquals(0, lista.size());
      
    }catch (SQLException e) {
      e.printStackTrace();
    }
}
  public void testConteggioQuestionariApprovatiPerAzienda() {
    //azienda che esiste
    String aziendaEs = "SpinVector";
    //azienda che non esiste
    String aziendaNo = "AziendaNonEsistente";
    
    try {
      int numero = model.conteggioQuestionariApprovatiPerAnno(aziendaEs);
      
      assertNotNull(numero);
      
      numero = model.conteggioQuestionariApprovatiPerAnno(aziendaNo);
      
      assertEquals(numero,0);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * Salva documento convenzione sul db.
   */
  public void testSalvaConvenzione() {
    try {
      model.salvaConvenzione("pivaProva", "test", "test", "test", "test","test","test","test testo");
      model.salvaConvenzione("pivaProva", "test", "test", "test", "test","test","test","test testo");
    } catch (SQLException e) {
      if (e.getErrorCode() == MYSQL_DUPLICATE_PK) {
        fail("Partita Iva gia usata");
      }
      e.printStackTrace();
    }
  }
  
  /**
   * Test RicercaConvenzione per id.
   */
  public void testRicercaConvenzionePerId() {
    try {
      model.salvaConvenzione("pivaProva", "test", "test", "test", "test", "test","test","test testo");
      
      ArrayList<String> ambitiAzienda1 = new ArrayList<String>();
      ambitiAzienda1.add("Realtà Aumentata");
      ambitiAzienda1.add("Realtà Virtuale");
      ambitiAzienda1.add("Sviluppo videogiochi");
      
      ArrayList<String> skillAzienda1 = new ArrayList<String>();
      skillAzienda1.add("Conoscenza del FrameWork AngularJS");
      skillAzienda1.add("Conoscenza di Java");
      skillAzienda1.add("Conoscenza dei linguaggi HTML, CSS, JS");
      
      modelAzienda.aggiungiPagina("test", "test", "test", ambitiAzienda1, skillAzienda1);
      
      assertNotNull(model.ricercaConvenzionePerPartitaIva("pivaProva"));
      
      assertNull(model.ricercaConvenzionePerPartitaIva("pivaProvaErrata"));
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**test ricerca questionario per id.
   * 
   */
  public void testRicercaQuestionarioPerId() {
    try {
    //  model.salvaQuestionario("prova", "prova", "prova", "prova", 99, "prova", 1, 1, 1);
      assertNotNull(model.ricercaQuestionarioPerId(0));
      
      //supponendo che ci siano meno di 55 questionari
      assertNull(model.ricercaQuestionarioPerId(55));
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}
