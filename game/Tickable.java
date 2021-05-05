package game;


import javafx.scene.image.ImageView;

public interface Tickable {
    void tick();
    default ImageView getSprite() {
        System.out.println("Method to make tickables addable to the screen "
                + "even after the room is already shown");
        return new ImageView();
    }
}
