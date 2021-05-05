package game;

import javafx.scene.image.ImageView;

public class Potion extends Item {
    private short effect;
    private short type;
    private Player player;

    public Potion(String name, ImageView sprite, Inventory inventory,
                  Player player, short type, short effect) {
        super(name, sprite, inventory);
        this.type = type;
        this.effect = effect;
        this.player = player;
    }

    @Override
    public void use() {
        switch (type) {
        //healing
        case 1:
            this.player.setHealth((short) (this.effect + this.player.getHealth()));
            break;
        //damage
        case 2:
            this.player.setDamageMultiplier((short) 2);
            break;
        //lol
        case 3:
            this.player.setHealth((short) 0);
            break;
        default:
            System.out.println("Default Potion, uh oh");
            break;
        }
        this.inventory.takeOut(this);
        System.out.println("Player used potion!");
    }
}
