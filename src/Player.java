import java.util.ArrayList;

public class Player {

    // Class variables
    public String name;
    public ArrayList<Troop> squad;
    public ArrayList<Card> hand = new ArrayList<>();
    public boolean jokerActive = false;

    // Constructor
    public Player(String name) {
        this.name = name;
        this.squad = new ArrayList<>();
        squad.add(new Troop("Tank", 5));
        squad.add(new Troop("Warrior", 4));
        squad.add(new Troop("Mage", 3));
        squad.add(new Troop("King", 5));
    }

    // Checks if the King troop is alive, to decide when game is over
    public boolean isGameOver() {
        for (Troop t : squad) {
            if (t.name.equals("King")) {
                return !t.isAlive();
            }
        }
        return true; // if King not found at all, game is over
    }

}
