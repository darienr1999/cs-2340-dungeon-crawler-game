package game;

import java.util.Random;

public class GraphNode {
    private GraphNode[] adjacentNodes;
    private Room room;
    private Random random;

    private final int numAdjacentNodes = 4;

    public GraphNode(Room room, Random random) {
        // all nodes created are null by default
        this.room = room;
        this.adjacentNodes = new GraphNode[numAdjacentNodes];
        this.random = random;
    }

    public void setAdjacentNode(int index, GraphNode adjacentNode) {
        if (index < 0 || index >= numAdjacentNodes) {
            throw new IndexOutOfBoundsException();
        }
        this.adjacentNodes[index] = adjacentNode;
        //adjacentNode.adjacentNodes[random.nextInt(numAdjacentNodes)] = this;
        adjacentNode.adjacentNodes[0] = this; //zeroth door every time
    }

    public GraphNode getAdjacentNode(int index) {
        if (index < 0 || index >= numAdjacentNodes) {
            throw new IndexOutOfBoundsException();
        }
        return this.adjacentNodes[index];
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public GraphNode[] getAdjacentNodes() {
        return adjacentNodes;
    }

    @Override
    public String toString() {
        return "Node holding Room #" + this.room.getId();
    }

    public String toStringAdjacentNodes() {
        String str = "Adjacent Nodes:\n";
        for (int i = 0; i < numAdjacentNodes; i++) {
            str += adjacentNodes[i] + "\n";
        }
        return str;
    }
}
