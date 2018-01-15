package it.tirociniofacile.model;

import java.sql.SQLException;
import java.util.ArrayList;

import it.tirociniofacile.bean.DocumentoQuestionarioBean;
import junit.framework.TestCase;

public class DocumentoModelTest_jdbc  extends TestCase{
 
  private static DocumentoModel_jdbc model;
  private static PaginaAziendaModel_jdbc modelAzienda;
 
  private final int MYSQL_DUPLICATE_PK= 1062;
  
  static {
    model = new DocumentoModel_jdbc();
  }
  
  public void testConteggioQuestionariApprovatiPerAnno() {
    //anno che esiste
    String annoEs = "2015";
    //anno che non esiste
    String annoNo = "1111";
    
    try {
      int numero = model.conteggioQuestionariApprovatiPerAnno(annoEs);
      
      assertNotNull(numero);
      
      numero = model.conteggioQuestionariApprovatiPerAnno(annoNo);
      
      assertNull(numero);
    } catch (SQLException e) {
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
      
      assertNull(numero);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  
  public void testSalvaConvenzione() {
    try {
      model.salvaConvenzione("pivaProva", "test", "test", "test", "test","test","test");
      model.salvaConvenzione("pivaProva", "test", "test", "test", "test","test","test");
    } catch (SQLException e) {
      if (e.getErrorCode() == MYSQL_DUPLICATE_PK ) {
        fail("Partita Iva gia usata");
      }
      e.printStackTrace();
    }
  }
  
  public void testRicercaConvenzionePerId() {
    try {
      model.salvaConvenzione("pivaProva", "test", "test", "test", "test", "test","test");
      
      ArrayList<String> ambitiAzienda1 = new   ArrayList<String>();
      ambitiAzienda1.add("Realtà Aumentata");
      ambitiAzienda1.add("Realtà Virtuale");
      ambitiAzienda1.add("Sviluppo videogiochi");
      ArrayList<String> skillAzienda1 = new  ArrayList<String>();
      skillAzienda1.add("Conoscenza del FrameWork AngularJS");
      skillAzienda1.add("Conoscenza di Java");
      skillAzienda1.add("Conoscenza dei linguaggi HTML, CSS, JS");
      
      modelAzienda.aggiungiPagina("test", "test", "test", ambitiAzienda1, skillAzienda1);
      
      assertNotNull(model.ricercaConvenzionePerId("pivaProva"));
      assertNull(model.ricercaConvenzionePerId("ProvaSbagliato"));
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  
  public void testRicercaQuestionarioPerId() {
    try {
      model.salvaQuestionario("test","test","test","test",1,1,1,"test");
      
      assertNotNull(model.ricercaQuestionarioPerId(1));
      
      //supponendo che ci siano meno di 55 questionari
      assertNull(model.ricercaQuestionarioPerId(55));
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
