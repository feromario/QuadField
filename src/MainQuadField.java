

public class MainQuadField {

    // Static

    // Turn logic and game loop
    public static void gameLoop() {

        // Player 1 starts the game
        boolean isPlayer1Turn = true;

        // Runs while isGamesOver is false
        while (!isGameOver()) {
            // if true, player 1 executes turn, else player 2
            if (isPlayer1Turn) {
                // p1 takes turn
            } else {
                // p2 takes turn
            }

            // Switches boolean value, to switch turns
            isPlayer1Turn = !isPlayer1Turn;
        }
    }

    public static void main(String[] args) {






    }


}
