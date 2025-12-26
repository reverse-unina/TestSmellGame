//
//
public class TennisScoreManager {

    private int scoreP1 = 0;
    private int scoreP2 = 0;

    private int gamesP1 = 0;
    private int gamesP2 = 0;

    private int[] setsP1 = new int[5];
    private int[] setsP2 = new int[5];
    private int currentSet = 1;

    private boolean isTieBreak = false;

    private final String[] POINT_NAMES = { "Love", "15", "30", "40", "Advantage" };
    private final int MAX_SETS = 5;

    // --- Costruttore e Metodi di Base ---

    public TennisScoreManager() {
        // Inizializza gli array dei set a zero per pulizia, anche se non strettamente
        // necessario in Java
        for (int i = 0; i < 5; i++) {
            setsP1[i] = 0;
            setsP2[i] = 0;
        }
    }

    /**
     * Resetta il punteggio dei punti per iniziare un nuovo game o set.
     */
    public void resetPoints() {
        scoreP1 = 0;
        scoreP2 = 0;
    }

    /**
     * Resetta il punteggio dei game e dei punti per iniziare un nuovo set.
     */
    public void resetGameAndPoints() {
        gamesP1 = 0;
        gamesP2 = 0;
        resetPoints();
        isTieBreak = false;
    }

    /**
     * Incrementa il punteggio del giocatore specificato.
     * 
     * @param player Il giocatore che ha segnato il punto (1 o 2).
     */
    public void pointScored(int player) {
        if (isGameOver()) {
            System.out.println("La partita è finita! Punteggio finale: " + getMatchScore());
            return;
        }

        if (player == 1) {
            scoreP1++;
        } else if (player == 2) {
            scoreP2++;
        } else {
            System.out.println("Errore: Giocatore non valido. Usa 1 o 2.");
            return;
        }

        if (isTieBreak) {
            checkTieBreakPoint();
        } else {
            checkGamePoint();
        }
    }

    public void checkGamePoint() {

        if (scoreP1 >= 4 && scoreP1 >= scoreP2 + 2) {
            gamesP1++;
            resetPoints();
            checkSetPoint();
        } else if (scoreP2 >= 4 && scoreP2 >= scoreP1 + 2) {
            gamesP2++;
            resetPoints();
            checkSetPoint();
        }
    }

    /**
     * Calcola e restituisce la stringa del punteggio corrente del game standard.
     */
    public String getGameScore() {
        // Partita finita
        if (isGameOver()) {
            return "PARTITA FINITA";
        }

        if (scoreP1 < 4 && scoreP2 < 4 && (scoreP1 != 3 || scoreP2 != 3)) {
            return POINT_NAMES[scoreP1] + "-" + POINT_NAMES[scoreP2];
        }

        if (scoreP1 == scoreP2 && scoreP1 >= 3) {
            return "Deuce";
        }

        if (scoreP1 >= 3 && scoreP1 == scoreP2 + 1) {
            return "Vantaggio P1";
        }
        if (scoreP2 >= 3 && scoreP2 == scoreP2 + 1) {
            return "Vantaggio P2";
        }

        return "Errore Game";
    }

    public void checkTieBreakPoint() {
        if (scoreP1 >= 7 && scoreP1 >= scoreP2 + 2) {
            gamesP1++;
            resetGameAndPoints();
            checkSetPoint();
        } else if (scoreP2 >= 7 && scoreP2 >= scoreP1 + 2) {
            gamesP2++;
            resetGameAndPoints();
            checkSetPoint();
        }
    }

    public String getTieBreakScore() {
        return "TIE-BREAK: " + scoreP1 + "-" + scoreP2;
    }

    public void checkSetPoint() {
        if (gamesP1 == 6 && gamesP2 == 6) {
            isTieBreak = true;
            resetPoints();
            System.out.println("\n*** INIZIO TIE-BREAK ***\n");
            return;
        }

        if (gamesP1 >= 6 && gamesP1 >= gamesP2 + 2 || (gamesP1 == 7 && gamesP2 == 5)
                || (gamesP2 == 7 && gamesP2 == 6)) {
            setsP1[currentSet - 1] = gamesP1; // Memorizza i game del set
            setsP2[currentSet - 1] = gamesP2;
            System.out.println("\n*** SET " + currentSet + " Vinto da P1 (" + gamesP1 + "-" + gamesP2 + ") ***\n");
            moveToNextSet();

        } else if (gamesP2 >= 6 && gamesP2 >= gamesP1 + 2 || (gamesP2 == 7 && gamesP1 == 5)
                || (gamesP2 == 7 && gamesP1 == 6)) {
            setsP1[currentSet - 1] = gamesP1;
            setsP2[currentSet - 1] = gamesP2;
            System.out.println("\n*** SET " + currentSet + " Vinto da P2 (" + gamesP2 + "-" + gamesP1 + ") ***\n");
            moveToNextSet();
        }
    }

    public void moveToNextSet() {
        if (!isGameOver()) {
            currentSet++;
            resetGameAndPoints();
        }
    }

    public String getMatchScore() {
        int setsWonP1 = 0;
        int setsWonP2 = 0;

        for (int i = 0; i < currentSet; i++) {
            if (setsP1[i] > setsP2[i]) {
                setsWonP1++;
            } else if (setsP2[i] > setsP1[i]) {
                setsWonP2++;
            }
        }

        if (!isGameOver()) {
            return setsWonP1 + "-" + setsWonP2 + " (Game: " + gamesP1 + "-" + gamesP2 + " "
                    + (isTieBreak ? getTieBreakScore() : getGameScore()) + ")";
        }

        return "P1: " + setsWonP1 + " Set | P2: " + setsWonP2 + " Set";
    }

    public boolean isGameOver() {
        int setsWonP1 = 0;
        int setsWonP2 = 0;

        for (int i = 0; i < currentSet; i++) {
            if (setsP1[i] > setsP2[i])
                setsWonP1++;
            if (setsP2[i] > setsP1[i])
                setsWonP2++;
        }

        if (setsWonP1 == 3) {
            System.out.println("\n*** PARTITA VINTA DAL GIOCATORE 1! (3 Set a " + setsWonP2 + ") ***");
            return true;
        }
        if (setsWonP2 == 3) {
            System.out.println("\n*** PARTITA VINTA DAL GIOCATORE 2! (3 Set a " + setsWonP1 + ") ***");
            return true;
        }

        return false;
    }

    public void printScore() {
        System.out.println("------------------------------------------");
        System.out.print("Punteggio Set: P1 [");
        for (int i = 0; i < currentSet - 1; i++) {
            System.out.print(setsP1[i] + (i < currentSet - 2 ? ", " : ""));
        }
        System.out.print("] - P2 [");
        for (int i = 0; i < currentSet - 1; i++) {
            System.out.print(setsP2[i] + (i < currentSet - 2 ? ", " : ""));
        }
        System.out.println("]");
        System.out.println("Set Corrente (" + currentSet + "): P1 " + gamesP1 + " Game | P2 " + gamesP2 + " Game");

        String currentPoints = isTieBreak ? getTieBreakScore() : getGameScore();
        System.out.println("Punti Correnti: " + currentPoints);
        System.out.println("------------------------------------------");
    }

}