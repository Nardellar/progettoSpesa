package testProgramma;
import programma.ListaSpesa; 

import programma.ArticoloSpesa;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;


import java.util.ArrayList;

import java.util.LinkedList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import classiAstratte.FormatoArticolo;
import exceptions.ParametroException;
import exceptions.RidondanzaException;

class ListaSpesaTest {
	
	ListaSpesa lista;
	@BeforeEach
	void inizializzazione() throws ParametroException {
		 lista = new ListaSpesa("giovanni");		
	}

	@Test
	void costuttore() throws ParametroException {
		ListaSpesa prova = new ListaSpesa("   pippo    ");
		assertEquals(prova.getNome(), "pippo");
	}
	@Test
	void aggiungi() {
		Assertions.assertDoesNotThrow (()->
		{
			ArticoloSpesa prova1= new ArticoloSpesa("albero", 10, "lalala", 1);
			ArticoloSpesa prova2= new ArticoloSpesa("alto", 17.74f, "mobile", 1);
			lista.aggiungi(prova1);
			lista.aggiungi(prova2);
			assertEquals(2,lista.size());
			assertEquals(prova1,lista.getArticolo(0));
			assertEquals(prova2,lista.getArticolo(1));
		});
	}
	@SuppressWarnings("unused")
	@Test
	void aggiungiOggettoDiverso() {
		ParametroException pe = Assertions.assertThrows(exceptions.ParametroException.class, ()->
		{
			@SuppressWarnings("serial")
			class ArticoloDiverso extends classiAstratte.FormatoArticolo{

				public ArticoloDiverso(String nome, float costo, String categoria, int quantita) throws ParametroException{
						super(nome,costo,categoria,quantita);
				}
				public ArticoloDiverso(String nome, float costo) throws ParametroException {
					this(nome, costo, CATEGORIA_DEFAULT, QUANTITA_DEFAULT);
				}
				@Override
				public FormatoArticolo myClone() throws ParametroException {
					return null;
				}	
			}
			ArticoloDiverso diverso = new ArticoloDiverso("cioa",27);
			lista.aggiungi(diverso);
		});
	}
	@Test
	void aggiungiGiaPresente() {
		RidondanzaException re = Assertions.assertThrows(exceptions.RidondanzaException.class, ()->
		{
			ArticoloSpesa prova1= new ArticoloSpesa("albero", 10, "lalala", 1);
			ArticoloSpesa prova2= new ArticoloSpesa("alto", 10, "mobile", 1);
			lista.aggiungi(prova1);
			lista.aggiungi(prova2);
			ArticoloSpesa copia2= new ArticoloSpesa("AlTo", 10, "mobile", 1);
			lista.aggiungi(copia2);
		});
		assertEquals("articolo gia' presente in lista", re.getMessage());
	}
	
	@Test
	void aggiungiAll() throws ParametroException, RidondanzaException {
		ArticoloSpesa prova1= new ArticoloSpesa("oioli", 10, "lavandino", 1);
		ArticoloSpesa prova2= new ArticoloSpesa("comodino", 27.02f, "mobile", 2);
		ArticoloSpesa prova3= new ArticoloSpesa("letto", 195.99f, "mobile", 1);
		ArticoloSpesa prova4= new ArticoloSpesa("madame de pumpadour", 12f, "libri", 1);
		LinkedList<ArticoloSpesa> gianni = new LinkedList<ArticoloSpesa>();
		
		gianni.add(prova1);
		gianni.add(prova2);
		gianni.add(prova3);
		gianni.add(prova4);
		lista.aggiungiAll(gianni);
		
		assertTrue(lista.contains(prova1));
		assertTrue(lista.contains(prova2));
		assertTrue(lista.contains(prova3));
		assertTrue(lista.contains(prova4));
		
		
		Assertions.assertThrows(RidondanzaException.class, ()->
		{
			LinkedList<FormatoArticolo> sbagliato = new LinkedList<>();
			sbagliato.add(prova1);
			sbagliato.add(prova2);
			sbagliato.add(prova3);
			sbagliato.add(prova1);
			ListaSpesa prova = new ListaSpesa("prova");
			prova.aggiungiAll(sbagliato);
		});
	}
	
	
	
	
	@SuppressWarnings("unused")
	@Test
	void ricercaCategoria() {
		Assertions.assertDoesNotThrow (()->
		{
			ArticoloSpesa prova1= new ArticoloSpesa("albero", 10, "lalala", 1);
			ArticoloSpesa prova2= new ArticoloSpesa("comodino", 10, "mobile", 1);
			ArticoloSpesa prova3= new ArticoloSpesa("letto", 195.99f, "mobile", 1);
			ArticoloSpesa prova4= new ArticoloSpesa("mela", 2.65f, "frutta", 6);

			lista.aggiungi(prova1);
			lista.aggiungi(prova2);
			lista.aggiungi(prova3);
			lista.aggiungi(prova4);
			
			ArrayList<ArticoloSpesa> risultato = lista.ricercaCategoria("mobile");
			assertEquals(2, risultato.size());
			assertTrue(risultato.contains(prova2));
			assertTrue(risultato.contains(prova3));
			assertFalse(risultato.contains(prova1));
			assertFalse(risultato.contains(prova4));
			//controllo che le modifiche non si trasmettano:
			risultato.get(0).setCategoria("sono cambiata!");
			assertTrue(risultato.get(0).equals(prova2));
			assertEquals("mobile",lista.getArticolo(1).getCategoria());
		});
		ParametroException pe = Assertions.assertThrows(exceptions.ParametroException.class, ()->
		{
			ArrayList<ArticoloSpesa> risultatoSbagliato = lista.ricercaCategoria(null);
			
		});
	}
		
		
		@SuppressWarnings("unused")
		@Test
		void ricercaNome() {
			Assertions.assertDoesNotThrow (()->
			{
				ArticoloSpesa prova1= new ArticoloSpesa("oioli", 10, "lavandino", 1);
				ArticoloSpesa prova2= new ArticoloSpesa("comodino", 10, "mobile", 1);
				ArticoloSpesa prova3= new ArticoloSpesa("letto", 195.99f, "mobile", 1);
				ArticoloSpesa prova4= new ArticoloSpesa("mela", 2.65f, "frutta", 6);
				ArticoloSpesa prova5= new ArticoloSpesa("letame", 999f, "placeLAholder", 1);

				
				lista.aggiungi(prova1);
				lista.aggiungi(prova2);
				lista.aggiungi(prova3);
				lista.aggiungi(prova4);
				lista.aggiungi(prova5);
				
				
				ArrayList<ArticoloSpesa> risultato = lista.ricercaNome("let");
				assertEquals(2, risultato.size());
				assertTrue(risultato.contains(prova3));
				assertTrue(risultato.contains(prova5));					
			});
			
			ParametroException pe = Assertions.assertThrows(exceptions.ParametroException.class, ()->
			{
				ArrayList<ArticoloSpesa> risultatoSbagliato = lista.ricercaNome(null);
				
			});
		}
		
		@Test
		void eliminaPerNome() {
			Assertions.assertDoesNotThrow (()->
			{
				ArticoloSpesa prova1= new ArticoloSpesa("oioli", 10, "lavandino", 1);
				ArticoloSpesa prova2= new ArticoloSpesa("comodino", 10, "mobile", 1);
				ArticoloSpesa prova3= new ArticoloSpesa("letto", 195.99f, "mobile", 1);
				ArticoloSpesa prova4= new ArticoloSpesa("mela", 2.65f, "frutta", 6);
				ArticoloSpesa prova5= new ArticoloSpesa("letame", 999f, "placeLAholder", 1);

	
				lista.aggiungi(prova1);
				lista.aggiungi(prova2);
				lista.aggiungi(prova3);
				lista.aggiungi(prova4);
				lista.aggiungi(prova5);
				
				
				assertTrue(lista.eliminaPerNome("letto"));
				assertEquals(4, lista.size());
				assertFalse(lista.contains(prova3));
				
				assertFalse(lista.eliminaPerNome("elefanteTandemPsichicoDaGuerra"));

			});
		}
		
		@SuppressWarnings("unused")
		@Test
		void eliminaPerCategoria() {
			Assertions.assertDoesNotThrow(()->
			{
				ArticoloSpesa prova1= new ArticoloSpesa("oioli", 10, "lavandino", 1);
				ArticoloSpesa prova2= new ArticoloSpesa("comodino", 10, "mobile", 1);
				ArticoloSpesa prova3= new ArticoloSpesa("letto", 195.99f, "mobile", 1);
				ArticoloSpesa prova4= new ArticoloSpesa("mela", 2.65f, "frutta", 6);
				ArticoloSpesa prova5= new ArticoloSpesa("letame", 999f, "placeLAholder", 1);

	
				lista.aggiungi(prova1);
				lista.aggiungi(prova2);
				lista.aggiungi(prova3);
				lista.aggiungi(prova4);
				lista.aggiungi(prova5);
				
				
				assertEquals(2,lista.eliminaPerCategoria("mobile"));
				assertEquals(3, lista.size());
				assertFalse(lista.contains(prova2));
				assertFalse(lista.contains(prova3));
			});
			
			ParametroException pe = Assertions.assertThrows(exceptions.ParametroException.class, ()->
			{
				lista.eliminaPerCategoria("    ");
			});
		}
		
		@SuppressWarnings("unused")
		@Test
		void eliminaCategoria() {
			Assertions.assertDoesNotThrow(()->
			{
				ArticoloSpesa prova1= new ArticoloSpesa("oioli", 10, "lavandino", 1);
				ArticoloSpesa prova2= new ArticoloSpesa("comodino", 10, "mobile", 1);
				ArticoloSpesa prova3= new ArticoloSpesa("letto", 195.99f, "mobile", 1);
				ArticoloSpesa prova4= new ArticoloSpesa("mela", 2.65f, "frutta", 6);
				ArticoloSpesa prova5= new ArticoloSpesa("letame", 999f, "placeLAholder", 1);

				
				lista.aggiungi(prova1);
				lista.aggiungi(prova2);
				lista.aggiungi(prova3);
				lista.aggiungi(prova4);
				lista.aggiungi(prova5);
				
				
				assertEquals(2,lista.eliminaCategoria("mobile"));
				assertEquals(5, lista.size());
				assertEquals(ArticoloSpesa.CATEGORIA_DEFAULT, prova2.getCategoria());
				assertEquals(ArticoloSpesa.CATEGORIA_DEFAULT, prova3.getCategoria());
			});
			
			ParametroException pe = Assertions.assertThrows(exceptions.ParametroException.class, ()->
			{
				lista.eliminaPerCategoria("    ");
			});
		}
		
		@Test
		void costoTotale() {
			Assertions.assertDoesNotThrow(()->
			{
				ArticoloSpesa prova1= new ArticoloSpesa("oioli", 10, "lavandino", 1);
				ArticoloSpesa prova2= new ArticoloSpesa("comodino", 27.02f, "mobile", 2);
				ArticoloSpesa prova3= new ArticoloSpesa("letto", 195.99f, "mobile", 1);
				ArticoloSpesa prova4= new ArticoloSpesa("mela", 2.65f, "frutta", 6);
				
	
				lista.aggiungi(prova1);
				lista.aggiungi(prova2);
				lista.aggiungi(prova3);
				lista.aggiungi(prova4);
				
				
				/*il terzo parametro e' il delta, e' necessario per specificare fino a che cifra
				 * debbano essere uguali i due valori
				 */
				assertEquals(275.93f, lista.costoTotale(), 0.01);
			});
		}
		
		@Test
		void MaggioriDi() {
			Assertions.assertDoesNotThrow(()->
			{
				ArticoloSpesa prova1= new ArticoloSpesa("oioli", 10, "lavandino", 1);
				ArticoloSpesa prova2= new ArticoloSpesa("comodino", 27.02f, "mobile", 2);
				ArticoloSpesa prova3= new ArticoloSpesa("letto", 195.99f, "mobile", 1);
				ArticoloSpesa prova4= new ArticoloSpesa("mela", 2.65f, "frutta", 6);
				
	
				lista.aggiungi(prova1);
				lista.aggiungi(prova2);
				lista.aggiungi(prova3);
				lista.aggiungi(prova4);
				
				ArrayList<ArticoloSpesa> risultato = lista.MaggioriDi(27.0111111111f);

				assertEquals(2,risultato.size());
				assertTrue(risultato.contains(prova2));
				assertTrue(risultato.contains(prova3));  
			});
			ParametroException pe = Assertions.assertThrows(exceptions.ParametroException.class, ()->
			{
				lista.MaggioriDi(-45.6f);
			});
			assertEquals("valore x negativo",pe.getMessage());
		}
		
		@Test
		void MInoriDi() {
			Assertions.assertDoesNotThrow(()->
			{
				ArticoloSpesa prova1= new ArticoloSpesa("oioli", 10, "lavandino", 1);
				ArticoloSpesa prova2= new ArticoloSpesa("comodino", 27.02f, "mobile", 2);
				ArticoloSpesa prova3= new ArticoloSpesa("letto", 195.99f, "mobile", 1);
				ArticoloSpesa prova4= new ArticoloSpesa("mela", 2.65f, "frutta", 6);
				
				lista.aggiungi(prova1);
				lista.aggiungi(prova2);
				lista.aggiungi(prova3);
				lista.aggiungi(prova4);
				
				ArrayList<ArticoloSpesa> risultato = lista.MinoriDi(27.0111111111f);

				assertEquals(2,risultato.size());
				assertTrue(risultato.contains(prova1));
				assertTrue(risultato.contains(prova4));  
			});
			
			ParametroException pe = Assertions.assertThrows(exceptions.ParametroException.class, ()->
			{
				lista.MinoriDi(-1.6f);
			});
			assertEquals("valore x negativo o 0",pe.getMessage());
		}
		
		@Test
		void CompresiTra() {
			Assertions.assertDoesNotThrow(()->
			{
				ArticoloSpesa prova1= new ArticoloSpesa("oioli", 10, "lavandino", 1);
				ArticoloSpesa prova2= new ArticoloSpesa("comodino", 27.02f, "mobile", 2);
				ArticoloSpesa prova3= new ArticoloSpesa("letto", 195.99f, "mobile", 1);
				ArticoloSpesa prova4= new ArticoloSpesa("mela", 2.65f, "frutta", 6);
				
				lista.aggiungi(prova1);
				lista.aggiungi(prova2);
				lista.aggiungi(prova3);
				lista.aggiungi(prova4);
				
				ArrayList<ArticoloSpesa> risultato = lista.CompresiTra(27.0111111111f, 190f);

				assertEquals(1,risultato.size());
				assertTrue(risultato.contains(prova2));
			});
			
			ParametroException pe = Assertions.assertThrows(exceptions.ParametroException.class, ()->
			{
				lista.CompresiTra(-27.0111111111f, 190f);
			});
			assertEquals("valore x negativo",pe.getMessage());
			
			ParametroException pe2 = Assertions.assertThrows(exceptions.ParametroException.class, ()->
			{
				lista.CompresiTra(27.0111111111f, -190f);
			});
			assertEquals("valore y negativo",pe2.getMessage());
			
			ParametroException pe3 = Assertions.assertThrows(exceptions.ParametroException.class, ()->
			{
				lista.CompresiTra( 190f, 27.0111111111f);
			});
			assertEquals("valore x maggiore di valore y",pe3.getMessage());
		}
		
		@Test 
		void iterator() {
			Assertions.assertDoesNotThrow(()->
			{
				ArticoloSpesa prova1= new ArticoloSpesa("oioli", 10, "lavandino", 1);
				ArticoloSpesa prova2= new ArticoloSpesa("comodino", 27.02f, "mobile", 2);
				ArticoloSpesa prova3= new ArticoloSpesa("letto", 195.99f, "mobile", 1);
				ArticoloSpesa prova4= new ArticoloSpesa("mela", 2.65f, "frutta", 6);
				
				lista.aggiungi(prova1);
				lista.aggiungi(prova2);
				lista.aggiungi(prova3);
				lista.aggiungi(prova4);
				
				ArrayList<ArticoloSpesa> risultato = new ArrayList<ArticoloSpesa>();

				for(ArticoloSpesa element : lista) {
					risultato.add(element);
				}
				assertEquals(4, risultato.size());
				assertEquals(prova1, risultato.get(0));
				assertEquals(prova2, risultato.get(1));
				assertEquals(prova3, risultato.get(2));
				assertEquals(prova4, risultato.get(3));
			});
		}
		
		@Test 
		void toStringTest() {
			Assertions.assertDoesNotThrow(()->
			{
				ArticoloSpesa prova1= new ArticoloSpesa("oioli", 10, "lavandino", 1);
				ArticoloSpesa prova2= new ArticoloSpesa("comodino", 27.02f, "mobile", 2);
				ArticoloSpesa prova3= new ArticoloSpesa("letto", 195.99f, "mobile", 1);
				
				lista.aggiungi(prova1);
				lista.aggiungi(prova2);
				lista.aggiungi(prova3);
				
				
				assertEquals("Lista: giovanni\n" + "Articoli contenuti:\n" + prova1.toString() + "\n" + prova2.toString() + "\n" + prova3.toString() + "\n", lista.toString());
			});
		}	
		
		
		
		
}
				





