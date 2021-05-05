package game;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class ItemButton extends Button {

    private Item item;

    public ItemButton(String text, ImageView sprite, Item item) {
        super(text, sprite);
        this.item = item;
    }

    public ItemButton(String text) {
        super(text);
        this.item = null;
    }

    public Item getItem() {
        return this.item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

}
