// ***************************************** Imports ************************************************
import java.awt.*;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.SwingWorker;

public class MainQuadField {

    // ************************************** Main instances *******************************************
    static Scanner scan            = new Scanner(System.in);
    static Player player1;
    static Player player2;
    static JTextArea battleLog     = new JTextArea();
    static Deck deck               = new Deck();
    static boolean isPlayer1Turn   = true;
    static int round               = 1;

    // *********************************** North zone instances ****************************************
    static JPanel gamePanel        = new JPanel(new BorderLayout());
    static JLabel turnLabel        = new JLabel("", JLabel.CENTER);

    // *********************************** West zone instances *****************************************
    static JLabel p1NameLabel      = new JLabel();
    static JLabel p1SoldierLabel   = new JLabel();
    static JLabel p1TankLabel      = new JLabel();
    static JLabel p1WarriorLabel   = new JLabel();
    static JLabel p1MageLabel      = new JLabel();
    static JLabel p1KingLabel      = new JLabel();
    static JLabel p1TankRevive     = new JLabel();
    static JLabel p1WarriorRevive  = new JLabel();
    static JLabel p1MageRevive     = new JLabel();

    // *********************************** East zone instances *****************************************
    static JLabel p2NameLabel      = new JLabel();
    static JLabel p2SoldierLabel   = new JLabel();
    static JLabel p2TankLabel      = new JLabel();
    static JLabel p2WarriorLabel   = new JLabel();
    static JLabel p2MageLabel      = new JLabel();
    static JLabel p2KingLabel      = new JLabel();
    static JLabel p2TankRevive     = new JLabel();
    static JLabel p2WarriorRevive  = new JLabel();
    static JLabel p2MageRevive     = new JLabel();

    // *********************************** Center zone instances *****************************************
    // n/a

    // *********************************** South zone instances *****************************************
    static JLabel deckSizeLabel    = new JLabel();




    // *************************************** gameLoop() ***********************************************
    // Deals with turn logic (CONSOLE)
    public static void gameLoop() {

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

    // ************************************* printStatus() ***********************************************
    // Displays results (CONSOLE)
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

    // ************************************* takeTurn() *************************************************
    // Draws card from deck and calls applyEffect() (HELPER)
    public static void takeTurn(Player attacker, Player defender) {
        Card drawn = deck.draw();
        battleLog.append("─".repeat(90) + "\n");
        battleLog.append("***************\n");
        battleLog.append("# Round " + round + "\n");
        battleLog.append("***************\n");

        //battleLog.append(attacker.name + " drew " + drawn.name);
        //battleLog.append("\nEffect: " + drawn.effect + "\n");
        //battleLog.append("\n");

        CardEffects.applyEffect(drawn, attacker, defender);
        battleLog.append("\n\n");
        battleLog.append("─".repeat(90) + "\n");
        round++;
        updateDisplay();


        // Display after each turn
        //printStatus();

        // Pause before next turn
        //System.out.println("Press enter for " + defender.name + "'s turn...");
        //scan.nextLine();

    }

    // *************************************** calcHPD() **********************************************
    // Calculates health difference at end of game (HELPER)
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

    // *************************************** isGameOver() ******************************************
    // Calls isGameOver() method from Player class (HELPER)
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

    // #################################### SWING GUI METHODS #########################################
    // JFrame - main window
    // JPanel - container that holds other components
    // JLabel - displays text
    // JButton - a clickable button
    // JTextField - text input box

    // ************************************ updateDisplay() ********************************************
    // Displays updated label values (SWING)
    public static void updateDisplay() {
        updateTroopLabels(player1, p1SoldierLabel, p1TankLabel, p1WarriorLabel, p1MageLabel, p1KingLabel);
        updateTroopLabels(player2, p2SoldierLabel, p2TankLabel, p2WarriorLabel, p2MageLabel, p2KingLabel);

        p1TankRevive.setText("Tank Revive: " + countReviveCards(player1, "Tank Revive"));
        p1WarriorRevive.setText("Warrior Revive: " + countReviveCards(player1, "Warrior Revive"));
        p1MageRevive.setText("Mage Revive: " + countReviveCards(player1, "Mage Revive"));
        p2TankRevive.setText("Tank Revive: " + countReviveCards(player2, "Tank Revive"));
        p2WarriorRevive.setText("Warrior Revive: " + countReviveCards(player2, "Warrior Revive"));
        p2MageRevive.setText("Mage Revive: " + countReviveCards(player2, "Mage Revive"));

        gamePanel.revalidate();
        gamePanel.repaint();
    }

    // ********************************** updateTroopLabels() ****************************************
    // Updates troop labels (HELPER)
    private static void updateTroopLabels(Player player, JLabel soldierLabel, JLabel tankLabel, JLabel warriorLabel, JLabel mageLabel, JLabel kingLabel) {
        int soldierCount = 0;
        for (Troop t : player.squad) {
            switch (t.name) {
                case "Soldier" -> { if (t.isAlive()) soldierCount++; }
                case "Tank"    -> tankLabel.setText("Tank: " + getTroopStatus(t));
                case "Warrior" -> warriorLabel.setText("Warrior: " + getTroopStatus(t));
                case "Mage"    -> mageLabel.setText("Mage: " + getTroopStatus(t));
                case "King"    -> kingLabel.setText("King: " + getTroopStatus(t));
            }
        }

        if  (soldierCount > 0) {
            soldierLabel.setText("Soldier x" + soldierCount);
        } else  {
            soldierLabel.setText("");
        }
    }

    // ********************************* countReviveCards() *****************************************
    // Keeps count of revive cards (HELPER)
    private static int countReviveCards(Player player, String cardName) {
        int count = 0;
        for (Card c : player.hand) {
            if (c.name.equals(cardName)) {
                count++;
            }
        }
        return count;
    }

    // ********************************** getTroopStatus() *******************************************
    // Returns health of the troop in its parameter (HELPER)
    private static String getTroopStatus(Troop t) {
        if (t.isAlive()) {
            return t.health + "";
        } else {
            return "Dead";
        }
    }

    // ##################################### Main method #################################################
    public static void main(String[] args) {
        deck.shuffle();
        // ##################################### SWING GUI ##############################################
        // ------------------------------------ Frame --------------------------------------------------
        JFrame frame = new JFrame("Quad Field");
        frame.setSize(800, 600); // width x height
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // close program with window
        JPanel panel = new JPanel();

        // --------------------------------------- Players ----------------------------------------------
        JLabel label1 = new JLabel("Player 1 name:");
        JTextField field1 = new JTextField(15); // 15 is the character width

        JLabel label2 = new JLabel("Player 2 name:");
        JTextField field2 = new JTextField(15); // 15 is the character width

        JButton startButton = new JButton("Start Game");

        // ------------------------------------ Panel elements -----------------------------------------
        panel.add(label1);
        panel.add(field1);
        panel.add(label2);
        panel.add(field2);

        panel.add(startButton);
        frame.add(panel);
        frame.setVisible(true);

        // ------------------------------------- Game board ---------------------------------------------
        // ______________________________________ NORTH _________________________________________________
        // Keep track on whose turn it is
        turnLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gamePanel.add(turnLabel, BorderLayout.NORTH);

        // ________________________________________ WEST _________________________________________________
        // Display player 1's troops and hand
        JPanel player1Panel = new JPanel();
        player1Panel.setLayout(new BoxLayout(player1Panel, BoxLayout.Y_AXIS));

        player1Panel.add(p1NameLabel);
        player1Panel.add(p1SoldierLabel);
        player1Panel.add(p1TankLabel);
        player1Panel.add(p1WarriorLabel);
        player1Panel.add(p1MageLabel);
        player1Panel.add(p1KingLabel);
        player1Panel.add(new JLabel("-----------------"));
        player1Panel.add(new JLabel("Hand: "));
        player1Panel.add(p1TankRevive);
        player1Panel.add(p1WarriorRevive);
        player1Panel.add(p1MageRevive);

        gamePanel.add(player1Panel, BorderLayout.WEST);

        // __________________________________________ EAST ______________________________________________
        // Display player 2's troops and hand
        JPanel player2Panel = new JPanel();
        player2Panel.setLayout(new BoxLayout(player2Panel, BoxLayout.Y_AXIS));

        player2Panel.add(p2NameLabel);
        player2Panel.add(p2SoldierLabel);
        player2Panel.add(p2TankLabel);
        player2Panel.add(p2WarriorLabel);
        player2Panel.add(p2MageLabel);
        player2Panel.add(p2KingLabel);
        player2Panel.add(new JLabel("-----------------"));
        player2Panel.add(new JLabel("Hand: "));
        player2Panel.add(p2TankRevive);
        player2Panel.add(p2WarriorRevive);
        player2Panel.add(p2MageRevive);

        gamePanel.add(player2Panel, BorderLayout.EAST);

        // _______________________________________ CENTER ________________________________________________
        // Show battle log
        battleLog.setEditable(false); // not editable
        battleLog.setLineWrap(true); // wraps around if line is too long

        JScrollPane scrollPane = new JScrollPane(battleLog);
        gamePanel.add(scrollPane, BorderLayout.CENTER);

        // ______________________________________ SOUTH _________________________________________________
        // Houses the draw card button and shows how many cards are left
        JPanel southPanel = new JPanel(new FlowLayout()); // flowlayout - places elements horizontally
        JButton drawButton = new JButton("Draw Card");

        southPanel.add(deckSizeLabel);
        southPanel.add(drawButton);
        gamePanel.add(southPanel, BorderLayout.SOUTH);

        // EVENT LISTENER FOR DRAWING CARDS
        drawButton.addActionListener(e -> {
            drawButton.setEnabled(false);

            SwingWorker<Void, Void> worker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() {
                    if (isPlayer1Turn) {
                        takeTurn(player1, player2);
                    } else {
                        takeTurn(player2, player1);
                    }
                    return null;
                }

                @Override
                protected void done() {
                    deckSizeLabel.setText("Cards left: " + deck.size());
                    isPlayer1Turn = !isPlayer1Turn;
                    turnLabel.setText("It is " + (isPlayer1Turn ? player1.name : player2.name) + "'s turn.");
                    updateDisplay();
                    drawButton.setEnabled(true);
                }
            };
            worker.execute();
        });


        // EVENT LISTENER FOR STARTING GAME AND SETTING UP GAME BOARD
        startButton.addActionListener(e -> {
            // set up players
            String name1 = field1.getText();
            String name2 = field2.getText();
            player1 = new Player(name1);
            player2 = new Player(name2);
            System.out.println("Players added: " + name1 + " vs " + name2);

            // update player 1 labels
            p1NameLabel.setText(player1.name);
            p1TankLabel.setText("Tank: " + player1.squad.get(0).health);
            p1WarriorLabel.setText("Warrior: " + player1.squad.get(1).health);
            p1MageLabel.setText("Mage: " + player1.squad.get(2).health);
            p1KingLabel.setText("King: " + player1.squad.get(3).health);

            // update player 2 labels
            p2NameLabel.setText(player2.name);
            p2TankLabel.setText("Tank: " + player2.squad.get(0).health);
            p2WarriorLabel.setText("Warrior: " + player2.squad.get(1).health);
            p2MageLabel.setText("Mage: " + player2.squad.get(2).health);
            p2KingLabel.setText("King: " + player2.squad.get(3).health);

            // switching panels
            frame.getContentPane().removeAll(); // clear the name panel
            frame.add(gamePanel); // add the game panel
            frame.revalidate(); // refresh frame
            turnLabel.setText("It is " + player1.name + "'s turn.");
        });

        // -------------------------------- Run game on CONSOLE ---------------------------------------
        System.out.println("Enter name of player 1: ");
        player1 = new Player(scan.nextLine());
        System.out.println("Enter name of player 2: ");
        player2 = new Player(scan.nextLine());

        gameLoop();
    } // ############################## END OF MAIN METHOD ##############################################
}


// TODO: gui, executable
// Bugs: support method should ignore soldiers and heal troop behind
// Bugs: when 2 soldiers present, after swing gets called, somehow 1 solder is left alive