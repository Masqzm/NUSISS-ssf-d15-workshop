package ssf.day15.models;

import java.util.TreeMap;

public class Cart {
    private String id;
    //private String username;
    // Cart items map (K:item name, V:qty)
    private TreeMap<String, Integer> itemsMap = new TreeMap<>();

    @Override
    public String toString() {
        //return "Cart [id=" + id + ", username=" + username + ", itemsMap=" + itemsMap + "]";
        return "Cart [id=" + id + ", itemsMap=" + itemsMap + "]";
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    // public String getUsername() {
    //     return username;
    // }
    // public void setUsername(String username) {
    //     this.username = username;
    // }
    public TreeMap<String, Integer> getItemsMap() {
        return itemsMap;
    }
    public void setItemsMap(TreeMap<String, Integer> itemsMap) {
        this.itemsMap = itemsMap;
    }
}
