package game;

//import javafx.event.Event;
import javafx.event.EventHandler;
//import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
//import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.BitSet;

public class Input {
    private BitSet keyboardBitSet = new BitSet();
    //keybindings
    private KeyCode up = KeyCode.W;
    private KeyCode left = KeyCode.A;
    private KeyCode down = KeyCode.S;
    private KeyCode right = KeyCode.D;
    private KeyCode sprint = KeyCode.SHIFT;
    private KeyCode attack = KeyCode.SPACE;
    private KeyCode inventory = KeyCode.E;
    private KeyCode drop = KeyCode.Q;

    private Stage roomScene;

    public Input(Stage roomScene) {
        this.roomScene = roomScene;
    }

    public void addListeners() {
        roomScene.addEventFilter(KeyEvent.KEY_PRESSED, keyPressed);
        roomScene.addEventFilter(KeyEvent.KEY_RELEASED, keyReleased);
    }

    public void removeListeners() {
        roomScene.removeEventFilter(KeyEvent.KEY_PRESSED, keyPressed);
        roomScene.removeEventFilter(KeyEvent.KEY_RELEASED, keyReleased);
    }



    private EventHandler<KeyEvent> keyPressed = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            keyboardBitSet.set(event.getCode().ordinal(), true);
        }
    };
    private EventHandler<KeyEvent> keyReleased = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            keyboardBitSet.set(event.getCode().ordinal(), false);
        }
    };

    public boolean isMoveUp() {
        return keyboardBitSet.get(up.ordinal());
    }
    public boolean isMoveDown() {
        return keyboardBitSet.get(down.ordinal());
    }
    public boolean isMoveLeft() {
        return keyboardBitSet.get(left.ordinal());
    }
    public boolean isMoveRight() {
        return keyboardBitSet.get(right.ordinal());
    }
    public boolean isSprinting() {
        return keyboardBitSet.get(sprint.ordinal());
    }
    public boolean isAttacking() {
        return keyboardBitSet.get(attack.ordinal());
    }
    public boolean isInventory() {
        return keyboardBitSet.get(inventory.ordinal());
    }
    public boolean isDrop() {
        return keyboardBitSet.get(drop.ordinal());
    }
    public void releaseInputs() {
        keyboardBitSet.set(up.ordinal(), false);
        keyboardBitSet.set(down.ordinal(), false);
        keyboardBitSet.set(left.ordinal(), false);
        keyboardBitSet.set(right.ordinal(), false);
        keyboardBitSet.set(sprint.ordinal(), false);
        keyboardBitSet.set(inventory.ordinal(), false);
    }

}
