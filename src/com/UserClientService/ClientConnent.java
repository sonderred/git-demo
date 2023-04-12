package com.UserClientService;

import com.Common.Message;
import com.Common.MessageType;

import java.io.FileOutputStream;
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
                }
                //从服务器端接受私聊消息，返回给receiver
                else if(message.getMessageType().equals(MessageType.message_com_mes)){
                    System.out.println(message.getSender()+"对你个靓仔说："+message.getContent());
                }
                else if (message.getMessageType().equals(MessageType.message_sending_messsage)) {
                    System.out.println(message.getContent());
                }
                //发送平台消息
                else if(message.getMessageType().equals(MessageType.message_ret_com_mes_platform)){
                    System.out.println(message.getSender()+"对大家说："+message.getContent());
                }
                //接收文件
                else if(message.getMessageType().equals(MessageType.message_ret_sendingFile)){
                  //接收文件
                    FileOutputStream fileOnputStream = new FileOutputStream(message.getDestFilePath(),true);
                    fileOnputStream.write(message.getFileBytes());
                    fileOnputStream.close();
                    System.out.println(message.getSender()+"给你发了一个文件："+message.getDestFilePath());
                    System.out.println("文件大小："+message.getFileBytes().length+"字节");
                }
                //接收用户上下线消息
                else if(message.getMessageType().equals(MessageType.message_ret_userOnLine)){
                      if(message.getMessageType().equals(MessageType.message_ret_userOnLine)  )
                         { System.out.println(message.getSender()+"上线了");}
                      else
                         { System.out.println(message.getSender()+"下线了");}
                    System.out.println("当前在线用户：");
                    String[] onLineFriend = message.getUserlist();
                    for (int i = 0; i < onLineFriend.length; i++) {
                        System.out.println("在线用户: " + onLineFriend[i]);
                    }
                }
                else if(message.getMessageType().equals(MessageType.message_ret_userOffLine)){
                    System.out.println(message.getSender()+"下线了");
                    System.out.println("当前在线用户：");
                    String[] onLineFriend = message.getUserlist();
                    for (int i = 0; i < onLineFriend.length; i++) {
                        System.out.println("\t " + onLineFriend[i]);
                    }
                }
                //查询个人信息
                else if(message.getMessageType().equals(MessageType.message_ret_referUserInformation)){
                    System.out.println("用户信息：");
                    System.out.println("用户名："+message.getSender());
                    System.out.println("昵称："+message.getRealName());
                    System.out.println("出生地："+message.getBirthPlace());
                }
                //返回修改个人信息的回复
                else if(message.getMessageType().equals(MessageType.message_modifyUserInformation)){
                    System.out.println(message.getSender()+" 用户成功"+message.getContent()+"为 "+message.getNewUserName()+" 请重新登录");
                    //重新登录
                    System.out.println("请重新登录");
                    getSocket().close();
                    break;
                }
                //返回找回密码的回复
                else if(message.getMessageType().equals(MessageType.message_findPassword)){
                    System.out.println("记好密码，不能多看一眼："+message.getPassWord());
                    //重新登录
                    System.out.println("请重新登录");
                    getSocket().close();
                    break;
                }
                else {
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
