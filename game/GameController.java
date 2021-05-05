package game;


import java.util.Timer;


public class GameController {
    private Graph graph;
    private Timer timer;
    private Tick task;
    private short interval;

    public GameController(short interval) {
        this.interval = interval;
        graph = new Graph();
    }
    public void start(GraphNode currentNode) {
        timer = new Timer();
        if (this.graph.getStartNode() == null) {
            this.graph.setStartNode(currentNode);
        }
        this.graph.setCurrentNode(currentNode);
        task = new Tick();
        timer.schedule(task, 0, interval);
        task.setRoom(currentNode.getRoom());
    }

    public void stop() {
        timer.cancel();
    }
    public Graph getGraph() {
        return graph;
    }
    public Room getRoom() {
        return this.graph.getCurrentNode().getRoom();
    }
}