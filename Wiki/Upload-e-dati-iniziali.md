TestSmellCheck (se runnato tramite container docker generati con gli script/comandi forniti) presenta già al suo avvio dei dati precaricati, quali utenti, missioni, assignment, badge, pagine di apprendimento ed esercizi di refactoring/check-smell.

I dati degli utenti precaricati sono i seguenti:

| Email             | Username | Password |
|-------------------|----------|----------|
| player1@gmail.com | player1  | player1  |
| player2@gmail.com | player2  | player2  |
| player3@gmail.com | player3  | player3  |
| player4@gmail.com | player4  | player4  |

## Upload di nuovi file nel container docker dell'Exercise Service
Supponendo sempre che i servizi siano runnuti tramite container docker, TestSmellGame mette a disposizioni degli scritp `.bat` per Windows e `.sh` per Linux per automatizzare l'upload nel container di nuovi assignment, missioni, pagine di apprendimento o esericizi. Nello specifico:
- `upload_ExerciseDB_files.bat`/`upload_ExerciseDB_files.sh` può essere utilizzato per caricare nuovi esercizi di check-smell, nuovi esercizi di refactoring e/o nuove pagine educative;
- `upload_assignment.bat`/`upload_assignment.sh` può essere utilizzato per caricare nuovi assignment;
- `upload_missions.bat`/`upload_missions.sh` può essere utilizzato per caricare nuove missioni.