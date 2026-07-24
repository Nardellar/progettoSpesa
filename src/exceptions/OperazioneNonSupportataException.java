package exceptions;

/**
* Eccezione per segnalare che una certo metodo non e' supportato dall'implementazione corrente
*
* @author Simone Nardella
*/
public class OperazioneNonSupportataException extends GenericException{
	
	private static final long serialVersionUID = 5196303505896248678L;
	public OperazioneNonSupportataException (String msg){
		super( msg );
	}
	public OperazioneNonSupportataException (){
		super("OperazioneNonSupportataException " );
	}
}
