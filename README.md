# progettoSpesa

Progetto universitario in Java per la gestione di **liste della spesa**: creazione e modifica di liste e articoli, categorie condivise, interfaccia a riga di comando e interfaccia grafica (Swing).

Autore: Simone Nardella

## Funzionalità

- Creare, eliminare e selezionare più liste della spesa
- Aggiungere/rimuovere articoli (nome, costo, quantità, categoria)
- Categorie comuni a tutte le liste; eliminando una categoria gli articoli passano a `"Non Categorizzati"`
- Calcolo del costo totale di una lista (costo × quantità)
- Ricerca articoli per prefisso del nome
- Filtrare gli articoli di una lista per categoria
- Persistenza su file (`liste.txt`) tramite serializzazione Java
- Due interfacce utente:
  - **1** — terminale (CLI)
  - **2** — grafica Swing

## Architettura

```
src/
├── main/                 # Entry point (Main)
├── programma/            # Modello: ArticoloSpesa, ListaSpesa, GestioneListeSpesa
├── classiAstratte/       # FormatoArticolo, FormatoLista
├── interfacciaUtente/    # CLI e GUI Swing
├── exceptions/           # Eccezioni di dominio
├── testProgramma/        # Test JUnit
└── jbook/util/           # Utility Input (lettura da console)
```

| Pacchetto | Ruolo |
|-----------|--------|
| `main` | Avvio e scelta CLI/GUI |
| `programma` | Logica di business e gestore statico delle liste |
| `classiAstratte` | Contratti astratti per articoli e liste |
| `interfacciaUtente` | Presentazione (terminale + Swing) |
| `exceptions` | Errori di parametro, ridondanza, elemento non trovato, ecc. |
| `testProgramma` | Test unitari delle classi principali |

## Requisiti

- JDK (progetto con `module-info.java`, moduli JPMS)
- JUnit (Jupiter + JUnit 4, come dichiarato nel modulo)
- Modulo `java.desktop` (Swing)

Aprire il progetto con **Eclipse** (`.project` / `.classpath`) o **IntelliJ IDEA** (`progettoSpesa.iml`).

## Come avviare

1. Compilare i sorgenti in `src/` (da IDE o `javac`).
2. Eseguire la classe `main.Main`.
3. Alla richiesta:
   - `1` → interfaccia da terminale
   - `2` → interfaccia grafica

Nel terminale, dalla home puoi creare/eliminare liste e categorie, salvare/caricare da file, e operare sul contenuto di una lista selezionata. Digita `Q` per tornare indietro nei menu.

## Test

I test sono in `src/testProgramma/`:

- `ArticoloSpesaTest`
- `ListaSpesaTest`
- `FormatoArticoloTest`
- `FormatoListaTest`
- `GestioneListeTest`

Eseguirli dall’IDE (runner JUnit) dopo aver aggiunto JUnit al classpath del modulo.

## Struttura del repository

```
progettoSpesa/
├── src/              # Codice sorgente
├── .idea/            # Configurazione IntelliJ (senza workspace personale)
├── .settings/        # Preferenze Eclipse
├── .classpath
├── .project
├── progettoSpesa.iml
├── .gitignore
└── README.md
```

I file compilati (`bin/`, `*.class`), i dati runtime (`*.dat`, `liste.txt`) e lo stato personale dell’IDE non sono versionati.
