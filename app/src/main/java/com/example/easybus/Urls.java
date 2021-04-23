package com.example.easybus;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Urls {
    private static InetAddress ip;
     static String url1 ,url2,url3;
    public static void main(String args[]) {

        try {
            ip = InetAddress.getLocalHost();
            System.out.println(ip.getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

         url1 = "http://";
         url2 = "172.20.10.5";
         url3 = "/mysql/";


    }
    static String ROOT_URL = url1+url2+url3;
    static String FORGOT_PASSWORD_URL = "http://192.168.27.107/LoginRegister/" + "forgot.php";
}