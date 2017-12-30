package it.tirociniofacile.model;

import it.tirociniofacile.bean.DocumentoConvenzioneBean;
import it.tirociniofacile.bean.DocumentoQuestionarioBean;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Classe model per la gestione di lettura e scrittura 
 * dei documenti (convenzione e questionario) sul database.
 * @author Paolo De Cristofaro
 */
public class DocumentoModel {
  private static DataSource ds;

  static {
    try {
      Context initCtx = new InitialContext();
      Context envCtx = (Context) initCtx.lookup("java:comp/env");

      ds = (DataSource) envCtx.lookup("jdbc/tirociniofacile");
    } catch (NamingException e) {
      System.out.println("Naming Exception:" + e.getMessage());
    }
  }
  
  //variabili di istanza
  private static final String TABLE_NAME_CONVENZIONI = "DomandaConvenzioneAzienda";
  private static final String TABLE_NAME_QUESTIONARI = "QuestionarioValutazioneAzienda";
  
  /**
   * Conta il numero di questionari approvati in un anno indicato.
   * @param anno l'anno in cui contare
   * @return un intero corrispondente al numero di questionari approvati
   */
  public synchronized int conteggioQuestionariApprovatiPerAnno(String anno) throws SQLException {
    
    return 0;
  }
  
  /**
   * Conta il numero di questionari approvati in una certa azienda.
   * @param azienda l'azienda per cui cercare
   * @return un intero corrispondente al numero di questionari approvati
   */
  public synchronized int conteggioQuestionariApprovatiPerAzienda(String azienda) 
      throws SQLException {
    
    return 0;
  }
  
  /**
   * Salva il questionario all'interno del database.
   * @param informazioniSulTirocinio informazioni generiche sul tirocinio
   * @param commenti commenti dello studente sull'esperienza
   * @param suggerimenti suggerimenti dello studente sull'esperienza
   * @param annoAccademico l'anno in cui è stato svolto il tirocinio
   * @param giudizioEsperienza grado di giudizio sull'esperienza
   * @param giudizioAzienda grado di giudizio sull'azienda
   * @param giudizioUniversita grado di giudizio sull'università
   * @param matricola identificativo dello studente
   */
  public synchronized void salvaQuestionario(String informazioniSulTirocinio, 
      String commenti, String suggerimenti, String annoAccademico, String giudizioEsperienza,
      String giudizioAzienda, String giudizioUniversita, String matricola) {
    
  }
  
  /**
   * Salva il documento di convenzione all'interno del database.
   * @param nomeAzienda nome univoco dell'azienda che si convenziona
   * @param sedeLegale nazione in cui ha sede l'azienda
   * @param citta città in cui ha sede l'azienda
   * @param rappLegale nome del rappresentate legale dell'azienda
   * @param luogoDiNascitaRappLegale luogo di nascita del rappresentate legale dell'azienda
   * @param dataDiNascitaRappLegale data di nascita del rappresentate legale dell'azienda
   */
  public synchronized void salvaConvenzione(String nomeAzienda, String sedeLegale,
      String citta, String rappLegale, String luogoDiNascitaRappLegale,
      String dataDiNascitaRappLegale) {
     
  }
  
  /**
   * Salva il documento PDF all'interno del database.
   * @param pdf il documento da salvare
   * @param id id che collega il pdf ad un documento (convenzione o questionario)
   */
  public synchronized void salvaPdf(File pdf,int id) {
    
  }
  
  /**
   * Permette di ricercare un documento convenzione fornendo l'id.
   * @param id identificativo del documento da ricercare 
   * @return un documento convenzione
   */
  public synchronized DocumentoConvenzioneBean ricercaConvenzionePerId(int id) {
    return null;
  }
  
  /**
   * Permette di ricercare un documento questionario fornendo l'id.
   * @param id identificativo del documento da ricercare 
   * @return un documento questionario
   */
  public synchronized DocumentoQuestionarioBean ricercaQuestionarioPerId(int id) {
    return null;
  }
  
  /**
   * Cancella il documento il cui id corrisponde a quello passato.
   * @param id identificativo del documento da ricercare
   */
  public synchronized void cancellaDocumento(int id) {
    
  }
  
  /**
   * Approva il documento il cui id corrisponde a quello passato.
   * @param id identificativo del documento da ricercare
   */
  public synchronized void approvaDocumento(int id) {
    return;
  }
}
