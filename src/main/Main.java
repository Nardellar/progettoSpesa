package main;

import java.io.FileNotFoundException;
import java.io.IOException;

import exceptions.ElementoNonTrovatoException;
import exceptions.ParametroException;
import exceptions.RidondanzaException;
import interfacciaUtente.InterfacciaGraficaDefinitiva;
import interfacciaUtente.InterfacciaRigaComando;
import jbook.util.Input;

public class Main {

	public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException, ParametroException, RidondanzaException, IOException, ElementoNonTrovatoException {
		
		System.out.println("inserire 1 per l'interfaccia da terminale, 2 per quella grafica");
		boolean a = true;
		int scelta = 0;
		while(a) {
			scelta = Input.readInt();
			switch(scelta) {
			case 1 -> {
				InterfacciaRigaComando.interfaccia();
				a = false;
			}
			
			case 2 -> {
				new InterfacciaGraficaDefinitiva();
				a = false;
			}
			
			default-> System.out.println("comando sbagliato, inserire nuovamente");
			}
		}
		System.out.println("programma terminato con successo");

	}

}
