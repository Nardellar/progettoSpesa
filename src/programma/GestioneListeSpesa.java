/*Deve essere possibile data una stringa e una lista della spesa trovare la lista degli articoli che 
hanno la stringa come prefisso. 
Quando una categoria viene cancellata dovete assegnare a tutti gli articoli
in tutte le liste la categoria "Non Categorizzati".
nella classe GestioneListe che contiene le liste dovete avere anche tutte le categorie
perché queste sono comuni a tutte le liste
*/



package programma;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import exceptions.ElementoNonTrovatoException;

import exceptions.ParametroException;
import exceptions.RidondanzaException;

/**
* Classe statica. Fornisce tutte le funzioni per la gestione delle varie liste, poiche' e' un gestore
* e fornisce solo "servizi" non ha senso che possa essere instanziata.
* <p>
* poiche' solo le nested-class possono essere rese "static" vi e' una seconda classe "GestoreListe".
* GestoreListe ha:
* - modificatore final: per impedirne l'estensione, dato che estendere una classe statica non ha senso.
* - costruttore privato: per evitare l'istanziazione, dato che instanziare una classe statica non ha senso
* - tutte le funzioni statiche: non potendo essere instanziata, tutti i metodi non statici sarebbero inaccessibili
* 
* 
* @author Simone Nardella
*/
public class GestioneListeSpesa implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -725636044004166855L;

	public static final class GestioneListe implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 8028958460415971332L;
		static private ArrayList<ListaSpesa> listone = new ArrayList<ListaSpesa>();
		static private ArrayList<String> elencoCategorie = new  ArrayList<String>();
		
		/**
		 * costruttore privato del gestioneSpesa. non puo' mai essere invocato
		 */
	    private GestioneListe () { 
	    }
	    
	   /**
		* Cancella tutte le informazioni immagazzinate nel gestore ( liste e categorie)
	    */
	    public static void reset() {
	    	listone.clear();
	    	elencoCategorie.clear();
		}
	    
	    /**
	     * Controlla la validita' di una stringa
	     *   
	     * @param str la stringa da valutare
	     * @throws ParametroException se str == null oppure str.isBlanck = true
	     */
	    static void checkString(String str) throws ParametroException {
	    	if(str == null || str.isBlank()) {
	    		throw new ParametroException("stringa fornita non valida");
	    	}
	    	
	    }
	    
	    /**
	     * crea una nuova lista della spesa
	     * 
	     * @param nome della lista che si vuole creare
	     * @throws ParametroException se il nome della lista == null oppure nome.isBlank = true
	     * @throws RidondanzaException se e' gia presente una lista con lo stesso nome
	     */
	    public static void creaListaSpesa(String nome) throws ParametroException, RidondanzaException {
	    	checkString(nome);

	    	if(containsLista(nome.trim())) {
	    		throw new RidondanzaException("lista gia' presente");
	    	}
	    	ListaSpesa lista = new ListaSpesa(nome.trim());
	    	listone.add(lista);
	       
	    }
	    
	    /**
	     * cambia il nome della lista della spesa
	     * 
	     * @param nomeLista il nome della lista che si vuole cambiare
	     * @param newNome il nuovo nome che si vuole assegnare
	     * @throws ElementoNonTrovatoException se la lista da modificare non esiste
	     * @throws ParametroException se una delle due stringhe fornite non e' adatta come nome lista
	     */
	    public static void cambiaNomeListaSpesa(String nomeLista, String newNome) throws ElementoNonTrovatoException, ParametroException {
	    	/*non e' necessario fare lo stesso controllo su newNome dato che gia' la funzione setNome
	    	 * lo applica 
	    	 */
	    	getListaSpesa(nomeLista).setNome(newNome.trim());
	    	}
	    
	    /**
	     * fornito un nome viene eliminata la listaSpesa avente quel nome
	     * 
	     * @param nome della lista che si vuole eliminare
	     * @return <strong>true</strong> se la lista conteneva l'elemento indicato, <strong>false</strong> altrimenti
	     * @throws ParametroException se la stringa fornita non e' adatta come nome lista
	     */
	    public static boolean eliminaListaSpesa(String nome) throws ParametroException{

	    	checkString(nome);

	    	if(!containsLista(nome)) {
	    		return false;
	    	}
	    	
			try {
				return listone.remove(getListaSpesa(nome));
			} 
			catch (ElementoNonTrovatoException e) {
				return false;
			}
			
	    }
	    
	    /**
	     * restituisce se esista una lista con il nome fornito
	     * 
	     * @param nome della lista da ricercare
	     * @return <strong>true</strong> se la lista conteneva l'elemento indicato, <strong>false</strong> altrimenti
	     * @throws ParametroException se nome == null oppure nome.isBlank = true
	     */
	    public static boolean containsLista(String nome) throws ParametroException {
	    	for(ListaSpesa element : listone) {
	    		if(element.getNome().equalsIgnoreCase(nome.trim())) {
	    			return true;
	    		}
	    	}
	    	return false;
	    }
	    
	    /**
	     * restituisce la lista della spesa dato il suo nome
	     * 
	     * @param nomeLista della spesa che si vuole ottenere
	     * @return la lista con il nome indicato.
	     * @throws ParametroException se nomeLista == null oppure nomeLista.isBlank = true
	     * @throws ElementoNonTrovatoException se la lista indicata non esiste
	     */
	    public static ListaSpesa getListaSpesa(String nomeLista) throws ParametroException, ElementoNonTrovatoException {
	    	checkString(nomeLista);
	    		
	    	for(ListaSpesa element : listone) {
	    		if(element.getNome().equalsIgnoreCase(nomeLista.trim())) {
	    			return element;
	    		}
	    	}
	    	throw new ElementoNonTrovatoException("lista inesistente");
		       
	    }
	    
	    /**
	     * Restiuisce il numero totale di liste
	     * 
	     * @return il numero di liste totali
	     */
	    public static int numeroListe() {
	    	return listone.size();
	    }
	    
	    /**
	     * crea una nuova categoria
	     * 
	     * @param newCategoria il nome della categoria che si vuole creare
	     * @throws ParametroException se newCategoria == null oppure newCategoria.isBlank = true
	     * @throws RidondanzaException se la categoria da inserire e' gia' presente
	     */
	    public static void creaCategoria(String newCategoria) throws ParametroException, RidondanzaException {
	    	checkString(newCategoria);
	    	if(elencoCategorie.contains(newCategoria.trim().toLowerCase())) {
	    		throw new RidondanzaException("categoria ridondante");
	    	}
	    	elencoCategorie.add(newCategoria.trim().toLowerCase());
	    }
	    
	    /**
	     * elimina la categoria richiesta. tutti gli articoli appartenenti a essa verranno 
	     * impostati alla categoria di default
	     * 
	     * @param categoria la categoria da eliminare
	     * @return <strong>true</strong> se il Gestore conteneva la categoria indicata, <strong>false</strong> altrimenti
	     * @throws ParametroException se categoria == null oppure categoria.isBlank = true
	     */
	    //l'eccezione ridondanzaException non e' presente nel java doc perche' e' impossibile sia lanciata, la eredita dal aggiungiAll 
	    public static boolean eliminaCategoria(String categoria) throws ParametroException { 
	    	checkString(categoria);
	    	if (!elencoCategorie.removeIf(pippo->pippo.equalsIgnoreCase(categoria))) {
	    		return false;
	    	}
	    	for(ListaSpesa lista : listone) {
	    			lista.eliminaCategoria(categoria);
	    	}	    	
	    	return true;
	    	
	    }
	    /**
	     * restiuisce se la categoria fornita e' presente 
	     * 
	     * @param categoria la categoria da ricercare
	     * @return <strong>true</strong> se il Gestore contiene la categoria indicata, <strong>false</strong> altrimenti
	     * @throws ParametroException se categoria == null oppure categoria.isBlank = true
	     */
	    public static boolean containsCategoria(String categoria) throws ParametroException {
	    	checkString(categoria);
	    	return elencoCategorie.contains(categoria.toLowerCase().trim());
	    }
	    
	    /**
	     * Restiuisce il numero di categorie presenti 
	     * 
	     * @return il numero di categorie presenti
	     */
	    public static int numeroCategorie() {
	    	return elencoCategorie.size();
	    }
	    
	    /**
	     * aggiunge l'articolo specificato alla lista specificata
	     * 
	     * @param articolo l'articolo da aggiungere
	     * @param nomeLista la lista in cui aggiungere l'articolo
	     * @throws ParametroException se nomeLista == null oppure nomeLista.isBlank = true oppure se la categoria dell'articolo non esiste
	     * @throws ElementoNonTrovatoException se la lista indicata non esiste
	     * @throws RidondanzaException se l'articolo e' gia' presente in lista
	     */
	    public static void aggiungiArticolo(ArticoloSpesa articolo, String nomeLista) throws ParametroException, RidondanzaException, ElementoNonTrovatoException{
	    	if(!containsCategoria(articolo.getCategoria()) &&  !articolo.getCategoria().equals(ArticoloSpesa.CATEGORIA_DEFAULT)) {
	    		throw new ParametroException("categoria dell'articolo inesistente");
	    	}
	    	getListaSpesa(nomeLista).aggiungi(articolo);
 
	    }
	    
	    /**
	     * elimina l'articolo specificato nella lista specificata
	     * 
	     * @param articolo l'articolo da eliminare
	     * @param nomeLista la lista in cui eliminare l'articolo
	     * @return <strong>true</strong> se l'articolo da eliminare era presente in lista, <strong>false</strong> altrimenti
	     * @throws ElementoNonTrovatoException se la lista specificata non esiste
	     * @throws ParametroException se nomeLista == null o nomeLista.isBlank = true, oppure se il nome dell'articolo = null o nomeArticolo.isBlank = true
	     */
	    public static boolean eliminaArticolo(ArticoloSpesa articolo, String nomeLista) throws ElementoNonTrovatoException, ParametroException{
	    	return getListaSpesa(nomeLista).eliminaPerNome(articolo.getNome());
	    	 
	    }
	    
	    /**
	     * elimina l'articolo col nome specificato nella lista specificata
	     * 
	     * @param nomeArticolo il nome dell'articolo da eliminare
	     * @param nomeLista la lista da cui eliminare l'articolo
	     * @return <strong>true</strong> se l'articolo da eliminare era presente in lista, <strong>false</strong> altrimenti
	     * @throws ElementoNonTrovatoException se la lista specificata non esiste
	     * @throws ParametroException se nomeLista == null o nomeLista.isBlank = true oppure se nomeArticolo = null o nomeArticolo.isBlank = true
	     */
	    public static boolean eliminaArticolo(String nomeArticolo, String nomeLista) throws ParametroException, ElementoNonTrovatoException {
	    	return getListaSpesa(nomeLista).eliminaPerNome(nomeArticolo);
	    	 
	    }
	    
	    /**
	     * Indica se l'articolo e' contenuto nella lista specificata
	     * 
	     * @param articolo l'articolo da ricercare
	     * @param nomeLista la lista in cui cercare l'articolo
	     * @return <strong>true</strong> se l'articolo e' presente, <strong>false</strong> altrimenti
	     * @throws ParametroException se nomeLista == null oppure nomeLista.isBlank = true
	     * @throws ElementoNonTrovatoException se la lista indicata non esiste
	     */
	    public static boolean containsInLista(ArticoloSpesa articolo, String nomeLista) throws ParametroException, ElementoNonTrovatoException {
	    	
	    	return getListaSpesa(nomeLista).contains(articolo);
	    }
	    
	    /**
	     * Indica se l'articolo e' contenuto nella lista specificata
	     * 
	     * @param nomeArticolo il nome dell'articolo da ricercare
	     * @param nomeLista la lista in cui cercare l'articolo
	     * @return <strong>true</strong> se l'articolo e' presente, <strong>false</strong> altrimenti
	     * @throws ParametroException se almeno una delle due stringhe e' vuota oppure "null"
	     * @throws ElementoNonTrovatoException se la lista indicata non esiste
	     */
	    public static boolean containsInLista(String nomeArticolo, String nomeLista) throws ParametroException, ElementoNonTrovatoException {
	    	
	    	return getListaSpesa(nomeLista).contains(nomeArticolo);
		
	    }
	    
	    /**
	     * Restituisce tutti gli articoli con prefisso "nomeArticolo" nella lista specificata 
	     * 
	     * @param nomeArticolo il prefisso con cui cercare
	     * @param nomeLista la lista in cui ricercare gli articoli
	     * @return tutti gli articoli con prefisso == nomeArticolo
	     * @throws ParametroException se nomeLista == null, nomeLista.isBlank = true o nomeArticolo e' "null".
	     * @throws ElementoNonTrovatoException se la lista indicata non esiste
	     */
	    
	    public static ArrayList<ArticoloSpesa> cercaArticoli(String nomeArticolo, String nomeLista) throws ParametroException, ElementoNonTrovatoException{
			
	    	return getListaSpesa(nomeLista).ricercaNome(nomeArticolo);
	    	 
	    }
	    /**
		 * Restituisce una rappresentazione a Stringa dell'oggetto
		 * 
		 * @return una stringa rappresentante l'oggetto
		 */
	    
	    public static String mytoString() {
	    	StringBuilder risultato = new StringBuilder();
	    	risultato.append("Gestore:\nCategorie: " + elencoCategorie.toString() + "\n");

	    	for(ListaSpesa element : listone) {
	    		 risultato.append(element.toString()).append("\n");
	    	}
			
			return risultato.toString();
			
		}
	    
	   
	    
	    
	    
	    /**
	     * restituisce una copia di tutte le liste della spesa
	     * 
	     * @return una lista di listeSpesa
	     */
	    @SuppressWarnings("unchecked")
		public static ArrayList<ListaSpesa> getListe() {
	    	return (ArrayList<ListaSpesa>) listone.clone();
	    }
	    
	    /**
	     * restituisce una copia di tutte le categorie
	     * 
	     * @return una lista contenente tutte le categorie
	     */
	    @SuppressWarnings("unchecked")
		public static ArrayList<String> getCategorie(){
			return (ArrayList<String>) elencoCategorie.clone();
			
		}
	    
	    /**
	     * Salva su file tutte le liste della spesa ed il loro contenuto
	     * @throws FileNotFoundException 
	     * @throws IOException
	     */
		public static void serializza() throws FileNotFoundException, IOException {
			ObjectOutputStream fbinarioOut = new ObjectOutputStream(new FileOutputStream("liste.txt"));
			HashMap<ArrayList<ListaSpesa>, ArrayList<String>> mario = new HashMap<>();
			mario.put(listone, elencoCategorie);
			fbinarioOut.writeObject(mario);
			fbinarioOut.flush();
			fbinarioOut.close();
		}
		
		
		/**
		 * Carica da file tutte le liste della spesa salvate ed il loro contenuto. I dati presenti nel gestore prima della deserializzazione vengono cancellati  
		 * @throws ClassNotFoundException
		 * @throws IOException
		 * @throws ParametroException
		 * @throws RidondanzaException
		 */
		public static void deserializza() throws ClassNotFoundException, IOException, ParametroException, RidondanzaException {
			ObjectInputStream fin = new ObjectInputStream(new FileInputStream("liste.txt"));
			
			@SuppressWarnings("unchecked")
			HashMap<ArrayList<ListaSpesa>, ArrayList<String>> map = (HashMap<ArrayList<ListaSpesa>, ArrayList<String>>) fin.readObject();
			fin.close();
			Map.Entry<ArrayList<ListaSpesa>, ArrayList<String>> entry = (Entry<ArrayList<ListaSpesa>, ArrayList<String>>) map.entrySet().iterator().next();
			
			listone.clear();
	    	elencoCategorie.clear();
	    	
			listone.addAll(entry.getKey());
			elencoCategorie.addAll(entry.getValue());	
		}
	}
}
