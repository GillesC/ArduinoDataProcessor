import java.util.ArrayList;

/**
 * Created by Gilles Callebaut on 15/04/2016.
 *
 */
public class Node {
    private int nodeID;
    private ArrayList<Data> nodeData;

    public Node(int nodeID) {
        this.nodeID = nodeID;
    }

    public void pushData(ArrayList<Data> data){
        this.nodeData = data;
    }
}
