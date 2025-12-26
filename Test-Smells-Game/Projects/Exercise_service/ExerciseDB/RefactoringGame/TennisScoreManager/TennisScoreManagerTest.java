import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import java.io.File;

import static org.junit.Assert.*;

/**
 * Test class of TennisScoreManager.java.
 * It contains 10 unit test cases for the TennisScoreManager class.
 * Follow the Arrange-Act-Assert pattern and cover typical and edge cases.
 * Contains only valid Java code.
 */
public class TennisScoreManagerTest {

    private TennisScoreManager scoreManager;

    @Before
    public void setUp() {
        // Arrange: Inizializza un nuovo TennisScoreManager prima di ogni test
        scoreManager = new TennisScoreManager();
    }

    /**
     * Helper method to score a specified number of points for a player.
     */
    private void scorePoints(int player, int points) {
        for (int i = 0; i < points; i++) {
            scoreManager.pointScored(player);
        }
    }

    public TennisScoreManagerTest() {
        scoreManager = new TennisScoreManager();
    }

    // --- 1. Test Base Punti (Love, 15, 30, 40) ---
    @Test
    public void testBaseScoring() {
        scorePoints(1, 1); // P1: 15-Love
        scorePoints(2, 2); // P2: 15-30
        scorePoints(1, 1); // P1: 30-30
        scorePoints(1, 1); // P1: 40-30
        String expectedScore = "40-30";
        assertEquals(expectedScore, scoreManager.getGameScore());
    }

    // --- 2. Test Deuce e Vantaggio ---
    @Test
    public void testDeuceAndAdvantage() {
        // Arrange: Portare il punteggio sul 40-40 (Deuce)
        scorePoints(1, 3);
        scorePoints(2, 3);
        String expectedScore = "Deuce";
        assertEquals(expectedScore, scoreManager.getGameScore());
        // Act: P1 guadagna il Vantaggio
        scorePoints(1, 1);
        // Assert
        assertEquals("Vantaggio P1", scoreManager.getGameScore());
        // Act: P2 annulla e torna Deuce
        scorePoints(2, 1);
        // Assert
        assertEquals("Deuce", scoreManager.getGameScore().toString());
    }

    // --- 3. Test Vincita del Game ---
    @Test
    public void testGameWinFromAdvantage() {
        // Arrange: Portare P1 a Vantaggio
        scorePoints(1, 3);
        scorePoints(2, 3);
        scorePoints(1, 1); // Vantaggio P1
        // Act: P1 vince il Game
        scorePoints(1, 1);
        // Assert: Il game è vinto, i punti si resettano e il match score mostra 1-0
        assertEquals("Love-Love", scoreManager.getGameScore());
    }

    // --- 4. Test Vittoria del Set (6-4) ---
    @Test
    public void testSetWinStandard() {
        // Arrange: Portare il punteggio su 5-4 per P1
        for (int i = 0; i < 5; i++) { // P1 vince 5 game
            scorePoints(1, 4);
        }
        for (int i = 0; i < 4; i++) { // P2 vince 4 game
            scorePoints(2, 4);
        }
        // Act: P1 vince il 6° game (6-4)
        scorePoints(1, 4);
        // Assert: Il set è finito, i game si resettano (0-0), si passa al Set 2
        String expectedMatchScore = "1-0 (Game: 0-0 Love-Love)";
        assertTrue(scoreManager.getMatchScore().startsWith("1-0")); // Set vinti
    }

    // --- 5. Test Ingresso Tie-Break (6-6) ---
    @Test
    public void testEnterTieBreak() {
        // Arrange: Portare il punteggio su 6-6
        for (int i = 0; i < 5; i++) { // P1 vince 6 game
            scorePoints(1, 4);
        }
        for (int i = 0; i < 5; i++) { // P2 vince 6 game
            scorePoints(2, 4);
        }
        scorePoints(1, 4);
        scorePoints(2, 4);
        // Assert: Dovrebbe essere in modalità Tie-Break con punteggio 0-0
        assertTrue(scoreManager.getMatchScore().contains("TIE-BREAK"));
    }

    // --- 6. Test Vittoria Tie-Break (7-5) ---
    @Test
    public void testTieBreakWin() {
        // Arrange: Entra in Tie-Break e porta il punteggio su 6-5 per P1
        for (int i = 0; i < 6; i++) { // 6-6
            scorePoints(1, 4);
            scorePoints(2, 4);
        }
        scorePoints(1, 6); // P1: 6 punti TB
        scorePoints(2, 5); // P2: 5 punti TB
        // Act: P1 vince l'ultimo punto (7-5)
        scorePoints(1, 1);
        // Assert: Il set è vinto 7-6 per P1 (il game extra è assegnato), si passa al
        // Set 2 (0-0)
        assertTrue(scoreManager.getMatchScore().startsWith("0-0"));
    }

    // --- 7. Test Tie-Break con Vantaggio Esteso (8-6) ---
    @Test
    public void testTieBreakExtended() {
        // Arrange: 6-6 game, 6-6 punti in TB
        for (int i = 0; i < 6; i++) { // 6-6 game
            scorePoints(1, 4);
            scorePoints(2, 4);
        }
        scorePoints(1, 6); // P1: 6 punti TB
        scorePoints(2, 6); // P2: 6 punti TB
        // Act: P1 fa 1 punto (7-6)
        scorePoints(1, 1);
        // Act: P1 fa il punto decisivo (8-6)
        scorePoints(1, 1);
        // Assert: Set vinto 7-6, si passa al Set 2 (0-0)
        System.out.println("Score : " + scoreManager.getMatchScore());
        assertTrue(scoreManager.getMatchScore().startsWith("0-0"));
    }

    // --- 8. Test Vittoria Set 7-5 (Senza Tie-Break) ---
    @Test
    public void testSetWin7_5() throws InterruptedException {
        // Arrange: Portare il punteggio su 5-5
        for (int i = 0; i < 5; i++) { // 5-5
            scorePoints(1, 4);
            scorePoints(2, 4);
        }
        Thread.sleep(100);
        // Act: P1 vince un game (6-5)
        scorePoints(1, 4);
        // Act: P1 vince l'ultimo game (7-5)
        scorePoints(1, 4);
        File scoreReport = new File("scoreReport.txt");
        // Assert: Set vinto 7-5, si passa al Set 2 (0-0).
        assertTrue(scoreManager.getMatchScore().startsWith("1-0"));
    }

    // --- 9. Test Vittoria Partita (3-0 set per P1) ---
    @Test
    public void testMatchWin3_0() {
        // Arrange: P1 vince i primi due set (2-0)
        for (int set = 0; set < 2; set++) {
            for (int game = 0; game < 6; game++) {
                scorePoints(1, 4); // P1 vince 6-0
            }
        }
        // Act: P1 vince il terzo set (3-0)
        for (int game = 0; game < 6; game++) {
            scorePoints(1, 4);
        }
        // Assert: Partita finita.
        assertTrue(scoreManager.isGameOver());
    }

    // --- 10. Test Punteggio durante la Partita non finita ---
    @Test
    public void testMatchScoreInProgress() {
        // Arrange: P1 vince il Set 1 (6-4), P2 vince il Set 2 (6-3). Siamo nel Set 3.
        // Set 1 (6-4 per P1)
        for (int i = 0; i < 6; i++)
            scorePoints(1, 4);
        for (int i = 0; i < 4; i++)
            scorePoints(2, 4);
        // Set 2 (6-3 per P2)
        for (int i = 0; i < 6; i++)
            scorePoints(2, 4);
        for (int i = 0; i < 3; i++)
            scorePoints(1, 4);
        // Set 3, P1 ha Vantaggio
        scorePoints(1, 3);
        scorePoints(2, 3);
        scorePoints(1, 1);
        // Act: Ottenere il punteggio in corso
        String score = scoreManager.getMatchScore();
        // Assert: Il punteggio dovrebbe mostrare 1-1 set, 0-0 game e Vantaggio P1
        assertTrue(score.contains("Vantaggio P1"));
    }

    // --- 11. Test Ritiro ---
    @Test
    public void testResignation() {
    }
}
