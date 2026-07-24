package exceptions;

/**
* Eccezione per segnalare l'assenza di una data istanza in una struttura dati
*
* @author Simone Nardella
*/

public class ElementoNonTrovatoException extends GenericException{

	private static final long serialVersionUID = -103886372164196739L;
	public ElementoNonTrovatoException (String msg){
		super( msg );
	}
	public ElementoNonTrovatoException (){
		super("elemento non trovato " );
	}

}
