public class CardEffects {
    private static final int MAX_HEALTH = 5;

    // Deal damage to first enemy in line (helper)
    private static void damageFirstInLine(Player defender, int dmg) {
        for (Troop t : defender.squad) {
            if (t.isAlive()) {
                t.takeDamage(dmg);
                System.out.println(t.name + " took " + dmg + " damage! HP: " + t.health);
                return; // guarantees only the first enemy in line gets hit, exits loop
            }
        }
    }

    // Contains switch statement for cards drawn
    public static void applyEffect(Card drawn, Player attacker, Player defender) {
        switch (drawn.name) {
            // Using arrow syntax instead of the break syntax
            // Tank cases
            case "Dash" -> dash(attacker, defender);
            case "Heal" -> heal(attacker, defender);
            case "Necromancer" -> {
                System.out.println("Test 3");
            }
            case "Life Steal" -> {
                System.out.println("Test 4");
            }

            // Warrior cases
            case "Slash" -> slash(attacker, defender);
            case "Swing" -> swing(attacker, defender);
            case "Inspire" -> inspire(attacker, defender);
            case "Cleave" -> cleave(attacker, defender);

            // Mage cases
            case "Support" -> support(attacker, defender);
            case "Fireball" -> {
                System.out.println("Test 2");
            }
            case "Magic Blast" -> magicBlast(attacker, defender);
            case "Thunder" -> thunder(attacker, defender);

            // King cases
            case "Jab" -> jab(attacker, defender);
            case "Hook" -> hook(attacker, defender);
            case "Straight" -> straight(attacker, defender);

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
    }

    // Tank
    public static void dash(Player attacker, Player defender) {
        System.out.println("Dash! 1 dmg");
        damageFirstInLine(defender, 1);
    }

    public static void heal(Player attacker, Player defender) {
        System.out.println("Heal! restore 1 hp to itself");
        for (Troop t : attacker.squad) {
            if (t.name.equals("Tank") && t.isAlive()) {
                t.health = Math.min(MAX_HEALTH, t.health + 1);
                System.out.println("Tank HP: " + t.health);
                return;
            }
        }
    }

    // Warrior
    public static void slash(Player attacker, Player defender) {
        System.out.println("Slash! 2 dmg");
        damageFirstInLine(defender, 2);
    }

    public static void cleave(Player attacker, Player defender) {
        System.out.println("Cleave! 2 dmg");
        damageFirstInLine(defender, 4);
    }

    public static void swing(Player attacker, Player defender) {
        System.out.println("Swing! 1 dmg to first 2 enemies in line");
        int hits = 0; // to count 2 hits
        for (Troop t : defender.squad) {
            if (t.isAlive()) {
                t.takeDamage(1);
                System.out.println(t.name + " took 1 damage! HP: " + t.health);
                hits++;
                if (hits >= 2) {return;} // stops after hitting 2 troops
            }
        }
    }

    public static void inspire(Player attacker, Player defender) {
        System.out.println("Inspire! draw 2");
        for (int i = 0; i < 2; i++) {
            if (!MainQuadField.deck.isEmpty()) {
                Card drawn = MainQuadField.deck.draw();
                System.out.println(attacker.name + " drew " + drawn.name);
                applyEffect(drawn, attacker, defender); // applies card after draw
            } else {
                System.out.println("Deck is empty!");
                break;
            }
        }
    }

    // Mage
    public static void magicBlast(Player attacker, Player defender) {
        System.out.println("Magic Blast! 2 dmg");
        damageFirstInLine(defender, 3);
    }

    public static void thunder(Player attacker, Player defender) {
        System.out.println("Thunder! 1 dmg to every troop");
        for (Troop t : defender.squad) {
            if (t.isAlive()) {
                t.takeDamage(1);
                System.out.println(t.name + " took " + 1 + " damage! HP: " + t.health);
            }
        }
    }

    public static void support(Player attacker, Player defender) {
        System.out.println("Support! heal 1 hp to a troop");
        for (Troop t : attacker.squad) {
            if (t.isAlive() && !t.name.equals("Mage")) {
                t.health = Math.min(MAX_HEALTH, t.health + 1);
                System.out.println(t.name + " was healed! HP: " + t.health);
                return;
            }
        }
        System.out.println("No troop to heal!");
    }

    // King
    public static void jab(Player attacker, Player defender) {
        System.out.println("Jab! 1 dmg");
        damageFirstInLine(defender, 1);
    }

    public static void hook(Player attacker, Player defender) {
        System.out.println("Hook! 2 dmg");
        damageFirstInLine(defender, 2);
    }

    public static void straight(Player attacker, Player defender) {
        System.out.println("Straight! 3 dmg");
        damageFirstInLine(defender, 3);
    }
}
