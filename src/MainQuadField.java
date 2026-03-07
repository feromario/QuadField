import java.awt.*;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.BorderLayout;

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

    // calculate hp differential at the end of the game
    public static int calcHPD(Player winner, Player loser) {
        int winnerHP = 0;
        int loserHP = 0;

        for (Troop t : winner.squad) {
            winnerHP += t.health;
        }

        for (Troop t : loser.squad) {
            loserHP  += t.health;
        }

        int HPD = winnerHP - loserHP;
        return HPD;
    }

    // Game end method
    public static boolean isGameOver() {
        // shows winner and hpd
        if (player1.isGameOver()) {
            int HPD = calcHPD(player2, player1);
            System.out.println(player2.name + " wins.");
            System.out.println("HPD: " + HPD);
        } else if (player2.isGameOver()) {
            int HPD = calcHPD(player1, player2);
            System.out.println(player1.name + " wins.");
            System.out.println("HPD: " + HPD);
        }

        // Checks whether the game is over for either player to end game loop
        if (player1.isGameOver() || player2.isGameOver())  {
            return true;
        } else  {
            return false;
        }
    }

    // ----- SWING GUI ----------------------------------------------------------------------------
    // JFrame - main window
    // JPanel - container that holds other components
    // JLabel - displays text
    // JButton - a clickable button
    // JTextField - text input box



    // --------------------------------------------------------------------------------------------

    // Main method
    public static void main(String[] args) {

        // ----- SWING GUI ------------------------------------------------------------------------
        JFrame frame = new JFrame("Quad Field");
        frame.setSize(800, 600); // width x height
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // close program with window

        // Instances
        JPanel gamePanel = new JPanel(new BorderLayout()); // makes it possible to use zones
        JLabel turnLabel = new JLabel("", JLabel.CENTER);

        // Container
        JPanel panel = new JPanel();

        // Player 1
        JLabel label1 = new JLabel("Player 1 name:");
        JTextField field1 = new JTextField(15); // 15 is the character width

        // Player 1
        JLabel label2 = new JLabel("Player 2 name:");
        JTextField field2 = new JTextField(15); // 15 is the character width

        JButton startButton = new JButton("Start Game");

        // add all elements to the panel
        panel.add(label1);
        panel.add(field1);
        panel.add(label2);
        panel.add(field2);
        panel.add(startButton);

        // add the panel to the frame
        frame.add(panel);
        frame.setVisible(true);

        // Creating players
        startButton.addActionListener(e -> {
            String name1 = field1.getText();
            String name2 = field2.getText();
            player1 = new Player(name1);
            player2 = new Player(name2);
            System.out.println("Players added: " + name1 + " vs " + name2);

            // switching panels
            frame.getContentPane().removeAll(); // clear the name panel
            frame.add(gamePanel); // add the game panel
            frame.revalidate(); // refresh frame
            turnLabel.setText("It is " + player1.name + "'s turn.");
        });

        // ------ Game Board -----------------------------
        // NORTH Section - Player's turn
        turnLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gamePanel.add(turnLabel, BorderLayout.NORTH);




        // ----------------------------------------------------------------------------------------

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


// TODO: gui, executable
