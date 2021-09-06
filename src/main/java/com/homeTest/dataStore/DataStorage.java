package com.homeTest.dataStore;

import com.homeTest.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DataStorage {

    private static DataStorage instance = null;

    private DataStorage() {
    }

    public static DataStorage getInstance() {
        if (instance == null) {
            instance = new DataStorage();
        }
        return instance;
    }

    private Map<String, String> userDataStore = new HashMap<>();

    public void addUser(User user) {
        userDataStore.putIfAbsent(user.getUserName(), user.getPassword());
    }

    public void updatePassword(User user) {

        userDataStore.put(user.getUserName(), user.getPassword());
    }

    public Set<String> getAllUsers(){
        return userDataStore.keySet();
    }

    public String getUserPassword(String user){
        return userDataStore.get(user);
    }
}
