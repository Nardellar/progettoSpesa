/*Gli articoli hanno associato un costo e una quantità. L’associazione fra un articolo e una categoria come la 
sua quantità e il suo costo possono essere modificati. Quando inserite un articolo potete non specificare la
categoria e la quantità (ma dovete sempre specificare nome e costo). Se non specificato all'articolo viene
assegnata una categoria predefinita "Non Categorizzati" e quantità 1*/

/*ArticoloSpesa si differenzia dal formato per: quantita' >=1, costo >0;
 */
package programma;

import exceptions.ParametroException;
import classiAstratte.FormatoArticolo;
import exceptions.OperazioneNonSupportataException;

/**
* Versione orientata alla spesa dell'articolo. Si differenzia dal FormatoArticolo per:
* quantita' default = 1
* non puo' avere quantita <1
* non puo' avere prezzo <= 0
* non supporta l'operazione "setNome"
* 
* @author Simone Nardella
*/

public class ArticoloSpesa extends classiAstratte.FormatoArticolo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2250630367534150483L;
	
	/**
	 * Valore di  default di quantita'
	 */
	public static final int QUANTITA_DEFAULT = 1;
	

	/**
	 * Controlla che il valore fornito sia adatto per essere un costo di ArticoloSpesa 
	 * 
	 * @param costo il valore da controllare
	 * @return <strong>true</strong> se il valore e' valido, <strong>false</strong> altrimenti
	 */
	/* 
	 * articolo spesa non puo' avere quantita <1 e prezzo <= 0
	 */
	public static boolean checkCosto(float costo)  {
		
		if(costo <=0) {
			return false;
		}
		
		return true;
	}
	/**
	 * Controlla che la quantita' fornita sia adatta per un ArticoloSpesa
	 * 
	 * @param quantita il valore da controllare
	 * @return <strong>true</strong> se il valore e' valido, <strong>false</strong> altrimentin
	 */
	public static boolean checkQuantita(int quantita) {
		if(quantita < 1) {
			return false;
		}
		
		return true;
		
		
	}
	/**
	 * Costruttore della classe. Crea un nuovo ArticoloSpesa
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
	public ArticoloSpesa(String nome, float costo, String categoria, int quantita) throws ParametroException {
		super(nome, costo, categoria, quantita);
		if(!checkCosto(costo)) {
			throw new exceptions.ParametroException("costo non valido");
		}
		this.costo =  (float) (Math.floor(costo * 100) / 100);
		if(!checkQuantita(quantita)) {
			throw new exceptions.ParametroException("quantita' non valida");
		}
		else {
			this.quantita = quantita;
		}
		
	}
	/**
	 * Costruttore della classe. Crea un nuovo ArticoloSpesa
	 * <p>
	 * Ad ogni parametro viene controllata la validita'. Categoria e quantita' sono impostate ai 
	 * valori di default
	 * 
	 * @param nome il nome che si vuole dare all'articolo
	 * @param costo il costo dell'articolo per singolo pezzo
	 * @throws ParametroException se uno dei seguenti parametri non e' valido
	 */
	public ArticoloSpesa(String nome, float costo) throws ParametroException {
		this(nome, costo, CATEGORIA_DEFAULT, QUANTITA_DEFAULT);
	}
	/**
	 * Costruttore della classe. Crea un nuovo ArticoloSpesa
	 * <p>
	 * Ad ogni parametro viene controllata la validita'. La quantita' e' impostata a default, se la 
	 * categoria fornita non e' valida viene impostata anch'essa al valore di default 
	 * 
	 * @param nome il nome che si vuole dare all'articolo
	 * @param costo il costo dell'articolo per singolo pezzo
	 * @param categoria la categoria dell'articolo
	 * @throws ParametroException se uno dei seguenti parametri non e' valido
	 */
	public ArticoloSpesa(String nome, float costo, String categoria) throws ParametroException {
		this(nome, costo, categoria, QUANTITA_DEFAULT);
	}
	
	/**
	 * Costruttore della classe. Crea un nuovo ArticoloSpesa
	 * <p>
	 * Ad ogni parametro viene controllata la validita'. La categoria e' impostata a default, se la 
	 * quantita' fornita non e' valida viene impostata anch'essa al valore di default 
	 * 
	 * @param nome il nome che si vuole dare all'articolo
	 * @param costo il costo dell'articolo per singolo pezzo
	 * @param quantita la quantita dell'articolo
	 * @throws ParametroException se uno dei seguenti parametri non e' valido
	 */
	public ArticoloSpesa(String nome, float costo, int quantita) throws ParametroException {
		this(nome, costo, CATEGORIA_DEFAULT, quantita);
	}
	/**
	 * ArticoloSpesa non supporta il cambio nome
	 * 
	 * @throws OperazioneNonSupportataException se la funzione e' invocata
	 */
	@Override
	public ArticoloSpesa setNome(String newNome) throws OperazioneNonSupportataException  {
		throw new exceptions.OperazioneNonSupportataException("non e' permesso cambiare nome");
	}
	
	
	@Override
	public ArticoloSpesa setCategoria(String newCategoria) throws ParametroException {
		if(!FormatoArticolo.checkString(newCategoria)) {
			throw new ParametroException("categoria non valida");
		}
		categoria = newCategoria;
		return this;
	}
	
	/**
	 * @throws ParametroException se newQuantita <1
	 */
	@Override
	public ArticoloSpesa setQuantita(int newQuantita) throws ParametroException {
		if(!checkQuantita(newQuantita)) {
			throw new exceptions.ParametroException("nuova quantita' non valida");
		}
		quantita = newQuantita;
		return this;
	}
	
	/**
	 * Imposta un nuovo costo all'articolo. Tutti i valori vengono arrotondati per difetto alla seconda cifra decimale
	 *.
	 * 
	 *  @throws ParametroException se newCosto <= 0
	 */
	@Override
	public ArticoloSpesa setCosto(float newCosto) throws ParametroException {
		if(!checkCosto(newCosto)) {
			throw new exceptions.ParametroException("costo non valido");
		}
		//tronca il costo alla seconda cifra decimale
		this.costo = (float) (Math.floor(newCosto * 100) / 100);
		return this;
	}
	
	@Override
	public ArticoloSpesa myClone() throws ParametroException{
		ArticoloSpesa clone = new ArticoloSpesa(this.nome, this.costo, this.categoria, this.quantita);
		return clone;
	}

	
	
	
	
}
