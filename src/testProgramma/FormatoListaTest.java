package testProgramma;

import static org.junit.Assert.assertEquals;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import classiAstratte.FormatoArticolo;
import classiAstratte.FormatoLista;
import exceptions.GenericException;
import exceptions.ParametroException;
import programma.ListaSpesa;
	class FormatoListaTest {
		@SuppressWarnings("serial")
		private class FormatoListaConcreta extends classiAstratte.FormatoLista {
			public FormatoListaConcreta(String nome) throws ParametroException {
				super(nome);
			}
			@Override
			public <Param extends FormatoArticolo> void aggiungi(Param articolo) throws GenericException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public int size() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public <Param extends FormatoArticolo> boolean contains(Param articolo) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public ArrayList<? extends FormatoArticolo> ricercaCategoria(String str) throws GenericException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public ArrayList<? extends FormatoArticolo> ricercaNome(String str) throws GenericException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean eliminaPerNome(String str) throws ParametroException {
				return false;
				// TODO Auto-generated method stub
				
			}

			@Override
			public int eliminaPerCategoria(String nomeCategoria) throws ParametroException {
				return 0;
				// TODO Auto-generated method stub
				
			}

			@Override
			public int eliminaCategoria(String nomeCategoria) throws ParametroException {
				return 0;
				// TODO Auto-generated method stub
				
			}

			@Override
			public float costoTotale() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public ArrayList<? extends FormatoArticolo> MaggioriDi(float x) throws ParametroException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public ArrayList<? extends FormatoArticolo> MinoriDi(float x) throws ParametroException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public ArrayList<? extends FormatoArticolo> CompresiTra(float x, float y) throws ParametroException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String toString() {
				// TODO Auto-generated method stub
				return null;
			}
			@Override
			public <Param extends FormatoArticolo> void aggiungiAll(Collection<Param> lista) throws GenericException {
				// TODO Auto-generated method stub
				
			}
			@Override
			public FormatoArticolo getArticolo(int index) throws CloneNotSupportedException, GenericException {
				// TODO Auto-generated method stub
				return null;
			}
		}

			
	
	
	FormatoListaConcreta base;
	@BeforeEach
	void inizializzazione() throws ParametroException {
		base = new FormatoListaConcreta("mario");
	}
	
	@Test
	void costuttore(){
		
		Assertions.assertDoesNotThrow (()->
		{
		FormatoLista prova = new FormatoListaConcreta("  pippo      ");
		assertEquals(prova.getNome(), "pippo");
		});
	}
	@Test
	void costuttoreNomeInvalido() {
		exceptions.ParametroException pe = Assertions.assertThrows(exceptions.ParametroException.class, ()->
		{			
			@SuppressWarnings("unused")
			FormatoLista prova = new FormatoListaConcreta("  ");
		});
		assertEquals("nome non valido",pe.getMessage());
	}
	@SuppressWarnings("unused")
	@Test
	void costuttoreNomeNullo() {
		exceptions.ParametroException pe = Assertions.assertThrows(exceptions.ParametroException.class, ()->
		{
			FormatoLista prova = new FormatoListaConcreta(null);
		});
		assertEquals("nome non valido",pe.getMessage());
	}
	
	@Test
	void getNome() {
		assertEquals("mario", base.getNome());
	}
	
	@Test
	void setNome() {
		Assertions.assertDoesNotThrow (()->
		{
			base.setNome("ciccio");
		});
		
		
		Assertions.assertThrows(ParametroException.class, () ->{
			base.setNome("             ");
		});
	}
	
	
	@SuppressWarnings("unlikely-arg-type")
	@Test
	void equals() {
		Assertions.assertDoesNotThrow (()->
		{
			FormatoLista prova = new FormatoListaConcreta("  pippo      ");
			FormatoLista copia = new FormatoListaConcreta("        pippo");
			FormatoLista copia2 = new ListaSpesa("   pippo     ");
			
			assertTrue(prova.equals(copia));
			assertTrue(prova.equals(copia2));
			ArrayList<Integer> diverso = new ArrayList<>();
			assertFalse(prova.equals(diverso));
			
		});
		
	}
}