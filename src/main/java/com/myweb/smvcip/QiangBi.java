package com.myweb.smvcip;


import com.myweb.smvcip.account.Account;
import com.myweb.smvcip.refresh.Refresh;
import com.myweb.smvcip.refresh.RefreshApi;
import com.myweb.smvcip.utils.Result;
import com.myweb.smvcip.utils.Timer;

import java.text.SimpleDateFormat;
import java.util.Date;

public class QiangBi {

    public static boolean login(String username, String password){
        while (true) {
            Result result = null;
            try {
                Thread.sleep(200);
                result = Refresh.login(username, password);
            } catch (Exception e) {
                //e.printStackTrace();
            }
            if(result.getCode() == 4){
                System.out.println(new SimpleDateFormat("HH:mm:ss").format(new Date()) + " 账号：" + username + " 验证码识别失败！");
            }else if (result.getCode() == 403) {
                System.out.println(new SimpleDateFormat("HH:mm:ss").format(new Date()) + " 账号：" + username + " 登录失败，很可能是本机IP已经被禁，请为本机更换IP！");
                return false;
            } else if (result.getCode() == 1 || result.getCode() == 302) {
                System.out.println(new SimpleDateFormat("HH:mm:ss").format(new Date()) + " 账号：" + username + " 登录成功！");
                Account.setLogined(username);
                return true;
            } else if(result.getCode() == 0){
                System.out.println(new SimpleDateFormat("HH:mm:ss").format(new Date()) + " 账号：" + username + " 获取验证码失败！");
            }else if(result.getCode() == 2){
                System.out.println(new SimpleDateFormat("HH:mm:ss").format(new Date()) + " 账号：" + username + " 频繁操作！");
                return false;
            }
            else System.out.println(result.getCode());
        }
    }

    public static boolean refresh(String username) {
        Result result = null;
        long start = new Date().getTime();
        try {
            result = RefreshApi.refresh();
        } catch (Exception e) {
            //e.printStackTrace();
            return false;
        }
        if (result.getCode() == 1) {
            long end = new Date().getTime();
            System.out.println(new SimpleDateFormat("HH:mm:ss").format(new Date()) + " 账号：" + username + " 刷新成功，用时 " + (end - start) + "ms");
            if(result.getOut().contains("买入")) {
                System.out.println(result.getOut());
                Timer.gotit = Timer.gotit + 1;
            }else if(result.getOut().contains("验证码")){
                System.out.println(new SimpleDateFormat("HH:mm:ss").format(new Date()) + " 账号：" + username + " 刷新失败，需重新登录");
                Account.outLogined(username);
            }
            return true;
        } else if (result.getCode() == 403) {
            System.out.println(new SimpleDateFormat("HH:mm:ss").format(new Date()) + " 账号：" + username + " 刷新失败，很可能是本机IP已经被禁，请为本机更换IP！");
            return false;
        }
        return false;
    }
}