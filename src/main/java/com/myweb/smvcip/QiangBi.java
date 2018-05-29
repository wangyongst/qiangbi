package com.myweb.smvcip;


import com.myweb.smvcip.refresh.Refresh;
import com.myweb.smvcip.utils.Result;

import java.text.SimpleDateFormat;
import java.util.Date;

public class QiangBi {
    private boolean need = true;

    public static boolean login(String username, String password) throws Exception {
        while (true) {
            Result result = Refresh.login(username, password);
            if(result.getCode() == 4){
                System.out.println(new SimpleDateFormat("HH:mm:ss").format(new Date()) + " 账号：" + username + " 验证码识别失败！");
            }else if (result.getCode() == 403) {
                System.out.println(new SimpleDateFormat("HH:mm:ss").format(new Date()) + " 账号：" + username + " 登录失败，很可能是本机IP已经被禁，请为本机更换IP！");
                return false;
            } else if (result.getCode() == 1) {
                System.out.println(new SimpleDateFormat("HH:mm:ss").format(new Date()) + " 账号：" + username + " 登录成功！");
                System.out.println(result.getOut());
                return true;
            } else if(result.getCode() == 0){
                System.out.println(new SimpleDateFormat("HH:mm:ss").format(new Date()) + " 账号：" + username + " 获取验证码失败！");
            }else if(result.getCode() == 2){
                System.out.println(new SimpleDateFormat("HH:mm:ss").format(new Date()) + " 账号：" + username + " 频繁操作！");
            }
            else System.out.println(result.getCode());
        }
    }

//    public void refresh(Refresh refresh) {
//        int ref = 0;
//        long start = new Date().getTime();
//        try {
//            ref = refresh.refresh();
//        } catch (Exception e) {
//            return;
//        }
//        if (printCode(ref)) return;
//        else if (ref == 1) {
//            long end = new Date().getTime();
//            System.out.println(new SimpleDateFormat("HH:mm:ss").format(new Date()) + " 刷新账号：" + refresh.getUsername() + " 刷新成功，用时 " + (end - start) + "ms");
//            return;
//        } else if (ref == 2) {
//            System.out.println(new SimpleDateFormat("HH:mm:ss").format(new Date()) + " 刷新账号：" + refresh.getUsername() + " 频繁操作！");
//            return;
//        } else if (ref == 3) {
//            System.out.println(new SimpleDateFormat("HH:mm:ss").format(new Date()) + " 刷新账号：" + refresh.getUsername() + " 需要重新登录！");
//            need = true;
//            return;
//        } else if (ref == 9) {
//            System.out.println("qiang dao le !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//        }
//        return;
//    }
//
//    public static boolean printCode(int code) {
//        if (code == 403) {
//            System.out.println(new SimpleDateFormat("HH:mm:ss").format(new Date()) + " 代理IP地址被禁");
//            return true;
//        }
//        if (code == 302) {
//            System.out.println(new SimpleDateFormat("HH:mm:ss").format(new Date()) + " 对方服务器异常");
//            return true;
//        }
//        return false;
//    }
//
//
//    public void letGo(Refresh refresh) {
//        while (true) {
//            if (need) {
//                login(refresh);
//            } else refresh(refresh);
//        }
//    }
}