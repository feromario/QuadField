import java.util.ArrayList;
import java.util.Collections;

public class Deck {

    // Class variables
    private ArrayList<Card> cards;

    // Constructor
    public Deck() {
        this.cards = new ArrayList<>();
        // Tank cards
        addCard("Dash", "Deal 1 DMG", 8);
        addCard("Heal", "Heal 1 HP", 8);
        addCard("Necromancer", "Spawn foot soldier", 4);
        addCard("Life Steal", "Steal 1 HP", 2);

        // Warrior cards
        addCard("Slash", "Deal 2 DMG", 8);
        addCard("Swing", "Deal 1 DMG to first 2 in line", 8);
        addCard("Inspire", "Draw 2 cards", 4);
        addCard("Cleave", "Deal 4 DMG", 2);

        // Mage cards
        addCard("Support", "Heal 1 HP to 1st friendly troop in line (not self)", 8);
        addCard("Fireball", "Deal 1 DMG to 2nd in line", 8);
        addCard("Magic Blast", "Deal 3 DMG", 4);
        addCard("Thunder", "Deal 1 DMG to every enemy troop", 2);

        // King cards
        addCard("Jab", "Deal 1 DMG", 8);
        addCard("Hook", "Deal 2 DMG", 4);
        addCard("Straight", "Deal 3 DMG", 2);

        // Special cards
        addCard("Tank Revive", "Revive the Tank", 8);
        addCard("Warrior Revive", "Revive the Warrior", 8);
        addCard("Mage Revive", "Revive the Mage", 8);
        addCard("Joker", "Double effect of next card", 4);

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
