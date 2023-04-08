//package com.Server;
//
//import com.ServerCommon.Message;
//import com.ServerCommon.MessageType;
//import com.ServerCommon.UserInformation;
//import org.testng.annotations.Test;
//
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.util.HashMap;
//@Test
////服务器端，连接客户端，接收客户端的消息，发送消息给客户端
//public class ServerService {
//    private ServerSocket serverSocket ;//客户端的套接字
//    //存放用户
//    private static HashMap<String, UserInformation> ServerCollection = new HashMap<String, UserInformation>();
//    static{
//        ServerCollection.put("bili", new UserInformation("bili", "123456"));
//    }
//    //判断用户是否合法
//    private boolean checkUser(String userName, String password) {
//        UserInformation UserInformation = ServerCollection.get(userName);
//        if (UserInformation == null) {
//            return false;
//        }
//        if (ServerCollection.get(userName).getPassword().equals(password)) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//    public void serverService () {
//        System.out.println("服务器运行...");
//        try {
//            try {
//                serverSocket = new ServerSocket(9999);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            while (true) {
//                Socket socket = serverSocket.accept();
//                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
//                //接受客户端的消息
//                UserInformation object = (UserInformation) objectInputStream.readObject();
//                Message message = new Message();
//                if (checkUser(object.getUserName(), object.getPassword())) {
//                    System.out.println("登录成功");
//                    message.setMessageType(MessageType.message_succeed);
//                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
//                    objectOutputStream.writeObject(message);
//                    ServerConnent serverConnent = new ServerConnent(object.getUserName(), socket);
//                    serverConnent.start();
//                    //加入集合
//                }
//                else{
//                    System.out.println("登录失败");
//                    message.setMessageType(MessageType.message_login_fail);
//                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
//                    objectOutputStream.writeObject(message);
//                    socket.close();//关闭
//                }
//            }
//        } catch(Exception e){
//            e.printStackTrace();
//        }finally {
//            try {
//                serverSocket.close();//退出后台
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}
