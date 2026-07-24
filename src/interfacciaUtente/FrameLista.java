package interfacciaUtente;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import programma.ListaSpesa;

public class FrameLista extends JFrame {
	

	private static final long serialVersionUID = -680850976262535218L;
	ListaOperazioni listaOperazioni;
	ContenutoLista contenutoLista;
	InterfacciaGraficaDefinitiva IGD;
	
	public FrameLista(ListaSpesa lista, InterfacciaGraficaDefinitiva IGD) {
		super("Lista:" + lista.getNome());
		
		setLayout(new BorderLayout());
		contenutoLista = new ContenutoLista(lista);
		listaOperazioni = new ListaOperazioni(lista, contenutoLista);
		this.IGD = IGD;
		
		add(listaOperazioni, BorderLayout.NORTH);
		
		add(contenutoLista, BorderLayout.CENTER);
		
		setSize(900, 500);		
		
		setLocationRelativeTo(null);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                  IGD.rendiVisibile();
                  IGD.aggiornaCategorie();
                }
        });
		
	}
	

}
