L'Exercise Service interagisce con i seguenti gruppi di file: esercizi (sia di refactoring che di quiz), contenuto delle pagine educative, assignment, missioni, badge e levelConfig.

Ciascun gruppo è associato ad una precisa directory, la cui lista è riportata qui sotto:
- `ExerciseDB/RefactoringGame/`: directory contenente gli esercizi di refacotring;
- `ExerciseDB/CheckSmellGame/`: directory contenente gli esercizi di check-smell;
- `ExerciseDB/LearningContent/`: directory per il contenuto delle pagine di apprendimento;
- `/usr/src/app/assets/assignments/`: directory contenente gli assignment;
- `/usr/src/app/assets/missions/`: directory contenente le missioni;
- `/usr/src/app/assets/levelConfig/`: directory contenente il file `levelConfig.json`;
- `/usr/src/app/assets/badges/`: directory contenente i badge in formato `.png`.

## Esercizi di refactoring
Ogni esercizio di refactoring si compone di 3 file:
- `{nomeFile1}.java`: file contente la classe Java dell'esercizio;
- `{nomeFile2}Test.java`: file contenente la classe di test dell'esercizio;
- `{nomeFile3}Config.json`: file di configurazione dell'esercizio.

I 3 file devono essere raggruppati all'interno della stessa directory. Non vi è alcun vincolo su come nominare i file e la cartella che li contiene; è consigliato che:
- `{nomeFile1}` corrisponda al nome della classe Java che contiene;
- `{nomeFile2}Test` corrisponda al nome della classe Java di test che contiene;
- `{nomeFile1}` e `{nomeFile2}` corrispondano. 

Il file di configurazione deve essere strutturato come segue:
```json
{
  "exerciseId": string,
  "class_name": string,
  "refactoring_game_configuration": {
    "dependencies": [
      string
    ],
    "refactoring_limit": number,
    "smells_allowed": number,
    "level": number,
    "ignored_smells": [
      string
    ]
  },
  "auto_valutative": boolean
}
```

dove:
- `exerciseId` rappresenta l'identificativo dell'esercizio. Questo deve essere unico all'interno della directory `ExerciseDB/RefactoringGame/`;
- `class_name` rappresenta il nome della classe Java oggetto dell'esercizio e deve essere identica al nome della classe riportata nel file `{nomeFile1}.java`;
- `refactoring_game_configuration` contiene la configurazione dell'esercizio;
- `dependencies` rappresenta le dipendenze necessarie al Compiler Service per compilare l'esercizio;
- `refactoring_limit` rappresenta il limite di copertura del codice;
- `smells_allowed` rappresenta il numero massimo di smell ancora presenti nel codice accettabili per considerare superato l'esercizio;
 - `level` rappresenta il livello minimo che l'accout dell'utente deve possedere per poter affrontare l'esercizio;
 - `ignored_smells` rappresenta gli smell ignorati.

## Esercizi di check-game

Ogni esercizio di check-game si compone di 1 solo file:
- `{nomeFile}Config.json`: file di configurazione dell'esercizio.

Non è richiesto che il file di configurazione sia posizionato in una sottodirectory di `ExerciseDB/CheckSmellGame/`, come non è richiesto che questa abbia nome `{nomeFile}`. Rimane comunque consigliato creare una settodirectory `{nomeFile}` con al suo interno `{nomeFile}Config.json`.

Il file di configurazione deve essere strutturato come segue:
```json
{
  "exerciseId": string,
  "check_game_configuration": {
    "questions": [
      {
        "questionTitle": string,
        "questionCode": string,
        "answers": [
          {
            "answerText": string,
            "isCorrect": boolean
          }
        ]
      }
    ],
    "level": 1
  },
  "auto_valutative": boolean
}
```

dove:
- `exerciseId` rappresenta l'identificativo dell'esercizio. Questo deve essere unico all'interno della directory `ExerciseDB/CheckSmellGame/`;
- `check_game_configuration` contiene la configurazione dell'esercizio. Nello specifico:
- `questions` rappresenta la lista di domande dell'esercizio;
- `questionTitle` rappresenta il titolo della domanda;
- 'questionCode' rappresenta il blocco di codice Java oggetto della domanda;
- `answers` rappresenta la lista di risposte a crocetta per la domanda; 
- `answerText` rappresenta il testo della risposta;
- `isCorrect` rappresenta la correttezza della risposta.
- `level` rappresenta il livello minimo che l'accout dell'utente deve possedere per poter affrontare l'esercizio;

## Pagine educative
Ogni pagina educativa (il suo contenuto) si compone di 1 solo file:
- `{nomeFile}Config.json`: file di contenuto della pagina educativa.

Non è richiesto che il file di contenuto sia posizionato in una sottodirectory di `ExerciseDB/LearningContent/`, come non è richiesto che questa abbia nome `{nomeFile}`. Rimane comunque consigliato creare una settodirectory `{nomeFile}` con al suo interno `{nomeFile}Config.json`.

Il file di configurazione deve essere strutturato come segue:
```json
{
  "learningId": string,
  "title": string,
  "content": string,
  "external_reference": string
}
```

dove:
- `leaningId` rappresenta l'identificativo della pagina. Questo deve essere unico all'interno della directory `ExerciseDB/LearningContent/`;
- `title` rappresenta il titolo del contenuto che verrà mostrato;
- `content` rappresenta il contenuto di apprendimento da mostrare;
- `external_reference` rappresenta un link a una risorsa esterna alla quale l'utente potrà accedere dalla pagina di apprendimento per approfondire l'argomento. Si tratta di un campo opzionale;

## Assignment/Esperimenti
Ogni assignment si compone di 1 solo file:
- `{nomeFile}.json`: file di configurazione dell'assignment.

Non è richiesto che il file di contenuto sia posizionato in una sottodirectory di `/usr/src/app/assets/assignments/`, come non è richiesto che questa abbia nome `{nomeFile}`. Per continuità con la versione precedente, è consigliabile posizioneare il file all'interno del percorso `/usr/src/app/assets/assignments/` senza usare sottodirectory.

Il file di configurazione deve essere strutturato come segue:
```json
{
  "assignmentId": string,
  "gameType": string,
  "students": [
    {
      "name": string,
      "exerciseId": string,
      "startTime": string,
      "startDate": string,
      "endTime": string,
      "endDate": string,
      "submitted": boolean
    }
  ],
  "type": ["competitivo", "collaborativo"]
}
```

dove:
- `assignmentId` rappresenta l'identificativo dell'assignment. Questo deve essere unico all'interno della directory `/usr/src/app/assets/assignments/`;
- `gameType` rappresenta il tipo di esercizi assegnati con l'assignment. Questo può avere valore `refactoring`, per indicare che gli esercizi sono di tipo refactoring, o `smell-check`, per indicare che gli esercizi sono di smell-check;
- `students` rappresenta la lista di studentu/utenti a cui l'assignment è asseganto e i respettivi esercizi del tipo scelto assegnati;
- `name` rappresenta il nome utente dell'utente selezionato per l'assignment;
- `exerciseId` rappresenta l'identificativo dell'esercizio assegnato all'utente. Il valore deve corrispondere all'`exerciseId` di uno degli esercizi presenti all'interno di `ExerciseDB/`;
- `startTime` rappresenta l'ora di inizio dell'assignmenti, a partire dalla quale l'utente può parteciparvi. Deve essere rappresentata nella forma `hh-mm`;
- `startDate` rappresenta la data di inizio dell'assignment, a partire dalla quale l'utente può parteciparvi. Deve essere rappresentata nella forma `dd-mm-yy`;
- `endTime` rappresenta l'ora di fine dell'assignment, dopo la quale l'utente non potrà più parteciparvi. Deve essere rappresentata nella forma `hh-mm`;
- `endDate` rappresenta la data di fine dell'assignment, dopo la quale l'utente non potrà più parteciparvi. Deve essere rappresentata nella forma `dd-mm-yy`;
- `submitted` indica se l'utente ha consegnato o meno l'esercizio. Una volta che l'utente ha consegnato l'esercizio, non potra più accedere all'assignment.

## Missioni
Ogni missione si compone di 1 solo file:
- `{nomeFile}.json`: file di configurazione della missione.

Non è richiesto che il file di contenuto sia posizionato in una sottodirectory di `/usr/src/app/assets/missions/`, come non è richiesto che questa abbia nome `{nomeFile}`. Per continuità con la versione precedente, è consigliabile posizioneare il file all'interno del percorso `/usr/src/app/assets/missions/` senza usare sottodirectory.

Il file di configurazione deve essere strutturato come segue:
```json
{
  "missionId": string",
  "name": string,
  "badge": string,
  "badge_filename": string,
  "steps": [
    {
      "type": string,
      "id": string
    },
  ]
}
```

dove:
- `missionId` rappresenta l'identificativo della missione. Questo deve essere unico all'interno della directory `/usr/src/app/assets/missions/`;
- `name` rappresenta il nome della missione. Non è richiesto che sia identico a `missionId`;
- `badge` rappresenta il nome o la descrizione del badge che verrà sbloccato dall'utente al completamento della missione;
- `badge_filename` rappresenta il nome del file `.png` che rappresenta il badge, situato nella directory `/usr/src/app/assets/badges/`;
- `steps` rappresenta descrive la lista di passi da completare per superare la missione.
- `type` rappresenta il tipo di passo, che può corrispondere a un esercizio di tipo refacotring, a un esercizio di tipo check-smell o alla visione di una pagina educativa. I valori che può assumere sono rispettivamente `refactoring`, `check-smell` e `learning`;
- `id` rappresenta l'identificativo del file associato al passo. Deve quindi corrispondere all'`exerciseId` di un esercizio in `ExerciseDB/RefactoringGame/`, all'`exerciseId` di un esercizione in `ExerciseDB/ChechSmellGame/` o al campo `learningId` di un file in `ExerciseDB/LearningContent/`, in base al valore associato a `type`; 

## LevelConfig
Il file levelConfig.json rappresetna il file di configurazione per il levelling dell'utente e per impostazioni più generiche degli esercizi. Deve essere strutturato come segue:
```json
{
  "expValues": [number],
  "badgeValues": 
  [
    {
      "name": string,
      "description": string,
      "points": string,
      "filename": string
    }
  ],
  "answerPercentage": number
}
```

dove:
- `expValues` rappresenta la lista di punti esperienza che l'utente deve raggiungere per salire di un livello. Ad esempio, supponendo che il valore associato sia `[5, 10]`, l'utente deve raggiungere 5 punti esperienza per salive al livello 2, mentre ne deve raggiungere 10 per salire al livello 3;
- `badgeValues` descrive i basge che l'utente può ottenere guadaganndo punti esperienza;
- `name` rappresenta il nome del badge che verrà sbloccato;
- `description` rappresenta la descrizione del badge.
- `points` rappresenta il numero di punti esperienza necessari all'utente per sbloccare il badge associato. Per continuità con la verisone precedente, è stato mantenuto di tipo string;
- `filename` rappresenta il nome del file `.png` che rappresenta il badge, situato nella directory `/usr/src/app/assets/badges/`;
- `answerPercentage` rappresenta, in percentuale, il punteggio menimo che un utente deve raggiungere in un esercizio di check-smell affinchè questo venga cosiderato superato.
