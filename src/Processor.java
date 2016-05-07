import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.io.*;
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
        System.out.println("Run is started from ProcessorThread");
        while (true) {
            try {
                Thread.sleep(PERIOD);
                System.out.println("Starting processing!!! :D");
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
            if (!nodeToDataMapping.containsKey(nodeID)) {
                System.out.println("New node found: "+nodeID);
                nodeToDataMapping.put(nodeID, new ArrayList<Data>());
            }
            System.out.println("Saving data from that node: "+d);
            nodeToDataMapping.get(nodeID).add(d);
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

        printArrayJSON(Data.getLiveNodes(),"data-live-nodes.json");

        clearAll();
    }

    private void printArrayJSON(List<String> liveNodes, String fileName) {
        NodesAlive nA = new NodesAlive(liveNodes);
        Gson gson = new Gson();
        String data = gson.toJson(nA,NodesAlive.class);
        System.out.println("JSON data for live nodes: "+data);
        writeToFile(data, fileName, false);
    }

    private void clearAll() {
        Data.clearAll();
    }

    private void printDataJSON(HashMap<Integer, ArrayList<Data>> nodeToDataMapping, String fileName) {
        Gson gson = new Gson();
        String dataRead = readFromFile(fileName);
        Nodes nodes;

        if(dataRead==null){
            nodes = new Nodes();
        }else{
            nodes = gson.fromJson(dataRead, Nodes.class);
        }


        for(int nodeID: nodeToDataMapping.keySet()){
            Node n;
            n = nodes.get(nodeID);
            if(n==null){
                n = new Node(nodeID);
            }
            System.out.println("Node "+n.getID()+" found.");
            System.out.println("\t pushing data");
            n.pushData(nodeToDataMapping.get(nodeID));
            nodes.pushNode(n);
        }

        String data = gson.toJson(nodes,Nodes.class);
        writeToFile(data, fileName, false);
    }

    private String readFromFile(String fileName) {
        try {
            File file = new File(fileName);

            //if file doesnt exists, then create it
            if (!file.exists()) {
                return null;
            }

            FileReader fileReader = new FileReader(file.getName());
            try (BufferedReader reader = new BufferedReader(fileReader)) {
                StringBuilder sb = new StringBuilder();
                String line = reader.readLine();

                while (line != null) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                    line = reader.readLine();
                }
                System.out.println("Done");
                return sb.toString();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void printDataCSV(List<Data> dataList, String fileName) {
        String data = listToDataString(dataList);
        writeToFile(data,fileName, true);
    }

    private void writeToFile(String data, String fileName, boolean append) {
        try {
            File file = new File(fileName);

            //if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();

            }
            FileWriter fileWriter = new FileWriter(file.getName(), append);
            BufferedWriter bufferWriter = new BufferedWriter(fileWriter);
            bufferWriter.write(data);
            bufferWriter.close();

            System.out.println("Done");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String listToDataString(List<Data> dataList) {
        StringBuilder b = new StringBuilder();
        for (Data d : dataList) {
            b.append("\n"+d.getTime() + "," + d.getValue() + "\n");
            System.out.println("Adding data line: "+d.getTime() + "," + d.getValue());
        }
        return b.toString().replaceAll("(?m)^[ \t]*\r?\n", "");
    }
}
