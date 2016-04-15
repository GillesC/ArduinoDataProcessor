import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Handler;

/**
 * Created by Gilles Callebaut on 15/04/2016.
 *
 * Class which gets dataList from class Data
 * And writes to the corresponding files
 */
class Processor implements Runnable {
    private static long PERIOD = 30*1000;
    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(PERIOD);
                processData();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void processData() {
        List<Data> listOfData = Data.getDataList();

        List<Data> tempData = new ArrayList<>();
        List<Data> ventData = new ArrayList<>();
        List<Data> ligthData = new ArrayList<>();

        HashMap<Integer,ArrayList<Data>> nodeToDataMapping = new HashMap<>();


        // Set data from node
        for (Data d: listOfData){
            int nodeID = d.getNode();
            if(nodeToDataMapping.containsKey(nodeID)){
                List<Data> listForNode = nodeToDataMapping.get(nodeID);
                listForNode.add(d);
            }else{
                nodeToDataMapping.put(nodeID, new ArrayList<>());
            }
        }


        // Collect common data from sensor
        for (Data d: listOfData) {
            switch (d.getSensor()){
                case Data.TEMPERATURE:
                    tempData.add(d);
                    break;
                case Data.LIGHT:
                    ligthData.add(d);
                    break;
                case Data.VENTILATOR:
                    ventData.add(d);
                    break;
                default:
                    System.err.println("WRONG SENSOR DATA");
            }
        }


        // Print common data to file
    }
}
