import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Gilles Callebaut on 15/04/2016.
 *
 * Data format in:
 * #SENSOR#NODE#DATA#TIME
 */
class Data {
    private static List<Data> list = Collections.synchronizedList(new ArrayList<>());

    static final String TEMPERATURE = "TEMP";
    static final String LIGHT = "LIGHT";
    static final String VENTILATOR = "VENT";

    private int node;
    private String sensor;
    private String data;
    private String time;

    // TODO ook checken op juiste sensor
    Data(String line) throws Exception {
        String[] dataSplit = line.split("#");
        if(dataSplit.length!=4 || !correctSensor(dataSplit[0])){
            System.err.println("Wrong format, deleting data... \t RECEIVED: "+line);
            throw new Exception("WRONG FORMAT or No valid sensor");
        }
        sensor = dataSplit[0];
        node = Integer.parseInt(dataSplit[1]);
        data = dataSplit[2];
        time = dataSplit[3];
    }

    private boolean correctSensor(String sensor) {
        boolean match = false;
        if(sensor.equals(LIGHT)) match = true;
        if(sensor.equals(TEMPERATURE)) match = true;
        if(sensor.equals(VENTILATOR)) match = true;
        return match;
    }

    static void pushData(Data d){
        list.add(d);
    }

    static List<Data> getDataList(){
        List<Data> dataList = new ArrayList<>(list);
        list.clear();
        return dataList;
    }

    @Override
    public String toString() {
        return "Data{" +
                "node=" + node +
                ", sensor='" + sensor + '\'' +
                ", data='" + data + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    String getSensor() {
        return sensor;
    }

    int getNodeID() {
        return node;
    }

    String getValue() {
        return data;
    }

    String getTime() {
        return time;
    }
}
