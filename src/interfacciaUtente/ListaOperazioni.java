package interfacciaUtente;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JButton;
import javax.swing.JPanel;

import exceptions.ElementoNonTrovatoException;
import exceptions.ParametroException;
import programma.ListaSpesa;

public class ListaOperazioni extends JPanel {
	

	private static final long serialVersionUID = 1209199453561859280L;
	JButton bottone1;
	JButton bottone2;
	JButton bottone3;
	JButton bottone4;
	JButton bottone5;
	JButton bottone6;
	JButton bottone7;
	JButton bottone8;


	
	ListaOperazioni(ListaSpesa lista, ContenutoLista contenutoLista){
		
		setPreferredSize(new Dimension(1000, 50));
        Dimension bottoneDimensione = new Dimension(120, 30);
    

		bottone1 = new JButton("Aggiungi nuovo articolo");
		bottone1.setMaximumSize(bottoneDimensione);
	 	bottone1.setMinimumSize(bottoneDimensione);
	 	bottone1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					contenutoLista.aggiungiArticolo();
				} catch (HeadlessException | ParametroException | ElementoNonTrovatoException e1) {/*impossibile*/}

			}
	 		
	 	});
	 	
	 	bottone2 = new JButton("Cerca per categoria");
		bottone2.setAlignmentX(CENTER_ALIGNMENT);
		bottone2.setMaximumSize(bottoneDimensione);
	 	bottone2.setMinimumSize(bottoneDimensione);
	 	bottone2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				contenutoLista.cercaPerCategoria();

			}
	 	});
	 		
	 	

	 	
	 	bottone3 = new JButton("Cerca per nome");
		bottone3.setAlignmentX(CENTER_ALIGNMENT);
		bottone3.setMaximumSize(bottoneDimensione);
	 	bottone3.setMinimumSize(bottoneDimensione);
	 	bottone3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				contenutoLista.cercaPerNome();

			}
	 	});
	 	
	 	bottone4 = new JButton("Cerca per prezzo");
		bottone4.setAlignmentX(CENTER_ALIGNMENT);
		bottone4.setMaximumSize(bottoneDimensione);
	 	bottone4.setMinimumSize(bottoneDimensione);
	 	bottone4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				contenutoLista.cercaPerPrezzo();

			}
	 	});
	 	
	 	bottone5 = new JButton("Elimina per categoria");
		bottone5.setAlignmentX(CENTER_ALIGNMENT);
		bottone5.setMaximumSize(bottoneDimensione);
	 	bottone5.setMinimumSize(bottoneDimensione);
	 	bottone5.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				contenutoLista.eliminaPerCategoria();

			}
	 	});
	 
	 	bottone6 = new JButton("Elimina articolo");
		bottone6.setAlignmentX(CENTER_ALIGNMENT);
		bottone6.setMaximumSize(bottoneDimensione);
	 	bottone6.setMinimumSize(bottoneDimensione);
	 	bottone6.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed(ActionEvent e) {
				contenutoLista.eliminaArticolo();

			}
	 	});
	 	
		setLayout(new FlowLayout(FlowLayout.LEADING));
		add(bottone1);
		add(bottone2);
		add(bottone3);
		add(bottone4);
		add(bottone5);
		add(bottone6);
	}
}

