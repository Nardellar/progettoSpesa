package testProgramma;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import exceptions.ElementoNonTrovatoException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import exceptions.ParametroException;
import exceptions.RidondanzaException;
import programma.ArticoloSpesa;
import programma.GestioneListeSpesa.GestioneListe;

class GestioneListeTest {

	@AfterEach
	void svuotamento() {
		GestioneListe.reset();
	}
	@Test
    void creaListaSpesa(){
		Assertions.assertDoesNotThrow(()->
		{
			GestioneListe.creaListaSpesa("pierino");
			assertEquals(1, GestioneListe.numeroListe());
			assertTrue(GestioneListe.containsLista("pierino"));
		});
		RidondanzaException re = Assertions.assertThrows(RidondanzaException.class, ()->
		{
			GestioneListe.creaListaSpesa("PieRiNo");
		});
		assertEquals("lista gia' presente",re.getMessage());
		
		ParametroException pa = Assertions.assertThrows(ParametroException.class, ()->
		{
			GestioneListe.creaListaSpesa(null);
		});
		assertEquals("lista gia' presente",re.getMessage());
    }

	@Test
    void cambiaNomeListaSpesa() throws ParametroException, RidondanzaException, ElementoNonTrovatoException { 
		
			GestioneListe.creaListaSpesa("pierino");
			GestioneListe.cambiaNomeListaSpesa("PiErInO", "gianpancrazio  ");
			assertEquals(1, GestioneListe.numeroListe());
			assertTrue(GestioneListe.containsLista("  gianpaNCrazio   "));	
		
		exceptions.ParametroException pe = Assertions.assertThrows(exceptions.ParametroException.class, ()->
		{
			GestioneListe.cambiaNomeListaSpesa(" ", "marioooo  ");
		});
		
		assertEquals("stringa fornita non valida",pe.getMessage());
		
		ElementoNonTrovatoException nsee = Assertions.assertThrows(ElementoNonTrovatoException.class, ()->
		{
			GestioneListe.cambiaNomeListaSpesa("caioSempronio ", "marioooo  ");
		});
		
		assertEquals("lista inesistente",nsee.getMessage());
    }
	
	
	@Test
    void eliminaListaSpesa() {
		Assertions.assertDoesNotThrow(()->
		{
			GestioneListe.creaListaSpesa("pierino");
			assertTrue(GestioneListe.eliminaListaSpesa("PIERINO"));
			assertFalse(GestioneListe.eliminaListaSpesa("gianpiero"));
			assertEquals(0, GestioneListe.numeroListe());
		});
		
		exceptions.ParametroException pe = Assertions.assertThrows(exceptions.ParametroException.class, ()->
		{
			GestioneListe.eliminaListaSpesa(null);
		});
		
		assertEquals("stringa fornita non valida",pe.getMessage());
		
	}
	
	
	@Test
	void containsLista() {
		Assertions.assertDoesNotThrow(()->
		{
			GestioneListe.creaListaSpesa("pierino");
			GestioneListe.creaListaSpesa("genoveffo");
			GestioneListe.creaListaSpesa("gianpiero");
			
			assertTrue(GestioneListe.containsLista("  gianpIERO   "));
			assertTrue(GestioneListe.containsLista("GENOVEFFO   "));
			assertFalse(GestioneListe.containsLista("matteo"));
		});
	}
	
	@Test
    void creaCategoria() {
		Assertions.assertDoesNotThrow(()->
		{
			GestioneListe.creaCategoria("lampade");
			assertEquals(1, GestioneListe.numeroCategorie());
			assertTrue(GestioneListe.containsCategoria("lampade"));
		
			RidondanzaException re = Assertions.assertThrows(RidondanzaException.class, ()->
			{
				GestioneListe.creaCategoria("LAMpade");
			});
			assertEquals("categoria ridondante", re.getMessage());
		});
		
    }
	
	@Test
    void eliminaCategoria() { 
		Assertions.assertDoesNotThrow(()->
		{
			GestioneListe.creaCategoria("lampade");
			GestioneListe.creaCategoria("sedie");
			GestioneListe.creaListaSpesa("pierino");
			GestioneListe.creaListaSpesa("caio");
			
			ArticoloSpesa articolo1 = new ArticoloSpesa("lampada", 25.77f, "laMpade", 2);
			ArticoloSpesa articolo2 = new ArticoloSpesa("sedia da gaming", 67f, "sedie", 1);
			ArticoloSpesa articolo3 = new ArticoloSpesa("lampadario", 234.99f, "   LAMPADE", 1);
			ArticoloSpesa articolo4 = new ArticoloSpesa("pianatane", 43.99f, "lampade", 1);

			GestioneListe.aggiungiArticolo(articolo1, "pierino");
			GestioneListe.aggiungiArticolo(articolo2, "pierino");
			GestioneListe.aggiungiArticolo(articolo3, "pierino");
			GestioneListe.aggiungiArticolo(articolo4, "caio");
			
			assertEquals(2, GestioneListe.numeroCategorie());
			GestioneListe.eliminaCategoria("lampade");
			assertEquals(1, GestioneListe.numeroCategorie());
			assertFalse(GestioneListe.containsCategoria("lampade"));
			
			ArrayList<ArticoloSpesa> risultato = GestioneListe.getListaSpesa("pierino").ricercaCategoria(ArticoloSpesa.CATEGORIA_DEFAULT);
			assertEquals(2,risultato.size());
			assertTrue(risultato.contains(articolo1));
			assertTrue(risultato.contains(articolo3));
			
			risultato = GestioneListe.getListaSpesa("caio").ricercaCategoria(ArticoloSpesa.CATEGORIA_DEFAULT);
			assertTrue(risultato.contains(articolo4));
		});
		
    }
	
	@Test
    void containsCategoria() {
		Assertions.assertDoesNotThrow(()->
		{
			GestioneListe.creaCategoria("lampade");
			GestioneListe.creaCategoria("sedie");
			assertTrue(GestioneListe.containsCategoria("   lamPade"));
			assertTrue(GestioneListe.containsCategoria("SEdie   "));
			assertFalse(GestioneListe.containsCategoria("porte"));
		});
		
	}
	
	
	@Test     
    void aggiungiArticolo() {
		Assertions.assertDoesNotThrow(()->
		{
			GestioneListe.creaListaSpesa("pierino");
			GestioneListe.creaCategoria("lampade");
			ArticoloSpesa giusto = new ArticoloSpesa("lampada", 25.77f, "lampade", 2);
		
			GestioneListe.aggiungiArticolo(giusto, "pierino");
			
			assertTrue(GestioneListe.getListaSpesa("pierino").contains(giusto));
			
			RidondanzaException re = Assertions.assertThrows(RidondanzaException.class, ()->
			{
				GestioneListe.aggiungiArticolo(giusto, "pierino");
			});
			assertEquals("articolo gia' presente in lista",re.getMessage());
			
			ElementoNonTrovatoException pe = Assertions.assertThrows(ElementoNonTrovatoException.class, ()->
			{
				GestioneListe.aggiungiArticolo(giusto, "simone");
			});
			
			assertEquals("lista inesistente",pe.getMessage());
		});
	}
		
	@Test
	void aggiungiArticoloConCategoriaNuova() {
		ParametroException pe = Assertions.assertThrows(ParametroException.class, ()->
		{
			GestioneListe.creaListaSpesa("pierino");
			ArticoloSpesa giusto = new ArticoloSpesa("armadio", 25.77f, "mobili", 2);
			
			GestioneListe.aggiungiArticolo(giusto, "pierino");
		});
		assertEquals("categoria dell'articolo inesistente", pe.getMessage());
    }
	
	@Test
	void eliminaArticolo(){
		Assertions.assertDoesNotThrow(()->
		{
			GestioneListe.creaListaSpesa("pierino");
			GestioneListe.creaCategoria("lampade");
			ArticoloSpesa articolo1 = new ArticoloSpesa("lampada", 25.77f, "lampade", 2);
			ArticoloSpesa articolo2 = new ArticoloSpesa("led", 3f, "lampade", 2);
			ArticoloSpesa articolo3 = new ArticoloSpesa("faro", 45.77f, "lampade", 2);
	
			GestioneListe.aggiungiArticolo(articolo1, "pierino");
			GestioneListe.aggiungiArticolo(articolo2, "pierino");
			GestioneListe.aggiungiArticolo(articolo3, "pierino");
	
			
			assertTrue(GestioneListe.getListaSpesa("pierino").contains(articolo1));
			assertTrue(GestioneListe.getListaSpesa("pierino").contains(articolo2));
			assertTrue(GestioneListe.getListaSpesa("pierino").contains(articolo3));
	
			GestioneListe.eliminaArticolo(articolo1, "pierino");
			GestioneListe.eliminaArticolo(articolo3, "pierino");
			
			assertEquals(1,GestioneListe.getListaSpesa("pierino").size());
			assertFalse(GestioneListe.getListaSpesa("pierino").contains(articolo1));
			assertFalse(GestioneListe.getListaSpesa("pierino").contains(articolo3));
		});
	}
	
	@Test
	void eliminaArticoloPerNome(){
		Assertions.assertDoesNotThrow(()->
		{
			GestioneListe.creaListaSpesa("pierino");
			GestioneListe.creaCategoria("lampade");
			ArticoloSpesa articolo1 = new ArticoloSpesa("lampada", 25.77f, "lampade", 2);
			ArticoloSpesa articolo2 = new ArticoloSpesa("led", 3f, "lampade", 2);
			ArticoloSpesa articolo3 = new ArticoloSpesa("faro", 45.77f, "lampade", 2);
	
			GestioneListe.aggiungiArticolo(articolo1, "pierino");
			GestioneListe.aggiungiArticolo(articolo2, "pierino");
			GestioneListe.aggiungiArticolo(articolo3, "pierino");
	
			
			assertTrue(GestioneListe.getListaSpesa("pierino").contains(articolo1));
			assertTrue(GestioneListe.getListaSpesa("pierino").contains(articolo2));
			assertTrue(GestioneListe.getListaSpesa("pierino").contains(articolo3));
	
			GestioneListe.eliminaArticolo(articolo1.getNome(), "pierino");
			GestioneListe.eliminaArticolo(articolo3.getNome(), "pierino");
			
			assertEquals(1,GestioneListe.getListaSpesa("pierino").size());
			assertFalse(GestioneListe.getListaSpesa("pierino").contains(articolo1));
			assertFalse(GestioneListe.getListaSpesa("pierino").contains(articolo3));
		});
	}
	
	@Test
	 void containsInLista() {
		Assertions.assertDoesNotThrow(()->
		{
			GestioneListe.creaListaSpesa("pierino");
			GestioneListe.creaCategoria("lampade");
			ArticoloSpesa articolo1 = new ArticoloSpesa("lampada", 25.77f, "lampade", 2);
		
			GestioneListe.aggiungiArticolo(articolo1, "pierino");
			assertTrue(GestioneListe.containsInLista(articolo1, "pierino"));
			Assertions.assertThrows(ParametroException.class, ()->
			{
				GestioneListe.containsInLista(articolo1, "    ");
				
			});
			
			Assertions.assertThrows(ParametroException.class, ()->
			{
				GestioneListe.containsInLista(articolo1, null);
				
			});
			
			Assertions.assertThrows(ElementoNonTrovatoException.class, ()->
			{
				GestioneListe.containsInLista(articolo1, "gianfranco");
				
			});
		});
		
	}
		
	@Test
	 void containsInLista2() {
		Assertions.assertDoesNotThrow(()->
		{
			GestioneListe.creaListaSpesa("pierino");
			GestioneListe.creaCategoria("lampade");
			ArticoloSpesa articolo1 = new ArticoloSpesa("lampada", 25.77f, "lampade", 2);
		
			GestioneListe.aggiungiArticolo(articolo1, "pierino");
			assertTrue(GestioneListe.containsInLista("lampada", "pierino"));

			Assertions.assertThrows(ParametroException.class, ()->
			{
				GestioneListe.containsInLista(articolo1, "    ");
				
			});
			
			Assertions.assertThrows(ParametroException.class, ()->
			{
				GestioneListe.containsInLista(articolo1, null);
				
			});
			
			Assertions.assertThrows(ElementoNonTrovatoException.class, ()->
			{
				GestioneListe.containsInLista(articolo1, "gianfranco");
				
			});
		});	
	 }
	
	 @Test
	 void cercaArticoli() {
		 Assertions.assertDoesNotThrow(()->
			{
				GestioneListe.creaListaSpesa("pierino");
				GestioneListe.creaListaSpesa("sempronio");
				GestioneListe.creaCategoria("lampade");
				GestioneListe.creaCategoria("libri");

				ArticoloSpesa articolo1 = new ArticoloSpesa("lampada", 25.77f, "lampade", 2);
				ArticoloSpesa articolo2 = new ArticoloSpesa("led", 3f, "lampade", 2);
				ArticoloSpesa articolo3 = new ArticoloSpesa("nulla di nuovo sul fronte occidentale", 45.77f, "libri", 1);
	
				GestioneListe.aggiungiArticolo(articolo1, "pierino");
				GestioneListe.aggiungiArticolo(articolo2, "pierino");
				GestioneListe.aggiungiArticolo(articolo3, "sempronio");
				
				assertEquals(2,GestioneListe.cercaArticoli("l", "pierino").size());
				assertTrue(GestioneListe.cercaArticoli("l", "pierino").contains(articolo1));
				assertTrue(GestioneListe.cercaArticoli("l", "pierino").contains(articolo2));
				assertEquals(0,GestioneListe.cercaArticoli("l", "sempronio").size());
				assertFalse(GestioneListe.cercaArticoli("l", "sempronio").contains(articolo3));
			});
	    }
	
	@Test
	 void myToString() throws ParametroException, RidondanzaException, ElementoNonTrovatoException {
		
			GestioneListe.creaListaSpesa("pierino");
			GestioneListe.creaListaSpesa("marcolindo");

			GestioneListe.creaCategoria("lampade");
			ArticoloSpesa articolo1 = new ArticoloSpesa("lampada", 25.77f, "lampade", 2);
		
			GestioneListe.aggiungiArticolo(articolo1, "pierino");
			
			assertEquals("Gestore:\n"
					+ "Categorie: [lampade]\n"
					+ "Lista: pierino\n"
					+ "Articoli contenuti:\n"
					+ "Nome: lampada	Costo: 25.77	Categoria: lampade	Quantita': 2\n"
					+ "\n"
					+ "Lista: marcolindo\n"
					+ "Articoli contenuti:\n"
					+ "nessun articolo presente\n", GestioneListe.mytoString());
	}
	
	 
	@Test
	void serializzazione_deserializzazione()  {
		Assertions.assertDoesNotThrow(()->
		{
			ArticoloSpesa prova1= new ArticoloSpesa("oioli", 10, "lavandino", 1);
			ArticoloSpesa prova2= new ArticoloSpesa("comodino", 27.02f, "mobile", 2);
			ArticoloSpesa prova3= new ArticoloSpesa("letto", 195.99f, "mobile", 1);
			ArticoloSpesa prova4= new ArticoloSpesa("madame de pumpadour", 12f, "libri", 1);
			
			
			GestioneListe.creaListaSpesa("lista");
			GestioneListe.creaListaSpesa("pippo");

			GestioneListe.creaCategoria("lavandino");
			GestioneListe.creaCategoria("mobile");
			GestioneListe.creaCategoria("libri");

			GestioneListe.aggiungiArticolo(prova1, "lista");
			GestioneListe.aggiungiArticolo(prova2, "lista");
			GestioneListe.aggiungiArticolo(prova3, "pippo");
			GestioneListe.aggiungiArticolo(prova4, "pippo");

			GestioneListe.serializza();
			GestioneListe.reset();
			GestioneListe.deserializza();
							
			assertTrue(GestioneListe.containsLista("lista"));
			assertTrue(GestioneListe.containsLista("pippo"));
			assertTrue(GestioneListe.containsInLista(prova1, "lista"));
			assertTrue(GestioneListe.containsInLista(prova2, "lista"));
			assertTrue(GestioneListe.containsInLista(prova3, "pippo"));
			assertTrue(GestioneListe.containsInLista(prova4, "pippo"));
			assertTrue(GestioneListe.containsCategoria("lavandino"));
			assertTrue(GestioneListe.containsCategoria("mobile"));
			assertTrue(GestioneListe.containsCategoria("libri"));

		});	
			
	}
	 

	
}


