package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Boss extends Minion {

    public Boss(Room room, Player player, short x, short y) {
        super(room, player, (short) 0, x, y);
        this.sprite = new ImageView(new Image("res/Sprites/Boss/BossFull.png"));
        this.damage = 3;
        this.speed = 2;
        this.health = 100;
    }
    public void tick() {
        System.out.println(x + " " + y);
        if (health <= 75 && health > 50) {
            sprite.setImage(new Image("res/Sprites/Boss/Boss3_4.png"));
        } else if (health <= 50 && health >= 25) {
            sprite.setImage(new Image("res/Sprites/Boss/Boss1_2.png"));
        } else if (health <= 25 && health > 0) {
            sprite.setImage(new Image("res/Sprites/Boss/Boss1_4.png"));
        } else if (health <= 0) {
            this.sprite.setVisible(false);
            room.getGrid().getChildren().remove(this);
            room.getTickablesToRemove().add(this);
        }

        double dirX = player.getX() - getX();
        double dirY = player.getY() - getY();
        //prevent division by 0
        double magnitude = Math.sqrt(Math.pow(dirX, 2) + Math.pow(dirY, 2)) + 0.0001;
        dirX = speed * dirX / magnitude;
        dirY = speed * dirY / magnitude;

        setX((short) (getX() + dirX));
        setY((short) (getY() + dirY));

        //sprite.setTranslateX(getX() + 580);
        //sprite.setTranslateY(getY() + 430);
        sprite.setTranslateX(getX());
        sprite.setTranslateY(getY());
    }
}
