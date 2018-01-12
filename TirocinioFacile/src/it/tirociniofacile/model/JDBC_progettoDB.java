package it.tirociniofacile.model;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
/**
 * Classe che testa il funzionamento delle 10 operazioni richieste per il progetto 2016/17 del corso di Basi di Dati,
 * sono state aggiunte altre operazioni che eseguono query annidate (da op11 a op15)
 * @author Paolo De Cristofaro, Andrea Sessa, Alessandro De Riso
 */
public class JDBC_progettoDB {
	public static void main(String[] args)  {
		in = new Scanner (System.in);
		String nomeDB,user,pass;
		boolean connection=true;
		do{
			System.out.print ("Prima di iniziare inserire nome del database, username password\nInserire nome del database: ");
			nomeDB = in.nextLine();
			System.out.print("Inserire username: ");
			user = in.nextLine();
			System.out.print("Inserire password: ");
			pass = in.nextLine();
			if(nomeDB.equals("progetto") && user.equals("root") && pass.equals("3468"))
				connection=false;
			else
				System.out.println("Errore Nella Connessione Al Database, Hai inserito Dati sbagliati, Riprova\n");
		}while(connection);
	/*
		//Inizia una connessione
		String nomeDB = "progetto";
		String user = "root";
		String pass = "1111";
	*/	
		getConnection(nomeDB,user,pass);
		
		int i;
		do{
			System.out.flush();
			System.out.println("\nSeleziona l'operazione da effettuare:\n"
			 		+ "0: PER USCIRE\n"
			 		+ "1: Inserire un’attività disponibile all’interno di un parco.\n"
			 		+ "2: Stampare il numero di biglietti venduti da un parco.\n"
			 		+ "3: Stampare l’elenco dei dipendenti di un parco, con relativo tipo di contratto.\n"
			 		+ "4: Stampa tutte le promozioni offerte da un parco.\n"
			 		+ "5: Stampare pacchetti proposti da tutte le agenzie convenzionate da un parco.\n"
			 		+ "6: Aggiungere una nuova struttura ad un parco.\n"
			 		+ "7: Stampare tutti i clienti che hanno acquistato un biglietto.\n"
			 		+ "8: Inserire una nuova agenzia.\n"
			 		+ "9: Cancellare un’attività disponibile all’interno di un parco.\n"
			 		+ "10: Stampare tutti i clienti che hanno prenotato un pacchetto.\t\t\n"
			 		+ "\nOppure Scegli una query offerta dagli sviluppatori \n"
					+ "11: Seleziona i pacchetti che hanno un agenzia in un indirizzo specifico \n"
			 		+ "12: Seleziona i clienti omonimi con stesso nome, stesso cognome ma con codici fiscali diversi\n"
					+ "13: Stampa il nome dell'attività a cui partecipa uno specifico cliente\n"
			 		+ "14: Stampare il codice, la descrizione e il prezzo di tutte le promozioni di un tipo specifico di parchi\n"
					+ "15: Stampare tutti gli hotel convenzionati con un parco specifico\t\t");
			
			String input = in.nextLine();
			i = Integer.parseInt(input);
			switch(i){
				case(0):{
			 		System.out.println("\n\nProgramma Terminato.");
			 		break;
			 	}
			 	case(1):{
			 		getOP1();
			 		break;
			 	}
			 	case(2):{
			 		getOP2();
			 		break;
			 	}
			 	case(3):{
			 		getOP3();
			 		break;
			 	}
			 	case(4):{
			 		getOP4();
			 		break;
			 	}
			 	case(5):{
			 		getOP5();
			 		break;
			 	}
			 	case(6):{
			 		getOP6();
			 		break;
			 	}
			 	case(7):{
			 		getOP7();
			 		break;
			 	}
			 	case(8):{
			 		getOP8();
			 		break;
			 	}
			 	case(9):{
			 		getOP9();
			 		break;
			 	}
			 	case(10):{
			 		getOP10();
			 		break;
			 	}
			 	case(11):{
			 		getOP11();
			 		break;
			 	}
			 	case(12):{
			 		getOP12();
			 		break;
			 	}
			 	case(13):{
			 		getOP13();
			 		break;
			 	}
			 	case(14):{
			 		getOP14();
			 		break;
			 	}
			 	case(15):{
			 		getOP15();
			 		break;
			 	}
			}
		 }while(i != 0);
	}
	//metodi
	/**
	 * Inizializza la connessione con il database, specificando il database a cui si vuole connettere
	 * @ in caso di connessione errata o mancante
	 */
	public static void getConnection(String nomeDB,String user,String pass) {
		try{
			/*jdbs:mysql://indirizzo dell'host/nome del database*/
			String url = "jdbc:mysql://127.0.0.1/" + nomeDB;
		 
			//Nome utente, password per la connessione al database
			con = (Connection) DriverManager.getConnection(url, user, pass);
			stmt = (Statement) con.createStatement();
		}
		catch(Exception e){
			e.printStackTrace();
			System.exit(0);
		}
	}
	/**
	 * Esegue l'operazione 1
	 */
	public static void getOP1(){
		try{
			 /*Cosa chiede l'operazione 1 */
			System.out.println("Aggiungere una nuova attività (Codice,Nome,Prezzo,Parco)");
			System.out.print("Inserisci CODICE (MAX 6 CHAR) =  ");
			String codice = in.nextLine();
			if (codice.length() > 6){
				do{
					System.out.println("Codice Attività Troppo Lungo, Riprova");
					System.out.println("Inserisci CODICE (MAX 6 CHAR) =  ");
					codice = in.nextLine();
				}while(codice.length()>6);
			}
			System.out.print("Inserisci Nome =  ");
			String nome = in.nextLine();
			System.out.print("Inserisci Prezzo =  ");
			String sPrezzo = in.nextLine();
			double prezzo = Double.parseDouble(sPrezzo);	
			System.out.print("Inserisci Parco =  ");
			String parco = in.nextLine();

			// the mysql insert statement (inserimento)
			String query = " insert into attivita (codiceattivita,nome,prezzo,parco)" + " values (?, ?, ?, ?)";
		     
		    // create the mysql insert preparedstatement
		    PreparedStatement preparedStmt = (PreparedStatement) con.prepareStatement(query);
		    preparedStmt.setString (1,codice);/*numero colonna+ dato(Striga)*/
		    preparedStmt.setString (2,nome);
		    preparedStmt.setDouble (3,prezzo);
		    preparedStmt.setString (4,parco);
		     
		    // execute the preparedstatement
		    preparedStmt.execute();
		    
		    String verifica = "select * from attivita where attivita.codiceattivita = '"+codice+"'";
		    System.out.println("Eseguita...\nPer conferma si esegue la query: "+verifica);
		    ResultSet rs = stmt.executeQuery(verifica);
		    
		    while (rs.next()) {
		    	System.out.println("Codice Attività: "+rs.getString(1) + ", Nome: "+rs.getString(2)+ ", Prezzo: "+rs.getString(3)
		    	+ ", Parco: "+rs.getString(4) + "\n");
		    }
		    
		}
		catch(SQLException e ){
			System.out.println("\nErrore nell'inserimento dei dati\n");
			e.printStackTrace();
		}
	}
	/**
	 * Esegue l'operazione 2
	 */
	public static void getOP2(){
		try{
			/*Cosa chiede l'operazione 2 */
			System.out.print("Digita nome del parco =  ");
			String nomeparco = in.nextLine();
			ResultSet rs = stmt.executeQuery("select nome,numerobigliettivenduti from parco where nome ='"+nomeparco+"'");
		    
		    /*Pendi il risultato*/
		    while (rs.next()) {
		     	String nomeParco = rs.getString("nome");
			    String numerobiglietti = rs.getString("NumeroBigliettiVenduti");
			   	System.out.println("PARCO [NOME: "+nomeParco+", NUMERO BIGLIETTI VENDUTI: "+numerobiglietti+"]"); 	
		    }
		    System.out.println("Eseguita...\n  ");
		}
		catch(SQLException e ){
		//	e.printStackTrace();
			System.out.println("\nErrore nell'inserimento dei dati\n");
		}
	}
	/**
	 * Esegue l'operazione 3
	 */
	public static void getOP3(){
		try{
			/*Cosa chiede l'operazione 3 */
			System.out.print("Digita nome del parco di cui stampare i dipendenti (con relativo tipo di contratto) =  ");
			String nomeparco = in.nextLine();
			ResultSet rs= stmt.executeQuery("select Nome,TipoContratto from dipendente  where parco ='"+nomeparco+"'");
		    
		    //stampa a video tutti i dipendenti e il relativo contratto
		    while (rs.next()){
		     	String nomeDip = rs.getString("nome");
			   	String tipoContratto = rs.getString("Tipocontratto");
			   	System.out.println("DIPENDENTE [NOME: "+nomeDip+", TIPO CONTRATTO: "+tipoContratto + "]");
		    }	
		    System.out.println("Eseguita...\n ");
		}
		catch(SQLException e ){
		//	e.printStackTrace();
			System.out.println("\nErrore nell'inserimento dei dati\n");
		}
	}
	/**
	 * Esegue l'operazione 4	 
	 */
	public static void getOP4(){
		try{
			/*Cosa chiede l'operazione 4 */
			System.out.print("Digita nome del parco di cui stampare le promozioni: ");
			String nomeparco = in.nextLine();
			ResultSet rs= stmt.executeQuery("select Nome,Descrizione from promozione  where parco ='"+nomeparco+"'");
		     
		     //stampa tutte le promozioni di un parco
		     while (rs.next()) {
		    	 	String nomePromo = rs.getString("nome");
			    	String descrizionePromo = rs.getString("Descrizione");
			    	System.out.println("PROMOZIONE [NOME: "+nomePromo+", DESCRIZIONE: "+descrizionePromo + "]");
		     }	
		     System.out.println("Eseguita...\n ");
		}
		catch(SQLException e ){
			System.out.println("\nErrore nell'inserimento dei dati\n");
		}
	}
	/**
	 * Esegue operazione 5
	 */
	public static void getOP5() {
		try{
			 /*Cosa chiede l'operazione 5 */
			System.out.print("Digita nome del parco di cui stampare i pacchetti in vendita dalle agenzie: ");
			String nomeparco = in.nextLine();
			ResultSet rs = stmt.executeQuery("select * from agenzia join pacchetto on agenzia.PartitaIVA=pacchetto.Agenzia "
		     		+ "where agenzia.parco ='"+nomeparco+"'");
		    
			//carica il risultato
		    while (rs.next()) {		/*Numero di colonna*/			
		     	String partitaIVA = rs.getString(1);
			   	String nomeAgenzia = rs.getString(2);			    	
			   	String codicePacchetto = rs.getString(6);
			   	String nomePacchetto = rs.getString(7);
			   	String descrizionePacchetto = rs.getString(8);
			   	String prezzoPacchetto = rs.getString(9);
			   	String hotel= rs.getString(10);
			   	String ristorante= rs.getString(11);
			   	System.out.println("AGENZIA [NOME: "+nomeAgenzia+", PARTITA IVA: "+partitaIVA+", CODICE PACCHETTO: "+codicePacchetto+", NOME PACCHETTO: "+nomePacchetto
			    			+", DESCRIZIONE: "+descrizionePacchetto+", PREZZO: "+prezzoPacchetto+", HOTEL: "+hotel+", RISTORANTE: "+ristorante+" ]");
		     }	
		     System.out.println("Eseguita...\n ");
		}
		catch(SQLException e ){
			System.out.println("\nErrore nell'inserimento dei dati\n");
		}
	}
	/**
	 * Esegue operazione 6
	 */
	public static void getOP6() {
		try{
			/*Cosa chiede l'operazione 6 */
			System.out.println("Aggiungere una nuova struttura  Codice,Nome,Categoria,Parco   ");
			System.out.print("Inserisci CODICE STRUTTURA (MAX 6 CHAR) =  ");
			String codicestruttura = in.nextLine();
			if (codicestruttura.length() > 6){
				do{
				System.out.println("Codice Struttura Troppo Lungo, Riprova");
				System.out.print("Inserisci CODICE STRUTTURA (MAX 6 CHAR) =  ");
				codicestruttura = in.nextLine();
				}while(codicestruttura.length()>6);
			}
			System.out.print("Inserisci Nome =  ");
			String nome = in.nextLine();
			System.out.print("Inserisci Categoria =  ");
			String categoria = in.nextLine();
			System.out.print("Inserisci Parco =  ");
			String parco = in.nextLine();
			
			// the mysql insert statement (inserimento)
			String query = " insert into struttura (codicestruttura,nome,categoria,parco)" + " values (?, ?, ?, ?)";
		     
		    // create the mysql insert preparedstatement
		    PreparedStatement preparedStmt = (PreparedStatement) con.prepareStatement(query);
		    preparedStmt.setString (1,codicestruttura);/*numero colonna+ dato(Striga)*/
		    preparedStmt.setString (2,nome);
		    preparedStmt.setString (3,categoria);
		    preparedStmt.setString (4,parco);
		     
		    // execute the prepared statement
		    preparedStmt.execute();
		      
		    String verifica = " select * from struttura where struttura.CodiceStruttura = '"+codicestruttura+"'";
		    System.out.println("Eseguita...\nPer conferma si esegue la query: "+verifica);
		    ResultSet rs = stmt.executeQuery(verifica);
		    while (rs.next()) {
		    	System.out.println("Codice Struttura: "+rs.getString(1) + ", Nome: "+rs.getString(2)+ ", Categoria: "+rs.getString(3)
		    	+ ", Parco: "+rs.getString(4) + "\n");
		    }
		}
		catch(SQLException e ){
			System.out.println("\nErrore nell'inserimento dei dati\n");
		}
	}
	/**
	 * Esegue operazione 7 
	 */
	public static void getOP7() {
		try{
			System.out.println("Stampa tutti i cliente che hanno acquistato un biglietto   "); 
			ResultSet rs= stmt.executeQuery("select distinct nome,cognome from biglietto join cliente on biglietto.cliente = cliente.CodiceFiscale");
		     /*Eccezzione inserimento parco non presente*/
		     
		     /*Pendi il risultato*/
		     while (rs.next()) {
		    	 	String nome= rs.getString("nome");
			    	String cognome = rs.getString("cognome");
			    	System.out.println("CLIENTE [NOME: "+nome+", COGNOME: "+cognome+"]"); 	
		     }
		     System.out.println("Eseguita...\n ");
		}
		catch(SQLException e ){
			System.out.println("\nErrore nell'inserimento dei dati\n");
		}
	}
	/**
	 * Esegue operazione 8
	 */
	public static void getOP8() {
		try{
			/*Cosa chiede l'operazione 8 */
			System.out.println("Aggiungere una nuova agenzia partitaiva,nome,indirizzo,numerotelefono   ");
			System.out.println("Inserisci PartitaIva (MAX 11 CHAR) =  ");
			String PartitaIva = in.nextLine();
			if (PartitaIva.length() > 11){
				do{
				System.out.println("PartitaIva Troppo Lungo, Riprova");
				System.out.println("Inserisci PartitaIva (MAX 11 CHAR) =  ");
				PartitaIva = in.nextLine();
				}while(PartitaIva.length()>6);
			}
			System.out.println("Inserisci Nome =  ");
			String nomeAgenzia = in.nextLine();
			System.out.println("Inserisci indirizzo =  ");
			String indirizzo = in.nextLine();
			System.out.println("Inserisci numero di telefono =  ");
			String numerotelefono = in.nextLine();
			System.out.println("Inserisci parco dove risiede l'agenzia =  ");
			String parco = in.nextLine();
			
			// the mysql insert statement (inserimento)
			String query = " insert into agenzia (PartitaIVA,nome,Indirizzo,NumeroTelefono,Parco)" + " values (?, ?, ?, ?, ?)";
		     
		    // create the mysql insert preparedstatement
		    PreparedStatement preparedStmt = (PreparedStatement) con.prepareStatement(query);
		    preparedStmt.setString (1,PartitaIva);/*numero colonna+ dato(Striga)*/
		    preparedStmt.setString (2,nomeAgenzia);
		    preparedStmt.setString (3,indirizzo);
		    preparedStmt.setString (4,numerotelefono);
		    preparedStmt.setString(5, parco);

		    // execute the preparedstatement
		    preparedStmt.execute();
		   
		    String verifica = "select * from agenzia where agenzia.PartitaIVA = '"+PartitaIva+"'";
		    System.out.println("Eseguita...\nPer conferma si esegue la query: "+verifica);
		    ResultSet rs = stmt.executeQuery(verifica);
		    
		    while (rs.next()) {
		    	System.out.println("Partita Iva: "+rs.getString(1) + ", Nome: "+rs.getString(2)+ ", Indirizzo: "+rs.getString(3)
		    	+", Numero di Telefono: "+numerotelefono+ ", Parco: "+rs.getString(5) + "\n");
		    }
		}
		catch(SQLException e ){
			System.out.println("\nErrore nell'inserimento dei dati\n");
		}
	}
	/**
	 * Esegue operazione 9
	 */
	public static void getOP9() {
		try{
			String tutteAttivita = "select * from attivita";
		    System.out.println("Per la scelta dell'attività da eliminare sarà eseguita la query : "+tutteAttivita+"\n");
		    ResultSet rs = stmt.executeQuery(tutteAttivita);
		    while (rs.next()) {
		    	System.out.println("ATTIVITA [Codice Attivita: "+rs.getString(1) + ", Nome: "+rs.getString(2)+ ", Prezzo: "+rs.getString(3)
		    	+", Parco: "+rs.getString(4) + "]");
		    }
			System.out.print("\nInserisci codice dell'attività da eliminare = ");
			String codiceattivita = in.nextLine();
			String query = "DELETE FROM attivita WHERE codiceattivita = ?";
		    
			PreparedStatement preparedStmt = (PreparedStatement) con.prepareStatement(query);
		    preparedStmt.setString (1,codiceattivita);
		    preparedStmt.executeUpdate();

		    System.out.println("Eseguita...\nPer conferma sono mostrate nuovamente tutte le attività presenti: ");
		    rs = stmt.executeQuery(tutteAttivita);
		    while (rs.next()) {
		    	System.out.println("ATTIVITA [Codice Attivita: "+rs.getString(1) + ", Nome: "+rs.getString(2)+ ", Prezzo: "+rs.getString(3)
		    	+", Parco: "+rs.getString(4) + "]");
		    }
		}
		catch(Exception e ){
			System.out.println("\nErrore nell'inserimento dei dati\n");
		}
	}
	/**
	 * Esegue operazione 10 
	 */
	public static void getOP10() {
		try{
			System.out.println("Stampare i clienti che hanno acquistato dei pacchetti:");
			ResultSet rs ;
		     rs= stmt.executeQuery("select distinct cliente.nome,cliente.cognome from pacchetto join cliente on pacchetto.Cliente = cliente.CodiceFiscale");
		     
		     /*Pendi il risultato*/
		    while (rs.next()) {
		    	String nome= rs.getString("nome");
			    String cognome = rs.getString("cognome");
			    System.out.println("CLIENTE [NOME: "+nome+", COGNOME: "+cognome+"]"); 
		    }
		    System.out.println("Eseguita...\n ");
		}
		catch(SQLException e ){
			System.out.println("\nErrore nell'inserimento dei dati\n");
		}
	}
	
	/**
	 * Esegue operazione 11 
	 */
	public static void getOP11() {
		try{
			System.out.println("Digita l'indirizzo dell agenzia");
			String indirizzo = in.nextLine();
			ResultSet rs = stmt.executeQuery("Select P.Codice,P.Descrizione,p.prezzo,p.Agenzia from pacchetto p join agenzia a on a.PartitaIVA = p.Agenzia where a.indirizzo ='"+indirizzo+"'");
		     /*Eccezzione inserimento parco non presente*/
		     
		     /*Pendi il risultato*/
		     while (rs.next()) {		/*Numero di colonna*/			
			     	String Codice = rs.getString(1);
				   	String Descrizione = rs.getString(2);			    	
				   	String Prezzo = rs.getString(3);
				   	String Agenzia = rs.getString(4);
				   	System.out.println("PACCHETTO [CODICE:"+Codice+", Descrizione : "+Descrizione+", Prezzo: "+Prezzo+", Agenzia: "+Agenzia+" ]");
			     }	
		    System.out.println("Eseguita...\n ");
		}
		catch(SQLException e ){
			e.printStackTrace();
			System.out.println("\nErrore nell'inserimento dei dati\n");
		}
	}
	
	/**
	 * Esegue operazione 12 
	 */
	public static void getOP12() {
		try{
			System.out.println("Stampa i clienti omonimi con stesso nome, stesso cognome ma con codici fiscali diversi : ");
			ResultSet rs = stmt.executeQuery("SELECT CodiceFiscale,nome,cognome FROM cliente C WHERE exists (SELECT * FROM Cliente C1 WHERE C.Nome=C1.Nome and C.Cognome = C1.Cognome and C.CodiceFiscale <> C1.CodiceFiscale)");
		     /*Eccezzione inserimento parco non presente*/
		     
		     /*Pendi il risultato*/
		    while (rs.next()) {
		    	String CodiceFiscale= rs.getString("CodiceFiscale");
			    String nome = rs.getString("nome");
			    String cognome = rs.getString("cognome");
			    System.out.println("CLIENTE [CodiceFiscale: "+CodiceFiscale+"NOME:"+nome+", COGNOME: "+cognome+"]"); 
		    }
		    System.out.println("Eseguita...\n ");
		}
		catch(SQLException e ){
			System.out.println("\nErrore nell'inserimento dei dati\n");
		}
	}
	
	/**
	 * Esegue operazione 13
	 */
	public static void getOP13() {
		try{
			System.out.println("Digita il Codice Fiscale del cliente: ");
			String cliente = in.nextLine();
			ResultSet rs = stmt.executeQuery("Select A.nome from attivita A join partecipazione P on A.CodiceAttivita=P.Attivita where P.Cliente = '"+cliente+"'");
		     /*Eccezzione inserimento parco non presente*/
		     
		     /*Pendi il risultato*/
		     while (rs.next()) {		/*Numero di colonna*/			
			     	String Nome = rs.getString(1);
			     	System.out.println("Attività [Nome: "+Nome+"]");
			     }	
		    System.out.println("Eseguita...\n ");
		}
		catch(SQLException e ){
			e.printStackTrace();
			System.out.println("\nErrore nell'inserimento dei dati\n");
		}
	}
	/**
	 * Esegue operazione 14
	 */
	public static void getOP14() {
		try{
			System.out.println("Digita il tipo del parco tra 'tematico','acquatico' o 'avventura' : ");
			String tipo = in.nextLine();
			ResultSet rs = stmt.executeQuery("select PR.codice as Codice_Promo , Pr.Descrizione, Pr.Costo , Pa.nome as Nome_Parco from promozione PR join Parco PA on Pr.parco = PA.nome where Pa.tipo='"+tipo+"'");
		     /*Eccezzione inserimento parco non presente*/
		     
		     /*Pendi il risultato*/
		     while (rs.next()) {		/*Numero di colonna*/			
			     	String Codice= rs.getString(1);
			     	String Descrizione= rs.getString(2);
			     	String Costo = rs.getString(3);
			     	String Parco= rs.getString(4);
			     	System.out.println("Promozioni [CodicePromo: "+Codice+", DescrizionePromo : "+Descrizione+", PrezzoPromo : "+Costo+", Nome_Parco: "+Parco+"]");
			     }	
		    System.out.println("Eseguita...\n ");
		}
		catch(SQLException e ){
			e.printStackTrace();
			System.out.println("\nErrore nell'inserimento dei dati\n");
		}
	}
	
	/**
	 * Esegue operazione 15
	 */
	public static void getOP15() {
		try{
			System.out.println("Digita il nome del parco : ");
			String nome = in.nextLine();
			ResultSet rs = stmt.executeQuery("select P.Hotel, a.parco as Parco_convenzionato from pacchetto P join agenzia a on P.Agenzia = a.PartitaIVA where a.parco = '"+nome+"' and p.hotel is not null");
		     /*Eccezzione inserimento parco non presente*/
		     
		     /*Pendi il risultato*/
		     while (rs.next()) {		/*Numero di colonna*/			
			     	String nHotel= rs.getString(1);
			     	String nParco= rs.getString(2);
			     	
			     	System.out.println("Hotel Convenzionati [Nome Hotel: "+nHotel+", Nome Parco : "+nParco+"]");
			     }	
		    System.out.println("Eseguita...\n ");
		}
		catch(SQLException e ){
			e.printStackTrace();
			System.out.println("\nErrore nell'inserimento dei dati\n");
		}
	}	
	//variabili di istanza
	private static Statement stmt;
	private static Connection con;
	private static Scanner in;
}