import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.Iterator;

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
        ArrayList<Data> temp = new ArrayList<>();

        for(Data d: data){
            boolean isEqual = false;
            for(Data nodeD: this.nodeData){
                if(d.equals(nodeD)){
                    isEqual = true;
                }
            }
            if(!isEqual) temp.add(d);
        }
        this.nodeData.addAll(temp);
    }

    public int getID() {
        return nodeID;
    }

    public ArrayList<Data> getData() {
        return nodeData;
    }
}
