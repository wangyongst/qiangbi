package com.myweb.smvcip;


import com.myweb.smvcip.refresh.Refresh;

import java.text.SimpleDateFormat;
import java.util.Date;

public class QiangBi {
    private boolean need = true;

    public static boolean mainLogin(String username, String password) {
        try {
            if (!Main.isLogin(new MainApi(username, password))) {
                System.out.println("抢购账号：" + username + " 登录失败，很可能是本机IP已经被禁，请为本机更换IP！");
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        System.out.println("抢购账号：" + username + " 登录成功！");
        return true;
    }

    public void login(Refresh refresh) {
        int isLogin = 0;
        try {
            isLogin = refresh.login();
        } catch (Exception e) {
            return;
        }
        if (printCode(isLogin)) return;
        else if (isLogin == 9) {
            System.out.println(new SimpleDateFormat("HH:mm:ss").format(new Date()) + " 刷新账号：" + refresh.getUsername() + " 登录成功！");
            need = false;
            return;
        } else if (isLogin == 2) {
            System.out.println(new SimpleDateFormat("HH:mm:ss").format(new Date()) + " 刷新账号：" + refresh.getUsername() + " 频繁操作！");
            return;
        } else if (isLogin == 3) {
            System.out.println(new SimpleDateFormat("HH:mm:ss").format(new Date()) + " 刷新账号：" + refresh.getUsername() + " 验证码识别失败！");
            return;
        }
        return;
    }

    public void refresh(Refresh refresh) {
        int ref = 0;
        long start = new Date().getTime();
        try {
            ref = refresh.refresh();
        } catch (Exception e) {
            return;
        }
        if (printCode(ref)) return;
        else if (ref == 1) {
            long end = new Date().getTime();
            System.out.println(new SimpleDateFormat("HH:mm:ss").format(new Date()) + " 刷新账号：" + refresh.getUsername() + " 刷新成功，用时 " + (end - start) + "ms");
            return;
        } else if (ref == 2) {
            System.out.println(new SimpleDateFormat("HH:mm:ss").format(new Date()) + " 刷新账号：" + refresh.getUsername() + " 频繁操作！");
            return;
        } else if (ref == 3) {
            System.out.println(new SimpleDateFormat("HH:mm:ss").format(new Date()) + " 刷新账号：" + refresh.getUsername() + " 需要重新登录！");
            need = true;
            return;
        } else if (ref == 9) {
            System.out.println("qiang dao le !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        }
        return;
    }

    public static boolean printCode(int code) {
        if (code == 403) {
            System.out.println(new SimpleDateFormat("HH:mm:ss").format(new Date()) + " 代理IP地址被禁");
            return true;
        }
        if (code == 302) {
            System.out.println(new SimpleDateFormat("HH:mm:ss").format(new Date()) + " 对方服务器异常");
            return true;
        }
        return false;
    }


    public void letGo(Refresh refresh) {
        while (true) {
            if (need) {
                login(refresh);
            } else refresh(refresh);
        }
    }
}