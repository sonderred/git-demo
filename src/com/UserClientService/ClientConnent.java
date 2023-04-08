package com.UserClientService;

import com.Common.Message;

import java.io.ObjectInputStream;
import java.net.Socket;

//接受服务器端的消息
public class ClientConnent extends Thread {
    private Socket socket;//客户端的套接字
    public ClientConnent(Socket socket) {
        this.socket = socket;
    }
    public void run() {
        //接受服务器端的消息
        while(true){
            try {
                //接受服务器端的消息
                System.out.println("接收服务器端的信息...");
                ObjectInputStream objectInputstream = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) objectInputstream.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
    public Socket getSocket() {
        return socket;
    }
}
