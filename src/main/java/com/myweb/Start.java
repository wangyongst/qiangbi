package com.myweb;

import com.myweb.smvcip.QiangBi;
import com.myweb.smvcip.account.Account;
import com.myweb.smvcip.utils.Timer;

public class Start {
    public static final int SLEEP = 100;
    public static final int MAX_THREAD = 1;


    public static void main(String[] args) {

//        if (!QiangBi.login("rlh003", "yhxt123456")) {
//            System.exit(0);
//        }
        Account.getAccounts().forEach((k, v) -> {
            QiangBi.login(k, v);
        });
        Timer timer = new Timer();
        while (timer.getCount() < MAX_THREAD) {
            Account.getLogined().forEach((s, b) -> {
                try {
                    Thread.sleep(200);
                    System.out.println(Timer.gotit);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!b) {
                    timer.setCount(timer.getCount() + 1);
                    Account.getAccounts().forEach((k, v) -> {
                        if (k.equals(s)) QiangBi.login(k, v);
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
