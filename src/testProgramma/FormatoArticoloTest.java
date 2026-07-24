package testProgramma;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import classiAstratte.FormatoArticolo;
import exceptions.ParametroException;
public class FormatoArticoloTest {

	@SuppressWarnings("serial")
	private class ArticoloFormatoConcreto extends classiAstratte.FormatoArticolo{

		public ArticoloFormatoConcreto(String nome, float costo, String categoria, int quantita) throws ParametroException{
				super(nome,costo,categoria,quantita);
		}
		public ArticoloFormatoConcreto(String nome, float costo) throws ParametroException {
			this(nome, costo, CATEGORIA_DEFAULT, QUANTITA_DEFAULT);
		}
		public  ArticoloFormatoConcreto(String nome, float costo, String categoria) throws ParametroException {
			this(nome, costo, categoria, QUANTITA_DEFAULT);
		}
		public  ArticoloFormatoConcreto(String nome, float costo, int quantita) throws ParametroException {
			this(nome, costo, CATEGORIA_DEFAULT, quantita);
		}
		@Override
		public FormatoArticolo myClone() throws ParametroException {
			// TODO Auto-generated method stub
			return null;
		}
	}
	ArticoloFormatoConcreto articoloBase;
	@BeforeEach
	void inizializzazione() throws ParametroException{
		articoloBase = new ArticoloFormatoConcreto("microonde", 37.7f, "elettrodomestici   ", 2);
	}
	
	@Test
	void checkNome() {
		String giusto = "mamma";
		String sbagliato = "        ";
		String sbagliato2 = null;
		assertTrue(FormatoArticolo.checkString(giusto));
		assertFalse(FormatoArticolo.checkString(sbagliato));
		assertFalse(FormatoArticolo.checkString(sbagliato2));
	}
	
	@Test
	void checkCategoria() {
		String giusto = "mamma";
		String sbagliato = "        ";
		String sbagliato2 = null;
		assertTrue(FormatoArticolo.checkString(giusto));
		assertFalse(FormatoArticolo.checkString(sbagliato));
		assertFalse(FormatoArticolo.checkString(sbagliato2));
	}
	
	@Test
	void costruttore1() {
		Assertions.assertDoesNotThrow (()->
		{
		ArticoloFormatoConcreto articolo = new ArticoloFormatoConcreto("  lavatrice", 50, "elettrodomestici   ", 2);
		
		assertEquals("lavatrice", articolo.getNome());
		assertEquals(50, articolo.getCosto());
		assertEquals("elettrodomestici", articolo.getCategoria());
		assertEquals(2, articolo.getQuantita());
		});
	}
	
	@Test
	void costruttore2() {
		Assertions.assertDoesNotThrow (()->
		{
		ArticoloFormatoConcreto articolo = new ArticoloFormatoConcreto("    lavatrice    ", 50 );
		assertEquals("lavatrice", articolo.getNome());
		assertEquals(50, articolo.getCosto());
		assertEquals(ArticoloFormatoConcreto.CATEGORIA_DEFAULT, articolo.getCategoria());
		assertEquals(ArticoloFormatoConcreto.QUANTITA_DEFAULT, articolo.getQuantita());
		});
	}
	@Test
	void costruttore3() {
		Assertions.assertDoesNotThrow (()->
		{
			ArticoloFormatoConcreto articolo = new ArticoloFormatoConcreto("lavatrice", 50, "    elettrodomestici" );
			assertEquals("lavatrice", articolo.getNome());
			assertEquals(50, articolo.getCosto());
			assertEquals("elettrodomestici", articolo.getCategoria());
			assertEquals(ArticoloFormatoConcreto.QUANTITA_DEFAULT, articolo.getQuantita());
		});
		}
	@Test
	void costruttore4() {
		Assertions.assertDoesNotThrow (()->
		{
		ArticoloFormatoConcreto articolo = new ArticoloFormatoConcreto("lavatrice     ", 50, 3 );
		assertEquals("lavatrice", articolo.getNome());
		assertEquals(50, articolo.getCosto());
		assertEquals(ArticoloFormatoConcreto.CATEGORIA_DEFAULT, articolo.getCategoria());
		assertEquals(3, articolo.getQuantita());
		});
	}
	
	@SuppressWarnings("unused")
	@Test
	void eccezioneNome1() {
		ParametroException pe = Assertions.assertThrows(ParametroException.class, ()->
		{
			ArticoloFormatoConcreto articolo = new ArticoloFormatoConcreto("", 50, 3 );
		});
		assertEquals("nome non valido",pe.getMessage());
	}
	@SuppressWarnings("unused")
	@Test
	void eccezioneNome2() {
		exceptions.ParametroException pe = Assertions.assertThrows(exceptions.ParametroException.class, ()->
		{
			ArticoloFormatoConcreto articolo = new ArticoloFormatoConcreto("        ", 50, 3 );
		});
		assertEquals("nome non valido",pe.getMessage());
	}
	@SuppressWarnings("unused")
	@Test
	void eccezioneNome3() {
		Assertions.assertDoesNotThrow (()->
		{
			ArticoloFormatoConcreto articolo = new ArticoloFormatoConcreto("    jsod    ", 50, 3 );
		});		
	}
	@SuppressWarnings("unused")
	@Test
	void eccezioneNome4() {
		exceptions.ParametroException pe = Assertions.assertThrows(exceptions.ParametroException.class, ()->
		{
			ArticoloFormatoConcreto articolo = new ArticoloFormatoConcreto(null, 50, 3);
			
		});
		assertEquals("nome non valido",pe.getMessage());
	}
	
	@Test
	void categoriaVuota() {
		Assertions.assertDoesNotThrow (()->
		{
			ArticoloFormatoConcreto articolo = new ArticoloFormatoConcreto("    jsod    ", 50, "  ",3 );
			assertEquals(ArticoloFormatoConcreto.CATEGORIA_DEFAULT, articolo.getCategoria());
		});
	}
	@Test
	void categoriaVuota2() {
		Assertions.assertDoesNotThrow (()->
		{
			ArticoloFormatoConcreto articolo = new ArticoloFormatoConcreto("    jsod    ", 50, null,3 );
			assertEquals(ArticoloFormatoConcreto.CATEGORIA_DEFAULT, articolo.getCategoria());
			});
	}
	
	@Test
	void getNome() {
		assertEquals("microonde", articoloBase.getNome());
	}
	@Test
	void getCosto() {
		assertEquals(37.7f, articoloBase.getCosto());
	}
	@Test
	void getCategoria() {
		assertEquals("elettrodomestici", articoloBase.getCategoria());
	}
	@Test
	void getQuantita() {
		assertEquals(2, articoloBase.getQuantita());
	}
	@Test
	void setNome() {
		Assertions.assertDoesNotThrow (()->
		{
		articoloBase.setNome("pippo");
		assertEquals("pippo", articoloBase.getNome());
		});
		
		Assertions.assertThrows(exceptions.ParametroException.class, ()->
			{
				articoloBase.setNome("     ");
			});
	}
	
	@Test
	void setCategoria() {
		Assertions.assertDoesNotThrow (()->
		{
		articoloBase.setCategoria("pippo");
		assertEquals("pippo", articoloBase.getCategoria());
		});
		
		Assertions.assertThrows(exceptions.ParametroException.class, ()->
			{
				articoloBase.setCategoria("     ");
			});
	}
	
	@Test
	void setCosto() {
		Assertions.assertDoesNotThrow (()->
		{
		assertEquals(10, articoloBase.setCosto(10).getCosto());
		});
	}
	
	@Test
	void setQuantita() {
		Assertions.assertDoesNotThrow (()->
		{
		assertEquals(5, articoloBase.setQuantita(5).getQuantita());
		});
	}
	
	@Test
	void TestToString() {
		assertEquals("Nome: microonde	Costo: 37.7	Categoria: elettrodomestici	Quantita': 2", articoloBase.toString());
	}
	@Test
	void testEquals() {
		Assertions.assertDoesNotThrow (()->
		{
		ArticoloFormatoConcreto articolo = new ArticoloFormatoConcreto("frigo", 50, 5 );
		ArticoloFormatoConcreto articoloCopia = new ArticoloFormatoConcreto("FRIgo", 78, 2);
		ArticoloFormatoConcreto articoloDiverso = new ArticoloFormatoConcreto("frigorifero", 78, 2);
		
		assertTrue(articolo.equals(articoloCopia));
		assertFalse(articolo.equals(articoloDiverso));
		});
	}
	
	@Test
	void matchCategoria() {
		Assertions.assertDoesNotThrow (()->
		{
		ArticoloFormatoConcreto articolo = new ArticoloFormatoConcreto("frigo", 50, "cucina" );
		ArticoloFormatoConcreto articoloCopia = new ArticoloFormatoConcreto("FRIgo", 78, "cUcIna");
		ArticoloFormatoConcreto articoloDiverso = new ArticoloFormatoConcreto("frigorifero", 78, "mobile");
		assertTrue(articolo.matchCategoria(articoloCopia.getCategoria()));
		assertFalse(articolo.matchCategoria(articoloDiverso.getCategoria()));
		});
	}
	
	
}
