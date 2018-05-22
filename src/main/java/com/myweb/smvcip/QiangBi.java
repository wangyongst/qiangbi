package com.myweb.smvcip;


import com.myweb.smvcip.refresh.Refresh;
import com.myweb.smvcip.refresh.RefreshApi;

public class QiangBi {

    public static boolean mainLogin(String username, String password) {
        try {
            if (!Main.isLogin(new MainApi(username, password))) {
                System.out.println("抢购账号：" + username + " 登录失败，很可能是本机IP已经被禁，请为本机更换IP！");
                return false;
            }
        } catch (Exception e) {
           e.printStackTrace();
            return false;
        }
        System.out.println("抢购账号：" + username + " 登录成功！");
        return true;
    }

    public static boolean isLogin(String username, String password) {
        boolean refreshLogin = false;
        try {
            RefreshApi refreshApi = new RefreshApi();
           refreshLogin = Refresh.isLogin(refreshApi, username, password);
            if(!refreshLogin) {
                System.out.println("刷新账号：" + username + " 登录失败，程序将自动重新登录！");
            }
        } catch (Exception e) {
            return false;
        }
        System.out.println("刷新账号：" + username + " 登录成功！");
        return refreshLogin;
    }

    public static boolean refresh(String username, String password) {
        boolean refresh = false;
        try {
            RefreshApi refreshApi = new RefreshApi();
            refresh = Refresh.refresh(refreshApi, username, password);
            if (!refresh) {
                System.out.println("刷新账号：" + username + " 刷新失败，程序将自动重新刷新！");
            }
        } catch (Exception e) {
            return true;
        }
        return refresh;
    }


    public static void letGo(String username, String password) {
        boolean login = false;
        while (true) {
            while (!login) login = isLogin(username, password);
            login = refresh(username, password);
        }
    }
}