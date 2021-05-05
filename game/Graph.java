package game;

import java.util.ArrayList;
import java.util.Random;

public class Graph {
    private GraphNode startNode;
    private GraphNode exitNode;
    private GraphNode currentNode;
    private Random random;
    private int size;
    private ArrayList<Integer> typeRoomsRemaining;

    private final int numTypeRooms = 8;

    public Graph(Random random) {
        this.random = random;
        this.typeRoomsRemaining = new ArrayList<>();
        // i = 1 is first room always
        for (int i = 2; i <= numTypeRooms; i++) {
            typeRoomsRemaining.add(i);
        }
    }

    public Graph() {
        this(new Random());
    }

    public GraphNode getNextRoomNode(int index) {
        GraphNode nextRoomNode = this.currentNode.getAdjacentNode(index);
        if (nextRoomNode == null) {
            return null;
        } else {
            return nextRoomNode;
        }
    }

    public GraphNode addNextRoomNode(int index, Room next, Integer randomRoomId) {
        this.typeRoomsRemaining.remove(randomRoomId);
        GraphNode nextNode = new GraphNode(next, this.random);
        this.currentNode.setAdjacentNode(index, nextNode);
        this.size++;
        return nextNode;
    }

    public int random() {
        if (this.typeRoomsRemaining.size() < 1) {
            return -1;
        }
        int randomInt;
        if (this.size < 5) {
            randomInt = this.random.nextInt(this.typeRoomsRemaining.size() - 1);
        } else {
            randomInt = this.random.nextInt(this.typeRoomsRemaining.size());
        }
        int randomRoomId = this.typeRoomsRemaining.get(randomInt);
        return randomRoomId;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public GraphNode getStartNode() {
        return startNode;
    }

    public void setStartNode(GraphNode startNode) {
        this.startNode = startNode;
        this.size = 1;
    }

    public GraphNode getExitNode() {
        return exitNode;
    }

    public void setExitNode(GraphNode exitNode) {
        this.exitNode = exitNode;
    }

    public GraphNode getCurrentNode() {
        return currentNode;
    }

    public void setCurrentNode(GraphNode currentNode) {
        this.currentNode = currentNode;
    }

    public Random getRandom() {
        return this.random;
    }
}
