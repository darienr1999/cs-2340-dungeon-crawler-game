package game;
//import javafx.application.Platform;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.image.*;
import javafx.util.Duration;
import javafx.util.Pair;
import main.Main;
import main.Scenes;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import static main.Scenes.createDeathPopup;

public class Room implements Tickable {
    private static GameController gc;
    private GridPane grid;
    private Scene scene;
    private LinkedList<Pair<Integer, Integer>> doors;
    private Stage stage;
    private StackPane root;
    private ArrayList<Tickable> tickables = new ArrayList<>();
    private Image bg;
    private ImageView bgView;
    private ImageView weaponIcon;
    private Player player;
    private int id;
    private short xOld;
    private short yOld;
    private ArrayList<Tickable> tickablesToAdd = new ArrayList<>();
    private ArrayList<ImageView> hearts = new ArrayList<>();
    private ArrayList<Tickable> tickablesToRemove = new ArrayList<>();
    private ArrayList<Minion> minions = new ArrayList<>();
    private ArrayList<Item> drops = new ArrayList<>();

    protected final boolean developerMode = true;

    public Room(Stage primaryStage, int id, GameController gc, Player p) {
        doors = new LinkedList<>();
        grid = new GridPane();
        this.stage = primaryStage;
        this.player = p;
        this.gc = gc;
        //grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 20, 20, 20));
        //MONEY
        Label moneyLabel = new Label("Money: ");
        GridPane.setConstraints(moneyLabel, 0, 0);
        String mValue = Integer.toString(Main.getMoney());
        Label money = new Label(mValue);
        HBox moneyBox = new HBox();
        moneyBox.setAlignment(Pos.TOP_CENTER);
        moneyBox.getChildren().addAll(moneyLabel, money);
        grid.add(moneyBox, 0, 0);

        root = new StackPane();

        BackgroundFill back = new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY);
        Background b = new Background(back);
        root.setBackground(b);

        Image heartImg = new Image("res/Sprites/Heart.png");

        Random rngMinionSprite = new Random();

        roomSetup(id, rngMinionSprite);

        bgView = new ImageView(bg);
        bgView.toBack();

        weaponIcon = new ImageView();
        weaponIcon.toFront();
        weaponIcon.setTranslateX(-440);
        weaponIcon.setTranslateY(345);
        weaponIcon.setFitWidth(25);
        weaponIcon.setFitHeight(50);
        //Fits the level1 image to the window size allows it to scale
        //bgView.fitWidthProperty().bind(grid.widthProperty());
        //bgView.fitHeightProperty().bind(grid.heightProperty());
        root.setAlignment(Pos.CENTER);
        //grid.getChildren().add(bgView);

        tickables.add(player);

        Input playerIn = new Input(primaryStage);
        player.setPlayerInput(playerIn);
        player.getPlayerInput().addListeners();
        root.getChildren().addAll(bgView, player.getSprite(), weaponIcon);
        for (Minion m : minions) {
            root.getChildren().add(m.getSprite());
        }
        grid.add(root, 0, 1);
        scene = new Scene(grid, 1350, 900);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

        FadeTransition f = new FadeTransition();
        f.setDuration(Duration.millis(300));
        f.setNode(bgView);
        f.setFromValue(0);
        f.setToValue(1);
        f.play();

        for (int i = 0; i < player.getMaxHealth(); i++) {
            ImageView h = new ImageView(heartImg);
            h.setTranslateX(-600 + i * 16);
            h.setTranslateY(350);
            h.setVisible(true);
            root.getChildren().add(h);
            h.toFront();
            hearts.add(h);
        }
    }

    public static GameController getGc() {
        return gc;
    }

    public Scene getScene() {
        return scene;
    }

    public int getId() {
        return id;
    }

    public void tick() {
        for (Tickable tickable : tickables) {
            tickable.tick();
        }
        for (Minion m : minions) {
            m.tick();
        }
        int k = 0;
        for (; k < player.getHealth(); k++) {
            hearts.get(k).setVisible(true);
        }
        for (; k < player.getMaxHealth(); k++) {
            hearts.get(k).setVisible(false);
        }
        if (player.getWeapon() != null) {
            weaponIcon.setImage(player.getWeapon().getSprite().getImage());
            if (player.getSwing()) {
                weaponIcon.setVisible(false);
            } else {
                weaponIcon.setVisible(true);
            }
        } else {
            weaponIcon.setImage(null);
        }
        globalBoundaryCheck();
        while (!tickablesToAdd.isEmpty()) {
            Tickable toAdd = tickablesToAdd.remove(0);
            if (toAdd instanceof Minion) {
                minions.add((Minion) toAdd);
            } else if (toAdd instanceof Item) {
                drops.add((Item) toAdd);
            } else {
                tickables.add(toAdd);
            }
            this.grid.getChildren().add(toAdd.getSprite());
        }

        while (!tickablesToRemove.isEmpty()) {
            Tickable toRemove = tickablesToRemove.remove(0);
            if (toRemove instanceof Minion) {
                minions.remove(toRemove);
            } else {
                tickables.remove(toRemove);
            }
            this.grid.getChildren().remove(toRemove.getSprite());
        }

        //kill the player if it touches dark purple
        boolean dead = true;
        for (int i = -3; i < 3; i++) {
            for (int j = -3; j < 3; j++) {
                int color = bg.getPixelReader().getArgb(
                        player.getX() + 653 + i, player.getY() + 375 + j);
                if (color != -14937833) {
                    dead = false;
                }
            }
        }
        if (player.getHealth() <= 0) {
            dead = true;
        }
        if (developerMode) {
            dead = false;
        }
        if (dead) {
            Platform.runLater(() -> {
                Media sound = new Media(new File("src/res/Sound/oof.mp3").toURI().toString());
                MediaPlayer mediaPlayer = new MediaPlayer(sound);
                mediaPlayer.play();
            });
            if (player.getHealth() <= 0) {
                System.out.println("Player took too much damage and died");
            } else {
                System.out.println("Player fell into the abyss");
            }
            gc.stop();
            createDeathPopup();
        }

        //detect if player has entered a door
        for (Pair<Integer, Integer> door : doors) {
            if (Math.abs(player.getX() - door.getKey())
                < 20 && Math.abs(player.getY() - door.getValue()) < 35) {
                int doorIndex = doors.indexOf(door);

                Graph graph = gc.getGraph();
                GraphNode nextNode = graph.getNextRoomNode(doorIndex);
                //save player location
                if (player.getY() < 0) {
                    yOld = (short) (player.getY() + 3);
                } else {
                    yOld = (short) (player.getY() - 3);
                }
                if (player.getX() < 0) {
                    xOld = (short) (player.getX() + 3);
                } else {
                    xOld = (short) (player.getX() - 3);
                }
                if (nextNode == null) {
                    int randomRoomId = graph.random();
                    if (!minions.isEmpty()) {
                        System.out.println("Cannot enter new rooms when minions are present!");
                    } else if (this.id == 8) {
                        gc.stop();
                        Scenes.createWinScene();
                        stage.setScene(Scenes.getWinScene());
                    } else if (randomRoomId == -1) {
                        System.out.println("Door is a dead end");
                    } else {
                        gc.stop();
                        Room next = new Room(stage, randomRoomId, gc, player);
                        nextNode = graph.addNextRoomNode(doorIndex, next, randomRoomId);
                        gc.start(nextNode);
                    }
                } else {
                    gc.stop();
                    stage.setScene(nextNode.getRoom().getScene());
                    FadeTransition f = new FadeTransition();
                    f.setDuration(Duration.millis(300));
                    f.setNode(nextNode.getRoom().bgView);
                    f.setFromValue(0);
                    f.setToValue(1);
                    f.play();
                    player.setY(nextNode.getRoom().yOld);
                    player.setX(nextNode.getRoom().xOld);
                    nextNode.getRoom().getRootElement().getChildren().add(player.getSprite());
                    gc.start(nextNode);
                }
            }
        }
    }
    public void roomSetup(int id, Random rngMinionSprite) {
        if (id == 1) {
            switch (Main.getWeapon()) {
            case "Sword":
                player.getInventory().putInside(new Weapon(Main.getWeapon(),
                        new ImageView(new Image("res/Sprites/Sword.png")),
                        player.getInventory(), player, 1, 10));
                System.out.println("Sword added to inventory");
                //just for testing purposes...
                break;
            case "Knife":
                player.getInventory().putInside(new Weapon(Main.getWeapon(),
                        new ImageView(new Image("res/Sprites/Knife.png")),
                        player.getInventory(), player, 1, 2));
                System.out.println("Knife added to inventory");
                break;
            case "Sledgehammer":
                player.getInventory().putInside(new Weapon(Main.getWeapon(),
                        new ImageView(new Image("res/Sprites/Sledgehammer.png")),
                        player.getInventory(), player, 10, 20));
                System.out.println("Sledgehammer added to inventory");
                break;
            default:
                System.out.println("Room setup default, uh oh");
                break;
            }
            player.getInventory().putInside(new Potion("Health Potion",
                    new ImageView(new Image("res/Sprites/Potion1.png")),
                    player.getInventory(), player, (short) 1, (short) 3));
            player.getInventory().get(0).use();
        }
        switch (id) {
        case 1:
            this.id = 1;
            bg = new Image("res/Maps/Room1.png");
            doors.add(new Pair<Integer, Integer>(-512, -320));
            doors.add(new Pair<Integer, Integer>(-308, -320));
            doors.add(new Pair<Integer, Integer>(60, -320));
            doors.add(new Pair<Integer, Integer>(472, -320));
            minions.add(new Minion(this, player, (short) 1, (short) 10, (short) 10));
            break;
        case 2:
            this.id = 2;
            bg = new Image("res/Maps/Room2.png");
            doors.add(new Pair<Integer, Integer>(-20, -324));
            doors.add(new Pair<Integer, Integer>(-20, 296));
            doors.add(new Pair<Integer, Integer>(-596, -36));
            doors.add(new Pair<Integer, Integer>(596, -36));
            player.setX((short) -20);
            player.setY((short) -289);
            short minionNum = (short) (rngMinionSprite.nextInt(3) + 1);
            minions.add(new Minion(this, player, minionNum, (short) 10, (short) 10));
            minionNum = (short) (rngMinionSprite.nextInt(3) + 1);
            minions.add(new Minion(this, player, minionNum, (short) -30, (short) 10));
            break;
        case 3:
            this.id = 3;
            bg = new Image("res/Maps/Room3.png");
            doors.add(new Pair<Integer, Integer>(-20, -324));
            player.setX((short) -20);
            player.setY((short) -280);

            break;
        case 4:
            //challenge room 1
            this.id = 4;
            bg = new Image("res/Maps/Room4.png");
            doors.add(new Pair<Integer, Integer>(-20, 296));
            doors.add(new Pair<Integer, Integer>(-20, -324));
            doors.add(new Pair<Integer, Integer>(-594, 6));
            doors.add(new Pair<Integer, Integer>(594, 6));
            player.setX((short) -20);
            player.setY((short) 261);
            Scenes.createChallengePopup();
            break;
        case 5:
            this.id = 5;
            bg = new Image("res/Maps/Room5.png");
            doors.add(new Pair<Integer, Integer>(-20, 296));
            doors.add(new Pair<Integer, Integer>(-20, -324));
            doors.add(new Pair<Integer, Integer>(-594, 6));
            doors.add(new Pair<Integer, Integer>(594, 6));
            player.setX((short) -20);
            player.setY((short) 261);

            minionNum = (short) (rngMinionSprite.nextInt(3) + 1);
            minions.add(new Minion(this, player, minionNum, (short) 10, (short) 10));
            minionNum = (short) (rngMinionSprite.nextInt(3) + 1);
            minions.add(new Minion(this, player, minionNum, (short) -30, (short) 10));
            minionNum = (short) (rngMinionSprite.nextInt(3) + 1);
            minions.add(new Minion(this, player, minionNum, (short) 50, (short) 10));

            break;
        case 6:
            //challenge room 2
            this.id = 6;
            bg = new Image("res/Maps/Room6.png");
            doors.add(new Pair<Integer, Integer>(-20, 296));
            doors.add(new Pair<Integer, Integer>(-20, -324));
            doors.add(new Pair<Integer, Integer>(-594, 6));
            doors.add(new Pair<Integer, Integer>(594, 6));
            player.setX((short) -20);
            player.setY((short) 261);
            Scenes.createChallengePopup();
            break;
        case 7:
            this.id = 7;
            bg = new Image("res/Maps/Room7.png");
            doors.add(new Pair<Integer, Integer>(-20, 296));
            doors.add(new Pair<Integer, Integer>(-20, -324));
            doors.add(new Pair<Integer, Integer>(-594, 6));
            doors.add(new Pair<Integer, Integer>(594, 6));
            player.setX((short) -20);
            player.setY((short) 261);

            minionNum = (short) (rngMinionSprite.nextInt(3) + 1);
            minions.add(new Minion(this, player, minionNum, (short) 10, (short) 10));
            minionNum = (short) (rngMinionSprite.nextInt(3) + 1);
            minions.add(new Minion(this, player, minionNum, (short) -30, (short) 10));
            minionNum = (short) (rngMinionSprite.nextInt(3) + 1);
            minions.add(new Minion(this, player, minionNum, (short) 50, (short) 10));
            break;
        case 8:
            this.id = 8;
            bg = new Image("res/Maps/Room8.png");
            doors.add(new Pair<Integer, Integer>(-20, 296));
            doors.add(new Pair<Integer, Integer>(-20, -324));
            doors.add(new Pair<Integer, Integer>(-594, 6));
            doors.add(new Pair<Integer, Integer>(594, 48));
            player.setX((short) -20);
            player.setY((short) 261);
            Boss b = new Boss(this, player, (short) 0, (short) 0);
            minions.add(b);
            break;
        default:
            break;
        }
    }

    public void challenge() {
        Random rngMinionSprite = new Random();
        Short minionNum = (short) (rngMinionSprite.nextInt(3) + 1);
        Minion m = new Minion(this, player, minionNum, (short) 23, (short) 10);
        minions.add(m);
        root.getChildren().add(m.getSprite());

        minionNum = (short) (rngMinionSprite.nextInt(3) + 1);
        m = new Minion(this, player, minionNum, (short) 0, (short) 30);
        minions.add(m);
        root.getChildren().add(m.getSprite());

        minionNum = (short) (rngMinionSprite.nextInt(3) + 1);
        m = new Minion(this, player, minionNum, (short) 30, (short) 21);
        minions.add(m);
        root.getChildren().add(m.getSprite());

        minionNum = (short) (rngMinionSprite.nextInt(3) + 1);
        m = new Minion(this, player, minionNum, (short) 10, (short) 10);
        minions.add(m);
        root.getChildren().add(m.getSprite());

        minionNum = (short) (rngMinionSprite.nextInt(3) + 1);
        m = new Minion(this, player, minionNum, (short) 50, (short) 0);
        minions.add(m);
        root.getChildren().add(m.getSprite());
    }

    public GridPane getGrid() {
        return grid;
    }

    public LinkedList<Pair<Integer, Integer>> getDoors() {
        return doors;
    }

    public Stage getStage() {
        return stage;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public short getxOld() {
        return xOld;
    }

    public short getyOld() {
        return yOld;
    }

    public StackPane getRootElement() {
        return root;
    }

    //Since all maps are the same size we can prevent the player
    //from moving out of the map area.
    private void globalBoundaryCheck() {
        if (player.getX() <= -606) {
            player.setX((short) -606);
        }
        if (player.getX() >= 606) {
            player.setX((short) 606);
        }
        if (player.getY() <= -302) {
            player.setY((short) -302);
        }
        if (player.getY() >= 320) {
            player.setY((short) 320);
        }
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Minion> getMinions() {
        return minions;
    }

    public ArrayList<Item> getDrops() {
        return drops;
    }

    public LinkedList<Pair<Integer, Integer>> getRoomDoors() {
        return doors;
    }

    public ArrayList<Tickable> getTickables() {
        return tickables;
    }

    public ArrayList<Tickable> getTickablesToAdd() {
        return tickablesToAdd;
    }

    public ArrayList<Tickable> getTickablesToRemove() {
        return this.tickablesToRemove;
    }
}