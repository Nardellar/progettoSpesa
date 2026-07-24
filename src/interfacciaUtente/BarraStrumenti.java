package interfacciaUtente;



import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import exceptions.ParametroException;
import exceptions.RidondanzaException;
import programma.GestioneListeSpesa.GestioneListe;

public class BarraStrumenti extends JPanel{
	
	private static final long serialVersionUID = -2885418193992320870L;
	JButton bottone1;
	JButton bottone2;
	JButton bottone3;
	JButton bottone4;
	JButton bottone5;
	JButton bottone6;
	JButton bottone7;


	
	BarraStrumenti(ElencoListe elencoListe, TextAreaListe textAreaListe){
		
		setPreferredSize(new Dimension(300, 100));
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        Dimension bottoneDimensione = new Dimension(200, 40);

        
        
        
          
		
		bottone1 = new JButton("Crea una nuova lista");
		bottone1.setAlignmentX(CENTER_ALIGNMENT);
		bottone1.setMaximumSize(bottoneDimensione);
	 	bottone1.setMinimumSize(bottoneDimensione);
	 	bottone1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				elencoListe.aggiungiListaUtente();				
			}
	 		
	 	});
	 	
	 	bottone2 = new JButton("Elimina lista");
		bottone2.setAlignmentX(CENTER_ALIGNMENT);
		bottone2.setMaximumSize(bottoneDimensione);
	 	bottone2.setMinimumSize(bottoneDimensione);
	 	bottone2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				elencoListe.eliminaLista();				
			}
	 		
	 	});
	 	
		bottone3 = new JButton("Crea categoria");
		bottone3.setAlignmentX(CENTER_ALIGNMENT);
		bottone3.setMaximumSize(bottoneDimensione);
	 	bottone3.setMinimumSize(bottoneDimensione);		
		bottone3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
					JTextField categoria = new JTextField(20);
					JComponent[] inputs =new JComponent []{new JLabel ("Nuova categoria") , categoria };
					int result;
					while(true) {
						result = JOptionPane.showConfirmDialog(null, inputs, "Creazione categoria", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
						
					    if (result == JOptionPane.CANCEL_OPTION || result == JOptionPane.CLOSED_OPTION) {
					    	return;
					    }
					        
					    if (result == JOptionPane.OK_OPTION){
					    	try {
					    		GestioneListe.creaCategoria(categoria.getText());
								textAreaListe.categorie.setText(textAreaListe.stampaCategorie());
								return;
							} 
					    	catch (ParametroException e1) {
								JOptionPane.showMessageDialog(null, "Il nome della categoria non può essere vuoto.", "Errore", JOptionPane.ERROR_MESSAGE);
							} 
					    	catch (RidondanzaException e1) {
								JOptionPane.showMessageDialog(null, "Esiste gia' una categoria con questo nome", "Errore", JOptionPane.ERROR_MESSAGE);
	
							}
					    }
					}
			}
		});
					
				
	 	
	 	
	 	bottone4 = new JButton("Elimina categoria");
		bottone4.setAlignmentX(CENTER_ALIGNMENT);
		bottone4.setMaximumSize(bottoneDimensione);
	 	bottone4.setMinimumSize(bottoneDimensione);	
	 	
	 	bottone4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
					JTextField categoria = new JTextField(20);
					JComponent[] inputs =new JComponent []{new JLabel ("Categoria da eliminare") , categoria };
					int result;
					while(true) {
						result = JOptionPane.showConfirmDialog(null, inputs, "Eliminazione categoria", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
						
					    if (result == JOptionPane.CANCEL_OPTION || result == JOptionPane.CLOSED_OPTION) {
					    	return;
					    }
					        
					    if (result == JOptionPane.OK_OPTION){
					    		try {
									boolean eliminazione = GestioneListe.eliminaCategoria(categoria.getText());
									if(eliminazione) {
										JOptionPane.showMessageDialog(null, "Eliminazione avvenuta con successo", "Successo", JOptionPane.INFORMATION_MESSAGE);
										textAreaListe.categorie.setText(textAreaListe.stampaCategorie());
									}
									else {
										JOptionPane.showMessageDialog(null, "Non esiste tale categoria, nessuna eliminazione effettuata", "Messaggio", JOptionPane.INFORMATION_MESSAGE);
									}
									return;
								} 
					    		catch (ParametroException e1) {
									JOptionPane.showMessageDialog(null, "Il nome della categoria non può essere vuoto.", "Errore", JOptionPane.ERROR_MESSAGE);
								}		
					    }
					}
			}
		});
		
	 	
	 	bottone5 = new JButton("Salva su file");
		bottone5.setAlignmentX(CENTER_ALIGNMENT);
		bottone5.setMaximumSize(bottoneDimensione);
	 	bottone5.setMinimumSize(bottoneDimensione);	
	 	bottone5.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					GestioneListe.serializza();
					JOptionPane.showMessageDialog(null, "Salvataggio avvenuto con successo", "Successo", JOptionPane.INFORMATION_MESSAGE);

				} catch (FileNotFoundException e1) {
					JOptionPane.showMessageDialog(null, "File destinazione non trovato", "Errore", JOptionPane.ERROR_MESSAGE);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "C'e' stato un errore durante il salvataggio", "Errore", JOptionPane.ERROR_MESSAGE);
				}
			}
	 		
	 	});
		
		
		bottone6 = new JButton("Carica da file");
		bottone6.setAlignmentX(CENTER_ALIGNMENT);
		bottone6.setMaximumSize(bottoneDimensione);
	 	bottone6.setMinimumSize(bottoneDimensione);
	 	bottone6.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
					try {
						GestioneListe.deserializza();
						elencoListe.caricaListe(GestioneListe.getListe());
						JOptionPane.showMessageDialog(null, "Caricamento avvenuto con successo", "Successo", JOptionPane.INFORMATION_MESSAGE);
					} catch (ClassNotFoundException | ParametroException | RidondanzaException | IOException e1) {
						JOptionPane.showMessageDialog(null, "C'e' stato un problema durante il caricamento da file", "Errore", JOptionPane.ERROR_MESSAGE);
					}				
			}
	 		
	 	});
		
		
		bottone7 = new JButton("Chiudi Terminale");
		bottone7.setAlignmentX(CENTER_ALIGNMENT);
		bottone7.setMaximumSize(bottoneDimensione);
	 	bottone7.setMinimumSize(bottoneDimensione);	
	 	bottone7.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				 System.exit(0);
			}
	 	});
		
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		add(Box.createVerticalGlue());
		add(bottone1);
		add(Box.createVerticalGlue());
		add(bottone2);
		add(Box.createVerticalGlue());
		add(bottone3);
		add(Box.createVerticalGlue());
		add(bottone4);
		add(Box.createVerticalGlue());
		add(bottone5);
		add(Box.createVerticalGlue());
		add(bottone6);
		add(Box.createVerticalGlue());
		add(bottone7);
		add(Box.createVerticalGlue());

		
		
	}
	

}