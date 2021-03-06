package com.myweb.smvcip;


import com.myweb.smvcip.account.Accounts;
import com.myweb.smvcip.account.Test;
import com.myweb.smvcip.refresh.Refresh;
import com.myweb.smvcip.refresh.RefreshApi;
import com.myweb.smvcip.utils.Result;
import com.myweb.smvcip.utils.Timer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.Date;

public class QiangBi {

    public static boolean login(String username, String password){
            Result result = null;
            try {
                result = Refresh.login(username, password);
            } catch (Exception e) {
                return false;
            }
            if(result.getCode() == 4){
                System.out.println(new SimpleDateFormat("HH:mm:ss").format(new Date()) + " IP:" + Accounts.getAccounts().get(username).getIp() + " 账号：" + username + " 验证码识别失败！");
                return false;
            }else if (result.getCode() == 403) {
                System.out.println(new SimpleDateFormat("HH:mm:ss").format(new Date()) + " IP:" + Accounts.getAccounts().get(username).getIp() + " 账号：" + username + " 登录失败，很可能是本机IP已经被禁，请为本机更换IP！");
                return false;
            } else if (result.getCode() == 1 || result.getCode() == 302) {
                System.out.println(new SimpleDateFormat("HH:mm:ss").format(new Date()) + " IP:" + Accounts.getAccounts().get(username).getIp() + " 账号：" + username + " 登录成功！");
                Accounts.setLogined(username);
                return true;
            } else if(result.getCode() == 0){
                System.out.println(new SimpleDateFormat("HH:mm:ss").format(new Date()) + " IP:" + Accounts.getAccounts().get(username).getIp() + " 账号：" + username + " 获取验证码失败！");
                return false;
            }else if(result.getCode() == 2){
                System.out.println(new SimpleDateFormat("HH:mm:ss").format(new Date()) + " IP:" + Accounts.getAccounts().get(username).getIp() + " 账号：" + username + " 频繁操作！");
                return false;
            }
            else {
                System.out.println(result.getCode());
                return false;
            }
    }

    public static boolean refresh(String username) {
        Result result = null;
        long start = new Date().getTime();
        try {
            result = RefreshApi.refresh(username);
        } catch (Exception e) {
            return false;
        }
        if (result.getCode() == 1) {
            long end = new Date().getTime();
            System.out.println(new SimpleDateFormat("HH:mm:ss").format(new Date()) +  " IP:" + Accounts.getAccounts().get(username).getIp() + " 账号："  +  username + " 刷新成功，用时 " + (end - start) + "ms");
            if(result.getOut().contains("买入")) {
                Document document = Jsoup.parse(result.getOut());
                Elements elements = document.select("a[href*=/index.php/Works/isr?do=purchase&id=]");
                elements.forEach(e-> {
                    try {
                        RefreshApi.buy(Accounts.MAINACCOUNT,e.attr("href"));
                    } catch (Exception e1) {
                    }
                });
                Timer.gotit = Timer.gotit + 1;
            }else if(result.getOut().contains("验证码")){
                System.out.println(new SimpleDateFormat("HH:mm:ss").format(new Date()) +  " IP:" + Accounts.getAccounts().get(username).getIp() + " 账号：" + username + " 刷新失败，需重新登录");
                Accounts.outLogined(username);
                return false;
            }
        } else if (result.getCode() == 403) {
            System.out.println(new SimpleDateFormat("HH:mm:ss").format(new Date()) +  " IP:" + Accounts.getAccounts().get(username).getIp() + " 账号：" + username + " 刷新失败，很可能是本机IP已经被禁，请为本机更换IP！");
            return false;
        }
        return false;
    }
}