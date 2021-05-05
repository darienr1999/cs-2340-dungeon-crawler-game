package game;
import javafx.scene.image.ImageView;

public class Projectile implements Tickable {
    private static final int INCREMENT = 4;

    private short x;
    private short y;
    private ImageView sprite;
    private Minion minion;
    private int ticksTillDespawn;


    public Projectile(short x, short y, ImageView sprite, Minion minion) {
        this.x = x;
        this.y = y;
        this.minion = minion;
        this.sprite = sprite;
        this.ticksTillDespawn = 100;
    }

    public void tick() {
        if (ticksTillDespawn < 0) {
            this.minion.getRoom().getTickables().remove(this);
            this.sprite.setVisible(false);
            this.minion.setProjectile(null);
            this.minion.setProjectileLaunched(false);
            this.minion.setCompareTimer(minion.getTimer());
            return;
        }
        double dirX = minion.getPlayer().getX() - x;
        double dirY = minion.getPlayer().getY() - y;
        double magnitude = Math.sqrt(Math.pow(dirX, 2) + Math.pow(dirY, 2)) + 0.0001;
        dirX = INCREMENT * dirX / magnitude;
        dirY = INCREMENT * dirY / magnitude;

        x = ((short) (x + dirX));
        y = ((short) (y + dirY));

        sprite.setTranslateX(x + 635);
        sprite.setTranslateY(y + 330);

        //System.out.println(ticksTillDespawn);
        ticksTillDespawn--;

        //System.out.println("Projectile coordinates: (" + x + ", " + y + ")");
    }

    public ImageView getSprite() {
        return this.sprite;
    }

}
