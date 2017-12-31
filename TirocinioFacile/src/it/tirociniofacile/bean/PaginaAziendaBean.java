package it.tirociniofacile.bean;

import java.util.ArrayList;

public class PaginaAziendaBean {
   
        private String localita;
  
  private String descrizione;
  private String nomeAzienda;
  private ArrayList<String> ambito = new ArrayList<String>();
  private ArrayList<String> skill = new ArrayList<String>();
  
  public PaginaAziendaBean() {
    
  }
  
  /**
   * Rappresenta una pagina azienda.
   * @param localita luogo dove è ubicata l'azienda
   * @param descrizione descrizione dell'azienda
   * @param nomeAzienda nome dell'azienda
   * @param ambito ambiti dove l'azienda lavora
   * @param skill skill richieste ai tirocinanti da parte dell'azienda
   */
  public PaginaAziendaBean(String localita, String descrizione, String nomeAzienda, 
      ArrayList<String> ambito, ArrayList<String> skill) {
    this.localita = localita;
    this.descrizione = descrizione;
    this.nomeAzienda = nomeAzienda;
    this.ambito = ambito;
    this.skill = skill;
  }

  public String getLocalita() {
    return localita;
  }
  
  public void setLocalita(String localita) {
    this.localita = localita;
  }
  
  public String getDescrizione() {
    return descrizione;
  }
  
  public void setDescrizione(String descrizione) {
    this.descrizione = descrizione;
  }
  
  public String getNomeAzienda() {
    return nomeAzienda;
  }
  
  public void setNomeAzienda(String nomeAzienda) {
    this.nomeAzienda = nomeAzienda;
  }
  
  public ArrayList<String> getAmbito() {
    return ambito;
  }
  
  public void setAmbito(ArrayList<String> ambito) {
    this.ambito = ambito;
  }
  
  public ArrayList<String> getSkill() {
    return skill;
  }
  
  public void setSkill(ArrayList<String> skill) {
    this.skill = skill;
  }
  
}
