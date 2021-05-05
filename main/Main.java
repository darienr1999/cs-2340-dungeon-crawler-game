package main;

//import game.GameController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    private static Stage stage;
    private static int money;
    private static String difficulty;
    private static String weapon;

    public static Stage getStage() {
        return stage;
    }

    public static void setMoney(int i) {
        money = i;
    }

    public static int getMoney() {
        return money;
    }

    public static void setDifficulty(String value) {
        difficulty = value;
    }

    public static String getDifficulty() {
        return difficulty;
    }

    public static void setWeapon(String value) {
        weapon = value;
    }

    public static String getWeapon() {
        return weapon;
    }

    /**
     * This method runs Dungeon crawler game
     * @param primaryStage stage of the game
     * @throws Exception throws exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        primaryStage.setTitle("Welcome");
        //Create Scenes
        Scenes.createMenuScene();
        Scenes.createConfigScene();
        //End create Scenes
        primaryStage.setTitle("Dungeon Crawler");
        primaryStage.setScene(Scenes.getMenu());
        primaryStage.show();
    }

    /**
     * This runs the main method
     * @param args argument
     */
    public static void main(String[] args) {
        launch(args);
    }
}
