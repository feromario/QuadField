import java.util.ArrayList;
import java.util.Collections;

public class Deck {

    // Class variables
    private ArrayList<Card> cards;

    // Constructor
    public Deck() {
        this.cards = new ArrayList<>();
        // Tank cards
        addCard("Dash", "Deal 1 DMG", 4);
        addCard("Heal", "Heal 1 HP", 4);
        addCard("Necromancer", "Spawn foot soldier", 2);
        addCard("Life Steal", "Steal 1 HP", 1);

        // more cards
    }

    // Helper method for card counts
    private void addCard(String name, String effect, int count) {
        for (int i = 0; i < count; i++) {
            cards.add(new Card(name, effect));
        }
    }

    // Shuffle deck
    public void shuffle() {
        Collections.shuffle(cards);
    }

    // Drawing a card, removes top element from array list
    public Card draw() {
        return cards.remove(0);
    }

    // Checks if array list is empty
    public boolean isEmpty() {
        return cards.isEmpty();
    }

    // Check how many elements are left
    public int size() {
        return cards.size();
    }

}
