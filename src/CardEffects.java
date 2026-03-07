import java.util.ArrayList;
// for gui: all SOP's replaced with MainQuadField.battleLog.append to add them to the battle log text area

public class CardEffects {
    private static final int MAX_HEALTH = 5;

    // Deal damage to first enemy in line (helper)
    private static void damageFirstInLine(Player defender, int dmg) {
        for (Troop t : defender.squad) {
            if (t.isAlive()) {
                t.takeDamage(dmg);
                MainQuadField.battleLog.append(t.name + " took " + dmg + " damage! HP: " + t.health);
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

    // joker card
    public static void joker(Player attacker, Player defender) {
        MainQuadField.battleLog.append("Joker! next card effect doubled");
        attacker.jokerActive = true;
        Card drawn = MainQuadField.deck.draw();
        MainQuadField.battleLog.append("Drawn Card: " + drawn.name);
        applyEffect(drawn, attacker, defender);
    }

    // Contains switch statement for cards drawn
    public static void applyEffect(Card drawn, Player attacker, Player defender) {
        // check for joker
        if (drawn.name.equals("Joker")) {
            joker(attacker, defender);
            return;
        }
        int mult = attacker.jokerActive ? 2 : 1;
        attacker.jokerActive = false; // reset after every card draw

        // switch to card
        switch (drawn.name) {
            // Using arrow syntax instead of the break syntax
            // Tank cases
            case "Dash" -> dash(attacker, defender, mult);
            case "Heal" -> heal(attacker, defender, mult);
            case "Necromancer" -> necromancer(attacker, defender, mult);
            case "Life Steal" -> lifeSteal(attacker, defender, mult);

            // Warrior cases
            case "Slash" -> slash(attacker, defender, mult);
            case "Swing" -> swing(attacker, defender, mult);
            case "Inspire" -> inspire(attacker, defender, mult);
            case "Cleave" -> cleave(attacker, defender, mult);

            // Mage cases
            case "Support" -> support(attacker, defender, mult);
            case "Fireball" -> fireball(attacker, defender, mult);
            case "Magic Blast" -> magicBlast(attacker, defender, mult);
            case "Thunder" -> thunder(attacker, defender, mult);

            // King cases
            case "Jab" -> jab(attacker, defender, mult);
            case "Hook" -> hook(attacker, defender, mult);
            case "Straight" -> straight(attacker, defender, mult);

            // Special cases
            case "Tank Revive" -> revive(drawn, attacker, defender);
            case "Warrior Revive" -> revive(drawn, attacker, defender);
            case "Mage Revive" -> revive(drawn, attacker, defender);
            // case "Joker" -> joker(attacker, defender);
        }
    }

    // ─────────────── TANK ─────────────────────────────────────────────────────────────────────
    public static void dash(Player attacker, Player defender, int mult) {
        // check
        if (!isTroopAlive(attacker, "Tank")) {
            MainQuadField.battleLog.append("Troop is dead, turn skipped!");
            return;
        }
        // effect
        MainQuadField.battleLog.append("Dash! " + (1 * mult) + " dmg");
        damageFirstInLine(defender, 1 * mult);
    }

    public static void heal(Player attacker, Player defender, int mult) {
        // check
        if (!isTroopAlive(attacker, "Tank")) {
            MainQuadField.battleLog.append("Troop is dead, turn skipped!");
            return;
        }
        // effect
        MainQuadField.battleLog.append("Heal! restore " + (1 * mult) + " hp to itself");
        for (Troop t : attacker.squad) {
            if (t.name.equals("Tank") && t.isAlive()) {
                t.health = Math.min(MAX_HEALTH, t.health + (1 * mult));
                MainQuadField.battleLog.append("Tank HP: " + t.health);
                return;
            }
        }
    }

    public static void lifeSteal(Player attacker, Player defender, int mult) {
        // check
        if (!isTroopAlive(attacker, "Tank")) {
            MainQuadField.battleLog.append("Troop is dead, turn skipped!");
            return;
        }
        // effect
        MainQuadField.battleLog.append("Life Steal! steal 1hp from enemy");

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
            target.takeDamage(1 * mult);
            tank.health = Math.min(MAX_HEALTH, tank.health + (1 * mult));
            MainQuadField.battleLog.append(target.name + " lost " + (1 * mult) + " HP! HP: " + target.health);
            MainQuadField.battleLog.append("Tank gained " + (1 * mult) + " HP! HP: " + tank.health);
        } else if (target == null) {
            MainQuadField.battleLog.append("No enemy troops!");
        } else {
            MainQuadField.battleLog.append("Tank is dead, cannot heal!");
        }
    }

    public static void necromancer(Player attacker, Player defender, int mult) {
        // check
        if (!isTroopAlive(attacker, "Tank")) {
            MainQuadField.battleLog.append("Troop is dead, turn skipped!");
            return;
        }
        // effect
        MainQuadField.battleLog.append("Necromancer! spawns foot soldier");

        for (int i = 0; i < mult; i++) {
            Troop soldier = new Troop("Soldier", 1);
            // find index of Tank and insert Soldier in front
            for (int j = 0; j < attacker.squad.size(); j++) {
                if (attacker.squad.get(j).name.equals("Tank") && attacker.squad.get(j).isAlive()) {
                    attacker.squad.add(j, soldier); // shifts index to the right
                    MainQuadField.battleLog.append("A soldier with 1 hp gets in front!");
                    return;
                }
            }
        }
    }

    // ─────────────── WARRIOR ─────────────────────────────────────────────────────────────────────
    public static void slash(Player attacker, Player defender, int mult) {
        // check
        if (!isTroopAlive(attacker, "Warrior")) {
            MainQuadField.battleLog.append("Troop is dead, turn skipped!");
            return;
        }
        // effect
        MainQuadField.battleLog.append("Slash! " + (2 * mult) + " dmg");
        damageFirstInLine(defender, 2 * mult);
    }

    public static void cleave(Player attacker, Player defender, int mult) {
        // check
        if (!isTroopAlive(attacker, "Warrior")) {
            MainQuadField.battleLog.append("Troop is dead, turn skipped!");
            return;
        }
        // effect
        MainQuadField.battleLog.append("Cleave! " + (4 * mult) + " dmg");
        damageFirstInLine(defender, 4 * mult);
    }

    public static void swing(Player attacker, Player defender, int mult) {
        // check
        if (!isTroopAlive(attacker, "Warrior")) {
            MainQuadField.battleLog.append("Troop is dead, turn skipped!");
            return;
        }
        // effect
        MainQuadField.battleLog.append("Swing! " + (1 * mult) + " dmg to first 2 enemies in line");
        int hits = 0; // to count 2 hits
        for (Troop t : defender.squad) {
            if (t.isAlive()) {
                t.takeDamage(1 * mult);
                MainQuadField.battleLog.append(t.name + " took " + (1 * mult) + " damage! HP: " + t.health);
                hits++;
                if (hits >= 2) {return;} // stops after hitting 2 troops
            }
        }
    }

    public static void inspire(Player attacker, Player defender, int mult) {
        // check
        if (!isTroopAlive(attacker, "Warrior")) {
            MainQuadField.battleLog.append("Troop is dead, turn skipped!");
            return;
        }
        // effect
        int cardsToDraw = 2 * mult;
        MainQuadField.battleLog.append("Inspire! draw " + cardsToDraw);
        for (int i = 0; i < cardsToDraw; i++) {
            if (!MainQuadField.deck.isEmpty()) {
                Card drawn = MainQuadField.deck.draw();
                MainQuadField.battleLog.append(attacker.name + " drew " + drawn.name);
                applyEffect(drawn, attacker, defender); // applies card after draw
            } else {
                MainQuadField.battleLog.append("Deck is empty!");
                break;
            }
        }
    }

    // ─────────────── MAGE ─────────────────────────────────────────────────────────────────────
    public static void magicBlast(Player attacker, Player defender, int mult) {
        // check
        if (!isTroopAlive(attacker, "Mage")) {
            MainQuadField.battleLog.append("Troop is dead, turn skipped!");
            return;
        }
        // effect
        MainQuadField.battleLog.append("Magic Blast! " + (3 * mult) + " dmg");
        damageFirstInLine(defender, 3 * mult);
    }

    public static void thunder(Player attacker, Player defender, int mult) {
        // check
        if (!isTroopAlive(attacker, "Mage")) {
            MainQuadField.battleLog.append("Troop is dead, turn skipped!");
            return;
        }
        // effect
        MainQuadField.battleLog.append("Thunder! " + (1 * mult) + " dmg to every troop");
        for (Troop t : defender.squad) {
            if (t.isAlive()) {
                t.takeDamage(1 * mult);
                MainQuadField.battleLog.append(t.name + " took " + (1 * mult) + " damage! HP: " + t.health);
            }
        }
    }

    public static void support(Player attacker, Player defender, int mult) {
        // check
        if (!isTroopAlive(attacker, "Mage")) {
            MainQuadField.battleLog.append("Troop is dead, turn skipped!");
            return;
        }
        // effect
        MainQuadField.battleLog.append("Support! heal " + (1 * mult) + " hp to a troop");
        for (Troop t : attacker.squad) {
            if (t.isAlive() && !t.name.equals("Mage")) {
                t.health = Math.min(MAX_HEALTH, t.health + (1 * mult));
                MainQuadField.battleLog.append(t.name + " was healed! HP: " + t.health);
                return;
            }
        }
        MainQuadField.battleLog.append("No troop to heal!");
    }

    public static void fireball(Player attacker, Player defender, int mult) {
        // check
        if (!isTroopAlive(attacker, "Mage")) {
            MainQuadField.battleLog.append("Troop is dead, turn skipped!");
            return;
        }
        // effect
        MainQuadField.battleLog.append("Fireball! " + (1 * mult) + " dmg to second enemy in line");

        // collecting list of all alive troop currently
        ArrayList<Troop> aliveTroops = new ArrayList<>();
        for (Troop t : defender.squad) {
            if (t.isAlive()) {
                aliveTroops.add(t);
            }
        }

        if (aliveTroops.size() >= 2) { // checks if there are more than 2 troops
            aliveTroops.get(1).takeDamage(1 * mult);
            MainQuadField.battleLog.append(aliveTroops.get(1).name + " took " + (1 * mult) +" damage! HP: " + aliveTroops.get(1).health);

        } else if (aliveTroops.size() == 1) { // only king left
            aliveTroops.get(0).takeDamage(1 * mult);
            MainQuadField.battleLog.append(aliveTroops.get(0).name + " took " + (1 * mult) + " damage! HP: " + aliveTroops.get(0).health);
        } else {
            MainQuadField.battleLog.append("No troops left!");
        }
    }

    // ─────────────── KING ─────────────────────────────────────────────────────────────────────
    public static void jab(Player attacker, Player defender, int mult) {
        MainQuadField.battleLog.append("Jab! " + (1 * mult) + " dmg");
        damageFirstInLine(defender, 1 * mult);
    }

    public static void hook(Player attacker, Player defender, int mult) {
        MainQuadField.battleLog.append("Hook! " + (2 * mult) + " dmg");
        damageFirstInLine(defender, 2 * mult);
    }

    public static void straight(Player attacker, Player defender, int mult) {
        MainQuadField.battleLog.append("Straight! " + (3 * mult) + " dmg");
        damageFirstInLine(defender, 3 * mult);
    }

    // ─────────────── SPECIAL ─────────────────────────────────────────────────────────────────────
    public static void revive(Card drawn, Player attacker, Player defender) {
        // save the card to player's hand
        attacker.hand.add(drawn);
        MainQuadField.battleLog.append(attacker.name + " drew " + drawn.name);

        // count how many of this revive type the player has
        int count = 0;
        for (Card c : attacker.hand) {
            if (c.name.equals(drawn.name)) {
                count++;
            }
        }
        MainQuadField.battleLog.append(attacker.name + " has " + count + " " + drawn.name);

        // check if player has at least 3 revives of the same type
        if (count >= 3) {
            // which troop the revive belongs too
            String troopName = drawn.name.replace(" Revive", "");

            // only revive if that troop is dead
            if (!isTroopAlive(attacker, troopName)) {
                MainQuadField.battleLog.append("Revive " + troopName + "? y/n");
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

                            MainQuadField.battleLog.append(troopName + " revived!");
                            return;
                        }
                    }

                } else {
                    MainQuadField.battleLog.append("Not enough. Cards saved for later.");
                }
            } else {
                MainQuadField.battleLog.append(troopName + " is alive. Cards saved for later.");
            }
        }
    }




}








































