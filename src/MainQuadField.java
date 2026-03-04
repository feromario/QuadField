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

    // What happens in a turn, helper method
    public static void takeTurn(Player attacker, Player defender) {
        Card drawn = deck.draw();
        System.out.println(attacker.name + " drew: " + drawn.name);

        // Switch statement to check for card
        switch (drawn.name) {
            // Using arrow syntax instead of the break syntax
            // Tank cases
            case "Dash" -> {
                System.out.println("Test 1");
                // CardEffects.dash(attacker, defender);
            }
            case "Heal" -> {
                System.out.println("Test 2");
            }
            case "Necromancer" -> {
                System.out.println("Test 3");
            }
            case "Life Steal" -> {
                System.out.println("Test 4");
            }

            // Warrior cases
            case "Slash" -> {
                System.out.println("Test 1");
            }
            case "Swing" -> {
                System.out.println("Test 2");
            }
            case "Inspire" -> {
                System.out.println("Test 3");
            }
            case "Cleave" -> {
                System.out.println("Test 4");
            }

            // Mage cases
            case "Support" -> {
                System.out.println("Test 1");
            }
            case "Fireball" -> {
                System.out.println("Test 2");
            }
            case "Magic Blast" -> {
                System.out.println("Test 3");
            }
            case "Thunder" -> {
                System.out.println("Test 4");
            }

            // King cases
            case "Jab" -> {
                System.out.println("Test 1");
            }
            case "Hook" -> {
                System.out.println("Test 2");
            }
            case "Straight" -> {
                System.out.println("Test 3");
            }

            // Special cases
            case "Revive Tank" -> {
                System.out.println("Test 1");
            }
            case "Revive Warrior" -> {
                System.out.println("Test 2");
            }
            case "Revive Mage" -> {
                System.out.println("Test 3");
            }
            case "Joker" -> {
                System.out.println("Test 4");
            }

        }

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


// TODO: 2. work on CardEffects class 3. display results better
