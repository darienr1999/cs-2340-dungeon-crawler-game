package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


import java.util.Random;

public class Minion implements Tickable {
    protected int speed;
    protected short health;
    protected short x;
    protected short y;
    protected ImageView sprite;
    protected Player player;
    private boolean projectileLaunched;
    private Projectile projectile;
    protected Room room;
    private short type;
    protected short damage;
    private int timer;
    private int compareTimer;
    private static short totalMonstersKilled = 0;

    public Minion(Room room, Player player, short type, short x, short y) {

        this.room = room;
        this.player = player;
        this.type = type;
        this.x = x;
        this.y = y;

        switch (type) {
        case 1:
            Image img1 = new Image("res/Sprites/Minion.png");
            this.sprite = new ImageView(img1);
            this.speed = 6;
            this.type = 1;
            this.damage = 1;
            health = 3;
            break;
        case 2:
            Image img2 = new Image("res/Sprites/FireMinion.png");
            this.sprite = new ImageView(img2);
            this.speed = 2;
            this.type = 2;
            this.damage = 1;
            health = 2;
            break;
        case 3:
            Image img3 = new Image("res/Sprites/RockMinion.png");
            this.sprite = new ImageView(img3);
            this.speed = 2;
            this.type = 3;
            this.damage = 2;
            health = 6;
            break;
        default:
            this.sprite = new ImageView(new Image("res/Sprites/Boss/BossFull.png"));
            break;
        }
        sprite.toFront();
    }

    public ImageView getSprite() {
        return sprite;
    }

    public short getHealth() {
        return health;
    }

    public void setHealth(short health) {
        this.health = health;
    }

    public Player getPlayer() {
        return this.player;
    }

    public short getDamage() {
        return damage;
    }

    public void setX(short x) {
        this.x = x;
    }
    public short getX() {
        return x;
    }
    public void setY(short y) {
        this.y = y;
    }
    public short getY() {
        return y;
    }

    public void tick() {
        if (health <= 0) {
            this.sprite.setVisible(false);
            room.getGrid().getChildren().remove(this);
            room.getTickablesToRemove().add(this);
            totalMonstersKilled++;

            if (projectileLaunched) {
                room.getTickables().remove(projectile);
                projectile.getSprite().setVisible(false);
                projectile = null;
                projectileLaunched = false;
                compareTimer = timer;
            }
            Random random = new Random();
            int dropID = random.nextInt(10);
            Item i;
            switch (dropID) {
            case 0:
                i = new Potion("Health Potion", new ImageView(new Image("res/Sprites/Potion1.png")),
                        player.getInventory(), player, (short) 1, (short) 3);
                break;
            case 1:
                i = new Potion("Attack Potion", new ImageView(new Image("res/Sprites/Potion2.png")),
                        player.getInventory(), player, (short) 2, (short) 2);
                break;
            case 2:
                i = new Potion("Unkown", new ImageView(new Image("res/Sprites/Potion3.png")),
                        player.getInventory(), player, (short) 3, (short) 0);
                break;
            case 3:
                i = new Weapon("Sword", new ImageView(new Image("res/Sprites/Sword.png")),
                        player.getInventory(), player, 1, 10);
                break;
            case 4:
                i = new Weapon("Knife", new ImageView(new Image("res/Sprites/Knife.png")),
                        player.getInventory(), player, 1, 2);
                break;
            case 5:
                i = new Weapon("Sledgehammer",
                        new ImageView(new Image("res/Sprites/Sledgehammer.png")),
                        player.getInventory(), player, 10, 20);
                break;
            case 6:
                i = new Weapon("Excalibur", new ImageView(new Image("res/Sprites/Excalibur.png")),
                        player.getInventory(), player, 3, 1);
                break;
            default:
                i = new Potion("Health Potion", new ImageView(new Image("res/Sprites/Potion1.png")),
                        player.getInventory(), player, (short) 1, (short) 3);
            }
            room.getDrops().add(i);
            i.getSprite().setTranslateX(this.x);
            i.getSprite().setTranslateY(this.y);
            i.getSprite().toFront();
            room.getRootElement().getChildren().add(i.getSprite());
        }

        double dirX = player.getX() - getX();
        double dirY = player.getY() - getY();
        //prevent division by 0
        double magnitude = Math.sqrt(Math.pow(dirX, 2) + Math.pow(dirY, 2)) + 0.0001;
        dirX = speed * dirX / magnitude;
        dirY = speed * dirY / magnitude;

        setX((short) (getX() + dirX));
        setY((short) (getY() + dirY));

        sprite.setTranslateX(getX());
        sprite.setTranslateY(getY());

        if (!this.projectileLaunched && type == 2 && (timer - compareTimer >= 100)) {
            launchProjectile();

        } else if (type == 2 && projectileLaunched
                && projectile.getSprite().getBoundsInParent().intersects(
                        player.getSprite().getBoundsInParent())) {
            room.getTickables().remove(projectile);
            projectile.getSprite().setVisible(false);
            projectile = null;
            projectileLaunched = false;
            compareTimer = timer;
            player.setHealth((short) (player.getHealth() - 1));
            player.setImmune(true);
        }
        //System.out.println("Minion coordinates: (" + getX() + ", " + getY() + ")");
        timer++;
    }

    public void launchProjectile() {
        Image im = new Image("/res/Sprites/Projectile.png");
        ImageView imview = new ImageView(im);
        imview.toFront();
        projectile = new Projectile(this.x, this.y, imview, this);
        this.room.getTickablesToAdd().add(projectile);
        this.projectileLaunched = true;

    }

    public Room getRoom() {
        return room;
    }

    public void setProjectile(Projectile projectile) {
        this.projectile = projectile;
    }

    public void setProjectileLaunched(boolean projectileLaunched) {
        this.projectileLaunched = projectileLaunched;
    }

    public void setCompareTimer(int compareTimer) {
        this.compareTimer = compareTimer;
    }

    public int getTimer() {
        return this.timer;
    }

    public static short getTotalMonstersKilled() {
        return totalMonstersKilled;
    }

}
