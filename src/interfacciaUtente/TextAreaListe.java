package interfacciaUtente;

import javax.swing.*;

import programma.GestioneListeSpesa.GestioneListe;

import java.awt.*;


public class TextAreaListe extends JPanel {
	
	private static final long serialVersionUID = 9184646004028095220L;
	public JTextField categorie = new JTextField();

	public TextAreaListe(ElencoListe elencoListe) {
		JTextField titolo = new JTextField("Liste");
		titolo.setHorizontalAlignment(JTextField.CENTER);
		titolo.setPreferredSize(new Dimension(40,40));
		titolo.setEditable(false);
		
		
		aggiornaCategorie();
		categorie.setPreferredSize(new Dimension(40,40));
		categorie.setEditable(false);
		setLayout(new BorderLayout());
		add(titolo, BorderLayout.PAGE_START);
		add(elencoListe, BorderLayout.CENTER);
		add(categorie, BorderLayout.PAGE_END);
	}
	
	public void aggiornaCategorie() {
		categorie.setText(stampaCategorie());
	}
	
	public String stampaCategorie() {
		StringBuilder risultato = new StringBuilder();
		
		//questo if e' necessario, altrimenti il "risultato.setCharAt" da' errore poiche' lenght di 0 - 1 = errore
		if(GestioneListe.getCategorie().size()==0) {
			risultato.append("nessuna categoria esistente\n");
			return risultato.toString();
		}
		risultato.append("Categorie esistenti: ");
		
		for(String element : GestioneListe.getCategorie()) {
			risultato.append(" " + element + ",");
		}
		
		risultato.setCharAt(risultato.length()-1, '.');
		return risultato.toString();
	}
}



