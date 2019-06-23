

import javax.ejb.Singleton;
import java.util.ArrayList;

@Singleton
public class OrderStorage {
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