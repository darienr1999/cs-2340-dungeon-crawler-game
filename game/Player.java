package game;

import javafx.scene.image.ImageView;

public class Player implements Tickable {
    private static final int INCREMENT = 8;

    private String name;
    private short difficulty;
    private short health;
    private short maxHealth;
    private short level;
    private short currency;
    private volatile short x;
    private volatile short y;
    private ImageView sprite;
    private Input playerInput;
    private Weapon weapon;
    private short damageMultiplier;
    private Inventory inventory;
    private int frameCounter = 0;
    //How long the players immunity is after getting hit
    private int immunityTime = 15;

    //Counter that keeps track of how much immunity is left
    private int immunityCounter = 0;
    //Whether the player is currently immune or not
    private boolean immune = false;
    private boolean swing = false;
    private int swingCounter = 0;
    private static short totalDamageDealt = 0;
    private static short totalDamageTaken = 0;

    public short getTotalDamageDealt() {
        return totalDamageDealt;
    }
    public short getTotalDamageTaken() {
        return totalDamageTaken;
    }
    public ImageView getSprite() {
        return sprite;
    }
    public Input getPlayerInput() {
        return playerInput;
    }
    public void setPlayerInput(Input input) {
        playerInput = input;
    }

    public Player() {

    }

    public Player(String name, short difficulty, ImageView sprite, short x, short y) {
        this.difficulty = difficulty;
        this.name = name;
        this.x = x;
        this.y = y;
        this.sprite = sprite;
        this.damageMultiplier = 1;
        level = 1;
        this.inventory = new Inventory();
        switch (difficulty) {
        case 1: //normal
            currency = 80;
            health = 8;
            maxHealth = health;
            break;
        case 2: //hard
            currency = 60;
            health = 6;
            maxHealth = health;
            break;
        case 3: //hardcore
            currency = 0;
            health = 1;
            maxHealth = health;
            break;
        default: //easy and default
            currency = 100;
            health = 10;
            maxHealth = health;
        }
    }
    public short getCurrency() {
        return currency;
    }

    public short getHealth() {
        return health;
    }

    public void setHealth(short health) {
        if (health > this.maxHealth) {
            this.health = maxHealth;
        } else {
            this.health = health;
        }
    }

    public short getMaxHealth() {
        return maxHealth;
    }

    public short getDifficulty() {
        return difficulty;
    }

    public short getLevel() {
        return level;
    }

    public boolean isImmune() {
        return immune;
    }

    public void setImmune(boolean immune) {
        this.immune = immune;
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

    public Inventory getInventory() {
        return inventory;
    }

    public void setDamageMultiplier(short d) {
        this.damageMultiplier = d;
    }

    public short getDamageMultiplier() {
        return damageMultiplier;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public boolean getSwing() {
        return swing;
    }

    public void processInput() {
        int speed = 0;
        if (!getPlayerInput().isSprinting()) {
            speed = INCREMENT;
        } else {
            speed = 2 * INCREMENT;
        }
        if (playerInput.isMoveUp()) {
            //setY((short) (getY() + 1));
            setY((short) (getY() - speed));
        }
        if (playerInput.isMoveDown()) {
            //setY((short) (getY() - 1));
            setY((short) (getY() + speed));
        }
        if (playerInput.isMoveRight()) {
            //setX((short) (getX() + 1));
            sprite.setScaleX(1.0);
            setX((short) (getX() + speed));
        }
        if (playerInput.isMoveLeft()) {
            //setX((short) (getX() - 1));
            sprite.setScaleX(-1.0);
            setX((short) (getX() - speed));
        }
        if (playerInput.isAttacking()) {
            checkAttackMinion();
        }
        if (playerInput.isInventory()) {
            this.inventory.displayInventory();
            playerInput.releaseInputs();
        }
        if (playerInput.isDrop()) {
            if (weapon != null) {
                Room room = Room.getGc().getRoom();
                Weapon drop = new Weapon(weapon.getName(), weapon.getSprite(),
                        inventory, room.getPlayer(), weapon.getDamage());
                room.getDrops().add(drop);
                drop.getSprite().setTranslateX(this.x + 50);
                drop.getSprite().setTranslateY(this.y + 50);
                drop.getSprite().toFront();
                room.getRootElement().getChildren().add(drop.getSprite());
                weapon = null;
            }
        }
    }
    public void checkAttackMinion() {
        if (!swing) {
            double range = 20.0;
            if (weapon != null) {
                System.out.println("ATTACKING with: " + weapon.getName());
            }
            Room currRoom = Room.getGc().getRoom();
            for (Minion minion : currRoom.getMinions()) {
                if (sprite.getBoundsInParent().intersects(
                        minion.getSprite().getBoundsInParent().getMinX() - range / 2,
                        minion.getSprite().getBoundsInParent().getMinY() - range / 2,
                        minion.getSprite().getBoundsInParent().getWidth() + range,
                        minion.getSprite().getBoundsInParent().getHeight() + range)) {
                    minion.setHealth((short) (minion.getHealth()
                            - ((weapon == null) ? (0) : (weapon.getDamage())) * damageMultiplier));
                    totalDamageDealt += weapon.getDamage() * damageMultiplier;
                }
            }
            swing = true;
        }
    }

    public void isColliding() {
        //if the player is not immune it can take a hit if it gets hit immunity timer starts
        Room currRoom = Room.getGc().getRoom();
        if (!immune) {
            for (Minion minion : currRoom.getMinions()) {
                if (sprite.getBoundsInParent().intersects(minion.getSprite().getBoundsInParent())) {
                    setHealth((short) (getHealth() - minion.getDamage()));
                    totalDamageTaken += minion.getDamage();
                    immune = true;
                    break;
                }
            }
        }
        for (Item item : currRoom.getDrops()) {
            if (!inventory.isFull()
                    && sprite.getBoundsInParent().
                    intersects(item.getSprite().getBoundsInParent())) {
                currRoom.getTickablesToRemove().add(item);
                currRoom.getRootElement().getChildren().remove(item.getSprite());
                inventory.putInside(item);
            }
        }
    }

    public void tick() {
        processInput();
        isColliding();
        sprite.setTranslateX(getX());
        sprite.setTranslateY(getY());
        //if counter hits immunity time, immunity ends, otherwise count up
        if (immune) {
            if (immunityCounter >= immunityTime) {
                immune = false;
                immunityCounter = 0;
            } else {
                immunityCounter += 1;
            }
        }
        if (swing && weapon != null) {
            if (swingCounter < weapon.getSwingTimer()) {
                swingCounter++;
            } else {
                swingCounter = 0;
                swing = false;
            }
        }
    }

    public void equipWeapon(Weapon weaponToEquip) {
        if (this.weapon == null) {
            this.weapon = weaponToEquip;
        } else {
            Weapon temp = this.weapon;
            this.weapon = weaponToEquip;
            this.inventory.putInside(temp);
        }
    }
}
