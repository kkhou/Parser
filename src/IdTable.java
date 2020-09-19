import java.util.HashMap;

public class IdTable {

    // data member
    public HashMap<String, Integer> ID_hash;

    public IdTable() {
        ID_hash = new HashMap<String, Integer>();
    }

    // member methods
    public void add(String id){
        ID_hash.put(id, ID_hash.size());
    }

    public int getAddress(String id) {
        if (ID_hash.containsKey(id)) {
            return ID_hash.get(id);
        } else {
            return -1;
        }
    }

    public String toString(){
        return String.valueOf(ID_hash.keySet());
    }
}
