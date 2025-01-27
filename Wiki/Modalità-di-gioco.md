Attualmente, TestSmellGame prevede 3 modalità di gioco:
- Gioco libero, dove un utente può esercitarsi nel risolvere esercizi di tipo refactoring e check-smell scegliendo tra quelli resi disponibili. Ogni esercizio può essere risolto quante volte si desidera;
- Assignment/esperimenti, dove un utente ha un tempo massimo entro il quale completare gli esercizi di refacotoring e/o check-smell assegnati. Ogni esercizio può essere risolto una sola volta. Una volta consegnato, l'utente non potrà più accedervi.;
- Missioni, dove un utente può cimentarsi nel risolvere una serie di esercizi, uno dopo l'altro, intervallati (se previsto) da contenuti educativi. Per accedere allo step successivo l'utente deve aver completato con successo lo step corrente.  completare con successo lo step corrente. Ogni step può essere ripetuto indefinitamente finché non viene superato. Tuttavia, una volta superato, non sarà più possibile tornare indietro. Completata la missione, l'utente non potrà più accedervi.

## Calcolo di punteggi e punti esperienza
### Esercizi di refactoring
Un esercizio di refactoring è considerato come completato con successo se:
- Il numero di test smell rimanenti nella classe di test è minore o uguale al numero di smell consentiti (proprietà `smells_allowed`); 
- La code coverage del test rifattorizzato è pari a quella del test originale (meno un possibile scarto pari alla proprietà `refactoring_limit). 

Se queste condizioni sono soddisfatte, all'utente verrà assegnato un punteggio calcolato come:

$$ Punteggio = smell\\_ammessi - smell\\_rimasti $$

Identifichiamo ora 3 casi:
- Se l'esercizio è stato completato per la prima volta, l'utente otterrà punti esperienza, incrementerà il suo score per la modalità "refactoring" e gli verrà assegnato uno score per l'esercizio risolto pari al punteggio ottenuto;
- Se l'esercizio è già stato completato in precedenza e il punteggio è migliorato, l'utente otterrà punti esperienza e incrementerà il suo score per la modalità "refactoring" pari alla differenza tra il nuovo punteggio e quello precedente. Lo score per l'esercizio verrà aggiornato col nuovo punteggio;
- Se l'esercizio è già stato completato in precedenza e il punteggio ottenuto è pari o inferiore a quello precedente, i punti esperienza e gli score non verranno aggiornati.

### Esercizi di check-smell
Un eserizio di check-smell è considerato come completato con successo se:
- Il punteggio ottenuto è pari o superione al campo `answerPercentage` del file `levelConfig.json`. Il punteggio è calcolato come segue:

$$ Punteggio = \frac{\sum{risposte\\_corrette} - 0.5 \cdot \sum{risposte\\_errate}}{totale_risposte\\_corrette} \cdot 100 $$

Il completamento con successo di un esercizio farà ottenere all'utente 1 punto esperienza e incrementerà dello stesso valore il suo punteggio relativo alla classifica "check-game". Se dovesse nuovamente completare con successo l'esercizio, non otterrà alcun nuovo punto.

### Missioni
Una missione è considerata completata (con successo) quando l'utente ha completato con successo tutti i suoi step. Il punteggio assegnato è calcolato come segue: 

$$ Punteggio = \sum{step\\_refactoring} + \sum{step\\_check-smell} $$

Questo punteggio concorrerà a determinare la sua classifica per la modalità "missions". Inoltre, al termine di una missione, sbloccherà il badge associato, visibile nella sua pagina utente. 

## Punti esperienza e livello utente
Al raggiungimento di un certo numero di punti esperienza, l'utente:
- Salirà di livello, sbloccando nuovi esercizio di difficoltà via via crescente;
- Sbloccherà nuovi badge visibili nella sua pagina utente.