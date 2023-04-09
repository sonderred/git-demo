package com.UI;

import com.UserClientService.ClientService;

public class start_UI { //启动界面
     private boolean loop = true;
     private String key = "";
     private ClientService clientService = new ClientService();

    public static void main(String[] args) {
        start_UI start_UI = new start_UI();
        start_UI.ask_UI();
    }
    public void ask_UI() {
         while (loop) {
             System.out.println("Welcome to the TV show management system");
             System.out.println("\t\t1. 登录账号");
             System.out.println("\t\t9. Exit");
             System.out.print("Please select number: ");

             key = Utility.readString(1);//工具类Utility，接受输入
             switch (key) {
                 case "1":
                     System.out.println("登录账号");
                     System.out.println("请输入用户名：");
                     String UserName = Utility.readString(50);
                     System.out.println("请输入密码： ");
                     String passWord = Utility.readString(50);
                        if (clientService.JudgeUser(UserName, passWord)) {
                            System.out.println("登录成功");
                            ask_UI2(UserName);
                        } else {
                            System.out.println("登录失败");
                        }

                     break;
                 case "9":
                     System.out.println("Exit");
                     loop = false;
                     break;
                 default:
                     System.out.println("Please select and think clearly ");
             }
         }
     }

     public void ask_UI2(String UserName) {
        /*用户端可以查询、修改自己的个人信息（如用户名、个人信息等）、修改个人信息
        用户端可以在平台上打字聊天
        各用户端之间可以私聊
        用户端和服务端可以知道有哪些其他用户在线
        用户端和服务端能接收到其他用户上线和下线的信息
        用户端可以找回自己的密码，找回密码需要具备的条件可自定义*/
         String key1 = "";
         int i= 0;
         while (loop) {
             if(i==0){
             System.out.println("TV用户：" + UserName);
             System.out.println("\t 1.显示其他在线用户");
             System.out.println("\t 2.平台聊天");
             System.out.println("\t 3.用户私聊");
             System.out.println("\t 4.发送文件");
             System.out.println("\t 9. Exit");

                 System.out.println("Please select number: ");
             }else{
                 System.out.println("选择number，进行操作: ");
             }
             key1 = Utility.readString(1);
             switch (key1) {
                 case "1":
                     System.out.println("==显示其他用户==");
                     clientService.requestUserList();
                     try {
                         Thread.sleep(500);
                     } catch (InterruptedException e) {
                         throw new RuntimeException(e);
                     }
                     System.out.println("显示其他用户成功");
                     break;
                 case "2":
                     System.out.println("平台聊天");
                     break;
                 case "3":
                     System.out.println("用户私聊");
                        break;
                 case "4":
                     System.out.println("发送文件");
                        break;
                 case "9":
                     System.out.println("Exit");
                     loop = false;
                     break;
                    default:
                        System.out.println("Please select and think clearly ");
             }
             i++;
         }
     }
 }
