import java.util.ArrayList;

/**
 * Created by Gilles Callebaut on 15/04/2016.
 *
 */
class Node {
    private int nodeID;
    private ArrayList<Data> nodeData = null;

    Node(int nodeID) {
        this.nodeID = nodeID;
    }

    void pushData(ArrayList<Data> data){
        if(nodeData==null){
            nodeData = new ArrayList<>();
        }
        this.nodeData.addAll(data);
    }

    public int getID() {
        return nodeID;
    }

    public ArrayList<Data> getData() {
        return nodeData;
    }
}
