package classiAstratte;
/*Gli articoli possono essere rimossi da una lista.
Deve essere possibile data una stringa e una lista della spesa trovare la lista degli articoli che
hanno la stringa come prefisso.
Per una lista deve essere possibile calcolare il costo totale degli articoli (considerando costo e quantità degli
articoli contenuti) e ritornare data una categoria gli articoli presenti nella lista che hanno associata quella
categoria.
Le liste devono essere iterabili per permettere ulteriori elaborazioni da parte del codice cliente.
*/

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import exceptions.ParametroException;

/**
* Versione astratta di una lista di articoli. Fornisce tutte le funzioni minime che una lista deve possedere.-
* alcuni metodi posseggono il "throws exceptions.GenericException" poiche' non tutti i figli potrebbero
* voler implementare ogni metodo
* 
* @author Simone Nardella
*/

public abstract class FormatoLista implements Serializable {
	
	private static final long serialVersionUID = 3613862763863470853L;
	protected String nome;
	
	
	/**
	 * Costruttore che sfruttano le classi figlie. crea una nuova lista
	 * <p>
	 * Al nome viene controllata la validita'.
	 * 
	 * @param nome il nome che si vuole dare alla lista
	 * @throws ParametroException se nome = null oppure nome.isBlank = true
	 */
	public FormatoLista(String nome) throws ParametroException {
		if(nome == null || nome.isBlank()) {
			throw new exceptions.ParametroException("nome non valido");
		}
		else {
			this.nome = nome.trim();
		}
	}
	/**
	 * Restituisce il nome della lista
	 * 
	 * @return il nome della lista
	 */
	public String getNome() {
		return this.nome;
	}
	
	/**
	 * Imposta un nuovo nome alla lista
	 * 
	 * @param newNome il nuovo nome da impostare
	 * @throws ParametroException newnome = null oppure newNome.isBlank = true 
	 */
	
	public void setNome(String newNome) throws ParametroException {
		if(newNome == null || newNome.isBlank()) {
			throw new exceptions.ParametroException("nome non valido");
		}
		this.nome = newNome;
	}
	
	/**
	 * Aggiunge un articolo alla lista
	 * 
	 * @param <Param> il tipo dell'articolo deve estendere la classe "FormatoArticolo"
	 * @param articolo l'articolo da aggiungere
	 * @throws exceptions.GenericException per permettere alle classi figlie di lanciare eccezioni piu' specifiche
	 */
	public abstract<Param extends FormatoArticolo> void aggiungi(Param articolo) throws exceptions.GenericException;
	
	/**
	 * Restituisce la dimensione della lista
	 * 
	 * @return la dimensione della lista
	 */
	public abstract int size();
	
	/**
	 * indica se l'articolo fornito e' presente in lista
	 * 
	 * @param <Param> il tipo dell'articolo deve estendere la classe <em>"FormatoArticolo"</em>
	 * @param articolo l'articolo da cercare
	 * @return  <strong>true</strong> se presente, <strong>false</strong> altrimenti
	 */
	public abstract <Param extends FormatoArticolo> boolean contains(Param articolo);
	/**
	 * restituisce una copia dell'articolo in posizione <em>"index"</em>
	 * 
	 * @param index la posizione dell'oggetto da restituire
	 * @return l'oggetto in posizione <em>index</em>
	 * @throws CloneNotSupportedException se l'oggetto in lista non supporta <em>Clone</em>
	 * @throws IndexOutOfBoundsException se l'index fornito e' < 0 oppure > lista.size
	 * @throws exceptions.GenericException per permettere alle classi figlie di lanciare eccezioni piu' specifiche
	 */
	public abstract FormatoArticolo getArticolo(int index) throws CloneNotSupportedException,  exceptions.GenericException;
	
	/**
	 * Restituisce tutti gli articoli aventi categoria == str
	 * 
	 * @param str la categoria da cercare
	 * @return la lista di articoli aventi quella categoria
	 * @throws exceptions.GenericException per permettere alle classi figlie di lanciare eccezioni piu' specifiche
	 */
	public abstract ArrayList<? extends FormatoArticolo> ricercaCategoria(String str) throws exceptions.GenericException;
	
	/**
	 * Restituisce tutti gli articoli aventi nome che inizia con "str".
	 * <p>
	 * se str.isBlank == true vengono restituiti tutti gli articoli della lista
	 * 
	 * @param str le iniziali con cui cercare
	 * @return la lista di articoli con nome avente come prefisso "str"
	 * @throws exceptions.GenericException per permettere alle classi figlie di lanciare eccezioni piu' specifiche
	 */
	public abstract ArrayList<? extends FormatoArticolo> ricercaNome(String str) throws exceptions.GenericException;
		
	/**
	 * Elimina dalla lista l'articolo avente come nome "str"(case insensitive)
	 * 
	 * @param str il nome dell'articolo da eliminare
	 * @return <strong>true</strong> se la lista conteneva l'elemento indicato, <strong>false</strong> altrimenti
	 * @throws ParametroException se str == null oppure str.isBlank = true	 
	 */
	public abstract boolean eliminaPerNome(String str) throws ParametroException;

	/**
	 * Elimina dalla lista tutti gli articoli appartenenti alla categoria "nomeCategoria" 
	 * 
	 * @param nomeCategoria la categoria per cui eliminare
	 * @return il numero di elementi eliminati
	 * @throws ParametroException se nomeCategoria = null oppure nomeCategoria.isBlank = true
	 */
	public abstract int eliminaPerCategoria(String nomeCategoria) throws ParametroException;	
	
	/**
	 * Impostan alla categoria di default tutti gli articoli appartenenti alla categoria fornita 
	 * 
	 * @param nomeCategoria la categoria da eliminare
	 * @return il numero di articoli a cui viene eliminata la categoria
	 * @throws ParametroException se nomeCategoria = null oppure nomeCategoria.isBlank = true 
	 */
	
	public abstract int eliminaCategoria(String nomeCategoria) throws ParametroException;
	
	/**
	 * restituisce il costo totale di tutti gli articoli nella lista della spesa (tenendo conto anche
	 * delle quantita')
	 * 
	 * @return il costo totale della lista
	 */
	public abstract float costoTotale();

	/**
	 * Trova tutti gli articoli con un costo maggiore di un certo valore.
 	 *
 	 * @param x il costo di cui gli articoli devono essere maggiori
	 * @return gli articoli con costo maggiore x
	 * @throws ParametroException se x e' un valore negativo o 0
	 */
	public abstract ArrayList<? extends FormatoArticolo> MaggioriDi(float x) throws ParametroException;
	
	/**
	 * Trova tutti gli articoli con un costo inferiore a un certo valore.
 	 *
 	 * @param x il costo di cui gli articoli devono essere minori
	 * @return gli articoli con costo minore  x
	 * @throws ParametroException se x e' un valore negativo o 0
	 */
	public abstract ArrayList<? extends FormatoArticolo> MinoriDi(float x) throws ParametroException;
	
	/**
	 * Trova tutti gli articoli con un costo compreso tra due valori.
 	 *
 	 * @param x il costo di cui gli articoli devono essere maggiori o uguali
 	 * @param y il costo di cui gli articoli devono essere minori o uguali
	 * @return gli articoli con costo compreso tra x e y
	 * @throws ParametroException se x o y sono un valore negativo, valgono 0 oppure se y < x
	 */
	public abstract ArrayList<? extends FormatoArticolo> CompresiTra(float x, float y) throws ParametroException;
	 
	
	/**
	 * Restituisce una rappresentazione a Stringa dell'oggetto
	 * 
	 * @return una stringa rappresentante l'oggetto
	 */
	@Override
	public abstract String toString();
	
	/**
	 * Indica se questo oggetto e' equivalente a quello fornito
	 * 
	 * @param lista l'oggetto che si vuole comparare
	 * @return <strong>true</strong> se l'oggetto coincide, <strong>false</strong> altrimenti 
	 */
	@Override
	public boolean equals(Object lista) {
		if(!(lista instanceof FormatoLista)) {
			return false;
		}
		FormatoLista temp = (FormatoLista) lista;
		if(temp.getNome().equalsIgnoreCase(this.nome)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Aggiunge un gruppo di articoli alla lista
	 * 
	 * @param <Param>  il tipo di oggetto degli articoli deve estendere la classe <em>"FormatoArticolo"</em>
	 * @param lista la collezione di articoli da aggiungere
	 * @throws exceptions.GenericException per permettere alle classi figlie di lanciare eccezioni piu' specifiche
	 */
	public abstract <Param extends FormatoArticolo> void aggiungiAll(Collection<Param> lista)
			throws exceptions.GenericException;
		
	
	
	
	
}
	
	
	



