package it.tirociniofacile.model;

import it.tirociniofacile.bean.ProfiloAziendaBean;
import it.tirociniofacile.bean.ProfiloStudenteBean;
import it.tirociniofacile.bean.UtenteBean;

import java.sql.SQLException;
import java.util.ArrayList;

import junit.framework.TestCase;

/**
 * Classe che testa i metodi della classe UtenteModel, è stata creata una classe di supporto
 * UtenteModel_jdbc che effettua una connessione diretta al database.
 * 
 * @author Paolo De Cristofaro
 *
 */
public class UtenteModelTest_jdbc extends TestCase {

  private static UtenteModel_jdbc model;

  static {
    model = new UtenteModel_jdbc();
  }

  public void testSalvaAccountStudente() {

    ProfiloStudenteBean studente = new ProfiloStudenteBean("vittorio@studenti.unisa.it", "vittorio",
        "0512103123");

    UtenteBean utente = model.caricaAccount(studente.getEmail(), studente.getPassword());
    assertNull(utente);

    boolean ins = model.salvaAccountStudente(studente.getEmail(), studente.getPassword(),
        studente.getMatricola());

    assertTrue(ins);

    // inserimento duplicato
    boolean ins2 = model.salvaAccountStudente(studente.getEmail(), studente.getPassword(),
        studente.getMatricola());

    assertFalse(ins2);

    UtenteBean utente2 = model.caricaAccount(studente.getEmail(), studente.getPassword());
    assertNotNull(utente2);

    ProfiloStudenteBean s = (ProfiloStudenteBean) utente2;
    assertEquals(studente, s);

    model.eliminaProfiloStudente(s);

  }

  public void testSalvaAccountAzienda() {

    ProfiloAziendaBean azienda = new ProfiloAziendaBean("azienda_test@gmail.com", "azienda_test",
        "azienda_test");

    UtenteBean utente = model.caricaAccount(azienda.getEmail(), azienda.getPassword());
    assertNull(utente);

    boolean ins = model.salvaAccountAzienda(azienda.getEmail(), azienda.getPassword(),
        azienda.getNomeAzienda());

    assertTrue(ins);

    // inserimento duplicato per azienda
    boolean ins2 = model.salvaAccountAzienda(azienda.getEmail(), azienda.getPassword(),
        azienda.getNomeAzienda());

    assertFalse(ins2);

    utente = model.caricaAccount(azienda.getEmail(), azienda.getPassword());
    assertEquals(utente, azienda);
    ProfiloAziendaBean a = (ProfiloAziendaBean) utente;
    assertEquals(a, azienda);

    model.cancellaAccountAzienda(azienda.getEmail());

  }

  public void testSalvaUtentiNelFile() {
    ArrayList<UtenteBean> listaUtenti = new ArrayList<>();

    model.salvaUtentiNelFile(listaUtenti); // svuota il file dal contenuto precedente

    UtenteBean u1 = new UtenteBean("utente1@unisa.it", "utente1");
    UtenteBean u2 = new UtenteBean("utente2@unisa.it", "utente2");
    UtenteBean u3 = new UtenteBean("utente3@unisa.it", "utente3");
    UtenteBean u4 = new UtenteBean("utente4@unisa.it", "utente4");

    listaUtenti.add(u1);
    listaUtenti.add(u2);
    listaUtenti.add(u3);
    listaUtenti.add(u4);

    model.salvaUtentiNelFile(listaUtenti); // salva una lista fittizia nel file

    ArrayList<UtenteBean> newList = model.caricaUtentiDaFile();

    assertEquals(newList, listaUtenti);

    ArrayList<UtenteBean> svuota = new ArrayList<>();
    model.salvaUtentiNelFile(svuota);

  }

  public void testCaricaUtentiDaFile() {
    ArrayList<UtenteBean> lista = null;
    assertNull(lista);

    lista = model.caricaUtentiDaFile();
    assertNotNull(lista);
  }
  
  public void testCercaAccountPerEmail() {

    ProfiloStudenteBean psb = new ProfiloStudenteBean("decri.paolo@gmail.com", "decri.paolo",
        "0512101010");

    boolean res1 = model.cercaAccountPerEmail(psb.getEmail());
    assertFalse(res1);

    model.salvaAccountStudente(psb.getEmail(), psb.getPassword(), psb.getMatricola());

    boolean res2 = model.cercaAccountPerEmail(psb.getEmail());
    assertTrue(res2);

    model.eliminaProfiloStudente(psb);

  }

  public void testCaricaAccount() {

    // legge lo studente appena salvato
    ProfiloStudenteBean psb = new ProfiloStudenteBean("studente_prova@studenti.unisa.it", "prova",
        "0512101010");

    model.salvaAccountStudente(psb.getEmail(), psb.getPassword(), psb.getMatricola());

    UtenteBean utente1 = model.caricaAccount(psb.getEmail(), psb.getPassword());
    assertNotNull(utente1);

    ProfiloStudenteBean studente = (ProfiloStudenteBean) utente1;
    assertEquals(studente.getEmail(), psb.getEmail());
    assertEquals(studente.getPassword(), psb.getPassword());
    assertEquals(studente.getMatricola(), psb.getMatricola());

    model.eliminaProfiloStudente(psb);

    // legge l'azienda appena salvata
    ProfiloAziendaBean pab = new ProfiloAziendaBean("azienda_prova@gmail.com", "prova",
        "Azienda Prova");

    model.salvaAccountAzienda(pab.getEmail(), pab.getPassword(), pab.getNomeAzienda());

    UtenteBean utente2 = model.caricaAccount(pab.getEmail(), pab.getPassword());
    assertNotNull(utente2);

    ProfiloAziendaBean azienda = (ProfiloAziendaBean) utente2;
    assertEquals(azienda.getEmail(), pab.getEmail());
    assertEquals(azienda.getPassword(), pab.getPassword());
    assertEquals(azienda.getNomeAzienda(), pab.getNomeAzienda());

    model.cancellaAccountAzienda(pab.getEmail());

    // legge un utente amministrativo
    ArrayList<UtenteBean> lista = new ArrayList<>();
    UtenteBean ub = new UtenteBean("utente1@unisa.it", "utente1");
    lista.add(ub);

    model.salvaUtentiNelFile(lista);
    lista = model.caricaUtentiDaFile();

    UtenteBean utente3 = model.caricaAccount(ub.getEmail(), ub.getPassword());
    assertNotNull(utente3);
    assertEquals(utente3.getEmail(), ub.getEmail());
    assertEquals(utente3.getPassword(), ub.getPassword());

    // elimina gli account inseriti per il test
    ArrayList<UtenteBean> svuota = new ArrayList<>();
    model.salvaUtentiNelFile(svuota);

  }

  public void testCancellaAccountAzienda() {

    ProfiloAziendaBean pab = new ProfiloAziendaBean("azienda_prova@gmail.com", "prova",
        "Azienda Prova");

    model.cancellaAccountAzienda(pab.getEmail());

    UtenteBean ub = model.caricaAccount(pab.getEmail(), pab.getPassword());
    assertNull(ub);

    model.salvaAccountAzienda(pab.getEmail(), pab.getPassword(), pab.getNomeAzienda());

    UtenteBean ub2 = model.caricaAccount(pab.getEmail(), pab.getPassword());
    assertNotNull(ub2);

    ProfiloAziendaBean a = (ProfiloAziendaBean) ub2;
    assertEquals(pab, a);

    model.cancellaAccountAzienda(pab.getEmail());

    UtenteBean ub3 = model.caricaAccount(pab.getEmail(), pab.getPassword());
    assertNull(ub3);
  }

}
