package com.UserClientService;
//用于客户端的服务连接数据通道

import com.Common.Message;
import com.Common.MessageType;
import com.Common.UserInformation;

import java.io.*;
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
                //判断合法对象,若成功则发送上线消息
                if (message.getMessageType().equals(MessageType.message_succeed)) {//

                    flag = true;
                    ClientConnent clientConnent = new ClientConnent(socket);//创建线程
                    clientConnent.start();//启动线程
                    //加入集合
                    ClientCollection.addClient(user.getUserName(), clientConnent);
                }
                //注册
                else if (message.getMessageType().equals(MessageType.message_register_succeed)) {
                    flag = true;
                }
                if (!flag) {
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
            System.out.println(user.getUserName());
            //getSocket返回个空指针异常
            Socket socket = clientConnent.getSocket();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//用户私聊
    public void privateChat(String receiver, String message) {
        System.out.println("消息发送中...");
        Message privateMessage = new Message();
        privateMessage.setMessageType(MessageType.message_com_mes);
        privateMessage.setSender(user.getUserName());
        privateMessage.setReceiver(receiver);
        privateMessage.setContent(message);
        try {
            //发送服务器，获得线程的socket的输出流
            ObjectOutputStream objectOutputStream =
                    new ObjectOutputStream(ClientCollection.getClient(user.getUserName()).getSocket().getOutputStream());
            objectOutputStream.writeObject(privateMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//平台聊天
    public void platformChat(String message) {
        System.out.println("消息发送中...");
        Message platformMessage = new Message();
        platformMessage.setMessageType(MessageType.message_com_mes_platform);
        platformMessage.setSender(user.getUserName());
        platformMessage.setContent(message);
        try {
            //发送服务器，获得线程的socket的输出流
            ObjectOutputStream objectOutputStream =
                    new ObjectOutputStream(ClientCollection.getClient(user.getUserName()).getSocket().getOutputStream());
            objectOutputStream.writeObject(platformMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//发送文件
    public void sendFile(String receiver, String srcfilePath,String desFilePath) {
        System.out.println("文件发送中...");
        Message fileMessage = new Message();
        fileMessage.setMessageType(MessageType.message_sendingFile);
        fileMessage.setSender(user.getUserName());
        fileMessage.setReceiver(receiver);
        fileMessage.setSrcFilePath(srcfilePath);
        fileMessage.setDestFilePath(desFilePath);
        try {
            FileInputStream fileInputStream = null;
            String srcFilePath = fileMessage.getSrcFilePath();
            fileInputStream = new FileInputStream(srcFilePath);
            //定义一个字节数组,用于存储读取到的文件数据
            byte[] fileBytes = new byte[(int) new File(srcFilePath).length()];
            fileInputStream.read(fileBytes);//将文件数据读取到字节数组中
            fileMessage.setFileBytes(fileBytes);//将文件数据存储到Message对象中
           if(fileInputStream!=null) fileInputStream.close();
            ObjectOutputStream objectOutputStream =
                    new ObjectOutputStream(ClientCollection.getClient(user.getUserName()).getSocket().getOutputStream());
            objectOutputStream.writeObject(fileMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void returnOnlineMessage() {
        Message message = new Message();
        message.setMessageType(MessageType.message_ret_userOnLine);
        message.setSender(user.getUserName());
        try {
            ObjectOutputStream objectOutputStream =
                    new ObjectOutputStream(ClientCollection.getClient(user.getUserName()).getSocket().getOutputStream());
            objectOutputStream.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //退出
    public void exit() {
        Message message = new Message();
        message.setMessageType(MessageType.message_client_exit);
        message.setSender(user.getUserName());
        try {
            //发送服务器，获得线程的socket的输出流
            ObjectOutputStream objectOutputStream =
                    new ObjectOutputStream(ClientCollection.getClient(user.getUserName()).getSocket().getOutputStream());
            objectOutputStream.writeObject(message);
            ClientCollection.removeClient(user.getUserName());
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();}
    }
    //查询个人信息
    public void requestReferUserInformation() {
        Message message = new Message();
        message.setMessageType(MessageType.message_referUserInformation);
        message.setSender(user.getUserName());
        try {
            //发送服务器，获得线程的socket的输出流
            ObjectOutputStream objectOutputStream =
                    new ObjectOutputStream(ClientCollection.getClient(user.getUserName()).getSocket().getOutputStream());
            objectOutputStream.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
//修改个人信息
    public void requestModifyUserInformation(String newUserName, String newpassWord) {

        Message message = new Message();
        message.setMessageType(MessageType.message_modifyUserInformation);
        message.setSender(user.getUserName());
        user.setUserName(newUserName);
        user.setPassword(newpassWord);
        message.setNewUserName(newUserName);
        message.setNewPassword(newpassWord);
        try {
            //发送服务器，获得线程的socket的输出流
            ObjectOutputStream objectOutputStream =
                    new ObjectOutputStream(ClientCollection.getClient(message.getSender()).getSocket().getOutputStream());
            objectOutputStream.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //修改成功后，将ClientCollection中的键值对删除，重新添加
        ClientCollection.addClient(message.getNewUserName(), ClientCollection.getClient(message.getSender()));
        ClientCollection.removeClient(message.getSender());
    }
}

