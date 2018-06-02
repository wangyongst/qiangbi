package com.myweb;

import com.myweb.smvcip.QiangBi;
import com.myweb.smvcip.account.Account;
import com.myweb.smvcip.account.Accounts;
import com.myweb.smvcip.account.Server;
import com.myweb.smvcip.account.Test;
import com.myweb.smvcip.utils.Timer;

import java.util.List;

public class Start {
    public static final int SLEEP = 60;
    public static final int MAX_THREAD = 1;


    public static void main(String[] args) {

        Test.makeAccount();

        Timer timer = new Timer();
        while (timer.getCount() < MAX_THREAD) {
            Accounts.getAccounts().forEach((k,v) -> {
                try {
                    Thread.sleep(Start.SLEEP);
                    System.out.println(Timer.gotit);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!v.isLogined()) {
                    timer.setCount(timer.getCount() + 1);
                   QiangBi.login(k,v.getPassword());
                    timer.setCount(timer.getCount() - 1);
                } else {
                    timer.setCount(timer.getCount() + 1);
                    QiangBi.refresh(k);
                    timer.setCount(timer.getCount() -1);
                }
            });
        }
    }
}
