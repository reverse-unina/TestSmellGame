Attualmente, TestSmellGame prevede 3 modalità di gioco:
- Gioco libero, dove un utente può esercitarsi nel risolvere esercizi di tipo refactoring e check-smell scegliendo tra quelli resi disponibili, potendoli risolvere qunte volte vuole;
- Assignment/esperimenti, dove un utente ha un tempo massimo entro il quale completare gli esercizi di refacotoring e/o check-smell assegnati, con la possibilità di risolverli uan sola volta. Una volta che l'esercizio sarà stato consegnato, l'utente non potrà può accedervi;
- Missioni, dove un utente può cimentarsi nel risolvere una serie di esercizi, uno dopo l'altro, intervallati (se previsto) da contenuto educativo. Per accedere allo step successivo l'utente deve aver completato con successo lo step corrente. L'utente può ritentare qualsiasi step della missione indefinitamente finchè non riesce a superarlo. Una volta superato non potrà tornare indietro. Una volta che la missiona sarà stata completata, l'utente non potrà più accedervi.

Il completamento, con successo, di un esercizio di gioco libero permette all'utente di ottenere punti esperienza. Raggiunto un certo quantitativo di punti esperienza, l'utente potrà salire di livello, sbloccando ad esempio esercizi di difficoltà maggiore, e sbloccare nuovi badge.

Il completamento con successo di una missione permetterà all'utente di sbloccare il relativo badge associato.

## Calcolo dei punteggi
Un esercizio di refactoring è considerato come completato con successo se il numero di test smell rimanenti nella classe di test è minore o uguale al numero di smell consentiti (proprietà `smells_allowed`). Il punteggio assegnato alla soluzione è calcolato come segue:

$$ Punteggio = smell\\_ammessi - smell\\_rimasti $$

Un eserizio di check-smell è considerato come completato con successo se il punteggio ottenuto è paro o superione al campo `answerPercentage` del file `levelConfig.json`. Il punteggio assegnato alla soluzione è calcolato come segue:

$$ Punteggio = \frac{\sum{risposte\\_corrette} - 0.5 \cdot \sum{risposte\\_errate}}{totale_risposte\\_corrette} \cdot 100 $$

Una missione è considerata completata (con successo) quando l'utente ha completato con successo tutti i suoi step. Il punteggio assegnato alla soluzione è calcolato come segue: 

$$ Punteggio = \sum{step\\_refactoring} + \sum{step\\_check-smell} $$
