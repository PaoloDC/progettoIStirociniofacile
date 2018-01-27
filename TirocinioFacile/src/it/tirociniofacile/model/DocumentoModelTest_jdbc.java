package it.tirociniofacile.model;

import static org.junit.Assert.assertNotEquals;

import it.tirociniofacile.bean.DocumentoConvenzioneBean;
import it.tirociniofacile.bean.DocumentoQuestionarioBean;
import it.tirociniofacile.bean.PaginaAziendaBean;
import it.tirociniofacile.bean.ProfiloAziendaBean;
import it.tirociniofacile.bean.ProfiloStudenteBean;
import java.sql.SQLException;
import java.util.ArrayList;

import junit.framework.TestCase;

public class DocumentoModelTest_jdbc extends TestCase {

  private static DocumentoModel_jdbc model;
  private static PaginaAziendaModel_jdbc modelPaginaAzienda;
  private static UtenteModel_jdbc modelUtente;


  static {
    model = new DocumentoModel_jdbc();
    modelPaginaAzienda = new PaginaAziendaModel_jdbc();
    modelUtente = new UtenteModel_jdbc();
  }
  /**
   * testa il metodo getTuttiDocumentiConvenzioneAzienda.
   */
  
  public void testGetTuttiDocumentiConvenzioneAzienda() {

    try {

      ArrayList<DocumentoConvenzioneBean> listaDocumentiIniziale = model
          .getTuttiDocumentiConvenzioneAzienda();

      assertNotNull(listaDocumentiIniziale);

      String piva = "piva_prova";
      boolean inserito = model.salvaConvenzione(piva, "", "", "", "", "", "", "");
      assertTrue(inserito);

      model.salvaPdfConvenzione("url_prova", piva);

      ArrayList<DocumentoConvenzioneBean> listaDocumentiNuova = model
          .getTuttiDocumentiConvenzioneAzienda();

      assertEquals(listaDocumentiIniziale.size() + 1, listaDocumentiNuova.size());

      model.cancellaDocumento(piva);

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  /**
   * testa il metodo getTuttiDocumentiQuestionari.
   */
  
  public void testGetTuttiDocumentiQuestionari() {
    try {

      ArrayList<DocumentoQuestionarioBean> listaDocumentiIniziale = model
          .getTuttiDocumentiQuestionari();
      assertNotNull(listaDocumentiIniziale);

      ProfiloStudenteBean psb = new ProfiloStudenteBean();
      psb.setEmail("studente_prova@studenti.unisa.it");
      psb.setPassword("prova");
      psb.setMatricola("0512101010");

      modelUtente.salvaAccountStudente(psb.getEmail(), psb.getPassword(), psb.getMatricola());

      ProfiloAziendaBean azienda = new ProfiloAziendaBean();
      azienda.setEmail("azienda_prova@gmail.com");
      azienda.setNomeAzienda("nome prova");
      azienda.setPassword("prova");

      modelUtente.salvaAccountAzienda(azienda.getEmail(), azienda.getPassword(),
          azienda.getNomeAzienda());

      PaginaAziendaBean pab = new PaginaAziendaBean();
      pab.setDescrizione("descr_prova");
      pab.setLocalita("loc_prova");
      pab.setNomeAzienda("nome_prova");
      pab.setAmbito(new ArrayList<>());
      pab.setSkill(new ArrayList<>());

      int id = modelPaginaAzienda.aggiungiPagina(pab.getLocalita(), pab.getDescrizione(),
          azienda.getEmail(), pab.getAmbito(), pab.getSkill());
      pab.setId(id);

      int idQuest = model.salvaQuestionario("test", "test", "test", psb.getEmail(), pab.getId(),
          "test", 2, 5, 4, "test");

      model.salvaPdfQuestionario("url_prova", idQuest);

      ArrayList<DocumentoQuestionarioBean> listaDocumentiNuova = model
          .getTuttiDocumentiQuestionari();

      assertEquals(listaDocumentiIniziale.size() + 1, listaDocumentiNuova.size());

      model.cancellaDocumento("" + idQuest);
      modelPaginaAzienda.eliminaPagina(pab.getId());
      modelUtente.eliminaProfiloStudente(psb);
      modelUtente.cancellaAccountAzienda(azienda.getEmail());

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  /**
   * testa il metodo getTuttiDocumentiQuestionariPerPagina.
   */
  
  public void testGetTuttiDocumentiQuestionariPerPagina() {
    try {
      ArrayList<DocumentoQuestionarioBean> listaDocumenti = model
          .getTuttiDocumentiQuestionariPerPagina(1);

      assertNotNull(listaDocumenti);

      int idPaginaFittizio = 78956;
      listaDocumenti = model.getTuttiDocumentiQuestionariPerPagina(idPaginaFittizio);
      assertEquals(0, listaDocumenti.size());

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * testa il metodo ricercaQuestionariNonApprovatiPerStudente. 
   */
  public void testricercaQuestionariNonApprovatiPerStudente() {
    try {
      ArrayList<String> lista = new ArrayList<String>();
      lista = model.ricercaQuestionariNonApprovatiPerStudente("paolo@studenti.unisa.it");
      assertNotNull(lista);

      lista = model.ricercaQuestionariNonApprovatiPerStudente("prereaolo@studenti.unisa.it");
      // assertNull(lista); assertEquals(0, lista.size());

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  /**
   * Testa il metodo conteggioQuestionariApprovatiPerAnno.
   */
  
  public void testConteggioQuestionariApprovatiPerAnno() {
    // anno che esiste
    String annoEs = "2017";
    // anno che non esiste
    String annoNo = "1111";

    try {
      int numero = model.conteggioQuestionariApprovatiPerAnno(annoEs);

      assertNotNull(numero);

      numero = model.conteggioQuestionariApprovatiPerAnno(annoNo);

      assertEquals(numero, 0);

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  /**
   * testa il metodo conteggioQuestionariApprovatiPerAzienda.
   */
  
  public void testConteggioQuestionariApprovatiPerAzienda() {
    // azienda che esiste
    ProfiloAziendaBean pab = new ProfiloAziendaBean();
    pab.setEmail("mail_prova");
    pab.setPassword("pass");
    pab.setNomeAzienda("nome prova");

    modelUtente.salvaAccountAzienda(pab.getEmail(), pab.getPassword(), pab.getNomeAzienda());

    try {
      int cont = model.conteggioQuestionariApprovatiPerAzienda(pab.getNomeAzienda());
      assertEquals(0, cont);

    } catch (SQLException e) {
      e.printStackTrace();
    }

    modelUtente.cancellaAccountAzienda(pab.getEmail());
  }

  /**
   * testa il metodo salvaQuestionario.
   */
  
  public void testSalvaQuestionario() {
    try {

      ArrayList<DocumentoQuestionarioBean> listaDocumentiIniziale = model
          .getTuttiDocumentiQuestionari();
      assertNotNull(listaDocumentiIniziale);

      ProfiloStudenteBean psb = new ProfiloStudenteBean();
      psb.setEmail("studente_prova@studenti.unisa.it");
      psb.setPassword("prova");
      psb.setMatricola("0512101010");

      modelUtente.salvaAccountStudente(psb.getEmail(), psb.getPassword(), psb.getMatricola());

      ProfiloAziendaBean azienda = new ProfiloAziendaBean();
      azienda.setEmail("azienda_prova@gmail.com");
      azienda.setNomeAzienda("nome prova");
      azienda.setPassword("prova");

      modelUtente.salvaAccountAzienda(azienda.getEmail(), azienda.getPassword(),
          azienda.getNomeAzienda());

      PaginaAziendaBean pab = new PaginaAziendaBean();
      pab.setDescrizione("descr_prova");
      pab.setLocalita("loc_prova");
      pab.setNomeAzienda("nome_prova");
      pab.setAmbito(new ArrayList<>());
      pab.setSkill(new ArrayList<>());

      int id = modelPaginaAzienda.aggiungiPagina(pab.getLocalita(), pab.getDescrizione(),
          azienda.getEmail(), pab.getAmbito(), pab.getSkill());
      pab.setId(id);

      int idQuest = model.salvaQuestionario("test", "test", "test", psb.getEmail(), pab.getId(),
          "test", 2, 5, 4, "test");

      assertNotNull(idQuest);
      assertNotEquals(-1, idQuest);

      model.cancellaDocumento("" + idQuest);
      modelPaginaAzienda.eliminaPagina(pab.getId());
      modelUtente.eliminaProfiloStudente(psb);
      modelUtente.cancellaAccountAzienda(azienda.getEmail());

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * testa il metodo salvaConvenzione.
   */
  
  public void testSalvaConvenzione() {
    try {

      String piva = "piva_prova";
      boolean salvato = model.salvaConvenzione(piva, "", "", "", "", "", "", "");
      assertTrue(salvato);

      salvato = model.salvaConvenzione(piva, "", "", "", "", "", "", "");
      assertFalse(salvato);

      model.cancellaDocumento(piva);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  /**
   * testa il metodo salvaPdfConvenzione.
   */
  
  public void testSalvaPdfConvenzione() {
    String piva = "piva_prova";
    boolean inserito = model.salvaConvenzione(piva, "", "", "", "", "", "", "");
    assertTrue(inserito);

    DocumentoConvenzioneBean doc = model.ricercaConvenzionePerPartitaIva(piva);
    assertNull(doc);

    model.salvaPdfConvenzione("url_prova", piva);
    doc = model.ricercaConvenzionePerPartitaIva(piva);
    assertNotNull(doc);

    model.cancellaDocumento(piva);
  }
  /**
   * testa il metodo salvaPdfQuestionario.
   */
  
  public void testSalvaPdfQuestionario() {
    try {

      ArrayList<DocumentoQuestionarioBean> listaDocumentiIniziale = model
          .getTuttiDocumentiQuestionari();
      assertNotNull(listaDocumentiIniziale);

      ProfiloStudenteBean psb = new ProfiloStudenteBean();
      psb.setEmail("studente_prova@studenti.unisa.it");
      psb.setPassword("prova");
      psb.setMatricola("0512101010");

      modelUtente.salvaAccountStudente(psb.getEmail(), psb.getPassword(), psb.getMatricola());

      ProfiloAziendaBean azienda = new ProfiloAziendaBean();
      azienda.setEmail("azienda_prova@gmail.com");
      azienda.setNomeAzienda("nome prova");
      azienda.setPassword("prova");

      modelUtente.salvaAccountAzienda(azienda.getEmail(), azienda.getPassword(),
          azienda.getNomeAzienda());

      PaginaAziendaBean pab = new PaginaAziendaBean();
      pab.setDescrizione("descr_prova");
      pab.setLocalita("loc_prova");
      pab.setNomeAzienda("nome_prova");
      pab.setAmbito(new ArrayList<>());
      pab.setSkill(new ArrayList<>());

      int id = modelPaginaAzienda.aggiungiPagina(pab.getLocalita(), pab.getDescrizione(),
          azienda.getEmail(), pab.getAmbito(), pab.getSkill());
      pab.setId(id);

      int idQuest = model.salvaQuestionario("test", "test", "test", psb.getEmail(), pab.getId(),
          "test", 2, 5, 4, "test");

      DocumentoQuestionarioBean doc = model.ricercaQuestionarioPerId(idQuest);
      assertNotNull(doc);
      assertNull(doc.getUrl());

      model.salvaPdfQuestionario("url_prova", idQuest);

      DocumentoQuestionarioBean doc2 = model.ricercaQuestionarioPerId(idQuest);
      assertNotNull(doc2.getUrl());

      model.cancellaDocumento("" + idQuest);
      modelPaginaAzienda.eliminaPagina(pab.getId());
      modelUtente.eliminaProfiloStudente(psb);
      modelUtente.cancellaAccountAzienda(azienda.getEmail());

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  /**
   * testa il metodo ricercaConvenzionePerEmail.
   */
  
  public void testRicercaConvenzionePerEmail() {
    try {

      ProfiloAziendaBean azienda = new ProfiloAziendaBean();
      azienda.setEmail("azienda_prova@gmail.com");
      azienda.setNomeAzienda("nome prova");
      azienda.setPassword("prova");

      modelUtente.salvaAccountAzienda(azienda.getEmail(), azienda.getPassword(),
          azienda.getNomeAzienda());

      String piva = "piva_prova";
      boolean inserito = model.salvaConvenzione(piva, azienda.getNomeAzienda(), "", "", "", "", "",
          "");
      assertTrue(inserito);

      DocumentoConvenzioneBean doc = model.ricercaConvenzionePerEmail(azienda.getEmail());
      assertNotNull(doc);
      assertEquals(doc.getNomeAzienda(), azienda.getNomeAzienda());

      model.cancellaDocumento(piva);
      modelUtente.cancellaAccountAzienda(azienda.getEmail());

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  /**
   * testa il metodo ricercaConvenzionePerPartitaIva.
   */
  
  public void testRicercaConvenzionePerPartitaIva() {
    try {
      String piva = "piva_prova";

      DocumentoConvenzioneBean doc = model.ricercaConvenzionePerPartitaIva(piva);
      assertNull(doc);

      model.salvaConvenzione(piva, "", "", "", "", "", "", "");
      model.salvaPdfConvenzione("url_prova", piva);
      DocumentoConvenzioneBean doc2 = model.ricercaConvenzionePerPartitaIva(piva);

      assertNotNull(doc2);
      assertEquals(piva, doc2.getPartitaIva());

      model.cancellaDocumento(piva);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  /**
   * testa il metodo ricercaQuestionarioPerId.
   */
  
  public void testRicercaQuestionarioPerId() {
    try {

      ArrayList<DocumentoQuestionarioBean> listaDocumentiIniziale = model
          .getTuttiDocumentiQuestionari();
      assertNotNull(listaDocumentiIniziale);

      ProfiloStudenteBean psb = new ProfiloStudenteBean();
      psb.setEmail("studente_prova@studenti.unisa.it");
      psb.setPassword("prova");
      psb.setMatricola("0512101010");

      modelUtente.salvaAccountStudente(psb.getEmail(), psb.getPassword(), psb.getMatricola());

      ProfiloAziendaBean azienda = new ProfiloAziendaBean();
      azienda.setEmail("azienda_prova@gmail.com");
      azienda.setNomeAzienda("nome prova");
      azienda.setPassword("prova");

      modelUtente.salvaAccountAzienda(azienda.getEmail(), azienda.getPassword(),
          azienda.getNomeAzienda());

      PaginaAziendaBean pab = new PaginaAziendaBean();
      pab.setDescrizione("descr_prova");
      pab.setLocalita("loc_prova");
      pab.setNomeAzienda("nome_prova");
      pab.setAmbito(new ArrayList<>());
      pab.setSkill(new ArrayList<>());

      int id = modelPaginaAzienda.aggiungiPagina(pab.getLocalita(), pab.getDescrizione(),
          azienda.getEmail(), pab.getAmbito(), pab.getSkill());
      pab.setId(id);

      int idQuest = model.salvaQuestionario("test", "test", "test", psb.getEmail(), pab.getId(),
          "test", 2, 5, 4, "test");

      DocumentoQuestionarioBean doc = model.ricercaQuestionarioPerId(idQuest);
      assertNotNull(doc);
      assertEquals(idQuest, doc.getId());

      model.cancellaDocumento("" + idQuest);
      modelPaginaAzienda.eliminaPagina(pab.getId());
      modelUtente.cancellaAccountAzienda(azienda.getEmail());
      modelUtente.eliminaProfiloStudente(psb);

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  /**
   * testa il metodo cancellaDocumento.
   */
  
  public void testCancellaDocumento() {

    ProfiloStudenteBean psb = new ProfiloStudenteBean();
    psb.setEmail("studente_prova@studenti.unisa.it");
    psb.setPassword("prova");
    psb.setMatricola("0512101010");

    modelUtente.salvaAccountStudente(psb.getEmail(), psb.getPassword(), psb.getMatricola());

    ProfiloAziendaBean azienda = new ProfiloAziendaBean();
    azienda.setEmail("azienda_prova@gmail.com");
    azienda.setNomeAzienda("nome prova");
    azienda.setPassword("prova");

    modelUtente.salvaAccountAzienda(azienda.getEmail(), azienda.getPassword(),
        azienda.getNomeAzienda());

    PaginaAziendaBean pab = new PaginaAziendaBean();
    pab.setDescrizione("descr_prova");
    pab.setLocalita("loc_prova");
    pab.setNomeAzienda("nome_prova");
    pab.setAmbito(new ArrayList<>());
    pab.setSkill(new ArrayList<>());

    int id = modelPaginaAzienda.aggiungiPagina(pab.getLocalita(), pab.getDescrizione(),
        azienda.getEmail(), pab.getAmbito(), pab.getSkill());
    pab.setId(id);

    int idQuest = model.salvaQuestionario("test", "test", "test", psb.getEmail(), pab.getId(),
        "test", 2, 5, 4, "test");

    DocumentoQuestionarioBean doc = model.ricercaQuestionarioPerId(idQuest);
    assertNotNull(doc);

    model.cancellaDocumento("" + idQuest);

    DocumentoQuestionarioBean doc2 = model.ricercaQuestionarioPerId(idQuest);
    assertNull(doc2);

    String piva = "piva_prova";
    boolean inserito = model.salvaConvenzione(piva, "", "", "", "", "", "", "");
    assertTrue(inserito);

    model.salvaPdfConvenzione("url_prova", piva);

    DocumentoConvenzioneBean conv = model.ricercaConvenzionePerPartitaIva(piva);
    assertNotNull(conv);

    model.cancellaDocumento(piva);

    DocumentoConvenzioneBean conv2 = model.ricercaConvenzionePerPartitaIva(piva);
    assertNull(conv2);

    modelPaginaAzienda.eliminaPagina(pab.getId());
    modelUtente.cancellaAccountAzienda(azienda.getEmail());
    modelUtente.eliminaProfiloStudente(psb);
  }

  
  /**
   * testa il metodo approvaDocumento.
   */
  
  public void testApprovaDocumento() {
    ProfiloStudenteBean psb = new ProfiloStudenteBean();
    psb.setEmail("studente_prova@studenti.unisa.it");
    psb.setPassword("prova");
    psb.setMatricola("0512101010");

    modelUtente.salvaAccountStudente(psb.getEmail(), psb.getPassword(), psb.getMatricola());

    ProfiloAziendaBean azienda = new ProfiloAziendaBean();
    azienda.setEmail("azienda_prova@gmail.com");
    azienda.setNomeAzienda("nome prova");
    azienda.setPassword("prova");

    modelUtente.salvaAccountAzienda(azienda.getEmail(), azienda.getPassword(),
        azienda.getNomeAzienda());

    PaginaAziendaBean pab = new PaginaAziendaBean();
    pab.setDescrizione("descr_prova");
    pab.setLocalita("loc_prova");
    pab.setNomeAzienda("nome_prova");
    pab.setAmbito(new ArrayList<>());
    pab.setSkill(new ArrayList<>());

    int id = modelPaginaAzienda.aggiungiPagina(pab.getLocalita(), pab.getDescrizione(),
        azienda.getEmail(), pab.getAmbito(), pab.getSkill());
    pab.setId(id);

    int idQuest = model.salvaQuestionario("test", "test", "test", psb.getEmail(), pab.getId(),
        "test", 2, 5, 4, "test");

    DocumentoQuestionarioBean doc = model.ricercaQuestionarioPerId(idQuest);
    assertNotNull(doc);
    assertFalse(doc.isApprovato());

    model.approvaDocumento("" + idQuest);
    
    DocumentoQuestionarioBean doc2 = model.ricercaQuestionarioPerId(idQuest);
    assertNotNull(doc2);
    assertTrue(doc2.isApprovato());
    
    String piva = "piva_prova";
    boolean inserito = model.salvaConvenzione(piva, "", "", "", "", "", "", "");
    assertTrue(inserito);

    model.salvaPdfConvenzione("url_prova", piva);

    DocumentoConvenzioneBean conv = model.ricercaConvenzionePerPartitaIva(piva);
    assertFalse(conv.isApprovato());
    
    model.approvaDocumento(piva);
    DocumentoConvenzioneBean conv2 = model.ricercaConvenzionePerPartitaIva(piva);
    assertTrue(conv2.isApprovato());

    model.cancellaDocumento("" + idQuest);
    model.cancellaDocumento(piva);
    modelPaginaAzienda.eliminaPagina(pab.getId());
    modelUtente.cancellaAccountAzienda(azienda.getEmail());
    modelUtente.eliminaProfiloStudente(psb);
  }
}