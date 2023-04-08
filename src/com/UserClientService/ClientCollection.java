package com.UserClientService;

import java.util.HashMap;

//存放客户端的信息
public class ClientCollection {
    private static HashMap<String, ClientConnent> clients = new HashMap<String, ClientConnent>();
    public static void addClient(String userName, ClientConnent clientConnent) {
        clients.put(userName, clientConnent);
    }
    public static ClientConnent getClient(String userName) {
        return clients.get(userName);
    }
    public static void removeClient(String userName) {
        clients.remove(userName);
    }
}
