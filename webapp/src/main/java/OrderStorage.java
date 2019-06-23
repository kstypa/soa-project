import java.util.ArrayList;

public class OrderStorage {
    private static OrderStorage instance;

    public static OrderStorage getInstance() {
        if (instance == null) {
            instance = new OrderStorage();
        }
        return instance;
    }

    private OrderStorage() {

    }

    public ArrayList<String> arrayList = new ArrayList<>();

    public ArrayList<String> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<String> arrayList) {
        this.arrayList = arrayList;
    }

    public void add(String s){
        arrayList.add(s);
    }
}