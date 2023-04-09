package com.UserClientService;

import com.Common.Message;
import com.Common.MessageType;

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
                //System.out.println("与服务器端保持连接中...");
                ObjectInputStream objectInputstream = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) objectInputstream.readObject();
                //发送消息给服务器端(获取好友在线列表)
                if(message.getMessageType().equals(MessageType.message_ret_onLineFriend)){
                    String[] onLineFriend = message.getUserlist();
                   // System.out.println(message.getReceiver()+"请求在线好友列表：");
                    for (int i = 0; i < onLineFriend.length; i++) {
                        System.out.println("在线用户: " + onLineFriend[i]);
                    }
                    /*for (String s : onLineFriend) {
                        System.out.println("用户 "+s);
                    }*/

                }else {
                    System.out.println("无用输出");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    public Socket getSocket() {
        return socket;
    }
}
