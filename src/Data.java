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

    public static final String TEMPERATURE = "TEMP";
    public static final String LIGHT = "LIGHT";
    public static final String VENTILATOR = "VENT";

    private int node;
    private String sensor;
    private String data;
    private String time;

    Data(String line) {
        String[] dataSplit = line.split("#");
        if(dataSplit.length!=4){
            System.err.println("Wrong format, deleting data...");
            return;
        }
        sensor = dataSplit[0];
        node = Integer.parseInt(dataSplit[1]);
        data = dataSplit[2];
        time = dataSplit[3];
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

    public String getSensor() {
        return sensor;
    }

    public int getNode() {
        return node;
    }
}
