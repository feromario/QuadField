import javax.smartcardio.Card;
import java.util.ArrayList;

public class Deck {
    private String linkedTroop;
    private int power;

    public Deck(String linkedTroop, int power) {
        this.linkedTroop = linkedTroop;
        this.power = power;
    }
}
