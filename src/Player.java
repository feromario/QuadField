import java.util.HashMap;
import java.util.Map;

public class Player {
    private String name;
    private Map<String, Troop> squad = new HashMap<>();
    private int mult = 1;

    public Player(String name) {
        this.name = name;
        squad.put("Tank", new Troop("Tank", 5));
        squad.put("Warrior", new Troop("Warrior", 4));
        squad.put("Mage", new Troop("Mage", 3));
        squad.put("King", new Troop("King", 5));
    }

    public boolean isKingAlive() {
        return squad.get("King").getIsAlive();
    }

}
