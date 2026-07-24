package interfacciaUtente;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import classiAstratte.FormatoArticolo;
import exceptions.ElementoNonTrovatoException;
import exceptions.ParametroException;
import exceptions.RidondanzaException;
import jbook.util.Input;
import programma.ArticoloSpesa;
import programma.GestioneListeSpesa.GestioneListe;
import programma.ListaSpesa;

public class InterfacciaRigaComando {
	
	
		
		
		public static void interfaccia() throws FileNotFoundException, IOException, ClassNotFoundException, ParametroException, RidondanzaException, ElementoNonTrovatoException {
			System.out.println("Benvenuto! Cosa vuoi fare?\n" +
							   "per scegliere l'operazione, inserire il numero ad inizio riga\n" +
			                   "In qualsiasi momento, digitare 'Q' per tornare indietro\n");
		
			
			while(true) {
				System.out.println("------------HOME------------\n" +
						           "1. Crea una nuova lista\n" +
						           "2. Elimina lista\n" +
								   "3. Seleziona lista\n" + 
						           "4. Crea categoria\n" +
								   "5. Elimina categoria\n" +
								   "6. Salva su file\n" +
								   "7. Carica da file\n" +
								   "8. Visualizza tutte le liste\n" +
								   "9. Chiudi Terminale\n");
					
				
				try {
					int scelta = Input.readInt();
					switch(scelta) {
					
						case 1 -> creaLista();
						
						case 2-> eliminaLista();
						
						case 3 -> { 
							ListaSpesa result = selezionaLista();
							if (result!= null) {
								operaSuLista(result);
							}
						}
						
						case 4 -> creaCategoria();
						  
						case 5 -> eliminaCategoria();
						
						case 6 -> salvaFile();
						
						case 7 -> caricaFile();
						
						case 8 -> System.out.println(GestioneListe.mytoString());
						 
						case 9 -> {
							System.out.println("arrivederci!!!");
							return;
						}
						default -> System.out.println("non esiste un opzione con questo numero, riprovare\n");
	
					}
				} 
				catch (java.lang.NumberFormatException e) {
					System.out.println("inserire il numero ad inizio riga. Stringhe o altri formati non sono accettati");
				}
			}
		}
		
		
//-------------------------------------------
		private static void creaLista() {
			System.out.println("------------CREAZIONE LISTA------------");
			stampaNomiListe();
			String risposta;
			while(true) {
				risposta = Input.readString("Fornire un nome alla nuova lista:\n");
				
					if(risposta.equals("Q")) {
						return;
					}
					else {
						try {
							GestioneListe.creaListaSpesa(risposta);
							System.out.println("lista \"" + risposta + "\" creata con successo\n");
							return;
						} 
						catch (ParametroException e) {
							System.out.println("Il nome inserito non e' adatto per una lista, riprovare");
						}	
						catch (RidondanzaException e) {
							System.out.println("Esiste gia' una lista con questo nome, riprovare");
						}
					}
			}
		}
		
		
//------------------------------------------------
		private static void stampaNomiListe() {
			System.out.print("\nListe gia' esistenti:");
			//questo if e' necessario, altrimenti il "risultato.setCharAt" da' errore poiche' lenght di 0 - 1 = errore
			if(GestioneListe.getListe().size()==0) {
				System.out.println("nessuna lista creata\n");
				return;
			}
			
			StringBuilder risultato = new StringBuilder();
	    
			for(ListaSpesa element : GestioneListe.getListe()) {
				risultato.append(" " + element.getNome() + ",");
			}
			
			risultato.setCharAt(risultato.length()-1, '.');
			System.out.println(risultato);
		}
		
//----------------------------------------------
		private static void eliminaLista() {
			System.out.println("------------ELIMINAZIONE LISTE------------");
			stampaNomiListe();
			String risposta;
			while(true) {
				risposta = Input.readString("Fornire il nome della lista da eliminare:\n");
				
				if(risposta.equals("Q")) {
					return;
				}
				else {
					try {
						if(GestioneListe.eliminaListaSpesa(risposta)) {
							System.out.println("Eliminazione avvenuta con successo");
						}
						else {
							System.out.println("Nessuna lista possiede questo nome, nessuna eliminazione effettuata\n");
						}
						break;
					}
					catch (ParametroException e) {							
						System.out.println("Il nome inserito non e' adatto per una lista, riprovare");		
					} 
				}
			}
		}
		
//---------------------------------------------
		private static ListaSpesa selezionaLista() {
			System.out.println("------------SELEZIONE LISTE------------");
			stampaNomiListe();
			String risposta;
			while(true) {
				risposta = Input.readString("Fornire il nome della lista da selezionare:\n");
				
				if(risposta.equals("Q")) {
					return null;
				}
				else {
					try {
						return GestioneListe.getListaSpesa(risposta);
	
					} catch (ParametroException e) {							
						System.out.println("Il nome inserito non e' adatto per una lista, riprovare");
							
					} catch (ElementoNonTrovatoException e) {
						System.out.println("Non esiste una lista con tale nome, riprovare");
					}	
				}
			}
		}

//-----------------------------------------
		private static void operaSuLista(ListaSpesa listaScelta) throws ParametroException, ElementoNonTrovatoException, RidondanzaException {
			while(true) {
			System.out.println("------------OPERAZIONI LISTA------------\n" +
							   "Contenuto lista selezionata:\n" +
					           listaScelta.toString()+ "\n" +
							   "Costo totale: " + listaScelta.costoTotale() + "\n");
			String risposta;
			
			risposta = Input.readString("Operazioni:\n" +
										"1. Aggiungi nuovo articolo\n" +
										"2. Cerca per categoria\n" + 
										"3. Cerca per nome\n" +
										"4. Cerca per prezzo\n" +
										"5. Elimina per categoria\n" +
										"6. Elimina articolo\n");
												
			
			switch(risposta) {
					case "1" -> {
						ArticoloSpesa result = creazioneArticolo(listaScelta);
						if(result != null) {
							aggiuntaArticolo(result, listaScelta);
						}
						
					}				
					
					case "2" -> ricercaPerCategoria(listaScelta);
					
					case "3" -> ricercaPerNome(listaScelta);
					
					case "4" -> ricercaPerPrezzo(listaScelta);
					
					case "5" -> eliminaPerCategoria(listaScelta);
					
					case "6" -> eliminaPerNome(listaScelta);
								
					case "Q" -> {return;}
					
					default -> System.out.println("non esiste un operazione con tale identificativo");	
					}
				}
			}
		


//----------------------------------------------------
		private static ArticoloSpesa creazioneArticolo(ListaSpesa lista) throws ParametroException, ElementoNonTrovatoException {
			System.out.println("------------CREAZIONE ARTICOLO------------");
			
			stampaNomiArticoli(lista);
			stampaCategorie();
			String nome;
			float costo;
			int quantita;
			String categoria;
			String placeholder;
			
			//controllo nome
			while(true) {
				nome = Input.readString("Inserire il nome del nuovo articolo\n");
				
				if(nome.equals("Q")) {
					return null;
				}
				
					if(!FormatoArticolo.checkString(nome)) {
						System.out.println("il nome inserito non e' adatto per essere un nome articolo, riprovare");
					}
					else if(GestioneListe.containsInLista(nome, lista.getNome())) {
						System.out.println("l'articolo e' gia' presente in lista");
					}
					else {
						break;
					}
				
			}
			
			//controllo costo
			while(true) {
				placeholder = Input.readString("Inserire il costo del nuovo articolo\n");
				
				if(placeholder.equals("Q")) {
					return null;
				}
				try {
					costo = Float.valueOf(placeholder);
					if(!ArticoloSpesa.checkCosto(costo)) {
						 System.out.println("costo inserito non valido, costi minori o pari a 0 non sono accettati, riprovare");
					}
					else {
						break;
					}
				}
				catch (NumberFormatException e) {
			        System.out.println("formato dati erroneo. Inserire solamente cifre, riprovare");
			    }
				
			}
			
			//controllo categoria
			
			categoria = Input.readString("Inserire la categoria del nuovo articolo (opzionale, lasciare vuoto se non si vuole specificare)\n");
			
			if(categoria.equals("Q")) {
				return null;
			}
			if(!FormatoArticolo.checkString(categoria)) {
				categoria = ArticoloSpesa.CATEGORIA_DEFAULT;
			}
		
			//controllo quantita'
			while(true) {
				placeholder = Input.readString("Inserire la quantita' del nuovo articolo (opzionale, lasciare vuoto se non si vuole specificare)\n");
				
				if(placeholder.equals("Q")) {
					return null;
				}
				if(placeholder.isBlank()) {
					quantita = ArticoloSpesa.QUANTITA_DEFAULT;
					break;
				}
				try {
					quantita = Integer.valueOf(placeholder);
					if(!ArticoloSpesa.checkQuantita(quantita)) {
						 System.out.println("quantita' inserita non valida, quantita' minori di 1 non sono accettate, riprovare");
					}
					else {
						break;
					}
				}
				catch (NumberFormatException e) {
				    System.out.println("formato dati erroneo. Inserire solamente numeri interi");
				}
			}
			
			
			
			
			ArticoloSpesa articolo = null;
		
			articolo = new ArticoloSpesa(nome, costo, categoria, quantita);
			return articolo;
		}
//-----------------------------------------------
		
		private static void aggiuntaArticolo(ArticoloSpesa articolo, ListaSpesa lista) throws ParametroException, RidondanzaException, ElementoNonTrovatoException {
			while(true) {
				try {
					GestioneListe.aggiungiArticolo(articolo, lista.getNome());
					System.out.println("aggiunta avvenuta con successo!\n");
					return;
				} 
				catch (ParametroException e) {
					
						String risposta = Input.readString("la categoria assegnata all'articolo non esiste. Creare la categoria corrispondente? (y/n)\n");
						switch(risposta) {
						
						case "y" -> {
						
								GestioneListe.creaCategoria(articolo.getCategoria());
						}
						
						case "n","Q" -> {
							System.out.println("creazione articolo annullata\n");
							return;
						}
						
						default -> System.out.println("comando inserito sbagliato, riprovare");
						}
				} 
			}
		}	
//-------------------------------------------------------
		
		private static void stampaNomiArticoli(ListaSpesa lista) {
			System.out.print("\nArticoli nella lista:");
			//questo if e' necessario, altrimenti il "risultato.setCharAt" da' errore poiche' lenght di 0 - 1 = errore
			if(lista.size()==0) {
				System.out.println(" nessun articolo presente\n");
				return;
			}
			
			StringBuilder risultato = new StringBuilder();
			
				for(ArticoloSpesa element : lista) { 
					risultato.append(" " + element.getNome() + ",");
				}
				risultato.setCharAt(risultato.length()-1, '.');
				System.out.println(risultato);

			
		}
		
//---------------------------------------------------
		private static void ricercaPerCategoria(ListaSpesa listaCorrente) throws ParametroException, ElementoNonTrovatoException {
			System.out.println("------------RICERCA PER CATEGORIA------------\n");
			stampaCategorie();
			String risposta = null;
			
			while(true) {
				risposta = Input.readString("Inserire categoria da ricercare. Lasciare vuoto per trovare gli articoli non categorizzati\n");
				if(risposta.equals("Q")) {
					return;
				}
				
				try {
					if(!GestioneListe.containsCategoria(risposta)) {
						System.out.println("categoria inesistente, riprovare");
					}
					else {
						break;
					}
				}
				catch (ParametroException e) {
					risposta = "non categorizzato";
					break;
				}
			}
			ArrayList <ArticoloSpesa> risultato = null;
			risultato = GestioneListe.getListaSpesa(listaCorrente.getNome()).ricercaCategoria(risposta);
			
			System.out.println("Articoli con categoria \"" + risposta + "\":");
			if(risultato.isEmpty()) {
				System.out.println("nessun elemento");
			}
			for(ArticoloSpesa element : risultato) {
				System.out.println(element.toString());
			}
			System.out.println("\n");
		}

//-----------------------------------------------------------------------
		private static void stampaCategorie() {
			System.out.println("Categorie esistenti: ");
			
			//questo if e' necessario, altrimenti il "risultato.setCharAt" da' errore poiche' lenght di 0 - 1 = errore
			if(GestioneListe.getCategorie().size()==0) {
				System.out.println("nessuna categoria esistente\n");
				return;
			}
			
			StringBuilder risultato = new StringBuilder();
			for(String element : GestioneListe.getCategorie()) {
				risultato.append(" " + element + ",");
			}
			
			risultato.setCharAt(risultato.length()-1, '.');
			System.out.println(risultato);
		}

//------------------------------------------------------------------------
		private static void ricercaPerNome(ListaSpesa listaScelta){
			System.out.println("------------RICERCA PER NOME------------\n");
			stampaNomiArticoli(listaScelta);
			String risposta = null;
			ArrayList<ArticoloSpesa> risultato = new ArrayList<>();
			
			while(true) {
				risposta = Input.readString("Inserire il nome da ricercare. Lasciare vuoto per visualizzare tutti gli articoli presenti:\n");
				
				try {
					risultato = listaScelta.ricercaNome(risposta);
					break;
				} 
				catch (ParametroException e) {
					System.out.println("Errore, inserire un nome");
				}
			}
				
			if(risultato.isEmpty()) {
				System.out.println("nessun elemento trovato\n");
				return;
			}
			
			for(ArticoloSpesa element : risultato) {
				System.out.println(element.toString());
			}
			
			
		}
		
//-------------------------------------------
		private static void ricercaPerPrezzo(ListaSpesa lista) throws ParametroException {
			System.out.println("------------RICERCA PER PREZZO------------\n");
			String risposta = null;
			
			
			while(true) {
				risposta = Input.readString("Inserire il tipo di ricerca:\n" +
											"1. Ricerca articoli sotto un certo prezzo\n" +
											"2. Ricerca articoli sopra un certo prezzo\n" +
											"3. Ricerca articoli compresi tra due prezzi\n");
				String placeholder;
				float valore;
				switch(risposta) {
				
					case "1" -> {
						while(true) {
							placeholder = Input.readString("Inserire prezzo:\n");
							
							if(placeholder.equals("Q")) {
								return;
							}
							if(placeholder.isBlank()) {
								System.out.println("Errore, inserire un valore");
							}
							
							try {
								valore = Float.valueOf(placeholder);
								if(!ArticoloSpesa.checkCosto(valore)) {
									System.out.println("Sono accettati solo valori positivi come prezzo, riprovare\n");
								}
								else {
									break;
								}
							}
							catch (NumberFormatException e){
								System.out.println("Formato dati sbagliato, inserire solo cifre");
							}
						}
						
						ArrayList<ArticoloSpesa> risultato = new ArrayList<>();							
						
						while(true) {
							try {
								risultato = lista.MinoriDi(valore);
								break;
							} 
							catch (ParametroException e) {
								System.out.println("valore inserito non valido, inserire solo valori positivi");
							}
						}
						if(risultato.isEmpty()) {
							System.out.println("nessun elemento trovato\n");
							break;
						}
						for(ArticoloSpesa element : risultato) {
							System.out.println(element);
						}
					}
					
					case "2" -> {

						while(true) {
							placeholder = Input.readString("Inserire prezzo:\n");
							
							if(placeholder.equals("Q")) {
								return;
							}
							if(placeholder.isBlank()) {
								System.out.println("Errore, inserire un valore");
							}
							
							try {
								valore = Float.valueOf(placeholder);
								if(!ArticoloSpesa.checkCosto(valore)) {
									System.out.println("Sono accettati solo valori positivi come prezzo, riprovare\n");
								}
								else {
									break;
								}
							}
							catch (NumberFormatException e){
								System.out.println("Formato dati sbagliato, inserire solo cifre");

							}
						}
						
						ArrayList<ArticoloSpesa> risultato = new ArrayList<>();							
						
						while(true) {
							try {
								risultato = lista.MaggioriDi(valore);
								break;
							} 
							catch (ParametroException e) {
								System.out.println("valore inserito non valido, inserire solo valori positivi");
							}
						}
						if(risultato.isEmpty()) {
							System.out.println("nessun elemento trovato\n");
							break;
						}
						for(ArticoloSpesa element : risultato) {
							System.out.println(element);
						}
						
					}
					case "3" -> {
						while(true) {
							placeholder = Input.readString("Inserire prezzo minimo:\n");
							
							if(placeholder.equals("Q")) {
								return;
							}
							if(placeholder.isBlank()) {
								System.out.println("Errore, inserire un valore");
							}
							
							try {
								valore = Float.valueOf(placeholder);
								if(!ArticoloSpesa.checkCosto(valore)) {
									System.out.println("Sono accettati solo valori postivi come prezzo, riprovare\n");
								}
								break;
							}
							catch (NumberFormatException e) {
						        System.out.println("Formato dati erroneo. Inserire solamente cifre, riprovare");
						    }
						}
						String placeholder2;
						float valore2;
						while(true) {
							placeholder2 = Input.readString("Inserire prezzo massimo:\n");
							
							if(placeholder2.equals("Q")) {
								return;
							}
							if(placeholder2.isBlank()) {
								System.out.println("Errore, inserire un valore");
							}
							
							try {
								valore2 = Float.valueOf(placeholder2);
								if(!ArticoloSpesa.checkCosto(valore)) {
									System.out.println("Sono accettati solo valori postivi come prezzo, riprovare\n");
								}
								if(valore2<valore) {
									System.out.println("Errore, non puoi inserire un valore massimo minore di quello minimo, riprovare");
								}
								else {
									break;
								}
							}
							catch (NumberFormatException e) {
						        System.out.println("Formato dati erroneo. Inserire solamente cifre, riprovare");
						    }
						}
						
						ArrayList<ArticoloSpesa> risultato = new ArrayList<>();							
						
						while(true) {
							risultato = lista.CompresiTra(valore, valore2);
							break;
							
							
						}
						if(risultato.isEmpty()) {
							System.out.println("nessun elemento trovato\n");
							break;
						}
						
						for(ArticoloSpesa element : risultato) {
							System.out.println(element);
						}
					}
					
					case "Q" -> {return;}
					
					default -> System.out.println("Non esiste un' operazione con questo identificativo, riprovare\n");
				}
			}
			
		}
		
//---------------------------------------------------------------------
		private static void eliminaPerCategoria(ListaSpesa lista) {
			System.out.println("------------ELIMINAZIONE PER CATEGORIE------------\n");
			stampaCategorie();
			String risposta = null;
			while(true) {
				risposta = Input.readString("inserire la categoria per cui eliminare:\n");
				
				if(risposta.equals("Q")) {
					return;
				}
				try {
					System.out.println("Numero articoli eliminati: " + lista.eliminaPerCategoria(risposta) + "\n");					
					return;
				} 
				catch (ParametroException e) {
					System.out.println("Errore, non hai inserito niente, riprovare");
				} 
			}	
		}
	
//------------------------------------------------------
		private static void eliminaPerNome(ListaSpesa lista) {
			System.out.println("------------ELIMINAZIONE PER NOME------------\n");
			stampaNomiArticoli(lista);
			String risposta = null;
			
			while(true) {
				risposta = Input.readString("Inserire il nome dell'articolo da eliminare:\n");
				if(risposta.equals("Q")) {
					return;
				}
				try {
					if(lista.eliminaPerNome(risposta)){
						System.out.println("Articolo eliminato con successo");
					}
					else {
						System.out.println("Nessun articolo possiede questo nome, nessuna eliminazione effettuata\n");
					}
					break;
				}
				catch(ParametroException e) {
					System.out.println("Errore, non hai inserito niente, riprovare\n");
				}
				
			}		
		}
		
//-------------------------------------------------------------------
		private static void creaCategoria(){
			System.out.println("------------CREAZIONE CATEGORIE------------\n");
			stampaCategorie();
			String risposta = null;
			while(true) {
				risposta = Input.readString("inserire nuova categoria:\n");
				
				if(risposta.equals("Q")) {
					return;
				}
				
				try {
					GestioneListe.creaCategoria(risposta);
					return;
				} 
				catch (ParametroException e) {
					System.out.println("Errore, non hai inserito niente, riprovare");
				} 
				catch (RidondanzaException e) {
					System.out.println("Errore, esiste gia' una categoria con questo nome, riprovare");
				}
			}
		}
		
//------------------------------
		private static void eliminaCategoria() {
			System.out.println("------------ELIMINAZIONE CATEGORIE------------\n");
			stampaCategorie();
			String risposta = null;
			while(true) {
				risposta = Input.readString("Inserire la categoria da eliminare. Tutti gli articoli appartenti a essa verrano impostati a \"non categorizzati\":\n");
				
				if(risposta.equals("Q")) {
					return;
				}
				
				try {
					if(GestioneListe.eliminaCategoria(risposta)) {
						System.out.println("Eliminazione avvenuta con successo\n");						
					}
					else {
						System.out.println("Non esiste tale categoria, nessuna eliminazione effettuata\n");
					}
					return;
				} catch (ParametroException e) {
					System.out.println("Errore non hai inserito niente, riprovare");
				}
				
			}
		}
		
//----------------------------------------------
		private static void salvaFile() throws FileNotFoundException, IOException {
				GestioneListe.serializza();
				System.out.println("Operazione eseguita con successo\n");
		}
		
//-----------------------------------------------------
		private static void caricaFile() throws ClassNotFoundException, ParametroException, RidondanzaException, IOException  {
			GestioneListe.deserializza();
			System.out.println("Operazione eseguita con successo\n");

	}
		
}
				
				
		

		
	
		
	


