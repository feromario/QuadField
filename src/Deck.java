import java.util.ArrayList;
import java.util.Collections;

public class Deck {

    // Class variables
    private ArrayList<Card> cards;

    // Constructor
    public Deck() {
        cards = new ArrayList<>();
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
