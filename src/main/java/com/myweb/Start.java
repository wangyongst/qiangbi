package com.myweb;

import com.myweb.smvcip.QiangBi;
import com.myweb.smvcip.account.Account;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Start {
    public static final int SLEEP = 100;
    public static final int MAX_THREAD = 1;
    private static ExecutorService executors = Executors.newFixedThreadPool(MAX_THREAD);
    private static  CountDownLatch countDownLatch = new CountDownLatch(MAX_THREAD);

    public static void main(String[] args) {

        if (!QiangBi.login("rlh003", "yhxt123456")) {
            System.exit(0);
        }
        Account.getAccounts().forEach((k, v) -> {
            QiangBi.login(k, v);
        });

        while (((ThreadPoolExecutor)executors).getActiveCount() <= MAX_THREAD) {
            Account.getLogined().forEach((s, b) -> {
                if (!b) {
                    Account.getAccounts().forEach((k, v) -> {
                        if (k.equals(s)) QiangBi.login(k, v);
                    });
                }
                executors.execute(new Thread(() -> {
                    try {
                        Thread.sleep(SLEEP);
                        QiangBi.refresh(s);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }));
            });
        }
    }
}
