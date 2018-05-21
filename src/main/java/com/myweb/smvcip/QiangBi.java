package com.myweb.smvcip;


import com.myweb.smvcip.refresh.Refresh;
import com.myweb.smvcip.refresh.RefreshApi;

public class QiangBi {

    public static boolean mainLogin(String username, String password) {
        try {
            //if(!Main.isLogin(new MainApi("rlh003","yhxt123456"))){
            if (!Main.isLogin(new MainApi(username, password))) {
                System.out.println("抢购账号：" + username + " 登录失败，很可能是本机IP已经被禁，请为本机更换IP！");
            }
        } catch (Exception e) {
            System.out.println("抢购账号：" + username + " 登录失败，程序出现异常！");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean refresh(String username, String password) {
        try {
            RefreshApi refreshApi = new RefreshApi();
            if (!Refresh.refresh(refreshApi, username, password)) {
                System.out.println("刷新账号：" + username + " 登录失败，程序将自动开启新线程！");
                return false;
            }
        } catch (Exception e) {
            System.out.println("刷新账号：" + username + " 登录失败，程序出现异常！");
            e.printStackTrace();
            return false;
        }
        return true;
    }
}