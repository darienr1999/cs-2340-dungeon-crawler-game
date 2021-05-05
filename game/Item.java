package game;


import javafx.scene.image.ImageView;

public abstract class Item implements Tickable {
    private ImageView sprite;
    protected Inventory inventory;
    private String name;

    public Item(String name, ImageView sprite, Inventory inventory) {
        this.name = name;
        this.sprite = sprite;
        this.inventory = inventory;
    }

    public abstract void use();

    public ImageView getSprite() {
        return this.sprite;
    }

    public void setSprite(ImageView sprite) {
        this.sprite = sprite;
    }

    public String getName() {
        return name;
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public void tick() {

    }
}
