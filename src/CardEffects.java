public class CardEffects {

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

    // Warrior
    public static void slash(Player attacker, Player defender) {
        System.out.println("Slash! 2 dmg");
        damageFirstInLine(defender, 2);
    }

    public static void cleave(Player attacker, Player defender) {
        System.out.println("Cleave! 2 dmg");
        damageFirstInLine(defender, 4);
    }


}
