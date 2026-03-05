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
