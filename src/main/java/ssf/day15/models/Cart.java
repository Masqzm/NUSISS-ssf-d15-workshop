package ssf.day15.models;

import java.util.TreeMap;

public class Cart {
    private String id;
    private String userID;
    // Cart items map (K:item name, V:qty)
    private TreeMap<String, Integer> items = new TreeMap<>();

    @Override
    public String toString() {
        return "Cart [id=" + id + ", userID=" + userID + ", items=" + items + "]";
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getUserID() {
        return userID;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }
    public TreeMap<String, Integer> getItems() {
        return items;
    }
    public void setItems(TreeMap<String, Integer> items) {
        this.items = items;
    }
}
