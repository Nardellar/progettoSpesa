package interfacciaUtente;

import java.awt.*;
import javax.swing.*;


public class InterfacciaGraficaDefinitiva extends JFrame {
	
	private static final long serialVersionUID = 5871454238366453743L;
	public ElencoListe elencoListe = new ElencoListe(this);
	public TextAreaListe textAreaListe = new TextAreaListe(elencoListe);

	public BarraStrumenti barraStrumenti= new BarraStrumenti(elencoListe, textAreaListe);



	
	public InterfacciaGraficaDefinitiva() {
		super("Home");
		
		setLayout(new BorderLayout());
		
		
		add(barraStrumenti, BorderLayout.WEST);
		
		
		add(textAreaListe, BorderLayout.CENTER);
		
		setSize(800,500);
		
		setLocationRelativeTo(null);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setVisible(true);		
	}
	
	
	public void rendiInvisibile() {
		setVisible(false);
		
	}
	public void rendiVisibile() {
		setVisible(true);
		
	}
	public void aggiornaCategorie() {
		textAreaListe.aggiornaCategorie();
	}
}
