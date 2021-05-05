package game;

import javafx.scene.image.ImageView;

public class Weapon extends Item {
    private Player player;
    private int damage;
    private int swingTimer;
    private String name;

    public Weapon(String name, ImageView sprite, Inventory inventory, Player player, int damage) {
        super(name, sprite, inventory);
        this.player = player;
        this.damage = damage;
        this.name = name;
    }
    //SECOND CONSTRUCTOR USING SWING TIMERS
    public Weapon(String name, ImageView sprite, Inventory inventory,
                  Player player, int damage, int swingTimer) {
        super(name, sprite, inventory);
        this.player = player;
        this.damage = damage;
        this.swingTimer = swingTimer;
        this.name = name;
    }

    @Override
    public void use() {
        this.player.equipWeapon(this);
        this.inventory.takeOut(this);
        System.out.println("Player equipped weapon");
    }

    public int getDamage() {

        return damage;
    }
    public int getSwingTimer() {
        return swingTimer;
    }
    public String getName() {
        return name;

    }
}
