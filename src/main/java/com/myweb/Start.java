package com.myweb;

import com.myweb.smvcip.QiangBi;
import com.myweb.smvcip.account.Account;
import com.myweb.smvcip.account.Accounts;
import com.myweb.smvcip.account.Server;
import com.myweb.smvcip.account.Test;
import com.myweb.smvcip.utils.Timer;

import java.util.List;

public class Start {
    public static final int SLEEP = 100;
    public static final int MAX_THREAD = 1;


    public static void main(String[] args) {

//        if (!QiangBi.login("rlh003", "yhxt123456")) {
//            System.exit(0);
//        }
        Timer count = new Timer();
        count.setCount(0);
        final List<Server> servers = Test.getServer();
        Test.getUNAPW().forEach((k,v) ->{
            Account account = new Account();
            account.setUsername(k);
            account.setPassword(v);
            account.setIp(servers.get(count.getCount()).getIp());
            account.setPort(servers.get(count.getCount()).getPort());
            account.setLogined(false);
            Accounts.putAccounts(account);
            count.setCount(count.getCount()+1);
            if (count.getCount() > 1) count.setCount(0);
        });

        Accounts.getAccounts().forEach((k, v) -> {
            QiangBi.login(k, v.getPassword());
        });
        Timer timer = new Timer();
        while (timer.getCount() < MAX_THREAD) {
            Accounts.getLogined().forEach((s, b) -> {
                try {
                    Thread.sleep(200);
                    System.out.println(Timer.gotit);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!b) {
                    timer.setCount(timer.getCount() + 1);
                    Accounts.getAccounts().forEach((k, v) -> {
                        if (k.equals(s)) QiangBi.login(k, v.getPassword());
                    });
                    timer.setCount(timer.getCount() - 1);
                } else {
                    timer.setCount(timer.getCount() + 1);
                    QiangBi.refresh(s);
                    timer.setCount(timer.getCount() -1);
                }
            });
        }
    }
}
