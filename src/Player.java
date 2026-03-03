import java.util.ArrayList;

public class Player {

    // Class variables
    private String name;
    private ArrayList<Troop> squad;

    // Constructor
    public Player(String name) {
        this.name = name;
        squad.add(new Troop("Tank", 5));
        squad.add(new Troop("Warrior", 4));
        squad.add(new Troop("Mage", 3));
        squad.add(new Troop("King", 5));
    }

    // Checks if the King troop is alive, to decide when game is over
    public boolean isGameOver() {
        if (!squad.get(3).isAlive()) {
            return true;
        } else {
            return false;
        }

    }

}
