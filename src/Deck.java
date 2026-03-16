import java.util.ArrayList;
import java.util.Collections;

public class Deck {

    // Class variables
    private ArrayList<Card> cards;

    // Constructor
    public Deck() {
        this.cards = new ArrayList<>();
        // Tank cards
        addCard("Dash", "Tank deals 1 damage.", 8);
        addCard("Heal", "Tank heals 1 hp.", 8);
        addCard("Necromancer", "Tank spawns a foot soldier.", 4);
        addCard("Life Steal", "Tank steals 1 hp from opposition.", 2);

        // Warrior cards
        addCard("Slash", "Warrior deals 2 damage.", 8);
        addCard("Swing", "Warrior deals 1 damage to first 2 enemies in line.", 8);
        addCard("Inspire", "Warrior draws 2 cards.", 4);
        addCard("Cleave", "Warrior deals 4 damage.", 2);

        // Mage cards
        addCard("Support", "Mage heals 1 hp to 1st friendly troop in line, besides itself.", 8);
        addCard("Fireball", "Mage deals 1 damage to 2nd enemy in line.", 8);
        addCard("Magic Blast", "Mage deals 3 damage.", 4);
        addCard("Thunder", "Mage deals 1 damage to every enemy troop.", 2);

        // King cards
        addCard("Jab", "King deals 1 damage.", 8);
        addCard("Hook", "King deals 2 damage.", 4);
        addCard("Straight", "King deals 3 damage.", 2);

        // Special cards
        addCard("Tank Revive", "Revive the Tank. (3 cards needed)", 8);
        addCard("Warrior Revive", "Revive the Warrior. (3 cards needed)", 8);
        addCard("Mage Revive", "Revive the Mage. (3 cards needed)", 8);
        addCard("Joker", "Double effect of next card drawn. (not applicable to joker and revive cards)", 4);

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
