package exceptions;

/**
* Eccezione per segnalare un errore nei parametri forniti, che sia a causa del loro formato o del loro valore
*
* @author Simone Nardella
*/

public class ParametroException extends GenericException{

	private static final long serialVersionUID = -2496307028514010189L;
	public ParametroException (String msg){
		super( msg );
	}
	public ParametroException (){
		super("ParametroException " );
	}
}