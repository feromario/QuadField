import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainQuadField {
    public static void main(String[] args) {
        // Create players
        Player p1 = new Player("Player 1");
        Player p2 = new Player("Player 2");
        // Create deck
        List<Deck> deck = createDeck();
        Collections.shuffle(deck);

        // Decide turn
        Player activePlayer = p1;
        Player opponent = p2;

        // Game loop
        while (p1.isKingAlive() && p2.isKingAlive()) {
            System.out.println(activePlayer + " starting...");


        }










    }

    private static List<Deck> createDeck() {
        List<Deck> deck = new ArrayList<>();
        String[] troops = {"Tank", "Knight", "Mage", "King"};
        for (String t : troops) {
            for (int i = 1; i < 5; i++) {
                deck.add(new Deck(t, 10));
            }
        }
        deck.add(new Deck("Wild Power", 0));
        return deck;
    }
}
