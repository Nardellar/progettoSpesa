package exceptions;

/**
* Eccezione per segnalare che l'aggiunta dell'oggetto porterebbe ad una ridondanza
*
* @author Simone Nardella
*/
public class RidondanzaException extends GenericException{
	
	private static final long serialVersionUID = 8688829388866535659L;
	public RidondanzaException (String msg){
		super( msg );
	}
	public RidondanzaException (){
		super("elemento ridondante " );
	}
}