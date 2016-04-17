import java.util.ArrayList;

/**
 * Created by Gilles Callebaut on 15/04/2016.
 *
 */
class Node {
    private int nodeID;
    private ArrayList<Data> nodeData = new ArrayList<>();

    Node(int nodeID) {
        this.nodeID = nodeID;
    }

    void pushData(ArrayList<Data> data){
        this.nodeData.addAll(data);
        System.out.println("\tPushed data to node: "+nodeID);
        System.out.println("\t"+data);
    }

    public int getID() {
        return nodeID;
    }

    public ArrayList<Data> getData() {
        return nodeData;
    }
}
