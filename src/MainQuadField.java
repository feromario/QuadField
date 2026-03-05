import java.util.Scanner;

public class MainQuadField {

    // Creating instances
    static Scanner scan = new Scanner(System.in);
    static Player player1;
    static Player player2;
    static Deck deck = new Deck();

    // Turn logic and game loop
    public static void gameLoop() {

        // Player 1 starts the game
        boolean isPlayer1Turn = true;

        // Runs while isGamesOver is false
        while (!isGameOver()) {
            // If true, player 1 executes turn, else player 2
            if (isPlayer1Turn) {
                takeTurn(player1, player2);
            } else {
                takeTurn(player2, player1);
            }

            // Switches boolean value, to switch turns
            isPlayer1Turn = !isPlayer1Turn;
        }
        System.out.println("Game Over");
    }

    // Display
    public static void printStatus() {
        System.out.println("─".repeat(30));
        for (int i = 0; i < 2; i++) {
            Player p = i == 0 ? player1 : player2;
            System.out.println(p.name + "'s squad:");
            for (Troop t : p.squad) {
                if (t.isAlive()) {
                    System.out.println("   " + t.name + " -> " + t.health + " health.");
                } else {
                    System.out.println("   " + t.name + " is dead.");
                }
            }
            System.out.println("─".repeat(30));
        }
    }

    // What happens in a turn, helper method
    public static void takeTurn(Player attacker, Player defender) {
        Card drawn = deck.draw();
        System.out.println(attacker.name + " drew: " + drawn.name);
        CardEffects.applyEffect(drawn, attacker, defender);

        // Display after each turn
        printStatus();

        // Pause before next turn
        System.out.println("Press enter for " + defender.name + "'s turn...");
        scan.nextLine();

    }

    // Game end method
    public static boolean isGameOver() {

        // Checks whether the game is over for either player to end game loop
        if (player1.isGameOver() || player2.isGameOver())  {
            return true;
        } else  {
            return false;
        }
    }

    // Main method
    public static void main(String[] args) {

        // Get player names
        System.out.println("Enter name of player 1: ");
        player1 = new Player(scan.nextLine());
        System.out.println("Enter name of player 2: ");
        player2 = new Player(scan.nextLine());

        // Shuffle deck to start game
        deck.shuffle();

        // Start game loop
        gameLoop();



    }

}


// TODO: joker method, improve display again, test gameplay, gui, executable
