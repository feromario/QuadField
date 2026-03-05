import java.util.ArrayList;

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

    // checks if a troop is alive (helper)
    private static boolean isTroopAlive(Player player, String troopName) {
        for (Troop t : player.squad) {
            if (t.name.equals(troopName) && t.isAlive()) {
                return true;
            }
        }
        return false;
    }

    // Contains switch statement for cards drawn
    public static void applyEffect(Card drawn, Player attacker, Player defender) {
        switch (drawn.name) {
            // Using arrow syntax instead of the break syntax
            // Tank cases
            case "Dash" -> dash(attacker, defender);
            case "Heal" -> heal(attacker, defender);
            case "Necromancer" -> necromancer(attacker, defender);
            case "Life Steal" -> lifeSteal(attacker, defender);

            // Warrior cases
            case "Slash" -> slash(attacker, defender);
            case "Swing" -> swing(attacker, defender);
            case "Inspire" -> inspire(attacker, defender);
            case "Cleave" -> cleave(attacker, defender);

            // Mage cases
            case "Support" -> support(attacker, defender);
            case "Fireball" -> fireball(attacker, defender);
            case "Magic Blast" -> magicBlast(attacker, defender);
            case "Thunder" -> thunder(attacker, defender);

            // King cases
            case "Jab" -> jab(attacker, defender);
            case "Hook" -> hook(attacker, defender);
            case "Straight" -> straight(attacker, defender);

            // Special cases
            case "Tank Revive" -> revive(drawn, attacker, defender);
            case "Warrior Revive" -> revive(drawn, attacker, defender);
            case "Mage Revive" -> revive(drawn, attacker, defender);
            case "Joker" -> joker(attacker, defender);
        }
    }

    // ─────────────── TANK ─────────────────────────────────────────────────────────────────────
    public static void dash(Player attacker, Player defender) {
        // check
        if (!isTroopAlive(attacker, "Tank")) {
            System.out.println("Troop is dead, turn skipped!");
            return;
        }
        // effect
        System.out.println("Dash! 1 dmg");
        damageFirstInLine(defender, 1);
    }

    public static void heal(Player attacker, Player defender) {
        // check
        if (!isTroopAlive(attacker, "Tank")) {
            System.out.println("Troop is dead, turn skipped!");
            return;
        }
        // effect
        System.out.println("Heal! restore 1 hp to itself");
        for (Troop t : attacker.squad) {
            if (t.name.equals("Tank") && t.isAlive()) {
                t.health = Math.min(MAX_HEALTH, t.health + 1);
                System.out.println("Tank HP: " + t.health);
                return;
            }
        }
    }

    public static void lifeSteal(Player attacker, Player defender) {
        // check
        if (!isTroopAlive(attacker, "Tank")) {
            System.out.println("Troop is dead, turn skipped!");
            return;
        }
        // effect
        System.out.println("Life Steal! steal 1hp from enemy");

        // find the first alive enemy troop
        Troop target = null;
        for (Troop t : defender.squad) {
            if (t.isAlive()) {
                target = t;
                break;
            }
        }

        // find the friendly tank
        Troop tank = null;
        for (Troop t : attacker.squad) {
            if (t.name.equals("Tank") && t.isAlive()) {
                tank = t;
                break;
            }
        }

        // steal the 1 hp
        if (target != null && tank != null) {
            target.takeDamage(1);
            tank.health = Math.min(MAX_HEALTH, tank.health + 1);
            System.out.println(target.name + " lost 1 HP! HP: " + target.health);
            System.out.println("Tank gained 1 HP! HP: " + tank.health);
        } else if (target == null) {
            System.out.println("No enemy troops!");
        } else {
            System.out.println("Tank is dead, cannot heal!");
        }
    }

    public static void necromancer(Player attacker, Player defender) {
        // check
        if (!isTroopAlive(attacker, "Tank")) {
            System.out.println("Troop is dead, turn skipped!");
            return;
        }
        // effect
        System.out.println("Necromancer! spawns foot soldier");
        Troop soldier = new Troop("Soldier", 1);

        // find index of Tank and insert Soldier in front
        for (int i = 0; i < attacker.squad.size(); i++) {
            if (attacker.squad.get(i).name.equals("Tank") && attacker.squad.get(i).isAlive()) {
                attacker.squad.add(i, soldier); // shifts index to the right
                System.out.println("A soldier with 1 hp gets in front!");
                return;
            }
        }
    }

    // ─────────────── WARRIOR ─────────────────────────────────────────────────────────────────────
    public static void slash(Player attacker, Player defender) {
        // check
        if (!isTroopAlive(attacker, "Warrior")) {
            System.out.println("Troop is dead, turn skipped!");
            return;
        }
        // effect
        System.out.println("Slash! 2 dmg");
        damageFirstInLine(defender, 2);
    }

    public static void cleave(Player attacker, Player defender) {
        // check
        if (!isTroopAlive(attacker, "Warrior")) {
            System.out.println("Troop is dead, turn skipped!");
            return;
        }
        // effect
        System.out.println("Cleave! 2 dmg");
        damageFirstInLine(defender, 4);
    }

    public static void swing(Player attacker, Player defender) {
        // check
        if (!isTroopAlive(attacker, "Warrior")) {
            System.out.println("Troop is dead, turn skipped!");
            return;
        }
        // effect
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
        // check
        if (!isTroopAlive(attacker, "Warrior")) {
            System.out.println("Troop is dead, turn skipped!");
            return;
        }
        // effect
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

    // ─────────────── MAGE ─────────────────────────────────────────────────────────────────────
    public static void magicBlast(Player attacker, Player defender) {
        // check
        if (!isTroopAlive(attacker, "Mage")) {
            System.out.println("Troop is dead, turn skipped!");
            return;
        }
        // effect
        System.out.println("Magic Blast! 2 dmg");
        damageFirstInLine(defender, 3);
    }

    public static void thunder(Player attacker, Player defender) {
        // check
        if (!isTroopAlive(attacker, "Mage")) {
            System.out.println("Troop is dead, turn skipped!");
            return;
        }
        // effect
        System.out.println("Thunder! 1 dmg to every troop");
        for (Troop t : defender.squad) {
            if (t.isAlive()) {
                t.takeDamage(1);
                System.out.println(t.name + " took " + 1 + " damage! HP: " + t.health);
            }
        }
    }

    public static void support(Player attacker, Player defender) {
        // check
        if (!isTroopAlive(attacker, "Mage")) {
            System.out.println("Troop is dead, turn skipped!");
            return;
        }
        // effect
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

    public static void fireball(Player attacker, Player defender) {
        // check
        if (!isTroopAlive(attacker, "Mage")) {
            System.out.println("Troop is dead, turn skipped!");
            return;
        }
        // effect
        System.out.println("Fireball! 1 dmg to second enemy in line");

        // collecting list of all alive troop currently
        ArrayList<Troop> aliveTroops = new ArrayList<>();
        for (Troop t : defender.squad) {
            if (t.isAlive()) {
                aliveTroops.add(t);
            }
        }

        if (aliveTroops.size() >= 2) { // checks if there are more than 2 troops
            aliveTroops.get(1).takeDamage(1);
            System.out.println(aliveTroops.get(1).name + " took 1 damage! HP: " + aliveTroops.get(1).health);

        } else if (aliveTroops.size() == 1) { // only king left
            aliveTroops.get(0).takeDamage(1);
            System.out.println(aliveTroops.get(0).name + " took 1 damage! HP: " + aliveTroops.get(0).health);
        } else {
            System.out.println("No troops left!");
        }
    }

    // ─────────────── KING ─────────────────────────────────────────────────────────────────────
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

    // ─────────────── SPECIAL ─────────────────────────────────────────────────────────────────────
    public static void revive(Card drawn, Player attacker, Player defender) {
        // save the card to player's hand
        attacker.hand.add(drawn);
        System.out.println(attacker.name + " drew " + drawn.name);

        // count how many of this revive type the player has
        int count = 0;
        for (Card c : attacker.hand) {
            if (c.name.equals(drawn.name)) {
                count++;
            }
        }
        System.out.println(attacker.name + " has " + count + " " + drawn.name);

        // check if player has at least 3 revives of the same type
        if (count >= 3) {
            // which troop the revive belongs too
            String troopName = drawn.name.replace(" Revive", "");

            // only revive if that troop is dead
            if (!isTroopAlive(attacker, troopName)) {
                System.out.println("Revive " + troopName + "? y/n");
                String input = MainQuadField.scan.nextLine();

                if (input.equals("y")) {
                    // remove those 3 cards
                    int removed = 0;
                    for (int i = attacker.hand.size() - 1; i >= 0 && removed < 3; i--) {
                        if (attacker.hand.get(i).name.equals(drawn.name)) {
                            attacker.hand.remove(i);
                            removed++;
                        }
                    }
                    // revive the troop
                    for (Troop t : attacker.squad) {
                        if (t.name.equals(troopName)) {
                            // decides what health to give each troop, hardcoded
                            if (troopName.equals("Tank")) {
                                t.health = 5;
                            } else if (troopName.equals("Warrior")) {
                                t.health = 4;
                            } else if (troopName.equals("Mage")) {
                                t.health = 3;
                            }

                            System.out.println(troopName + " revived!");
                            return;
                        }
                    }

                } else {
                    System.out.println("Not enough. Cards saved for later.");
                }
            } else {
                System.out.println(troopName + " is alive. Cards saved for later.");
            }
        }
    }

    // currently same as Dash, subject to change
    public static void joker(Player attacker, Player defender) {
        // effect
        System.out.println("Dash! 1 dmg");
        damageFirstInLine(defender, 1);
    }



}








































