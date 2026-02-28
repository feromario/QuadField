public class Troop {
    private String type;
    private int health;
    private boolean isAlive;

    // GETTER
    public boolean getIsAlive() {
        return isAlive;
    }

    // SETTER
    // public void setType(String newType) { this.type = newType; }

    public Troop(String type, int health) {
        this.type = type;
        this.health = health;
    }

    public void takeDamage(int damage) {
        this.health -= damage;
        if (this.health <= 0) {
            this.health = 0;
            this.isAlive = false;
            System.out.println(type + " has fallen!");
        }
    }
}
