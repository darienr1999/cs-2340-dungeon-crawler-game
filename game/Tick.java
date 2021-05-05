package game;

import javafx.application.Platform;

//import java.util.ArrayList;
import java.util.TimerTask;

public class Tick extends TimerTask {
    private Room current;
    public void run() {
        Platform.runLater(() -> {
            current.tick();
        });
    }
    public void setRoom(Room current) {
        this.current = current;
    }
    public Room getRoom() {
        return this.current;
    }
}

