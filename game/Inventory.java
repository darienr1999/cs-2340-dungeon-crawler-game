package game;

import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class Inventory {
    private ArrayList<Item> items;
    private static short capacity = 6;
    private boolean inventoryShown;
    private volatile InventoryWindow inventoryWindow;

    public Inventory(Item... startingItems) {
        items = new ArrayList<>();
        for (Item item : startingItems) {
            items.add(item);
        }

        this.inventoryShown = false;
    }

    public int size() {
        return this.items.size();
    }

    public short getCapacity() {
        return this.capacity;
    }

    public boolean putInside(Item item) {
        if (this.size() < this.capacity) {
            this.items.add(item);
            item.setSprite(new ImageView(item.getSprite().getImage()));
            return true;
        } else {
            System.out.println("Your inventory is full");
            //MIGHTWANT: drop Item to overworld or it will be sent to the Ether!
            return false;
        }
    }

    public void takeOut(Item item) {
        this.items.remove(item);
    }

    public ArrayList<Item> getItems() {
        return this.items;
    }

    public Item get(int index) {
        if (index >= this.size()) {
            return null;
        } else {
            return this.items.get(index);
        }
    }

    public InventoryWindow displayInventory() {
        if (!inventoryShown) {
            inventoryWindow = new InventoryWindow(this);
            inventoryWindow.show();
            inventoryShown = true;

        }

        return this.inventoryWindow;
    }

    public void setInventoryShown(boolean inventoryShown) {
        this.inventoryShown = inventoryShown;
    }
    public boolean getInventoryShown() {
        return inventoryShown;
    }

    public boolean isFull() {
        return items.size() == capacity;
    }

    public InventoryWindow getInventoryWindow() {
        return this.inventoryWindow;
    }

    public boolean contains(Item item) {
        return this.items.contains(item);
    }
}
