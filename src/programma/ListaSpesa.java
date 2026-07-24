/*Gli articoli possono essere rimossi da una lista. 
Deve essere possibile data una stringa e una lista della spesa trovare la lista degli articoli che
hanno la stringa come prefisso.
Per una lista deve essere possibile calcolare il costo totale degli articoli (considerando costo e quantità degli
articoli contenuti) e ritornare data una categoria gli articoli presenti nella lista che hanno associata quella
categoria.
Le liste devono essere iterabili per permettere ulteriori elaborazioni da parte del codice cliente.
*/

package programma;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import classiAstratte.FormatoArticolo;
import classiAstratte.FormatoLista;
import exceptions.ParametroException;
import exceptions.RidondanzaException;

/**
* Versione lista di ArticoliSpesa. Implementa tutti i metodi della classe astratta.
* 
* @author Simone Nardella
*/
public class ListaSpesa extends FormatoLista implements Iterable<ArticoloSpesa>{
	
	private static final long serialVersionUID = 8549379011558757467L;
	private ArrayList <ArticoloSpesa> lista;
	
	
	
	/**
	 * Costruttore della classe.
	 * 
	 * @param nome il nome da dare alla lista
	 * @throws ParametroException se nome == null oppure nome.isBlank = true
	 */
	public ListaSpesa(String nome) throws ParametroException {
		super(nome);
		lista = new ArrayList <ArticoloSpesa>();
	}
	/**
	 * @throws ParametroException se <em>articolo</em> non e' di tipo <em>"ArticoloSpesa"</em>
	 * @throws RidondanzaException se <em>articolo</em> e' gia' presente in lista
	 */
	@Override
	public <Param extends FormatoArticolo> void aggiungi(Param articolo) throws exceptions.ParametroException, RidondanzaException {
		if(articolo.getClass() != ArticoloSpesa.class) {
			throw new exceptions.ParametroException();
		}
		if(lista.contains(articolo)) {
			throw new exceptions.RidondanzaException("articolo gia' presente in lista");
		}
		lista.add((ArticoloSpesa) articolo);
	}
	
	/**
	 * @throws ParametroException se uno o piu' elementi della collezione non sono di tipo <em>ArticoloSpesa</em> o sono == null
	 * @throws RidondanzaException se uno o piu' articoli della collezione e' gia' presente in lista oppure se la collezione ha elementi ridondanti 
	 */
	
	@Override
	public <Param extends FormatoArticolo> void aggiungiAll(Collection<Param> articoliDaAggiungere) throws ParametroException, RidondanzaException {
		
		//controllo che tutti gli elementi siano ArticoloSpesa
		ArrayList<ArticoloSpesa> articoliDaAggiungereCastati = new ArrayList<>();
		for(Object element : articoliDaAggiungere) {
			if(element.getClass() != ArticoloSpesa.class || element == null) {
				throw new exceptions.ParametroException("Tipologia oggetto errata");
			}
			articoliDaAggiungereCastati.add((ArticoloSpesa) element);
		}
		
		for(ArticoloSpesa element : articoliDaAggiungereCastati) {
			if(lista.contains(element)) {
				/*il removeAll e' necessario perche' se viene trovata una ridondanza alla ennesima
				 * iterazione, la funzione non puo' solo interrompersi, deve anche togliere
				 * gli elementi aggiunti precedentemente
				 */
				lista.removeAll(articoliDaAggiungereCastati);
				throw new RidondanzaException("elemento gia' presente in lista");
			}
			else {
				lista.add(element);
			}
		}
	}
	
	@Override
	public int size() {
		return lista.size();
	}
	
	@Override
	public <Param extends FormatoArticolo> boolean contains(Param articolo) {
		if(lista.contains(articolo)) {
			return true;
		}
		return false;
	}
	
	/**
	 * indica se l'articolo fornito e' presente in lista
	 * 
	 * @param <Param> il tipo dell'articolo deve estendere la classe <em>"FormatoArticolo"</em>
	 * @param nomeArticolo il nome dell'articolo da cercare
	 * @return  <strong>true</strong> se presente, <strong>false</strong> altrimenti
	 */
	public <Param extends FormatoArticolo> boolean contains(String nomeArticolo) {
		for(ArticoloSpesa element : lista) {
			if (element.getNome().equals(nomeArticolo)){
				return true;
			}
		}
		return false;
	}
	
	
	@Override
	public ArticoloSpesa getArticolo(int index) throws CloneNotSupportedException, ParametroException {
		return lista.get(index).myClone();
	}
	
	/**
	 * restiuisce una copia dell'articolo avente nome "nomeArticolo"
	 * 
	 * @param nomeArticolo il nome dell'articolo che si vuole ottenere
	 * @return l'articolo richiesto
	 * @throws CloneNotSupportedException se l'oggetto non supporta l'operazione Clone
	 * @throws ParametroException se vi sono problemi nella creazione della copia
	 */
	public  ArticoloSpesa getArticolo(String nomeArticolo) throws CloneNotSupportedException, ParametroException {
		for(ArticoloSpesa element : lista) {
			if(element.getNome().equals(nomeArticolo)) {
				return element.myClone();
			}
		}
		return null;
	}

	
	
	/**
	 * @throws ParametroException se str == null oppure str.isBlank = true
	 */
	@Override
	public ArrayList<ArticoloSpesa> ricercaCategoria(String str) throws ParametroException {
		ArticoloSpesa.checkString(str);
		ArrayList<ArticoloSpesa> trovati = new ArrayList<ArticoloSpesa>();
		for(ArticoloSpesa elemento : lista) {
			if (elemento.matchCategoria(str)) {
				trovati.add(elemento.myClone());
			}
		}
		return trovati;
	}
	
	/**
	 * @throws ParametroException se str == null
	 */
	
	/* come la rubrica del mio telefono. i nomi vengono sempre cercati partendo dall'inizio
	 * --->startwith 
	 */
	@Override
	public ArrayList<ArticoloSpesa> ricercaNome(String str) throws ParametroException {
		if(str == null) {
			throw new ParametroException();
		}
		ArrayList<ArticoloSpesa> trovati = new ArrayList<ArticoloSpesa>();
		for(ArticoloSpesa elemento : lista) {
			if (elemento.getNome().toLowerCase().startsWith(str.toLowerCase())) {
				trovati.add(elemento);
			}
		}
		return trovati;
	}
	
	/*per eliminare NON sfrutto la ricerca per nome, questa restiuirebbe TUTTI gli articoli che iniziano per una data str
	per l'eliminazione voglio che si sia piu precisi*/
	@Override
	public boolean eliminaPerNome(String nomeArticolo) throws ParametroException {
		if(nomeArticolo.isBlank()) {
			throw new ParametroException();
		}
		ArticoloSpesa daRimuovere = null;
		for(ArticoloSpesa elemento : lista) {
			if (elemento.getNome().equalsIgnoreCase(nomeArticolo)){
				daRimuovere = (elemento);
			}
		}
		return lista.remove(daRimuovere);
			
	}
	
	
	//aggiunta
	public int eliminaPerCategoria(String nomeCategoria) throws ParametroException {
		ArrayList<ArticoloSpesa> daRimuovere = new ArrayList<ArticoloSpesa>();
		for(ArticoloSpesa elemento : lista) {
			if (elemento.matchCategoria(nomeCategoria)){
				daRimuovere.add(elemento); 
			}
		}
		lista.removeAll(daRimuovere);
		return daRimuovere.size();
	}
	
	public int eliminaCategoria(String nomeCategoria) throws ParametroException {
		int n=0;
		for(ArticoloSpesa elemento : lista) {
			if (elemento.matchCategoria(nomeCategoria)){
				elemento.setCategoria(ArticoloSpesa.CATEGORIA_DEFAULT);
				n++;
			}	
		}
		return n;
	}
	
	@Override
	public float costoTotale() {
		float totale = 0;
		for(ArticoloSpesa element : lista) {
			totale += element.getCosto() * element.getQuantita();
		}
		return totale;
	}
	
	@Override
	public ArrayList<ArticoloSpesa> MaggioriDi(float x) throws ParametroException {
		if(x<0) {
			throw new ParametroException("valore x negativo");
		}
		ArrayList<ArticoloSpesa> risultato = new ArrayList<ArticoloSpesa>();
		for(ArticoloSpesa element : lista) {
			if(element.getCosto()> x) {
				risultato.add(element);
			}
		}
		return risultato;
	}
	@Override
	public ArrayList<ArticoloSpesa> MinoriDi(float x) throws ParametroException {
			if(x<=0) {
				throw new ParametroException("valore x negativo o 0");
			}
			ArrayList<ArticoloSpesa> risultato = new ArrayList<ArticoloSpesa>();
			for(ArticoloSpesa element : lista) {
				if(element.getCosto()< x) {
					risultato.add(element);
				}
			}
			return risultato;
	}
	@Override
	public ArrayList<ArticoloSpesa> CompresiTra(float x, float y) throws ParametroException {
			if(x<0) {
				throw new ParametroException("valore x negativo");
			}
			else if(y<0) {
				throw new ParametroException("valore y negativo");
			}
			else if(x > y) {
				throw new ParametroException("valore x maggiore di valore y");

			}
			ArrayList<ArticoloSpesa> risultato = new ArrayList<ArticoloSpesa>();
			for(ArticoloSpesa element : lista) {
				if(element.getCosto()>= x && element.getCosto()<= y) {
					risultato.add(element);
				}
			}
			return risultato;
	}
	
	/**
	 * Restituisce un iteratore ad ArticoliSpesa
	 * 
	 * @return un iteratore della lista.
	 */
	public Iterator<ArticoloSpesa> iterator() {
		 return lista.iterator();
	}
	@Override
	public String toString() {
		 StringBuilder risultato = new StringBuilder("Lista: " + nome + "\nArticoli contenuti:\n" );
		 for(ArticoloSpesa element : lista ) {
			 risultato.append(element.toString()).append("\n");
		 }
		 if(lista.size() == 0) {
			 risultato.append("nessun articolo presente");
		 }
		return risultato.toString();
		
	}
	
    
}