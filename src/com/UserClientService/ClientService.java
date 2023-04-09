package com.UserClientService;
//用于客户端的服务连接数据通道

import com.Common.Message;
import com.Common.MessageType;
import com.Common.UserInformation;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
public class ClientService {
    private UserInformation user = new UserInformation();
    private Socket socket;
    public boolean JudgeUser(String UserName, String passWord) {
        boolean flag = false;
        try {
            user.setUserName(UserName);
            user.setPassword(passWord);
            //创建一个Socket对象，指定服务器的IP地址和端口号
            try {
                socket = new Socket(InetAddress.getByName("127.0.0.1"), 9999);
                ObjectOutputStream objectOutStream = new ObjectOutputStream(socket.getOutputStream());
                objectOutStream.writeObject(user);//发送对象
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) objectInputStream.readObject();//接收对象
                //判断合法对象。
                if (message.getMessageType().equals(MessageType.message_succeed)) {//

                    flag = true;
                    ClientConnent clientConnent = new ClientConnent(socket);//创建线程
                    clientConnent.start();//启动线程
                    //加入集合
                    ClientCollection.addClient(user.getUserName(), clientConnent);

                }
              if(!flag){
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
        }
        return flag;
    }

    //请求用户列表
    public void requestUserList() {
        try {//创建一个Message对象
            Message message = new Message();
            message.setMessageType(MessageType.message_get_onLineFriend);
            message.setSender(user.getUserName());
            //发送服务器，获得线程的socket的输出流
            ClientConnent clientConnent = ClientCollection.getClient(user.getUserName());
            //getSocket返回个空指针异常
            Socket socket = clientConnent.getSocket();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
