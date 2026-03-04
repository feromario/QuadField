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
            // if true, player 1 executes turn, else player 2
            if (isPlayer1Turn) {
                // p1 takes turn
                Card drawn = deck.draw();
            } else {
                // p2 takes turn
            }

            // Switches boolean value, to switch turns
            isPlayer1Turn = !isPlayer1Turn;
        }
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
        player1 = new Player("Player 1 enter name: " + scan.nextLine());
        player2 = new Player("Player 2 enter name: " + scan.nextLine());

        // Shuffle deck to start game
        deck.shuffle();





    }

}


// TODO: work on deck class and on the game loop
