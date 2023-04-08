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
                    System.out.println("登录成+6666666666661功");
                    ClientConnent clientConnent = new ClientConnent(socket);//创建线程
                    clientConnent.start();//启动线程
                    //加入集合

                }
                if (flag) {
                    System.out.println("登录成功");
                } else {
                    System.out.println("登录失败");
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
        }
        return flag;
    }
}