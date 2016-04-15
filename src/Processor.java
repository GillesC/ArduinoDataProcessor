import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Gilles Callebaut on 15/04/2016.
 * <p>
 * Class which gets dataList from class Data
 * And writes to the corresponding files
 */
class Processor implements Runnable {
    private static long PERIOD = 30 * 1000;

    @Override
    public void run() {
        while (true) {
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

        HashMap<Integer, ArrayList<Data>> nodeToDataMapping = new HashMap<>();


        // Set data from node
        for (Data d : listOfData) {
            int nodeID = d.getNodeID();
            if (nodeToDataMapping.containsKey(nodeID)) {
                List<Data> listForNode = nodeToDataMapping.get(nodeID);
                listForNode.add(d);
            } else {
                nodeToDataMapping.put(nodeID, new ArrayList<>());
            }
        }


        // Collect common data from sensor
        for (Data d : listOfData) {
            switch (d.getSensor()) {
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
        printDataCSV(tempData, "data-temp.csv");
        printDataCSV(ventData, "data-vent.csv");
        printDataCSV(ligthData, "data-light.csv");

        printDataJSON(nodeToDataMapping,"data-nodes.json");
    }

    private void printDataJSON(HashMap<Integer, ArrayList<Data>> nodeToDataMapping, String fileName) {
        Gson gson = new Gson();
        Nodes nodes = new Nodes();
        for(int i=0; i<nodeToDataMapping.size();i++){
            Node n = new Node(nodeToDataMapping.get(i).get(0).getNodeID());
            n.pushData(nodeToDataMapping.get(i));
            nodes.pushNode(n);
        }
        String data = gson.toJson(nodes,Nodes.class);
        writeToFile(data, fileName);
    }

    private void printDataCSV(List<Data> dataList, String fileName) {
        String data = listToDataString(dataList);
        writeToFile(data,fileName);
    }

    private void writeToFile(String data, String fileName) {
        try {
            File file = new File(fileName);

            //if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fileWritter = new FileWriter(file.getName(), true);
            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
            bufferWritter.write(data);
            bufferWritter.close();

            System.out.println("Done");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String listToDataString(List<Data> dataList) {
        StringBuilder b = new StringBuilder();
        for (Data d : dataList) {
            b.append(d.getTime() + "," + d.getValue() + System.lineSeparator());
        }
        return b.toString().trim();
    }
}
