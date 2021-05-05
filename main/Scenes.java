package main;
import game.*;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.stage.Stage;

//SCENE CONNECTIONS  menu -> config -> gameScreen
public class Scenes {
    private static Scene menu;
    private static Scene config;
    private static Scene win;
    private static Popup deathPopup;
    private static Popup statsPopup;
    private static Popup challengePopup;
    private static Stage stage;
    private static boolean isPlayerDead;
    private static GameController gameController;

    /**
     * Creates scene for main menu
     */
    public static void createMenuScene() {
        Font.loadFont(Scenes.class.getResourceAsStream("../res/Fonts/8bit.TTF"), 16);

        Label title = new Label("Dungeon Crawler");
        Label start = new Label("Click to enter the Dungeon");
        title.getStyleClass().add("title");
        Button startButton = new Button("Start");
        startButton.setOnAction(e -> Main.getStage().setScene(config));
        VBox vbox = new VBox(title, start, startButton);
        vbox.setSpacing(30);
        vbox.setAlignment(Pos.CENTER);
        menu = new Scene(vbox, 800, 600);
        menu.getStylesheets().add("res/Stylesheets/Main.css");
        BackgroundFill back = new BackgroundFill(Color.CORAL, CornerRadii.EMPTY, Insets.EMPTY);
        Background b = new Background(back);
        vbox.setBackground(b);
    }

    /**
     * Creates scene for config Screen
     */
    public static void createConfigScene() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 20, 20, 20));
        //NAME
        Label enterName = new Label("Enter Name: ");
        GridPane.setConstraints(enterName, 0, 0);
        TextField playerNameInput = new TextField();
        playerNameInput.setPromptText("Name");
        GridPane.setConstraints(playerNameInput, 0, 1);
        //DIFFICULTY
        Label difficultyLabel = new Label("Select Difficulty");
        GridPane.setConstraints(difficultyLabel, 1, 0);
        ComboBox<String> difficulty = new ComboBox<>();
        difficulty.getStyleClass().add("dropdown");
        difficulty.setValue("Medium");
        GridPane.setConstraints(difficulty, 1, 1);
        difficulty.getItems().addAll("lmao", "Hard", "Medium", "Easy");
        difficulty.setOnAction(e ->
                System.out.println(difficulty.getSelectionModel().getSelectedIndex() + 1));
        //WEAPON CHOICE
        Label weaponText = new Label("Select Starting weapon");
        GridPane.setConstraints(weaponText, 2, 0);
        ChoiceBox<String> weapons = new ChoiceBox<>();
        weapons.getStyleClass().add("dropdown");
        weapons.getItems().addAll("Sword", "Knife", "Sledgehammer");
        weapons.setValue("Sword");
        GridPane.setConstraints(weapons, 2, 1);
        //CONFIRM BUTTON
        Button setNameButton = new Button("Confirm");
        GridPane.setConstraints(setNameButton, 0, 2);
        grid.getChildren().addAll(enterName, playerNameInput, difficultyLabel,
                difficulty, setNameButton, weaponText, weapons);

        setNameButton.setOnAction(processName -> {
            if (playerNameInput.getText().trim().isEmpty()) {
                Label nullName = new Label("Please Enter a Valid Name");
                GridPane.setConstraints(nullName, 0, 3);
                grid.getChildren().add(nullName);
            } else {
                Main.setMoney((difficulty.getSelectionModel().getSelectedIndex() + 1) * 500);
                Main.setDifficulty(difficulty.getValue());
                Main.setWeapon(weapons.getValue());

                //switch to room1
                startGame();
            }
        });
        BackgroundFill back = new BackgroundFill(Color.CORAL, CornerRadii.EMPTY, Insets.EMPTY);
        Background b = new Background(back);
        grid.setBackground(b);
        config = new Scene(grid, 800, 600);
        config.getStylesheets().add("res/Stylesheets/Main.css");
    }

    /**
     * Creates scene for win screen
     */
    public static void createWinScene() {
        isPlayerDead = false;
        Label title = new Label("You win!");
        title.getStyleClass().add("title");
        Button startButton = new Button("Play Again");
        Button quitButton = new Button("Quit");
        startButton.setOnAction(e -> startGame());
        Button statsButton = new Button("Stats");
        statsButton.setOnAction(event -> {
            createStatsScreen();
        });
        quitButton.setOnAction(e -> Platform.exit());
        VBox vbox = new VBox(title, startButton, statsButton, quitButton);
        vbox.setSpacing(30);
        vbox.setAlignment(Pos.CENTER);
        win = new Scene(vbox, 800, 600);
        win.getStylesheets().add("res/Stylesheets/Main.css");
        BackgroundFill back = new BackgroundFill(Color.CORAL, CornerRadii.EMPTY, Insets.EMPTY);
        Background b = new Background(back);
        vbox.setBackground(b);
    }

    /**
     * Creates death popup
     */
    public static void createDeathPopup() {
        isPlayerDead = true;
        Label title = new Label("You Died");
        title.getStyleClass().add("title");
        Button startButton = new Button("Play Again");
        Button quitButton = new Button("Quit");
        Button statsButton = new Button("Stats");
        startButton.setOnAction(e -> {
            deathPopup.hide();
            isPlayerDead = false;
            startGame();
        });
        quitButton.setOnAction(e -> Platform.exit());
        statsButton.setOnAction(event -> {
            deathPopup.hide();
            createStatsScreen();
        });
        VBox vbox = new VBox(title, startButton, statsButton, quitButton);
        vbox.setSpacing(30);
        vbox.setPadding(new Insets(30, 30, 30, 30));
        vbox.setAlignment(Pos.CENTER);
        vbox.getStylesheets().add("res/Stylesheets/Main.css");
        BackgroundFill back = new BackgroundFill(Color.CORAL, CornerRadii.EMPTY, Insets.EMPTY);
        Background b = new Background(back);
        vbox.setBackground(b);

        deathPopup = new Popup();
        deathPopup.getContent().add(vbox);
        deathPopup.show(stage);
    }

    public static void createStatsScreen() {
        Player player = gameController.getRoom().getPlayer();

        VBox vbox = new VBox();
        //BorderPane borderPane = new BorderPane();
        Label monstersSlain = new Label("Monsters Slain: " + Minion.getTotalMonstersKilled());
        Label damageDealt = new Label("Damage Dealt: " + player.getTotalDamageDealt());
        Label damageTaken = new Label("Damage Taken: " + player.getTotalDamageTaken());
        Button closeButton = new Button("Close");
        vbox.getChildren().addAll(monstersSlain, damageDealt, damageTaken, closeButton);
        vbox.setSpacing(30);
        vbox.setPadding(new Insets(30, 30, 30, 30));
        vbox.setAlignment(Pos.CENTER);
        vbox.getStylesheets().add("res/Stylesheets/Main.css");
        BackgroundFill back = new BackgroundFill(Color.CORAL, CornerRadii.EMPTY, Insets.EMPTY);
        Background b = new Background(back);
        vbox.setBackground(b);
        //borderPane.setCenter(vbox);
        //Button closeButton = new Button("Close");
        closeButton.setOnAction(event -> {
            statsPopup.hide();
            if (isPlayerDead) {
                deathPopup.show(stage);
            } else {
                stage.setScene(win);
            }
        });
        //borderPane.setBottom(closeButton);
        statsPopup = new Popup();
        statsPopup.getContent().addAll(vbox);
        statsPopup.show(stage);
    }

    public static void createChallengePopup() {
        Label title = new Label("Challenge Room");
        title.getStyleClass().add("title");
        Button accept = new Button("Accept Challenge");
        Button reject = new Button("Reject Challenge");
        accept.setOnAction(e -> {
            Room room = gameController.getRoom();
            room.challenge();
            challengePopup.hide();
        });
        reject.setOnAction(e -> challengePopup.hide());
        VBox vbox = new VBox(title, accept, reject);
        vbox.setSpacing(30);
        vbox.setPadding(new Insets(30, 30, 30, 30));
        vbox.setAlignment(Pos.CENTER);
        vbox.getStylesheets().add("res/Stylesheets/Main.css");
        BackgroundFill back = new BackgroundFill(Color.CORAL, CornerRadii.EMPTY, Insets.EMPTY);
        Background b = new Background(back);
        vbox.setBackground(b);

        challengePopup = new Popup();
        challengePopup.getContent().add(vbox);
        challengePopup.show(stage);
    }

    public static Scene getMenu() {
        return menu;
    }

    public static Scene getWinScene() {
        return win;
    }

    public static Popup getDeathPopup() {
        return deathPopup;
    }

    public static Popup getChallengePopup() {
        return challengePopup;
    }

    public static Popup getStatsPopup() {
        return statsPopup;
    }

    public static void startGame() {
        GameController gc = new GameController((short) 50);
        gameController = gc;


        Image spriteImg = new Image("res/Sprites/Sprite.png", 75, 75, true, true);
        ImageView sprite = new ImageView(spriteImg);
        sprite.toFront();

        Room room1 = new Room(Main.getStage(), 1, gc,
                new Player("name", (short) 300, sprite, (short) 300, (short) 0));
        GraphNode room1Node = new GraphNode(room1, gc.getGraph().getRandom());
        gc.start(room1Node);
        stage = gc.getRoom().getStage();
    }


}

