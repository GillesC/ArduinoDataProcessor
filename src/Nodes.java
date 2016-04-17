import java.util.ArrayList;

/**
 * Created by Gilles Callebaut on 15/04/2016.
 */
public class Nodes {
    ArrayList<Node> nodeData;

    public Nodes() {
        this.nodeData = new ArrayList<>();
    }

    public void pushNode(Node n){
        if(nodeData.contains(n)){
            // just push the data
            int index = nodeData.indexOf(n);
            nodeData.get(index).pushData(n.getData());
        }else {
            this.nodeData.add(n);
        }
    }
}
