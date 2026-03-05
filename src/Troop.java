public class Troop {

    // Class variables
    public String name;
    public int health;

    // Constructor
    public Troop(String name, int health) {
        this.name = name;
        this.health = health;
    }

    // Checks if troop is alive, returns boolean
    public boolean isAlive() {
        return health > 0; // returns true if health is more than zero
    }

    // Method for the troop to take damage
    public void takeDamage(int damage) {
        // Math.max takes the larger of the two values
        // in case the health goes into negatives
        health = Math.max(0, health - damage);
    }

}
