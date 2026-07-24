package exceptions;

/**
* Eccezione Generica che contiene piu' eccezioni specifiche. Serve alle classi astratte per permettere ai loro figli di lanciare eccezioni piu' specifiche
*
* @author Simone Nardella
*/
public class GenericException extends Exception{
	
	private static final long serialVersionUID = 150330376002775608L;
	public GenericException (String msg){
		super( msg );
	}
	public GenericException (){
		super("GenericException ");
	}
	
}
