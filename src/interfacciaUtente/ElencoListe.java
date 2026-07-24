package interfacciaUtente;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import exceptions.ElementoNonTrovatoException;
import exceptions.ParametroException;
import exceptions.RidondanzaException;
import programma.GestioneListeSpesa.GestioneListe;
import programma.ListaSpesa;

public class ElencoListe extends JPanel implements ActionListener {
	
	private static final long serialVersionUID = 1401204366027121776L;
	private ArrayList<JButton> listaBottoni;
	InterfacciaGraficaDefinitiva IGD;
	
	public  ElencoListe(InterfacciaGraficaDefinitiva IGD) {
		this.IGD = IGD;
		listaBottoni = new ArrayList<>();
		setLayout(new GridLayout(3,2));
	}
	
	public void aggiungiListaUtente() {
		JTextField nomeLista = new JTextField(20);
		JComponent[] inputs =new JComponent []{new JLabel ("Nome della Lista") , nomeLista };
		int result;
		while(true) {
	        result = JOptionPane.showConfirmDialog(null, inputs, "Creazione lista", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
		
	        if (result == JOptionPane.CANCEL_OPTION || result == JOptionPane.CLOSED_OPTION) {
	            return;
	        }
	        
	        if (result == JOptionPane.OK_OPTION){ 
		        try {
					GestioneListe.creaListaSpesa(nomeLista.getText());
					JButton bottone = new JButton(nomeLista.getText());
					listaBottoni.add(bottone);
			        add(bottone);
			        bottone.addActionListener(this);
			        break;

				} catch (ParametroException e) {
					 JOptionPane.showMessageDialog(null, "Il nome della lista non può essere vuoto.", "Errore", JOptionPane.ERROR_MESSAGE);
					 
				} catch (RidondanzaException e) {
		            JOptionPane.showMessageDialog(null, "Esiste gia' una lista con questo nome", "Errore", JOptionPane.ERROR_MESSAGE);
	
				}
	       }
	    }
		updateUI();
	}
	
	public void eliminaLista() {
		JTextField nomeLista = new JTextField(20);
		JComponent[] inputs =new JComponent []{new JLabel ("Nome della Lista") , nomeLista };
		int result;
		while(true) {
	        result = JOptionPane.showConfirmDialog(null, inputs, "Eliminazione lista", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
		
	        if (result == JOptionPane.CANCEL_OPTION || result == JOptionPane.CLOSED_OPTION) {
	            return;
	        }
	        
	        if (result == JOptionPane.OK_OPTION){ 
					try {
						if(GestioneListe.eliminaListaSpesa(nomeLista.getText())) { 
							JButton daEliminare = null;
							for(JButton button : listaBottoni) {
								if(button.getText().equals(nomeLista.getText())) {
									daEliminare = button;
									break;
								}
							}
							remove(daEliminare);
							listaBottoni.remove(daEliminare);
							JOptionPane.showMessageDialog(null, "Eliminazione avvenuta con successo", "Successo", JOptionPane.INFORMATION_MESSAGE);
						}
						else {
							JOptionPane.showMessageDialog(null, "Non esiste tale lista, nessuna eliminazione effettuata", "Messaggio", JOptionPane.INFORMATION_MESSAGE);
						}
						break;

					} 
					catch (ParametroException e) {
						 JOptionPane.showMessageDialog(null, "Il nome della lista non può essere vuoto.", "Errore", JOptionPane.ERROR_MESSAGE);
					} 
			       

				
	       }
	    }
		updateUI();
	}
	
	
	public void caricaListe(ArrayList<ListaSpesa> collezione) {
		listaBottoni.clear();
		removeAll();
		for(ListaSpesa lista : collezione) {
			JButton bottone = new JButton(lista.getNome());

			listaBottoni.add(bottone);
	        bottone.addActionListener(this);

	        add(bottone);
		}
		updateUI();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			ListaSpesa listaSelezionata = GestioneListe.getListaSpesa(((JButton) e.getSource()).getText());
			IGD.rendiInvisibile();
			new FrameLista(listaSelezionata, IGD);
			
			
		} 
		catch (ParametroException | ElementoNonTrovatoException e1) {/*impossibile*/}
	}
	

}
