/*nel formato generico ho meno limitazioni sui valori, il costo deve poter essere negativo (es per spese o guadagni)
 * la quantita' deve poter essere negativi (es: gestione di un magazzino) 
 * per questo cambio anche i default values*/


package classiAstratte;

import java.io.Serializable;

import exceptions.ParametroException;

/**
* Versione astratta di un articolo. Fornisce tutte le funzioni minime che un articolo deve possedere.-
* alcuni metodi posseggono il "throws exceptions.GenericException" poiche' i figli possano lanciare
* eccezioni diverse dal padre
* 
* @author Simone Nardella
*/

public abstract class FormatoArticolo implements Cloneable, Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5758531929130909642L;
	//protected perche' altrimenti le sottoclassi non potrebbero accedervi
	protected String nome;
	protected String categoria; 
	/**
	 * Valore di  default di categoria
	 */
	public static final String CATEGORIA_DEFAULT = "non categorizzato";
	protected int quantita;
	/**
	 * Valore di  default di quantita'
	 */
	public static final int QUANTITA_DEFAULT = 0; 
	protected float costo;
	
	/**
	 * Controlla che la stringa fornita sia adatta ad essere un nome per un articolo
	 * 
	 * @param nome la stringa su cui applicare i controlli
	 * @return <strong>false</strong> se la stringa fornita e' null oppure nome.isBlank == true, altrimenti restituisce <strong>true</strong> 
	 */
	public static boolean checkString(String str){
		if(str == null || str.isBlank()){
			return false;
		}
		return true;
	}
	
	/**
	 * Costruttore che sfruttano le classi figlie. crea un nuovo articolo
	 * <p>
	 * Ad ogni parametro viene controllata la validita'. Se la categoria fornita non e' valida viene 
	 * impostata al valore di default
	 * 
	 * @param nome il nome che si vuole dare all'articolo
	 * @param costo il costo dell'articolo per singolo pezzo
	 * @param categoria la categoria dell'articolo
	 * @param quantita la quantita dell'articolo
	 * @throws ParametroException se uno dei seguenti parametri non e' valido
	 */
	
	public FormatoArticolo(String nome, float costo, String categoria, int quantita) throws ParametroException {
		if(!checkString(nome)){
			throw new exceptions.ParametroException("nome non valido");
		}
		
		this.nome = nome.trim();
		this.costo = costo;
		
		if(!checkString(categoria)) {
			this.categoria = CATEGORIA_DEFAULT;
		}
		else {
			this.categoria = categoria.trim();
		}
		this.quantita = quantita;
	}
	/**
	 * Costruttore che sfruttano le classi figlie. crea un nuvo articolo
	 * <p>
	 * Ad ogni parametro viene controllata la validita'. Categoria e quantita' sono impostate a default 
	 * 
	 * @param nome il nome che si vuole dare all'articolo
	 * @param costo il costo dell'articolo per singolo pezzo
	 * @throws ParametroException se uno dei seguenti parametri non e' valido
	 */
	public FormatoArticolo(String nome, float costo) throws ParametroException {
		this(nome, costo, CATEGORIA_DEFAULT, QUANTITA_DEFAULT);
	}
	/**
	 * Costruttore che sfruttano le classi figlie. crea un nuvo articolo
	 * <p>
	 * Ad ogni parametro viene controllata la validita'. La quantita' e' impostata a default, se la 
	 * categoria fornita non e' valida viene impostata anch'essa al valore di default 
	 * 
	 * @param nome il nome che si vuole dare all'articolo
	 * @param costo il costo dell'articolo per singolo pezzo
	 * @param categoria la categoria dell'articolo
	 * @throws ParametroException se uno dei seguenti parametri non e' valido
	 */
	public FormatoArticolo(String nome, float costo, String categoria) throws ParametroException {
		this(nome, costo, categoria, QUANTITA_DEFAULT);
	}
	/**
	 * Costruttore che sfruttano le classi figlie. crea un nuvo articolo
	 * <p>
	 * Ad ogni parametro viene controllata la validita'. La categoria e' impostata a default 
	 * 
	 * @param nome il nome che si vuole dare all'articolo
	 * @param costo il costo dell'articolo per singolo pezzo
	 * @param quantita la quantita' della'articolo
	 * @throws ParametroException se uno dei seguenti parametri non e' valido
	 */
	public FormatoArticolo(String nome, float costo, int quantita) throws ParametroException {
		this(nome, costo, CATEGORIA_DEFAULT, quantita);
	}
	/**
	 * Restituisce il nome dell'articolo
	 * 
	 * @return il nome dell'articolo
	 */
	//con gli oggetti immutabili (come stringhe) l'assegnamento NON fa puntare le due variabili alla stessa stringa ma ne crea effettivamente una copia
	public String getNome() {
		return nome; 
	}
	/**
	 * Restituisce il costo dell'articolo per pezzo singolo
	 * 
	 * @return il costo dell'articolo per pezzo singolo
	 */
	public float getCosto() {
		return costo;
	}
	
	/**
	 * Restituisce la categoria dell'articolo
	 * 
	 * @return la categoria dell'articolo
	 */
	public String getCategoria() {
		return categoria;
	}
	
	/**
	 * Restituisce la quantita' dell'articolo
	 * 
	 * @return la quantita' dell'articolo
	 */
	public int getQuantita() {
		return quantita;
	}
	
	/**
	 * Imposta un nuovo nome all'articolo
	 * <p>
	 * La funzione restituisce l'oggetto stesso per permettere di concatenare piu' funzioni
	 * 
	 * @param newNome il nuovo nome che si vuole assegnare
	 * @return l'oggetto stesso
	 * @throws ParametroException se newNome == null oppure newNome.isBlank = true
	 * @throws exceptions.GenericException per permettere alle classi figlie di lanciare eccezioni piu' specifiche
	 */
	public FormatoArticolo setNome(String newNome) throws ParametroException, exceptions.GenericException {
		if(!checkString(newNome)) {
			throw new ParametroException("nome non valido");
		}
		this.nome = newNome.trim();
		return this;
	}
	/**
	 * Imposta una nuova categoria all'articolo
	 * <p>
	 * La funzione restituisce l'oggetto stesso per permettere di concatenare piu' funzioni
	 * 
	 * @param newCategoria la nuova categoria che si vuole assegnare
	 * @return l'oggetto stesso
	 * @throws ParametroException se newCategoria == null oppure newCategoria.isBlank = true
	 * @throws exceptions.GenericException per permettere alle classi figlie di lanciare eccezioni piu' specifiche
	 */
	public FormatoArticolo setCategoria(String newCategoria) throws ParametroException, exceptions.GenericException{
		if (!FormatoArticolo.checkString(newCategoria)) {
			throw new ParametroException("categoria non valida");
		}
		this.categoria = newCategoria.trim();
		return this;
	}
	/**
	 * Imposta un nuovo costo all'articolo
	 * <p>
	 * La funzione restituisce l'oggetto stesso per permettere di concatenare piu' funzioni
	 * 
	 * @param newCosto il nuovo costo da assegnare 
	 * @return l'oggetto stesso
	 * @throws exceptions.GenericException per permettere alle classi figlie di lanciare eccezioni piu' specifiche
	 */
	public FormatoArticolo setCosto(float newCosto) throws exceptions.GenericException {
		this.costo = newCosto;
		return this;
	}
	/**
	 * Imposta una nuova quantita' all'articolo
	 * <p>
	 * La funzione restituisce l'oggetto stesso per permettere di concatenare piu' funzioni
	 * 
	 * @param newQuantita la nuova quantita' che si vuole assegnare
	 * @return l'oggetto stesso
	 * @throws exceptions.GenericException per permettere alle classi figlie di lanciare eccezioni piu' specifiche
	 */
	public FormatoArticolo setQuantita(int newQuantita) throws exceptions.GenericException{
		this.quantita = newQuantita;
		return this;
	}
	/**
	 * Restiuisce una rappresentazione a Stringa dell'oggetto
	 */
	@Override
	public String toString() {
		return ("Nome: " + this.nome + "\tCosto: " + this.costo + "\tCategoria: " + this.categoria + "\tQuantita': " + this.quantita);
	}

	/**
	 * Indica se questo oggetto e' equivalente a quello fornito
	 * 
	 * @param articolo l'oggetto che si vuole comparare
	 * @return <strong>true</strong> se l'oggetto coincide, <strong>false</strong> altrimenti 
	 */
	@Override
	public boolean equals(Object articolo) {
		if(!(articolo instanceof FormatoArticolo)) {
			return false;
		}
		FormatoArticolo temp = (FormatoArticolo) articolo;
		if(temp.getNome().equalsIgnoreCase(this.nome)) {
			return true;
		}
		return false;
	}
	/**
	 * Restituisce una copia dell'oggetto stesso
	 * 
	 * @return una copia dell'oggetto
	 * @throws ParametroException se vi e' un errore nella costruzione del clone
	 */
	abstract public FormatoArticolo myClone() throws ParametroException, exceptions.GenericException;
	
	/**
	 * Controlla validita dell'input e che la stringa fornita coincida con la catogoria dell'articolo ignorando il case
	 * 
	 * @param categoria la stringa da comparare alla categoira dell'articolo
	 * @return <strong>true</strong> se coincidono, <strong>false</strong> altrimenti
	 * @throws ParametroException se categoria == null oppure categoria.isBlank = true
	 */
	public Boolean matchCategoria(String categoria) throws ParametroException {
		if(!checkString(categoria)) {
			throw new ParametroException("categoria non valida");
		}
		if(categoria.trim().equalsIgnoreCase(this.categoria)) {
			return true;
		}
		return false;
	}
	
	
}
	

