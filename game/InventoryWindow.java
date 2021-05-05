package game;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class InventoryWindow extends Stage {
    private static final double MIN_HEIGHT = 160;
    private static final double MIN_WIDTH = 240;

    private Inventory inventory;
    public InventoryWindow(Inventory inventory) {

        this.inventory = inventory;
        this.setTitle("Inventory");
        this.setMinHeight(MIN_HEIGHT);
        this.setMinWidth(MIN_WIDTH);
        this.initStyle(StageStyle.UNDECORATED);

        Button exitButton = new Button("Close Inventory");
        exitButton.setOnAction(event -> {
            this.inventory.setInventoryShown(false);
            this.close();
        });

        BorderPane rootPane = new BorderPane();
        rootPane.getStylesheets().add("res/Stylesheets/Main.css");
        rootPane.setPadding(new Insets(5));
        rootPane.setBottom(exitButton);

        TilePane inventorySlots = new TilePane();
        inventorySlots.setPrefColumns(3);
        for (int i = 0; i < inventory.getCapacity(); i++) {
            Item item = inventory.get(i);
            ItemButton itemButton;
            if (item == null) {
                itemButton = new ItemButton("empty");
            } else {
                itemButton = new ItemButton(item.getName(), item.getSprite(), item);
            }
            itemButton.setOnAction(event -> {
                Item inButton = itemButton.getItem();
                if (item == null) {
                    System.out.println("This is an empty inventory space");
                } else {
                    System.out.println("weapon from inv: " + inButton.getName());
                    inButton.use();
                    itemButton.setItem(null);
                    itemButton.setGraphic(null);
                    itemButton.setText("empty");
                }
            });
            inventorySlots.getChildren().add(itemButton);
        }

        rootPane.setCenter(inventorySlots);
        this.setScene(new Scene(rootPane));

    }

}
