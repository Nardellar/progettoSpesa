package testProgramma;
import programma.ArticoloSpesa; 

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exceptions.ParametroException;

class ArticoloSpesaTest {
	ArticoloSpesa articolo;
	@BeforeEach
	void inizializzazione() throws ParametroException {
		
		articolo = new ArticoloSpesa("frigo", 12, 5);
	}
	@SuppressWarnings("unused")
	@Test
	void eccezionCostruttoreCosto() {
		exceptions.ParametroException pe = Assertions.assertThrows(exceptions.ParametroException.class, ()->
		{
			ArticoloSpesa articolo = new ArticoloSpesa("tavolo", -5, 3);
		});
		assertEquals("costo non valido",pe.getMessage());
	}
	@SuppressWarnings("unused")
	@Test
	void eccezioneCostruttoreQuantita() {
		exceptions.ParametroException pe = Assertions.assertThrows(exceptions.ParametroException.class, ()->
		{
			ArticoloSpesa articolo = new ArticoloSpesa("tavolo", 10, 0);
		});
		assertEquals("quantita' non valida",pe.getMessage());
	}
	@Test
	void setNome() {
		exceptions.OperazioneNonSupportataException pe = Assertions.assertThrows(exceptions.OperazioneNonSupportataException.class, ()->
		{
			articolo.setNome("ciao");
		});
		assertEquals("non e' permesso cambiare nome", pe.getMessage());	
	}
	@Test
	void setCategoriaEccezione() {
		exceptions.ParametroException pe = Assertions.assertThrows(exceptions.ParametroException.class, ()->
		{
			articolo.setCategoria(null);
		});
		assertEquals("categoria non valida", pe.getMessage());	
	}
	
	@SuppressWarnings("unused")
	@Test
	void setQuantitaEccezione() {
		exceptions.ParametroException pe = Assertions.assertThrows(exceptions.ParametroException.class, ()->
		{
			ArticoloSpesa articolo = new ArticoloSpesa("frigo",50,3);
			articolo.setQuantita(-7); 
		});
	}
	
	@Test
	void setCosto() {
		Assertions.assertDoesNotThrow(()->
		{
			ArticoloSpesa articolo = new ArticoloSpesa("frigo",50,3);
			articolo.setCosto(50.6789f);
			assertEquals(50.67f, articolo.getCosto(), 0.01);
		});
	}
	
	@SuppressWarnings("unused")
	@Test
	void setCostoEccezione() {
		exceptions.ParametroException pe = Assertions.assertThrows(exceptions.ParametroException.class, ()->
		{
			ArticoloSpesa articolo = new ArticoloSpesa("frigo",50,3);
			articolo.setCosto(-1); 
		});
	}
	@Test
	void testMyClone() {
		Assertions.assertDoesNotThrow(()->
		{
		
		ArticoloSpesa clone = articolo.myClone();
		articolo.setCosto(18.76f);
		assertTrue(articolo.equals(clone));
		assertTrue(articolo != clone);
		assertEquals(articolo.getCategoria(),clone.getCategoria());
		assertEquals(18.76f ,articolo.getCosto());
		assertEquals(12 ,clone.getCosto());
		assertEquals(articolo.getQuantita(),clone.getQuantita());
		});
	}
}
