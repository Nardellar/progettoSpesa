package interfacciaUtente;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import classiAstratte.FormatoArticolo;
import exceptions.ElementoNonTrovatoException;
import exceptions.ParametroException;
import exceptions.RidondanzaException;
import programma.ArticoloSpesa;
import programma.ListaSpesa;
import programma.GestioneListeSpesa.GestioneListe;

public class ContenutoLista extends JPanel {

	private static final long serialVersionUID = -484932934383858865L;
	JTable tabellaArticoli;
	 DefaultTableModel modello;
	 static ArrayList<String> elencoArticoli;
	 TextAreaListe textAreaListe;
	 JLabel labelLista;
	 ListaSpesa lista;

	    public ContenutoLista(ListaSpesa lista) {
	        setLayout(new BorderLayout());
	        this.lista = lista;

	        String[] colonne = {"Nome", "Prezzo", "Categoria", "Quantità"};
	         modello = new DefaultTableModel(colonne, 0) {
	       
				private static final long serialVersionUID = 6603285576045485836L;

				@Override
	        	    public boolean isCellEditable(int row, int column) {
	        	       return false;
	        	    }
	         };
	        
	        elencoArticoli = new ArrayList<>();
	        for(ArticoloSpesa articolo : lista) {
	        Object[] dati = {articolo.getNome(), articolo.getCosto(), articolo.getCategoria(), articolo.getQuantita()};
	        elencoArticoli.add(articolo.getNome());
            modello.addRow(dati);
	        }
	        
	        tabellaArticoli = new JTable(modello);
	        labelLista = new JLabel("Contenuto Lista \"" + lista.getNome() + "\"       Costo totale: " + lista.costoTotale());
	        add(labelLista, BorderLayout.NORTH);
	        add(new JScrollPane(tabellaArticoli), BorderLayout.CENTER);

	        tabellaArticoli.addMouseListener(new MouseAdapter() {
	        	@Override
	            public void mouseClicked(MouseEvent e) {
	                if (e.getClickCount() == 2) {
	                    int row = tabellaArticoli.getSelectedRow();
	                    String nome = (String) tabellaArticoli.getValueAt(row, 0);
	                    
	                    ArticoloSpesa articolo = null;
						try {
							articolo = lista.getArticolo(nome);
						} catch (ParametroException | CloneNotSupportedException e1) {/*impossibile*/}
	                    if (articolo != null) {
	                        apriDialogoModificaArticolo(articolo, row);
	                    }
	                }
	            }
	        });
	    }
	    
	    private void aggiornaCostoTotale() {
	    	labelLista.setText("Contenuto Lista \"" + lista.getNome() + "\"\tCosto totale: " + lista.costoTotale());
	    }

	    private void apriDialogoModificaArticolo(ArticoloSpesa articolo, int row) {
	        JTextField costoArticolo = new JTextField(String.valueOf(articolo.getCosto()), 20);
	        JTextField categoriaArticolo = new JTextField(articolo.getCategoria(), 20);
	        JTextField quantitaArticolo = new JTextField(String.valueOf(articolo.getQuantita()), 20);

	        JComponent[] inputs = new JComponent[] {
	            new JLabel("Prezzo"), costoArticolo,
	            new JLabel("Categoria"), categoriaArticolo,
	            new JLabel("Quantità"), quantitaArticolo
	        };

	        int result = JOptionPane.showConfirmDialog(null, inputs, "Modifica Articolo", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

	        if (result == JOptionPane.OK_OPTION) {
	            try {
	                // Controllo e aggiornamento dei dati
	                float nuovoCosto = Float.parseFloat(costoArticolo.getText());
	                String nuovaCategoria = categoriaArticolo.getText();
	                int nuovaQuantita = Integer.parseInt(quantitaArticolo.getText());

	                
	                if (!ArticoloSpesa.checkCosto(nuovoCosto)) {
	                    throw new ParametroException("Prezzo non valido.");
	                }
	                if (!ArticoloSpesa.checkQuantita(nuovaQuantita)) {
	                    throw new ParametroException("Quantità non valida.");
	                }
	                if (!FormatoArticolo.checkString(nuovaCategoria)) {
	                    nuovaCategoria = ArticoloSpesa.CATEGORIA_DEFAULT;
	                }

	                //aggiorno articolo
	                articolo.setCosto(nuovoCosto);
	                articolo.setCategoria(nuovaCategoria);
	                articolo.setQuantita(nuovaQuantita);
	     
		            try {
		              		GestioneListe.creaCategoria(nuovaCategoria);
		          	}
					 catch (RidondanzaException e) {/*non serve far niente se c'e' gia'*/}

	                //aggiorno tabella
	                modello.setValueAt(nuovoCosto, row, 1);
	                modello.setValueAt(nuovaCategoria, row, 2);
	                modello.setValueAt(nuovaQuantita, row, 3);
	                
	                aggiornaCostoTotale();

	                JOptionPane.showMessageDialog(null, "Modifica effettuata con successo.", "Successo", JOptionPane.INFORMATION_MESSAGE);

	            } catch (ParametroException | NumberFormatException e) {
	                JOptionPane.showMessageDialog(null, "Errore, valori inseriti non validi", "Errore", JOptionPane.ERROR_MESSAGE);
	            }
	        }
	    }
	     
	
	
	
	public void aggiungiArticolo() throws HeadlessException, ParametroException, ElementoNonTrovatoException {
			JTextField nomeArticolo = new JTextField(20);
			JTextField costoArticolo = new JTextField(20);
			JTextField categoriaArticolo = new JTextField(20);
			JTextField quantitaArticolo = new JTextField(20);
			

			JComponent[] inputs =new JComponent []{new JLabel ("Nome") , nomeArticolo,
					new JLabel ("Prezzo") , costoArticolo,
					new JLabel ("Categoria") , categoriaArticolo,
					new JLabel ("Quantita") , quantitaArticolo};
			
			int result;
			String nome = null;
			float costo = 0f;
			int quantita = 0;
			String categoria;
			while(true) {
		        result = JOptionPane.showConfirmDialog(null, inputs, "Creazione Articolo", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
			
		        if (result == JOptionPane.CANCEL_OPTION || result == JOptionPane.CLOSED_OPTION) {
		            return;
		        }
		        
		        if (result == JOptionPane.OK_OPTION){
		        	//controllo nome
		        	nome = nomeArticolo.getText();
		        }
		        	if(!FormatoArticolo.checkString(nome)) {
		        		JOptionPane.showMessageDialog(null, "Il nome dell'articolo non può essere vuoto.", "Errore", JOptionPane.ERROR_MESSAGE);
		        		continue;
					}
		        	if(GestioneListe.containsInLista(nome, lista.getNome())) {
		        		JOptionPane.showMessageDialog(null, "Esiste gia' un articolo con questo nome.", "Errore", JOptionPane.ERROR_MESSAGE);
		        		continue;
		        	}
						
		        	//controllo costo
		        	costo = 0f;
					try {
						costo = Float.valueOf(costoArticolo.getText());
						if(!ArticoloSpesa.checkCosto(costo)) {
			        		JOptionPane.showMessageDialog(null, "Prezzo inserito non valido, costi minori o pari a 0 non sono accettati.", "Errore", JOptionPane.ERROR_MESSAGE);
			        		continue;
						}
					}
					catch (NumberFormatException e) {
		        		JOptionPane.showMessageDialog(null, "Formato dati del prezzo erroneo. Inserire solamente cifre.", "Errore", JOptionPane.ERROR_MESSAGE);
		        		continue;
				    }
					
					//controllo quantita
					if(quantitaArticolo.getText().isBlank()) {
						quantita = ArticoloSpesa.QUANTITA_DEFAULT;
					
					}
					else {
						try {
							quantita = Integer.valueOf(quantitaArticolo.getText());
							if(!ArticoloSpesa.checkQuantita(quantita)) {
				        		JOptionPane.showMessageDialog(null,"Quantita' inserita non valida, quantita' minori di 1 non sono accettate.", "Errore", JOptionPane.ERROR_MESSAGE);
				        		continue;
							}
						}
						catch (NumberFormatException e) {
			        		JOptionPane.showMessageDialog(null,"Formato dati della quantita' erroneo. Inserire solamente numeri interi.", "Errore", JOptionPane.ERROR_MESSAGE);
			        		continue;
						}
					}
				
					//controllo categoria
					categoria = categoriaArticolo.getText();
					if(!FormatoArticolo.checkString(categoria)) {
						categoria = ArticoloSpesa.CATEGORIA_DEFAULT;
					}
					break;    
		    }
			ArticoloSpesa articolo = new ArticoloSpesa(nome, costo, categoria, quantita);
			try {
				GestioneListe.aggiungiArticolo(articolo, lista.getNome());
			} 
			catch (ParametroException | RidondanzaException | ElementoNonTrovatoException e) {
				//puo' accadere solo se la categoria non esiste
				int risposta;
				risposta = JOptionPane.showConfirmDialog(null,"La categoria assegnata all'articolo non esiste. Creare la categoria corrispondente?" , "Aggiunta articolo" , JOptionPane.YES_NO_OPTION);
				if(risposta == JOptionPane.YES_OPTION) {
					try {
						GestioneListe.creaCategoria(categoria);
						GestioneListe.aggiungiArticolo(articolo, lista.getNome());
					} catch (ParametroException | RidondanzaException e1) {/*impossibile*/}
				}
				else {
	        		JOptionPane.showMessageDialog(null, "Creazione articolo annullata", "Annullamento", JOptionPane.INFORMATION_MESSAGE);
	        		return;
				}
				
			
			}
			
            Object[] dati = {articolo.getNome(), articolo.getCosto(), articolo.getCategoria(), articolo.getQuantita()};

            modello.addRow(dati);
            elencoArticoli.add(articolo.getNome());
            aggiornaCostoTotale();
    		JOptionPane.showMessageDialog(null, "Creazione articolo avvenuta con successo", "Successo", JOptionPane.INFORMATION_MESSAGE);
		}
	
	
	
		
		void eliminaArticolo() {
			JTextField nomeArticolo = new JTextField(20);
		
			JComponent[] inputs =new JComponent []{new JLabel ("Nome") , nomeArticolo};
			nomeArticolo.setText(null);
			int result;
			while(true) {
		        result = JOptionPane.showConfirmDialog(null, inputs, "Eliminazione Articolo", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
			
		        if (result == JOptionPane.CANCEL_OPTION || result == JOptionPane.CLOSED_OPTION) {
		            return;
		        }
				if(result == JOptionPane.YES_OPTION) {
		            
		        	try {
						if(!GestioneListe.eliminaArticolo(nomeArticolo.getText(), lista.getNome())) {
							JOptionPane.showMessageDialog(null, "Non esiste un articolo con tale nome. Nessuna eliminazione effettuata.", "Errore", JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						break;
					} catch (ParametroException e) {
						JOptionPane.showMessageDialog(null, "Il nome dell'articolo non può essere vuoto.", "Errore", JOptionPane.ERROR_MESSAGE);
		        		continue;
					} catch (ElementoNonTrovatoException e) {/*impossibile*/}		
				}
			}
			
			
			modello.removeRow(elencoArticoli.indexOf(nomeArticolo.getText()));
			elencoArticoli.remove(nomeArticolo.getText());
			
			aggiornaCostoTotale();
			
    		JOptionPane.showMessageDialog(null, "Eliminazione articolo avvenuta con successo", "Successo", JOptionPane.INFORMATION_MESSAGE);
		}


		public void cercaPerCategoria() {
			JTextField categoriaScelta = new JTextField(20);
			
			JComponent[] inputs =new JComponent []{new JLabel ("Categoria (lasciare vuoto per \"non categorizzato\")") , categoriaScelta};
			int result;
			while(true) {
		        result = JOptionPane.showConfirmDialog(null, inputs, "Ricerca per categoria", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
			
		        if (result == JOptionPane.CANCEL_OPTION || result == JOptionPane.CLOSED_OPTION) {
		            return;
		        }
				if(result == JOptionPane.YES_OPTION) {
					 try {
						 	if(categoriaScelta.getText().isBlank()){
						 		categoriaScelta.setText("non categorizzato");
						 	}
			                ArrayList<ArticoloSpesa> articoliTrovati = lista.ricercaCategoria(categoriaScelta.getText());

			                if (articoliTrovati.isEmpty()) {
			                    JOptionPane.showMessageDialog(null, "Nessun articolo trovato con questa categoria: " + categoriaScelta.getText(), "Risultato", JOptionPane.INFORMATION_MESSAGE);
			                } else {
			                    StringBuilder risultato = new StringBuilder();
			                    for (ArticoloSpesa articolo : articoliTrovati) {
			                        risultato.append(articolo.toString()).append("\n");
			                    }

			                    JTextArea areaRisultato = new JTextArea(risultato.toString());
			                    areaRisultato.setEditable(false);
			                    JScrollPane scrollPane = new JScrollPane(areaRisultato);

			                    JOptionPane.showMessageDialog(null, scrollPane, "Articoli trovati", JOptionPane.INFORMATION_MESSAGE);
			                }
					 } catch (ParametroException e) {/*impossibile*/}
				}
			}
		}


		public void eliminaPerCategoria() {
			
			JTextField categoriaScelta = new JTextField(20);
			
			JComponent[] inputs =new JComponent []{new JLabel ("Categoria (lasciare vuoto per \"non categorizzato\")") , categoriaScelta};
			int result;
			 ArrayList<ArticoloSpesa> daEliminare;
			while(true) {
		        result = JOptionPane.showConfirmDialog(null, inputs, "Eliminazione per categoria", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
			
		        if (result == JOptionPane.CANCEL_OPTION || result == JOptionPane.CLOSED_OPTION) {
		            return;
		        }
				if(result == JOptionPane.YES_OPTION) {
					 try {
						 	if(categoriaScelta.getText().isBlank()){
						 		categoriaScelta.setText("non categorizzato");
						 	}
						   
						   daEliminare = lista.ricercaCategoria(categoriaScelta.getText());
			               int numeroArticoliEliminati = lista.eliminaPerCategoria(categoriaScelta.getText());

			                if (numeroArticoliEliminati == 0) {
			                    JOptionPane.showMessageDialog(null, "Nessun articolo trovato con questa categoria. Nesuna eliminazione effettuata" , "Risultato", JOptionPane.INFORMATION_MESSAGE);
			                    return;
			                } 
			                else {
			                    
			                    JTextArea areaRisultato = new JTextArea("Eliminazione avvenuta con successo, " + numeroArticoliEliminati + " articoli eliminato/i");
			                    areaRisultato.setEditable(false);
			                    JOptionPane.showMessageDialog(null, areaRisultato, "Articoli eliminati", JOptionPane.INFORMATION_MESSAGE);
			                    break;
			                }
					 } catch (ParametroException e) {/*impossibile*/}
					 
				}
			}		
			for(ArticoloSpesa element : daEliminare) {
				modello.removeRow(elencoArticoli.indexOf(element.getNome()));
				elencoArticoli.remove(element.getNome());
			}
			
			 aggiornaCostoTotale();
		}


		public void cercaPerNome() {
			JTextField nomeScelto = new JTextField(20);
			
			JComponent[] inputs =new JComponent []{new JLabel ("Nome") , nomeScelto};
			int result;
			while(true) {
		        result = JOptionPane.showConfirmDialog(null, inputs, "Ricerca per nome", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
			
		        if (result == JOptionPane.CANCEL_OPTION || result == JOptionPane.CLOSED_OPTION) {
		            return;
		        }
				if(result == JOptionPane.YES_OPTION) {
					try {
						ArrayList<ArticoloSpesa> articoliTrovati = lista.ricercaNome(nomeScelto.getText());
						 if (articoliTrovati.isEmpty()) {
			                    JOptionPane.showMessageDialog(null, "Nessun articolo trovato che inizia con questo nome: " + nomeScelto.getText(), "Risultato", JOptionPane.INFORMATION_MESSAGE);
			                } else {
			                    StringBuilder risultato = new StringBuilder();
			                    for (ArticoloSpesa articolo : articoliTrovati) {
			                        risultato.append(articolo.toString()).append("\n");
			                    }

			                    JTextArea areaRisultato = new JTextArea(risultato.toString());
			                    areaRisultato.setEditable(false);
			                    JScrollPane scrollPane = new JScrollPane(areaRisultato);

			                    JOptionPane.showMessageDialog(null, scrollPane, "Articoli trovati", JOptionPane.INFORMATION_MESSAGE);
			                }

					} catch (ParametroException e) {/*impossibile*/}
				}
			}
		}

		public void cercaPerPrezzo() {
		    String[] opzioni = {"Maggiore di x", "Minore di x", "Compreso tra x e y"};
		    int scelta = JOptionPane.showOptionDialog(null, "Seleziona il tipo di ricerca per prezzo:", 
		                                              "Ricerca per prezzo", 
		                                              JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, 
		                                              null, opzioni, opzioni[0]);

		    if (scelta == JOptionPane.CLOSED_OPTION) {
		        return;
		    }

		    try {
		        switch (scelta) {
		          //Maggiore di
		            case 0:
		                String inputMaggiore = JOptionPane.showInputDialog(null, "Inserisci il valore di x:");
		                if (inputMaggiore != null) {
		                    float x = Float.parseFloat(inputMaggiore);
		                    ArrayList<ArticoloSpesa> articoliMaggiore = lista.MaggioriDi(x);

		                    mostraRisultati(articoliMaggiore);
		                }
		                break;
		          //Minore di
		            case 1: 
		                String inputMinore = JOptionPane.showInputDialog(null, "Inserisci il valore di x:");
		                if (inputMinore != null) {
		                    float x = Float.parseFloat(inputMinore);
		                    ArrayList<ArticoloSpesa> articoliMinore = lista.MinoriDi(x);

		                    mostraRisultati(articoliMinore);
		                }
		                break;
		          //Compreso tra x e y
		            case 2:
		                JTextField inputX = new JTextField(5);
		                JTextField inputY = new JTextField(5);

		                JPanel pannelloCompresi = new JPanel();
		                pannelloCompresi.add(new JLabel("maggiore di (x):"));
		                pannelloCompresi.add(inputX);
		                //spazio tra i campi
		                pannelloCompresi.add(Box.createHorizontalStrut(15)); 
		                pannelloCompresi.add(new JLabel("minore di (y):"));
		                pannelloCompresi.add(inputY);

		                int risultatoCompresi = JOptionPane.showConfirmDialog(null, pannelloCompresi, 
		                                                                "Inserisci valori di x e y", 
		                                                                JOptionPane.OK_CANCEL_OPTION);
		                if (risultatoCompresi == JOptionPane.OK_OPTION) {
		                    float x = Float.parseFloat(inputX.getText());
		                    float y = Float.parseFloat(inputY.getText());
		                    ArrayList<ArticoloSpesa> articoliCompresi = lista.CompresiTra(x, y);

		                    mostraRisultati(articoliCompresi);
		                }
		                break;
		        }
		    }
		    //avviene se inserimenti vuoti o lettere
		    catch (NumberFormatException e) {
		        JOptionPane.showMessageDialog(null, "Inserisci un numero valido.", "Errore", JOptionPane.ERROR_MESSAGE);
		    } 
		    //avviene in funzione compresiTra se y < x
		    catch (ParametroException e) {
		        JOptionPane.showMessageDialog(null, "Errore: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
		    }
		}

		private void mostraRisultati(ArrayList<ArticoloSpesa> articoli) {
		    if (articoli.isEmpty()) {
		        JOptionPane.showMessageDialog(null, "Nessun articolo trovato.", "Risultato", JOptionPane.INFORMATION_MESSAGE);
		    } else {
		        StringBuilder risultato = new StringBuilder();
		        for (ArticoloSpesa articolo : articoli) {
		            risultato.append(articolo.toString()).append("\n");
		        }

		        JTextArea areaRisultato = new JTextArea(risultato.toString());
		        areaRisultato.setEditable(false);
		        JScrollPane scrollPane = new JScrollPane(areaRisultato);

		        JOptionPane.showMessageDialog(null, scrollPane, "Articoli trovati", JOptionPane.INFORMATION_MESSAGE);
		    }
		}

}




